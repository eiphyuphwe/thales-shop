package com.example.thalesshop.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thalesshop.model.Product
import com.example.thalesshop.network.ProductService
import com.example.thalesshop.network.RetrofitClient
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    private val productService = RetrofitClient.retrofit.create(ProductService::class.java)

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    // Original list of products
    private var originalProducts: List<Product> = emptyList()


    init {
        viewModelScope.launch {
            try {
                originalProducts = productService.getAllProducts()
                _products.value = originalProducts
            } catch (e: Exception) {
                // Handle error
                Log.e("Error", "Error fetching products: ${e.message}")
            }
        }
    }


    fun sortProductsByPriceAscending() {
        _products.value = originalProducts.sortedBy { it.price }
    }

    fun sortProductsByPriceDescending() {
        _products.value = originalProducts.sortedByDescending { it.price }
    }

    fun sortProductsByTypeAscending() {
        _products.value = originalProducts.sortedBy { it.type }
    }

    fun sortProductsByTypeDescending() {
        _products.value = originalProducts.sortedByDescending { it.type }
    }

    fun searchProducts(query: String) {
        if (query.isEmpty()) {
            _products.value = originalProducts
        } else {
            val filteredProducts = originalProducts.filter {
                it.name.contains(query, ignoreCase = true)
            }
            _products.value = filteredProducts
        }
    }
}
