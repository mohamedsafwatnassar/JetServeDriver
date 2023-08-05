package com.nextforce.jetservedriver.common.utils

import artifact.signals_bus.contract.Navigational
import artifact.signals_bus.contract.Signal as SignalContract

sealed class Signal(override var signature: String? = null) : SignalContract

object Load : Signal()
object StopLoading : Signal()

sealed class Navigate : Signal(), Navigational {
    object LoginSuccess : Signal()

    object GetUserSuccess : Signal()
    object GetUserFailed : Signal()
    object UpdateUserSuccess : Signal()
    object SignOutSuccess : Signal()

    object AddOrderSuccess : Signal()
}

sealed class SomethingWentWrong : Signal() {
    object Validation : SomethingWentWrong()
    object ConnectionFailure : SomethingWentWrong()
    object ErrorMessage : SomethingWentWrong()
    object Unknown : SomethingWentWrong()
    object PhoneNotValid : SomethingWentWrong()
    object InvalidPassword : SomethingWentWrong()
}

sealed class Informative : Signal()

sealed class UI : Signal()

sealed class PasswordValidation : Signal() {
    object NotMatch : PasswordValidation()
    object Formatting : PasswordValidation()
}
