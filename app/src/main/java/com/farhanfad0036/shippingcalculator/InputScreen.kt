package com.farhanfad0036.shippingcalculator

import android.content.Context
import android.content.Intent
import android.util.Log
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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.text.NumberFormat
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputScreen(navController: NavController) {
    var distance by rememberSaveable { mutableStateOf("") }
    var weight by rememberSaveable { mutableStateOf("") }
    var selectedService by rememberSaveable { mutableStateOf("Regular") }
    val branchList = listOf("Jakarta", "Bandung", "Yogyakarta", "Medan", "Surabaya", "Padang", "Palembang")
    var selectedBranch by rememberSaveable { mutableStateOf(branchList[0]) }
    var expanded by remember { mutableStateOf(false) }
    var result by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                navigationIcon = {
                    IconButton(onClick = {navController.navigateUp() }) {
                        Icon(imageVector = Icons.Default.ArrowBack,
                             contentDescription = stringResource(R.string.back)
                        )
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
            Text(stringResource(R.string.enter_details), style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {expanded = !expanded }
            ) {
                TextField(
                    value = selectedBranch,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(stringResource(R.string.branch)) },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier.menuAnchor(),
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false}
                ) {
                    branchList.forEach{ branch ->
                        DropdownMenuItem(
                            text = { Text(branch) },
                            onClick = {
                                selectedBranch = branch
                                expanded = false
                            }
                        )

                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = distance,
                onValueChange = {distance = it },
                label = { Text(stringResource(R.string.distance_label)) },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = weight,
                onValueChange = { weight = it },
                label = { Text(stringResource(R.string.weight_label)) },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                RadioButton(selected = selectedService == "Regular", onClick = {selectedService = "Regular" })
                Text(stringResource(R.string.regular), modifier = Modifier.padding(start = 8.dp))
                Spacer(modifier = Modifier.width(16.dp))
                RadioButton(selected = selectedService == "Express", onClick = {selectedService = "Express"})
                Text(stringResource(R.string.express), modifier = Modifier.padding(start = 8.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                result = calculateShippingCost(distance.toDoubleOrNull(),weight.toDoubleOrNull(),selectedService)
            } ) {
                Text(stringResource(R.string.calculate))
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (result.isNotEmpty()) {
                Text(
                    text = stringResource(R.string.shipping_cost, result),
                    style = MaterialTheme.typography.bodyLarge
                )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                shareResult(context, "Branch: $selectedBranch\n$result")
            }) {
                Text(stringResource(R.string.share))
            }
            }
        }
    }
}
fun calculateShippingCost(distance: Double?, weight: Double?, serviceType: String): String
{
    if (distance == null || weight == null || distance <= 0 || weight <= 0) return "invalid input"

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