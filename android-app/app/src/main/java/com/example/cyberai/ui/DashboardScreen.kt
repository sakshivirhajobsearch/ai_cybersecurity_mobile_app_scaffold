package com.example.cyberai.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlin.math.min
import kotlin.math.roundToInt

// ---------- Data models ----------
data class ThreatSummary(
    val total: Int,
    val critical: Int,
    val highConfidence: Int,
    val internal: Int,
    val external: Int
)

data class SeverityCount(val label: String, val count: Int)
data class IEVSplit(val internal: Int, val external: Int)

data class AlertRow(
    val channel: String,
    val title: String,
    val severity: String,
    val time: String
)

data class HealthRow(
    val component: String,
    val status: String,
    val time: String
)

// ---------- Fake repo (replace with real data later) ----------
object DashboardRepo {
    fun summary() = ThreatSummary(
        total = 10, critical = 3, highConfidence = 6, internal = 10, external = 0
    )

    fun severity(): List<SeverityCount> = listOf(
        SeverityCount("Critical", 3),
        SeverityCount("High", 3),
        SeverityCount("Medium", 3),
        SeverityCount("Low", 1),
    )

    fun split() = IEVSplit(internal = 10, external = 0)

    fun latestAlerts(): List<AlertRow> = listOf(
        AlertRow("Email", "Phishing detected for user alice", "High", "2025-10-21 20:58:26"),
        AlertRow("SMS", "Critical threat detected on Server3", "High", "2025-10-21 20:58:26"),
        AlertRow("Email", "Malware detected on Server2", "Medium", "2025-10-21 20:58:26"),
        AlertRow("SIEM", "Ransomware detected on Server9", "High", "2025-10-21 20:58:26"),
        AlertRow("DB", "SQL Injection attack on Server6", "High", "2025-10-21 20:58:26"),
        AlertRow("Net", "DDoS attack on Server5", "High", "2025-10-21 20:58:26"),
        AlertRow("Email", "Insider threat on Server4", "Medium", "2025-10-21 20:58:26"),
        AlertRow("Vuln", "Zero-day on Server10", "High", "2025-10-21 20:58:26"),
        AlertRow("FW", "Firewall anomaly detected", "Medium", "2025-10-21 20:58:26"),
        AlertRow("SIEM", "SIEM system warning", "Low", "2025-10-21 20:58:26"),
    )

    fun health(): List<HealthRow> = listOf(
        HealthRow("Firewall", "OK", "2025-10-21 20:58:26"),
        HealthRow("SIEM", "WARNING", "2025-10-21 20:58:26"),
        HealthRow("Endpoint Protection", "OK", "2025-10-21 20:58:26"),
        HealthRow("Email Gateway", "OK", "2025-10-21 20:58:26"),
        HealthRow("Network Switch", "OK", "2025-10-21 20:58:26"),
        HealthRow("Database Server", "OK", "2025-10-21 20:58:26"),
        HealthRow("Web Server", "WARNING", "2025-10-21 20:58:26"),
        HealthRow("VPN Gateway", "OK", "2025-10-21 20:58:26"),
        HealthRow("Intrusion Detection", "ERROR", "2025-10-21 20:58:26"),
        HealthRow("Backup System", "OK", "2025-10-21 20:58:26"),
    )
}

// ---------- Colors ----------
private fun severityColor(sev: String): Color = when (sev.lowercase()) {
    "critical" -> Color(0xFFd32f2f)
    "high"     -> Color(0xFFef5350)
    "medium"   -> Color(0xFFffb74d)
    "low"      -> Color(0xFF81c784)
    else       -> Color(0xFF90caf9)
}

private fun healthColor(status: String): Color = when (status.uppercase()) {
    "ERROR"   -> Color(0xFFd32f2f)
    "WARNING" -> Color(0xFFffb300)
    "OK"      -> Color(0xFF43a047)
    else      -> Color(0xFF90caf9)
}

// ---------- Composables ----------
@Composable
fun DashboardScreen() {
    val summary = remember { DashboardRepo.summary() }
    val severity = remember { DashboardRepo.severity() }
    val split = remember { DashboardRepo.split() }
    val alerts = remember { DashboardRepo.latestAlerts() }
    val health = remember { DashboardRepo.health() }

    Column(
        Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(12.dp)
    ) {
        Text(
            "SOC AI Dashboard",
            color = Color(0xFF9CDCFE),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.height(4.dp))
        Text(
            "Total: ${summary.total} | Critical: ${summary.critical} | High Conf: ${summary.highConfidence} | Internal: ${summary.internal} | External: ${summary.external}",
            color = Color(0xFFB0BEC5),
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(Modifier.height(12.dp))

        // Charts
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))) {
                Column(Modifier.padding(12.dp)) {
                    Text("Threats by Severity", color = Color.White)
                    Spacer(Modifier.height(8.dp))
                    BarChart(
                        data = severity,
                        barColor = Color(0xFF5C6BC0),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                    )
                }
            }
            Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))) {
                Column(Modifier.padding(12.dp)) {
                    Text("Internal vs External", color = Color.White)
                    Spacer(Modifier.height(8.dp))
                    PieChart(
                        internal = split.internal,
                        external = split.external,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        // Latest Alerts
        SectionHeader("Latest Alerts")
        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 280.dp)
            ) {
                items(alerts) { a ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .background(severityColor(a.severity).copy(alpha = 0.22f))
                            .padding(horizontal = 10.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(a.channel, color = Color.White, modifier = Modifier.width(64.dp))
                        Text(a.title, color = Color.White, modifier = Modifier.weight(1f))
                        Text(a.severity, color = Color.White, modifier = Modifier.width(72.dp))
                        Text(a.time, color = Color.White.copy(alpha = 0.85f))
                    }
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        // System Health
        SectionHeader("System Health")
        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 320.dp)
            ) {
                items(health) { h ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .background(healthColor(h.status).copy(alpha = 0.22f))
                            .padding(horizontal = 10.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(h.component, color = Color.White, modifier = Modifier.weight(1f))
                        Text(h.status, color = Color.White, modifier = Modifier.width(90.dp))
                        Text(h.time, color = Color.White.copy(alpha = 0.85f))
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        title,
        color = Color(0xFFFFCC80),
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(bottom = 6.dp)
    )
}

@Composable
private fun BarChart(
    data: List<SeverityCount>,
    barColor: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        if (data.isEmpty()) return@Canvas
        val maxVal = (data.maxOf { it.count }.coerceAtLeast(1)).toFloat()
        val barWidth = size.width / (data.size * 2f)
        val gap = barWidth
        val bottom = size.height * 0.9f
        val topPad = size.height * 0.1f

        // Axes
        drawLine(Color.Gray, start = Offset(0f, bottom), end = Offset(size.width, bottom), strokeWidth = 2f)

        data.forEachIndexed { idx, item ->
            val x = gap + idx * (barWidth + gap)
            val h = ((item.count / maxVal) * (bottom - topPad))
            drawRect(
                color = barColor,
                topLeft = Offset(x, bottom - h),
                size = Size(barWidth, h)
            )
        }
    }
}

@Composable
private fun PieChart(internal: Int, external: Int, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val total = (internal + external).coerceAtLeast(1)
        val internalAngle = 360f * internal / total
        val externalAngle = 360f - internalAngle

        val diameter = min(size.width, size.height) * 0.9f
        val left = (size.width - diameter) / 2f
        val top = (size.height - diameter) / 2f
        val rect = Rect(left, top, left + diameter, top + diameter)

        drawArc(
            color = Color(0xFF5C6BC0), // internal
            startAngle = -90f,
            sweepAngle = internalAngle,
            useCenter = true,
            size = Size(rect.width, rect.height),
            topLeft = Offset(rect.left, rect.top)
        )
        drawArc(
            color = Color(0xFFE57373), // external
            startAngle = -90f + internalAngle,
            sweepAngle = externalAngle,
            useCenter = true,
            size = Size(rect.width, rect.height),
            topLeft = Offset(rect.left, rect.top)
        )

        // Ring border for polish
        drawArc(
            color = Color.Black.copy(alpha = 0.5f),
            startAngle = 0f, sweepAngle = 360f,
            useCenter = false,
            size = Size(rect.width, rect.height),
            topLeft = Offset(rect.left, rect.top),
            style = Stroke(width = 3f)
        )
    }
}
