package com.example.penjualan_nurazizah

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.penjualan_nurazizah.room.TbBarang
import kotlinx.android.synthetic.main.activity_barang_adapter.view.*

class BarangAdapter (private val barang: ArrayList<TbBarang>,private val listener: OnAdapterListener)
    : RecyclerView.Adapter<BarangAdapter.BarangViewHolder>(){

    class BarangViewHolder(val view: View): RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarangViewHolder {
        return BarangViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.activity_barang_adapter,parent,false)
        )
    }

    override fun onBindViewHolder(holder: BarangViewHolder, position: Int) {
        val Tbbarang=barang[position]
        holder.view.T_nama.text = Tbbarang.nm_brg
        holder.view.T_hrgbrg.text = Tbbarang.hrg_brg.toString()
        holder.view.CV_Adapter.setOnClickListener{
            listener.onClick(Tbbarang)
        }
        holder.view.icon_edit.setOnClickListener {
            listener.onUpdate(Tbbarang)
        }
        holder.view.icon_delete.setOnClickListener {
            listener.onDelete(Tbbarang)
        }
    }

    override fun getItemCount()= barang.size

    fun setData(list: List<TbBarang>){
        barang.clear()
        barang.addAll(list)
        notifyDataSetChanged()
    }
    interface OnAdapterListener{
        fun onClick(Tbbarang: TbBarang)
        fun onUpdate(Tbbarang: TbBarang)
        fun onDelete(Tbbarang: TbBarang)

    }

}


