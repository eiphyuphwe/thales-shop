package com.example.thalesshop.model

data class Product(
    val id: Long,
    val name: String,
    val type: String,
    val picture: String,
    val price: Double,
    val description: String
)
