package com.rizkydian.internalmeet.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.rizkydian.remindmeet.R
import com.rizkydian.remindmeet.databinding.FragmentProfileBinding
import com.rizkydian.remindmeet.ui.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private val profileViewModel by viewModel<ProfileViewModel>()
    private lateinit var profileBinding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        profileBinding.apply {
            viewModel = profileViewModel
            lifecycleOwner = this@ProfileFragment
        }
        return profileBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        action()
    }

    private fun action() {
        bn_general.setOnClickListener {

        }
        bn_logout.setOnClickListener {
            this.requireActivity().startActivity(Intent(this.requireContext(), LoginActivity::class.java))
            this.requireActivity().finish()
        }
    }

}