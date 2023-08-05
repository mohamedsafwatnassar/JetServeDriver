package com.nextforce.jetservedriver.home.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderModel(
    var _id: Int = 0,
    var status: String = "",
    var date: String = "",
    var orderImage: String = "",
    var subCount: String = "",
    var reservedTime: String = "",
    // var totalAmount: String = "",
    var totalFees: String = "",
    var totalPrice: String = "",
    var driverImage: String = "",
    var driverName: String = "",
    var driverPhone: String = ""
) : Parcelable
