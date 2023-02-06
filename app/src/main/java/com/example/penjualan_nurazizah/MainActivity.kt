package com.example.penjualan_nurazizah

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.penjualan_nurazizah.room.Constant
import com.example.penjualan_nurazizah.room.TbBarang
import com.example.penjualan_nurazizah.room.dbPenjualan
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    val db by lazy { dbPenjualan(this) }
    lateinit var barangAdapter: BarangAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        halEdit()
        setupRecyclerView()

    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            val barang = db.tbBarangDao().getBrg()
            Log.d("MainActivity", "dbResponse: $barang")
            withContext(Dispatchers.Main) {
                barangAdapter.setData(barang)
            }
        }
    }

    private fun halEdit() {
        btnInput.setOnClickListener {
            intentEdit(0, Constant.TYPE_CREATE)

        }
    }

    fun intentEdit(tbbrgid: Int, intentType: Int) {
        startActivity(
            Intent(applicationContext, EditActivity::class.java)
                .putExtra("intent_id", tbbrgid)
                .putExtra("intent_type", intentType)
        )
    }

    fun setupRecyclerView() {
        barangAdapter = BarangAdapter(arrayListOf(), object : BarangAdapter.OnAdapterListener {
            override fun onClick(tbBarang: TbBarang) {
                intentEdit(tbBarang.id_brg, Constant.TYPE_READ)
            }

            override fun onUpdate(tbBarang: TbBarang) {
                intentEdit(tbBarang.id_brg,Constant.TYPE_UPDATE)

            }

            override fun onDelete(tbBarang: TbBarang) {
                hapusbrg(tbBarang)
            }
        })

        list_datapenjualan.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = barangAdapter
        }

    }

    private fun hapusbrg (tbBarang: TbBarang){
        val dialog = AlertDialog.Builder(this)
        dialog.apply {
            setTitle("Konfirmasi Hapus")
            setMessage("Yakin hapus ${tbBarang.nm_brg}?")
            setNegativeButton("Batal") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") { dialogInterface, i ->
                CoroutineScope(Dispatchers.IO).launch {
                    db.tbBarangDao().deleteBrg(tbBarang)
                    dialogInterface.dismiss()
                    loadData()
                }
            }
        }

        dialog.show()
    }
}