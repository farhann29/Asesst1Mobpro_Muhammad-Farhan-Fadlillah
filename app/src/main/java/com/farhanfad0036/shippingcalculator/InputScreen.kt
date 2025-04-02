package com.farhanfad0036.shippingcalculator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material3.*
import androidx.compose.ui.platform.LocalContext
import java.text.NumberFormat
import java.util.Locale
import android.content.Intent
import android.content.Context
import android.util.Log
import androidx.compose.foundation.pager.PagerSnapDistance


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputScreen(navController: NavController) {
    var distance by rememberSaveable { mutableStateOf("") }
    var weight by rememberSaveable { mutableStateOf("") }
    var selectedService by rememberSaveable { mutableStateOf("Regular") }
    var result by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Shipping Calculator") },
                navigationIcon = {
                    IconButton(onClick = {navController.navigateUp() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }

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
            Text("Enter shipping details", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = distance,
                onValueChange = {distance = it },
                label = { Text("Distance (km)") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = weight,
                onValueChange = { weight = it },
                label = { Text("Weight (kg)") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                RadioButton(selected = selectedService == "Regular", onClick = {selectedService = "Regular" })
                Text("Regular", modifier = Modifier.padding(start = 8.dp))
                Spacer(modifier = Modifier.width(16.dp))
                RadioButton(selected = selectedService == "Express", onClick = {selectedService = "Express"})
                Text("Express", modifier = Modifier.padding(start = 8.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                result = calculateShippingCost(distance.toDoubleOrNull(),weight.toDoubleOrNull(),selectedService)
            } ) {
                Text("Calculate")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (result.isNotEmpty()) {
                Text("Shipping Cost: $result", style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {shareResult(context, result)}) {
                Text("Share")
            }
            }
        }
    }
}
fun calculateShippingCost(distance: Double?, weight: Double?, serviceType: String): String
{
    if (distance == null || weight == null || distance <= 0 || weight <= 0) return "Invalid input"

    val baseRate = when (serviceType) {
        "Regular" -> 2000
        "Express" -> 5000
        else -> 0
    }
    val distanceFactor = 1 + ((distance - 10).coerceAtLeast(0.0) / 10 * 0.1)

    val totalCost = distanceFactor * weight * baseRate

    val  formattedCost = NumberFormat.getNumberInstance(Locale("id", "ID")).format(totalCost.toInt())

    return "Rp $formattedCost"
}

fun shareResult(context: Context, result: String) {
    try {
        val  intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "Shipping Cost Calculation:\n$result")
        }
        context.startActivity(Intent.createChooser(intent, "Share via"))
    } catch (e: Exception) {
        Log.e("ShareError", "Error sharing result: ${e.message}")
    }
}