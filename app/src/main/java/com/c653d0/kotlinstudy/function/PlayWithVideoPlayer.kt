package com.c653d0.kotlinstudy.function

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.webkit.WebView
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.c653d0.kotlinstudy.GetYingHuaData
import com.c653d0.kotlinstudy.decrypt.UrlDecrypt
import org.jsoup.Jsoup
import java.util.regex.Pattern


class PlayWithVideoPlayer() {
    private val TAG = "playerTest"


    companion object{
        private val TAG = "playerTest"

        val m3u8Parse = "https://jianghu.live2008.com/xin/?url="

        @JvmStatic
        fun usePlayer(basicUrl:String,context: Context,owner: LifecycleOwner){

            Log.d("someError", "usePlayer: ${basicUrl}")

            val html:MutableLiveData<String> = GetYingHuaData.getHtmlFromUrl(basicUrl,context)


            html.observe(owner, Observer {
                Log.d(TAG, "usePlayer: ${it}")
                val doc = Jsoup.parse(it)
                val includeHref = doc.body().getElementsByTag("script")[4].toString()
                val regex = Regex("(?<=\"url\":\").*(?=\",\"url_next)")
                val findContent = regex.findAll(includeHref)
                var beforeUrl:String = ""
                findContent.forEach {   x->
                    beforeUrl = x.value
                    println(x.value)
                }

                val split = beforeUrl.split('\\')
                val url = StringBuilder()
                repeat(split.size){
                    url.append(split[it])
                }
                Log.d(TAG, "usePlayer: ${url.toString()}")

                //Log.d("testTag", "usePlayer: $url")
                //val tmp = video.getElementsByTag("table")[0].getElementsByTag("iframe").attr("src")
                //val uri = video.attr("src")
                //val video = doc.getElementById("playleft")[0]
                //Log.d("videoLog", "usePlayer: ${video.toString()}")
                //val tmp = video.getElementsByTag("iframe").attr("src")
                //val url:Uri = Uri.parse()
                //val url: Uri = Uri.parse(tmp.subSequence(0,tmp.length-4).toString())

                //println("movBasicUrl usePlayer: $doc")

                val intent:Intent = Intent(Intent.ACTION_VIEW)
                intent.setDataAndType(Uri.parse(url.toString()),"video/*")
                context.startActivity(intent)
            })


        }

    }

}

