package com.c653d0.kotlinstudy.function

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.c653d0.kotlinstudy.GetYingHuaData
import com.c653d0.kotlinstudy.decrypt.UrlDecrypt
import org.jsoup.Jsoup


class PlayWithVideoPlayer() {
    private val TAG = "playerTest"

    companion object{

        @JvmStatic
        fun usePlayer(basicUrl:String,context: Context,owner: LifecycleOwner){

            //Log.d("someError", "usePlayer: ${basicUrl}")

            val html:MutableLiveData<String> = GetYingHuaData.getHtmlFromUrl(basicUrl,context)

            html.observe(owner, Observer {
                val doc = Jsoup.parse(it)
                val includeHref = doc.body().getElementsByClass("Player")[0].getElementsByTag("script")[0].toString()
                val href = includeHref.split(",")[7].split("\"")[3]
                val url = Uri.parse(UrlDecrypt.jtDecrypt(href))
                //Log.d("testTag", "usePlayer: $url")
                //val tmp = video.getElementsByTag("table")[0].getElementsByTag("iframe").attr("src")
                //val uri = video.attr("src")
                //val video = doc.getElementById("playleft")[0]
                //Log.d("videoLog", "usePlayer: ${video.toString()}")
                //val tmp = video.getElementsByTag("iframe").attr("src")
                //val url:Uri = Uri.parse()
                //val url: Uri = Uri.parse(tmp.subSequence(0,tmp.length-4).toString())

                Log.d("movBasicUrl", "usePlayer: $url")

                val intent:Intent = Intent(Intent.ACTION_VIEW)
                intent.setDataAndType(url,"video/*")
                context.startActivity(intent)
            })


        }

    }

}

