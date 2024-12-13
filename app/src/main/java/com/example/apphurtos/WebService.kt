package com.example.apphurtos

import com.example.apphurtos.Usuario
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

// Clase para la respuesta de la API
data class ApiResponse(
    val success: Boolean,
    val message: String? = null,
    val accessToken: String? = null,

)

object AppConstantes {

    //const val BASE_URL = "http://192.168.0.113:3000"
   const val BASE_URL = "http://172.21.11.135:3000"
}

interface WebService {
    @POST("/createusers")
    suspend fun agregarUsuario(
        @Body usuario: Usuario
    ): Response<ApiResponse>

    // Método para autenticación
    @POST("/api/login")
    suspend fun login(
        @Body credentials: Map<String, String>
    ): Response<ApiResponse>
    @POST("/report/vehicle")
    suspend fun registrarReporte(@Body reporte: ReporteVehiculo): Response<Any>
}

object RetrofitClient {
    // Configuración del cliente HTTP con timeouts
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    // Configuración de Retrofit
    val webService: WebService by lazy {
        Retrofit.Builder()
            .baseUrl(AppConstantes.BASE_URL)
            .client(okHttpClient) // Usar el cliente HTTP configurado
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(WebService::class.java)
    }
}