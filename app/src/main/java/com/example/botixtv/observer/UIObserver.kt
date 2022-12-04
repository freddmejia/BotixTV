package com.example.botixtv.observer

import com.example.botixtv.model.Noticias

interface UIObserver {
    fun on_click_noticia(noticia: Noticias)
}