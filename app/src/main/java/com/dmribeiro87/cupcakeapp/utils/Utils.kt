package com.dmribeiro87.cupcakeapp.utils

fun twoDecimals(value: Double): String {
    return String.format("%.2f", value).replace(".", ",")
}