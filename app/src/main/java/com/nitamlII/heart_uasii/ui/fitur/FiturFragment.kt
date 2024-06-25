package com.nitamlII.heart_uasii.ui.fitur

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nitamlII.heart_uasii.Fitur
import com.nitamlII.heart_uasii.R
import com.nitamlII.heart_uasii.adapter.ListFiturAdapter

class FiturFragment : Fragment() {

    private lateinit var rvFitur: RecyclerView
    private lateinit var list: ArrayList<Fitur>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_fitur, container, false)
        rvFitur = view.findViewById(R.id.rv_fitur)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list = getListFitur()
        showRecyclerList()
    }

    private fun getListFitur(): ArrayList<Fitur> {
        val dataFitur = resources.getStringArray(R.array.data_fitur)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhotoResources = resources.obtainTypedArray(R.array.data_photo)

        val listFitur = ArrayList<Fitur>()
        for (i in dataFitur.indices) {
            val fitur = Fitur(
                dataFitur[i],
                dataDescription[i],
                dataPhotoResources.getResourceId(i, -1)
            )
            listFitur.add(fitur)
        }
        dataPhotoResources.recycle()

        return listFitur
    }

    private fun showRecyclerList() {
        rvFitur.layoutManager = LinearLayoutManager(requireContext())
        val listFiturAdapter = ListFiturAdapter(list)
        rvFitur.adapter = listFiturAdapter

        listFiturAdapter.setOnItemClickCallback(object : ListFiturAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Fitur) {
                // Handle item click here if needed
            }
        })
    }
}
