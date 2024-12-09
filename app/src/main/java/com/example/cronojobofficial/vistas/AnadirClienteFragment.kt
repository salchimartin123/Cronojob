package com.example.cronojobofficial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cronojobofficial.Models.Cliente
import com.example.cronojobofficial.Models.Trabajador
import com.example.cronojobofficial.databinding.FragmentAnadirClienteBinding
import com.example.cronojobofficial.databinding.FragmentAnadirTrabajadorBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AnadirClienteFragment : Fragment() {
    // Variable para el binding
    private lateinit var binding: FragmentAnadirClienteBinding

    // Variable de Firebase RealTime Database
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Carga el diseño para este fragmento usando view binding
        binding = FragmentAnadirClienteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicialización de base de datos
        database = FirebaseDatabase.getInstance().getReference("Clientes")

        binding.buttonGuardarCliente.setOnClickListener {
            // Obtener los datos de los EditText
            val nombre = binding.tiNombreCliente.editText?.text.toString().trim()
            val correo = binding.tiCorreoCliente.editText?.text.toString().trim()
            val telefono = binding.tiTelefonoCliente.editText?.text.toString().trim()
            val descripcion = binding.tiDescripcionCliente.editText?.text.toString().trim()

            // Generar el id random
            val id = database.push().key
            if (id == null) {
                Snackbar.make(binding.root, "Error al generar ID", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validación de campos vacíos
            if (nombre.isEmpty()) {
                binding.tiNombreCliente.error = "Por favor ingresar nombre"
                return@setOnClickListener
            }
            if (correo.isEmpty()) {
                binding.tiCorreoCliente.error = "Por favor ingresar correo"
                return@setOnClickListener
            }
            if (telefono.isEmpty()) {
                binding.tiTelefonoCliente.error = "Por favor ingresar el telefono"
                return@setOnClickListener
            }
            if (descripcion.isEmpty()) {
                binding.tiDescripcionCliente.error = "Por favor ingresar la descripcion del trabajo"
                return@setOnClickListener
            }

            // Crear un objeto Trabajador
            val cliente = Cliente(id, nombre, correo, telefono, descripcion)

            // Guardar el trabajador en Firebase Database
            id.let {
                database.child(it).setValue(cliente)
                    .addOnSuccessListener {
                        // Limpiar los campos después de agregar el trabajador
                        binding.tiNombreCliente.editText?.setText("")
                        binding.tiCorreoCliente.editText?.setText("")
                        binding.tiTelefonoCliente.editText?.setText("")
                        binding.tiDescripcionCliente.editText?.setText("")

                        // Mostrar un mensaje de éxito
                        Snackbar.make(binding.root, "Cliente Agregado", Snackbar.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        // Mostrar mensaje de error si no se pudo guardar el trabajador
                        Snackbar.make(binding.root, "Error al agregar Cliente: ${e.message}", Snackbar.LENGTH_SHORT).show()
                    }
            }
        }
    }
}
