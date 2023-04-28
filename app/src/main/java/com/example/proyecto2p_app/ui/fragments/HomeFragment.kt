package com.example.proyecto2p_app.ui.fragments

import Nota
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
import org.w3c.dom.Text

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment(private val miId: Int) : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var notasListView: ListView
    private lateinit var notasAdapter: NotaListAdapter

    private var id = miId


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // encontrar el botón por su id
        val myButton = view.findViewById<FloatingActionButton>(R.id.btnAgregarNota)

        // establecer el listener para el botón
        myButton.setOnClickListener {
            // acción que se realiza cuando se presiona el botón
            Toast.makeText(context, "Agregando nota", Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.action_homeFragment_to_notaFragment)
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
        val listaNotas = mutableListOf<Nota>()

        val nota = Nota(1, "Nota 1", "Esta es la nota 1")
        val nota2 = Nota(2, "Nota 2", "Esta es la nota 2")
        val nota3 = Nota(3, "Nota 3", "Esta es la nota 3")
        val nota4 = Nota(4, "Nota 4", "Esta es la nota 4")


        listaNotas.add(nota)
        listaNotas.add(nota2)
        listaNotas.add(nota3)
        listaNotas.add(nota4)

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
        val miId = arguments?.getInt("miId")

        println("ID RECIBIDO DESDE EL ACTIVITY")
        println(miId)


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