package com.example.botixtv.adapter

import android.content.Context
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.botixtv.model.Noticias
import com.example.botixtv.observer.UIObserver
import com.example.botixtv.R
import com.example.botixtv.utils.Utils
import java.text.SimpleDateFormat
import com.squareup.picasso.Picasso

class Adapter (val context: Context, var list: ArrayList<Noticias>, val observer: UIObserver):
    RecyclerView.Adapter<Adapter.holder>() {

    class holder(view: View): RecyclerView.ViewHolder(view) {
        private var cardview : CardView
        private var imagen : ImageView
        private var titulo : TextView
        private var fecha : TextView
        init {
            cardview = view.findViewById(R.id.cardview)
            imagen = view.findViewById(R.id.imagen)
            titulo = view.findViewById(R.id.titulo)
            fecha = view.findViewById(R.id.fecha)
        }
        fun bin(context: Context, noticia: Noticias, observer: UIObserver) {
            titulo.text = noticia.titulo
            fecha.text = Utils.string_date(noticia.fecha_publicado)
            cardview.setOnClickListener {
                observer.on_click_noticia(
                    noticia = noticia
                )
            }
            try {
                Picasso.with(context)
                    .load(noticia.imagen)
                    .fit()
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(imagen)

            }catch (e:java.lang.Exception){
                e.printStackTrace()
                imagen.setBackgroundResource( R.drawable.placeholder )
            }
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): holder {
        return holder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.card_noticia, parent, false))
    }

    override fun onBindViewHolder(holder: holder, position: Int) {
        holder.bin(
            context,
            list[position],observer
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun set_data(arrayList: ArrayList<Noticias>){
        list = arrayList
        notifyDataSetChanged()
    }

}

