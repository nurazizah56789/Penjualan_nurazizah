package com.example.penjualan_nurazizah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.penjualan_nurazizah.room.Constant
import com.example.penjualan_nurazizah.room.TbBarang
import com.example.penjualan_nurazizah.room.dbPenjualan
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {
    val db by lazy { dbPenjualan(this) }
    private var tbBarang:Int =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setupView()
        tombolbarang()
        tbBarang = intent.getIntExtra("intent_id",0)
        Toast.makeText(this,tbBarang.toString(),Toast.LENGTH_SHORT).show()
    }
    fun setupView(){
        val intentType = intent.getIntExtra("intent_type",0)
        when (intentType){
            Constant.TYPE_CREATE ->{
               btnupdate.visibility = View.GONE

            }

            Constant.TYPE_READ ->{
                btnsave.visibility = View.GONE
                btnupdate.visibility =View.GONE
                ETid.visibility = View.GONE
                tampilbarang()
            }

            Constant.TYPE_UPDATE ->{
                btnsave.visibility = View.GONE
                ETid.visibility = View.GONE
                tampilbarang()
            }
        }
    }

    fun tombolbarang(){
        btnsave.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.tbBarangDao().addBrg(
                    TbBarang(
                        ETid.text.toString().toInt(),
                        ETnm_brg.text.toString(),
                        EThrg_brg.text.toString().toInt(),
                        ETstok.text.toString().toInt()
                    )
                )
                finish()
            }
        }

        btnupdate.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.tbBarangDao().updateBrg(
                    TbBarang(
                    ETid.text.toString().toInt(),
                    ETnm_brg.text.toString(),
                    EThrg_brg.text.toString().toInt(),
                    ETstok.text.toString().toInt()

                    )
                )

                finish()
            }
        }
    }

    fun tampilbarang(){
        tbBarang =intent.getIntExtra("intent_id",0)
        CoroutineScope(Dispatchers.IO).launch {
            val barang = db.tbBarangDao().tampilBrg(tbBarang)[0]
            //ETid.setText(barang.id_brg)
            ETnm_brg.setText(barang.nm_brg)
            EThrg_brg.setText(barang.hrg_brg)
            ETstok.setText(barang.stok)
        }
    }
}