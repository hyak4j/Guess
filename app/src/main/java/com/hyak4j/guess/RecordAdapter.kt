package com.hyak4j.guess

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hyak4j.guess.data.Record

class RecordAdapter(var records : List<Record>) : RecyclerView.Adapter<RecordViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        return RecordViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.row_record, parent, false))
    }

    override fun getItemCount(): Int {
        return records.size
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.nickText.text = records.get(position).nickname
        holder.countText.text = records.get(position).counter.toString()
    }

}

class RecordViewHolder(view: View) : RecyclerView.ViewHolder(view){
    var nickText = view.findViewById<TextView>(R.id.record_nickname)
    var countText = view.findViewById<TextView>(R.id.record_counter)
}