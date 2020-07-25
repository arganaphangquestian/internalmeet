package com.rizkydian.internalmeet.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.rizkydian.internalmeet.R
import com.rizkydian.internalmeet.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var profileBinding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        profileBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        profileBinding.apply {
            viewModel = profileViewModel
            lifecycleOwner = this@ProfileFragment
        }
        return profileBinding.root
    }

}