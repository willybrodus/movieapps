package com.company.core.utility

import java.text.ParseException
import java.text.SimpleDateFormat

fun String.formatDate(): String {
    val input = SimpleDateFormat("yyyy-MM-dd")
    val output = SimpleDateFormat("dd MMMM yyyy")
    return try {
        val inputDate = input.parse(this) // parse input
        output.format(inputDate) // format output
    } catch (e: ParseException) {
        e.printStackTrace()
        ""
    }
}