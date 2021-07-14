package com.c653d0.kotlinstudy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.c653d0.kotlinstudy.function.PlayWithVideoPlayer
import org.jetbrains.annotations.NotNull

class PlayListAdapter(owner: LifecycleOwner) : RecyclerView.Adapter<PlayListAdapter.PlayListViewHolder>() {

    var list:ArrayList<PlayListData> = ArrayList()
    var owner:LifecycleOwner = owner

    fun setPlayListData(list:ArrayList<PlayListData>){
        this.list = list
    }

    fun setLifecycleOwner(owner: LifecycleOwner){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayListViewHolder {
        val layoutInflater:LayoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_play_list,parent,false)
        return PlayListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PlayListViewHolder, position: Int) {
        holder.title.text = list[position].getTitle()

        holder.itemView.setOnClickListener {
            PlayWithVideoPlayer.usePlayer(list[position].getMoveUrl(),holder.itemView.context,owner)
        }
    }



    class PlayListViewHolder(@NotNull itemView:View):RecyclerView.ViewHolder(itemView){
        val title:TextView = itemView.findViewById(R.id.playListTitle)
    }
}