package com.c653d0.kotlinstudy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.annotations.NotNull

class SearchDataAdapter : RecyclerView.Adapter<SearchDataAdapter.SearchDataViewHolder>() {
    private var allSearchResultList:ArrayList<SearchPageList> = ArrayList()

    fun setResultList(list:ArrayList<SearchPageList>){
        this.allSearchResultList = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchDataViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_search_result,parent,false)
        return SearchDataViewHolder(view)
    }

    override fun getItemCount(): Int {
        return allSearchResultList.size
    }

    override fun onBindViewHolder(holder: SearchDataViewHolder, position: Int) {
        holder.searchTitle.text = allSearchResultList[position].getTitle()
        holder.searchIntroduction.text = holder.itemView.context.getString(R.string.introduction)+allSearchResultList[position].getIntroduction()
        holder.searchEpisode.text = allSearchResultList[position].getLatestEpisode()
    }

    class SearchDataViewHolder(@NotNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        val searchPicture:ImageView = itemView.findViewById(R.id.searchPicture)
        val searchTitle:TextView = itemView.findViewById(R.id.searchTitle)
        val searchEpisode:TextView = itemView.findViewById(R.id.searchEpisode)
        val searchIntroduction:TextView = itemView.findViewById(R.id.searchIntroduction)
    }
}