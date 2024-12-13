package com.example.apphurtos.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.apphurtos.R
import com.example.apphurtos.databinding.FragmentHomeBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MapStyleOptions

class HomeFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var map: GoogleMap

    companion object {
        private const val REQUEST_LOCATION_PERMISSION_CODE = 1001
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Encuentra el fragmento de mapa y configura el callback para cuando el mapa esté listo
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // Aplica el estilo del mapa
        try {
            val success = map.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map_style)
            )
            if (!success) {
                Log.e("MapStyle", "No se pudo aplicar el estilo.")
            }
        } catch (e: Exception) {
            Log.e("MapStyle", "Error aplicando el estilo del mapa: ", e)
        }

        // Carga el ícono desde los recursos y redimensiona
        val originalBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.mask)
        val scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, 100, 100, false)
        val icon = BitmapDescriptorFactory.fromBitmap(scaledBitmap)

        // Lista de ubicaciones con sus títulos
        val locations = listOf(
            LatLng(4.7110, -74.0721) to "Bogotá - Hurto 1",
            LatLng(4.6534, -74.0836) to "Bogotá - Hurto 2",
            LatLng(4.6097, -74.0817) to "Bogotá - Hurto 3",
            LatLng(4.7484, -74.0919) to "Bogotá - Hurto 4"
        )

        // Agrega un marcador para cada ubicación
        for ((coords, title) in locations) {
            val markerOptions = MarkerOptions()
                .position(coords)
                .title(title)
                .icon(icon)

            map.addMarker(markerOptions)
        }

        // Centra la cámara en el primer marcador
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(locations[0].first, 12f))

        enableLocation()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun isLocationPermissionGranted() =
        ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    private fun enableLocation() {
        if (!::map.isInitialized) return
        if (isLocationPermissionGranted()) {
            try {
                map.isMyLocationEnabled = true
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        } else {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            Toast.makeText(requireContext(), "Acepta los permisos en ajustes", Toast.LENGTH_SHORT).show()
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_LOCATION_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido
                Toast.makeText(requireContext(), "Permiso concedido", Toast.LENGTH_SHORT).show()
                enableLocation() // Habilita la ubicación
            } else {
                // Permiso denegado
                Toast.makeText(requireContext(), "Permiso denegado", Toast.LENGTH_SHORT).show()
            }
        }
    }
}