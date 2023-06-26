package com.example.androidtrainingproject.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtrainingproject.models.ProductResponse
import com.example.androidtrainingproject.repository.DefaultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: DefaultRepository
): ViewModel() {
    var isLoading by mutableStateOf(false)
    var fetchProductsError by mutableStateOf<Boolean?>(null)

    var allProducts by mutableStateOf<List<ProductResponse>>(emptyList())
    var filteredProducts by mutableStateOf<List<ProductResponse>>(emptyList())

    private var _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText

    init {
        getAllProducts("*")
    }

    private fun getAllProducts(populate: String) {
        viewModelScope.launch {
            try {
                isLoading = true
                val allProductsResponse = repository.getProducts(populate)
                allProducts = allProductsResponse
                filteredProducts = allProductsResponse
                fetchProductsError = false
            } catch (e: Exception) {
                fetchProductsError = true
            } finally {
                isLoading = false
            }
        }
    }

    fun updateSearchText(newValue: String) {
        _searchText.value = newValue
        val newFilteredProducts = allProducts.filter { product: ProductResponse ->
            product.title.contains(newValue, ignoreCase = true)
        }
        filteredProducts = newFilteredProducts
    }

    fun clearSearchText() {
        _searchText.value = ""
        filteredProducts = allProducts
    }
}