package com.example.botixtv.utils

import java.text.SimpleDateFormat

class Utils {
    companion object {
        val home = "1"
        val medicina = "2"
        val deporte = "3"
        val tecnologia = "4"
        val elementos = 20
        val server = "http://192.168.1.16:8000/graphql"
        fun string_date(stringDate: String): String{
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val formatter1 = SimpleDateFormat("MMMM dd, yyyy")
            val date = formatter.parse(stringDate)
            return formatter1.format(date)
        }
    }
}