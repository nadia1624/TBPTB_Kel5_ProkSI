package com.example.proksi_tbptb.frontend.DetailProker.component

import androidx.compose.ui.graphics.Color

// Kelas Status untuk menangani status dan warnanya
data class Status(
    val name: String,  // Nama status (e.g., "Done", "In Progress", "Not Started")
    val color: Color   // Warna yang terkait dengan status
)

// Fungsi untuk mendapatkan status dengan nama tertentu
fun getStatusByName(name: String): Status {
    return when (name) {
        "Done" -> Status(
            name = "Done",
            color = Color(0xFF388E3C) // Dark Green for Done
        )
        "In Progress" -> Status(
            name = "In Progress",
            color = Color(0xFFFBC02D) // Dark Yellow for In Progress
        )
        "Not Started" -> Status(
            name = "Not Started",
            color = Color(0xFFD32F2F) // Dark Red for Not Started
        )
        else -> Status(
            name = "Unknown",
            color = Color(0xFF9E9E9E) // Default gray for unknown status
        )
    }
}
