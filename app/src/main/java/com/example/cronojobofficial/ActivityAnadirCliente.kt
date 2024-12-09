package com.example.cronojobofficial

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cronojobofficial.Models.Cliente
import com.example.cronojobofficial.databinding.FragmentAnadirClienteBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ActivityAnadirCliente : AppCompatActivity() {

    // Activar viewBinding
    private lateinit var binding: FragmentAnadirClienteBinding

    // Activar firebase DATABASE REALTIME
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = FragmentAnadirClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ajuste de las barras de sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar Firebase Database
        database = FirebaseDatabase.getInstance().getReference("Clientes")

        binding.buttonGuardarCliente.setOnClickListener {

            // Obtener los datos de los EditText (accediendo a los TextInputEditText internos)
            val nombre = binding.editTextNombre.text.toString()
            val correo = binding.editTextCorreo.text.toString()
            val telefono = binding.editTextTelefono.text.toString()
            val descripcion = binding.editTextDescripcion.text.toString()

            // Generar el id random
            val id = database.push().key

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
                binding.tiTelefonoCliente.error = "Por favor ingresar el número de teléfono"
                return@setOnClickListener
            }
            if (descripcion.isEmpty()) {
                binding.tiDescripcionCliente.error = "Por favor ingresar la descripción del trabajo"
                return@setOnClickListener
            }

            // Crear un objeto Cliente
            val cliente = Cliente(id, nombre, correo, telefono, descripcion)

            // Guardar el cliente en Firebase Database
            id?.let {
                database.child(it).setValue(cliente).addOnSuccessListener {
                    // Limpiar los campos después de agregar el cliente
                    binding.editTextNombre.setText("")
                    binding.editTextCorreo.setText("")
                    binding.editTextTelefono.setText("")
                    binding.editTextDescripcion.setText("")

                    // Mostrar un mensaje de éxito
                    Snackbar.make(binding.root, "Cliente Agregado", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
}
