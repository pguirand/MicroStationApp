package com.example.microstationapp.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.microstationapp.data.remote.ApiResponse
import com.example.microstationapp.ui.customers.CustomerDetailScreen
import com.example.microstationapp.ui.customers.CustomerScreen
import com.example.microstationapp.ui.home.HomeScreen
import com.example.microstationapp.ui.products.ProductDetailScreen
import com.example.microstationapp.ui.products.ProductsScreen
import com.example.microstationapp.ui.sales.SalesScreen
import com.example.microstationapp.viewmodels.ProductsViewModel
import com.example.microstationapp.viewmodels.SalesViewModel
import com.example.microstationapp.viewmodels.UsersViewModel

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Sales : Screen("sales")
    data object Customers : Screen("customers")
    data object Inventory : Screen("inventory")
    data object Orders : Screen("orders")
    data object Products : Screen("products")
    data object Management : Screen("management")

}

@Composable
fun NavGraph(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.Sales.route) {
            val salesViewModel = hiltViewModel<SalesViewModel>()
            SalesScreen(
                modifier = Modifier.padding(paddingValues),
                viewModel = salesViewModel
            )
        }
        composable(Screen.Customers.route) {
            CustomerScreen(
                modifier = Modifier.padding(paddingValues),
                navController = navController,
                viewModel = hiltViewModel<UsersViewModel>()
            )
        }
        composable(Screen.Products.route) {
            ProductsScreen(
                navController = navController,
                viewModel = hiltViewModel<ProductsViewModel>()
            )
        }
        //Products details route with product ID
        composable(
            route = "productDetails/{productId}",
            arguments = listOf(navArgument("productId") {  type = NavType.StringType})
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")
            ProductDetailScreen(navController = navController, productId = productId)
        }

        //User details route with user ID
        composable(
            route = "customerDetails/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) {
            backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")
            CustomerDetailScreen(navController = navController, userId = userId)
        }
    }
}