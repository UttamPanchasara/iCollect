package com.uttampanchasara.network.response

import com.google.gson.annotations.SerializedName

data class Result(
        @SerializedName("href")
        val href: String,
        @SerializedName("ingredients")
        val ingredients: String,
        @SerializedName("thumbnail")
        val thumbnail: String,
        @SerializedName("title")
        val title: String
)