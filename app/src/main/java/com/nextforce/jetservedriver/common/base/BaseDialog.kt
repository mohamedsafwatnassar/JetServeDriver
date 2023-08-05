package com.nextforce.jetservedriver.common.base

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import artifact.signals_bus.SignalsReceiver
import artifact.signals_bus.contract.Signal
import com.google.android.material.snackbar.Snackbar
import com.nextforce.jetservedriver.ViewsManager

abstract class BaseDialog : DialogFragment() {
    private lateinit var signalsReceiver: SignalsReceiver<Signal>

    private var loadingShown: Boolean = false
    private lateinit var snackBar: Snackbar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signalsReceiver = SignalsReceiver(viewLifecycleOwner)
    }

    protected fun makeToast(text: String) {
        Snackbar.make(requireView(), text, Snackbar.LENGTH_LONG).show()
    }

    protected fun showLoading() {
        (requireActivity() as ViewsManager).showLoading()
    }

    protected fun hideLoading() {
        (requireActivity() as ViewsManager).hideLoading()
    }

    fun showMessage(
        message: String?,
        posActionName: String?,
        onClickListener: DialogInterface.OnClickListener?,
        isCancelable: Boolean
    ): AlertDialog? {
        val builder = AlertDialog.Builder(requireContext())

        builder.setMessage(message)
        builder.setPositiveButton(posActionName, onClickListener)
        builder.setCancelable(isCancelable)

        return builder.show()
    }
}
