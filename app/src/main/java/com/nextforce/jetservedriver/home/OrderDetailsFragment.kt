package com.nextforce.jetservedriver.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.nextforce.jetservedriver.R
import com.nextforce.jetservedriver.common.base.BaseFragment
import com.nextforce.jetservedriver.common.utils.gone
import com.nextforce.jetservedriver.common.utils.toast
import com.nextforce.jetservedriver.databinding.FragmentOrderDetailsBinding
import com.nextforce.jetservedriver.home.adapter.DetailsItemOrderAdapter
import com.nextforce.jetservedriver.home.model.ItemOrderModel
import com.nextforce.jetservedriver.home.model.OrderModel

class OrderDetailsFragment : BaseFragment() {
    private lateinit var binding: FragmentOrderDetailsBinding
    private lateinit var detailsItemOrderAdapter: DetailsItemOrderAdapter

    private val btnBackCallBackFunc: () -> Unit = {
        findNavController().navigateUp()
    }

    private val btnMenuVerticalCallBackFunc: () -> Unit = {
        toast("Clicked Menu Vertical")
    }

    private val itemOrderClickCallBack: (position: Int, itemOrder: ItemOrderModel) -> Unit =
        { position, itemOrder ->
            toast("Clicked Item Order $itemOrder -- $position")
            // val dialogFragment = MoreDetailsFragment()
            // dialogFragment.show(parentFragmentManager, "ExampleDialogFragment.TAG")
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // initialize custom app bar
        initToolbar()
        initView()
        setBtnListener()
    }

    private fun initToolbar() {
        binding.toolbarItemOrder.setTitleString("Order Details")
        binding.toolbarItemOrder.setTitleCenter(true)
        binding.toolbarItemOrder.setTitleColor(R.color.black)
        binding.toolbarItemOrder.useBackButton(
            true,
            btnBackCallBackFunc,
            R.drawable.ic_back
        )
        binding.toolbarItemOrder.useActionButton(
            true,
            R.drawable.ic_menu_vertical,
            btnMenuVerticalCallBackFunc
        )
    }

    private fun initView() {
        val order: OrderModel? = requireArguments().getParcelable("order")

        binding.edtReservedTime.isFocusable = false
        binding.edtReservedTime.isCursorVisible = false
        binding.txtTotalAmount.text = order?.totalPrice.toString()
        binding.txtTotalFeesPrice.text = order?.totalFees.toString()
        binding.txtTotalPrice.text = order?.totalPrice.toString()
        //
        binding.txtDriverNotAssigned.gone()
    }

    private fun setBtnListener() {
        binding.rvItemOrder.apply {
            detailsItemOrderAdapter =
                DetailsItemOrderAdapter(
                    getItemOrderList(),
                    itemOrderClickCallBack
                )

            adapter = detailsItemOrderAdapter
        }
    }

    private fun getItemOrderList(): ArrayList<ItemOrderModel> {
        val orderList = ArrayList<ItemOrderModel>()

        orderList.add(
            ItemOrderModel(
                1,
                "Pending",
                "9:43 PM 10, june 2023",
                "",
                destination = "3",
                feesPrice = 144.0,
                price = 333.33,
                clientPhone = "011555888999",
                zoneName = "Cairo"
            )
        )
        orderList.add(
            ItemOrderModel(
                2,
                "Created",
                "9:43 PM 10, june 2023",
                "",
                destination = "3",
                feesPrice = 144.0,
                price = 333.33,
                clientPhone = "011555888999",
                zoneName = "New Cairo"
            )
        )
        orderList.add(
            ItemOrderModel(
                3,
                "Assigned",
                "9:43 PM 10, june 2023",
                "",
                destination = "3",
                feesPrice = 144.0,
                price = 333.33,
                clientPhone = "011555888999",
                zoneName = "Giza"
            )
        )

        return orderList
    }
}
