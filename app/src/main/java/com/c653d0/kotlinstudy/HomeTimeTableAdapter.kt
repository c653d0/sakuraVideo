package com.c653d0.kotlinstudy

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.annotations.NotNull

class  HomeTimeTableAdapter(viewModel:MyViewModel) : RecyclerView.Adapter<HomeTimeTableAdapter.TimeTableTileViewHolder>() {
    private val TAG = "adapterTest"
    private val TAG1 = "adapterContentTest"

    private val viewModel = viewModel
    private var allFanJvList = FanJvLinkList()
    private var head: FanJvLinkList? = null

    public fun setAllFanJvList(list:FanJvLinkList){
        this.allFanJvList = list
        this.head = list.next
    }

    public fun getAnimationListFromLinkList():ArrayList<FanJvLinkList>{
        var head = allFanJvList.next
        val list = ArrayList<FanJvLinkList>()
        while(head != null){
            list.add(head)
            head = head.next
        }
        return list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeTableTileViewHolder {
        val layoutInflater:LayoutInflater = LayoutInflater.from(parent.context)
        val view  = layoutInflater.inflate(R.layout.item_fanjv_shijianbiao,parent,false)
        return TimeTableTileViewHolder(view)
    }

    override fun getItemCount(): Int {
        var head = allFanJvList.next
        var num = 0
        while(head != null){
            num++
            head = head.next
        }
        Log.d(TAG, "getItemCount: Num == $num")
        return num
    }

    override fun onBindViewHolder(holder: TimeTableTileViewHolder, position: Int) {
        /**
         * 修改传入adapter的数据，修改为链表
         * 遍历链表对textview赋值
         */

        val list = getAnimationListFromLinkList()
        val fanJv = list[position]
        var res = "${position+1}."+fanJv.getTitle()


        holder.fanJvText.text = res
        holder.latestEpisode.text = fanJv.getEpisode()

        Log.d(TAG1, "onBindViewHolder: "+"episode:"+fanJv.getEpisode() + "    res = "+res)

        holder.fanJvText.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(fanJv?.getEpisodeHref())
            holder.itemView.context.startActivity(intent)
            Toast.makeText(holder.itemView.context,fanJv?.getEpisodeHref(),Toast.LENGTH_SHORT).show()
        })
    }

    class TimeTableTileViewHolder(@NotNull itemView:View) : RecyclerView.ViewHolder(itemView) {
        val fanJvText:TextView = itemView.findViewById(R.id.item_fanjv_text)
        val latestEpisode:TextView = itemView.findViewById(R.id.latestEpisode)
    }
}
