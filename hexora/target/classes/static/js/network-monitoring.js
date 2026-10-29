const hostCount = document.querySelector("#host-count");
const onlineCount = document.querySelector("#online-count");
const offlineCount = document.querySelector("#offline-count");
const openPortCount = document.querySelector("#open-port-count");
const hostCards = document.querySelector("#host-cards");
const hostSelect = document.querySelector("#host-select");
const deviceList = document.querySelector("#device-list");
const deviceMessage = document.querySelector("#device-message");
const portsBody = document.querySelector("#ports-body");
const eventList = document.querySelector("#event-list");
const message = document.querySelector("#network-message");
const scanButton = document.querySelector("#scan-button");
const connectionState = document.querySelector("#connection-state");
const chart = document.querySelector("#activity-chart");
const detectedDevicesBody = document.querySelector("#detected-devices-body");
const detectedDevicesMessage = document.querySelector("#detected-devices-message");
const discoveryLiveState = document.querySelector("#discovery-live-state");
const discoverNetworkButton = document.querySelector("#discover-network-button");
const clearHistoryButton = document.querySelector("#clear-history-button");
const clearSelectedHistoryButton = document.querySelector("#clear-selected-history-button");
const selectAllHistoryCheckbox = document.querySelector("#select-all-history");
let currentStatus = null;
let refreshInProgress = false;
let discoveryRefreshInProgress = false;

function escapeHtml(value) {
    return String(value ?? "").replace(/[&<>"']/g, character => ({
        "&": "&amp;",
        "<": "&lt;",
        ">": "&gt;",
        '"': "&quot;",
        "'": "&#39;"
    })[character]);
}

function formatDate(value) {
    if (!value) return "Sin registros";
    const dateValue = Array.isArray(value)
        ? new Date(value[0], value[1] - 1, value[2], value[3] || 0, value[4] || 0, value[5] || 0)
        : new Date(typeof value === "string" && !/[zZ]|[+-]\d\d:\d\d$/.test(value) ? `${value}Z` : value);
    return new Intl.DateTimeFormat(undefined, {dateStyle: "medium", timeStyle: "short"}).format(dateValue);
}

function formatLatency(value) {
    return value === null || value === undefined ? "—" : `${value} ms`;
}

function renderHosts(hosts) {
    const currentSelection = hostSelect.value;
    if (!hosts.length) {
        hostCards.innerHTML = '<p class="empty-state">No hay dispositivos autorizados configurados.</p>';
        hostSelect.innerHTML = '<option value="">No hay dispositivos configurados</option>';
        scanButton.disabled = true;
        renderDeviceManagement(hosts);
        return;
    }

    hostSelect.innerHTML = hosts.map(host =>
        `<option value="${host.id}">${escapeHtml(host.name)} (${escapeHtml(host.address)})</option>`
    ).join("");
    if (hosts.some(host => String(host.id) === currentSelection)) {
        hostSelect.value = currentSelection;
    }
    scanButton.disabled = false;
    hostCards.innerHTML = hosts.map(host => {
        const status = String(host.status || "UNKNOWN").toLowerCase();
        const label = status === "online" ? "En línea" : status === "offline" ? "Fuera de línea" : "Desconocido";
        return `<article class="host-card">
            <div class="host-card-top">
                <h3>${escapeHtml(host.name)}</h3>
                <span class="status-pill status-${status}">${label}</span>
            </div>
            <p class="host-address">${escapeHtml(host.address)}</p>
            <div class="host-fact"><span>Última detección</span><strong>${escapeHtml(formatDate(host.lastResponse))}</strong></div>
            <div class="host-fact"><span>Latencia</span><strong>${escapeHtml(formatLatency(host.latencyMs))}</strong></div>
        </article>`;
    }).join("");
    renderDeviceManagement(hosts);
}

function renderDetectedInventory(devices) {
    if (!devices.length) {
        detectedDevicesBody.innerHTML = '<tr><td colspan="6" class="empty-state">No hay dispositivos actuales visibles. Pulsa “Detectar dispositivos en la red” para actualizar.</td></tr>';
        return;
    }

    detectedDevicesBody.innerHTML = devices.map(device => {
        const status = String(device.status || "UNKNOWN").toLowerCase();
        const registered = device.registered === true;
        const stateLabel = status === "online"
            ? registered ? "🟢 Online" : "🟢 Online · No registrado"
            : status === "offline"
                ? registered ? "🔴 Offline" : "🔴 Offline · No registrado"
                : "🟡 No registrado";
        const pillClass = status === "online" ? "online" : status === "offline" ? "offline" : "unregistered";
        return `<tr>
            <td>${escapeHtml(device.address)}</td>
            <td>${escapeHtml(device.macAddress || "No disponible")}</td>
            <td>${escapeHtml(device.name || device.address)}</td>
            <td><span class="status-pill status-${pillClass}">${stateLabel}</span></td>
            <td>${escapeHtml(device.detectionMethod || "Sin respuesta actual")}</td>
            <td>${escapeHtml(formatDate(device.lastDetectedAt))}</td>
        </tr>`;
    }).join("");
}

function showDiscoveryState(text) {
    detectedDevicesBody.innerHTML = `<tr><td colspan="6" class="empty-state">${escapeHtml(text)}</td></tr>`;
}

function renderDeviceManagement(hosts) {
    if (!hosts.length) {
        deviceList.innerHTML = '<p class="empty-state">No hay dispositivos autorizados configurados.</p>';
        return;
    }
    deviceList.innerHTML = hosts.map(host => {
        const monitoredPorts = (host.monitoredPorts || host.ports || []).map(item => {
            const portNumber = typeof item === "number" ? item : item.port ?? item.portNumber;
            if (!Number.isInteger(Number(portNumber))) {
                throw new Error(`La respuesta del dispositivo ${host.address} contiene un puerto sin número.`);
            }
            return {
                port: Number(portNumber),
                service: typeof item === "object" && item !== null ? item.service || item.protocol || "TCP" : "TCP",
                enabled: typeof item === "object" && item !== null ? item.enabled !== false : true
            };
        });
        return `<article class="device-item">
        <div class="device-item-heading">
            <div><strong>${escapeHtml(host.name)}</strong><span>${escapeHtml(host.address)}</span></div>
            <span class="device-port-count">${monitoredPorts.length} puertos</span>
        </div>
        <div class="monitored-port-list">
            ${monitoredPorts.map(port => `<div class="monitored-port-item">
                <label><input type="checkbox" data-host-id="${host.id}" data-port-enabled="${port.port}" ${port.enabled ? "checked" : ""}>
                    <span>TCP ${port.port} · ${escapeHtml(port.service)}</span></label>
                <span class="port-monitor-state">${port.enabled ? "Monitorizado" : "Pausado"}</span>
                <button class="remove-port-button" type="button" data-host-id="${host.id}" data-remove-port="${port.port}">Quitar</button>
            </div>`).join("")}
        </div>
        <form class="device-port-add-form" data-host-id="${host.id}">
            <label>Agregar puerto TCP
                <input name="port" type="number" min="1" max="65535" placeholder="Ej. 8080" required>
            </label>
            <button type="submit">Agregar puerto</button>
        </form>
        ${host.managedByUi ? `<div class="device-actions">
            <button class="remove-device-button" type="button" data-clear-host-history="${host.id}">Borrar registros</button>
            <button class="remove-device-button" type="button" data-remove-host="${host.id}">Quitar dispositivo</button>
        </div>` : ""}
    </article>`;
    }).join("");
}

function renderPorts(scans) {
    const latestByHostAndPort = new Map();
    scans.forEach(scan => scan.ports.forEach(port => {
        const key = `${scan.hostId}:${port.port}`;
        if (!latestByHostAndPort.has(key)) {
            latestByHostAndPort.set(key, {hostName: scan.hostName, ...port});
        }
    }));
    const rows = [...latestByHostAndPort.values()]
        .sort((left, right) => left.port - right.port || left.hostName.localeCompare(right.hostName));
    if (!rows.length) {
        portsBody.innerHTML = '<tr><td colspan="5" class="empty-state">No port observations yet.</td></tr>';
        return;
    }
    portsBody.innerHTML = rows.map(port => {
        const status = String(port.status).toLowerCase();
        return `<tr>
            <td>${escapeHtml(port.hostName)}</td>
            <td>${port.port}</td>
            <td>${escapeHtml(port.service)}</td>
            <td><span class="status-pill status-${status}">${status === "open" ? "Abierto" : "Cerrado"}</span></td>
            <td>${escapeHtml(formatDate(port.detectedAt))}</td>
        </tr>`;
    }).join("");
}

function renderHistory(scans) {
    const body = document.querySelector("#history-body");
    if (!scans.length) {
        body.innerHTML = '<tr><td colspan="6" class="empty-state">Todavía no hay revisiones registradas.</td></tr>';
        clearSelectedHistoryButton.disabled = true;
        selectAllHistoryCheckbox.checked = false;
        selectAllHistoryCheckbox.disabled = true;
        return;
    }
    body.innerHTML = scans.map(scan => `<tr>
        <td><input type="checkbox" data-delete-scan="${escapeHtml(scan.id)}"
            aria-label="Seleccionar revisión de ${escapeHtml(scan.hostName)} del ${escapeHtml(formatDate(scan.completedAt))}"></td>
        <td>${escapeHtml(scan.hostName)}</td>
        <td>${escapeHtml(scan.address)}</td>
        <td><span class="status-pill status-${String(scan.hostStatus).toLowerCase()}">${scan.hostStatus === "ONLINE" ? "En línea" : "Fuera de línea"}</span></td>
        <td>${scan.portsScanned}</td>
        <td>${escapeHtml(formatDate(scan.completedAt))}</td>
    </tr>`).join("");
    clearSelectedHistoryButton.disabled = true;
    selectAllHistoryCheckbox.checked = false;
    selectAllHistoryCheckbox.disabled = false;
}

function updateHistorySelection() {
    const checkboxes = [...document.querySelectorAll("[data-delete-scan]")];
    const selected = checkboxes.filter(checkbox => checkbox.checked);
    clearSelectedHistoryButton.disabled = selected.length === 0;
    selectAllHistoryCheckbox.checked = checkboxes.length > 0 && selected.length === checkboxes.length;
    selectAllHistoryCheckbox.disabled = checkboxes.length === 0;
}

function renderEvents(events) {
    if (!events.length) {
        eventList.innerHTML = '<p class="empty-state">No state changes recorded.</p>';
        return;
    }
    eventList.innerHTML = events.map(event => `<article class="event-item">
        <span class="event-dot"></span>
        <div><strong>${escapeHtml(event.hostName)} - ${escapeHtml(event.type.replaceAll("_", " "))}</strong>
        <p>${escapeHtml(event.description)}</p></div>
        <time>${escapeHtml(formatDate(event.createdAt))}</time>
    </article>`).join("");
}

function renderChart(scans, totalHosts) {
    const context = chart.getContext("2d");
    const width = chart.clientWidth;
    const height = chart.clientHeight;
    const pixelRatio = window.devicePixelRatio || 1;
    chart.width = width * pixelRatio;
    chart.height = height * pixelRatio;
    context.scale(pixelRatio, pixelRatio);
    context.clearRect(0, 0, width, height);

    const groups = new Map();
    scans.forEach(scan => {
        const timestamp = Array.isArray(scan.startedAt)
            ? new Date(scan.startedAt[0], scan.startedAt[1] - 1, scan.startedAt[2], scan.startedAt[3] || 0, scan.startedAt[4] || 0).getTime()
            : new Date(typeof scan.startedAt === "string" && !/[zZ]|[+-]\d\d:\d\d$/.test(scan.startedAt)
                ? `${scan.startedAt}Z`
                : scan.startedAt).getTime();
        const bucket = Math.floor(timestamp / 300_000);
        const group = groups.get(bucket) || new Map();
        group.set(scan.hostId, scan.hostStatus);
        groups.set(bucket, group);
    });
    const points = [...groups.entries()].sort((left, right) => left[0] - right[0]).slice(-12)
        .map(([, statuses]) => [...statuses.values()].filter(status => status === "ONLINE").length);
    if (!points.length || !width || !height) {
        context.fillStyle = "#6d8792";
        context.font = "13px sans-serif";
        context.fillText("La actividad aparecerá después de la primera revisión.", 8, 24);
        return;
    }

    const padding = {top: 15, right: 12, bottom: 25, left: 28};
    const plotWidth = width - padding.left - padding.right;
    const plotHeight = height - padding.top - padding.bottom;
    const maximum = Math.max(totalHosts, 1);
    context.strokeStyle = "#e4eef1";
    context.lineWidth = 1;
    context.beginPath();
    context.moveTo(padding.left, padding.top + plotHeight);
    context.lineTo(width - padding.right, padding.top + plotHeight);
    context.stroke();

    const coordinates = points.map((value, index) => ({
        x: padding.left + (points.length === 1 ? plotWidth / 2 : index * plotWidth / (points.length - 1)),
        y: padding.top + plotHeight - (value / maximum) * plotHeight
    }));
    context.strokeStyle = "#00a8c5";
    context.lineWidth = 2.5;
    context.beginPath();
    coordinates.forEach((point, index) => {
        if (index === 0) context.moveTo(point.x, point.y);
        else context.lineTo(point.x, point.y);
    });
    context.stroke();
    coordinates.forEach(point => {
        context.beginPath();
        context.arc(point.x, point.y, 4, 0, Math.PI * 2);
        context.fillStyle = "#00c2e0";
        context.fill();
    });
    context.fillStyle = "#6d8792";
    context.font = "11px sans-serif";
    context.fillText(`Online / ${maximum}`, 4, padding.top + 8);
    context.fillText("Recent checks", Math.max(4, width - 82), height - 4);
}

async function refreshDashboard() {
    if (refreshInProgress) return false;
    refreshInProgress = true;
    try {
        const [statusResponse, historyResponse] = await Promise.all([
            fetch("/api/network/status"),
            fetch("/api/network/history?limit=40")
        ]);
        if (!statusResponse.ok || !historyResponse.ok) {
            throw new Error("No se pudieron cargar los datos de monitorización de red.");
        }
        const [status, history] = await Promise.all([statusResponse.json(), historyResponse.json()]);
        currentStatus = status;
        hostCount.textContent = status.hostsMonitored;
        onlineCount.textContent = status.onlineHosts;
        offlineCount.textContent = status.offlineHosts;
        openPortCount.textContent = status.openPorts;
        renderHosts(status.hosts);
        renderPorts(status.recentScans);
        renderEvents(status.recentEvents);
        renderHistory(history);
        renderChart(history, status.hostsMonitored);
        message.textContent = "";
        message.classList.remove("error");
        return true;
    } catch (error) {
        message.textContent = error.message;
        message.classList.add("error");
        return false;
    } finally {
        refreshInProgress = false;
    }
}

document.querySelector("#scan-form").addEventListener("submit", async event => {
    event.preventDefault();
    const hostId = Number(hostSelect.value);
    if (!hostId) return;

    const startPort = document.querySelector("#start-port").value;
    const endPort = document.querySelector("#end-port").value;
    if (Boolean(startPort) !== Boolean(endPort)) {
        message.textContent = "Enter both range endpoints, or leave both empty for the common service ports.";
        message.classList.add("error");
        return;
    }

    scanButton.disabled = true;
    message.textContent = "Revisando el dispositivo autorizado...";
    message.classList.remove("error");
    try {
        const body = {hostId};
        if (startPort && endPort) {
            body.startPort = Number(startPort);
            body.endPort = Number(endPort);
        }
        const response = await fetch("/api/network/scan", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(body)
        });
        const result = await response.json();
        if (!response.ok) throw new Error(result.error || "The scan could not be completed.");
        message.textContent = `Revisión completada para ${result.hostName}.`;
        await refreshDashboard();
    } catch (error) {
        message.textContent = error.message;
        message.classList.add("error");
        await refreshDashboard();
    } finally {
        scanButton.disabled = !currentStatus?.hostsMonitored;
    }
});

document.querySelector("#history-body").addEventListener("change", event => {
    if (event.target.matches("[data-delete-scan]")) updateHistorySelection();
});

selectAllHistoryCheckbox.addEventListener("change", () => {
    document.querySelectorAll("[data-delete-scan]").forEach(checkbox => {
        checkbox.checked = selectAllHistoryCheckbox.checked;
    });
    updateHistorySelection();
});

clearSelectedHistoryButton.addEventListener("click", async () => {
    const scanIds = [...document.querySelectorAll("[data-delete-scan]:checked")]
        .map(checkbox => Number(checkbox.dataset.deleteScan));
    if (!scanIds.length) return;
    if (!window.confirm(`¿Quieres borrar las ${scanIds.length} revisiones seleccionadas y sus eventos asociados?`)) {
        return;
    }
    clearSelectedHistoryButton.disabled = true;
    message.textContent = "Borrando revisiones seleccionadas...";
    message.classList.remove("error");
    try {
        const response = await fetch("/api/network/history/scans", {
            method: "DELETE",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({scanIds})
        });
        await readApiResponse(response, "No se pudieron borrar las revisiones seleccionadas.");
        await refreshDashboard();
        message.textContent = `Se borraron ${scanIds.length} revisiones y sus eventos asociados.`;
    } catch (error) {
        message.textContent = error.message;
        message.classList.add("error");
    } finally {
        updateHistorySelection();
    }
});

function parsePorts(value) {
    const entries = value.split(",").map(port => port.trim());
    if (entries.some(port => !/^\d+$/.test(port))) {
        throw new Error("Escribe los puertos como números separados por coma.");
    }
    const ports = entries.map(Number);
    if (ports.length < 1 || ports.length > 32 || new Set(ports).size !== ports.length
        || ports.some(port => port < 1 || port > 65535)) {
        throw new Error("Agrega entre 1 y 32 puertos únicos, del 1 al 65535.");
    }
    return ports;
}

async function readApiResponse(response, fallbackMessage) {
    if (response.status === 204) return {};
    let result = {};
    try {
        result = await response.json();
    } catch (error) {
        if (response.ok) throw error;
    }
    if (!response.ok) throw new Error(result.error || fallbackMessage);
    return result;
}

document.querySelector("#device-form").addEventListener("submit", async event => {
    event.preventDefault();
    const form = event.currentTarget;
    const button = form.querySelector('button[type="submit"]');
    button.disabled = true;
    deviceMessage.textContent = "";
    deviceMessage.classList.remove("error");
    try {
        const response = await fetch("/api/network/devices", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({
                name: document.querySelector("#device-name").value,
                address: document.querySelector("#device-address").value,
                ports: parsePorts(document.querySelector("#device-ports").value)
            })
        });
        await readApiResponse(response, "No se pudo agregar el dispositivo.");
        form.reset();
        deviceMessage.textContent = "Dispositivo agregado. Se marcará Offline hasta que responda a una comprobación actual.";
        await refreshDashboard();
    } catch (error) {
        deviceMessage.textContent = error.message;
        deviceMessage.classList.add("error");
    } finally {
        button.disabled = false;
    }
});

async function scanLocalNetwork() {
    if (discoveryRefreshInProgress) return;
    discoveryRefreshInProgress = true;
    discoverNetworkButton.disabled = true;
    discoverNetworkButton.textContent = "Detectando dispositivos...";
    detectedDevicesMessage.textContent = "Sondeando la subred local para localizar dispositivos activos...";
    detectedDevicesMessage.classList.remove("error");
    showDiscoveryState("Buscando dispositivos en la subred actual...");
    try {
        const response = await fetch("/api/network/discover", {method: "POST", cache: "no-store"});
        const result = await readApiResponse(response, "No se pudo escanear la tabla ARP local.");
        renderDetectedInventory(result.devices);
        await refreshDashboard();
        const currentDevices = result.devices.length;
        detectedDevicesMessage.textContent = currentDevices
            ? `Mínimo ${currentDevices} dispositivo(s) detectado(s) en ${result.subnet}, incluida esta computadora. Puede haber más equipos que no respondan al sondeo.`
            : `Detección actualizada en ${result.subnet}: no se encontraron dispositivos que respondan. Comprueba que los celulares estén en la misma red Wi-Fi y activos; algunos routers aíslan los dispositivos.`;
    } catch (error) {
        showDiscoveryState("No se pudo actualizar la detección. La tabla anterior se borró; intenta de nuevo.");
        detectedDevicesMessage.textContent = error.message;
        detectedDevicesMessage.classList.add("error");
    } finally {
        discoveryRefreshInProgress = false;
        discoverNetworkButton.textContent = "Detectar dispositivos en la red";
        discoverNetworkButton.disabled = false;
    }
}

discoverNetworkButton.addEventListener("click", scanLocalNetwork);

deviceList.addEventListener("submit", async event => {
    if (!event.target.matches(".device-port-add-form")) return;
    event.preventDefault();
    const form = event.target;
    const button = form.querySelector('button[type="submit"]');
    button.disabled = true;
    deviceMessage.textContent = "";
    deviceMessage.classList.remove("error");
    try {
        const port = Number(form.elements.port.value);
        if (!Number.isInteger(port) || port < 1 || port > 65535) {
            throw new Error("El puerto debe estar entre 1 y 65535.");
        }
        const response = await fetch(`/api/network/devices/${form.dataset.hostId}/ports`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({port})
        });
        await readApiResponse(response, "No se pudo agregar el puerto.");
        deviceMessage.textContent = "Puerto agregado y habilitado para las próximas revisiones.";
        await refreshDashboard();
    } catch (error) {
        deviceMessage.textContent = error.message;
        deviceMessage.classList.add("error");
    } finally {
        button.disabled = false;
    }
});

deviceList.addEventListener("change", async event => {
    const checkbox = event.target.closest("[data-port-enabled]");
    if (!checkbox) return;
    const enabled = checkbox.checked;
    checkbox.disabled = true;
    deviceMessage.textContent = "";
    deviceMessage.classList.remove("error");
    try {
        const response = await fetch(
            `/api/network/devices/${checkbox.dataset.hostId}/ports/${checkbox.dataset.portEnabled}`,
            {
                method: "PUT",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify({enabled})
            }
        );
        await readApiResponse(response, "No se pudo actualizar el estado del puerto.");
        deviceMessage.textContent = enabled ? "Monitorización del puerto activada." : "Monitorización del puerto pausada.";
        await refreshDashboard();
    } catch (error) {
        deviceMessage.textContent = error.message;
        deviceMessage.classList.add("error");
        await refreshDashboard();
    }
});

deviceList.addEventListener("click", async event => {
    const button = event.target.closest("[data-clear-host-history], [data-remove-host], [data-remove-port]");
    if (!button) return;
    const removingPort = button.hasAttribute("data-remove-port");
    const clearingHostHistory = button.hasAttribute("data-clear-host-history");
    const hostId = clearingHostHistory ? button.dataset.clearHostHistory : button.dataset.hostId;
    const host = currentStatus?.hosts.find(item =>
        String(item.id) === String(hostId));
    const confirmed = window.confirm(clearingHostHistory
        ? `¿Quieres borrar el historial y los eventos de ${host?.name || "este dispositivo"}? El dispositivo seguirá registrado.`
        : removingPort
            ? `¿Quieres dejar de monitorizar el puerto ${button.dataset.removePort}?`
            : "¿Quieres quitar este dispositivo del monitoreo?");
    if (!confirmed) return;
    button.disabled = true;
    deviceMessage.textContent = "";
    deviceMessage.classList.remove("error");
    try {
        const url = clearingHostHistory
            ? `/api/network/devices/${hostId}/history`
            : removingPort
                ? `/api/network/devices/${button.dataset.hostId}/ports/${button.dataset.removePort}`
                : `/api/network/devices/${button.dataset.removeHost}`;
        const response = await fetch(url, {method: "DELETE"});
        await readApiResponse(response, "No se pudo quitar el dispositivo.");
        deviceMessage.textContent = clearingHostHistory
            ? "Historial del dispositivo borrado."
            : removingPort ? "Puerto quitado del monitoreo." : "Dispositivo quitado del monitoreo.";
        await refreshDashboard();
    } catch (error) {
        deviceMessage.textContent = error.message;
        deviceMessage.classList.add("error");
    } finally {
        button.disabled = false;
    }
});

clearHistoryButton.addEventListener("click", async () => {
    const confirmed = window.confirm(
        "¿Quieres borrar todo el historial de escaneos y eventos? Los dispositivos registrados se conservarán."
    );
    if (!confirmed) return;
    clearHistoryButton.disabled = true;
    message.textContent = "Borrando historial...";
    message.classList.remove("error");
    try {
        const response = await fetch("/api/network/history", {method: "DELETE"});
        await readApiResponse(response, "No se pudo borrar el historial.");
        await refreshDashboard();
        message.textContent = "Se borró el historial de escaneos y eventos.";
    } catch (error) {
        message.textContent = error.message;
        message.classList.add("error");
    } finally {
        clearHistoryButton.disabled = false;
    }
});

function connectWebSocket() {
    const protocol = window.location.protocol === "https:" ? "wss:" : "ws:";
    const connection = new WebSocket(`${protocol}//${window.location.host}/ws/network`);
    connection.addEventListener("open", () => {
        connectionState.textContent = "En vivo";
        connectionState.classList.add("connected");
        if (discoveryLiveState) {
            discoveryLiveState.textContent = "En vivo · cada 30 s";
            discoveryLiveState.classList.add("connected");
        }
    });
    connection.addEventListener("message", event => {
        try {
            const update = JSON.parse(event.data);
            if (update.type === "inventory" && Array.isArray(update.devices)) {
                renderDetectedInventory(update.devices);
                detectedDevicesMessage.textContent =
                    `Actualizado automáticamente · mínimo ${update.devices.length} dispositivo(s) en ${update.subnet}, incluida esta computadora.`;
                detectedDevicesMessage.classList.remove("error");
                return;
            }
            refreshDashboard();
        } catch (error) {
            console.error("No se pudo procesar la actualización de red en vivo.", error);
        }
    });
    connection.addEventListener("close", () => {
        connectionState.textContent = "Reconectando";
        connectionState.classList.remove("connected");
        if (discoveryLiveState) {
            discoveryLiveState.textContent = "Reconectando";
            discoveryLiveState.classList.remove("connected");
        }
        window.setTimeout(connectWebSocket, 3000);
    });
    connection.addEventListener("error", () => connection.close());
}

window.addEventListener("resize", () => {
    if (currentStatus) renderChart(currentStatus.recentScans, currentStatus.hostsMonitored);
});

refreshDashboard();
showDiscoveryState("Esperando la primera actualización automática de dispositivos conectados...");
detectedDevicesMessage.textContent = "La lista se actualizará automáticamente cuando el servidor termine la primera comprobación.";
connectWebSocket();
window.setInterval(refreshDashboard, 30_000);
