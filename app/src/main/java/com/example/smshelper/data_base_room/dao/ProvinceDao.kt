package com.example.smshelper.data_base_room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.smshelper.data_base_room.entity.Province_model
import io.reactivex.rxjava3.core.Flowable

@Dao
interface ProvinceDao {

    @Insert
    fun addProvince(provinceModel: Province_model)

    @Query("select * from Province_model")
    fun allProvince(): Flowable<List<Province_model>>

    @Query("select * from Province_model where province_name = :arg")
    fun getByName(arg: String): Province_model

    @Query("select * from Province_model where date_id = :con")
    fun getByIdListProvince(con: Int): Flowable<List<Province_model>>

    @Query("select * from Province_model where date_id = :con")
    fun getDateByIdListProvince(con: Int): List<Province_model>

}