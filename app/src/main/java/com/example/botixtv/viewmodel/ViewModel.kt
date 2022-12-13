package com.example.botixtv.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.http.HttpResponse
import com.apollographql.apollo3.exception.ApolloException
import com.example.botixtv.Fetch_noticias_by_categoria_ascQuery
import com.example.botixtv.apollo.apolloClient
import com.example.botixtv.model.Noticias
import com.example.botixtv.sealed.Resultado
import com.example.botixtv.selections.Fetch_noticias_by_categoria_ascQuerySelections
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ViewModel : ViewModel() {
    private val _loadProgress = MutableStateFlow<Boolean>(false)
    val loadProgress : StateFlow<Boolean> = _loadProgress

    private val _rest = MutableStateFlow<Resultado>(value = Resultado.Empty)
    val rest : StateFlow<Resultado> = _rest

    fun fetch_noticas(first: Int, page: Int, categoria_id: String) = viewModelScope.launch {
        _rest.value = Resultado.Empty
        val noticias_temp = ArrayList<Noticias> ()
        _loadProgress.value = true
        try {
            Log.e("ERRORGET","1")
            var response = apolloClient.query(
                Fetch_noticias_by_categoria_ascQuery(
                    first = first,
                    page = page,
                    categoria_id = categoria_id
                )
            ).execute()
            Log.e("ERRORGET","2")

            response?.data?.categoria_noticias_order?.data.let { noticias->
                var autor = ""
                var imagen = ""
                for (i in 0 until noticias!!.size) {
                    autor = noticias.get(i).autor?.let { it } ?: run { "" }
                    imagen = noticias.get(i).imagen?.let { it } ?: run { "" }

                    noticias_temp.add(
                        Noticias(
                            id = noticias.get(i).id.toInt(),
                            id_categoria = categoria_id.toInt(),
                            source = noticias.get(i).source,
                            autor = autor,
                            titulo = noticias.get(i).titulo,
                            descripcion = noticias.get(i).titulo,
                            url = noticias.get(i).url,
                            imagen = imagen,
                            fecha_publicado_utc = noticias.get(i).fecha_publicado_utc,
                            fecha_publicado = noticias.get(i).fecha_publicado,
                            contenido = noticias.get(i).contenido
                        )
                    )
                }
            }
            _rest.value = if (noticias_temp.isNotEmpty()) Resultado.Success(data = noticias_temp)
                else Resultado.Error(error = "")

        }catch (e: ApolloException){
            Log.e("ERRORGET","ERROR "+e.cause + " ")
            _rest.value = Resultado.Error(error = e.message.toString())
        }
        _loadProgress.value = false
    }
}