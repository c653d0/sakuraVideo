package com.c653d0.kotlinstudy.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.c653d0.kotlinstudy.GetYingHuaData
import com.c653d0.kotlinstudy.MyStringRequest
import com.c653d0.kotlinstudy.MyViewModel
import com.c653d0.kotlinstudy.R
import com.c653d0.kotlinstudy.home.timeTable.animationTimePagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class AnimationTimePagerFragment : Fragment() {
    //时间表控件
    var viewPager2: ViewPager2? = null
    var pagerTabDay: TabLayout? = null


    var textTest: TextView? = null
    var recommendAnimation: RecyclerView? = null

    //搜索控件
    var searchImageButton: ImageView? = null

    private var rootView:View ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if (rootView == null){
            rootView = inflater.inflate(R.layout.fragment_animation_home_page, container, false)
        }

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)


        //时间表
        viewPager2 = requireView().findViewById(R.id.pagerAnimationTime)
        pagerTabDay = requireView().findViewById(R.id.pagerTabDay)

        //搜索
        searchImageButton = requireView().findViewById(R.id.searchClick)


        //搜索
        searchImageButton?.setOnClickListener {
            val controller: NavController = Navigation.findNavController(it)
            controller.navigate(R.id.action_animationTimePagerFragment_to_searchPageFragment)
        }


        //时间表
        val adapter = animationTimePagerAdapter(
            viewModel,
            requireActivity()
        ).apply {
            viewPager2?.adapter = this
        }
        //缓存7页避免重复加载
        viewPager2!!.offscreenPageLimit = 7

        TabLayoutMediator(pagerTabDay!!, viewPager2!!) { tab, position ->
            when (position) {
                0 -> tab.text = "周一"
                1 -> tab.text = "周二"
                2 -> tab.text = "周三"
                3 -> tab.text = "周四"
                4 -> tab.text = "周五"
                5 -> tab.text = "周六"
                else -> tab.text = "周日"
            }
        }.attach()


        //val url = "http://www.yhdm.so/"
        val url = "https://www.sakuradm.tv/"
        val myQueue = Volley.newRequestQueue(requireContext())
        val stringRequest: MyStringRequest = MyStringRequest(
            Request.Method.GET,
            url,
            {
                Log.d("Html", "onActivityCreated: ${it}")
                viewModel.getTimeTable(it)
            },
            {
                Log.e("TAG", "onCreate: $it");
            }
        )
        myQueue.add(stringRequest)

        viewModel.getTimeTableLiveData().observe(requireActivity(), Observer {
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