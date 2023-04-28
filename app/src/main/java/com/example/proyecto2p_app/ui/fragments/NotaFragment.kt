package com.example.proyecto2p_app.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.proyecto2p_app.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class NotaFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_nota, container, false)

        // encontrar el botón por su id
        val myButton = view.findViewById<Button>(R.id.btnRegreso)

        // establecer el listener para el botón
        myButton.setOnClickListener {
            // acción que se realiza cuando se presiona el botón
            Toast.makeText(context, "Regresando a Home", Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.action_notaFragment_to_homeFragment)
        }

        return view
    }

    companion object {

        fun newInstance(param1: String, param2: String) =
            NotaFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}