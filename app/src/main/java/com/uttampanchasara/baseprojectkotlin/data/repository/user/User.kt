package com.uttampanchasara.baseprojectkotlin.data.repository.user

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "User")
data class User(
        @PrimaryKey
        val email: String,
        val name: Boolean)