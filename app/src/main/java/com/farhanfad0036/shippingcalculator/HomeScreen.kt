package com.farhanfad0036.shippingcalculator

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.farhanfad0036.shippingcalculator.navigation.Screen
import com.farhanfad0036.shippingcalculator.ui.theme.ShippingCalculatorTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun  HomeScreen(navController: NavController) {
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) }
            )
        }
    ) { paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_icon),
                contentDescription = "App Logo",
                modifier = Modifier.size(165.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(stringResource(R.string.fullapp_name), style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(16.dp))

            Text(stringResource(R.string.desc_app), style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = {navController.navigate(Screen.Input.route)}) {
                Text(stringResource(R.string.start))
            }
        }
    }
}

@Preview(showBackground = true, name = "Light mode")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode")
@Composable
fun HomeScreenPreview() {
    ShippingCalculatorTheme {
        val navController = rememberNavController()
        HomeScreen(navController = navController)
    }
}
