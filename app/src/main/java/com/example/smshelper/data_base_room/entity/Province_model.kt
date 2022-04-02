package com.example.smshelper.data_base_room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(entity = Date_model::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("date_id"),
    onDelete = CASCADE)]
)
data class Province_model(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var province_name: String,
    var date_id: Int
) {
}