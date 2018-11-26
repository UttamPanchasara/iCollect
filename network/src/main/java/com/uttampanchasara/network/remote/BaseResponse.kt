package com.uttampanchasara.network.remote

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(

        @field:SerializedName("result")
        val result: T,

        @field:SerializedName("status")
        val status: String
)

open class BaseResult {

    @field:SerializedName("type")
    val type: String? = null

    @field:SerializedName("message")
    val message: String? = null

    @field:SerializedName("code")
    val code: Int? = null
}

