package com.example.proyecto2p_app.ui.fragments

import ApiResponse
import Nota
import NotasResponse
import WebService
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import com.example.proyecto2p_app.R
import com.example.proyecto2p_app.ui.LoginActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {

    private var id: Int? = null
    private var param2: String? = null

    private lateinit var notasListView: ListView
    private lateinit var notasAdapter: NotaListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        /*println("ID FRAGMENT")
        println(miId)

        // Comprobar si el ID es nulo
        if (miId == null) {
            // Manejar el caso en que el ID es nulo
        } else {
            // El ID no es nulo, hacer algo con él
        }*/


        // encontrar el botón por su id
        val myButton = view.findViewById<FloatingActionButton>(R.id.btnAgregarNota)

        // establecer el listener para el botón
        myButton.setOnClickListener {
            // acción que se realiza cuando se presiona el botón
            Toast.makeText(context, "Agregando nota", Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.action_homeFragment_to_notaFragment)
        }

        // Crear route
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.43.79:7257/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(WebService::class.java)
        val listaNotas = mutableListOf<Nota>()

        val coroutineScope = CoroutineScope(Dispatchers.IO)

        println("RESPUESTA DE NOTAS 1")

        coroutineScope.launch {
            try {
                val response = apiService.getNotas(id!!)
                println(response.body().toString())

                val gson = Gson()
                val apiResponse = gson.fromJson(response.body().toString(), NotasResponse::class.java)

                print("RESPUESTA DE NOTAS")
                print(apiResponse.data)

            } catch (e: Exception) {
                // manejar la excepción
                println("ENTRA AL CATCH")
                println("Error: $e")
            }
        }

        // encontrar el botón por su id
        val btnSalir = view.findViewById<Button>(R.id.btnLogout)

        // establecer el listener para el botón de salir
        btnSalir.setOnClickListener {
            // crear un Intent para lanzar LoginActivity
            val intent = Intent(activity, LoginActivity::class.java)
            // agregar la bandera para limpiar la pila de actividades
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            // lanzar LoginActivity
            startActivity(intent)
            // finalizar la actividad actual (MainActivity)
            activity?.finish()
        }



        //declarar una lista de objetos NOTA



        val nota2 = Nota(2, "Nota 2", "Esta es la nota 2")
        val nota3 = Nota(3, "Nota 3", "Esta es la nota 3")
        val nota4 = Nota(4, "Nota 4", "Esta es la nota 4")
        val nota5 = Nota(5, "Nota 5", "Esta es la nota 5")



        listaNotas.add(nota2)
        listaNotas.add(nota3)
        listaNotas.add(nota4)
        listaNotas.add(nota5)

        notasListView = view.findViewById(R.id.listView)
        notasAdapter = NotaListAdapter(requireContext(), listaNotas) // listaNotas es una lista de objetos Nota

        notasListView.adapter = notasAdapter

        notasListView.setOnItemClickListener { parent, view, position, id ->
            println("SELECCIONADO")
            val notaSeleccionada = listaNotas[position]
            Toast.makeText(requireContext(), notaSeleccionada.titulo, Toast.LENGTH_SHORT).show()
        }

        return view
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }*/

        // Obtener el ID desde los argumentos
        id = arguments?.getInt("miId")

        println("ID RECIBIDO DESDE EL ACTIVITYYY")
        println(id)
    }

    class NotaListAdapter(context: Context, notas: List<Nota>) :
        ArrayAdapter<Nota>(context, 0, notas) {

        private val inflater = LayoutInflater.from(context)

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = convertView ?: inflater.inflate(R.layout.list_item_nota, parent, false)

            val item = getItem(position)

            val tituloTextView = view.findViewById<TextView>(R.id.titulo_nota_text_view)
            val descripcionTextView = view.findViewById<TextView>(R.id.descripcion_nota_text_view)

            tituloTextView.text = item?.titulo
            descripcionTextView.text = item?.descripcion

            return view
        }
    }


}