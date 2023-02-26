package com.c653d0.kotlinstudy

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.c653d0.kotlinstudy.decrypt.UrlDecrypt
import org.jsoup.Jsoup
import org.jsoup.select.Elements

class GetYingHuaData {
    val TAG: String = "getYingHuaData"
    //private val sakuraUrl = "https://www.sakuradm.tv/"
    private val sakuraUrl = "http://www.dm88.me/"

    /**
     *                  功能
     *
     *从html中获取时间表链接，返回名字+链接 和 最新一集+链接
     */

    /**TODO 获取一周的更新，分别存储一个链表
     * 将七个链表头结点用LIST存储，作为返回值
     */

    /**
     * List结构
     * List{
     *   FanJvLinkList{          //头结点
     *      title
     *      href
     *      next
     *   }
     * }
     */

    //调用这个方法直接从html返回番剧时间表
    fun getFanJvTimeTable(html: String): ArrayList<FanJvLinkList> {

        val fanJvTimeList = ArrayList<FanJvLinkList>()
        var res: String = ""
        //获取到时间表标签
        val doc = Jsoup.parse(html)
        val uls = doc.getElementsByClass("tab-content")[0]
        //print(uls)
        //val elements = uls.getElementsByTag("ul")  //一周更新，包含所有ul标签
        //print(uls.getElementById("week1"))
        //val allUl = elements[elements.size - 1]     //当天更新，处于所有标签的末尾，包含当天的所有li标签
        //Log.d(TAG, "onCreate: getThings\n$allUl")
        //val allLi = allUl.getElementsByTag("li")：

        for (i in 1..7) {
            val aDayData = uls.getElementById("week$i")
            //Log.d(TAG, "getFanJvTimeTable week data: $aDayData")
            val allLi = aDayData.getElementsByTag("li")
            //Log.d(TAG, "getFanJvTimeTable allLi data $i: $allLi")
            val tmp: FanJvLinkList = getDataFromElements(allLi)
            fanJvTimeList.add(tmp)
        }

        //Log.d("elements", "$elements")
        //Log.d("allUl", "$allUl")
        //Log.d("listData", fanJvTimeList[0].next!!.getTitle());

        //getDataFromElements(allLi)
        return fanJvTimeList

    }


    //从一天的li标签中返回链表
    //时间表需要
    private fun getDataFromElements(elements: Elements): FanJvLinkList {
        val head: FanJvLinkList = FanJvLinkList();
        var start: FanJvLinkList = head

        //对数据提取
        for (li in elements) {

            val episode = li.getElementsByTag("a")[0].getElementsByTag("span")[0].ownText();
            val episodeHref = sakuraUrl + li.getElementsByTag("a")[0].attr("href");
            val title = li.getElementsByTag("a")[0].attr("title");
            val titleHref = sakuraUrl + li.getElementsByTag("a")[0].attr("href");

            val node = FanJvLinkList(title, titleHref, episode, episodeHref)
            start.next = node
            start = start.next!!
            Log.d(TAG, "\n名字：$title \n链接：$titleHref \n最新一集：$episode\n最新一集链接：$episodeHref")
            //textView.text = "名字：$title \n链接：$titleHref \n最新一集：$episode\n最新一集链接：$episodeHref\n\n"+textView.text
            //res += "名字：$title \n链接：$titleHref \n最新一集：$episode\n最新一集链接：$episodeHref\n\n"
        }

        return head
    }


    companion object {
        private val sakuraUrl = "http://www.dm88.me/"

        //从URl中获取html
        @JvmStatic
        fun getHtmlFromUrl(url: String, context: Context): MutableLiveData<String> {
            val result: MutableLiveData<String> = MutableLiveData()


            val myQueue = Volley.newRequestQueue(context);
            val stringRequest: MyStringRequest = MyStringRequest(
                Request.Method.GET,
                url,
                {
                    result.value = it
                },
                {
                    Log.e("TAG", "onCreate: $it");
                }
            )
            myQueue.add(stringRequest)

            return result
        }

        @JvmStatic
        //获取搜索结果
        fun getSearchResult(
            url: String,
            context: Context,
            owner: LifecycleOwner
        ): MutableLiveData<ArrayList<SearchPageList>> {

            Log.d("sError", "getSearchResult: $url")

            val result:MutableLiveData<ArrayList<SearchPageList>> = MutableLiveData()

            getHtmlFromUrl(url, context).observe(owner, Observer {
                val doc = Jsoup.parse(it)
                val allList = doc.getElementById("searchList")
                val allLi = allList.getElementsByTag("li")

                //TODO id获取，新的Fragment展示搜索结果

                var title:String =""
                var pictureUrl:String =""
                var latestEpisode:String =""
                var introduction:String =""
                var id:String =""

                val list = ArrayList<SearchPageList>()

                //对数据提取
                for (li in allLi) {

                    title = li.getElementsByTag("a")[0].attr("title")
                    pictureUrl = li.getElementsByTag("a")[0].attr("data-original")
                    latestEpisode = li.getElementsByTag("span")[0].text()
                    introduction = li.getElementsByClass("detail")[0].getElementsByTag("p")[3].text()
                    val href:String = li.getElementsByClass("detail")[0].getElementsByTag("p")[3].getElementsByTag("a").attr("href")
                    id = href

                    Log.d("htmlUlContent", "getSearchResult: " +
                            "\n $title \n $pictureUrl \n $latestEpisode \n $introduction\n $id")
                    val tmp = SearchPageList(title,pictureUrl, latestEpisode, introduction, id)

                    list.add(tmp)
                }

                result.value = list
            })

            return result
        }

        @JvmStatic
        //获取详情页的播放列表
        fun getPlayList(url:String,context: Context,owner: LifecycleOwner) : MutableLiveData<ArrayList<PlayListData>>{

            val result:MutableLiveData<ArrayList<PlayListData>> = MutableLiveData()

            var title = ""
            var moveUrl = ""

            getHtmlFromUrl(url, context).observe(owner, Observer {
                val list:ArrayList<PlayListData> = ArrayList()
                val elements = Jsoup.parse(it)
                val ul = elements.getElementById("playlist1")
                val allLi = ul.getElementsByTag("li")

                for (li in allLi){
                    title = li.getElementsByTag("a").text()
                    moveUrl = sakuraUrl+li.getElementsByTag("a").attr("href")

                    list.add(PlayListData(moveUrl, title))

                    Log.d("getPlayList", "getPlayList: $title + $moveUrl")
                }

                result.value = list
            })


            return result
        }

        @JvmStatic
        //获取标题，图片，简介信息
        fun getInfo(url:String,context: Context,owner: LifecycleOwner):MutableLiveData<DetailsData>{
            val result = MutableLiveData<DetailsData>()

            getHtmlFromUrl(url,context).observe(owner, Observer {
                val elements = Jsoup.parse(it)
                val picture = elements.getElementsByClass("myui-content__thumb")[0].getElementsByTag("img").attr("src")
                val title = elements.getElementsByClass("myui-content__detail")[0].getElementsByTag("h1").text()
                val introduction = elements.getElementsByClass("myui-panel_bd")[0].getElementsByTag("span")[0].text()
                val tmp = DetailsData(title,picture,introduction)
                result.value = tmp
            })

            return result
        }

        fun getRealUrl(url:String, context: Context, owner: LifecycleOwner) : MutableLiveData<String> {
            val result = MutableLiveData<String>()

            val html: MutableLiveData<String> = GetYingHuaData.getHtmlFromUrl(url, context)

            html.observe(owner, Observer {
                val doc = Jsoup.parse(it)
                val includeHref = doc.body()
                    .getElementsByClass("Player")[0].getElementsByTag("script")[0].toString()
                val href = includeHref.split(",")[7].split("\"")[3]
                val url = UrlDecrypt.jtDecrypt(href)
                result.value = url
            })
            return result
        }
    }
}