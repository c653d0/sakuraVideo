package com.c653d0.kotlinstudy

class SearchPageList(
    private val title: String,
    private val pictureUrl: String,
    private val latestEpisode: String,
    private val introduction: String,
    private val id: String
) {
    var next:SearchPageList ?= null

    constructor():this("","","","","")

    fun getTitle() = title
    fun getPictureUrl() = pictureUrl
    fun getLatestEpisode() = latestEpisode
    fun getIntroduction() = introduction
    fun getId() = id
}