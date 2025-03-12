package com.example.microstationapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.microstationapp.data.DashBoardItems
import com.example.microstationapp.navigation.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }
//    val snackBarHostState = remember { SnackbarHostState() }
//    val coroutineScope = rememberCoroutineScope()

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false},
            title = { Text(text = "Unexpected Action")},
            text = { Text(text = dialogMessage)},
            confirmButton = {
                TextButton(onClick = { showDialog = false}) {
                    Text("OK")
                }
            }
        )
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFB3E5FC), Color(0xFFE1F5FE))
                )
            )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            border = null,
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                color = Color.Transparent,
            ) {
                Column(
                    modifier = Modifier
                        .padding(1.dp)
                        .fillMaxSize()
                        .background(Color.Gray),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Micro Station",
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Text(
                        text = "Micro Station Dash board",
                        style = MaterialTheme.typography.subtitle1,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
//                    SnackbarHost(hostState = snackBarHostState)
                    FlowRow(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .background(Color.LightGray, shape = RoundedCornerShape(4.dp))
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    )
                    {
                        DashBoardItems.entries.forEach { item ->
                            val label = item.label
                            Box(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .background(
                                        brush = Brush.horizontalGradient(
//                                            colors = listOf(Color.White, Color.LightGray)
                                            colors = listOf(Color(0xFFA7FFEB), Color(0xFF64FFDA))
                                        ),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(vertical = 8.dp, horizontal = 16.dp)
                                    .clickable {
                                        when (item) {
                                            DashBoardItems.SALES -> navController.navigate(Screen.Sales.route)
                                            DashBoardItems.CUSTOMERS -> navController.navigate(
                                                Screen.Customers.route
                                            )
//                                            DashBoardItems.INVENTORY -> navController.navigate(
//                                                Screen.Inventory.route
//                                            )
//                                            DashBoardItems.ORDERS -> navController.navigate(Screen.Orders.route)
                                            DashBoardItems.PRODUCTS -> navController.navigate(Screen.Products.route)
//                                            DashBoardItems.MANAGEMENT -> navController.navigate(
//                                                Screen.Management.route
//                                            )

                                            else -> {
//                                                coroutineScope.launch {
//                                                    snackBarHostState.showSnackbar(
//                                                        message = "Unexpected action for ${item.label}",
//                                                        actionLabel = "OK"
//                                                    )
//                                                }
                                                dialogMessage = "Unexpected action: $label"
                                                showDialog = true
                                                println("Unexpected action: $label")
                                            }
                                        }
                                    }
                            ) {
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.body1,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.DarkGray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}
