package com.example.microstationapp.ui.products

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.microstationapp.data.models.AllProductsModel
import com.example.microstationapp.data.models.ProductModel
import com.example.microstationapp.data.remote.ApiResponse
import com.example.microstationapp.viewmodels.ProductsViewModel

@Composable
fun ProductsScreen(
    navController: NavHostController,
    viewModel: ProductsViewModel
) {
    val productsListState by viewModel.listAllProducts.collectAsState()

    when(productsListState) {
        is ApiResponse.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(200, 200, 200, 100))
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .background(Color(200, 200, 200, 100))
                        .align(Alignment.Center),
                    color = Color.Yellow
                )
            }
        }
        is ApiResponse.Success -> {
            val allProductsModel = (productsListState as ApiResponse.Success<AllProductsModel>).data
            val productList = allProductsModel.products?.filterNotNull() ?: emptyList()

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(productList) { product ->
                    ProductItem(singleProductModel = product) {
                        navController.navigate("productDetails/${product.id}")
                    }

                }
            }
        }
        is ApiResponse.Error -> TODO()
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductItem(
    singleProductModel: ProductModel,
    onItemClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)

    ){
        Surface(
            onClick = onItemClick
        ) {
            Row(
                modifier = Modifier
                    .padding(1.dp)
                    .fillMaxSize()
                    .background(Color(120, 120, 200))
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(50.dp)
                        .padding(2.dp),
                    painter = rememberAsyncImagePainter(singleProductModel.thumbnail),
                    contentDescription = singleProductModel.description

                )
                Row() {
                    Column(
                        modifier = Modifier.padding(2.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text("text 1")
                        Text("text 2")
                        Text("text 3")
                        Text("text 4")
                    }
                    Column(modifier = Modifier.padding(4.dp)) {
                        Text("${singleProductModel.title}")
                        Text("${singleProductModel.description}")
                        Text("${singleProductModel.price}")
                        Text("${singleProductModel.category}")
                    }
                }
            }
        }
    }
}