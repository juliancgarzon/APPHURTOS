package com.example.apphurtos

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class ReporteHurtoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.report_vehicle) // Enlaza con el archivo XML

        // Encuentra el Spinner por su ID
        val spinnerTipoHurto = findViewById<Spinner>(R.id.spinner_tipo_hurto)

        // Definir las opciones de tipo de hurto
        val tiposHurto = arrayOf("Vehículos", "Motocicletas", "Objetos Personales", "Otros")

        // Crear un adaptador con las opciones
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, // El diseño básico para los items del Spinner
            tiposHurto
        )

        // Especificar el diseño para el dropdown (cuando se despliega)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Asignar el adaptador al Spinner
        spinnerTipoHurto.adapter = adapter

        // Capturar la selección del Spinner
        var tipoHurtoSeleccionado = ""
        spinnerTipoHurto.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                tipoHurtoSeleccionado = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Si no se seleccionó nada
            }
        }

        // Configurar el botón para enviar el reporte
        val btnReportarHurto = findViewById<Button>(R.id.btn_enviar_reporte)
        btnReportarHurto.setOnClickListener {
            enviarReporte(tipoHurtoSeleccionado)
        }
    }

    private fun enviarReporte(tipoHurto: String) {
        // Obtén los valores de los campos del formulario
        val numeroPlaca = findViewById<EditText>(R.id.edit_numero_placa).text.toString()
        val color = findViewById<EditText>(R.id.edit_color).text.toString()
        val modelo = findViewById<EditText>(R.id.edit_modelo).text.toString()
        val kilometraje = findViewById<EditText>(R.id.edit_kilometraje).text.toString().toIntOrNull()
        val otrosDatos = findViewById<EditText>(R.id.edit_otros_datos).text.toString()
        val descripcionHurto = findViewById<EditText>(R.id.edit_descripcion_hurto).text.toString()
        val direccion = findViewById<EditText>(R.id.edit_direccion).text.toString()

        // Datos de ubicación simulados
        val latitud = 4.710989 // Cambiar por datos reales si tienes acceso a GPS
        val longitud = -74.072090

        val fecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val hora = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
        // Crear el objeto ReporteVehiculo
        val reporte = ReporteVehiculo(
            id_usuario = 1, // Aquí puedes asignar el ID del usuario real
            tipo_hurto = tipoHurto,
            numero_placa = if (numeroPlaca.isNotBlank()) numeroPlaca else null,
            color = if (color.isNotBlank()) color else null,
            modelo = if (modelo.isNotBlank()) modelo else null,
            kilometraje = kilometraje,
            otros_datos = if (otrosDatos.isNotBlank()) otrosDatos else null,
            descripcion_hurto = if (descripcionHurto.isNotBlank()) descripcionHurto else null,
            fecha = fecha, // Cambiar por fecha real
            hora = hora, // Cambiar por hora real
            direccion = direccion,
            latitud = latitud,
            longitud = longitud
        )

        // Llamada a la API usando Retrofit
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.webService.registrarReporte(reporte)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@ReporteHurtoActivity, "Reporte enviado correctamente", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@ReporteHurtoActivity, "Error: ${response.errorBody()?.string()}", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ReporteHurtoActivity, "Error al enviar el reporte: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
