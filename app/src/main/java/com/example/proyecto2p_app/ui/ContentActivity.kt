package com.example.proyecto2p_app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.proyecto2p_app.R
import com.example.proyecto2p_app.ui.fragments.HomeFragment

class ContentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        // Obtén el ID del extra
        val id = intent.getIntExtra("ID", -1) // -1 es un valor predeterminado en caso de que el extra no se encuentre

        println("ID RECIBIDO")
        println(id)


        /*// Crear una instancia del Fragment
        val miFragment = HomeFragment()

        // Crear un Bundle y agregar el ID como argumento
        val args = Bundle()
        args.putInt("myID", id)

        // Establecer los argumentos en el Fragment
        miFragment.arguments = args*/

    }
}