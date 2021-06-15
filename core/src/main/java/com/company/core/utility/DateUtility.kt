package com.company.core.utility

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun String.formatDate(): String {
    val input = SimpleDateFormat("yyyy-MM-dd")
    val output = SimpleDateFormat("dd MMMM yyyy")
    return try {
        val inputDate : Date? = input.parse(this) // parse input
        inputDate?.let {
            output.format(inputDate) // format output
        }?:""
    } catch (e: ParseException) {
        e.printStackTrace()
        ""
    }
}