//package com.example.meiyong
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//
//class CardAdapter(val express_list : List<ExpressList>) : RecyclerView.Adapter<CardAdapter.ViewHolder>() {
//    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
//        val PackageImage : ImageView = view.findViewById(R.id.imageView_express_company)
//        val TextView2 : TextView = view.findViewById(R.id.textView_express_statement)
//        val TextView3 : TextView = view.findViewById(R.id.textView_express_number)
//        val TextView4 : TextView = view.findViewById(R.id.textView_express_information)
//    }
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardAdapter.ViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.express_information,parent,false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: CardAdapter.ViewHolder, position: Int) {
//        val express = express_list[position]
//        holder.PackageImage.setImageResource(express.ImageName)
//        holder.TextView2.text = if (express.PackageStatus == 1) "已送达" else "未送达"
//        holder.TextView3.text = express.PackageNumber
//        holder.TextView4.text = express.information
//    }
//
//    override fun getItemCount() = express_list.size
//
//}