package com.hexora.network.service;

import java.io.IOException;
import java.io.StringReader;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

final class NmapHostDiscovery {

    private static final Logger LOGGER = LoggerFactory.getLogger(NmapHostDiscovery.class);
    private static final Duration SCAN_TIMEOUT = Duration.ofSeconds(45);
    private static final String[] INSTALL_DIRECTORIES = {
        "ProgramFiles",
        "ProgramFiles(x86)"
    };

    List<NeighborEntry> discover(LocalNetworkDiscoveryService.LocalSubnet subnet) {
        Path executable = findExecutable();
        if (executable == null) {
            LOGGER.info("Nmap was not found; continuing with native local-network discovery");
            return List.of();
        }

        String target = LocalNetworkDiscoveryService.longToIpv4(subnet.network()).getHostAddress()
            + "/" + subnet.prefixLength();
        Process process;
        try {
            process = new ProcessBuilder(
                executable.toString(),
                "-sn",
                "-PR",
                "-n",
                "--min-rate",
                "100",
                "--max-retries",
                "1",
                "-oX",
                "-",
                target
            ).start();
        } catch (IOException exception) {
            LOGGER.warn("Nmap could not start; continuing with native local-network discovery", exception);
            return List.of();
        }

        ExecutorService outputReaders = Executors.newFixedThreadPool(2);
        try {
            Future<byte[]> xmlOutput = outputReaders.submit(process.getInputStream()::readAllBytes);
            Future<byte[]> errorOutput = outputReaders.submit(process.getErrorStream()::readAllBytes);
            if (!process.waitFor(SCAN_TIMEOUT.toMillis(), TimeUnit.MILLISECONDS)) {
                process.destroyForcibly();
                throw new NetworkDiscoveryException("Nmap local ARP discovery exceeded its time limit");
            }
            String xml = new String(xmlOutput.get(), StandardCharsets.UTF_8);
            String errors = new String(errorOutput.get(), StandardCharsets.UTF_8).trim();
            if (process.exitValue() != 0) {
                throw new NetworkDiscoveryException(
                    "Nmap local ARP discovery failed" + (errors.isEmpty() ? "" : ": " + errors));
            }
            return parseXml(xml, subnet);
        } catch (InterruptedException exception) {
            process.destroyForcibly();
            Thread.currentThread().interrupt();
            throw new NetworkDiscoveryException("Nmap local ARP discovery was interrupted", exception);
        } catch (ExecutionException exception) {
            process.destroyForcibly();
            throw new NetworkDiscoveryException("Nmap output could not be read", exception.getCause());
        } finally {
            outputReaders.shutdownNow();
        }
    }

    static List<NeighborEntry> parseXml(String xml, LocalNetworkDiscoveryService.LocalSubnet subnet) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
            var document = factory.newDocumentBuilder().parse(new InputSource(new StringReader(xml)));
            NodeList hosts = document.getElementsByTagName("host");
            List<NeighborEntry> devices = new ArrayList<>();
            for (int hostIndex = 0; hostIndex < hosts.getLength(); hostIndex++) {
                Element host = (Element) hosts.item(hostIndex);
                NodeList statuses = host.getElementsByTagName("status");
                if (statuses.getLength() == 0
                    || !"up".equals(((Element) statuses.item(0)).getAttribute("state"))) {
                    continue;
                }

                String address = null;
                String macAddress = null;
                NodeList addresses = host.getElementsByTagName("address");
                for (int addressIndex = 0; addressIndex < addresses.getLength(); addressIndex++) {
                    Element entry = (Element) addresses.item(addressIndex);
                    String type = entry.getAttribute("addrtype");
                    if ("ipv4".equals(type)) {
                        address = entry.getAttribute("addr");
                    } else if ("mac".equals(type)) {
                        macAddress = normalizeMacAddress(entry.getAttribute("addr"));
                    }
                }
                if (address != null && isInSubnet(address, subnet)) {
                    devices.add(new NeighborEntry(address, macAddress, "Nmap ARP"));
                }
            }
            return List.copyOf(devices);
        } catch (IOException | SAXException | javax.xml.parsers.ParserConfigurationException exception) {
            throw new NetworkDiscoveryException("Nmap returned invalid host-discovery XML", exception);
        }
    }

    private static boolean isInSubnet(String address, LocalNetworkDiscoveryService.LocalSubnet subnet) {
        try {
            Inet4Address parsed = LocalNetworkDiscoveryService.parseIpv4(address);
            return subnet.contains(parsed) && !parsed.equals(subnet.address());
        } catch (UnknownHostException exception) {
            return false;
        }
    }

    private static String normalizeMacAddress(String address) {
        if (!address.matches("(?i)[0-9a-f]{2}([:-][0-9a-f]{2}){5}")) {
            return null;
        }
        return address.replace('-', ':').toLowerCase(Locale.ROOT);
    }

    private static Path findExecutable() {
        for (String environmentVariable : INSTALL_DIRECTORIES) {
            String directory = System.getenv(environmentVariable);
            if (directory != null && !directory.isBlank()) {
                Path executable = Path.of(directory, "Nmap", "nmap.exe");
                if (Files.isRegularFile(executable)) {
                    return executable;
                }
            }
        }
        String path = System.getenv("PATH");
        if (path == null) {
            return null;
        }
        String pathSeparator = System.getProperty("path.separator", ";");
        for (String directory : path.split(java.util.regex.Pattern.quote(pathSeparator))) {
            Path executable = Path.of(directory).resolve("nmap.exe");
            if (Files.isRegularFile(executable)) {
                return executable;
            }
        }
        return null;
    }
}
