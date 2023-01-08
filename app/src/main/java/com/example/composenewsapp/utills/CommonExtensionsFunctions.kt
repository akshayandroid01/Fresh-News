package com.example.composenewsapp.utills

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun getDateFormat(string: String?): String? {
    val inputSDF = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault())
    val outputSDF = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val date = try {
        //here you get Date object from string
        string?.let { inputSDF.parse(it) }
    } catch (e: ParseException) {
        return string
    }
    //after changing date format again you can change to string with changed format
    return date?.let { outputSDF.format(it) }
}