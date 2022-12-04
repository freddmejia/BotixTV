package com.example.botixtv.model

import android.util.Log
import org.json.JSONObject
import java.text.SimpleDateFormat

class Noticias(val id: Int, val id_categoria: Int, val source: String,
               val autor: String, val titulo: String, val descripcion: String,
               val url: String, val imagen: String, val fecha_publicado_utc: String,
               var fecha_publicado: String, val contenido: String) {
    constructor(jsonObject: JSONObject): this(
        jsonObject.getInt("id"),
        jsonObject.getInt("categoria"),
        jsonObject.getString("source"),
        jsonObject.getString("autor"),
        jsonObject.getString("titulo"),
        jsonObject.getString("descripcion"),
        jsonObject.getString("url"),
        jsonObject.getString("imagen"),
        jsonObject.getString("fecha_publicado_utc"),
        jsonObject.getString("fecha_publicado"),
        jsonObject.getString("contenido")
    ){
        /*try {
            this.fecha_publicado = string_date(this.fecha_publicado)
        }catch (e: java.lang.Exception){
            Log.e("ERRORGET","FECHA "+e.message)
            e.printStackTrace()
        }*/
    }

    /*fun string_date(stringDate: String): String{
        val formatter = SimpleDateFormat("MMMM dd, yyyy")
        val date = formatter.parse(stringDate)
        return formatter.format(date)
    }*/
}