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

            //Log.d("someError", "usePlayer: ${basicUrl}")

            val html:MutableLiveData<String> = GetYingHuaData.getHtmlFromUrl(basicUrl,context)


            html.observe(owner, Observer {
                val doc = Jsoup.parse(it)
                val includeHref = doc.body().getElementsByTag("script")[4].toString()
                val m3u8Url = getUrl(includeHref)
                Log.d(TAG, "usePlayer: $m3u8Url")
                val url = Uri.parse(m3u8Url)
                Log.d(TAG, "usePlayer: $url")

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
                intent.setDataAndType(url,"video/*")
                context.startActivity(intent)
            })


        }

        fun getUrl(string: String) : String{
            val url = string.toCharArray()
            val result = StringBuilder()
            var index = 0
            repeat(url.size){ i ->
                if(url[i] == '"'){
                    if(url[i+1] == 'u'){
                        if(url[i+2] == 'r'){
                            if(url[i+3] == 'l'){
                                if(url[i+4] == '"'){
                                    if(url[i+5] == ':'){
                                        if(url[i+6] == '"'){
                                            index = i+7
                                            while (url[index] != '"'){
                                                if(url[index] != '\\'){
                                                    result.append(url[index])
                                                }
                                                index++
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            return result.toString()
        }

    }

}

