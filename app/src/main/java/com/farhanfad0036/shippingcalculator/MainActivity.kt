package com.farhanfad0036.shippingcalculator

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.farhanfad0036.shippingcalculator.navigation.SetupNavGraph
import com.farhanfad0036.shippingcalculator.ui.theme.ShippingCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShippingApp()
        }
    }
}

@Preview(showBackground = true )
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ShippingApp() {
    ShippingCalculatorTheme {
        val navController = rememberNavController()
        SetupNavGraph(navController = navController)
    }
}