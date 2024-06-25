package com.nitamlII.heart_uasii.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nitamlII.heart_uasii.Fitur
import com.nitamlII.heart_uasii.R

class ListFiturAdapter(private val listFitur: ArrayList<Fitur>) :
    RecyclerView.Adapter<ListFiturAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_fitur, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val fitur = listFitur[position]
        holder.imgPhoto.setImageResource(fitur.photo)
        holder.tvName.text = fitur.name
        holder.tvDesc.text = fitur.description

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(fitur)
        }
    }

    override fun getItemCount(): Int = listFitur.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvDesc: TextView = itemView.findViewById(R.id.tvDesc)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Fitur)
    }
}
