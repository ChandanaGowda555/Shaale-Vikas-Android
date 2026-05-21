package com.chandana.shaalevikas.data

data class MicroNeed(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val category: String = "Repair",
    val costEstimate: Double = 0.0,
    val priority: String = "High",
    var pledgedAmount: Double = 0.0,
    val beforeImageUrl: String = "https://images.unsplash.com/photo-1590069261209-f8e9b8642343?w=500"
)

data class ImpactStory(
    val id: String = "",
    val title: String = "",
    val beforeUrl: String = "",
    val afterUrl: String = "",
    val aiNarrative: String = ""
)

data class DonorRecord(
    val name: String = "",
    val amount: Int = 0,
    val supportNeed: String = "",
    val date: String = ""
)