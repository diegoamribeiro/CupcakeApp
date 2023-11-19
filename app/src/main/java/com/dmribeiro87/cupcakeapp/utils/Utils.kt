package com.dmribeiro87.cupcakeapp.utils

fun twoDecimals(value: Double): String {
    return String.format("%.2f", value).replace(".", ",")
}

fun formatCreditCardNumber(cardNumber: String): String {
    return cardNumber.replace("\\s+".toRegex(), "") // Remove espaços existentes
        .chunked(4)                   // Divide a string em pedaços de 4 caracteres
        .joinToString(" ")            // Junta os pedaços com um espaço entre eles
}

fun formatCardExpiryDate(date: String): String {
    val cleanDate = date.replace(Regex("[^\\d]"), "")
    return if (cleanDate.length >= 2) {
        val month = cleanDate.substring(0, 2)
        val year = if (cleanDate.length > 2) cleanDate.substring(2) else ""
        if (year.isNotEmpty()) "$month/$year" else month
    } else {
        cleanDate
    }
}
