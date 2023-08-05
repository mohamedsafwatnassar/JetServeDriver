package com.nextforce.jetservedriver

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.nextforce.jetservedriver.common.base.BaseFragment
import com.nextforce.jetservedriver.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment() {

    private lateinit var binding: FragmentProfileBinding

    private val btnBackCallback: () -> Unit = {
        findNavController().navigate(R.id.action_profile_to_home)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        binding.dailyOrdersCnt.tvText.text = "Daily orders "
        binding.dailyOrdersCnt.tvValue.text = "160"
        binding.totalOrdersCnt.tvText.text = "total orders "
        binding.totalOrdersCnt.tvValue.text = "20"
    }

    private fun initToolbar() {
        binding.toolbar.setTitleString("My Profile")
        binding.toolbar.setTitleCenter(true)
        binding.toolbar.setTitleColor(R.color.black)
        binding.toolbar.useBackButton(
            true,
            btnBackCallback,
            R.drawable.ic_back
        )
    }
}
