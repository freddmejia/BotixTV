package com.example.botixtv.model

import org.json.JSONObject

class NoticiasGraphql (var hasMorePages: Boolean, var total: Int, val noticias: Noticias) {
    constructor(jsonObject: JSONObject): this (
        jsonObject.getBoolean("hasMorePages"),
        jsonObject.getInt("total"),
        Noticias(jsonObject.getJSONObject("data"))
            )
}