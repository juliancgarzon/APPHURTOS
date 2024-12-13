package com.example.apphurtos

data class ReporteVehiculo(
    val id_usuario: Int,
    val tipo_hurto: String,
    val numero_placa: String?,
    val color: String?,
    val modelo: String?,
    val kilometraje: Int?,
    val otros_datos: String?,
    val descripcion_hurto: String?,
    val fecha: String,
    val hora: String,
    val direccion: String,
    val latitud: Double,
    val longitud: Double
)
