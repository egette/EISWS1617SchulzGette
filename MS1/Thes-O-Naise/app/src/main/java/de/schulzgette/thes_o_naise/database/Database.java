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
import java.util.HashMap;

import de.schulzgette.thes_o_naise.ThesenModel;

/**
 * Created by Jessica on 05.11.2016.
 */

public class Database {

    public static abstract  class UserpositiondataTable implements BaseColumns{
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


    public static final String SQL_CREATE_USERPOSITIONDATATABLE =
            "CREATE TABLE " + UserpositiondataTable.TABLE_NAME + " (" +
                    UserpositiondataTable.COLUMN_NAME_TID + " STRING PRIMARY KEY," +
                    UserpositiondataTable.COLUMN_NAME_POSITION + " TEXT" +

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

    public static final String SQL_DELETE_USERPOSTIONDATATABLE =
            "DROP TABLE IF EXISTS " + UserpositiondataTable.TABLE_NAME;
    public static final String SQL_DELETE_THESENTABLE =
            "DROP TABLE IF EXISTS " + ThesenTable.TABLE_NAME;



    public class ThesenDbHelper extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 2;
        public static final String DATABASE_NAME = "Thes-O-Naise.db";

        public ThesenDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_USERPOSITIONDATATABLE);
            db.execSQL(SQL_CREATE_THESENTABLE);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //onUpgrade(db, oldVersion, newVersion);
            db.execSQL(SQL_DELETE_USERPOSTIONDATATABLE);
            db.execSQL(SQL_DELETE_THESENTABLE);
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
            cursor = dbread.query(UserpositiondataTable.TABLE_NAME, new String[]{UserpositiondataTable.COLUMN_NAME_TID}, "tid = ?", new String[]{tid}, null, null, null);

            SQLiteDatabase dbwrite = thesenDbHelper.getWritableDatabase();

            if (cursor.getCount() < 1) {
                try {
                    ContentValues values = new ContentValues();
                    values.put(UserpositiondataTable.COLUMN_NAME_POSITION, position);
                    values.put(UserpositiondataTable.COLUMN_NAME_TID, tid);
                    long newRowId;
                    newRowId = dbwrite.insert(UserpositiondataTable.TABLE_NAME, null, values);
                } finally {
                    dbread.close();
                    dbwrite.close();
                }
            } else {
                try {
                    ContentValues values = new ContentValues();
                    values.put(UserpositiondataTable.COLUMN_NAME_POSITION, position);
                    values.put(UserpositiondataTable.COLUMN_NAME_TID, tid);
                    dbwrite.update(UserpositiondataTable.TABLE_NAME, values, "tid=?", new String[]{tid});
                } finally {
                    dbread.close();
                    dbwrite.close();
                }
            }
        }
    }

    public HashMap<String, String> getallPositions() {
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase db = thesenDbHelper.getReadableDatabase();
        try {
            HashMap<String, String> result = new HashMap<>();
            Cursor c = db.query(UserpositiondataTable.TABLE_NAME, new String[] {UserpositiondataTable.COLUMN_NAME_TID, UserpositiondataTable.COLUMN_NAME_POSITION }, null, null, null, null, null);
            try{
                while (c.moveToNext()) {
                    result.put(c.getString(0), c.getString(1));
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

    public void insertThese (String TID, String thesentext, String kategorie, String wahlkreis, Integer likesINT, Integer proINT, Integer neutralINT, Integer contraINT, JSONArray K_PRO, JSONArray  K_NEUTRAL, JSONArray K_CONTRA ){
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase dbwrite = thesenDbHelper.getWritableDatabase();
        SQLiteDatabase dbread = thesenDbHelper.getReadableDatabase();

        try {
                Log.d("TID DATA save", TID);
                Integer likes = likesINT;
                Integer anzahl_pro = proINT;
                Integer anzahl_neutral = neutralINT;
                Integer anzahl_contra = contraINT;
                String postionen_pro = K_PRO.toString();
                String postionen_neutral = K_NEUTRAL.toString();
                String postionen_contra = K_CONTRA.toString();

                if (TID != null){
                    Cursor cursor = dbread.query(ThesenTable.TABLE_NAME, new String[]{ThesenTable.COLUMN_NAME_TID}, "tid = ?", new String[]{TID}, null, null, null);
                    if (cursor.getCount() < 1) {
                        try {
                            ContentValues values = new ContentValues();
                            values.put(ThesenTable.COLUMN_NAME_TID, TID);
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
                        }
                    }else {
                        try {
                            ContentValues values = new ContentValues();
                            values.put(ThesenTable.COLUMN_NAME_TID, TID);
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
                            dbwrite.update(ThesenTable.TABLE_NAME, values, "tid=?", new String[]{TID});
                        }finally {
                        }
                    }
                }

        } finally {
            dbread.close();
            dbwrite.close();
        }

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

    public ThesenModel getTheseWithTID(String TID){
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase db = thesenDbHelper.getReadableDatabase();
        Cursor  c = null;
        ThesenModel result = null;
        try{
            if(TID != null){
                c = db.query(ThesenTable.TABLE_NAME, new String[]{ThesenTable.COLUMN_NAME_TID, ThesenTable.COLUMN_NAME_THESENTEXT, ThesenTable.COLUMN_NAME_KATEGORIE, ThesenTable.COLUMN_NAME_WAHLKREIS, ThesenTable.COLUMN_NAME_LIKES, ThesenTable.COLUMN_NAME_ANZAHL_PRO, ThesenTable.COLUMN_NAME_ANZAHL_NEUTRAL, ThesenTable.COLUMN_NAME_ANZAHL_CONTRA, ThesenTable.COLUMN_NAME_K_PRO, ThesenTable.COLUMN_NAME_K_NEUTRAL, ThesenTable.COLUMN_NAME_K_CONTRA}, "tid = ?", new String[]{TID}, null, null, null);
                if (c.getCount() < 1){
                    return null;
                }else {
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
                        result = new ThesenModel(tid, thesentext, kategorie2, wahlkreis, likes, anzahl_pro, anzahl_neutral, anzahl_contra, postionen_pro_Array, postionen_neutral_Array, postionen_contra_Array);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            c.close();
            db.close();
        }
        return result;
    }

    public JSONArray getBegruendungWithTIDandPosition(String TID, String position){
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase db = thesenDbHelper.getReadableDatabase();
        Cursor  c = null;
        JSONArray result = null;
        try{
            if(TID != null && position != null){
                c = db.query(ThesenTable.TABLE_NAME, new String[]{ThesenTable.COLUMN_NAME_TID, ThesenTable.COLUMN_NAME_ANZAHL_PRO, ThesenTable.COLUMN_NAME_ANZAHL_NEUTRAL, ThesenTable.COLUMN_NAME_ANZAHL_CONTRA, ThesenTable.COLUMN_NAME_K_PRO, ThesenTable.COLUMN_NAME_K_NEUTRAL, ThesenTable.COLUMN_NAME_K_CONTRA}, "tid = ?", new String[]{TID}, null, null, null);
                if (c.getCount() < 1){
                    return null;
                }else {
                    while (c.moveToNext()) {
                        String tid = c.getString(0);
                        String postionen_pro = c.getString(8);
                        String postionen_neutral = c.getString(9);
                        String postionen_contra = c.getString(10);
                        JSONArray postionen_pro_Array = new JSONArray(postionen_pro);
                        JSONArray postionen_neutral_Array = new JSONArray(postionen_neutral);
                        JSONArray postionen_contra_Array = new JSONArray(postionen_contra);
                        if(position == "Pro") result = postionen_pro_Array;
                        if(position == "Neutral") result = postionen_neutral_Array;
                        if(position == "Contra") result = postionen_contra_Array;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            c.close();
            db.close();
        }
        return result;
    }
}
