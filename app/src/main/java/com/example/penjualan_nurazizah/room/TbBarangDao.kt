package com.example.penjualan_nurazizah.room

import androidx.room.*

@Dao
interface TbBarangDao {

    @Insert
    fun addBrg(tbBarang: TbBarang)

    @Update
    fun updateBrg(tbBarang: TbBarang)

    @Delete
    fun deleteBrg(tbBarang: TbBarang)

    @Query("SELECT * FROM TbBarang")
    fun getBrg():List<TbBarang>

    @Query("SELECT * FROM TbBarang WHERE id_brg=:tbBrg_id")
    fun tampilBrg(tbBrg_id: Int):List<TbBarang>
}