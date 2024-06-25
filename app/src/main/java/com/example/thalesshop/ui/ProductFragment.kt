package com.example.thalesshop.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.thalesshop.databinding.FragmentProductBinding
import com.example.thalesshop.viewmodel.ProductViewModel

class ProductFragment : Fragment() {

    private lateinit var productViewModel: ProductViewModel
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProductBinding.inflate(inflater, container, false)
        productAdapter = ProductAdapter()

        binding.recyclerView.apply {
            adapter = productAdapter
            layoutManager = GridLayoutManager(context, 2) // Grid layout with 2 columns
            //addItemDecoration(GridItemDecoration()) // Optional: Add item decoration for spacing
        }

        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        productViewModel.products.observe(viewLifecycleOwner, Observer { products ->
            products?.let {
                productAdapter.setProducts(it)
            }
        })


        // Search functionality
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    productViewModel.searchProducts(it)
                }
                return true
            }
        })

        // Sorting functionality
        binding.sortByPrice.setOnClickListener {
            showSortOptionsDialog(SORT_BY_PRICE)
        }

        binding.sortByType.setOnClickListener {
            showSortOptionsDialog(SORT_BY_TYPE)
        }

        return binding.root
    }

    private fun showSortOptionsDialog(sortBy: Int) {
        val options = arrayOf("Ascending", "Descending")
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Sort By")

        builder.setSingleChoiceItems(options, -1) { dialog, which ->
            when (which) {
                0 -> {
                    when (sortBy) {
                        SORT_BY_PRICE -> productViewModel.sortProductsByPriceAscending()
                        SORT_BY_TYPE -> productViewModel.sortProductsByTypeAscending()
                    }
                }
                1 -> {
                    when (sortBy) {
                        SORT_BY_PRICE -> productViewModel.sortProductsByPriceDescending()
                        SORT_BY_TYPE -> productViewModel.sortProductsByTypeDescending()
                    }
                }
            }
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    companion object {
        private const val SORT_BY_PRICE = 0
        private const val SORT_BY_TYPE = 1
    }
}
