package com.nextforce.jetservedriver.splash

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.nextforce.jetservedriver.R
import com.nextforce.jetservedriver.common.base.BaseFragment
import com.nextforce.jetservedriver.common.utils.permissions.Permission
import com.nextforce.jetservedriver.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment() {

    private lateinit var binding: FragmentSplashBinding
    // private val viewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPermissionManager(this)
    }

    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT >= 32) { // for notification permission only
            askForPermissions(
                true,
                getString(R.string.no_notification_permission),
                R.drawable.ic_back,
                Permission.Notification
            )
        }
        // else
        // playStoreCheckForUpdate()
    }

    override fun onResume() {
        super.onResume()
        Handler(Looper.getMainLooper()).postDelayed({
            // if need to call api check version to make update to version app
            // navigate() -> if need to check to user to auto login
            findNavController().navigate(R.id.action_splash_to_home)
        }, 0)
    }

    private fun handlePermissions() {
        permissionResult.observe(viewLifecycleOwner) {
            it.peekContent().let { permission ->
                when (permission) {
                    // Permission.Notification -> playStoreCheckForUpdate()
                    else -> {
                        Log.d("myDebugData", " Elseeeeeeeee ")
                    }
                }
            }
        }
    }
}
