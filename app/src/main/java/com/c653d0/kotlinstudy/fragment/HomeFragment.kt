package com.c653d0.kotlinstudy.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
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
import com.c653d0.kotlinstudy.function.PlayWithVideoPlayer

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        return view
    }

    //设置adapter，数据
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val searchEdit:EditText = requireActivity().findViewById(R.id.searchEdit)
        val searchButton:ImageButton = requireActivity().findViewById(R.id.searchButton)

        searchButton.setOnClickListener {
            if ("" != searchEdit.text.toString()){
                Log.d("searchButton", "onActivityCreated: ${searchEdit.text}")
                //
            }else{
                Toast.makeText(requireContext(),"请输入内容",Toast.LENGTH_SHORT).show()
            }
        }
        val viewModel:MyViewModel = ViewModelProvider(this).get(MyViewModel::class.java)



    }

}