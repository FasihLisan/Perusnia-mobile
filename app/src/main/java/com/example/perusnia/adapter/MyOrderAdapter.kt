package com.example.perusnia.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.perusnia.Model.DataXXXXXX
import com.example.perusnia.R
import kotlinx.android.synthetic.main.myorder_item.view.*
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList

class MyOrderAdapter(val data: ArrayList<DataXXXXXX>, val listener: OnAdapterListener): RecyclerView.Adapter<MyOrderAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.myorder_item,parent,false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val order = data[position]
        holder.view.orderId.text = order.orderId
        holder.view.payment_type.text = order.paymentType
        holder.view.gross_amount.text = order.grossAmount

        if (order.transactionStatus.equals("settlement")){
            holder.view.transaction_status_background.setCardBackgroundColor(Color.parseColor("#00A96C"))
            holder.view.transaction_status.setTextColor(Color.parseColor("#FFFFFF"))
        }
        if (order.transactionStatus.equals("settlement")){
            holder.view.transaction_status.text = "Success"
        }else{
            holder.view.transaction_status.text = order.transactionStatus
        }
        holder.view.transaction_time.text = SimpleDateFormat("MMM, dd yyyy h:mm a").format(SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(order.transactionTime))
        holder.view.setOnClickListener(){
            listener.onClick(order)
        }

    }

    override fun getItemCount() = data.size

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view)

    fun setData(data2:List<DataXXXXXX>){
        data.clear()
        data.addAll(data2)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick( data: DataXXXXXX)
    }


}