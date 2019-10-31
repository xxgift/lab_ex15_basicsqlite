package com.egco428.lab_ex15_basicsqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase

class MovieDataSource (context: Context){
    // Database fields
    private var database: SQLiteDatabase? = null
    private val dbHelper: MySQLiteHelper
    private val allColumns = arrayOf<String>(MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_TITLE)

    // make sure to close the cursor
    val allComments: List<Movie>
        get() {
            val movies = ArrayList<Movie>()

            val cursor = database!!.query(MySQLiteHelper.TABLE_MOVIES,
                allColumns, null, null, null, null, null)

            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                val movie = cursorToComment(cursor)
                movies.add(movie)
                cursor.moveToNext()
            }
            cursor.close()
            return movies
        }

    init {
        dbHelper = MySQLiteHelper(context)
    }

    @Throws(SQLException::class)
    fun open() {
        database = dbHelper.getWritableDatabase()
    }

    fun close() {
        dbHelper.close()
    }

    fun createComment(movie: String): Movie {
        val values = ContentValues()
        values.put(MySQLiteHelper.COLUMN_TITLE, movie)
        val insertId = database!!.insert(MySQLiteHelper.TABLE_MOVIES, null,
            values)
        val cursor = database!!.query(MySQLiteHelper.TABLE_MOVIES,
            allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null)
        cursor.moveToFirst()
        val newComment = cursorToComment(cursor)
        cursor.close()
        return newComment
    }

    fun deleteComment(movie: Movie) {
        val id = movie.id
        println("Comment deleted with id: " + id)
        database!!.delete(MySQLiteHelper.TABLE_MOVIES, MySQLiteHelper.COLUMN_ID
                + " = " + id, null)
    }

    private fun cursorToComment(cursor: Cursor): Movie {
        val movie = Movie()
        movie.id = cursor.getLong(0)
        movie.title = cursor.getString(1)
        return movie
    }

}