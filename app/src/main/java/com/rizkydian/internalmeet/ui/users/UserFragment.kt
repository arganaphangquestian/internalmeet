package com.rizkydian.internalmeet.ui.users

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rizkydian.internalmeet.R
import com.rizkydian.internalmeet.data.User
import com.rizkydian.internalmeet.databinding.FragmentUserBinding
import com.rizkydian.internalmeet.ui.useradd.UserAddActivity
import com.rizkydian.internalmeet.ui.userdetail.UserDetailActivity
import com.rizkydian.internalmeet.ui.useredit.UserEditActivity
import com.rizkydian.internalmeet.utils.SharedPrefs
import com.rizkydian.internalmeet.utils.USERROLE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.card_user.view.*
import kotlinx.android.synthetic.main.fragment_user.*

@AndroidEntryPoint
class UserFragment : Fragment() {

    companion object {
        fun newInstance() = UserFragment()
    }

    private lateinit var userViewModel: UserViewModel
    private lateinit var userAdapter: UserAdapter
    private lateinit var userBinding: FragmentUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        userBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false)
        userBinding.apply {
            viewModel = userViewModel
            lifecycleOwner = this@UserFragment
        }
        return userBinding.root
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
        userAdapter = UserAdapter(userViewModel.getUsers())
        userAdapter.notifyDataSetChanged()
        rv_user.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(this@UserFragment.requireContext())
            setHasFixedSize(true)
        }
        action()
        onFilter()
    }

    private fun action() {
        val intentDetail = Intent(this.requireContext(), UserDetailActivity::class.java)
        val intentEdit = Intent(this.requireContext(), UserEditActivity::class.java)
        userAdapter.setOnClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User, itemView: View) {
                val popUp = PopupMenu(this@UserFragment.requireContext(), itemView.root)
                popUp.inflate(R.menu.context_menu_user_admin)
                popUp.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.menu_context_user_detail -> {
                            intentDetail.putExtra("nip", data.nip)
                            this@UserFragment.requireActivity().startActivity(intentDetail)
                            return@setOnMenuItemClickListener true
                        }
                        R.id.menu_context_user_edit -> {
                            intentEdit.putExtra("nip", data.nip)
                            this@UserFragment.requireActivity()
                                .startActivity(intentEdit)
                            return@setOnMenuItemClickListener true
                        }
                        else -> return@setOnMenuItemClickListener false
                    }
                }

                popUp.show()
            }

        })
        if (SharedPrefs.get(USERROLE, "") as String != "Admin") {
            fab_add.visibility = View.GONE
        }
        fab_add.setOnClickListener {
            startActivity(Intent(this.requireContext(), UserAddActivity::class.java))
        }
    }

    private fun onFilter() {
        userViewModel.position.observe(this.viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                userAdapter.updateOptions(userViewModel.getUsersFilter(it))
            } else {
                userAdapter.updateOptions(userViewModel.getUsers())
            }
            userAdapter.notifyDataSetChanged()
        })
    }

}