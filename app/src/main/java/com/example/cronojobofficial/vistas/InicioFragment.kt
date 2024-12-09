package com.example.cronojobofficial.vistas

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cronojobofficial.ActivityLogin
import com.example.cronojobofficial.VerClientesActivity
import com.example.cronojobofficial.VerTrabajadoresActivity
import com.example.cronojobofficial.databinding.FragmentInicioBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth

class InicioFragment : Fragment() {

    // Variable para el binding
    private var _binding: FragmentInicioBinding? = null
    private val binding get() = _binding!!

    // Variable de FirebaseAuth
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inicializamos FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // cargamos el layout para este fragmento
        _binding = FragmentInicioBinding.inflate(inflater, container, false)

        binding.btnVerTrabajadores.setOnClickListener {
            val intent = Intent(requireActivity(), VerTrabajadoresActivity::class.java)
            startActivity(intent)
        }

        binding.btnVerClientes.setOnClickListener {
            val intent = Intent(requireActivity(), VerClientesActivity::class.java)
            startActivity(intent)
        }

        // Configuración del botón de cerrar sesión
        binding.btnCerrarSesion.setOnClickListener {
            // Cerrar sesión de Firebase
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Cerrar sesión")
                .setMessage("¿Estás seguro de que deseas cerrar sesión?")
                .setNeutralButton("Cancelar") { dialog, which -> }
                .setPositiveButton("Aceptar") { dialog, which ->
                    auth.signOut()

                    // Crear un Intent para ir al ActivityLogin
                    val intent = Intent(activity, ActivityLogin::class.java)
                    startActivity(intent)

                    // Finalizar la actividad para que el usuario no regrese a ella
                    activity?.finish()
                }
                .show()
        }

        // volvemos a la vista
        return binding.root
    }
}
