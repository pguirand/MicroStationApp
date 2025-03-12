package com.example.microstationapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.microstationapp.data.models.AllProductsModel
import com.example.microstationapp.data.models.ProductModel
import com.example.microstationapp.data.remote.ApiResponse
import com.example.microstationapp.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(private val repository: Repository): ViewModel(){

    private val _listAllProducts = MutableStateFlow<ApiResponse<AllProductsModel>>(ApiResponse.Loading)
    val listAllProducts : StateFlow<ApiResponse<AllProductsModel>> = _listAllProducts

    private val _productDetail = MutableStateFlow<ApiResponse<ProductModel>>(ApiResponse.Loading)
    val productDetail : StateFlow<ApiResponse<ProductModel>> = _productDetail

    init {
        getAllProducts()
    }

    internal fun getAllProducts() {
        viewModelScope.launch {
            try {
                val productsList = repository.getAllProducts()
                _listAllProducts.value = ApiResponse.Success(productsList)
            } catch (e : Exception) {
//                Log.e("error", e.message?:"Unexpected Error")
                _listAllProducts.value = ApiResponse.Error(e.message?:"Unexpected Error")
            }
        }
    }

    fun getProductById(productId:String) {
        viewModelScope.launch {
            _productDetail.value = ApiResponse.Loading
            try {
                val product = repository.getProductById(productId)
                _productDetail.value = ApiResponse.Success(product)
            } catch (e:Exception) {
                _productDetail.value = ApiResponse.Error(e.message ?:"Unexpected error")
            }
        }
    }
}