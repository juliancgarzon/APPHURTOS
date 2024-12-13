package com.example.apphurtos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var usernameInput: EditText
    lateinit var passwordInput: EditText
    lateinit var loginBtn: Button
    lateinit var registerBtn: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usernameInput = findViewById(R.id.username_input)
        passwordInput = findViewById(R.id.password_input)
        loginBtn = findViewById(R.id.login_btn)
        registerBtn = findViewById(R.id.registerbtn)

        loginBtn.setOnClickListener {
            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                // Iniciar la llamada a la API
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val response = RetrofitClient.webService.login(
                            mapOf("usuario" to username, "contraseña" to password)
                        )

                        runOnUiThread {
                            if (response.isSuccessful && response.body()?.accessToken != null) {
                                // Redirigir a InicioActivity si la autenticación es exitosa
                                val intent = Intent(this@MainActivity, inicioActivity::class.java)
                                // Pasar el nombre de usuario a InicioActivity
                                intent.putExtra("USERNAME", username)
                                startActivity(intent)
                                finish() // Cierra la actividad actual
                            } else {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Usuario o contraseña incorrectos",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    } catch (e: Exception) {
                        runOnUiThread {
                            Toast.makeText(
                                this@MainActivity,
                                "Error de conexión o servidor: ${e.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Por favor ingrese todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        registerBtn.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}