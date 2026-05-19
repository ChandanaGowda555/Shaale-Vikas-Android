package com.chandana.shaalevikas.data

import androidx.compose.runtime.mutableStateListOf

data class MicroNeed(
    val id: String,
    val title: String,
    val description: String,
    val category: String,
    val costEstimate: Double,
    val priority: String,
    var pledgedAmount: Double = 0.0,
    val beforeImageUrl: String = ""
)

// Model for completed works matching your report's Impact section
data class ImpactStory(
    val id: String,
    val title: String,
    val beforeUrl: String,
    val afterUrl: String,
    val aiNarrative: String
)

// Model for the Donor Hall of Fame tracking community backers
data class DonorRecord(
    val name: String,
    val amount: Int,
    val supportNeed: String,
    val date: String
)

object MockDatabase {
    val activeNeeds = mutableStateListOf(
        MicroNeed(
            id = "1",
            title = "Repairing Leaking Roof - Class 4",
            description = "The roof leaks heavily during monsoon rains, disrupting classes.",
            category = "Repair",
            costEstimate = 15000.0,
            priority = "High",
            pledgedAmount = 4500.0
        ),
        MicroNeed(
            id = "2",
            title = "Desk Replacement for Primary Section",
            description = "Procuring 15 wooden desks to replace broken furniture.",
            category = "Resources",
            costEstimate = 8000.0,
            priority = "Medium",
            pledgedAmount = 8000.0
        )
    )

    val completedStories = listOf(
        ImpactStory(
            id = "1",
            title = "Sanitation Block Overhaul",
            // Realistic rural construction / broken infrastructure image
            beforeUrl = "https://images.unsplash.com/photo-1590069261209-f8e9b8642343?w=500",
            // Realistic freshly painted / finished structure image
            afterUrl = "https://images.unsplash.com/photo-1541888946425-d81bb19240f5?w=500",
            aiNarrative = "Through immediate alumni mobilization, the crumbling plumbing infrastructure was entirely replaced with modern, hygienic sanitation assets, restoring student dignity."
        )
    )

    val donorsList = listOf(
        DonorRecord("Ramesh K.", 15000, "Sanitation Block Overhaul", "12 May 2026"),
        DonorRecord("Anitha Rao", 8000, "Desk Replacement", "15 May 2026"),
        DonorRecord("Chandana P.", 500, "Leaking Roof Repair", "Yesterday")
    )
}