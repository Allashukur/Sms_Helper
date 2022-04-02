package com.example.smshelper.data_base_room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.smshelper.data_base_room.dao.DateDao
import com.example.smshelper.data_base_room.dao.ProvinceDao
import com.example.smshelper.data_base_room.dao.WorkersDao
import com.example.smshelper.data_base_room.entity.Date_model
import com.example.smshelper.data_base_room.entity.Province_model
import com.example.smshelper.data_base_room.entity.Workers_model

@Database(entities = [Date_model::class, Province_model::class, Workers_model::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun getDateDao():DateDao
    abstract fun getProvinceDao():ProvinceDao
    abstract fun getWorekersDao():WorkersDao

    companion object {
        private var instanse: AppDataBase? = null

        @Synchronized
        fun getInstanse(context: Context): AppDataBase {
            if (instanse == null) {
                instanse = Room.databaseBuilder(context, AppDataBase::class.java, "Sms Helper")
                    .allowMainThreadQueries().fallbackToDestructiveMigration().build()
            }
            return instanse!!
        }
    }

}