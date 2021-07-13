package com.c653d0.kotlinstudy

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SearchPageFragment : Fragment() {

    var mSearchView:SearchView ?= null
    var searchResultContent:RecyclerView ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search_page,container,false)

        mSearchView = view.findViewById(R.id.searchPageSearchView)
        searchResultContent = view.findViewById(R.id.searchResultList)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //跳转到这个fragment自动展开
        mSearchView!!.isIconified = false

        //搜索框参数设置
        mSearchView.apply {

            //设置搜索框展开时是否显示提交按钮，可不显示
            this!!.isSubmitButtonEnabled = true
            //让键盘的回车键设置成搜索
            imeOptions = EditorInfo.IME_ACTION_SEARCH
            //获取焦点
            isFocusable = true
            requestFocusFromTouch()
            //设置提示词
            queryHint = "请输入文字"
        }

        //点击搜索框也能展开
        mSearchView!!.setOnClickListener(View.OnClickListener {
            mSearchView!!.isIconified = false
        })

        var adapter:SearchDataAdapter = SearchDataAdapter()


        searchResultContent.apply {
            this!!.layoutManager = LinearLayoutManager(requireContext())
            this!!.adapter = adapter

            this.addItemDecoration(DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL))
        }

        // 设置搜索文本监听
        mSearchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            // 当点击搜索按钮时触发该方法
            override fun onQueryTextSubmit(query: String?): Boolean {

                Log.d("searchTest", "onQueryTextSubmit: \n 搜索:$query")
                val url = "http://www.yhdm.so/search/$query/"
                val resultList = GetYingHuaData.getSearchResult(url,requireContext(),requireActivity())



                resultList.observe(requireActivity(), Observer {
                    Log.d("RVApply", "onQueryTextSubmit: ")
                    //TODO("处理搜索的结果")


                    searchResultContent.apply {

                        adapter.setResultList(it)
                        adapter.notifyDataSetChanged()
                    }

                })

                //清除焦点，收软键盘
                mSearchView!!.clearFocus()

                return false
            }

            // 当搜索内容改变时触发该方法
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }
}