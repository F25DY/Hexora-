package com.hexora.network.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.List;
import org.junit.jupiter.api.Test;

class NmapHostDiscoveryTest {

    @Test
    void parsesOnlyUpHostsFromTheRequestedPrivateSubnet() throws Exception {
        Inet4Address localAddress = (Inet4Address) InetAddress.getByName("192.168.1.9");
        var subnet = LocalNetworkDiscoveryService.createSubnet(localAddress, (short) 24);
        String xml = """
            <?xml version="1.0"?>
            <!DOCTYPE nmaprun>
            <nmaprun>
              <host>
                <status state="up" reason="arp-response"/>
                <address addr="192.168.1.1" addrtype="ipv4"/>
                <address addr="50-42-89-17-BD-5E" addrtype="mac"/>
              </host>
              <host>
                <status state="down" reason="no-response"/>
                <address addr="192.168.1.25" addrtype="ipv4"/>
              </host>
              <host>
                <status state="up" reason="arp-response"/>
                <address addr="192.168.2.2" addrtype="ipv4"/>
              </host>
            </nmaprun>
            """;

        List<NeighborEntry> devices = NmapHostDiscovery.parseXml(xml, subnet);

        assertEquals(1, devices.size());
        assertEquals("192.168.1.1", devices.get(0).address());
        assertEquals("50:42:89:17:bd:5e", devices.get(0).macAddress());
        assertEquals("Nmap ARP", devices.get(0).detectionMethod());
    }

    @Test
    void ignoresInvalidIpv4AddressesFromNmapOutput() throws Exception {
        Inet4Address localAddress = (Inet4Address) InetAddress.getByName("192.168.1.9");
        var subnet = LocalNetworkDiscoveryService.createSubnet(localAddress, (short) 24);
        String xml = """
            <nmaprun>
              <host>
                <status state="up"/>
                <address addr="999.168.1.2" addrtype="ipv4"/>
              </host>
            </nmaprun>
            """;

        assertEquals(List.of(), NmapHostDiscovery.parseXml(xml, subnet));
    }
}
