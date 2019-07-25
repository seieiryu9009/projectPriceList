package com.gfakuntansi.gf_pricelist

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.produk_item_layout.view.*

class MyAdapter(private val context: Context, var dataList : ArrayList<ProductModel>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.produk_item_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val ( aId, aName ) = dataList[position]

        holder.id.text = aId
        holder.nama.text = aName
    }

    override fun getItemCount(): Int = dataList.size

    inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var id : TextView = itemView.produk_id
        var nama : TextView = itemView.produk_name
    }
}
