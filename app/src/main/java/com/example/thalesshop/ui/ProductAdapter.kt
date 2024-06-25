package com.example.thalesshop.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.thalesshop.databinding.ListItemBinding
import com.example.thalesshop.model.Product


class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    val imgURL = "https://upload.wikimedia.org/wikipedia/commons/thumb/1/15/Red_Apple.jpg/265px-Red_Apple.jpg"
    private var productList: List<Product> = emptyList()

    fun setProducts(products: List<Product>) {
        productList = products
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    inner class ProductViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {

            binding.apply {
                productName.text = product.name
                productPrice.text = "$ ${product.price}"
                productType.text = product.type
                productDescription.text = product.description
                // Load image using Glide or another image loading library
                Glide.with(itemView)
                    .load(product.picture)
                    .apply(RequestOptions().centerCrop())
                    .into(productImage)

            }
        }
    }
}
