package com.example.dhakaparkdriver.data

data class KpiCard(
    val title: String,
    val value: String,
    val trend: String? = null // e.g., "+5%"
)

data class RecentActivity(
    val description: String,
    val timestamp: String
)