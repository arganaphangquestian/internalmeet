package com.rizkydian.internalmeet.ui.meet

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rizkydian.remindmeet.R
import com.rizkydian.remindmeet.data.Meet
import com.rizkydian.remindmeet.ui.meetadd.MeetAddActivity
import kotlinx.android.synthetic.main.card_meet_pager.view.*

class MeetAdapter : RecyclerView.Adapter<MeetAdapter.ViewHolder>() {
    private lateinit var dataList: List<List<Meet>?>

    override fun onCreateViewHolder(container: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(container.context)
            .inflate(R.layout.card_meet_pager, container, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    fun setData(data: List<List<Meet>?>) {
        dataList = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: List<Meet>?) {
            if (data != null) {
                val itemAdapter = MeetItemAdapter(data)
                itemView.rv_meet.apply {
                    layoutManager = LinearLayoutManager(itemView.context)
                    this.adapter = itemAdapter
                }
                itemAdapter.setOnClickCallback(object : MeetItemAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: Meet) {
                        val intent = Intent(itemView.context, MeetAddActivity::class.java)
                        intent.putExtra("id", data.id)
                        itemView.context.startActivity(intent)
                    }

                })

            }
        }
    }

}