package com.example.smshelper.data_base_room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Date_model(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var date: String
)