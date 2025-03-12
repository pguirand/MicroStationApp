package com.example.microstationapp.ui.customers

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import com.example.microstationapp.data.models.AllUsersModel
import com.example.microstationapp.data.models.UserModel
import com.example.microstationapp.data.remote.ApiResponse
import com.example.microstationapp.ui.products.ProductItem
import com.example.microstationapp.viewmodels.UsersViewModel

@Composable
fun CustomerScreen(
    modifier: Modifier = Modifier,
    navController:NavHostController,
    viewModel: UsersViewModel
) {
    val usersListState by viewModel.listAllUsers.collectAsState()

    when(usersListState) {
        ApiResponse.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Gray)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .background(Color.Gray)
                        .align(Alignment.Center),
                    color = Color.Red
                )
            }
        }
        is ApiResponse.Success -> {
            val allUsersModel = (usersListState as ApiResponse.Success<AllUsersModel>).data
            val usersList = allUsersModel.users ?: emptyList()

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(usersList) { user ->
                    UserItem(singleUserModel = user) {
                        navController.navigate("customerDetails/${user.id}")
                    }
                }
            }


        }
        is ApiResponse.Error -> {
            val errorMessage = (usersListState as ApiResponse.Error).exception
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Red)
            ) {
                Text(
                    text = errorMessage,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)

                )
            }
        }

    }

}

@Composable
fun UserItem(
    singleUserModel: UserModel,
    onItemClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Surface(
            onClick = onItemClick
        ) {
            Row(
                modifier = Modifier
                    .padding(1.dp)
                    .fillMaxSize()
                    .background(Color(120, 120, 220))
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Image(
                    modifier = Modifier
                        .size(100.dp)
                        .padding(2.dp),
                    painter = rememberAsyncImagePainter(singleUserModel.image),
                    contentDescription = singleUserModel.userAgent
                )
                Spacer(modifier = Modifier.weight(0.5f))
                Column(
                    modifier = Modifier
                        .padding(4.dp)
                        .weight(0.5f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("${singleUserModel.firstName}")
                    Text("${singleUserModel.lastName}")
                    Text("${singleUserModel.age} years old")
                }
            }
        }
    }
}