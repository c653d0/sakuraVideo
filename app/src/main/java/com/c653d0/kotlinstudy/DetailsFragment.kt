package com.c653d0.kotlinstudy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.lang.StringBuilder
import android.R.string

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat

import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.MutableLiveData
import com.c653d0.kotlinstudy.decrypt.UrlDecrypt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jsoup.Jsoup
import java.lang.Thread.sleep


class DetailsFragment : Fragment() {
    var animePicture: ImageView? = null
    var animeTitle: TextView? = null
    var animeIntroduction: TextView? = null
    var chooseNumber: RecyclerView? = null
    var downloadButton: Button? = null

    var animationUrl: String = ""

    var allFanJvList: ArrayList<PlayListData> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val layoutInflater = LayoutInflater.from(requireContext())
        val view = layoutInflater.inflate(R.layout.fragment_details, container, false)

        animePicture = view.findViewById(R.id.animePicture)
        animeTitle = view.findViewById(R.id.animeTitle)
        animeIntroduction = view.findViewById(R.id.animeIntroduction)
        chooseNumber = view.findViewById(R.id.chooseNumber)
        downloadButton = view.findViewById(R.id.downloadButton)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        animationUrl = requireArguments().getString("resUrl")!!
        animeTitle!!.text = requireArguments().getString("resTitle")
        animeIntroduction!!.text = requireArguments().getString("resIntroduction")

        Glide.with(requireContext()).load(requireArguments().getString("resPicture"))
            .into(animePicture!!)

        val mAdapter: PlayListAdapter = PlayListAdapter(requireActivity())

        chooseNumber.apply {
            this!!.layoutManager = GridLayoutManager(
                requireContext(),
                5, GridLayoutManager.VERTICAL, false
            )
            this.adapter = mAdapter
        }

        GetYingHuaData.getPlayList(animationUrl, requireContext(), requireActivity())
            .observe(requireActivity(),
                Observer {
                    allFanJvList = it
                    mAdapter.list = it
                    mAdapter.notifyDataSetChanged()
                }
            )
        downloadButton?.setOnClickListener(View.OnClickListener {
            val allUrl = StringBuilder()
            var i = 0;
            for (item in allFanJvList) {
                GetYingHuaData.getRealUrl(item.getMoveUrl(), requireContext(), viewLifecycleOwner)
                    .observe(viewLifecycleOwner, Observer {
                        i++
                        allUrl.append(it + "\n")
                        if (i == allFanJvList.size) {
                            if (allUrl.isEmpty()){
                                Toast.makeText(context,"获取地址失败",Toast.LENGTH_SHORT).show()
                            }else{
                                val cm = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                ClipData.newPlainText("text",allUrl)?.let {
                                    cm.setPrimaryClip(it)
                                }

                                Toast.makeText(context,"已复制全部链接到剪贴板",Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
            }


        })
    }
}