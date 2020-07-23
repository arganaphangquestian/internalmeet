package com.rizkydian.internalmeet.ui.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.rizkydian.internalmeet.R
import com.rizkydian.internalmeet.data.User
import kotlinx.android.synthetic.main.card_user.view.*

class UserAdapter(ops: FirestoreRecyclerOptions<User>) :
    FirestoreRecyclerAdapter<User, UserAdapter.ViewHolder>(ops) {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_user, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: User) {
        holder.bind(model)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: User) {
            itemView.iv_icon.load("https://png2.cleanpng.com/sh/f59c42c36bc2e1919abc8121d37532c4/L0KzQYm4UMI3N6t4kZH0aYP2gLBuTf5ifJp0htN1LXXwcr3sjb1wbl5uhtZ4bnX2ebK0kCluapDxRdlqcoXncX68gvQ0Pmk4eqtsN0XpSHA4VsgxO2I5SaMANEC4SIK7U8U3PGEARuJ3Zx==/kisspng-national-emblem-of-indonesia-symbol-garuda-5bd3683b9c75f8.1680314115405814356409.png") {
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