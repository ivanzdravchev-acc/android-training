package com.example.androidtrainingproject

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.androidtrainingproject.models.OrderModel
import com.example.androidtrainingproject.models.ProductResponse
import java.time.LocalDateTime

class GlobalStorage {
    companion object {
        var cartProducts = mutableStateListOf<ProductResponse>()
        var orders = mutableStateListOf<OrderModel>()

        val userEmail = mutableStateOf("")

        fun addProduct(product: ProductResponse) {
            cartProducts.add(product)
        }

        fun removeProduct(index: Int) {
            cartProducts.removeAt(index)
        }

        fun placeOrder() {
            orders.add(
                OrderModel(products = cartProducts.toList(), purchaseDate = LocalDateTime.now())
            )
            cartProducts.clear()
        }

        fun clearCart() {
            cartProducts.clear()
        }
    }
}