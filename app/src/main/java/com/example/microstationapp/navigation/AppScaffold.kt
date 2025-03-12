package com.example.microstationapp.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.microstationapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold (
    navController: NavHostController,
    customTitle:String? = null,
    content: @Composable (PaddingValues) -> Unit
) {
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry.value?.destination?.route

    Scaffold(
        topBar = {
       if (currentDestination != Screen.Home.route && currentDestination != null) {
                TopAppBar(
                    title = { Text(text = customTitle?:currentDestination) },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                painter = painterResource(id = R.drawable.arrow_back_24dp_5f6368_fill0_wght400_grad0_opsz24),
                                contentDescription = "Back"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = Color(0xFF64FFDA)
                    )
                )
            }
        }
    ) { paddingValues ->
        content(paddingValues)

    }
}