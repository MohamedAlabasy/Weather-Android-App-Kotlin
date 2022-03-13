package com.elabasy.MyWeatherApp.Ui.Activities.Home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elabasy.MyWeatherApp.Interface.OnItemRecyclerViewClickListener
import com.elabasy.MyWeatherApp.Util.DataTesttt
import com.elabasy.MyWeatherApp.databinding.ItemCityRecyclerViewBinding
 import androidx.recyclerview.widget.RecyclerView.ViewHolder as ViewHolder1

class CityRecyclerViewAdapter(
    list: ArrayList<DataTesttt>? = null, var context: Context? = null)
    : RecyclerView.Adapter<CityRecyclerViewAdapter.ItemViewHolder>() {

    private var list = ArrayList<DataTesttt>()
    private lateinit var clickListener: OnItemRecyclerViewClickListener
    private lateinit var itemDeleteListener: OnItemRecyclerViewClickListener


    init {
        if (list != null) {
            this.list = ArrayList<DataTesttt>(list)
        }
        notifyDataSetChanged()
    }

    fun setList(list: ArrayList<DataTesttt>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    // to mack  Click Listener from activity
    fun setOnItemClick(recyclerViewOnItemClick: OnItemRecyclerViewClickListener) {
        clickListener = recyclerViewOnItemClick
        notifyDataSetChanged()
    }

    // to mack Delete from activity
    fun setOnDeleteItem(recyclerViewOnItemClick: OnItemRecyclerViewClickListener) {
        itemDeleteListener = recyclerViewOnItemClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding: ItemCityRecyclerViewBinding = ItemCityRecyclerViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ItemViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val data = list[position]
        holder.binding.item.setOnClickListener { clickListener.setOnItemClickListener(position) }
        holder.binding.delete.setOnClickListener {
            itemDeleteListener.setOnItemClickListener(position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ItemViewHolder(itemView: ItemCityRecyclerViewBinding) :
        ViewHolder1(itemView.root) {
        val binding: ItemCityRecyclerViewBinding = itemView

    }

    fun add(data: DataTesttt) {
        list.add(data)
        notifyItemInserted(list.size - 1)
    }


    fun remove(item: DataTesttt) {
        val position = list.indexOf(item)
        if (position > -1) {
            list.removeAt(position)
        }
        notifyItemRemoved(position)
    }


}