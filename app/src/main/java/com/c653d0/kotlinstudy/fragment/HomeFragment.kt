package com.c653d0.kotlinstudy.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.c653d0.kotlinstudy.HomeTimeTableAdapter
import com.c653d0.kotlinstudy.MyStringRequest
import com.c653d0.kotlinstudy.MyViewModel
import com.c653d0.kotlinstudy.R

class HomeFragment : Fragment() {
    var recyclerViewTimeTable: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerViewTimeTable = view.findViewById(R.id.recyclerViewFanJvTimeTable)
        return view
    }

    //设置adapter，数据
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val viewModel:MyViewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        val adapter = HomeTimeTableAdapter(viewModel,requireActivity())

        //recyclerViewTimeTable = activity?.findViewById<RecyclerView>(R.id.recyclerViewFanJvTimeTable)
        recyclerViewTimeTable.apply {
            this?.layoutManager =LinearLayoutManager(requireContext())
            this?.adapter = adapter
        }


        val url = "http://m.yhdm.so/"
        val myQueue = Volley.newRequestQueue(requireContext());
        val stringRequest: MyStringRequest = MyStringRequest(
            Request.Method.GET,
            url,
            Response.Listener {
                viewModel.getTimeTable(it)
                /*viewModel.getTimeTableLiveData().observe(this, Observer {
                    var res:String = ""
                    var num = 1
                    for (list in it){
                        var start = list.next
                        while (start != null){
                            res += "$num"+ start.getTitle() + "\n"
                            start = start.next
                            num++
                        }
                    }*/

                //Log.d(TAG2, "onCreate: +\n $res")
                //})
                /*val getYinghuaData:GetYingHuaData = GetYingHuaData()
                val res = getYinghuaData.getFanJvTimeTable(it)*/
            },
            Response.ErrorListener {
                Log.e("TAG", "onCreate: $it");
            }
        )
        myQueue.add(stringRequest)

        viewModel.getTimeTableLiveData().observe(requireActivity(), Observer{
            Log.d("isInCreate", "onActivityCreated: ")
            adapter.setAllFanJvList(it[0])
            adapter.notifyDataSetChanged()
        })

    }

}