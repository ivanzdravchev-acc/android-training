package com.example.androidtrainingproject.models

import java.time.LocalDateTime

data class OrderModel (
    val products: List<ProductResponse>,
    val purchaseDate: LocalDateTime
)