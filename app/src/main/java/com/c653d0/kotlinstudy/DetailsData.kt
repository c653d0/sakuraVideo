package com.c653d0.kotlinstudy

class DetailsData(
    private val animeTitle: String,
    private val animePic: String,
    private val animeIntroduction: String
) {

    constructor():this("","","")

    fun getAnimeTitle() = animeTitle
    fun getAnimePic() = animePic
    fun getAnimeIntroduction() = animeIntroduction
}