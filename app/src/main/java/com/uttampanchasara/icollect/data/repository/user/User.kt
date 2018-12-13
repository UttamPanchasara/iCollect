package com.uttampanchasara.icollect.data.repository.user

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "User")
data class User(
        @PrimaryKey
        val id: Int,
        val userName: String,
        val userEmail: String,
        val userNumber: String,
        val createdAt: String)