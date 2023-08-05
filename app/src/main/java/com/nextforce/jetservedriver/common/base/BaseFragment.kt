package com.nextforce.jetservedriver.common.base

import android.content.DialogInterface
import android.content.Intent
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nextforce.jetservedriver.R
import com.nextforce.jetservedriver.ViewsManager
import com.nextforce.jetservedriver.common.utils.Event
import com.nextforce.jetservedriver.common.utils.permissions.AskPermissionDialog
import com.nextforce.jetservedriver.common.utils.permissions.Permission
import com.nextforce.jetservedriver.common.utils.permissions.PermissionManager

private const val LOADING_INDICATOR_TAG = "LOADING"

abstract class BaseFragment : Fragment() {

    protected fun showLoading() {
        (requireActivity() as ViewsManager).showLoading()
    }

    protected fun hideLoading() {
        (requireActivity() as ViewsManager).hideLoading()
    }

    open fun showMessage(
        message: String?,
        positiveActionName: String?,
        onPositiveClick: DialogInterface.OnClickListener?,
        negativeText: String?,
        onNegativeClick: DialogInterface.OnClickListener?,
        isCancelable: Boolean
    ): AlertDialog? {
        val builder = AlertDialog.Builder(
            requireContext()
        )
        builder.setMessage(message)
        builder.setPositiveButton(positiveActionName, onPositiveClick)
        builder.setNegativeButton(negativeText, onNegativeClick)
        builder.setCancelable(isCancelable)
        return builder.show()
    }

    private lateinit var permissionManager: PermissionManager
    private val _permissionResult = MutableLiveData<Event<Permission>>()
    val permissionResult: LiveData<Event<Permission>>
        get() = _permissionResult
    private lateinit var askPermissionDialog: AskPermissionDialog

    private val askPermissionCb: (action: String) -> Unit = {
        askPermissionDialog.dismiss()
        openPermissionsSettings(it)
    }

    fun askForPermissions(
        isRequired: Boolean = false,
        rational: String,
        rationaleImage: Int,
        vararg permission: Permission
    ) {
        permissionManager
            .request(*permission)
            .isRequired(isRequired)
            .rationale(rational, rationaleImage)
            .checkPermission { granted ->
                if (granted) {
                    permissionManager.checkDetailedPermission {
                        _permissionResult.value = Event(permission[0])
                    }
                } else {
                    if (permissionManager.checkPermissionIsRequired()) {
                        checkPermissionType(permission[0])
                    }
                    /*  else {
                          if (permissionManager.checkPermissionIsRationalShown()){
                              askForPermissions(
                                  fragment,
                                  isRequired,
                                  rational,
                                  rationaleImage,
                                  *permission
                              )
                          }else {
                              Toast.makeText(requireContext(), "can't access", Toast.LENGTH_SHORT).show()
                               // can't show again
                          }

                          }*/
                }
            }
    }

    private fun checkPermissionType(
        permission: Permission
    ) {
        val rational: String
        val action: String
        val rationalImage: Int
        when (permission) {
            Permission.Notification -> {
                rationalImage = R.drawable.bg_dialog_confirm_cancel_trip
                action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                rational = getString(R.string.permission_denied)
            }
            else -> {
                rationalImage = R.drawable.bg_dialog_confirm_cancel_trip
                action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                rational = getString(R.string.permission_denied)
            }
        }
        displaySettingsDialog(rational, rationalImage, action)
    }

    fun initPermissionManager(fragment: Fragment): PermissionManager {
        if (!this::permissionManager.isInitialized) {
            permissionManager = PermissionManager.from(fragment)
        }
        return permissionManager
    }

    private fun displaySettingsDialog(message: String, image: Int, action: String) {
        askPermissionDialog = AskPermissionDialog({ askPermissionCb(action) }, message, image)
        askPermissionDialog.show(
            this.requireActivity().supportFragmentManager,
            "AskPermissionDialog"
        )
    }

    private fun openPermissionsSettings(action: String) {
        try {
            val intent = Intent(action)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, requireContext().packageName)
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Activity Not Found", Toast.LENGTH_SHORT).show()
        }
    }
}
