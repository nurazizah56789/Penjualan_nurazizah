package com.example.penjualan_nurazizah.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TbBarang::class], version = 1)
abstract class dbPenjualan : RoomDatabase(){
    abstract fun tbBarangDao() : TbBarangDao

    companion object{
        @Volatile private var instance : dbPenjualan? = null
        private val Lock = Any()
        operator fun invoke(context: Context) = instance?: synchronized(Lock){
            instance?: buildDatabase(context).also{
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            dbPenjualan::class.java,
            "penjualan.db"
        ).build()
    }
}