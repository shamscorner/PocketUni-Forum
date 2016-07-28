package com.shamsapp.shamscorner.com.pocketuni_forum.sqlite_manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by shamim on 28-Jul-16.
 */
public class DBHelper extends SQLiteOpenHelper {

    private String DB_NAME, TBL_NAME, sqlCreateTableInitial;

    public DBHelper(Context context, String DB_NAME, String TBL_NAME, String sqlCreateTableInitial) {
        super(context, DB_NAME, null, 1);
        this.TBL_NAME = TBL_NAME;
        this.sqlCreateTableInitial = sqlCreateTableInitial;
        this.DB_NAME = DB_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreateTableInitial);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TBL_NAME);
        onCreate(db);
    }
    public boolean insert(String[] columnNames, String... arg){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        for(int i=0; i<arg.length; i++){
            contentValues.put(columnNames[i], arg[i]);
        }

        db.insert(TBL_NAME, null, contentValues);
        return true;
    }
    public Cursor getData(String where){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TBL_NAME+" WHERE "+where, null);
        return res;
    }
    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TBL_NAME);
        return numRows;
    }
    public boolean update(String[] columnNames, String whereClauses, String[] whereArg, String... arg){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        for(int i=0; i<arg.length; i++){
            contentValues.put(columnNames[i], arg[i]);
        }

        db.update(TBL_NAME, contentValues, whereClauses, whereArg);
        return true;
    }
    public Integer delete (String whereClauses, String[] whereArg) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TBL_NAME, whereClauses, whereArg);
    }
    public boolean deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TBL_NAME);
        return true;
    }
    public ArrayList<String> getAllData(String colName, String sql){
        ArrayList<String> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(sql, null);
        res.moveToFirst();

        while(res.isAfterLast() == false){
            arrayList.add(res.getString(res.getColumnIndex(colName)));
            res.moveToNext();
        }

        return arrayList;
    }
}
