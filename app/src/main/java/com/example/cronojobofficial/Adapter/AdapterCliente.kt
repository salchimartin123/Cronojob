package com.example.cronojobofficial.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cronojobofficial.Models.Cliente
import com.example.cronojobofficial.R

class AdapterCliente(private var clientes: ArrayList<Cliente>):
    RecyclerView.Adapter<AdapterCliente.ViewHolder>() {
    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
            val nombre: TextView = itemView.findViewById(R.id.tvNombreCliente)
            val correo: TextView = itemView.findViewById(R.id.tvCorreoCliente)
            val numero: TextView = itemView.findViewById(R.id.tvNumeroCliente)
            val descripcion: TextView = itemView.findViewById(R.id.tvDescripcionCliente)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterCliente.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_clientes, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        val cliente = clientes[position]

        holder.nombre.text = cliente.nombre
        holder.correo.text = cliente.correo
        holder.numero.text = cliente.numero
        holder.descripcion.text = cliente.descripcion
    }

    override fun getItemCount(): Int {
        return clientes.size
    }
}