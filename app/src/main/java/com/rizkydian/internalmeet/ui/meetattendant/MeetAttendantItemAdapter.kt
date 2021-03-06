package com.rizkydian.internalmeet.ui.meetattendant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import com.rizkydian.internalmeet.R
import com.rizkydian.internalmeet.data.User
import kotlinx.android.synthetic.main.card_user.view.*

class MeetAttendantItemAdapter :
    RecyclerView.Adapter<MeetAttendantItemAdapter.ViewHolder>() {

    private lateinit var dataList : List<User>

    fun setData(data: List<User>) {
        dataList = data
        this.notifyDataSetChanged()
    }

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_user, parent, false)
        )
    }

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: User) {
            itemView.iv_icon.load(R.drawable.ic_profile) {
                allowHardware(false)
                crossfade(true)
            }

            itemView.tv_title.text = data.name
            itemView.tv_role.text = data.role
            itemView.setOnClickListener { onItemClickCallback?.onItemClicked(data) }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

}