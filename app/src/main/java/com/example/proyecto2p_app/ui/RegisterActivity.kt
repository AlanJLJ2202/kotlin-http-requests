package com.example.proyecto2p_app.ui

import WebService
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.proyecto2p_app.MainActivity
import com.example.proyecto2p_app.R
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        val btnRegistro = findViewById<Button>(R.id.button_login)
        val boton_exit = findViewById<Button>(R.id.button_exit)
        val texto_usuario = findViewById<EditText>(R.id.editText_username)
        val texto_password = findViewById<EditText>(R.id.editText_password)
        val texto_password2 = findViewById<EditText>(R.id.editText_password2)


        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.43.79:7257/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(WebService::class.java)

        btnRegistro.setOnClickListener {
            // Crea un nuevo hilo de trabajo
            val coroutineScope = CoroutineScope(Dispatchers.IO)


            //validar si los campos no estan vacios

            if(texto_usuario.text.toString() == "" || texto_password.text.toString() == "" || texto_password2.text.toString() == ""){
                Toast.makeText(this, "No se permiten campos vacíos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{
                if (texto_password.text.toString() != texto_password2.text.toString()){
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            // Ejecuta la solicitud HTTP en el nuevo hilo de trabajo
            coroutineScope.launch {
                println("Entra a la función")
                try {

                    println("DATOS ENVIADOS")
                    println(texto_usuario.text.toString())
                    println(texto_password.text.toString())

                    val response = apiService.registro(0, texto_usuario.text.toString(), texto_password.text.toString())


                    //if (response.isSuccessful) {

                    println("API RESPONSE")
                    println(response.body().toString())
                    val bodyResponse = response.body().toString()

                    println(bodyResponse)

                    if(bodyResponse != "null"){
                        println("Entra al if")

                        //NAVEGAR A CONTENT ACTIVITY

                        runOnUiThread {
                            val builder = AlertDialog.Builder(this@RegisterActivity)
                            builder.setMessage("Usuario registrado con éxito, inicia sesión")
                            builder.setCancelable(false)
                            builder.setPositiveButton("Aceptar") { dialog, _ ->
                                dialog.dismiss()

                                // Crea un Intent para ir a la siguiente actividad
                                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)

                                // Inicia la siguiente actividad
                                startActivity(intent)
                            }

                            val dialog = builder.create()
                            dialog.show()
                        }
                    } else {

                        println("Entra al else")

                        runOnUiThread {
                            val builder = AlertDialog.Builder(this@RegisterActivity)
                            builder.setMessage("Ocurrió un error al registrarse")
                            builder.setCancelable(false)
                            builder.setPositiveButton("Aceptar") { dialog, _ ->
                                dialog.dismiss()
                            }
                            val dialog = builder.create()
                            dialog.show()
                        }
                    }

                } catch (e: Exception) {
                    // manejar la excepción

                    println("Error: $e")
                }
            }
        }

        boton_exit.setOnClickListener {
            println("Entra a la función")
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        //Navegar al segundo activity
        fun navegarSegundoActivity() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}