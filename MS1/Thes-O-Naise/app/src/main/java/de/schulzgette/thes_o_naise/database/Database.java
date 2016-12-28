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
import java.util.Collections;
import java.util.Comparator;

import de.schulzgette.thes_o_naise.Models.BegruendungModel;
import de.schulzgette.thes_o_naise.Models.BenachrichtigungModel;
import de.schulzgette.thes_o_naise.Models.KandidatenModel;
import de.schulzgette.thes_o_naise.Models.KommentarModel;
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
    public static abstract class MeineThesenTable implements BaseColumns{
        public static final String TABLE_NAME = "meinethesen";
        public static final String COLUMN_NAME_TID = "tid";
        public static final String COLUMN_NAME_KPROLÄNGE = "kpro";
        public static final String COLUMN_NAME_KNEUTRALLÄNGE = "kneutral";
        public static final String COLUMN_NAME_KCONTRALÄNGE = "kcontra";
        public static final String COLUMN_NAME_KPOSITIONLÄNGE = "kposition";
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
        public static final String COLUMN_NAME_BEGRUENDUNGEN = "begruendungen";
    }

    public static abstract  class ThesenTable implements BaseColumns{
        public static final String TABLE_NAME = "thesendata2";
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

    public static abstract  class BegruendungTable implements BaseColumns{
        public static final String TABLE_NAME = "begruendungen";
        public static final String COLUMN_NAME_PRIMARYKEY = "pk";
        public static final String COLUMN_NAME_TID = "tid";
        public static final String COLUMN_NAME_POSITION = "position";
        public static final String COLUMN_NAME_BID = "bid";
        public static final String COLUMN_NAME_LIKE = "like";
    }
    public static abstract  class BenachrichtigungsTable implements BaseColumns{
        public static final String TABLE_NAME = "benachrichtigung";
        public static final String COLUMN_NAME_PRIMARYKEY = "pk";
        public static final String COLUMN_NAME_TID = "tid";
        public static final String COLUMN_NAME_TYP = "typ";
        public static final String COLUMN_NAME_SATZ = "satz";
        public static final String COLUMN_NAME_UID = "uid";
        public static final String COLUMN_NAME_CREATED = "created_at";
    }

    public static final String SQL_CREATE_BENACHRICHTIGUNGSTABLE =
            "CREATE TABLE " + BenachrichtigungsTable.TABLE_NAME + " (" +
                    BenachrichtigungsTable.COLUMN_NAME_PRIMARYKEY + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    BenachrichtigungsTable.COLUMN_NAME_TID + " TEXT," +
                    BenachrichtigungsTable.COLUMN_NAME_TYP + " TEXT," +
                    BenachrichtigungsTable.COLUMN_NAME_SATZ + " TEXT," +
                    BenachrichtigungsTable.COLUMN_NAME_UID + " TEXT," +
                    BenachrichtigungsTable.COLUMN_NAME_CREATED + " DATETIME DEFAULT CURRENT_TIMESTAMP" +
                    " )";

    public static final String SQL_CREATE_USERPOSITIONDATATABLE =
            "CREATE TABLE " + UserpositiondataTable.TABLE_NAME + " (" +
                    UserpositiondataTable.COLUMN_NAME_TID + " STRING PRIMARY KEY," +
                    UserpositiondataTable.COLUMN_NAME_POSITION + " TEXT," +
                    UserpositiondataTable.COLUMN_NAME_SERVER + " TEXT," +
                    UserpositiondataTable.COLUMN_NAME_LAST_POSITION + " TEXT" +
                    " )";

    public static final String SQL_CREATE_MEINETHESENTABLE =
            "CREATE TABLE " + MeineThesenTable.TABLE_NAME + " (" +
                    MeineThesenTable.COLUMN_NAME_TID + " STRING PRIMARY KEY," +
                    MeineThesenTable.COLUMN_NAME_KPROLÄNGE + " INTEGER," +
                    MeineThesenTable.COLUMN_NAME_KNEUTRALLÄNGE + " INTEGER," +
                    MeineThesenTable.COLUMN_NAME_KCONTRALÄNGE + " INTEGER," +
                    MeineThesenTable.COLUMN_NAME_KPOSITIONLÄNGE + " INTEGER" +
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
                    KandidatenTable.COLUMN_NAME_BEGRUENDUNGEN + " TEXT," +
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

    public static final String SQL_CREATE_BEGRUENDUNGDATATABLE =
            "CREATE TABLE " + BegruendungTable.TABLE_NAME + " (" +
                    BegruendungTable.COLUMN_NAME_PRIMARYKEY + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    BegruendungTable.COLUMN_NAME_TID + " TEXT," +
                    BegruendungTable.COLUMN_NAME_POSITION + " TEXT," +
                    BegruendungTable.COLUMN_NAME_BID + " TEXT," +
                    BegruendungTable.COLUMN_NAME_LIKE + " INTEGER" +
                    " )";

    public static final String SQL_DELETE_USERPOSTIONDATATABLE =
            "DROP TABLE IF EXISTS " + UserpositiondataTable.TABLE_NAME;
    public static final String SQL_DELETE_THESENTABLE =
            "DROP TABLE IF EXISTS " + ThesenTable.TABLE_NAME;
    public static final String SQL_DELETE_KANDIDATENTABLE =
            "DROP TABLE IF EXISTS " + KandidatenTable.TABLE_NAME;
    public static final String SQL_DELETE_BEGRUENDUNGDATATABLE =
            "DROP TABLE IF EXISTS " + BegruendungTable.TABLE_NAME;
    public static final String SQL_DELETE_MEINETHESENTABLE =
            "DROP TABLE IF EXISTS " + MeineThesenTable.TABLE_NAME;
    public static final String SQL_DELETE_BENACHRICHTIGUNGSTABLE =
            "DROP TABLE IF EXISTS " + BenachrichtigungsTable.TABLE_NAME;


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
            db.execSQL(SQL_CREATE_BEGRUENDUNGDATATABLE);
            db.execSQL(SQL_CREATE_MEINETHESENTABLE);
            db.execSQL(SQL_CREATE_BENACHRICHTIGUNGSTABLE);

        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            onUpgrade(db, oldVersion, newVersion);
            db.execSQL(SQL_DELETE_USERPOSTIONDATATABLE);
            db.execSQL(SQL_DELETE_THESENTABLE);
            db.execSQL(SQL_DELETE_KANDIDATENTABLE);
            db.execSQL(SQL_DELETE_BEGRUENDUNGDATATABLE);
            db.execSQL(SQL_DELETE_MEINETHESENTABLE);
            db.execSQL(SQL_DELETE_BENACHRICHTIGUNGSTABLE);

            onCreate(db);
        }

        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL(SQL_DELETE_USERPOSTIONDATATABLE);
            db.execSQL(SQL_DELETE_THESENTABLE);
            db.execSQL(SQL_DELETE_KANDIDATENTABLE);
            db.execSQL(SQL_DELETE_BEGRUENDUNGDATATABLE);
            db.execSQL(SQL_DELETE_MEINETHESENTABLE);
            db.execSQL(SQL_DELETE_BENACHRICHTIGUNGSTABLE);
            onCreate(db);
        }
    }


    private Context context;

    public  Database(Context context) { this.context = context; }

    public void updateMeineThesen(String tid){
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase dbwrite = thesenDbHelper.getWritableDatabase();
            if(tid != null){
                try {
                    ContentValues values = new ContentValues();
                    values.put(MeineThesenTable.COLUMN_NAME_TID, tid);
                    values.put(MeineThesenTable.COLUMN_NAME_KPROLÄNGE, 0);
                    values.put(MeineThesenTable.COLUMN_NAME_KNEUTRALLÄNGE, 0);
                    values.put(MeineThesenTable.COLUMN_NAME_KCONTRALÄNGE, 0);
                    values.put(MeineThesenTable.COLUMN_NAME_KPOSITIONLÄNGE, 0);

                    dbwrite.insert(MeineThesenTable.TABLE_NAME, null, values);
                } finally {
                    dbwrite.close();
                }
            }
    }
    public ArrayList<String> getMeineThesen(){
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase dbread = thesenDbHelper.getReadableDatabase();
        Cursor cursor;
        ArrayList<String> result = new ArrayList<>();
        cursor = dbread.query(MeineThesenTable.TABLE_NAME, new String[]{MeineThesenTable.COLUMN_NAME_TID}, null, null, null, null, null);
        try {
            while(cursor.moveToNext()){
                result.add(cursor.getString(0));
            }
        }finally{
            cursor.close();
        }
        return result;
    }

    public ArrayList<BenachrichtigungModel> getArraylistMeineThesen (){
        ArrayList<BenachrichtigungModel> benachrichtigungModels = new ArrayList<>();
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase db = thesenDbHelper.getReadableDatabase();
        ArrayList<String> thesenliste = getMeineThesen();

        try{
            Cursor c2 = db.query(BenachrichtigungsTable.TABLE_NAME, new String[]{BenachrichtigungsTable.COLUMN_NAME_TID, BenachrichtigungsTable.COLUMN_NAME_UID, BenachrichtigungsTable.COLUMN_NAME_SATZ, BenachrichtigungsTable.COLUMN_NAME_CREATED}, null, null,null, null, BenachrichtigungsTable.COLUMN_NAME_CREATED + " DESC");
            while(c2.moveToNext()){
                String TID = c2.getString(0);
                String UID = c2.getString(1);
                String Satz = c2.getString(2);
                String thesentext = getThesentextWithTID(TID);
                benachrichtigungModels.add(new BenachrichtigungModel(TID, thesentext, UID, Satz));
            }
            c2.close();
            if(thesenliste != null) {
                for(int i = 0; i < thesenliste.size(); i++) {
                    String TID = thesenliste.get(i);
                    Cursor c = db.query(BenachrichtigungsTable.TABLE_NAME, new String[]{BenachrichtigungsTable.COLUMN_NAME_TID}, "tid = ?", new String[]{TID}, null, null, null);
                    if (c.getCount() < 1) {
                        String thesentext = getThesentextWithTID(TID);
                        benachrichtigungModels.add(new BenachrichtigungModel(TID, thesentext, "", "Keine Neuigkeiten zur These"));
                    }
                    c.close();
                }
            }
        } finally {
            db.close();
        }
        return benachrichtigungModels;
    }

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

    public void vergleichJsonArrayBenachrichtigung(Integer altelänge, JSONArray neu, String typ, String TID, SQLiteDatabase dbwrite) throws JSONException {
        if(neu.length() > altelänge){
            Log.d("NEUES", "Alte Länge ist kleiner als neue");
            Log.d("Bei der : ", TID);
            Integer anzahl = neu.length() - altelänge;
            for(int i = 0; i < anzahl; i++){
                if(typ.equals("position")){
                    JSONObject jsonObject = null;
                    if(altelänge == 0 && neu.length() == 1){
                        jsonObject = neu.getJSONObject(0);
                    }else{
                        jsonObject = neu.getJSONObject(neu.length()-i);
                    }
                    String uid = jsonObject.getString("UID");
                    String position = jsonObject.getString("POS");
                    String name = getKandidatUsername(uid);
                    String satz = "Der Kandidat "+name +" hat sich zur These "+ position+" positioniert.";


                    ContentValues values = new ContentValues();
                    values.put(BenachrichtigungsTable.COLUMN_NAME_TID, TID);
                    values.put(BenachrichtigungsTable.COLUMN_NAME_TYP, "Meine Thesen");
                    values.put(BenachrichtigungsTable.COLUMN_NAME_SATZ, satz);
                    values.put(BenachrichtigungsTable.COLUMN_NAME_UID, uid);
                    dbwrite.insert(BenachrichtigungsTable.TABLE_NAME, null, values);
                }else {
                    JSONObject begruendung = null;
                    if(altelänge == 0 && neu.length() == 1){
                        begruendung = neu.getJSONObject(0);
                    }else{
                        begruendung = neu.getJSONObject(neu.length()-i);
                    }
                    String uid = begruendung.getString("UID");
                    String name = getKandidatUsername(uid);
                    String satz = "Der Kandidat "+name +" hat eine Begründung "+ typ+" hinzugefügt.";

                    ContentValues values = new ContentValues();
                    values.put(BenachrichtigungsTable.COLUMN_NAME_TID, TID);
                    values.put(BenachrichtigungsTable.COLUMN_NAME_TYP, "Meine Thesen");
                    values.put(BenachrichtigungsTable.COLUMN_NAME_SATZ, satz);
                    values.put(BenachrichtigungsTable.COLUMN_NAME_UID, uid);
                    dbwrite.insert(BenachrichtigungsTable.TABLE_NAME, null, values);
                }
            }
        }
    }

    public void insertThese (String TID, String thesentext, String kategorie, String wahlkreis, Integer likesINT, Integer proINT, Integer neutralINT, Integer contraINT, JSONArray K_PRO, JSONArray  K_NEUTRAL, JSONArray K_CONTRA, JSONArray W_PRO, JSONArray W_NEUTRAL, JSONArray W_CONTRA, JSONArray K_POSITION ){
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase dbwrite = thesenDbHelper.getWritableDatabase();
        SQLiteDatabase dbread = thesenDbHelper.getReadableDatabase();
        ArrayList<String> meineThesenliste = getMeineThesen();
        if(meineThesenliste.contains(TID)){
            Log.d("MEINE THESE", TID);
            Cursor cursor = dbread.query(MeineThesenTable.TABLE_NAME, new String[]{MeineThesenTable.COLUMN_NAME_TID, MeineThesenTable.COLUMN_NAME_KPROLÄNGE, MeineThesenTable.COLUMN_NAME_KNEUTRALLÄNGE, MeineThesenTable.COLUMN_NAME_KCONTRALÄNGE, MeineThesenTable.COLUMN_NAME_KPOSITIONLÄNGE}, "tid = ?", new String[]{TID}, null, null, null);
            while (cursor.moveToNext()){
                Integer kprolaenge = cursor.getInt(1);
                Integer kneutrallaenge = cursor.getInt(2);
                Integer kcontralaenge = cursor.getInt(3);
                Integer kpositionlaenge = cursor.getInt(4);
                Log.d("KPOSITION1", kpositionlaenge.toString());
                Integer Kpop = K_POSITION.length();
                Log.d("KPOSITION2", Kpop.toString());
                try {
                    vergleichJsonArrayBenachrichtigung(kprolaenge, K_PRO, "Pro", TID, dbwrite);
                    vergleichJsonArrayBenachrichtigung(kneutrallaenge, K_NEUTRAL, "Neutral", TID, dbwrite);
                    vergleichJsonArrayBenachrichtigung(kcontralaenge, K_CONTRA, "Contra", TID, dbwrite);
                    vergleichJsonArrayBenachrichtigung(kpositionlaenge, K_POSITION, "position", TID, dbwrite);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            cursor.close();
            ContentValues values2 = new ContentValues();
            values2.put(MeineThesenTable.COLUMN_NAME_TID, TID);
            values2.put(MeineThesenTable.COLUMN_NAME_KPROLÄNGE, K_PRO.length());
            values2.put(MeineThesenTable.COLUMN_NAME_KNEUTRALLÄNGE, K_NEUTRAL.length());
            values2.put(MeineThesenTable.COLUMN_NAME_KCONTRALÄNGE, K_CONTRA.length());
            values2.put(MeineThesenTable.COLUMN_NAME_KPOSITIONLÄNGE, K_POSITION.length());
            dbwrite.update(MeineThesenTable.TABLE_NAME, values2, "tid=?", new String[]{TID});
        }
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
                Cursor  c = db.query(ThesenTable.TABLE_NAME, new String[]{ThesenTable.COLUMN_NAME_TID, ThesenTable.COLUMN_NAME_THESENTEXT, ThesenTable.COLUMN_NAME_KATEGORIE, ThesenTable.COLUMN_NAME_WAHLKREIS, ThesenTable.COLUMN_NAME_LIKES, ThesenTable.COLUMN_NAME_ANZAHL_PRO, ThesenTable.COLUMN_NAME_ANZAHL_NEUTRAL, ThesenTable.COLUMN_NAME_ANZAHL_CONTRA, ThesenTable.COLUMN_NAME_K_PRO, ThesenTable.COLUMN_NAME_K_NEUTRAL, ThesenTable.COLUMN_NAME_K_CONTRA, ThesenTable.COLUMN_NAME_W_PRO, ThesenTable.COLUMN_NAME_W_NEUTRAL, ThesenTable.COLUMN_NAME_W_CONTRA, ThesenTable.COLUMN_NAME_K_POSITION}, "kategorie = ?", new String[]{kategorie}, null, null, null);

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
                c = db.query(ThesenTable.TABLE_NAME, new String[]{ThesenTable.COLUMN_NAME_TID, ThesenTable.COLUMN_NAME_THESENTEXT, ThesenTable.COLUMN_NAME_KATEGORIE, ThesenTable.COLUMN_NAME_WAHLKREIS, ThesenTable.COLUMN_NAME_LIKES, ThesenTable.COLUMN_NAME_ANZAHL_PRO, ThesenTable.COLUMN_NAME_ANZAHL_NEUTRAL, ThesenTable.COLUMN_NAME_ANZAHL_CONTRA, ThesenTable.COLUMN_NAME_K_PRO, ThesenTable.COLUMN_NAME_K_NEUTRAL, ThesenTable.COLUMN_NAME_K_CONTRA, ThesenTable.COLUMN_NAME_W_PRO, ThesenTable.COLUMN_NAME_W_NEUTRAL, ThesenTable.COLUMN_NAME_W_CONTRA, ThesenTable.COLUMN_NAME_K_POSITION}, "tid = ?", new String[]{TID}, null, null, null);
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

    public ArrayList<BegruendungModel> getBegruendungenWithTIDandPosition(String TID, String position){
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase db = thesenDbHelper.getReadableDatabase();
        Cursor  c = null;
        ArrayList<BegruendungModel> result = new ArrayList<>();
        try{
            if(TID != null && position != null){
                c = db.query(ThesenTable.TABLE_NAME, new String[]{ThesenTable.COLUMN_NAME_TID, ThesenTable.COLUMN_NAME_K_PRO, ThesenTable.COLUMN_NAME_K_NEUTRAL, ThesenTable.COLUMN_NAME_K_CONTRA, ThesenTable.COLUMN_NAME_W_PRO, ThesenTable.COLUMN_NAME_W_NEUTRAL, ThesenTable.COLUMN_NAME_W_CONTRA, ThesenTable.COLUMN_NAME_K_POSITION}, "tid = ?", new String[]{TID}, null, null, null);
                if (c.getCount() < 1){
                    return null;
                }else {
                    while (c.moveToNext()) {
                        String tid = c.getString(0);
                        String k_pro = c.getString(1);
                        String k_neutral = c.getString(2);
                        String k_contra = c.getString(3);
                        String w_pro = c.getString(4);
                        String w_neutral = c.getString(5);
                        String w_contra = c.getString(6);
                        JSONArray k_pro_Array = new JSONArray(k_pro);
                        JSONArray k_neutral_Array = new JSONArray(k_neutral);
                        JSONArray k_contra_Array = new JSONArray(k_contra);
                        JSONArray w_pro_Array = new JSONArray(w_pro);
                        JSONArray w_neutral_Array = new JSONArray(w_neutral);
                        JSONArray w_contra_Array = new JSONArray(w_contra);
                        if(position.equals("Pro")){
                           result =  makeArraylistWithJSONArrays(k_pro_Array, w_pro_Array);
                        }
                       if(position.equals("Neutral")) {
                           result =  makeArraylistWithJSONArrays(k_neutral_Array, w_neutral_Array);
                       }
                       if(position.equals("Contra")){
                           result =  makeArraylistWithJSONArrays(k_contra_Array, w_contra_Array);
                       }
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

    public ArrayList<BegruendungModel> makeArraylistWithJSONArrays (JSONArray k, JSONArray w){
        ArrayList<BegruendungModel> resultkandidaten = new ArrayList<>();
        ArrayList<BegruendungModel> resultwaehler = new ArrayList<>();
        ArrayList<BegruendungModel> result = new ArrayList<>();
        try{
            for(int i = 0; i<k.length(); i++){
                JSONObject object =(JSONObject) k.get(i);
                String UID = object.getString("UID");
                String Username = object.getString("Username");
                String begruendungstext = object.getString("Text");
                Integer likes = object.getInt("likes");
                JSONArray kommentare = (JSONArray) object.get("Kommentare");
                ArrayList<KommentarModel> kommentareliste = new ArrayList<>();
                if (kommentare.length() > 0) {
                    for (int j = 0; j < kommentare.length(); j++) {
                        JSONObject kommentar = (JSONObject) kommentare.get(j);
                        String kommentartext = kommentar.getString("Kommentar");
                        String uid = kommentar.getString("UID");
                        String username = kommentar.getString("USERNAME");
                        kommentareliste.add(new KommentarModel(kommentartext,uid,username));
                    }
                }
                resultkandidaten.add(new BegruendungModel(kommentareliste, begruendungstext, likes, UID, "k", Username));
            }
            Collections.sort(resultkandidaten, new Comparator<BegruendungModel>() {
                //Begründungen werden absteigend nach Likes sortiert
                @Override
                public int compare(BegruendungModel obj1, BegruendungModel obj2) {
                    return (obj1.getLikes() > obj2.getLikes()) ? -1: (obj1.getLikes() > obj2.getLikes()) ? 1:0 ;
                }
            });
            for(int i = 0; i<w.length(); i++){
                JSONObject object =(JSONObject) w.get(i);
                String UID = object.getString("UID");
                String Username = object.getString("Username");
                String begruendungstext = object.getString("Text");
                Integer likes = object.getInt("likes");
                JSONArray kommentare = (JSONArray) object.get("Kommentare");
                ArrayList<KommentarModel> kommentareliste = new ArrayList<>();
                if (kommentare.length() > 0) {
                    for (int j = 0; j < kommentare.length(); j++) {
                        JSONObject kommentar = (JSONObject) kommentare.get(j);
                        String kommentartext = kommentar.getString("Kommentar");
                        String uid = kommentar.getString("UID");
                        String username = kommentar.getString("USERNAME");
                        kommentareliste.add(new KommentarModel(kommentartext,uid,username));
                    }
                }
                resultwaehler.add(new BegruendungModel(kommentareliste, begruendungstext, likes, UID, "w", Username));
            }
            Collections.sort(resultwaehler, new Comparator<BegruendungModel>() {
                //Begründungen werden absteigend nach Likes sortiert
                @Override
                public int compare(BegruendungModel obj1, BegruendungModel obj2) {
                    return (obj1.getLikes() > obj2.getLikes()) ? -1: (obj1.getLikes() > obj2.getLikes()) ? 1:0 ;
                }
            });
        }catch (JSONException e) {
            e.printStackTrace();
        }
        result.addAll(resultkandidaten);
        result.addAll(resultwaehler);
        return  result;
    }

    public void insertKandidat(String KID, String vorname, String nachname, String partei, String email, String wahlkreis, JSONArray beantworteteThesen, JSONArray begruendungen, JSONObject biografie, JSONObject wahlprogramm){
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
                        String begruendungen2 = begruendungen.toString();
                        String biografie2 = biografie.toString();
                        String wahlprogramm2 = wahlprogramm.toString();
                        values.put(KandidatenTable.COLUMN_NAME_KID, KID);
                        values.put(KandidatenTable.COLUMN_NAME_VORNAME, vorname);
                        values.put(KandidatenTable.COLUMN_NAME_NACHNAME, nachname);
                        values.put(KandidatenTable.COLUMN_NAME_PARTEI, partei);
                        values.put(KandidatenTable.COLUMN_NAME_EMAIL, email);
                        values.put(KandidatenTable.COLUMN_NAME_WAHLKREIS, wahlkreis);
                        values.put(KandidatenTable.COLUMN_NAME_BEANTWORTETETHESEN, beantwortete_Thesen);
                        values.put(KandidatenTable.COLUMN_NAME_BEGRUENDUNGEN, begruendungen2);
                        values.put(KandidatenTable.COLUMN_NAME_BIOGRAPHIE, biografie2);
                        values.put(KandidatenTable.COLUMN_NAME_WAHLPROGRAMM, wahlprogramm2);

                        dbwrite.insert(KandidatenTable.TABLE_NAME, null, values);
                    } finally {
                        cursor.close();
                    }
                }else {
                    try {
                        ContentValues values = new ContentValues();
                        String beantwortete_Thesen = beantworteteThesen.toString();
                        String begruendungen2 = begruendungen.toString();
                        String biografie2 = biografie.toString();
                        String wahlprogramm2 = wahlprogramm.toString();
                        values.put(KandidatenTable.COLUMN_NAME_KID, KID);
                        values.put(KandidatenTable.COLUMN_NAME_VORNAME, vorname);
                        values.put(KandidatenTable.COLUMN_NAME_NACHNAME, nachname);
                        values.put(KandidatenTable.COLUMN_NAME_PARTEI, partei);
                        values.put(KandidatenTable.COLUMN_NAME_EMAIL, email);
                        values.put(KandidatenTable.COLUMN_NAME_WAHLKREIS, wahlkreis);
                        values.put(KandidatenTable.COLUMN_NAME_BEANTWORTETETHESEN, beantwortete_Thesen);
                        values.put(KandidatenTable.COLUMN_NAME_BEGRUENDUNGEN, begruendungen2);
                        values.put(KandidatenTable.COLUMN_NAME_BIOGRAPHIE, biografie2);
                        values.put(KandidatenTable.COLUMN_NAME_WAHLPROGRAMM, wahlprogramm2);
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
                Cursor  c = db.query(KandidatenTable.TABLE_NAME, new String[]{KandidatenTable.COLUMN_NAME_KID, KandidatenTable.COLUMN_NAME_VORNAME, KandidatenTable.COLUMN_NAME_NACHNAME, KandidatenTable.COLUMN_NAME_PARTEI, KandidatenTable.COLUMN_NAME_EMAIL, KandidatenTable.COLUMN_NAME_WAHLKREIS, KandidatenTable.COLUMN_NAME_BEANTWORTETETHESEN, KandidatenTable.COLUMN_NAME_PUNKTE_INSGESAMT, KandidatenTable.COLUMN_NAME_PUNKTE_LOKAL, KandidatenTable.COLUMN_NAME_PUNKTE_UMWELT, KandidatenTable.COLUMN_NAME_PUNKTE_AP, KandidatenTable.COLUMN_NAME_PUNKTE_SATIRE, KandidatenTable.COLUMN_NAME_BEGRUENDUNGEN, KandidatenTable.COLUMN_NAME_BIOGRAPHIE, KandidatenTable.COLUMN_NAME_WAHLPROGRAMM}, "wahlkreis = ?", new String[]{wahlkreis}, null, null, null);

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
                        String begruendungen2 = c.getString(12);
                        String biografie2 = c.getString(13);
                        String wahlprogramm2 = c.getString(14);
                        JSONArray beantworteteThesen = new JSONArray(beantwortete_Thesen);
                        JSONArray begruendungen = new JSONArray(begruendungen2);
                        JSONObject biografie = new JSONObject(biografie2);
                        JSONObject wahlprogramm = new JSONObject(wahlprogramm2);
                        kandidatenModels.add(new KandidatenModel(kid,  vorname, nachname,  partei,  email, wahlkreis,  beantworteteThesen, Punkte_Insgesamt, Punkte_Lokal, Punkte_Umwelt, Punkte_AP, Punkte_Satire, begruendungen, biografie, wahlprogramm));
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
                Cursor  c = db.query(KandidatenTable.TABLE_NAME, new String[]{KandidatenTable.COLUMN_NAME_KID, KandidatenTable.COLUMN_NAME_VORNAME, KandidatenTable.COLUMN_NAME_NACHNAME, KandidatenTable.COLUMN_NAME_PARTEI, KandidatenTable.COLUMN_NAME_EMAIL, KandidatenTable.COLUMN_NAME_WAHLKREIS, KandidatenTable.COLUMN_NAME_BEANTWORTETETHESEN, KandidatenTable.COLUMN_NAME_PUNKTE_INSGESAMT, KandidatenTable.COLUMN_NAME_PUNKTE_LOKAL, KandidatenTable.COLUMN_NAME_PUNKTE_UMWELT, KandidatenTable.COLUMN_NAME_PUNKTE_AP, KandidatenTable.COLUMN_NAME_PUNKTE_SATIRE, KandidatenTable.COLUMN_NAME_BEGRUENDUNGEN, KandidatenTable.COLUMN_NAME_BIOGRAPHIE, KandidatenTable.COLUMN_NAME_WAHLPROGRAMM}, "wahlkreis = ?", new String[]{wahlkreis}, null, null, kategorie + " ASC");

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
                        String begruendungen2 = c.getString(12);
                        String biografie2 = c.getString(13);
                        String wahlprogramm2 = c.getString(14);
                        JSONArray beantworteteThesen = new JSONArray(beantwortete_Thesen);
                        JSONArray begruendungen = new JSONArray(begruendungen2);
                        JSONObject biografie = new JSONObject(biografie2);
                        JSONObject wahlprogramm = new JSONObject(wahlprogramm2);
                        Integer check = 0;
                        String kategorie2 = "";
                        if(kategorie.equals(KandidatenTable.COLUMN_NAME_PUNKTE_INSGESAMT)){
                            if(Punkte_Insgesamt==0){
                                check = 0;
                            }else{
                                check = 1;
                            }
                        }
                        if(kategorie.equals(KandidatenTable.COLUMN_NAME_PUNKTE_LOKAL)) kategorie2 = "Lokal";
                        if(kategorie.equals(KandidatenTable.COLUMN_NAME_PUNKTE_UMWELT)) kategorie2 = "Umwelt";
                        if(kategorie.equals(KandidatenTable.COLUMN_NAME_PUNKTE_AP)) kategorie2 = "Aussenpolitik";
                        if(kategorie.equals(KandidatenTable.COLUMN_NAME_PUNKTE_SATIRE)) kategorie2 = "Satire";
                        for (int i =0; i<beantworteteThesen.length(); i++){
                            JSONObject object = (JSONObject) beantworteteThesen.get(i);
                            String objectKategorie = object.getString("KATEGORIE");
                            if(objectKategorie.equals(kategorie2))check = 1;
                        }

                        if (check == 1) kandidatenModels.add(new KandidatenModel(kid,  vorname, nachname,  partei,  email, wahlkreis,  beantworteteThesen, Punkte_Insgesamt, Punkte_Lokal, Punkte_Umwelt, Punkte_AP, Punkte_Satire, begruendungen, biografie, wahlprogramm));
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
                Cursor  c = db.query(KandidatenTable.TABLE_NAME, new String[]{KandidatenTable.COLUMN_NAME_KID, KandidatenTable.COLUMN_NAME_VORNAME, KandidatenTable.COLUMN_NAME_NACHNAME, KandidatenTable.COLUMN_NAME_PARTEI, KandidatenTable.COLUMN_NAME_EMAIL, KandidatenTable.COLUMN_NAME_WAHLKREIS, KandidatenTable.COLUMN_NAME_BEANTWORTETETHESEN, KandidatenTable.COLUMN_NAME_PUNKTE_INSGESAMT, KandidatenTable.COLUMN_NAME_PUNKTE_LOKAL, KandidatenTable.COLUMN_NAME_PUNKTE_UMWELT, KandidatenTable.COLUMN_NAME_PUNKTE_AP, KandidatenTable.COLUMN_NAME_PUNKTE_SATIRE, KandidatenTable.COLUMN_NAME_BEGRUENDUNGEN, KandidatenTable.COLUMN_NAME_BIOGRAPHIE, KandidatenTable.COLUMN_NAME_WAHLPROGRAMM}, "kid = ?", new String[]{kid}, null, null, null);

                try {
                    while (c.moveToNext()) {

                        Log.d("KID DATA get", kid);
                        String vorname = c.getString(1);
                        String nachname = c.getString(2);
                        String partei = c.getString(3);
                        String email = c.getString(4);
                        String wahlkreis = c.getString(5);
                        String beantwortete_Thesen = c.getString(6);
                        String begruendungen2 = c.getString(12);
                        String biografie2 = c.getString(13);
                        String wahlprogramm2 = c.getString(14);
                        JSONArray beantworteteThesen = new JSONArray(beantwortete_Thesen);
                        JSONArray begruendungen = new JSONArray(begruendungen2);
                        JSONObject biografie = new JSONObject(biografie2);
                        JSONObject wahlprogramm = new JSONObject(wahlprogramm2);
                        //TODO MEHR KATEGORIEN ?
                        Integer Punkte_Insgesamt = c.getInt(7);
                        Integer Punkte_Lokal = c.getInt(8);
                        Integer Punkte_Umwelt = c.getInt(9);
                        Integer Punkte_AP = c.getInt(10);
                        Integer Punkte_Satire = c.getInt(11);
                        kandidatenModel = new KandidatenModel(kid,  vorname, nachname,  partei,  email, wahlkreis,  beantworteteThesen, Punkte_Insgesamt, Punkte_Lokal, Punkte_Umwelt, Punkte_AP, Punkte_Satire, begruendungen, biografie, wahlprogramm);
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

    public String getKandidatUsername(String kid){
        String result = null;
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase db = thesenDbHelper.getReadableDatabase();


        try{
            if(kid != null) {
                Cursor  c = db.query(KandidatenTable.TABLE_NAME, new String[]{KandidatenTable.COLUMN_NAME_KID, KandidatenTable.COLUMN_NAME_VORNAME, KandidatenTable.COLUMN_NAME_NACHNAME}, "kid = ?", new String[]{kid}, null, null, null);

                try {
                    while (c.moveToNext()) {

                        Log.d("KID DATA get", kid);
                        String vorname = c.getString(1);
                        String nachname = c.getString(2);
                        result = vorname + " " + nachname;
                    }

                } finally {
                    c.close();
                }
            }
        } finally {
            db.close();
        }
        return result;
    }

    public void insertLikeBegruendung(String TID, String BID, String Position, Integer Like){
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase dbread = thesenDbHelper.getReadableDatabase();
        Cursor cursor;
        if (TID != null && Position != null) {
            cursor = dbread.query(BegruendungTable.TABLE_NAME, new String[]{BegruendungTable.COLUMN_NAME_TID, BegruendungTable.COLUMN_NAME_BID, BegruendungTable.COLUMN_NAME_POSITION}, "tid=? AND bid=? AND position=?", new String[]{TID, BID, Position}, null, null, null);

            SQLiteDatabase dbwrite = thesenDbHelper.getWritableDatabase();

            if (cursor.getCount() < 1) {
                try {
                    ContentValues values = new ContentValues();
                    values.put(BegruendungTable.COLUMN_NAME_POSITION, Position);
                    values.put(BegruendungTable.COLUMN_NAME_TID, TID);
                    values.put(BegruendungTable.COLUMN_NAME_BID, BID);
                    values.put(BegruendungTable.COLUMN_NAME_LIKE, Like);
                    dbwrite.insert(BegruendungTable.TABLE_NAME, null, values);
                } finally {
                    dbread.close();
                    dbwrite.close();
                }
            } else {
                try {
                    ContentValues values = new ContentValues();
                    values.put(BegruendungTable.COLUMN_NAME_POSITION, Position);
                    values.put(BegruendungTable.COLUMN_NAME_TID, TID);
                    values.put(BegruendungTable.COLUMN_NAME_BID, BID);
                    values.put(BegruendungTable.COLUMN_NAME_LIKE, Like);
                    dbwrite.update(BegruendungTable.TABLE_NAME, values, "tid=? AND bid=? AND position=?", new String[]{TID, BID, Position});
                } finally {
                    dbread.close();
                    dbwrite.close();
                }
            }
        }
    }

    public Integer getLikeBegruendung(String TID, String BID, String Position ){
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase dbread = thesenDbHelper.getReadableDatabase();
        Cursor cursor;
        Integer result = 0;
        if (TID != null && Position != null && BID != null) {
            try{
                cursor = dbread.query(BegruendungTable.TABLE_NAME, new String[]{BegruendungTable.COLUMN_NAME_TID, BegruendungTable.COLUMN_NAME_BID, BegruendungTable.COLUMN_NAME_POSITION, BegruendungTable.COLUMN_NAME_LIKE}, "tid=? AND bid=? AND position=?", new String[]{TID, BID, Position}, null, null, null);
                while(cursor.moveToNext()){
                    result = cursor.getInt(3);
                }
            } finally {
                dbread.close();
            }

        }
        return result;
    }
}
