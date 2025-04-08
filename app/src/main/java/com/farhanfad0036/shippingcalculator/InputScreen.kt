package com.farhanfad0036.shippingcalculator

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.LocalShipping
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.farhanfad0036.shippingcalculator.ui.theme.ShippingCalculatorTheme
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
    val regularIconColor by animateColorAsState(
        targetValue = if (selectedService == "Regular") Color(0xFF1976D2) else Color.Gray,
        label = "RegularIconColor"
    )
    val expressIconColor by animateColorAsState(
        targetValue = if (selectedService == "Express") Color(0xFFE53935) else Color.Gray,
        label = "ExpressIconColor"
    )

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

            Row (
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable { selectedService = "Regular" }
                        .padding(8.dp)
                ) {
                    RadioButton(
                        selected = selectedService == "Regular",
                        onClick = {selectedService = "Regular"}
                    )
                    Icon(
                        imageVector = Icons.Filled.LocalShipping,
                        contentDescription = stringResource(R.string.regular),
                        tint = regularIconColor,
                        modifier = Modifier
                            .size(20.dp)
                            .padding(start = 4.dp)
                    )
                    Text(
                        text = stringResource(R.string.regular),
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.width(24.dp))

                Row (verticalAlignment = Alignment.CenterVertically,
                     modifier = Modifier
                         .clickable { selectedService = "Express" }
                         .padding(8.dp)
                    ) {
                    RadioButton(
                        selected = selectedService == "Express",
                        onClick = {selectedService = "Express"}
                    )
                    Icon(
                        imageVector = Icons.Filled.Flight,
                        contentDescription = stringResource(R.string.express),
                        tint = expressIconColor,
                        modifier = Modifier
                            .size(20.dp)
                            .padding(start = 4.dp)
                    )
                    Text(
                        text = stringResource(R.string.express),
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
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
                val message = """
                    Selected Branch: $selectedBranch
                    Service Type: $selectedService
                    Result: $result
                """.trimIndent()

                shareResult(context, message)
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

@Preview(showBackground = true, name = "Light Mode")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode")
@Composable
fun InputScreenPreview() {
    ShippingCalculatorTheme {
        val navController = rememberNavController()
        InputScreen(navController = navController)
    }
}