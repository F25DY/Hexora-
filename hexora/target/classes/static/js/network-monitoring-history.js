const historyList = document.querySelector("#discovery-history-list");
const historyMessage = document.querySelector("#discovery-history-message");

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
    return new Intl.DateTimeFormat("es", {dateStyle: "medium", timeStyle: "short"}).format(new Date(value));
}

function renderHistory(snapshots) {
    if (!snapshots.length) {
        historyList.innerHTML = '<section class="network-card"><p class="empty-state">Todavía no hay detecciones guardadas. Pulsa “Detectar dispositivos en la red” para crear la primera.</p></section>';
        return;
    }

    historyList.innerHTML = snapshots.map(snapshot => {
        const devices = snapshot.devices || [];
        const rows = devices.length
            ? devices.map(device => {
                const online = device.status === "ONLINE";
                const state = online ? "Online" : "Offline";
                const registration = device.registered ? "" : " · No registrado";
                const isGateway = device.gateway === true || (device.detectionMethod && device.detectionMethod.includes("Puerta de enlace"));
                const gatewayBadge = isGateway ? ' <span class="gateway-badge" title="Puerta de enlace predeterminada (Gateway)">🌐 Gateway</span>' : "";
                return `<tr>
                    <td>${escapeHtml(device.address)}</td>
                    <td>${escapeHtml(device.macAddress || "No disponible")}</td>
                    <td>${escapeHtml(device.name || device.address)}${gatewayBadge}</td>
                    <td><span class="status-pill status-${online ? "online" : "offline"}">${state}${registration}</span></td>
                    <td>${escapeHtml(device.detectionMethod || "Sin respuesta actual")}</td>
                    <td>${escapeHtml(formatDate(device.lastDetectedAt))}</td>
                </tr>`;
            }).join("")
            : '<tr><td colspan="6" class="empty-state">Esta detección no encontró dispositivos.</td></tr>';

        return `<section class="network-card ports-panel discovery-history-entry">
            <div class="section-heading">
                <div>
                    <p class="network-eyebrow">DETECCIÓN #${escapeHtml(snapshot.id)}</p>
                    <h2>${escapeHtml(formatDate(snapshot.observedAt))}</h2>
                    <p class="management-help">Subred: ${escapeHtml(snapshot.subnet)} · Origen: ${escapeHtml(snapshot.source)} · Dispositivos: ${devices.length}</p>
                </div>
            </div>
            <div class="table-scroll">
                <table>
                    <thead>
                    <tr><th>IP</th><th>MAC</th><th>Hostname</th><th>Estado</th><th>Método de detección</th><th>Última detección</th></tr>
                    </thead>
                    <tbody>${rows}</tbody>
                </table>
            </div>
        </section>`;
    }).join("");
}

async function loadHistory() {
    try {
        const response = await fetch("/api/network/discoveries/history");
        if (!response.ok) throw new Error("No se pudo cargar el historial de detecciones.");
        renderHistory(await response.json());
        historyMessage.hidden = true;
    } catch (error) {
        historyMessage.textContent = error.message;
        historyMessage.classList.add("error");
    }
}

loadHistory();
