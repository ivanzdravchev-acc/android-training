package com.example.androidtrainingproject.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtrainingproject.models.CategoryModel
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

    var allProducts = mutableStateListOf<ProductResponse>()
    var filteredProducts = mutableStateListOf<ProductResponse>()

    private var _activeFiltersCount = MutableStateFlow(0)
    var activeFiltersCount: StateFlow<Int> = _activeFiltersCount

    var allCategories = mutableStateListOf<CategoryModel>()

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
                val allCategoriesResponse = repository.getCategories()
                allProducts.addAll(allProductsResponse)
                allCategories.addAll(allCategoriesResponse)
                filteredProducts.addAll(allProductsResponse)
            } catch (_: Exception) {
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
        filteredProducts.clear()
        filteredProducts.addAll(newFilteredProducts)
    }

    fun clearSearchText() {
        _searchText.value = ""
        filteredProducts.clear()
        filteredProducts.addAll(allProducts)
    }

    fun applyFilters(
        checkedCategories: MutableList<String>,
        rating: Int,
        price: ClosedFloatingPointRange<Float>
    ) {
        filteredProducts.clear()
        filteredProducts.addAll(allProducts.filter { product ->
            checkedCategories.contains(product.category)
                && product.rating.toInt() >= rating
                && product.price >= price.start && product.price <= price.endInclusive
        })

        getActiveFiltersCount(checkedCategories, rating, price)
    }

    private fun getActiveFiltersCount(
        checkedCategories: MutableList<String>,
        rating: Int,
        price: ClosedFloatingPointRange<Float>
    ) {
        var count = 0;
        if (rating != 1) {
            count += 1
        }
        if (checkedCategories.count() != 4) {
            count += 1
        }
        if (price.start != 0f || price.endInclusive != 200f) {
            count += 1
        }
        _activeFiltersCount.value = count
    }

    fun removeAllFilters() {
        filteredProducts.clear()
        filteredProducts.addAll(allProducts)
        _activeFiltersCount.value = 0
    }
}