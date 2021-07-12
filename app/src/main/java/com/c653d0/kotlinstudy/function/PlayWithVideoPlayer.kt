package com.c653d0.kotlinstudy.function

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.c653d0.kotlinstudy.GetYingHuaData
import com.c653d0.kotlinstudy.MyStringRequest
import org.jsoup.Jsoup


class PlayWithVideoPlayer() {
    private val TAG = "playerTest"

    companion object{

        @JvmStatic
        fun usePlayer(basicUrl:String,context: Context,owner: LifecycleOwner){

            val html:MutableLiveData<String> = GetYingHuaData.getHtmlFromUrl(basicUrl,context)

            html.observe(owner, Observer {
                val doc = Jsoup.parse(it)
                val video = doc.getElementsByClass("bofang")[0]
                val tmp = video.getElementsByTag("div").attr("data-vid")
                val url: Uri = Uri.parse(tmp.subSequence(0,tmp.length-4).toString())

                val intent:Intent = Intent(Intent.ACTION_VIEW)
                intent.setDataAndType(url,"video/*")
                context.startActivity(intent)
            })


        }
    }

}

