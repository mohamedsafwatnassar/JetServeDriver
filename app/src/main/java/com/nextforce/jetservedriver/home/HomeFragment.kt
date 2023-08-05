package com.nextforce.jetservedriver.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.nextforce.jetservedriver.R
import com.nextforce.jetservedriver.common.base.BaseFragment
import com.nextforce.jetservedriver.databinding.FragmentHomeBinding
import com.nextforce.jetservedriver.home.adapter.OrderAdapter
import com.nextforce.jetservedriver.home.model.OrderModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var orderAdapter: OrderAdapter

    private val btnNotificationCallBackFunction: () -> Unit = {
        findNavController().navigate(R.id.action_home_to_profile)
    }

    private val btnOrderClickCallBack: (position: Int, order: OrderModel) -> Unit = { _, order ->
        Log.d("btnOrderClickCallBack", "order $order")
        val bundle = Bundle()
        bundle.putParcelable("order", order)
        findNavController().navigate(R.id.action_home_to_orderDetails, bundle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initRecycler()
    }

    private fun initToolbar() {
        binding.toolbar.setTitleString("Driver APP")
        binding.toolbar.setTitleCenter(true)
        binding.toolbar.setTitleColor(R.color.black)
        binding.toolbar.useBackButton(
            true,
            btnNotificationCallBackFunction,
            R.drawable.ic_account_circle
        )
        binding.toolbar.useActionButton(
            true,
            R.drawable.ic_notifications_none,
            btnNotificationCallBackFunction
        )
    }

    private fun initRecycler() {
        binding.rvOrder.apply {
            orderAdapter = OrderAdapter(getOrderList(), btnOrderClickCallBack)
            adapter = orderAdapter
        }
    }

    private fun getOrderList(): ArrayList<OrderModel> {
        val orderList = ArrayList<OrderModel>()
        val zoneList = ArrayList<String>()

        zoneList.add("hyper one")
        zoneList.add("beverly")
        zoneList.add("Cairo")
        zoneList.add("Giza")
        zoneList.add("October")

        orderList.add(
            OrderModel(
                1,
                "pending", // pending
                "9:43 PM",
                "",
                "3",
                "9:43 PM 10, june 2023",
                "144444",
                "333.33",
                "",
                "Mohamed Mohamed 1",
                ""
            )
        )
        orderList.add(
            OrderModel(
                2,
                "picked",
                "9:43 PM 10, june 2023",
                "",
                "2",
                "9:43 PM",
                "144444",
                "222",
                "",
                "Mohamed 2",
                ""
            )
        )
        orderList.add(
            OrderModel(
                3,
                "assigned",
                "9:43 PM 10, june 2023",
                "",
                "1",
                "9:43 PM",
                "144444",
                "11",
                "Mohamed Mohamed 3",
                "Driver Not Assigned Yet",
                ""
            )
        )
        orderList.add(
            OrderModel(
                4,
                "delivered",
                "10, june 2023",
                "",
                "10",
                "9:43 PM",
                "144444",
                "101010.10",
                "",
                "Mohamed Mohamed 4",
                ""
            )
        )
        orderList.add(
            OrderModel(
                5,
                "delivered",
                "10, june 2023",
                "",
                "7",
                "9:43 PM",
                "144444",
                "777.4",
                "",
                "Mohamed Mohamed 5",
                ""
            )
        )

        return orderList
    }
}
