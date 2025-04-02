package com.farhanfad0036.shippingcalculator.navigation

sealed class Screen (val route:String){
    data object Home :  Screen("homeScreen")
    data object Input :  Screen("inputScreen")
}