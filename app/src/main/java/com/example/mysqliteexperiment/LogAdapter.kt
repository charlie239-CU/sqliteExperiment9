package com.example.courage

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.mysqliteexperiment.R

class LogAdapter(private val context: Activity, private val nameArray: MutableList<String>, private val emailArray: MutableList<String>, private val idArray: MutableList<String>)
    : ArrayAdapter<String>(context, R.layout.sql_data_list_view, nameArray) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.sql_data_list_view, null, true)

        val name = rowView.findViewById(R.id.nameList) as TextView
        val email = rowView.findViewById(R.id.emailList) as TextView
        val id = rowView.findViewById(R.id.idList) as TextView

        name.text = nameArray[position]
        email.text=emailArray[position]
        id.text = idArray[position]

        return rowView
    }
}