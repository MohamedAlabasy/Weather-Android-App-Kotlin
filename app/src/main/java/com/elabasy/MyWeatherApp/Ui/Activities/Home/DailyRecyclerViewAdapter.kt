package com.elabasy.MyWeatherApp.Ui.Activities.Home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.elabasy.MyWeatherApp.Util.DataTesttt
import com.elabasy.MyWeatherApp.databinding.ItemDailyRecyclerViewBinding
import androidx.recyclerview.widget.RecyclerView.ViewHolder as ViewHolder1

class DailyRecyclerViewAdapter(list: ArrayList<DataTesttt>? = null, var context: Context? = null) :
    RecyclerView.Adapter<DailyRecyclerViewAdapter.ItemViewHolder>() {

    private var list = ArrayList<DataTesttt>()

    init {
        if (list != null) {
            this.list = ArrayList<DataTesttt>(list)
        }
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding: ItemDailyRecyclerViewBinding = ItemDailyRecyclerViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val data = list[position]
        if (position == 4) { holder.binding.view.visibility = GONE }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ItemViewHolder(itemView: ItemDailyRecyclerViewBinding) :
        ViewHolder1(itemView.root) {
        val binding: ItemDailyRecyclerViewBinding = itemView

    }

    fun setList(list: ArrayList<DataTesttt>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    private fun getItem(position: Int): DataTesttt {
        return list[position]
    }


}