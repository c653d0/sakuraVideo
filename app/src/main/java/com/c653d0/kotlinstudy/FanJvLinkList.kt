package com.c653d0.kotlinstudy

class FanJvLinkList (
    private val title: String,
    private val titleHref:String,
    private val episode:String,
    private val episodeHref:String){

    var next:FanJvLinkList? = null

    constructor():this("","","","")

    fun getTitle() = title
    fun getTitleHref() = titleHref
    fun getEpisode() = episode
    fun getEpisodeHref() = episodeHref
}