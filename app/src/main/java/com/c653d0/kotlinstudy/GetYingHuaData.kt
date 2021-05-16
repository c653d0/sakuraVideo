package com.c653d0.kotlinstudy

import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.select.Elements

class GetYingHuaData {
    val TAG: String = "getYingHuaData"

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
        val uls = doc.getElementsByClass("tlist")[0]
        val elements = uls.getElementsByTag("ul")  //一周更新，包含所有ul标签
        //val allUl = elements[elements.size - 1]     //当天更新，处于所有标签的末尾，包含当天的所有li标签
        //Log.d(TAG, "onCreate: getThings\n$allUl")
        //val allLi = allUl.getElementsByTag("li")

        for (element in elements){
            val allLi = element.getElementsByTag("li")
            val tmp:FanJvLinkList = getDataFromElements(allLi)
            fanJvTimeList.add(tmp)
        }

        //Log.d("elements", "$elements")
        //Log.d("allUl", "$allUl")
        Log.d("listData", fanJvTimeList[0].next!!.getTitle());

        //getDataFromElements(allLi)
        return fanJvTimeList

    }

    //从一天的li标签中返回链表
    private fun getDataFromElements(elements: Elements):FanJvLinkList {
        val head: FanJvLinkList = FanJvLinkList();
        var start: FanJvLinkList = head

        //对数据提取
        for (li in elements) {
            //val contents = li.getElementsByTag("a")
            /*for (content in contents){
                val href = content.attr("href")
                val title:String
                if ("" == content.attr("title")){
                    title = content.ownText()
                }else{
                    title =content.attr("title")
                }


                Log.d("TAG", "onCreate:\n$href + $title")
            }*/
            val episode = li.getElementsByTag("a")[0].ownText();
            val episodeHref = "http://www.yhdm.io/" + li.getElementsByTag("a")[0].attr("href");
            val title = li.getElementsByTag("a")[1].attr("title");
            val titleHref = "http://www.yhdm.io/" + li.getElementsByTag("a")[1].attr("href");

            val node: FanJvLinkList = FanJvLinkList(title, titleHref, episode, episodeHref)
            start.next = node
            start = start.next!!
            Log.d(TAG, "\n名字：$title \n链接：$titleHref \n最新一集：$episode\n最新一集链接：$episodeHref")
            //textView.text = "名字：$title \n链接：$titleHref \n最新一集：$episode\n最新一集链接：$episodeHref\n\n"+textView.text
            //res += "名字：$title \n链接：$titleHref \n最新一集：$episode\n最新一集链接：$episodeHref\n\n"
        }

        return head
    }

}