package com.nextforce.jetservedriver.common.utils.permissions

import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.lang.ref.WeakReference

class PermissionManager private constructor(private val fragment: WeakReference<Fragment>) {
    companion object {
        fun from(fragment: Fragment) = PermissionManager(WeakReference(fragment))
    }

    private val requiredPermissions = mutableListOf<Permission>()
    private var rationale: String? = null
    private var rationaleImage: Int? = null
    private var isRequired: Boolean = false
    private var isRationalShown: Boolean = false
    private var callback: (Boolean) -> Unit = {}
    private var detailedCallback: (Map<Permission, Boolean>) -> Unit = {}
    private lateinit var askPermissionDialog: AskPermissionDialog

    private val askPermissionCb: () -> Unit = {
        askPermissionDialog.dismiss()
        requestPermissions()
    }

    private val permissionCheck =
        fragment.get()?.registerForActivityResult(RequestMultiplePermissions()) { grantResults ->
            sendResultAndCleanUp(grantResults)
        }

    fun rationale(description: String, image: Int): PermissionManager {
        rationale = description
        rationaleImage = image
        return this
    }

    fun request(vararg permission: Permission): PermissionManager {
        requiredPermissions.addAll(permission)
        return this
    }

    fun isRequired(isRequired: Boolean): PermissionManager {
        this.isRequired = isRequired
        return this
    }

    fun checkPermissionIsRequired(): Boolean = isRequired
    fun checkPermissionIsRationalShown(): Boolean = isRationalShown

    fun checkPermission(callback: (Boolean) -> Unit) {
        this.callback = callback
        handlePermissionRequest()
    }

    fun checkDetailedPermission(callback: (Map<Permission, Boolean>) -> Unit) {
        this.detailedCallback = callback
    }

    private fun handlePermissionRequest() {
        fragment.get()?.let { fragment ->
            when {
                areAllPermissionsGranted(fragment) -> sendPositiveResult()
                shouldShowPermissionRationale(fragment) -> displayRationale(fragment)
                else -> requestPermissions()
            }
        }
    }

    private fun displayRationale(fragment: Fragment) {
        askPermissionDialog = AskPermissionDialog(askPermissionCb, rationale!!, rationaleImage!!)
        askPermissionDialog.show(
            fragment.requireActivity().supportFragmentManager,
            "AskPermissionDialog"
        )
        isRationalShown = true
    }

//     fun showRationale(fragment: Fragment) { // show when user clicked don't ask again and close app and open it again
//            askPermissionDialog = AskPermissionDialog(askPermissionCb, rationale!!, rationaleImage!!)
//            askPermissionDialog.show(
//                fragment.requireActivity().supportFragmentManager,
//                "AskPermissionDialog"
//            )
//            isRationalShown = true
//    }

    private fun sendPositiveResult() {
        sendResultAndCleanUp(getPermissionList().associateWith { true })
    }

    private fun sendResultAndCleanUp(grantResults: Map<String, Boolean>) {
        callback(grantResults.all { it.value })
        detailedCallback(grantResults.mapKeys { Permission.from(it.key) })
        if (!checkPermissionIsRequired()) cleanUp()
    }

    private fun cleanUp() {
        requiredPermissions.clear()
        rationale = null
        rationaleImage = null
        callback = {}
        isRationalShown = false
        detailedCallback = {}
    }

    private fun requestPermissions() {
        permissionCheck?.launch(getPermissionList())
    }

    private fun areAllPermissionsGranted(fragment: Fragment) =
        requiredPermissions.all { it.isGranted(fragment) }

    private fun shouldShowPermissionRationale(fragment: Fragment) =
        requiredPermissions.any { it.requiresRationale(fragment) }

    private fun getPermissionList() =
        requiredPermissions.flatMap { it.permissions.toList() }.toTypedArray()

    private fun Permission.isGranted(fragment: Fragment): Boolean {
        return permissions.all { hasPermission(fragment, it) }
    }

    private fun Permission.requiresRationale(fragment: Fragment) =
        permissions.any { fragment.shouldShowRequestPermissionRationale(it) }

    private fun hasPermission(fragment: Fragment, permission: String) =
        ContextCompat.checkSelfPermission(
            fragment.requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
}
