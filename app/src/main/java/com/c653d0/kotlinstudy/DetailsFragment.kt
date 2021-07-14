package com.c653d0.kotlinstudy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class DetailsFragment : Fragment() {
    var animePicture:ImageView ?= null
    var animeTitle:TextView ?= null
    var animeIntroduction:TextView ?= null
    var chooseNumber:RecyclerView ?= null

    var animationId:String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val layoutInflater = LayoutInflater.from(requireContext())
        val view = layoutInflater.inflate(R.layout.fragment_details,container,false)

        animePicture = view.findViewById(R.id.animePicture)
        animeTitle = view.findViewById(R.id.animeTitle)
        animeIntroduction = view.findViewById(R.id.animeIntroduction)
        chooseNumber = view.findViewById(R.id.chooseNumber)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        animationId = requireArguments().getString("resId")!!
        animeTitle!!.text =requireArguments().getString("resTitle")
        animeIntroduction!!.text = requireArguments().getString("resIntroduction")

        val mAdapter:PlayListAdapter = PlayListAdapter(requireActivity())

        chooseNumber.apply {
            this!!.layoutManager = GridLayoutManager(requireContext(),
                5,GridLayoutManager.VERTICAL,false)
            this.adapter = mAdapter
        }

        GetYingHuaData.getPlayList(animationId,requireContext(),requireActivity()).observe(requireActivity(),
            Observer {
                mAdapter.list = it
                mAdapter.notifyDataSetChanged()
            })


    }
}