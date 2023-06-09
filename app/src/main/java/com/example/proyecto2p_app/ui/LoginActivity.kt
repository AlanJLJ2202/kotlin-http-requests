package com.example.proyecto2p_app.ui

import ApiResponse
import User
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
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLogin = findViewById<Button>(R.id.button_login)
        val btnRegistro = findViewById<Button>(R.id.button_registro)
        val boton_exit = findViewById<Button>(R.id.button_exit)
        val texto_usuario = findViewById<EditText>(R.id.editText_username)
        val texto_password = findViewById<EditText>(R.id.editText_password)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.43.79:7257/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(WebService::class.java)

        btnLogin.setOnClickListener {
            // Crea un nuevo hilo de trabajo
            val coroutineScope = CoroutineScope(Dispatchers.IO)


            //validar si los campos no estan vacios

            if(texto_usuario.text.toString() == "" || texto_password.text.toString() == ""){
                Toast.makeText(this, "No se permiten campos vacíos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Ejecuta la solicitud HTTP en el nuevo hilo de trabajo
            coroutineScope.launch {
                println("Entra a la función")
                try {

                    println("DATOS ENVIADOS")
                    println(texto_usuario.text.toString())
                    println(texto_password.text.toString())
                    val response = apiService.login(texto_usuario.text.toString(), texto_password.text.toString())

                    //if (response.isSuccessful) {

                        println("API RESPONSE")
                        println(response.body().toString())
                        val bodyResponse = response.body().toString()

                        val gson = Gson()
                        val apiResponse = gson.fromJson(response.body().toString(), ApiResponse::class.java)

                        println("RESPUESTA")
                        println(apiResponse.data)

                        val UserObject = gson.fromJson(apiResponse.data.toString(), User::class.java)
                        println("ID DEL USUARIO")
                        println(UserObject.id)

                        if(UserObject.id != 0){
                            println("Entra al if")

                            //NAVEGAR A CONTENT ACTIVITY

                            runOnUiThread {
                                val builder = AlertDialog.Builder(this@LoginActivity)
                                builder.setMessage("Bienvenid@!")
                                builder.setCancelable(false)
                                builder.setPositiveButton("Aceptar") { dialog, _ ->
                                    dialog.dismiss()

                                    // Crea un Intent para ir a la siguiente actividad
                                    val intent = Intent(this@LoginActivity, ContentActivity::class.java)


                                    intent.putExtra("ID", UserObject.id)

                                    // Inicia la siguiente actividad
                                    startActivity(intent)
                                }

                                val dialog = builder.create()
                                dialog.show()
                            }
                        } else {

                            println("Entra al else")

                            runOnUiThread {
                                val builder = AlertDialog.Builder(this@LoginActivity)
                                builder.setMessage("Usuario o contraseña incorrectos")
                                builder.setCancelable(false)
                                builder.setPositiveButton("Aceptar") { dialog, _ ->
                                    dialog.dismiss()
                                }
                                val dialog = builder.create()
                                dialog.show()
                            }
                        }

                    /*} else {


                        /*val builder = AlertDialog.Builder(this@LoginActivity)
                        builder.setMessage("Ocurrió un error al conectarse con el servidor")
                        builder.setCancelable(false)
                        builder.setPositiveButton("Aceptar") { dialog, _ ->
                            dialog.dismiss()
                        }*/
                        //Dialogo de error con toast


                    }*/
                } catch (e: Exception) {
                    // manejar la excepción

                    println("Error: $e")
                }
            }

            /*if (texto_usuario.text.toString() == "admin" && texto_password.text.toString() == "1234" ||
                texto_usuario.text.toString() == "jair" && texto_password.text.toString() == "1234" ||
                texto_usuario.text.toString() == "carlos" && texto_password.text.toString() == "1234") {
                navegarSegundoActivity()
            } else {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Usuario o contraseña incorrectos")
                builder.setCancelable(false)
                builder.setPositiveButton("Aceptar") { dialog, _ ->
                    dialog.dismiss()
                }
                val dialog = builder.create()
                dialog.show()
            }*/


        }

        boton_exit.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("¿Está seguro de que desea salir de la aplicación?")
            builder.setCancelable(false)
            builder.setPositiveButton("Sí") { _, _ ->
                finish()
            }
            builder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }

        btnRegistro.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
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

