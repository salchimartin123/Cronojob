package com.example.cronojobofficial.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cronojobofficial.Models.Trabajador
import com.example.cronojobofficial.R

class AdapterTrabajador(private var trabajadores: ArrayList<Trabajador>):
    RecyclerView.Adapter<AdapterTrabajador.ViewHolder>() {

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

        val nombre: TextView = itemView.findViewById(R.id.tvNombreTrabajador)
        val correo: TextView = itemView.findViewById(R.id.tvCorreoTrabajador)
        val cargo: TextView = itemView.findViewById(R.id.tvCargoTrabajador)

    }


    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int): AdapterTrabajador.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_trabajadores, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        val trabajador = trabajadores[position]

        holder.nombre.text = trabajador.nombre
        holder.correo.text = trabajador.correo
        holder.cargo.text = trabajador.cargo
    }

    override fun getItemCount(): Int {
        return trabajadores.size
    }

}