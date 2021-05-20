package com.c653d0.kotlinstudy.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.c653d0.kotlinstudy.MyStringRequest
import com.c653d0.kotlinstudy.MyViewModel
import com.c653d0.kotlinstudy.R
import com.c653d0.kotlinstudy.animationTimePagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class AnimationTimePagerFragment : Fragment() {
    var viewPager2:ViewPager2 ?= null
    var pagerTabDay:TabLayout ?= null
    var textTest:TextView ?= null
    var recommendAnimation:RecyclerView ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_animation_home_page, container, false)
        //时间表
        viewPager2 = view.findViewById(R.id.pagerAnimationTime)
        pagerTabDay = view.findViewById(R.id.pagerTabDay)


        textTest = view.findViewById(R.id.textView3)

        //推荐
        recommendAnimation = view.findViewById(R.id.recommendAnimation)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)

        //时间表
        val adapter = animationTimePagerAdapter(viewModel,requireActivity()).apply {
            viewPager2?.adapter = this
        }

        textTest?.text = "\n\n\n\n\n\n\n\n\n\n\n123456\n\n\n\n\n\n\n\n\n\n\n\n\n\n455678\n\n\n\n\n\n\n\n265461\n\n\n"

        TabLayoutMediator(pagerTabDay!!, viewPager2!!){ tab, position ->
            when(position){
                0 -> tab.text = "周一"
                1 -> tab.text = "周二"
                2 -> tab.text = "周三"
                3 -> tab.text = "周四"
                4 -> tab.text = "周五"
                5 -> tab.text = "周六"
                else -> tab.text = "周日"
            }
        }.attach()


        val url = "http://www.yhdm.io/"
        val myQueue = Volley.newRequestQueue(requireContext());
        val stringRequest: MyStringRequest = MyStringRequest(
            Request.Method.GET,
            url,
            Response.Listener {
                viewModel.getTimeTable(it)
            },
            Response.ErrorListener {
                Log.e("TAG", "onCreate: $it");
            }
        )
        myQueue.add(stringRequest)

        viewModel.getTimeTableLiveData().observe(requireActivity(), Observer{
            Log.d("isInCreate", "onActivityCreated: ")
            adapter.submitList(it)
            //adapter.notifyDataSetChanged()
        })


        //推荐
        recommendAnimation.apply {
            this?.layoutManager = LinearLayoutManager(requireContext())

        }
    }
}