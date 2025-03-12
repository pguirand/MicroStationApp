package com.example.microstationapp.ui.customers

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
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
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.microstationapp.data.remote.ApiResponse
import com.example.microstationapp.viewmodels.ProductsViewModel
import com.example.microstationapp.viewmodels.UsersViewModel


@Composable
fun CustomerDetailScreen(
    navController:NavHostController,
    userId:String?
) {
    if (userId == null) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)) {
            Text(
                text = "Error : User Id is missing",
                color = Color.Red,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    } else {
        var viewModel : UsersViewModel = hiltViewModel()
        val userDetailState by viewModel.singleUser.collectAsState()

        LaunchedEffect(userId) {
            viewModel.getUserById(userId)
        }

        when(userDetailState) {
            is ApiResponse.Error -> {
                val errorMessage = (userDetailState as ApiResponse.Error).exception
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(255,200,200))
                ) {
                    Text(
                        text = "Error : $errorMessage",
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            ApiResponse.Loading -> {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Gray)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Green
                    )
                }
            }
            is ApiResponse.Success -> {
                val user = (userDetailState as ApiResponse.Success).data

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    user.firstName?.let { it ->
                        Text(
                            text = it,
                            style = MaterialTheme.typography.headlineLarge
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Image(
                            painter = rememberAsyncImagePainter(user.image),
                            contentDescription = user.userAgent,
                            modifier = Modifier.size(200.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("${user.firstName} ${user.lastName}")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "${user.age} years old")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "${user.role}")
                    }
                }
            }
        }
    }
}