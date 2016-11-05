package de.schulzgette.thes_o_naise.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jessica on 05.11.2016.
 */

public class Database {

    public static abstract  class ThesenTable implements BaseColumns{
        public static final String TABLE_NAME = "userdata";
        public static final String COLUMN_NAME_TID = "tid";
        public static final String COLUMN_NAME_POSITION = "position";
    }



    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ThesenTable.TABLE_NAME + " (" +
                    ThesenTable.COLUMN_NAME_TID + " STRING PRIMARY KEY," +
                    ThesenTable.COLUMN_NAME_POSITION + " TEXT" +

            " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ThesenTable.TABLE_NAME;



    public class ThesenDbHelper extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "Thes-O-Naise.db";

        public ThesenDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) { db.execSQL(SQL_CREATE_ENTRIES);}

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }


    private Context context;

    public  Database(Context context) { this.context = context; }

    //schreibt eine Position zu einer tid in die Datenbank und ändert die Positon falls die TID schon vorhanden war
    public void insertposition(String position, String tid) {
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase dbread = thesenDbHelper.getReadableDatabase();
        Cursor cursor = dbread.query(ThesenTable.TABLE_NAME, new String[] {ThesenTable.COLUMN_NAME_TID}, "tid = ?", new String[]{tid}, null, null, null);

        SQLiteDatabase dbwrite = thesenDbHelper.getWritableDatabase();

        if (cursor.getCount()<1) {
            try {
                ContentValues values = new ContentValues();
                values.put(ThesenTable.COLUMN_NAME_POSITION, position);
                values.put(ThesenTable.COLUMN_NAME_TID, tid);


                long newRowId;
                newRowId = dbwrite.insert(ThesenTable.TABLE_NAME, null, values);
            } finally {
                dbwrite.close();
            }
        }else{
            try {
                ContentValues values = new ContentValues();
                values.put(ThesenTable.COLUMN_NAME_POSITION, position);
                values.put(ThesenTable.COLUMN_NAME_TID, tid);


                long newRowId;
                newRowId = dbwrite.update(ThesenTable.TABLE_NAME, values, "tid=?", new String[]{tid});
            } finally {
                dbwrite.close();
            }
        }
    }

    public List<String> getall() {
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase db = thesenDbHelper.getReadableDatabase();
        try {
            ArrayList<String> result = new ArrayList<>();
            Cursor c = db.query(ThesenTable.TABLE_NAME, new String[] {ThesenTable.COLUMN_NAME_TID, ThesenTable.COLUMN_NAME_POSITION }, null, null, null, null, null);
            try{
                while (c.moveToNext()) {
                    result.add(c.getString(0));
                    Log.d("TID:", c.getString(0));
                    Log.d("position:", c.getString(1));
                }
                return  result;
            }finally {
                c.close();
            }
        }finally {
            db.close();
        }
    }
}
