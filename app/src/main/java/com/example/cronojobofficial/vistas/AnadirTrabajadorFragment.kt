package com.example.cronojobofficial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cronojobofficial.Models.Trabajador
import com.example.cronojobofficial.databinding.FragmentAnadirTrabajadorBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AnadirTrabajadorFragment : Fragment() {
    // Variable para el binding
    private lateinit var binding: FragmentAnadirTrabajadorBinding

    // Variable Firebase RealTime Database
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Carga el diseño para este fragmento usando view binding
        binding = FragmentAnadirTrabajadorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicialización de base de datos
        database = FirebaseDatabase.getInstance().getReference("Trabajadores")

        binding.btnRegsitrarTrabajador.setOnClickListener {
            // Obtener los datos de los EditText
            val nombre = binding.tiNombreTrabajador.editText?.text.toString().trim()
            val correo = binding.tiCorreoTrabajador.editText?.text.toString().trim()
            val telefono = binding.tiTelefonoTrabajador.editText?.text.toString().trim()
            val cargo = binding.tiCargoTrabajador.editText?.text.toString().trim()

            // Generar el id random
            val id = database.push().key
            if (id == null) {
                Snackbar.make(binding.root, "Error al generar ID", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validación de campos vacíos
            if (nombre.isEmpty()) {
                binding.tiNombreTrabajador.error = "Por favor ingresar nombre"
                return@setOnClickListener
            }
            if (correo.isEmpty()) {
                binding.tiCorreoTrabajador.error = "Por favor ingresar correo"
                return@setOnClickListener
            }
            if (telefono.isEmpty()) {
                binding.tiTelefonoTrabajador.error = "Por favor ingresar el telefono"
                return@setOnClickListener
            }
            if (cargo.isEmpty()) {
                binding.tiCargoTrabajador.error = "Por favor ingresar el cargo"
                return@setOnClickListener
            }

            // Crear un objeto Trabajador
            val trabajador = Trabajador(id, nombre, correo, telefono, cargo)

            // Guardar el trabajador en Firebase Database
            id.let {
                database.child(it).setValue(trabajador)
                    .addOnSuccessListener {
                        // Limpiar los campos después de agregar el trabajador
                        binding.tiNombreTrabajador.editText?.setText("")
                        binding.tiCorreoTrabajador.editText?.setText("")
                        binding.tiTelefonoTrabajador.editText?.setText("")
                        binding.tiCargoTrabajador.editText?.setText("")

                        // Mostrar un mensaje de éxito
                        Snackbar.make(binding.root, "Trabajador Agregado", Snackbar.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        // Mostrar mensaje de error si no se pudo guardar el trabajador
                        Snackbar.make(binding.root, "Error al agregar trabajador: ${e.message}", Snackbar.LENGTH_SHORT).show()
                    }
            }
        }
    }
}
