package com.example.apphurtos

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.example.apphurtos.databinding.ActivityInicioBinding

class inicioActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityInicioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar la barra de herramientas
        setSupportActionBar(binding.appBarInicio.toolbar)

        // Obtener el nombre de usuario desde el Intent
        val username = intent.getStringExtra("USERNAME")

        // Acceder al `NavigationView` y luego al encabezado para encontrar el `TextView`
        val navView: NavigationView = binding.navView
        val headerView = navView.getHeaderView(0)
        val textView = headerView.findViewById<TextView>(R.id.textView)

        // Establecer el texto con el nombre de usuario
        textView.text = username ?: "Usuario no encontrado"

        // Configurar el botón flotante
        binding.appBarInicio.fab.setOnClickListener {
            // Inicia la actividad de reporte de hurto
            val intent = Intent(this, ReporteHurtoActivity::class.java)
            startActivity(intent)
        }

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navController = findNavController(R.id.nav_host_fragment_content_inicio)
        // Pasar cada ID de menú como un conjunto de IDs porque cada
        // menú debe considerarse como destinos de nivel superior.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflar el menú; esto agrega elementos a la barra de acción si está presente.
        menuInflater.inflate(R.menu.inicio, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_inicio)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
