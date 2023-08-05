package com.nextforce.jetservedriver.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nextforce.jetservedriver.databinding.ItemZoneBinding
import com.nextforce.jetservedriver.home.model.ZoneModel

class ZoneAdapter(
    private var chipList: ArrayList<ZoneModel>
) : RecyclerView.Adapter<ZoneAdapter.ChipViewHolder>() {
    private lateinit var binding: ItemZoneBinding

    class ChipViewHolder(itemView: ItemZoneBinding) : RecyclerView.ViewHolder(itemView.root) {
        val txtZone = itemView.txtZone
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChipViewHolder {
        binding = ItemZoneBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChipViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ChipViewHolder, position: Int) {
        val zone = chipList[position]

        holder.txtZone.text = zone.zoneName
    }

    override fun getItemCount(): Int {
        return chipList.size
    }
}
