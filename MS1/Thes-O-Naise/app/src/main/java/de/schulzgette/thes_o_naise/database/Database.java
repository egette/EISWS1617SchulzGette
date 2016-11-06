package de.schulzgette.thes_o_naise.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import de.schulzgette.thes_o_naise.ThesenModel;

/**
 * Created by Jessica on 05.11.2016.
 */

public class Database {

    public static abstract  class UserdataTable implements BaseColumns{
        public static final String TABLE_NAME = "userdata";
        public static final String COLUMN_NAME_TID = "tid";
        public static final String COLUMN_NAME_POSITION = "position";
    }

    public static abstract  class ThesenTable implements BaseColumns{
        public static final String TABLE_NAME = "thesendata";
        public static final String COLUMN_NAME_TID = "tid";
        public static final String COLUMN_NAME_THESENTEXT = "thesentext";
        public static final String COLUMN_NAME_KATEGORIE = "kategorie";
        public static final String COLUMN_NAME_WAHLKREIS = "wahlkreis";
        public static final String COLUMN_NAME_LIKES = "likes";
        public static final String COLUMN_NAME_ANZAHL_PRO = "anzahl_pro";
        public static final String COLUMN_NAME_ANZAHL_NEUTRAL = "anzahl_neutral";
        public static final String COLUMN_NAME_ANZAHL_CONTRA = "anzahl_contra";
        public static final String COLUMN_NAME_K_PRO = "K_pro";
        public static final String COLUMN_NAME_K_NEUTRAL = "K_NEUTRAL";
        public static final String COLUMN_NAME_K_CONTRA = "K_contra";

    }


    public static final String SQL_CREATE_USERDATATABLE =
            "CREATE TABLE " + UserdataTable.TABLE_NAME + " (" +
                    UserdataTable.COLUMN_NAME_TID + " STRING PRIMARY KEY," +
                    UserdataTable.COLUMN_NAME_POSITION + " TEXT" +

            " )";

    public static final String SQL_CREATE_THESENTABLE =
            "CREATE TABLE " + ThesenTable.TABLE_NAME + " (" +
                    ThesenTable.COLUMN_NAME_TID + " STRING PRIMARY KEY," +
                    ThesenTable.COLUMN_NAME_THESENTEXT + " TEXT," +
                    ThesenTable.COLUMN_NAME_KATEGORIE + " TEXT," +
                    ThesenTable.COLUMN_NAME_WAHLKREIS + " TEXT," +
                    ThesenTable.COLUMN_NAME_LIKES + " TEXT," +
                    ThesenTable.COLUMN_NAME_ANZAHL_PRO + " TEXT," +
                    ThesenTable.COLUMN_NAME_ANZAHL_NEUTRAL + " TEXT," +
                    ThesenTable.COLUMN_NAME_ANZAHL_CONTRA + " TEXT," +
                    ThesenTable.COLUMN_NAME_K_PRO + " TEXT," +
                    ThesenTable.COLUMN_NAME_K_NEUTRAL + " TEXT," +
                    ThesenTable.COLUMN_NAME_K_CONTRA + " TEXT" +
                    " )";

    public static final String SQL_DELETE_USERDATATABLE =
            "DROP TABLE IF EXISTS " + UserdataTable.TABLE_NAME;



    public class ThesenDbHelper extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "Thes-O-Naise.db";

        public ThesenDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_USERDATATABLE);
            db.execSQL(SQL_CREATE_THESENTABLE);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
            onCreate(db);
        }
    }


    private Context context;

    public  Database(Context context) { this.context = context; }

    //schreibt eine Position zu einer tid in die Datenbank und ändert die Positon falls die TID schon vorhanden war
    public void insertposition(String position, String tid) {
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase dbread = thesenDbHelper.getReadableDatabase();
        Cursor cursor;
        if (tid != null && position != null) {
            cursor = dbread.query(UserdataTable.TABLE_NAME, new String[]{UserdataTable.COLUMN_NAME_TID}, "tid = ?", new String[]{tid}, null, null, null);

            SQLiteDatabase dbwrite = thesenDbHelper.getWritableDatabase();

            if (cursor.getCount() < 1) {
                try {
                    ContentValues values = new ContentValues();
                    values.put(UserdataTable.COLUMN_NAME_POSITION, position);
                    values.put(UserdataTable.COLUMN_NAME_TID, tid);
                    long newRowId;
                    newRowId = dbwrite.insert(UserdataTable.TABLE_NAME, null, values);
                } finally {
                    dbread.close();
                    dbwrite.close();
                }
            } else {
                try {
                    ContentValues values = new ContentValues();
                    values.put(UserdataTable.COLUMN_NAME_POSITION, position);
                    values.put(UserdataTable.COLUMN_NAME_TID, tid);
                    dbwrite.update(UserdataTable.TABLE_NAME, values, "tid=?", new String[]{tid});
                } finally {
                    dbread.close();
                    dbwrite.close();
                }
            }
        }
    }

    public List<String> getall() {
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase db = thesenDbHelper.getReadableDatabase();
        try {
            ArrayList<String> result = new ArrayList<>();
            Cursor c = db.query(UserdataTable.TABLE_NAME, new String[] {UserdataTable.COLUMN_NAME_TID, UserdataTable.COLUMN_NAME_POSITION }, null, null, null, null, null);
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

    public int insertArrayListThesen (ArrayList<ThesenModel> thesenModels){
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase dbwrite = thesenDbHelper.getWritableDatabase();
        SQLiteDatabase dbread = thesenDbHelper.getReadableDatabase();
        int done = 0;

        for(int i = 0; i< thesenModels.size(); i++) {
            String tid = thesenModels.get(i).getTID();
            Log.d("TID DATA save", tid);
            String thesentext = thesenModels.get(i).getThesentext();
            String kategorie = thesenModels.get(i).getKategorie();
            String wahlkreis = thesenModels.get(i).getWahlkreis();
            Integer likes = thesenModels.get(i).getLikes();
            Integer anzahl_pro = thesenModels.get(i).getPro();
            Integer anzahl_neutral = thesenModels.get(i).getNeutral();
            Integer anzahl_contra = thesenModels.get(i).getContra();
            String postionen_pro = thesenModels.get(i).getPositionPro().toString();
            String postionen_neutral = thesenModels.get(i).getPositionNeutral().toString();
            String postionen_contra = thesenModels.get(i).getPositionContra().toString();

            if (tid != null){
                Cursor cursor = dbread.query(ThesenTable.TABLE_NAME, new String[]{UserdataTable.COLUMN_NAME_TID}, "tid = ?", new String[]{tid}, null, null, null);
                if (cursor.getCount() < 1) {
                    try {
                        ContentValues values = new ContentValues();
                        values.put(ThesenTable.COLUMN_NAME_TID, tid);
                        values.put(ThesenTable.COLUMN_NAME_THESENTEXT, thesentext);
                        values.put(ThesenTable.COLUMN_NAME_KATEGORIE, kategorie);
                        values.put(ThesenTable.COLUMN_NAME_WAHLKREIS, wahlkreis);
                        values.put(ThesenTable.COLUMN_NAME_LIKES, likes);
                        values.put(ThesenTable.COLUMN_NAME_ANZAHL_PRO, anzahl_pro);
                        values.put(ThesenTable.COLUMN_NAME_ANZAHL_NEUTRAL, anzahl_neutral);
                        values.put(ThesenTable.COLUMN_NAME_ANZAHL_CONTRA, anzahl_contra);
                        values.put(ThesenTable.COLUMN_NAME_K_PRO, postionen_pro);
                        values.put(ThesenTable.COLUMN_NAME_K_NEUTRAL, postionen_neutral);
                        values.put(ThesenTable.COLUMN_NAME_K_CONTRA, postionen_contra);
                        dbwrite.insert(ThesenTable.TABLE_NAME, null, values);
                    } finally {
                        dbread.close();
                        dbwrite.close();
                    }
                }else {
                    try {
                        ContentValues values = new ContentValues();
                        values.put(ThesenTable.COLUMN_NAME_TID, tid);
                        values.put(ThesenTable.COLUMN_NAME_THESENTEXT, thesentext);
                        values.put(ThesenTable.COLUMN_NAME_KATEGORIE, kategorie);
                        values.put(ThesenTable.COLUMN_NAME_WAHLKREIS, wahlkreis);
                        values.put(ThesenTable.COLUMN_NAME_LIKES, likes);
                        values.put(ThesenTable.COLUMN_NAME_ANZAHL_PRO, anzahl_pro);
                        values.put(ThesenTable.COLUMN_NAME_ANZAHL_NEUTRAL, anzahl_neutral);
                        values.put(ThesenTable.COLUMN_NAME_ANZAHL_CONTRA, anzahl_contra);
                        values.put(ThesenTable.COLUMN_NAME_K_PRO, postionen_pro);
                        values.put(ThesenTable.COLUMN_NAME_K_NEUTRAL, postionen_neutral);
                        values.put(ThesenTable.COLUMN_NAME_K_CONTRA, postionen_contra);
                        dbwrite.update(ThesenTable.TABLE_NAME, values, "tid=?", new String[]{tid});
                    }finally {
                        dbread.close();
                        dbwrite.close();
                    }
                }
            }
        }
        done = 1;
        return done;
    }

    public ArrayList<ThesenModel> getArraylistThesen (String kategorie){
        ArrayList<ThesenModel> thesenModels = new ArrayList<>();
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase db = thesenDbHelper.getReadableDatabase();


        try{
            if(kategorie != null) {
                Cursor  c = db.query(ThesenTable.TABLE_NAME, new String[]{ThesenTable.COLUMN_NAME_TID, ThesenTable.COLUMN_NAME_THESENTEXT, ThesenTable.COLUMN_NAME_KATEGORIE, ThesenTable.COLUMN_NAME_WAHLKREIS, ThesenTable.COLUMN_NAME_LIKES, ThesenTable.COLUMN_NAME_ANZAHL_PRO, ThesenTable.COLUMN_NAME_ANZAHL_NEUTRAL, ThesenTable.COLUMN_NAME_ANZAHL_CONTRA, ThesenTable.COLUMN_NAME_K_PRO, ThesenTable.COLUMN_NAME_K_NEUTRAL, ThesenTable.COLUMN_NAME_K_CONTRA}, "kategorie = ?", new String[]{kategorie}, null, null, null);

                try {
                    while (c.moveToNext()) {
                        String tid = c.getString(0);
                        Log.d("TID DATA get", tid);
                        String thesentext = c.getString(1);
                        String kategorie2 = c.getString(2);
                        String wahlkreis = c.getString(3);
                        Integer likes = c.getInt(4);
                        Integer anzahl_pro = c.getInt(5);
                        Integer anzahl_neutral = c.getInt(6);
                        Integer anzahl_contra = c.getInt(7);
                        String postionen_pro = c.getString(8);
                        String postionen_neutral = c.getString(9);
                        String postionen_contra = c.getString(10);
                        JSONArray postionen_pro_Array = new JSONArray(postionen_pro);
                        JSONArray postionen_neutral_Array = new JSONArray(postionen_neutral);
                        JSONArray postionen_contra_Array = new JSONArray(postionen_contra);
                        thesenModels.add(new ThesenModel(tid, thesentext, kategorie2, wahlkreis, likes, anzahl_pro, anzahl_neutral, anzahl_contra, postionen_pro_Array, postionen_neutral_Array, postionen_contra_Array));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    c.close();
                }
            }
        } finally {
            db.close();
        }
        return thesenModels;
    }

}
