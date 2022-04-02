package com.example.smshelper.data_base_room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(entity = Province_model::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("province_id"),
    onDelete = CASCADE)
    ]
)
data class Workers_model(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String,
    var number: String,
    var message: String,
    var working: Boolean,
    var province_id: Int,
    var date_id_model: Int,
    var sms_position: Int = 0
) {
}