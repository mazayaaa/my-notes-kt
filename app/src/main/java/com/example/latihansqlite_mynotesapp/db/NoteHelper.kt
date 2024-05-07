package com.example.latihansqlite_mynotesapp.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase

class NoteHelper(context: Context) {
    private var dataBaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    // koneksi database
    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }
    fun close() {
        dataBaseHelper.close()
        if (database.isOpen)
            database.close()
    }

    // mengambil seluruh data
    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "${DatabaseContract.NoteColumns._ID} ASC",
            null)
    }

    // mengambil data dengan id tertentu
    fun queryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE, null, "${DatabaseContract.NoteColumns._ID} = ?",
            arrayOf(id), null, null, null, null)
    }

    // menyimpan data
    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    // mengubah data
    fun update(id: String, values: ContentValues?): Int {
        return database.update(
            DATABASE_TABLE, values, "${DatabaseContract.NoteColumns._ID} = ?",
            arrayOf(id))
    }

    // menghapus data
    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "${DatabaseContract.NoteColumns._ID} = '$id'", null)
    }

    // menghapus seluruh data
    companion object {
        private const val DATABASE_TABLE = DatabaseContract.NoteColumns.TABLE_NAME
        private var INSTANCE: NoteHelper? = null
        fun getInstance(context: Context): NoteHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: NoteHelper(context)
            }
    }
}