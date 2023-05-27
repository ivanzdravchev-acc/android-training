package com.example.androidtrainingproject.models

data class ProductResponse(
    val id: Number,
    val title: String,
    val description: String,
    val short_description: String,
    val stock: Number,
    val price: Double,
    val rating: Number,
    val image: String,
    val category: String
)