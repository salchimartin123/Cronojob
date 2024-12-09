package com.example.cronojobofficial

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cronojobofficial.Adapter.AdapterTrabajador
import com.example.cronojobofficial.Models.Trabajador
import com.example.cronojobofficial.databinding.ActivityVerTrabajadoresBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class VerTrabajadoresActivity : AppCompatActivity() {


    //inicializar viewBinding
    private lateinit var binding: ActivityVerTrabajadoresBinding

    //declarar FIREBASE DATABASE REALTIME
    private lateinit var database: DatabaseReference

    //lista trabajadores
    private lateinit var trabajadoresList: ArrayList<Trabajador>

    //declarar adaptador
    private lateinit var adapterTrabajador: AdapterTrabajador

    //reciclar view
    private lateinit var trabajadorRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //viewBinding
        binding = ActivityVerTrabajadoresBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //mostrar datos en la pantalla

        trabajadorRecyclerView = binding.rvTrabajadores
        trabajadorRecyclerView.layoutManager = LinearLayoutManager(this)
        trabajadorRecyclerView.hasFixedSize()

        trabajadoresList = arrayListOf<Trabajador>()

        getTrabajador()
    }

    //definir el getTrabajador

    private fun getTrabajador() {
        database = FirebaseDatabase.getInstance().getReference("Trabajadores")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (trabajadoresSnapshot in snapshot.children){
                        val trabajador = trabajadoresSnapshot.getValue(Trabajador::class.java)
                        trabajadoresList.add(trabajador!!)
                        adapterTrabajador = AdapterTrabajador(trabajadoresList)
                        trabajadorRecyclerView.adapter = adapterTrabajador
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}