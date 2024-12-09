package com.example.cronojobofficial

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cronojobofficial.databinding.ActivityMainBinding
import com.example.cronojobofficial.vistas.InicioFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    //configurar viewBinding

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configuración de los bordes de la actividad (Edge to Edge)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Cargar el fragmento predeterminado (InicioFragment) al crear la actividad
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, InicioFragment()).commit()
        }

        // Configuración de la navegación mediante el BottomNavigationView
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.item_1 -> {
                    // Cargar InicioFragment
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, InicioFragment()).commit()
                    true
                }
                R.id.item_2 -> {
                    // Cargar AnadirTrabajadorFragment
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, AnadirTrabajadorFragment()).commit()
                    true
                }
                R.id.item_3 -> {
                    // Cargar AnadirClienteFragment
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, AnadirClienteFragment()).commit()
                    true
                }
                else -> false
            }
        }
    }
}