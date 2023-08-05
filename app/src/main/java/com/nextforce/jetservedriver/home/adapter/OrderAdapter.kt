package com.nextforce.jetservedriver.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nextforce.jetservedriver.common.utils.Constant
import com.nextforce.jetservedriver.databinding.ItemOrderBinding
import com.nextforce.jetservedriver.home.model.OrderModel

class OrderAdapter(
    private var orderList: ArrayList<OrderModel>,
    private val orderClickCallBack: (position: Int, orderModel: OrderModel) -> Unit
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    private lateinit var binding: ItemOrderBinding

    inner class OrderViewHolder(itemView: ItemOrderBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val txtStatusOrder = itemView.txtStatusOrder
        val txtDateOrder = itemView.txtDateOrder
        val chipRecycler = itemView.chipRecycler
        val txtSubCount = itemView.txtSubCount
        val txtReservedTime = itemView.txtReservedTime
        val txtTotalFees = itemView.txtTotalFees
        val txtTotalPrice = itemView.txtTotalPrice
        val txtDriverName = itemView.txtDriverName
        val imgDriver = itemView.imgDriver

        init {
            binding.root.setOnClickListener {
                orderClickCallBack.invoke(bindingAdapterPosition, orderList[bindingAdapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orderList[position]
        bindData(holder, order)
    }

    private fun bindData(holder: OrderViewHolder, order: OrderModel) {
        Glide.with(holder.itemView.context)
            .load(Constant.defaultImage)
            .circleCrop()
            // .placeholder(R.drawable.ic_placeholder_school)
            // .error(R.drawable.ic_placeholder_school)
            .into(holder.imgDriver)
        holder.txtStatusOrder.text = order.status
        holder.txtDateOrder.text = order.date
        holder.txtSubCount.text = "Count ${order.subCount}"
        holder.txtReservedTime.text = "Reserved time: ${order.reservedTime}"
        holder.txtTotalFees.text = "Total fees: ${order.totalFees}"
        holder.txtTotalPrice.text = "Total price: ${order.totalPrice} EGP"
        holder.txtDriverName.text = order.driverName
    }

    override fun getItemCount(): Int {
        return orderList.size
    }
}
