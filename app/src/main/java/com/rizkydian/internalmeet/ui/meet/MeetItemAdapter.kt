package com.rizkydian.internalmeet.ui.meet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rizkydian.remindmeet.R
import com.rizkydian.remindmeet.data.Meet
import kotlinx.android.synthetic.main.card_meet.view.*

class MeetItemAdapter(private val dataList: List<Meet>) :
    RecyclerView.Adapter<MeetItemAdapter.ViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(container: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(container.context)
            .inflate(R.layout.card_meet, container, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: Meet) {
            Glide.with(itemView.context).load("https://png2.cleanpng.com/sh/f59c42c36bc2e1919abc8121d37532c4/L0KzQYm4UMI3N6t4kZH0aYP2gLBuTf5ifJp0htN1LXXwcr3sjb1wbl5uhtZ4bnX2ebK0kCluapDxRdlqcoXncX68gvQ0Pmk4eqtsN0XpSHA4VsgxO2I5SaMANEC4SIK7U8U3PGEARuJ3Zx==/kisspng-national-emblem-of-indonesia-symbol-garuda-5bd3683b9c75f8.1680314115405814356409.png"
            ).centerCrop().into(itemView.iv_icon)

            itemView.tv_title.text = data.name
            itemView.tv_role.text = data.place
            itemView.setOnClickListener { onItemClickCallback?.onItemClicked(data) }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Meet)
    }

}