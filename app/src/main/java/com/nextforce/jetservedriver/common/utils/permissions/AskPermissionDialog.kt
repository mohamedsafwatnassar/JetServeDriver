package com.nextforce.jetservedriver.common.utils.permissions

import android.app.Dialog
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.nextforce.jetservedriver.R
import com.nextforce.jetservedriver.databinding.AskPermissionDialogBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AskPermissionDialog(
    private val grantPermissionCb: () -> Unit,
    private val rational: String,
    private val image: Int
) : DialogFragment() {

    // binding
    private var _binding: AskPermissionDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (dialog != null && dialog!!.window != null) {
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
            dialog!!.window!!.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
            )
        }
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AskPermissionDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog!!.setCanceledOnTouchOutside(false)

        // set curved edge for timer dialog
        dialog!!.window!!.setBackgroundDrawableResource(R.drawable.bg_dialog_confirm_cancel_trip)
        initView()

        btnListener()
    }

    private fun initView() {
        binding.txtDescDialog.text = rational
        binding.imgValidation.setImageResource(image)
    }

    private fun btnListener() {
        binding.btnCancelTrip.setOnClickListener {
            dialog?.dismiss()
            grantPermissionCb.invoke()
        }
    }

    override fun onStart() {
        super.onStart()
        setDialogStyle(dialog)
    }

    private fun setDialogStyle(dialog: Dialog?) {
        val window = dialog?.window
        val size = Point()
        val display = window?.windowManager?.defaultDisplay
        display?.getSize(size)
        val lp = window!!.attributes
        lp.width = (size.x - dpToPx(64))
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.attributes = lp
        window.setGravity(Gravity.CENTER)
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
