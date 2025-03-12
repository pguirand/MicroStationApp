package com.example.microstationapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.microstationapp.navigation.MainScaffold
import com.example.microstationapp.navigation.NavGraph
import com.example.microstationapp.ui.theme.MicroStationAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MicroStationAppTheme {
                val navController = rememberNavController()
                MainScaffold(navController) { paddingValues ->
                    NavGraph(
                        navController = navController,
                        paddingValues = paddingValues
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MicroStationAppTheme {
        Greeting("Android")
    }
}

/*
* MicroStation project.
TODO
Simulate loading / sealed class
Implement room - caching
Simulate managing local APIs implement retrofit and AppModule
Integrating logging screen
CRUD operations local and remote (get, POSt, put, delete)
Testing(unit, ui tests)
Generating Reports (Sales, Order…), etc
Accessibility / analytics ?*/