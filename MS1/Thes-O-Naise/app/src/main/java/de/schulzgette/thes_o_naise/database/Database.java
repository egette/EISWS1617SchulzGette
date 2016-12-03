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
import org.json.JSONObject;

import java.util.ArrayList;

import de.schulzgette.thes_o_naise.Models.KandidatenModel;
import de.schulzgette.thes_o_naise.Models.ThesenModel;

/**
 * Created by Jessica on 05.11.2016.
 */

public class Database {

    public static abstract  class UserpositiondataTable implements BaseColumns{
        public static final String TABLE_NAME = "userdata";
        public static final String COLUMN_NAME_TID = "tid";
        public static final String COLUMN_NAME_POSITION = "position";
        public static final String COLUMN_NAME_SERVER = "server";
        public static final String COLUMN_NAME_LAST_POSITION = "lastposition";
    }

    //TODO MEHR KATEGORIEN ?
    public static abstract  class KandidatenTable implements BaseColumns{
        public static final String TABLE_NAME = "kandidaten";
        public static final String COLUMN_NAME_KID = "kid";
        public static final String COLUMN_NAME_VORNAME = "vorname";
        public static final String COLUMN_NAME_NACHNAME = "nachname";
        public static final String COLUMN_NAME_PARTEI = "partei";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_WAHLKREIS = "wahlkreis";
        public static final String COLUMN_NAME_BEANTWORTETETHESEN = "tid";
        public static final String COLUMN_NAME_PUNKTE_INSGESAMT = "punkte_insgesamt";
        public static final String COLUMN_NAME_PUNKTE_LOKAL = "punkte_lokal";
        public static final String COLUMN_NAME_PUNKTE_UMWELT = "punkte_umwelt";
        public static final String COLUMN_NAME_PUNKTE_AP = "punkte_ap";
        public static final String COLUMN_NAME_PUNKTE_SATIRE = "punkte_satire";
        public static final String COLUMN_NAME_BIOGRAPHIE = "biographie";
        public static final String COLUMN_NAME_WAHLPROGRAMM = "wahlprogramm";
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
        public static final String COLUMN_NAME_W_PRO = "w_pro";
        public static final String COLUMN_NAME_W_NEUTRAL = "w_neutral";
        public static final String COLUMN_NAME_W_CONTRA = "w_contra";
        public static final String COLUMN_NAME_K_POSITION = "k_position";

    }


    public static final String SQL_CREATE_USERPOSITIONDATATABLE =
            "CREATE TABLE " + UserpositiondataTable.TABLE_NAME + " (" +
                    UserpositiondataTable.COLUMN_NAME_TID + " STRING PRIMARY KEY," +
                    UserpositiondataTable.COLUMN_NAME_POSITION + " TEXT," +
                    UserpositiondataTable.COLUMN_NAME_SERVER + " TEXT," +
                    UserpositiondataTable.COLUMN_NAME_LAST_POSITION + " TEXT" +

            " )";

    //TODO MEHR KATEGORIEN ?

    public static final String SQL_CREATE_KANDIDATENTABLE =
            "CREATE TABLE " + KandidatenTable.TABLE_NAME + " (" +
                    KandidatenTable.COLUMN_NAME_KID + " STRING PRIMARY KEY," +
                    KandidatenTable.COLUMN_NAME_VORNAME + " TEXT," +
                    KandidatenTable.COLUMN_NAME_NACHNAME+ " TEXT," +
                    KandidatenTable.COLUMN_NAME_PARTEI+ " TEXT," +
                    KandidatenTable.COLUMN_NAME_EMAIL + " TEXT," +
                    KandidatenTable.COLUMN_NAME_WAHLKREIS + " TEXT," +
                    KandidatenTable.COLUMN_NAME_BEANTWORTETETHESEN + " TEXT," +
                    KandidatenTable.COLUMN_NAME_PUNKTE_INSGESAMT + " INTEGER," +
                    KandidatenTable.COLUMN_NAME_PUNKTE_LOKAL + " INTEGER," +
                    KandidatenTable.COLUMN_NAME_PUNKTE_UMWELT + " INTEGER," +
                    KandidatenTable.COLUMN_NAME_PUNKTE_AP + " INTEGER," +
                    KandidatenTable.COLUMN_NAME_PUNKTE_SATIRE + " INTEGER," +
                    KandidatenTable.COLUMN_NAME_BIOGRAPHIE + " TEXT," +
                    KandidatenTable.COLUMN_NAME_WAHLPROGRAMM + " TEXT" +
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
                    ThesenTable.COLUMN_NAME_K_CONTRA + " TEXT," +
                    ThesenTable.COLUMN_NAME_W_PRO + " TEXT," +
                    ThesenTable.COLUMN_NAME_W_NEUTRAL + " TEXT," +
                    ThesenTable.COLUMN_NAME_W_CONTRA + " TEXT," +
                    ThesenTable.COLUMN_NAME_K_POSITION + " TEXT" +
                    " )";

    public static final String SQL_DELETE_USERPOSTIONDATATABLE =
            "DROP TABLE IF EXISTS " + UserpositiondataTable.TABLE_NAME;
    public static final String SQL_DELETE_THESENTABLE =
            "DROP TABLE IF EXISTS " + ThesenTable.TABLE_NAME;
    public static final String SQL_DELETE_KANDIDATENTABLE =
            "DROP TABLE IF EXISTS " + KandidatenTable.TABLE_NAME;


    public class ThesenDbHelper extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 10;
        public static final String DATABASE_NAME = "Thes-O-Naise.db";

        public ThesenDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_USERPOSITIONDATATABLE);
            db.execSQL(SQL_CREATE_THESENTABLE);
            db.execSQL(SQL_CREATE_KANDIDATENTABLE);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //onUpgrade(db, oldVersion, newVersion);
            db.execSQL(SQL_DELETE_USERPOSTIONDATATABLE);
            db.execSQL(SQL_DELETE_THESENTABLE);
            db.execSQL(SQL_DELETE_KANDIDATENTABLE);
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

    public void updatepositionwithServerdata(String position, String tid) {

        if (tid != null && position != null) {
            ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
            SQLiteDatabase dbwrite = thesenDbHelper.getWritableDatabase();
            try {
                ContentValues values = new ContentValues();
                values.put(UserpositiondataTable.COLUMN_NAME_LAST_POSITION, position);
                values.put(UserpositiondataTable.COLUMN_NAME_TID, tid);
                values.put(UserpositiondataTable.COLUMN_NAME_SERVER, "true");
                dbwrite.update(UserpositiondataTable.TABLE_NAME, values, "tid=?", new String[]{tid});
            } finally {
                dbwrite.close();
            }
        }
    }

    public JSONObject getallPositions() {
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase db = thesenDbHelper.getReadableDatabase();
        JSONObject mainObj = new JSONObject();
        JSONArray ja = new JSONArray();
        try {

            Cursor c = db.query(UserpositiondataTable.TABLE_NAME, new String[] {UserpositiondataTable.COLUMN_NAME_TID, UserpositiondataTable.COLUMN_NAME_POSITION, UserpositiondataTable.COLUMN_NAME_SERVER, UserpositiondataTable.COLUMN_NAME_LAST_POSITION }, null, null, null, null, null);
            try{
                while (c.moveToNext()) {
                    JSONObject jot = new JSONObject();
                    jot.put("TID", c.getString(0));
                    jot.put("POSITION",  c.getString(1));
                    if(c.getString(2) != null){
                        jot.put("GESENDET", "true");
                    }else{
                        jot.put("GESENDET", "false");
                    }
                    if(c.getString(3) != null) {
                        jot.put("LASTPOSITION", c.getString(3));
                    }else{
                        jot.put("LASTPOSITION", c.getString(1));
                    }
                    ja.put(jot);
                    Log.d("TID:", c.getString(0));
                    Log.d("position:", c.getString(1));
                }
                mainObj.put("Positionen", ja);

            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                c.close();
            }
        }finally {
            db.close();
        }
        return mainObj;
    }

    public String getUserPositionWithTID(String tid) {
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase db = thesenDbHelper.getReadableDatabase();
        String result= "nichts";
        try {
            if(tid != null) {
                Cursor c = db.query(UserpositiondataTable.TABLE_NAME, new String[]{UserpositiondataTable.COLUMN_NAME_TID, UserpositiondataTable.COLUMN_NAME_POSITION}, "tid = ?", new String[]{tid}, null, null, null);
                try {
                    while (c.moveToNext()) {
                        result = c.getString(1);
                        Log.d("TID:", c.getString(0));
                        Log.d("position:", c.getString(1));
                    }

                } finally {
                    c.close();
                }
            }
        }finally {
            db.close();
        }
        return result;
    }

    public void insertThese (String TID, String thesentext, String kategorie, String wahlkreis, Integer likesINT, Integer proINT, Integer neutralINT, Integer contraINT, JSONArray K_PRO, JSONArray  K_NEUTRAL, JSONArray K_CONTRA, JSONArray W_PRO, JSONArray W_NEUTRAL, JSONArray W_CONTRA, JSONArray K_POSITION ){
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase dbwrite = thesenDbHelper.getWritableDatabase();
        SQLiteDatabase dbread = thesenDbHelper.getReadableDatabase();

        try {
                Log.d("TID DATA save", TID);
                Integer likes = likesINT;
                Integer anzahl_pro = proINT;
                Integer anzahl_neutral = neutralINT;
                Integer anzahl_contra = contraINT;
                String k_pro = K_PRO.toString();
                String k_neutral = K_NEUTRAL.toString();
                String k_contra = K_CONTRA.toString();
                String w_pro = W_PRO.toString();
                String w_neutral = W_NEUTRAL.toString();
                String w_contra = W_CONTRA.toString();
                String k_positionen = K_POSITION.toString();

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
                            values.put(ThesenTable.COLUMN_NAME_K_PRO, k_pro);
                            values.put(ThesenTable.COLUMN_NAME_K_NEUTRAL, k_neutral);
                            values.put(ThesenTable.COLUMN_NAME_K_CONTRA, k_contra);
                            values.put(ThesenTable.COLUMN_NAME_W_PRO, w_pro);
                            values.put(ThesenTable.COLUMN_NAME_W_NEUTRAL, w_neutral);
                            values.put(ThesenTable.COLUMN_NAME_W_CONTRA, w_contra);
                            values.put(ThesenTable.COLUMN_NAME_K_POSITION, k_positionen);
                            dbwrite.insert(ThesenTable.TABLE_NAME, null, values);
                        } finally {
                            cursor.close();
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
                            values.put(ThesenTable.COLUMN_NAME_K_PRO, k_pro);
                            values.put(ThesenTable.COLUMN_NAME_K_NEUTRAL, k_neutral);
                            values.put(ThesenTable.COLUMN_NAME_K_CONTRA, k_contra);
                            values.put(ThesenTable.COLUMN_NAME_W_PRO, w_pro);
                            values.put(ThesenTable.COLUMN_NAME_W_NEUTRAL, w_neutral);
                            values.put(ThesenTable.COLUMN_NAME_W_CONTRA, w_contra);
                            values.put(ThesenTable.COLUMN_NAME_K_POSITION, k_positionen);
                            dbwrite.update(ThesenTable.TABLE_NAME, values, "tid=?", new String[]{TID});
                        }finally {
                            cursor.close();
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
                        String k_pro = c.getString(8);
                        String k_neutral = c.getString(9);
                        String k_contra = c.getString(10);
                        String w_pro = c.getString(11);
                        String w_neutral = c.getString(12);
                        String w_contra = c.getString(13);
                        String k_positionen = c.getString(14);
                        JSONArray k_pro_Array = new JSONArray(k_pro);
                        JSONArray k_neutral_Array = new JSONArray(k_neutral);
                        JSONArray k_contra_Array = new JSONArray(k_contra);
                        JSONArray w_pro_Array = new JSONArray(w_pro);
                        JSONArray w_neutral_Array = new JSONArray(w_neutral);
                        JSONArray w_contra_Array = new JSONArray(w_contra);
                        JSONArray k_positionen_Array = new JSONArray(k_positionen);
                        thesenModels.add(new ThesenModel(tid, thesentext, kategorie2, wahlkreis, likes, anzahl_pro, anzahl_neutral, anzahl_contra, k_pro_Array, k_neutral_Array, k_contra_Array, w_pro_Array, w_neutral_Array, w_contra_Array, k_positionen_Array));
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
                        String k_pro = c.getString(8);
                        String k_neutral = c.getString(9);
                        String k_contra = c.getString(10);
                        String w_pro = c.getString(11);
                        String w_neutral = c.getString(12);
                        String w_contra = c.getString(13);
                        String k_positionen = c.getString(14);
                        JSONArray k_pro_Array = new JSONArray(k_pro);
                        JSONArray k_neutral_Array = new JSONArray(k_neutral);
                        JSONArray k_contra_Array = new JSONArray(k_contra);
                        JSONArray w_pro_Array = new JSONArray(w_pro);
                        JSONArray w_neutral_Array = new JSONArray(w_neutral);
                        JSONArray w_contra_Array = new JSONArray(w_contra);
                        JSONArray k_positionen_Array = new JSONArray(k_positionen);
                        result = new ThesenModel(tid, thesentext, kategorie2, wahlkreis, likes, anzahl_pro, anzahl_neutral, anzahl_contra, k_pro_Array, k_neutral_Array, k_contra_Array, w_pro_Array, w_neutral_Array, w_contra_Array, k_positionen_Array);
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
    public String getThesentextWithTID(String TID){
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase db = thesenDbHelper.getReadableDatabase();
        Cursor  c = null;
        String result = null;
        try{
            if(TID != null){
                c = db.query(ThesenTable.TABLE_NAME, new String[]{ThesenTable.COLUMN_NAME_TID, ThesenTable.COLUMN_NAME_THESENTEXT}, "tid = ?", new String[]{TID}, null, null, null);
                if (c.getCount() < 1){
                    return null;
                }else {
                    while (c.moveToNext()) {
                        String tid = c.getString(0);
                        Log.d("TID DATA get", tid);
                        result = c.getString(1);
                    }
                }
            }
        } finally {
            c.close();
            db.close();
        }
        return result;
    }

    public JSONArray getBegruendungenWithTIDandPosition(String TID, String position){
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
                        if(position.equals("PRO")) result = postionen_pro_Array;
                        if(position.equals("NEUTRAL")) result = postionen_neutral_Array;
                        if(position.equals("CONTRA")) result = postionen_contra_Array;
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

    public void insertKandidat(String KID, String vorname, String nachname, String partei, String email, String wahlkreis, JSONArray beantworteteThesen ){
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase dbwrite = thesenDbHelper.getWritableDatabase();
        SQLiteDatabase dbread = thesenDbHelper.getReadableDatabase();

        try {
            Log.d("KID save", KID);

            if (KID != null){
                Cursor cursor = dbread.query(KandidatenTable.TABLE_NAME, new String[]{ThesenTable.COLUMN_NAME_TID}, "kid = ?", new String[]{KID}, null, null, null);
                if (cursor.getCount() < 1) {
                    try {
                        ContentValues values = new ContentValues();
                        String beantwortete_Thesen = beantworteteThesen.toString();
                        values.put(KandidatenTable.COLUMN_NAME_KID, KID);
                        values.put(KandidatenTable.COLUMN_NAME_VORNAME, vorname);
                        values.put(KandidatenTable.COLUMN_NAME_NACHNAME, nachname);
                        values.put(KandidatenTable.COLUMN_NAME_PARTEI, partei);
                        values.put(KandidatenTable.COLUMN_NAME_EMAIL, email);
                        values.put(KandidatenTable.COLUMN_NAME_WAHLKREIS, wahlkreis);
                        values.put(KandidatenTable.COLUMN_NAME_BEANTWORTETETHESEN, beantwortete_Thesen);

                        dbwrite.insert(KandidatenTable.TABLE_NAME, null, values);
                    } finally {
                        cursor.close();
                    }
                }else {
                    try {
                        ContentValues values = new ContentValues();
                        String beantwortete_Thesen = beantworteteThesen.toString();
                        values.put(KandidatenTable.COLUMN_NAME_KID, KID);
                        values.put(KandidatenTable.COLUMN_NAME_VORNAME, vorname);
                        values.put(KandidatenTable.COLUMN_NAME_NACHNAME, nachname);
                        values.put(KandidatenTable.COLUMN_NAME_PARTEI, partei);
                        values.put(KandidatenTable.COLUMN_NAME_EMAIL, email);
                        values.put(KandidatenTable.COLUMN_NAME_WAHLKREIS, wahlkreis);
                        values.put(KandidatenTable.COLUMN_NAME_BEANTWORTETETHESEN, beantwortete_Thesen);
                        dbwrite.update(KandidatenTable.TABLE_NAME, values, "kid=?", new String[]{KID});
                    }finally {
                        cursor.close();
                    }
                }
            }
        } finally {
            dbread.close();
            dbwrite.close();
        }
    }

    public void updateKandidatScore(String KID, Integer Insgesamt, Integer Lokal, Integer Umwelt, Integer AP, Integer Satire){
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase dbwrite = thesenDbHelper.getWritableDatabase();
        try {
            if (KID != null){
                Log.d("Update KandidatenScore:", KID);
                    ContentValues values = new ContentValues();
                    values.put(KandidatenTable.COLUMN_NAME_KID, KID);
                    //TODO MEHR KATEGORIEN ?
                    values.put(KandidatenTable.COLUMN_NAME_PUNKTE_INSGESAMT, Insgesamt);
                    values.put(KandidatenTable.COLUMN_NAME_PUNKTE_LOKAL, Lokal);
                    values.put(KandidatenTable.COLUMN_NAME_PUNKTE_UMWELT, Umwelt);
                    values.put(KandidatenTable.COLUMN_NAME_PUNKTE_AP, AP);
                    values.put(KandidatenTable.COLUMN_NAME_PUNKTE_SATIRE, Satire);
                    dbwrite.update(KandidatenTable.TABLE_NAME, values, "kid=?", new String[]{KID});
                }
        } finally {
            dbwrite.close();
        }
    }

    public ArrayList<KandidatenModel> getAllKandidaten (String wahlkreis){
        ArrayList<KandidatenModel> kandidatenModels = new ArrayList<>();
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase db = thesenDbHelper.getReadableDatabase();


        try{
            if(wahlkreis != null) {
                Cursor  c = db.query(KandidatenTable.TABLE_NAME, new String[]{KandidatenTable.COLUMN_NAME_KID, KandidatenTable.COLUMN_NAME_VORNAME, KandidatenTable.COLUMN_NAME_NACHNAME, KandidatenTable.COLUMN_NAME_PARTEI, KandidatenTable.COLUMN_NAME_EMAIL, KandidatenTable.COLUMN_NAME_WAHLKREIS, KandidatenTable.COLUMN_NAME_BEANTWORTETETHESEN, KandidatenTable.COLUMN_NAME_PUNKTE_INSGESAMT, KandidatenTable.COLUMN_NAME_PUNKTE_LOKAL, KandidatenTable.COLUMN_NAME_PUNKTE_UMWELT, KandidatenTable.COLUMN_NAME_PUNKTE_AP, KandidatenTable.COLUMN_NAME_PUNKTE_SATIRE}, "wahlkreis = ?", new String[]{wahlkreis}, null, null, null);

                try {
                    while (c.moveToNext()) {
                        String kid = c.getString(0);
                        Log.d("KID DATA get", kid);
                        String vorname = c.getString(1);
                        String nachname = c.getString(2);
                        String partei = c.getString(3);
                        String email = c.getString(4);
                        String beantwortete_Thesen = c.getString(6);
                        //TODO MEHR KATEGORIEN ?
                        Integer Punkte_Insgesamt = c.getInt(7);
                        Integer Punkte_Lokal = c.getInt(8);
                        Integer Punkte_Umwelt = c.getInt(9);
                        Integer Punkte_AP = c.getInt(10);
                        Integer Punkte_Satire = c.getInt(11);
                        JSONArray beantworteteThesen = new JSONArray(beantwortete_Thesen);
                        kandidatenModels.add(new KandidatenModel(kid,  vorname, nachname,  partei,  email, wahlkreis,  beantworteteThesen, Punkte_Insgesamt, Punkte_Lokal, Punkte_Umwelt, Punkte_AP, Punkte_Satire));
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
        return kandidatenModels;
    }

    public ArrayList<KandidatenModel> sortKandidatenScore (String kategorie, String wahlkreis){
        ArrayList<KandidatenModel> kandidatenModels = new ArrayList<>();
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase db = thesenDbHelper.getReadableDatabase();


        try{
            if(kategorie != null && wahlkreis != null) {
                Cursor  c = db.query(KandidatenTable.TABLE_NAME, new String[]{KandidatenTable.COLUMN_NAME_KID, KandidatenTable.COLUMN_NAME_VORNAME, KandidatenTable.COLUMN_NAME_NACHNAME, KandidatenTable.COLUMN_NAME_PARTEI, KandidatenTable.COLUMN_NAME_EMAIL, KandidatenTable.COLUMN_NAME_WAHLKREIS, KandidatenTable.COLUMN_NAME_BEANTWORTETETHESEN, KandidatenTable.COLUMN_NAME_PUNKTE_INSGESAMT, KandidatenTable.COLUMN_NAME_PUNKTE_LOKAL, KandidatenTable.COLUMN_NAME_PUNKTE_UMWELT, KandidatenTable.COLUMN_NAME_PUNKTE_AP, KandidatenTable.COLUMN_NAME_PUNKTE_SATIRE}, "wahlkreis = ?", new String[]{wahlkreis}, null, null, kategorie + " ASC");

                try {
                    while (c.moveToNext()) {
                        String kid = c.getString(0);
                        Log.d("KID SORT get", kid);
                        String vorname = c.getString(1);
                        String nachname = c.getString(2);
                        String partei = c.getString(3);
                        String email = c.getString(4);
                        String beantwortete_Thesen = c.getString(6);
                        //TODO MEHR KATEGORIEN ?
                        Integer Punkte_Insgesamt = c.getInt(7);
                        Integer Punkte_Lokal = c.getInt(8);
                        Integer Punkte_Umwelt = c.getInt(9);
                        Integer Punkte_AP = c.getInt(10);
                        Integer Punkte_Satire = c.getInt(11);
                        JSONArray beantworteteThesen = new JSONArray(beantwortete_Thesen);
                        Integer check = 0;
                        String kategorie2 = "";
                        if(kategorie.equals(KandidatenTable.COLUMN_NAME_PUNKTE_INSGESAMT)) check = 1;
                        if(kategorie.equals(KandidatenTable.COLUMN_NAME_PUNKTE_LOKAL)) kategorie2 = "Lokal";
                        if(kategorie.equals(KandidatenTable.COLUMN_NAME_PUNKTE_UMWELT)) kategorie2 = "Umwelt";
                        if(kategorie.equals(KandidatenTable.COLUMN_NAME_PUNKTE_AP)) kategorie2 = "Aussenpolitik";
                        if(kategorie.equals(KandidatenTable.COLUMN_NAME_PUNKTE_SATIRE)) kategorie2 = "Satire";
                        for (int i =0; i<beantworteteThesen.length(); i++){
                            JSONObject object = (JSONObject) beantworteteThesen.get(i);
                            String objectKategorie = object.getString("KATEGORIE");
                            if(objectKategorie.equals(kategorie2))check = 1;
                        }

                        if (check == 1) kandidatenModels.add(new KandidatenModel(kid,  vorname, nachname,  partei,  email, wahlkreis,  beantworteteThesen, Punkte_Insgesamt, Punkte_Lokal, Punkte_Umwelt, Punkte_AP, Punkte_Satire));
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
        return kandidatenModels;
    }

    public KandidatenModel getKandidat(String kid){
        KandidatenModel kandidatenModel = null;
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase db = thesenDbHelper.getReadableDatabase();


        try{
            if(kid != null) {
                Cursor  c = db.query(KandidatenTable.TABLE_NAME, new String[]{KandidatenTable.COLUMN_NAME_KID, KandidatenTable.COLUMN_NAME_VORNAME, KandidatenTable.COLUMN_NAME_NACHNAME, KandidatenTable.COLUMN_NAME_PARTEI, KandidatenTable.COLUMN_NAME_EMAIL, KandidatenTable.COLUMN_NAME_WAHLKREIS, KandidatenTable.COLUMN_NAME_BEANTWORTETETHESEN, KandidatenTable.COLUMN_NAME_PUNKTE_INSGESAMT, KandidatenTable.COLUMN_NAME_PUNKTE_LOKAL, KandidatenTable.COLUMN_NAME_PUNKTE_UMWELT, KandidatenTable.COLUMN_NAME_PUNKTE_AP, KandidatenTable.COLUMN_NAME_PUNKTE_SATIRE}, "kid = ?", new String[]{kid}, null, null, null);

                try {
                    while (c.moveToNext()) {

                        Log.d("KID DATA get", kid);
                        String vorname = c.getString(1);
                        String nachname = c.getString(2);
                        String partei = c.getString(3);
                        String email = c.getString(4);
                        String wahlkreis = c.getString(5);
                        String beantwortete_Thesen = c.getString(6);
                        JSONArray beantworteteThesen = new JSONArray(beantwortete_Thesen);
                        //TODO MEHR KATEGORIEN ?
                        Integer Punkte_Insgesamt = c.getInt(7);
                        Integer Punkte_Lokal = c.getInt(8);
                        Integer Punkte_Umwelt = c.getInt(9);
                        Integer Punkte_AP = c.getInt(10);
                        Integer Punkte_Satire = c.getInt(11);
                        kandidatenModel = new KandidatenModel(kid,  vorname, nachname,  partei,  email, wahlkreis,  beantworteteThesen, Punkte_Insgesamt, Punkte_Lokal, Punkte_Umwelt, Punkte_AP, Punkte_Satire);
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
        return kandidatenModel;
    }

}
