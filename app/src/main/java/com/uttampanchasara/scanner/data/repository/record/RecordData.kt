package com.uttampanchasara.scanner.data.repository.record

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Records")
data class RecordData(
        @PrimaryKey
        @SerializedName("time")
        val time: Long,
        @SerializedName("address")
        val address: String,
        @SerializedName("code")
        val code: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("number")
        val number: String
)