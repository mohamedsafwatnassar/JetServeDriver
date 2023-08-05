package com.nextforce.jetservedriver.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nextforce.jetservedriver.common.utils.visible
import com.nextforce.jetservedriver.databinding.ItemAddNewSubOrderBinding
import com.nextforce.jetservedriver.home.model.ItemOrderModel

class DetailsItemOrderAdapter(
    private var newItemList: ArrayList<ItemOrderModel>,
    private val itemOrderClickCallBack: (position: Int, orderModel: ItemOrderModel) -> Unit
) : RecyclerView.Adapter<DetailsItemOrderAdapter.OrderViewHolder>() {
    private lateinit var binding: ItemAddNewSubOrderBinding

    inner class OrderViewHolder(itemView: ItemAddNewSubOrderBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val constraintStatus = itemView.constraintStatus
        val txtStatusOrder = itemView.txtStatusOrder
        val txtDateOrder = itemView.txtDateOrder
        val edtClientPhone = itemView.edtClientPhone
        val edtDestination = itemView.edtDestination
        val edtFeesPrice = itemView.edtFeesPrice
        val edtPrice = itemView.edtPrice
        val txtZoneName = itemView.txtZoneName

        init {
            // click in item order to show extra details
            itemView.root.setOnClickListener {
                itemOrderClickCallBack.invoke(
                    absoluteAdapterPosition,
                    newItemList[absoluteAdapterPosition]
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        binding =
            ItemAddNewSubOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val itemOrder = newItemList[position]
        bindData(holder, itemOrder)
    }

    private fun bindData(holder: OrderViewHolder, itemOrder: ItemOrderModel) {
        holder.txtStatusOrder.text = itemOrder.status
        holder.txtDateOrder.text = itemOrder.date
        //
        holder.constraintStatus.visible()
        //
        holder.edtClientPhone.isFocusable = false
        holder.edtClientPhone.isCursorVisible = false
        holder.edtClientPhone.setText(itemOrder.clientPhone)
        //
        holder.edtDestination.isFocusable = false
        holder.edtDestination.isCursorVisible = false
        holder.edtClientPhone.setText(itemOrder.clientPhone)
        //
        holder.edtFeesPrice.isFocusable = false
        holder.edtFeesPrice.isCursorVisible = false
        holder.edtClientPhone.setText(itemOrder.feesPrice.toString())
        //
        holder.edtPrice.isFocusable = false
        holder.edtPrice.isCursorVisible = false
        holder.edtClientPhone.setText(itemOrder.price.toString())
        //
        holder.txtZoneName.text = itemOrder.zoneName
    }

    override fun getItemCount(): Int {
        return newItemList.size
    }
}
