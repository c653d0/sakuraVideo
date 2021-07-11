package com.c653d0.kotlinstudy.home.timeTable

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.c653d0.kotlinstudy.FanJvLinkList
import com.c653d0.kotlinstudy.HomeTimeTableAdapter
import com.c653d0.kotlinstudy.MyViewModel
import com.c653d0.kotlinstudy.R

class animationTimePagerAdapter(private val viewModel: MyViewModel, private val activit:LifecycleOwner) : ListAdapter<FanJvLinkList, AnimationPagerViewHolder>(
    DiffCallBack
) {

    object DiffCallBack : DiffUtil.ItemCallback<FanJvLinkList>() {
        override fun areItemsTheSame(oldItem: FanJvLinkList, newItem: FanJvLinkList): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: FanJvLinkList, newItem: FanJvLinkList): Boolean {
            return oldItem.next!!.equals(newItem.next)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimationPagerViewHolder {
        LayoutInflater.from(parent.context).inflate(R.layout.pager_item_anima_time_table,parent,false).apply{
            val holder =
                AnimationPagerViewHolder(
                    this
                )
            return holder
        }
    }

    //传入的是arrayList<FanJVLinkLIst>，getItem(position)可以得到当前position的FanJvLinkList
    //直接getItem就可得到头结点，直接遍历填充就行了

    override fun onBindViewHolder(holder: AnimationPagerViewHolder, position: Int) {
        val head = getItem(position)
        val recyclerView = holder.itemView.findViewById<RecyclerView>(R.id.itemPagerRecyclerView)

        val adapter = HomeTimeTableAdapter(viewModel,activit)

        recyclerView.apply {

            this.adapter = adapter
            layoutManager = LinearLayoutManager(holder.itemView.context)
            //TODO 还需要一个adapter

            adapter.setAllFanJvList(head)
            adapter.notifyDataSetChanged()
            /*viewModel.getTimeTableLiveData().observe(activit, Observer{
                    Log.d("isInCreate", "onActivityCreated: ")
                    adapter.setAllFanJvList(it[0])
                    adapter.notifyDataSetChanged()
                })*/
        }


    }

}
class AnimationPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)