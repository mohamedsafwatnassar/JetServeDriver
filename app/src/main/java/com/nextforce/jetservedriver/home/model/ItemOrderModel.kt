package com.nextforce.jetservedriver.home.model

data class ItemOrderModel(
    var _id: Int? = null,
    var status: String = "",
    var date: String = "",
    var orderImage: String = "",
    var destination: String = "",
    var feesPrice: Double = 0.0,
    var price: Double = 0.0,
    var clientPhone: String = "",
    var zoneName: String = "",
    var zoneId: String = "",
    var zonePrice: Double? = null
)
