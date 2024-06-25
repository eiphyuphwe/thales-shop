package com.example.thalesshop.network

import com.example.thalesshop.model.Product
import retrofit2.http.GET

interface ProductService {
    @GET("products")
    suspend fun getAllProducts(): List<Product>
}