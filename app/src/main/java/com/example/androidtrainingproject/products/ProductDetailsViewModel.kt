package com.example.androidtrainingproject.products

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtrainingproject.models.ProductResponse
import com.example.androidtrainingproject.repository.DefaultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val repository: DefaultRepository
): ViewModel() {
    var productData by mutableStateOf(null as ProductResponse?)

    fun getProductById(id: Number) {
        viewModelScope.launch {
            try {
                val response: ProductResponse = repository.getProductById(id)
                productData = response
            } catch (e: Exception) {
                Log.e("error fetching product info", e.toString())
            }
        }
    }
}