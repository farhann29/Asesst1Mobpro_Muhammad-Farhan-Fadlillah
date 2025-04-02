package com.farhanfad0036.shippingcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.farhanfad0036.shippingcalculator.navigation.SetupNavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShippingApp()
        }
    }
}

@Composable
fun ShippingApp() {
    val navController = rememberNavController()
    SetupNavGraph(navController = navController)
}