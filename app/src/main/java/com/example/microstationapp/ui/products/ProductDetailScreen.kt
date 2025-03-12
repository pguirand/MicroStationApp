package com.example.microstationapp.ui.products

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.microstationapp.data.models.ProductModel
import com.example.microstationapp.data.remote.ApiResponse
import com.example.microstationapp.viewmodels.ProductsViewModel

@Composable
fun ProductDetailScreen(
    navController:NavHostController,
    productId:String?
) {
    if (productId == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(255, 200, 200))
        ) {
            Text(
                text = "Error : Product ID is missing",
                color = Color.Red,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    } else {
        val viewModel : ProductsViewModel = hiltViewModel()
        val productDetailState by viewModel.productDetail.collectAsState()

        LaunchedEffect(productId) {
            viewModel.getProductById(productId)
        }

        when(productDetailState) {
            is ApiResponse.Error -> {
                val errorMessage = (productDetailState as ApiResponse.Error).exception
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(255, 200, 200))
                ) {
                    Text(
                        text = "Error: $errorMessage",
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            ApiResponse.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(200, 200, 200, 100))
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Yellow
                    )
                }
            }
            is ApiResponse.Success -> {
                val product = (productDetailState as ApiResponse.Success<ProductModel>).data
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    product.title?.let { Text(text = it, style = MaterialTheme.typography.headlineLarge) }
                    Spacer(modifier = Modifier.height(8.dp))
                    Image(
                        painter = rememberAsyncImagePainter(product.thumbnail),
                        contentDescription = product.description,
                        modifier = Modifier.size(200.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    product.description?.let { Text(text = it) }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Price: \$${product.price}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Category: ${product.category}")
                }
            }
        }
    }


}