package com.example.mysqliteexperiment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.*
import com.example.courage.LogAdapter
import example.javatpoint.com.kotlinsqlitecrud.DatabaseHandler

class MainActivity : AppCompatActivity() {
    private lateinit var idField:EditText
    private lateinit var nameField:EditText
    private lateinit var emailField:EditText
    private lateinit var createButton:Button
    private lateinit var readButton:Button
    private lateinit var deleteButton:Button
    private lateinit var updateButton:Button
    private lateinit var listView:ListView
    private lateinit var adapter:LogAdapter
    private lateinit var databaseHandler:DatabaseHandler
    private var nameList:MutableList<String> = mutableListOf()
    private var emailList:MutableList<String> = mutableListOf()
    private var idList:MutableList<String> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        idField=findViewById(R.id.id)
        nameField=findViewById(R.id.name)
        emailField=findViewById(R.id.email)
        createButton=findViewById(R.id.create)
        readButton=findViewById(R.id.read)
        deleteButton=findViewById(R.id.delete)
        updateButton=findViewById(R.id.update)
        listView=findViewById(R.id.listview)
        databaseHandler= DatabaseHandler(this)
        val blank= mutableListOf<String>()
        adapter = LogAdapter(this, nameList, emailList, idList)
        listView.adapter = adapter
        getData()
        createButton.setOnClickListener {
            val email=emailField.text.toString()
            val name=nameField.text.toString()
            val id=idField.text.toString()
            if(TextUtils.isEmpty(email) || TextUtils.isEmpty(name) || TextUtils.isEmpty(id)){
                Toast.makeText(this,"Empty Fields",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            addData()
            getData()
        }
        deleteButton.setOnClickListener {
            val email=emailField.text.toString()
            val name=nameField.text.toString()
            val id=idField.text.toString()
            if(TextUtils.isEmpty(id)){
                Toast.makeText(this,"Empty Fields",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            removeData()
            getData()
        }

        updateButton.setOnClickListener {
            val email=emailField.text.toString()
            val name=nameField.text.toString()
            val id=idField.text.toString()
            if(TextUtils.isEmpty(email) || TextUtils.isEmpty(name) || TextUtils.isEmpty(id)){
                Toast.makeText(this,"Empty Fields",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            updateData()
            getData()
        }

        readButton.setOnClickListener {
                getData()
        }



    }


    fun getData(){
        val list:MutableList<Student> = databaseHandler.viewstu()
        emailList.clear()
        nameList.clear()
        idList.clear()
        for(d in list){
            emailList.add(d!!.email!!)
            nameList.add(d!!.name!!)
            idList.add(d!!.id!!.toString())
        }
        Log.d("datalite",list.toString())
        adapter = LogAdapter(this, nameList, emailList, idList)
        listView.adapter = adapter
        listView.deferNotifyDataSetChanged()
    }

    fun addData(){
        val email=emailField.text.toString()
        val name=nameField.text.toString()
        val id=idField.text.toString().toInt()
        val stu=Student(name=name,email=email,id=id)
        val status=databaseHandler.addstu(stu)
        if(status > -1){
            Toast.makeText(applicationContext,"record save",Toast.LENGTH_LONG).show()
        }
    else{
        Toast.makeText(applicationContext,"Error",Toast.LENGTH_LONG).show()
        }
    }
    fun removeData(){
        val email=emailField.text.toString()
        val name=nameField.text.toString()
        val id=idField.text.toString().toInt()
        val stu=Student(name=name,email=email,id=id)
        databaseHandler.deletestu(id)

    }
    fun updateData(){
        val email=emailField.text.toString()
        val name=nameField.text.toString()
        val id=idField.text.toString().toInt()
        val stu=Student(name=name,email=email,id=id)
        val status=databaseHandler.updatestu(stu)
        if(status > -1){
            Toast.makeText(applicationContext,"record update",Toast.LENGTH_LONG).show()
        }
        else{
            Toast.makeText(applicationContext,"error",Toast.LENGTH_LONG).show()
        }

    }


}