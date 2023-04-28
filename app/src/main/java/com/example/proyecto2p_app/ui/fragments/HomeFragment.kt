package com.example.proyecto2p_app.ui.fragments

import Nota
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import com.example.proyecto2p_app.R
import org.w3c.dom.Text

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var itemList: List<Nota>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        val texto_usuario = findViewById<EditText>(R.id.txt_usuario)


    }



    class ItemListAdapter(
        private val context: Context,
        private val itemList: List<Nota>
    ) : ArrayAdapter<Nota>(context, 0, itemList) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var itemView = convertView
            if (itemView == null) {
                itemView = LayoutInflater.from(context).inflate(R.layout.fragment_home, parent, false)
            }

            val item = itemList[position]

            // Asignar los valores del objeto a la vista de lista
            val nameTextView = itemView?.findViewById<TextView>(R.id.name_text_view)

            if (nameTextView != null) {
                nameTextView.text = item.nombre
            }

            val descriptionTextView = itemView.findViewById<TextView>(R.id.description_text_view)
            descriptionTextView.text = item.description

            // Devolver la vista de lista con los valores asignados
            return itemView
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Obtener la lista de objetos desde la API
        val json = obtenerListaDeApi()
        itemList = parsearJson(json)

        // Inflar la vista de lista
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        val listView = rootView.findViewById<ListView>(R.id.list_view)

        // Crear el adaptador para la vista de lista
        val adapter = ItemListAdapter(requireContext(), itemList)

        // Asignar el adaptador a la vista de lista
        listView.adapter = adapter

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        textView = view.findViewById<TextView>(R.id.text_view_id)

        return rootView
    }



}