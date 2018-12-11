package com.uttampanchasara.icollect.data.repository.record

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Records")
data class RecordData(
        @SerializedName("createdAt")
        val createdAt: Long,
        @SerializedName("createdDate")
        val createdDate: String,
        @SerializedName("customerAddress")
        val customerAddress: String,
        @PrimaryKey
        @SerializedName("productId")
        val productId: String,
        @SerializedName("customerName")
        val customerName: String,
        @SerializedName("customerNumber")
        val customerNumber: String
)