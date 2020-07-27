package com.rizkydian.internalmeet.ui.meet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.rizkydian.internalmeet.R
import com.rizkydian.internalmeet.data.Meet
import kotlinx.android.synthetic.main.card_meet.view.*

class MeetItemAdapter(ops: FirestoreRecyclerOptions<Meet>) :
    FirestoreRecyclerAdapter<Meet, MeetItemAdapter.ViewHolder>(ops) {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(container: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(container.context)
            .inflate(R.layout.card_meet, container, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Meet) {
        holder.bind(model)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: Meet) {
            itemView.iv_icon.load(R.drawable.logo) {
                allowHardware(false)
                crossfade(true)
            }
            itemView.tv_title.text = data.name
            itemView.tv_role.text = data.place
            itemView.setOnClickListener { onItemClickCallback?.onItemClicked(data, itemView) }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Meet, itemView: View)
    }

}