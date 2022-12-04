package com.example.botixtv

import android.annotation.SuppressLint
import android.app.AlertDialog.THEME_DEVICE_DEFAULT_DARK
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.recyclerview.widget.GridLayoutManager
import com.example.botixtv.adapter.Adapter
import com.example.botixtv.databinding.ActivityMainBinding
import com.example.botixtv.model.Noticias
import com.example.botixtv.observer.UIObserver
import com.example.botixtv.utils.Utils
import com.example.botixtv.viewmodel.ViewModel
import com.squareup.picasso.Picasso


class MainActivity : FragmentActivity(),UIObserver {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: Adapter
    private var arraylist = ArrayList<Noticias>()
    private val viewModel : ViewModel by viewModels ()
    private var page = 0
    private var categoria_id = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cvHome.setOnClickListener {
            categoria_id = Utils.home
            seleccionar_card(categoria_id)
        }


        binding.cvMedicina.setOnClickListener {
            categoria_id = Utils.medicina
            seleccionar_card(categoria_id)

        }

        binding.cvDeporte.setOnClickListener {
            categoria_id = Utils.deporte
            seleccionar_card(categoria_id)
        }

        binding.cvTecnologia.setOnClickListener {
            categoria_id = Utils.tecnologia
            seleccionar_card(categoria_id)
        }

        set_up()

        lifecycleScope.launchWhenCreated {
            viewModel.resultNoticias.collect {
                arraylist.addAll(it)
                adapter.set_data(arraylist)
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.loadProgress.collect {
                binding.recycler.isVisible = !it
                binding.progressBar.isVisible = it
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.resultString.collect {
                if (it.isNotEmpty()){
                    Toast.makeText(this@MainActivity,it,Toast.LENGTH_LONG).show()
                }
            }
        }


    }



    fun set_up(){
        arraylist = arrayListOf()
        /*arraylist = arrayListOf<Noticias>(
            Noticias(1,1,"","","hello hello hello hello hello","","","https://i.blogs.es/2e76b4/frnlzagxoaior8l/840_560.jpg","","2022-11-26 23:58:04",""),
            Noticias(1,1,"","","hello22 hello22 hello22 ","","","","","2022-11-26 23:58:04",""),
            Noticias(1,1,"","","sports sports sports sports sports sports","","","","","2022-11-26 23:58:04",""),
            Noticias(1,1,"","","medic","","","","","2022-11-26 23:58:04",""),
            Noticias(1,1,"","","music","","","","","2022-11-26 23:58:04",""),
            Noticias(1,1,"","","hello1","","","","","2022-11-26 23:58:04",""),
            Noticias(1,1,"","","hello92","","","","","2022-11-26 23:58:04",""),
            Noticias(1,1,"","","sports2","","","","","2022-11-26 23:58:04",""),
            Noticias(1,1,"","","medic2","","","","","2022-11-26 23:58:04",""),
            Noticias(1,1,"","","music2","","","","","2022-11-26 23:58:04","")
        )*/
        adapter = Adapter(this, arraylist,this)
        binding.recycler.layoutManager = GridLayoutManager(this, 4)
        binding.recycler.adapter = adapter
        categoria_id = Utils.home
        seleccionar_card(categoria_id)
    }

    fun cambiar_aspecto_cardview(linear: LinearLayout,imageView: ImageView, resource: Int,textView: TextView, activa: Boolean){
        linear.background = resources.getDrawable(
            if (activa)
                R.drawable.card_activa
            else
                R.drawable.card_inactiva
        )
        imageView.setImageResource(resource)
        textView.setTextAppearance(
            this,
            if (activa)
                R.style.title_icon_blanco
            else
            R.style.title_icon_gris
        )
    }

    override fun on_click_noticia(noticia: Noticias) {
        mostrar_noticia_dialog(noticia = noticia)
    }

    fun load_noticias(categoria_id: String,page: Int){
        viewModel.fetch_noticas(
            first = Utils.elementos, page = page, categoria_id = categoria_id
        )
    }

    fun seleccionar_card(card: String){
        cambiar_aspecto_cardview(
            linear = binding.linearhome,
            imageView = binding.imagenhome,
            resource = R.drawable.home_gris,
            textView = binding.texthome,
            activa = false
        )

        cambiar_aspecto_cardview(
            linear = binding.linearmedicina,
            imageView = binding.imagemedicina,
            resource = R.drawable.medicina_gris,
            textView = binding.textmedicina,
            activa = false
        )

        cambiar_aspecto_cardview(
            linear = binding.lineardeporte,
            imageView = binding.imagedeporte,
            resource = R.drawable.deporte_gris,
            textView = binding.textdeporte,
            activa = false
        )

        cambiar_aspecto_cardview(
            linear = binding.lineartec,
            imageView = binding.imagetecn,
            resource = R.drawable.tecnologia_gris,
            textView = binding.texttecno,
            activa = false
        )

        if (card == Utils.home){
            cambiar_aspecto_cardview(
                linear = binding.linearhome,
                imageView = binding.imagenhome,
                resource = R.drawable.home_blanco,
                textView = binding.texthome,
                activa = true
            )
        }
        else if (card == Utils.medicina){
            cambiar_aspecto_cardview(
                linear = binding.linearmedicina,
                imageView = binding.imagemedicina,
                resource = R.drawable.medicina_blanco,
                textView = binding.textmedicina,
                activa = true
            )
        }
        else if (card == Utils.deporte){
            cambiar_aspecto_cardview(
                linear = binding.lineardeporte,
                imageView = binding.imagedeporte,
                resource = R.drawable.deporte_blanco,
                textView = binding.textdeporte,
                activa = true
            )
        }
        else if (card == Utils.tecnologia){
            cambiar_aspecto_cardview(
                linear = binding.lineartec,
                imageView = binding.imagetecn,
                resource = R.drawable.tecnologia_blanco,
                textView = binding.texttecno,
                activa = true
            )
        }
        arraylist = arrayListOf()

        load_noticias(
            categoria_id = card,
            page = 1
        )
    }

    private fun mostrar_noticia_dialog(noticia: Noticias) {
        val dialog = Dialog(this@MainActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.noticia_modal)
        val titulo = dialog.findViewById(R.id.titulo) as TextView
        val fecha = dialog.findViewById(R.id.fecha) as TextView
        val imagen = dialog.findViewById(R.id.imagen) as ImageView
        val contenido = dialog.findViewById(R.id.contenido) as TextView
        val cerrar = dialog.findViewById(R.id.cerrar) as TextView

        titulo.text = noticia.titulo
        contenido.text = noticia.contenido
        fecha.text = Utils.string_date(noticia.fecha_publicado)

        try {
            Picasso.with(this@MainActivity)
                .load(noticia.imagen)
                .fit()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(imagen)

        }catch (e:java.lang.Exception){
            e.printStackTrace()
            imagen.setBackgroundResource( R.drawable.placeholder )
        }

        cerrar.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}