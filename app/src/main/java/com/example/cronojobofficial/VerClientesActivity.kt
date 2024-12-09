package com.example.cronojobofficial

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cronojobofficial.Adapter.AdapterCliente
import com.example.cronojobofficial.Models.Cliente
import com.example.cronojobofficial.databinding.ActivityVerClientesBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class VerClientesActivity : AppCompatActivity() {
    //inicializar viewBinding
    private lateinit var binding: ActivityVerClientesBinding

    //declarar FIREBASE DATABASE REALTIME
    private lateinit var database: DatabaseReference

    //lista clientes
    private lateinit var clientesList: ArrayList<Cliente>

    //declarar adaptador
    private lateinit var adapterCliente: AdapterCliente

    //reciclar view
    private lateinit var clienteRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //viewBinding
        binding = ActivityVerClientesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //mostrar datos en la pantalla

        clienteRecyclerView = binding.rvClientes
        clienteRecyclerView.layoutManager = LinearLayoutManager(this)
        clienteRecyclerView.hasFixedSize()

        clientesList = arrayListOf<Cliente>()

        getCliente()
    }

    private fun getCliente() {
        database = FirebaseDatabase.getInstance().getReference("Clientes")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // Limpiamos la lista de clientes antes de agregar nuevos datos
                    clientesList.clear()

                    for (clientesSnapshot in snapshot.children) {
                        val cliente = clientesSnapshot.getValue(Cliente::class.java)
                        // Verificamos que cliente no sea null antes de agregarlo
                        if (cliente != null) {
                            clientesList.add(cliente)
                        }
                    }

                    // Inicializamos el adaptador solo una vez
                    adapterCliente = AdapterCliente(clientesList)
                    clienteRecyclerView.adapter = adapterCliente
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejo de errores si la consulta a la base de datos es cancelada
                println("Error al cargar los clientes: ${error.message}")
            }
        })
    }
}