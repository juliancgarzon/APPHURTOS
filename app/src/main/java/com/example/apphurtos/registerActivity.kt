package com.example.apphurtos

import android.widget.Toast
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.apphurtos.databinding.ActivityRegisterBinding
import com.example.apphurtos.Usuario
import kotlinx.coroutines.launch
import java.util.Calendar
import android.content.Intent

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private lateinit var firstNameInput: EditText
    private lateinit var lastNameInput: EditText
    private lateinit var cityInput: EditText
    private lateinit var birthDateInput: TextView
    private lateinit var emailInput: EditText
    private lateinit var usernameInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var registerBtn: Button





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firstNameInput = binding.firstNameInput
        lastNameInput = binding.lastNameInput
        cityInput = binding.cityInput
        birthDateInput = binding.birthDateInput
        emailInput = binding.emailInput
        usernameInput = binding.usernameInput
        passwordInput = binding.passwordInput
        registerBtn = binding.registerBtn

        // Inicializamos el DatePickerDialog
        birthDateInput.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val formattedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    birthDateInput.text = formattedDate
                },
                year, month, day
            )
            datePickerDialog.show()
        }

        registerBtn.setOnClickListener {
            // Recolectar datos del formulario
            val nombre = firstNameInput.text.toString()
            val apellido = lastNameInput.text.toString()
            val ciudad = cityInput.text.toString()
            val fecha_de_nacimiento = birthDateInput.text.toString()
            val email = emailInput.text.toString()
            val usuario = usernameInput.text.toString()
            val contraseña = passwordInput.text.toString()

            // Crear objeto Usuario con los nombres correctos
            val usuarionuevo  = Usuario(nombre, apellido, ciudad, fecha_de_nacimiento, email, usuario, contraseña)

            // Llamar a la función para registrar el usuario
            registerUser(usuarionuevo)
        }
        // Dentro del onCreate() en RegisterActivity.kt
        val loginVentana: TextView = findViewById(R.id.login_ventana)

        loginVentana.setOnClickListener {
            val intent = Intent(this@RegisterActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun registerUser(usuarionuevo: Usuario) {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.webService.agregarUsuario(usuarionuevo)
                if (response.isSuccessful) {
                    Log.i("Registro", "Usuario registrado exitosamente")

                    // Limpiar los campos del formulario
                    firstNameInput.setText("")
                    lastNameInput.setText("")
                    cityInput.setText("")
                    birthDateInput.text = "" // Para TextView
                    emailInput.setText("")
                    usernameInput.setText("")
                    passwordInput.setText("")

                    // Mostrar un mensaje de éxito
                    Toast.makeText(this@RegisterActivity, "Registro satisfactorio", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("Registro", "Error al registrar el usuario: ${response.errorBody()}")
                    Toast.makeText(this@RegisterActivity, "Error al registrar, intenta nuevamente", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("Registro", "Error en la conexión: ${e.message}")
                Toast.makeText(this@RegisterActivity, "Error en la conexión", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
