package com.rizkydian.internalmeet.ui.users

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rizkydian.internalmeet.R
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragment : Fragment() {

    companion object {
        fun newInstance() = UserFragment()
    }

    private lateinit var userViewModel: UserViewModel
    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onStart() {
        super.onStart()
        userAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        userAdapter.stopListening()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        userAdapter = UserAdapter(userViewModel.getUsers())
        userAdapter.notifyDataSetChanged()
        rv_user.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(this@UserFragment.requireContext())
            setHasFixedSize(true)
        }
    }

}