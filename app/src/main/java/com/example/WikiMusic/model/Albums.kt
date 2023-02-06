package com.example.WikiMusic.model

import com.google.gson.annotations.SerializedName

data class Albums(
    @SerializedName("@attr") val attr: Attr,
    val album: List<Album>
)