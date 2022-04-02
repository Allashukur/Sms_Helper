package com.example.smshelper.data_base_room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.smshelper.data_base_room.entity.Date_model
import io.reactivex.rxjava3.core.Flowable

@Dao
interface DateDao {
    @Insert
    fun addDate(dateModel: Date_model)

    @Query("select * from Date_model")
    fun allDate(): Flowable<List<Date_model>>

    @Query("select * from Date_model where date = :arg")
    fun dateBy(arg: String): Date_model
}