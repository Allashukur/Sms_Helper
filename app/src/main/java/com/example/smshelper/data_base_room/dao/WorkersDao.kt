package com.example.smshelper.data_base_room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.smshelper.data_base_room.entity.Workers_model
import io.reactivex.rxjava3.core.Flowable

@Dao
interface WorkersDao {

    @Insert
    fun addWorkers(workersModel: Workers_model)

    @Query("select * from Workers_model where number = :arg")
    fun getByNumber(arg: String): Workers_model

    @Query("select * from Workers_model where province_id = :arg")
    fun getByIdList(arg: Int): Flowable<List<Workers_model>>

    @Query("select * from Workers_model where province_id = :arg")
    fun getByIdList2(arg: Int): List<Workers_model>

    @Query("select * from Workers_model where date_id_model = :arg")
    fun getByProvinceIdList(arg: Int): List<Workers_model>

    @Update
    fun editWorker(workersModel: Workers_model)

}