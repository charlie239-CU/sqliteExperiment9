package example.javatpoint.com.kotlinsqlitecrud

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteException
import com.example.mysqliteexperiment.Student

//creating the database logic, extending the SQLiteOpenHelper base class  
class DatabaseHandler(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "sDatabase"
        private val TABLE_CONTACTS = "student"
        private val KEY_ID = "id"
        private val KEY_NAME = "name"
        private val KEY_EMAIL = "email"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Tstulates.  
        //creating table with fields  
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT" + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //  TODO("not implemented") //To change body of created functions use File | Settings | File Tstulates.  
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS)
        onCreate(db)
    }


    //method to insert data  
    fun addstu(stu: Student):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, stu.id)
        contentValues.put(KEY_NAME, stu.name) // Student Name  
        contentValues.put(KEY_EMAIL,stu.email ) // Student Phone  
        // Inserting Row  
        val success = db.insert(TABLE_CONTACTS, null, contentValues)
        //2nd argument is String containing nullColumnHack  
        db.close() // Closing database connection  
        return success
    }
    //method to read data  
    @SuppressLint("Range")
    fun viewstu():MutableList<Student>{
        val stuList:MutableList<Student> = mutableListOf()
        val selectQuery = "SELECT  * FROM $TABLE_CONTACTS"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var userId: Int
        var userName: String
        var userEmail: String
        if (cursor.moveToFirst()) {
            do {
                userId = cursor.getInt(cursor.getColumnIndex("id"))
                userName = cursor.getString(cursor.getColumnIndex("name"))
                userEmail = cursor.getString(cursor.getColumnIndex("email"))
                val stu= Student(id = userId, name = userName, email = userEmail)
                stuList.add(stu)
            } while (cursor.moveToNext())
        }
        return stuList
    }

    @SuppressLint("Range")
    fun viewstuById(id:Int):Student{
        val stu:Student = Student()
        val selectQuery = "SELECT  * FROM $TABLE_CONTACTS where id=$id"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return Student()
        }
        var userId: Int
        var userName: String
        var userEmail: String
        if (cursor.moveToFirst()) {
            do {
                userId = cursor.getInt(cursor.getColumnIndex("id"))
                userName = cursor.getString(cursor.getColumnIndex("name"))
                userEmail = cursor.getString(cursor.getColumnIndex("email"))
                val stu= Student(id = userId, name = userName, email = userEmail)

            } while (cursor.moveToNext())
        }
        return stu
    }

    //method to update data  
    fun updatestu(stu: Student):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, stu.id)
        contentValues.put(KEY_NAME, stu.name) // Student Name  
        contentValues.put(KEY_EMAIL,stu.email ) // Student Email  

        // Updating Row  
        val success = db.update(TABLE_CONTACTS, contentValues,"id="+stu.id,null)
        //2nd argument is String containing nullColumnHack  
        db.close() // Closing database connection  
        return success
    }



    //method to delete data  
    fun deletestu(id:Int){
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, id) // Student UserId
        // Deleting Row val stu=viewstuById(stu!!.id!!
        db.delete(TABLE_CONTACTS,"id="+id,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection

    }
}  