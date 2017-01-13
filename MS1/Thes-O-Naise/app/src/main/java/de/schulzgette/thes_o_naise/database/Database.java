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

    public static abstract class AbonnierteThesenTable implements BaseColumns{
        public static final String TABLE_NAME = "abothesen";
        public static final String COLUMN_NAME_TID = "tid";
        public static final String COLUMN_NAME_KPROLÄNGE = "kpro";
        public static final String COLUMN_NAME_KNEUTRALLÄNGE = "kneutral";
        public static final String COLUMN_NAME_KCONTRALÄNGE = "kcontra";
        public static final String COLUMN_NAME_KPOSITIONLÄNGE = "kposition";
    }

    public static abstract class AbonnierteKandidatenTable implements BaseColumns{
        public static final String TABLE_NAME = "abokandidaten";
        public static final String COLUMN_NAME_KID = "kid";
        public static final String COLUMN_NAME_BEGRUENDUNGENLÄNGE = "kbegruendung";
        public static final String COLUMN_NAME_KPOSITIONLÄNGE = "kposition";
    }


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
        public static final String COLUMN_NAME_PUNKTE_DrogenP = "punkte_drogen";
        public static final String COLUMN_NAME_PUNKTE_BildungsP = "punkte_bildung";
        public static final String COLUMN_NAME_PUNKTE_InnenP = "punkte_innenp";
        public static final String COLUMN_NAME_PUNKTE_WirtschaftP = "punkte_witschaftp";
        public static final String COLUMN_NAME_PUNKTE_EnergieP = "punkte_energiep";
        public static final String COLUMN_NAME_PUNKTE_Demokratie = "punkte_demokratie";
        public static final String COLUMN_NAME_PUNKTE_Justiz = "punkte_justiz";
        public static final String COLUMN_NAME_PUNKTE_SozialP = "punkte_sozial";
        public static final String COLUMN_NAME_PUNKTE_LandwirtschaftP = "punkte_landwirtschaft";
        public static final String COLUMN_NAME_PUNKTE_FamilienP = "punkte_familien";
        public static final String COLUMN_NAME_PUNKTE_RentenP = "punkte_renten";
        public static final String COLUMN_NAME_PUNKTE_GesundheitP = "punkte_gesundheit";
        public static final String COLUMN_NAME_PUNKTE_VerkehrP = "punkte_verkehrp";
        public static final String COLUMN_NAME_PUNKTE_DigitalP = "punkte_digitalp";
        public static final String COLUMN_NAME_PUNKTE_SATIRE = "punkte_satire";
        public static final String COLUMN_NAME_VERARBEITE_POS = "verarbeite_pos";
        public static final String COLUMN_NAME_ANZAHLLOKAL_POS = "anzahllokal_pos";
        public static final String COLUMN_NAME_ANZAHLUMWELT_POS = "anzahlumwelt_pos";
        public static final String COLUMN_NAME_ANZAHLAP_POS = "anzahlap_pos";
        public static final String COLUMN_NAME_ANZAHLDrogen_POS = "anzahldrogen_pos";
        public static final String COLUMN_NAME_ANZAHLBildungs_POS = "anzahlbildung_pos";
        public static final String COLUMN_NAME_ANZAHLInnen_POS = "anzahlinnen_pos";
        public static final String COLUMN_NAME_ANZAHLWirtschaft_POS = "anzahlwirtschaft_pos";
        public static final String COLUMN_NAME_ANZAHLEnergie_POS = "anzahlenergie_pos";
        public static final String COLUMN_NAME_ANZAHLDEMOKRATIE_POS = "anzahldemokratie_pos";
        public static final String COLUMN_NAME_ANZAHLJutiz_POS = "anzahljustiz_pos";
        public static final String COLUMN_NAME_ANZAHLSozial_POS = "anzahlsozial_pos";
        public static final String COLUMN_NAME_ANZAHLLandwirtschaft_POS = "anzahllandwirtschaft_pos";
        public static final String COLUMN_NAME_ANZAHLFamilien_POS = "anzahlfamilien_pos";
        public static final String COLUMN_NAME_ANZAHLRenten_POS = "anzahlrenten_pos";
        public static final String COLUMN_NAME_ANZAHLGesundheit_POS = "anzahlgesundheit_pos";
        public static final String COLUMN_NAME_ANZAHLVerkehr_POS = "anzahlverkehr_pos";
        public static final String COLUMN_NAME_ANZAHLDigital_POS = "anzahldigital_pos";
        public static final String COLUMN_NAME_ANZAHLSATIRE_POS = "anzahlsatire_pos";
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
        public static final String COLUMN_NAME_TIME = "time";

    }

    public static abstract  class BegruendungTable implements BaseColumns{
        public static final String TABLE_NAME = "begruendungen";
        public static final String COLUMN_NAME_PRIMARYKEY = "pk";
        public static final String COLUMN_NAME_TID = "tid";
        public static final String COLUMN_NAME_POSITION = "position";
        public static final String COLUMN_NAME_BID = "bid";
        public static final String COLUMN_NAME_LIKE = "like";
    }

    public static abstract  class ThesenLikesTable implements BaseColumns{
        public static final String TABLE_NAME = "thesenlikes";
        public static final String COLUMN_NAME_TID = "tid";
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

    public static final String SQL_CREATE_ABONNIERTETHESENTABLE =
            "CREATE TABLE " + AbonnierteThesenTable.TABLE_NAME + " (" +
                    AbonnierteThesenTable.COLUMN_NAME_TID + " STRING PRIMARY KEY," +
                    AbonnierteThesenTable.COLUMN_NAME_KPROLÄNGE + " INTEGER," +
                    AbonnierteThesenTable.COLUMN_NAME_KNEUTRALLÄNGE + " INTEGER," +
                    AbonnierteThesenTable.COLUMN_NAME_KCONTRALÄNGE + " INTEGER," +
                    AbonnierteThesenTable.COLUMN_NAME_KPOSITIONLÄNGE + " INTEGER" +
                    " )";

    public static final String SQL_CREATE_ABONNIERTEKANDIDATENTABLE =
            "CREATE TABLE " + AbonnierteKandidatenTable.TABLE_NAME + " (" +
                    AbonnierteKandidatenTable.COLUMN_NAME_KID + " STRING PRIMARY KEY," +
                    AbonnierteKandidatenTable.COLUMN_NAME_BEGRUENDUNGENLÄNGE + " INTEGER," +
                    AbonnierteKandidatenTable.COLUMN_NAME_KPOSITIONLÄNGE + " INTEGER" +
                    " )";



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
                    KandidatenTable.COLUMN_NAME_PUNKTE_DrogenP + " INTEGER," +
                    KandidatenTable.COLUMN_NAME_PUNKTE_BildungsP + " INTEGER," +
                    KandidatenTable.COLUMN_NAME_PUNKTE_InnenP + " INTEGER," +
                    KandidatenTable.COLUMN_NAME_PUNKTE_WirtschaftP + " INTEGER," +
                    KandidatenTable.COLUMN_NAME_PUNKTE_EnergieP + " INTEGER," +
                    KandidatenTable.COLUMN_NAME_PUNKTE_Demokratie + " INTEGER," +
                    KandidatenTable.COLUMN_NAME_PUNKTE_Justiz + " INTEGER," +
                    KandidatenTable.COLUMN_NAME_PUNKTE_SozialP + " INTEGER," +
                    KandidatenTable.COLUMN_NAME_PUNKTE_LandwirtschaftP + " INTEGER," +
                    KandidatenTable.COLUMN_NAME_PUNKTE_FamilienP + " INTEGER," +
                    KandidatenTable.COLUMN_NAME_PUNKTE_RentenP + " INTEGER," +
                    KandidatenTable.COLUMN_NAME_PUNKTE_GesundheitP+ " INTEGER," +
                    KandidatenTable.COLUMN_NAME_PUNKTE_VerkehrP + " INTEGER," +
                    KandidatenTable.COLUMN_NAME_PUNKTE_DigitalP + " INTEGER," +
                    KandidatenTable.COLUMN_NAME_PUNKTE_SATIRE + " INTEGER," +
                    KandidatenTable.COLUMN_NAME_VERARBEITE_POS + " INTEGER," +
                    KandidatenTable.COLUMN_NAME_ANZAHLLOKAL_POS + " INTEGER," +
                    KandidatenTable.COLUMN_NAME_ANZAHLUMWELT_POS + " INTEGER," +
                    KandidatenTable.COLUMN_NAME_ANZAHLAP_POS + " INTEGER," +
                    KandidatenTable.COLUMN_NAME_ANZAHLDrogen_POS + " INTEGER," +
                    KandidatenTable.COLUMN_NAME_ANZAHLBildungs_POS + " INTEGER," +
                    KandidatenTable.COLUMN_NAME_ANZAHLInnen_POS + " INTEGER," +
                    KandidatenTable.COLUMN_NAME_ANZAHLWirtschaft_POS + " INTEGER," +
                    KandidatenTable.COLUMN_NAME_ANZAHLEnergie_POS + " INTEGER," +
                    KandidatenTable.COLUMN_NAME_ANZAHLDEMOKRATIE_POS + " INTEGER," +
                    KandidatenTable.COLUMN_NAME_ANZAHLJutiz_POS + " INTEGER," +
                    KandidatenTable.COLUMN_NAME_ANZAHLSozial_POS + " INTEGER," +
                    KandidatenTable.COLUMN_NAME_ANZAHLLandwirtschaft_POS + " INTEGER," +
                    KandidatenTable.COLUMN_NAME_ANZAHLFamilien_POS + " INTEGER," +
                    KandidatenTable.COLUMN_NAME_ANZAHLRenten_POS + " INTEGER," +
                    KandidatenTable.COLUMN_NAME_ANZAHLGesundheit_POS + " INTEGER," +
                    KandidatenTable.COLUMN_NAME_ANZAHLVerkehr_POS + " INTEGER," +
                    KandidatenTable.COLUMN_NAME_ANZAHLDigital_POS + " INTEGER," +
                    KandidatenTable.COLUMN_NAME_ANZAHLSATIRE_POS + " INTEGER," +
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
                    ThesenTable.COLUMN_NAME_LIKES + " INTEGER," +
                    ThesenTable.COLUMN_NAME_ANZAHL_PRO + " TEXT," +
                    ThesenTable.COLUMN_NAME_ANZAHL_NEUTRAL + " TEXT," +
                    ThesenTable.COLUMN_NAME_ANZAHL_CONTRA + " TEXT," +
                    ThesenTable.COLUMN_NAME_K_PRO + " TEXT," +
                    ThesenTable.COLUMN_NAME_K_NEUTRAL + " TEXT," +
                    ThesenTable.COLUMN_NAME_K_CONTRA + " TEXT," +
                    ThesenTable.COLUMN_NAME_W_PRO + " TEXT," +
                    ThesenTable.COLUMN_NAME_W_NEUTRAL + " TEXT," +
                    ThesenTable.COLUMN_NAME_W_CONTRA + " TEXT," +
                    ThesenTable.COLUMN_NAME_K_POSITION + " TEXT," +
                    ThesenTable.COLUMN_NAME_TIME + " INTEGER" +
                    " )";

    public static final String SQL_CREATE_THESENLIKESTABLE =
            "CREATE TABLE " + ThesenLikesTable.TABLE_NAME + " (" +
                    ThesenLikesTable.COLUMN_NAME_TID + " STRING PRIMARY KEY," +
                    ThesenLikesTable.COLUMN_NAME_LIKE + " INTEGER" +
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
    public static final String SQL_DELETE_ABONNIERTETHESENTABLE =
            "DROP TABLE IF EXISTS " + AbonnierteThesenTable.TABLE_NAME;
    public static final String SQL_DELETE_BENACHRICHTIGUNGSTABLE =
            "DROP TABLE IF EXISTS " + BenachrichtigungsTable.TABLE_NAME;
    public static final String SQL_DELETE_ABONNIERTEKANDIDATENTABLE =
            "DROP TABLE IF EXISTS " + AbonnierteKandidatenTable.TABLE_NAME;
    public static final String SQL_DELETE_THESENLIKESTABLE =
            "DROP TABLE IF EXISTS " + ThesenLikesTable.TABLE_NAME;


    public class ThesenDbHelper extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 19;
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
            db.execSQL(SQL_CREATE_ABONNIERTETHESENTABLE);
            db.execSQL(SQL_CREATE_ABONNIERTEKANDIDATENTABLE);
            db.execSQL(SQL_CREATE_BENACHRICHTIGUNGSTABLE);
            db.execSQL(SQL_CREATE_THESENLIKESTABLE);

        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            onUpgrade(db, oldVersion, newVersion);
            db.execSQL(SQL_DELETE_USERPOSTIONDATATABLE);
            db.execSQL(SQL_DELETE_THESENTABLE);
            db.execSQL(SQL_DELETE_KANDIDATENTABLE);
            db.execSQL(SQL_DELETE_BEGRUENDUNGDATATABLE);
            db.execSQL(SQL_DELETE_MEINETHESENTABLE);
            db.execSQL(SQL_DELETE_ABONNIERTETHESENTABLE);
            db.execSQL(SQL_DELETE_ABONNIERTEKANDIDATENTABLE);
            db.execSQL(SQL_DELETE_BENACHRICHTIGUNGSTABLE);
            db.execSQL(SQL_DELETE_THESENLIKESTABLE);

            onCreate(db);
        }

        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL(SQL_DELETE_USERPOSTIONDATATABLE);
            db.execSQL(SQL_DELETE_THESENTABLE);
            db.execSQL(SQL_DELETE_KANDIDATENTABLE);
            db.execSQL(SQL_DELETE_BEGRUENDUNGDATATABLE);
            db.execSQL(SQL_DELETE_MEINETHESENTABLE);
            db.execSQL(SQL_DELETE_ABONNIERTETHESENTABLE);
            db.execSQL(SQL_DELETE_ABONNIERTEKANDIDATENTABLE);
            db.execSQL(SQL_DELETE_BENACHRICHTIGUNGSTABLE);
            db.execSQL(SQL_DELETE_THESENLIKESTABLE);
            onCreate(db);
        }

        @Override
        protected void finalize() throws Throwable {
            this.close();
            super.finalize();
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

    public void thesenAbonnieren(String tid){
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase dbwrite = thesenDbHelper.getWritableDatabase();
        if(tid != null){
            try {
                ThesenModel these = getTheseWithTID(tid);
                Integer kprolänge = these.getKPro().length();
                Integer kneutrallänge = these.getKNeutral().length();
                Integer kcontralänge = these.getKContra().length();
                Integer kpositionlänge = these.getKPosition().length();

                ContentValues values = new ContentValues();
                values.put(AbonnierteThesenTable.COLUMN_NAME_TID, tid);
                values.put(AbonnierteThesenTable.COLUMN_NAME_KPROLÄNGE, kprolänge);
                values.put(AbonnierteThesenTable.COLUMN_NAME_KNEUTRALLÄNGE, kneutrallänge);
                values.put(AbonnierteThesenTable.COLUMN_NAME_KCONTRALÄNGE, kcontralänge);
                values.put(AbonnierteThesenTable.COLUMN_NAME_KPOSITIONLÄNGE, kpositionlänge);

                dbwrite.insert(AbonnierteThesenTable.TABLE_NAME, null, values);
                Log.d("THESE ABONNIERT", "DIESE THESE: " + tid);
            } finally {
                dbwrite.close();
            }
        }
    }

    public void thesenDeAbonnieren(String tid){
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase dbwrite = thesenDbHelper.getWritableDatabase();
        if(tid != null){
            try {
                dbwrite.delete(AbonnierteThesenTable.TABLE_NAME, "tid=?", new String[]{tid});
                Log.d("THESE DEABONNIERT", "DIESE THESE: " + tid);
            } finally {
                dbwrite.close();
            }
        }
    }

    public ArrayList<String> getAbonnierteThesen(){
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase dbread = thesenDbHelper.getReadableDatabase();
        Cursor cursor;
        ArrayList<String> result = new ArrayList<>();
        cursor = dbread.query(AbonnierteThesenTable.TABLE_NAME, new String[]{AbonnierteThesenTable.COLUMN_NAME_TID}, null, null, null, null, null);
        try {
            while(cursor.moveToNext()){
                result.add(cursor.getString(0));
            }
        }finally{
            cursor.close();
            dbread.close();
        }
        return result;
    }

    public boolean istTheseAbonniert(String TID){
        ArrayList<String> thesenliste = getAbonnierteThesen();
        boolean result = false;
        if(thesenliste.contains(TID)) {
            result = true;
        }
        return result;
    }

    public void kandidatAbonnieren(String kid){
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase dbwrite = thesenDbHelper.getWritableDatabase();
        if(kid != null){
            try {
                KandidatenModel kandidat = getKandidat(kid);
                Integer kbgründungenlänge = kandidat.getBegruendungen().length();
                Integer kpositionlänge = kandidat.getBeantworteteThesen().length();

                ContentValues values = new ContentValues();
                values.put(AbonnierteKandidatenTable.COLUMN_NAME_KID, kid);
                values.put(AbonnierteKandidatenTable.COLUMN_NAME_BEGRUENDUNGENLÄNGE, kbgründungenlänge);
                values.put(AbonnierteKandidatenTable.COLUMN_NAME_KPOSITIONLÄNGE, kpositionlänge);

                dbwrite.insert(AbonnierteKandidatenTable.TABLE_NAME, null, values);
                Log.d("Kandidat ABONNIERT", "KID: " + kid);
            } finally {
                dbwrite.close();
            }
        }
    }

    public void kandidatDeAbonnieren(String kid){
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase dbwrite = thesenDbHelper.getWritableDatabase();
        if(kid != null){
            try {
                dbwrite.delete(AbonnierteKandidatenTable.TABLE_NAME, "kid=?", new String[]{kid});
                Log.d("KANDIDAT DEABONNIERT", "KID: " + kid);
            } finally {
                dbwrite.close();
            }
        }
    }

    public ArrayList<String> getAbonnierteKandidaten(){
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase dbread = thesenDbHelper.getReadableDatabase();
        Cursor cursor;
        ArrayList<String> result = new ArrayList<>();
        cursor = dbread.query(AbonnierteKandidatenTable.TABLE_NAME, new String[]{AbonnierteKandidatenTable.COLUMN_NAME_KID}, null, null, null, null, null);
        try {
            while(cursor.moveToNext()){
                result.add(cursor.getString(0));
            }
        }finally{
            cursor.close();
            dbread.close();
        }
        return result;
    }

    public boolean istKandidatAbonniert(String KID){
        ArrayList<String> kandidatenliste = getAbonnierteKandidaten();
        boolean result = false;
        if(kandidatenliste.contains(KID)) {
            result = true;
        }
        return result;
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
            dbread.close();
        }
        return result;
    }

    public ArrayList<BenachrichtigungModel> getArraylistMeineThesen(){
        ArrayList<BenachrichtigungModel> benachrichtigungModels = new ArrayList<>();
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase db = thesenDbHelper.getReadableDatabase();
        ArrayList<String> thesenliste = getMeineThesen();

        try{
            Cursor c2 = db.query(BenachrichtigungsTable.TABLE_NAME, new String[]{BenachrichtigungsTable.COLUMN_NAME_TID, BenachrichtigungsTable.COLUMN_NAME_UID, BenachrichtigungsTable.COLUMN_NAME_SATZ, BenachrichtigungsTable.COLUMN_NAME_CREATED}, "typ = ?", new String[]{"Meine Thesen"},null, null, BenachrichtigungsTable.COLUMN_NAME_CREATED + " DESC");
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

    public ArrayList<BenachrichtigungModel> getArraylistAbonnierteThesen(){
        ArrayList<BenachrichtigungModel> benachrichtigungModels = new ArrayList<>();
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase db = thesenDbHelper.getReadableDatabase();
        ArrayList<String> thesenliste = getAbonnierteThesen();

        try{
            Cursor c2 = db.query(BenachrichtigungsTable.TABLE_NAME, new String[]{BenachrichtigungsTable.COLUMN_NAME_TID, BenachrichtigungsTable.COLUMN_NAME_UID, BenachrichtigungsTable.COLUMN_NAME_SATZ, BenachrichtigungsTable.COLUMN_NAME_CREATED},"typ = ?", new String[]{"Abonniertes"},null, null, BenachrichtigungsTable.COLUMN_NAME_CREATED + " DESC");
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
        String result= "keine";
        try {
            if(tid != null) {
                Cursor c = db.query(UserpositiondataTable.TABLE_NAME, new String[]{UserpositiondataTable.COLUMN_NAME_TID, UserpositiondataTable.COLUMN_NAME_POSITION}, "tid = ?", new String[]{tid}, null, null, null);
                try {
                    while (c.moveToNext()) {
                        result = c.getString(1);
                        Log.d("TID:", c.getString(0) + "position: "+  c.getString(1) );
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

    public void vergleichJsonArrayBenachrichtigung(Integer altelänge, JSONArray neu, String typ, String TID,  String benachrichtungstyp) throws JSONException {
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase dbwrite = thesenDbHelper.getWritableDatabase();
        try{
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
                        values.put(BenachrichtigungsTable.COLUMN_NAME_TYP, benachrichtungstyp);
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
                        values.put(BenachrichtigungsTable.COLUMN_NAME_TYP, benachrichtungstyp);
                        values.put(BenachrichtigungsTable.COLUMN_NAME_SATZ, satz);
                        values.put(BenachrichtigungsTable.COLUMN_NAME_UID, uid);
                        dbwrite.insert(BenachrichtigungsTable.TABLE_NAME, null, values);
                    }
                }
            }
        }finally {
            dbwrite.close();
        }
    }

    public void insertThese (String TID, String thesentext, String kategorie, String wahlkreis, Integer likesINT, Integer proINT, Integer neutralINT, Integer contraINT, JSONArray K_PRO, JSONArray  K_NEUTRAL, JSONArray K_CONTRA, JSONArray W_PRO, JSONArray W_NEUTRAL, JSONArray W_CONTRA, JSONArray K_POSITION, Long Time ){
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase dbwrite = thesenDbHelper.getWritableDatabase();
        SQLiteDatabase dbread = thesenDbHelper.getReadableDatabase();
        ArrayList<String> meineThesenliste = getMeineThesen();
        ArrayList<String> abonnierteThesenliste = getAbonnierteThesen();
        if(meineThesenliste.contains(TID) || abonnierteThesenliste.contains(TID)){

            Cursor cursor = null;
            if(meineThesenliste.contains(TID)){
                cursor = dbread.query(MeineThesenTable.TABLE_NAME, new String[]{MeineThesenTable.COLUMN_NAME_TID, MeineThesenTable.COLUMN_NAME_KPROLÄNGE, MeineThesenTable.COLUMN_NAME_KNEUTRALLÄNGE, MeineThesenTable.COLUMN_NAME_KCONTRALÄNGE, MeineThesenTable.COLUMN_NAME_KPOSITIONLÄNGE}, "tid = ?", new String[]{TID}, null, null, null);
            }else if(abonnierteThesenliste.contains(TID)) cursor = dbread.query(AbonnierteThesenTable.TABLE_NAME, new String[]{AbonnierteThesenTable.COLUMN_NAME_TID, AbonnierteThesenTable.COLUMN_NAME_KPROLÄNGE, AbonnierteThesenTable.COLUMN_NAME_KNEUTRALLÄNGE, AbonnierteThesenTable.COLUMN_NAME_KCONTRALÄNGE, AbonnierteThesenTable.COLUMN_NAME_KPOSITIONLÄNGE}, "tid = ?", new String[]{TID}, null, null, null);

            while (cursor.moveToNext()){
                Integer kprolaenge = cursor.getInt(1);
                Integer kneutrallaenge = cursor.getInt(2);
                Integer kcontralaenge = cursor.getInt(3);
                Integer kpositionlaenge = cursor.getInt(4);
                try {
                    if(meineThesenliste.contains(TID)){
                        vergleichJsonArrayBenachrichtigung(kprolaenge, K_PRO, "Pro", TID,  "Meine Thesen");
                        vergleichJsonArrayBenachrichtigung(kneutrallaenge, K_NEUTRAL, "Neutral", TID, "Meine Thesen");
                        vergleichJsonArrayBenachrichtigung(kcontralaenge, K_CONTRA, "Contra", TID, "Meine Thesen");
                        vergleichJsonArrayBenachrichtigung(kpositionlaenge, K_POSITION, "position", TID, "Meine Thesen");
                    }else if (abonnierteThesenliste.contains(TID)){
                        vergleichJsonArrayBenachrichtigung(kprolaenge, K_PRO, "Pro", TID, "Abonniertes");
                        vergleichJsonArrayBenachrichtigung(kneutrallaenge, K_NEUTRAL, "Neutral", TID, "Abonniertes");
                        vergleichJsonArrayBenachrichtigung(kcontralaenge, K_CONTRA, "Contra", TID, "Abonniertes");
                        vergleichJsonArrayBenachrichtigung(kpositionlaenge, K_POSITION, "position", TID, "Abonniertes");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            cursor.close();
            if(meineThesenliste.contains(TID)) {
                ContentValues values2 = new ContentValues();
                values2.put(MeineThesenTable.COLUMN_NAME_TID, TID);
                values2.put(MeineThesenTable.COLUMN_NAME_KPROLÄNGE, K_PRO.length());
                values2.put(MeineThesenTable.COLUMN_NAME_KNEUTRALLÄNGE, K_NEUTRAL.length());
                values2.put(MeineThesenTable.COLUMN_NAME_KCONTRALÄNGE, K_CONTRA.length());
                values2.put(MeineThesenTable.COLUMN_NAME_KPOSITIONLÄNGE, K_POSITION.length());
                dbwrite.update(MeineThesenTable.TABLE_NAME, values2, "tid=?", new String[]{TID});
            }else if (abonnierteThesenliste.contains(TID)){
                ContentValues values2 = new ContentValues();
                values2.put(AbonnierteThesenTable.COLUMN_NAME_TID, TID);
                values2.put(AbonnierteThesenTable.COLUMN_NAME_KPROLÄNGE, K_PRO.length());
                values2.put(AbonnierteThesenTable.COLUMN_NAME_KNEUTRALLÄNGE, K_NEUTRAL.length());
                values2.put(AbonnierteThesenTable.COLUMN_NAME_KCONTRALÄNGE, K_CONTRA.length());
                values2.put(AbonnierteThesenTable.COLUMN_NAME_KPOSITIONLÄNGE, K_POSITION.length());
                dbwrite.update(AbonnierteThesenTable.TABLE_NAME, values2, "tid=?", new String[]{TID});
            }
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
                            values.put(ThesenTable.COLUMN_NAME_TIME, Time);
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
                            values.put(ThesenTable.COLUMN_NAME_TIME, Time);
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

    public ArrayList<ThesenModel> getArraylistThesen (String kategorie, String order, Boolean unbeantwortet){
        ArrayList<ThesenModel> thesenModels = new ArrayList<>();
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase db = thesenDbHelper.getReadableDatabase();


        try{
            if(kategorie != null) {
                Cursor  c = db.query(ThesenTable.TABLE_NAME, new String[]{ThesenTable.COLUMN_NAME_TID, ThesenTable.COLUMN_NAME_THESENTEXT, ThesenTable.COLUMN_NAME_KATEGORIE, ThesenTable.COLUMN_NAME_WAHLKREIS, ThesenTable.COLUMN_NAME_LIKES, ThesenTable.COLUMN_NAME_ANZAHL_PRO, ThesenTable.COLUMN_NAME_ANZAHL_NEUTRAL, ThesenTable.COLUMN_NAME_ANZAHL_CONTRA, ThesenTable.COLUMN_NAME_K_PRO, ThesenTable.COLUMN_NAME_K_NEUTRAL, ThesenTable.COLUMN_NAME_K_CONTRA, ThesenTable.COLUMN_NAME_W_PRO, ThesenTable.COLUMN_NAME_W_NEUTRAL, ThesenTable.COLUMN_NAME_W_CONTRA, ThesenTable.COLUMN_NAME_K_POSITION, ThesenTable.COLUMN_NAME_TIME}, "kategorie = ?", new String[]{kategorie}, null, null, order);

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
                        Integer time = c.getInt(15);
                        JSONArray k_pro_Array = new JSONArray(k_pro);
                        JSONArray k_neutral_Array = new JSONArray(k_neutral);
                        JSONArray k_contra_Array = new JSONArray(k_contra);
                        JSONArray w_pro_Array = new JSONArray(w_pro);
                        JSONArray w_neutral_Array = new JSONArray(w_neutral);
                        JSONArray w_contra_Array = new JSONArray(w_contra);
                        JSONArray k_positionen_Array = new JSONArray(k_positionen);
                        Boolean abo = istTheseAbonniert(tid);
                        String position = getUserPositionWithTID(tid);

                        Boolean check = false;
                        if(unbeantwortet){
                            //Log.d("position:  ", position + " " + tid);
                            if(position.equals("keine")) check = true;
                        }else{
                            check = true;
                        }
                        if(check) thesenModels.add(new ThesenModel(tid, thesentext, kategorie2, wahlkreis, likes, anzahl_pro, anzahl_neutral, anzahl_contra, time, k_pro_Array, k_neutral_Array, k_contra_Array, w_pro_Array, w_neutral_Array, w_contra_Array, k_positionen_Array, abo, position));
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
                c = db.query(ThesenTable.TABLE_NAME, new String[]{ThesenTable.COLUMN_NAME_TID, ThesenTable.COLUMN_NAME_THESENTEXT, ThesenTable.COLUMN_NAME_KATEGORIE, ThesenTable.COLUMN_NAME_WAHLKREIS, ThesenTable.COLUMN_NAME_LIKES, ThesenTable.COLUMN_NAME_ANZAHL_PRO, ThesenTable.COLUMN_NAME_ANZAHL_NEUTRAL, ThesenTable.COLUMN_NAME_ANZAHL_CONTRA, ThesenTable.COLUMN_NAME_K_PRO, ThesenTable.COLUMN_NAME_K_NEUTRAL, ThesenTable.COLUMN_NAME_K_CONTRA, ThesenTable.COLUMN_NAME_W_PRO, ThesenTable.COLUMN_NAME_W_NEUTRAL, ThesenTable.COLUMN_NAME_W_CONTRA, ThesenTable.COLUMN_NAME_K_POSITION, ThesenTable.COLUMN_NAME_TIME}, "tid = ?", new String[]{TID}, null, null, null);
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
                        Integer time = c.getInt(15);
                        JSONArray k_pro_Array = new JSONArray(k_pro);
                        JSONArray k_neutral_Array = new JSONArray(k_neutral);
                        JSONArray k_contra_Array = new JSONArray(k_contra);
                        JSONArray w_pro_Array = new JSONArray(w_pro);
                        JSONArray w_neutral_Array = new JSONArray(w_neutral);
                        JSONArray w_contra_Array = new JSONArray(w_contra);
                        JSONArray k_positionen_Array = new JSONArray(k_positionen);
                        Boolean abo = istTheseAbonniert(tid);
                        String position = getUserPositionWithTID(tid);

                        result = new ThesenModel(tid, thesentext, kategorie2, wahlkreis, likes, anzahl_pro, anzahl_neutral, anzahl_contra, time, k_pro_Array, k_neutral_Array, k_contra_Array, w_pro_Array, w_neutral_Array, w_contra_Array, k_positionen_Array, abo, position);
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

    public void vergleichJsonArrayBenachrichtigungKandidat(Integer altelänge, JSONArray neu, String typ, String KID,  String benachrichtungstyp) throws JSONException {
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase dbwrite = thesenDbHelper.getWritableDatabase();
        try{
            if(neu.length() > altelänge){
                Log.d("NEUES", "Alte Länge ist kleiner als neue");
                Log.d("Bei dem : ", KID);
                Integer anzahl = neu.length() - altelänge;
                for(int i = 0; i < anzahl; i++){
                    if(typ.equals("position")){
                        JSONObject jsonObject = null;
                        if(altelänge == 0 && neu.length() == 1){
                            jsonObject = neu.getJSONObject(0);
                        }else{
                            jsonObject = neu.getJSONObject(neu.length()-i);
                        }
                        String TID = jsonObject.getString("TID");
                        String position = jsonObject.getString("POS");
                        String name = getKandidatUsername(KID);
                        String satz = "Der Kandidat "+name +" hat sich zur These "+ position+" positioniert.";


                        ContentValues values = new ContentValues();
                        values.put(BenachrichtigungsTable.COLUMN_NAME_TID, TID);
                        values.put(BenachrichtigungsTable.COLUMN_NAME_TYP, benachrichtungstyp);
                        values.put(BenachrichtigungsTable.COLUMN_NAME_SATZ, satz);
                        values.put(BenachrichtigungsTable.COLUMN_NAME_UID, KID);
                        dbwrite.insert(BenachrichtigungsTable.TABLE_NAME, null, values);
                    }else {
                        JSONObject begruendung = null;
                        if(altelänge == 0 && neu.length() == 1){
                            begruendung = neu.getJSONObject(0);
                        }else{
                            begruendung = neu.getJSONObject(neu.length()-i);
                        }
                        String TID = begruendung.getString("TID");
                        String name = getKandidatUsername(KID);
                        String richtung = begruendung.getString("Richtung");
                        String satz = "Der Kandidat "+name +" hat eine Begründung "+ richtung+" hinzugefügt.";

                        ContentValues values = new ContentValues();
                        values.put(BenachrichtigungsTable.COLUMN_NAME_TID, TID);
                        values.put(BenachrichtigungsTable.COLUMN_NAME_TYP, benachrichtungstyp);
                        values.put(BenachrichtigungsTable.COLUMN_NAME_SATZ, satz);
                        values.put(BenachrichtigungsTable.COLUMN_NAME_UID, KID);
                        dbwrite.insert(BenachrichtigungsTable.TABLE_NAME, null, values);
                    }
                }
            }
        }finally {
            dbwrite.close();
        }
    }

    public void insertKandidat(String KID, String vorname, String nachname, String partei, String email, String wahlkreis, JSONArray beantworteteThesen, JSONArray begruendungen, JSONObject biografie, JSONObject wahlprogramm){
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase dbwrite = thesenDbHelper.getWritableDatabase();
        SQLiteDatabase dbread = thesenDbHelper.getReadableDatabase();
        if (istKandidatAbonniert(KID)){
            Cursor cursor = dbread.query(AbonnierteKandidatenTable.TABLE_NAME, new String[]{AbonnierteKandidatenTable.COLUMN_NAME_KID, AbonnierteKandidatenTable.COLUMN_NAME_BEGRUENDUNGENLÄNGE, AbonnierteKandidatenTable.COLUMN_NAME_KPOSITIONLÄNGE},"kid = ?", new String[]{KID}, null, null, null );
            while(cursor.moveToNext()){
                Integer kbegründgenlänge = cursor.getInt(1);
                Integer kposition = cursor.getInt(2);
                try{
                    vergleichJsonArrayBenachrichtigungKandidat(kbegründgenlänge, begruendungen, "begründung", KID, "Abonniertes");
                    vergleichJsonArrayBenachrichtigungKandidat(kposition, beantworteteThesen, "position", KID, "Abonniertes");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            cursor.close();

            ContentValues values2 = new ContentValues();
            values2.put(AbonnierteKandidatenTable.COLUMN_NAME_KID, KID);
            values2.put(AbonnierteKandidatenTable.COLUMN_NAME_BEGRUENDUNGENLÄNGE, begruendungen.length());
            values2.put(AbonnierteKandidatenTable.COLUMN_NAME_KPOSITIONLÄNGE, beantworteteThesen.length());
            dbwrite.update(AbonnierteKandidatenTable.TABLE_NAME, values2, "kid=?", new String[]{KID});
        }
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

    public void updateKandidatScore(String KID, Integer Insgesamt, Integer Lokal, Integer Umwelt, Integer AP, Integer Drogenp, Integer Bildungp, Integer Innenp, Integer Wirtschaftsp, Integer Energiep,
                                    Integer Demokratie, Integer Justiz, Integer Sozialp, Integer Landwirtschaft, Integer Familien, Integer Renten, Integer Gesundheit, Integer Verkehr, Integer Digital, Integer Satire,
                                    Integer verarbeitete_pos, Integer anzahlLokal, Integer anzahlumwelt, Integer anzahlap, Integer anzahldrogen, Integer anzahlbildung, Integer anzahlinnen, Integer anzahlwirtschaft,
                                    Integer anzahlenergie, Integer anzahldemokratie, Integer anzahljustiz, Integer anzahlsozial, Integer anzahllandwirtschaft, Integer anzahlfamilien, Integer anzahlrenten,
                                    Integer anzahlgesundheit, Integer anzahlverkehr, Integer anzahldigital, Integer anzahlsatire){
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase dbwrite = thesenDbHelper.getWritableDatabase();
        try {
            if (KID != null){
                Log.d("Update KandidatenScore:", KID);
                    ContentValues values = new ContentValues();
                    values.put(KandidatenTable.COLUMN_NAME_KID, KID);

                    values.put(KandidatenTable.COLUMN_NAME_PUNKTE_INSGESAMT, Insgesamt);
                    values.put(KandidatenTable.COLUMN_NAME_PUNKTE_LOKAL, Lokal);
                    values.put(KandidatenTable.COLUMN_NAME_PUNKTE_UMWELT, Umwelt);
                    values.put(KandidatenTable.COLUMN_NAME_PUNKTE_AP, AP);
                values.put(KandidatenTable.COLUMN_NAME_PUNKTE_DrogenP, Drogenp);
                values.put(KandidatenTable.COLUMN_NAME_PUNKTE_BildungsP, Bildungp);
                values.put(KandidatenTable.COLUMN_NAME_PUNKTE_InnenP, Innenp);
                values.put(KandidatenTable.COLUMN_NAME_PUNKTE_WirtschaftP, Wirtschaftsp);
                values.put(KandidatenTable.COLUMN_NAME_PUNKTE_EnergieP, Energiep);
                values.put(KandidatenTable.COLUMN_NAME_PUNKTE_Demokratie, Demokratie);
                values.put(KandidatenTable.COLUMN_NAME_PUNKTE_Justiz, Justiz);
                values.put(KandidatenTable.COLUMN_NAME_PUNKTE_SozialP, Sozialp);
                values.put(KandidatenTable.COLUMN_NAME_PUNKTE_LandwirtschaftP, Landwirtschaft);
                values.put(KandidatenTable.COLUMN_NAME_PUNKTE_FamilienP, Familien);
                values.put(KandidatenTable.COLUMN_NAME_PUNKTE_RentenP, Renten);
                values.put(KandidatenTable.COLUMN_NAME_PUNKTE_GesundheitP, Gesundheit);
                values.put(KandidatenTable.COLUMN_NAME_PUNKTE_VerkehrP, Verkehr);
                values.put(KandidatenTable.COLUMN_NAME_PUNKTE_DigitalP, Digital);
                    values.put(KandidatenTable.COLUMN_NAME_PUNKTE_SATIRE, Satire);
                    values.put(KandidatenTable.COLUMN_NAME_VERARBEITE_POS, verarbeitete_pos);
                    values.put(KandidatenTable.COLUMN_NAME_ANZAHLLOKAL_POS, anzahlLokal);
                    values.put(KandidatenTable.COLUMN_NAME_ANZAHLUMWELT_POS, anzahlumwelt);
                    values.put(KandidatenTable.COLUMN_NAME_ANZAHLAP_POS, anzahlap);
                values.put(KandidatenTable.COLUMN_NAME_ANZAHLDrogen_POS, anzahldrogen);
                values.put(KandidatenTable.COLUMN_NAME_ANZAHLBildungs_POS, anzahlbildung);
                values.put(KandidatenTable.COLUMN_NAME_ANZAHLInnen_POS, anzahlinnen);
                values.put(KandidatenTable.COLUMN_NAME_ANZAHLWirtschaft_POS, anzahlwirtschaft);
                values.put(KandidatenTable.COLUMN_NAME_ANZAHLEnergie_POS, anzahlenergie);
                values.put(KandidatenTable.COLUMN_NAME_ANZAHLDEMOKRATIE_POS, anzahldemokratie);
                values.put(KandidatenTable.COLUMN_NAME_ANZAHLJutiz_POS, anzahljustiz);
                values.put(KandidatenTable.COLUMN_NAME_ANZAHLSozial_POS, anzahlsozial);
                values.put(KandidatenTable.COLUMN_NAME_ANZAHLLandwirtschaft_POS, anzahllandwirtschaft);
                values.put(KandidatenTable.COLUMN_NAME_ANZAHLFamilien_POS, anzahlfamilien);
                values.put(KandidatenTable.COLUMN_NAME_ANZAHLRenten_POS, anzahlrenten);
                values.put(KandidatenTable.COLUMN_NAME_ANZAHLGesundheit_POS, anzahlgesundheit);
                values.put(KandidatenTable.COLUMN_NAME_ANZAHLVerkehr_POS, anzahlverkehr);
                values.put(KandidatenTable.COLUMN_NAME_ANZAHLDigital_POS, anzahldigital);
                    values.put(KandidatenTable.COLUMN_NAME_ANZAHLSATIRE_POS, anzahlsatire);
                    dbwrite.update(KandidatenTable.TABLE_NAME, values, "kid=?", new String[]{KID});
                }
        } finally {
            dbwrite.close();
        }
    }

    public Integer getVerarbeitetePositionen(String KID, String Column){
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase db = thesenDbHelper.getReadableDatabase();
        Integer result = 0;
        try{
            Cursor cursor = db.query(KandidatenTable.TABLE_NAME, new String[]{KandidatenTable.COLUMN_NAME_KID, Column}, "kid = ?", new String[]{KID}, null, null, null);
            while (cursor.moveToNext()){
                result = cursor.getInt(1);
            }
        }finally {
            db.close();
        }
        return result;
    }

    public ArrayList<KandidatenModel> getAllKandidaten (String wahlkreis){
        ArrayList<KandidatenModel> kandidatenModels = new ArrayList<>();
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase db = thesenDbHelper.getReadableDatabase();


        try{
            if(wahlkreis != null) {
                Cursor  c = db.query(KandidatenTable.TABLE_NAME, new String[]{KandidatenTable.COLUMN_NAME_KID, KandidatenTable.COLUMN_NAME_VORNAME, KandidatenTable.COLUMN_NAME_NACHNAME,
                        KandidatenTable.COLUMN_NAME_PARTEI, KandidatenTable.COLUMN_NAME_EMAIL, KandidatenTable.COLUMN_NAME_WAHLKREIS, KandidatenTable.COLUMN_NAME_BEANTWORTETETHESEN,
                        KandidatenTable.COLUMN_NAME_PUNKTE_INSGESAMT, KandidatenTable.COLUMN_NAME_PUNKTE_LOKAL, KandidatenTable.COLUMN_NAME_PUNKTE_UMWELT, KandidatenTable.COLUMN_NAME_PUNKTE_AP,
                        KandidatenTable.COLUMN_NAME_PUNKTE_SATIRE, KandidatenTable.COLUMN_NAME_BEGRUENDUNGEN, KandidatenTable.COLUMN_NAME_BIOGRAPHIE, KandidatenTable.COLUMN_NAME_WAHLPROGRAMM,
                        KandidatenTable.COLUMN_NAME_PUNKTE_DrogenP,  KandidatenTable.COLUMN_NAME_PUNKTE_BildungsP,  KandidatenTable.COLUMN_NAME_PUNKTE_InnenP, KandidatenTable.COLUMN_NAME_PUNKTE_WirtschaftP,
                        KandidatenTable.COLUMN_NAME_PUNKTE_EnergieP, KandidatenTable.COLUMN_NAME_PUNKTE_Demokratie, KandidatenTable.COLUMN_NAME_PUNKTE_Justiz, KandidatenTable.COLUMN_NAME_PUNKTE_SozialP,
                        KandidatenTable.COLUMN_NAME_PUNKTE_LandwirtschaftP, KandidatenTable.COLUMN_NAME_PUNKTE_FamilienP, KandidatenTable.COLUMN_NAME_PUNKTE_RentenP, KandidatenTable.COLUMN_NAME_PUNKTE_GesundheitP,
                        KandidatenTable.COLUMN_NAME_PUNKTE_VerkehrP, KandidatenTable.COLUMN_NAME_PUNKTE_DigitalP}, "wahlkreis = ?", new String[]{wahlkreis}, null, null, null);

                try {
                    while (c.moveToNext()) {
                        String kid = c.getString(0);
                        Log.d("KID DATA get", kid);
                        String vorname = c.getString(1);
                        String nachname = c.getString(2);
                        String partei = c.getString(3);
                        String email = c.getString(4);
                        String beantwortete_Thesen = c.getString(6);

                        Integer Punkte_Insgesamt = c.getInt(7);
                        Integer Punkte_Lokal = c.getInt(8);
                        Integer Punkte_Umwelt = c.getInt(9);
                        Integer Punkte_AP = c.getInt(10);
                        Integer Punkte_Satire = c.getInt(11);
                        String begruendungen2 = c.getString(12);
                        String biografie2 = c.getString(13);
                        String wahlprogramm2 = c.getString(14);
                        Integer Punkte_Drogen = c.getInt(15);
                        Integer Punkte_Bildung = c.getInt(16);
                        Integer Punkte_InnenP = c.getInt(17);
                        Integer Punkte_Wirtschaft = c.getInt(18);
                        Integer Punkte_Energie = c.getInt(19);
                        Integer Punkte_Demokratie = c.getInt(20);
                        Integer Punkte_Justiz = c.getInt(21);
                        Integer Punkte_Sozial = c.getInt(22);
                        Integer Punkte_Landwirtschaft = c.getInt(23);
                        Integer Punkte_Familien = c.getInt(24);
                        Integer Punkte_Renten = c.getInt(25);
                        Integer Punkte_Gesundheit = c.getInt(26);
                        Integer Punkte_Verkehr = c.getInt(27);
                        Integer Punkte_Digital = c.getInt(28);
                        JSONArray beantworteteThesen = new JSONArray(beantwortete_Thesen);
                        JSONArray begruendungen = new JSONArray(begruendungen2);
                        JSONObject biografie = new JSONObject(biografie2);
                        JSONObject wahlprogramm = new JSONObject(wahlprogramm2);
                        Boolean abo = istKandidatAbonniert(kid);
                        kandidatenModels.add(new KandidatenModel(kid,  vorname, nachname,  partei,  email, wahlkreis,  beantworteteThesen, Punkte_Insgesamt, Punkte_Lokal, Punkte_Umwelt, Punkte_AP,
                                Punkte_Satire, begruendungen, biografie, wahlprogramm, abo, Punkte_Drogen, Punkte_Bildung, Punkte_InnenP, Punkte_Wirtschaft, Punkte_Energie, Punkte_Demokratie, Punkte_Justiz, Punkte_Sozial,
                                Punkte_Landwirtschaft, Punkte_Familien, Punkte_Renten, Punkte_Gesundheit, Punkte_Verkehr, Punkte_Digital));
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
                Cursor  c = db.query(KandidatenTable.TABLE_NAME, new String[]{KandidatenTable.COLUMN_NAME_KID, KandidatenTable.COLUMN_NAME_VORNAME, KandidatenTable.COLUMN_NAME_NACHNAME,
                        KandidatenTable.COLUMN_NAME_PARTEI, KandidatenTable.COLUMN_NAME_EMAIL, KandidatenTable.COLUMN_NAME_WAHLKREIS, KandidatenTable.COLUMN_NAME_BEANTWORTETETHESEN,
                        KandidatenTable.COLUMN_NAME_PUNKTE_INSGESAMT, KandidatenTable.COLUMN_NAME_PUNKTE_LOKAL, KandidatenTable.COLUMN_NAME_PUNKTE_UMWELT, KandidatenTable.COLUMN_NAME_PUNKTE_AP,
                        KandidatenTable.COLUMN_NAME_PUNKTE_SATIRE, KandidatenTable.COLUMN_NAME_BEGRUENDUNGEN, KandidatenTable.COLUMN_NAME_BIOGRAPHIE, KandidatenTable.COLUMN_NAME_WAHLPROGRAMM,
                        KandidatenTable.COLUMN_NAME_PUNKTE_DrogenP,  KandidatenTable.COLUMN_NAME_PUNKTE_BildungsP,  KandidatenTable.COLUMN_NAME_PUNKTE_InnenP, KandidatenTable.COLUMN_NAME_PUNKTE_WirtschaftP,
                        KandidatenTable.COLUMN_NAME_PUNKTE_EnergieP, KandidatenTable.COLUMN_NAME_PUNKTE_Demokratie, KandidatenTable.COLUMN_NAME_PUNKTE_Justiz, KandidatenTable.COLUMN_NAME_PUNKTE_SozialP,
                        KandidatenTable.COLUMN_NAME_PUNKTE_LandwirtschaftP, KandidatenTable.COLUMN_NAME_PUNKTE_FamilienP, KandidatenTable.COLUMN_NAME_PUNKTE_RentenP, KandidatenTable.COLUMN_NAME_PUNKTE_GesundheitP,
                        KandidatenTable.COLUMN_NAME_PUNKTE_VerkehrP, KandidatenTable.COLUMN_NAME_PUNKTE_DigitalP, KandidatenTable.COLUMN_NAME_VERARBEITE_POS, KandidatenTable.COLUMN_NAME_ANZAHLLOKAL_POS,
                        KandidatenTable.COLUMN_NAME_ANZAHLUMWELT_POS, KandidatenTable.COLUMN_NAME_ANZAHLAP_POS, KandidatenTable.COLUMN_NAME_ANZAHLSATIRE_POS, KandidatenTable.COLUMN_NAME_ANZAHLDrogen_POS,
                        KandidatenTable.COLUMN_NAME_ANZAHLBildungs_POS, KandidatenTable.COLUMN_NAME_ANZAHLInnen_POS, KandidatenTable.COLUMN_NAME_ANZAHLWirtschaft_POS, KandidatenTable.COLUMN_NAME_ANZAHLEnergie_POS,
                        KandidatenTable.COLUMN_NAME_ANZAHLDEMOKRATIE_POS, KandidatenTable.COLUMN_NAME_ANZAHLJutiz_POS, KandidatenTable.COLUMN_NAME_ANZAHLSozial_POS, KandidatenTable.COLUMN_NAME_ANZAHLLandwirtschaft_POS,
                        KandidatenTable.COLUMN_NAME_ANZAHLFamilien_POS, KandidatenTable.COLUMN_NAME_ANZAHLRenten_POS, KandidatenTable.COLUMN_NAME_ANZAHLGesundheit_POS, KandidatenTable.COLUMN_NAME_ANZAHLVerkehr_POS,
                        KandidatenTable.COLUMN_NAME_ANZAHLDigital_POS}, "wahlkreis = ?", new String[]{wahlkreis}, null, null, kategorie + " ASC");

                try {
                    while (c.moveToNext()) {
                        String kid = c.getString(0);
                        Log.d("KID SORT get", kid);
                        String vorname = c.getString(1);
                        String nachname = c.getString(2);
                        String partei = c.getString(3);
                        String email = c.getString(4);
                        String beantwortete_Thesen = c.getString(6);

                        Integer Punkte_Insgesamt = c.getInt(7);
                        Integer Punkte_Lokal = c.getInt(8);
                        Integer Punkte_Umwelt = c.getInt(9);
                        Integer Punkte_AP = c.getInt(10);
                        Integer Punkte_Satire = c.getInt(11);
                        String begruendungen2 = c.getString(12);
                        String biografie2 = c.getString(13);
                        String wahlprogramm2 = c.getString(14);
                        Integer Punkte_Drogen = c.getInt(15);
                        Integer Punkte_Bildung = c.getInt(16);
                        Integer Punkte_InnenP = c.getInt(17);
                        Integer Punkte_Wirtschaft = c.getInt(18);
                        Integer Punkte_Energie = c.getInt(19);
                        Integer Punkte_Demokratie = c.getInt(20);
                        Integer Punkte_Justiz = c.getInt(21);
                        Integer Punkte_Sozial = c.getInt(22);
                        Integer Punkte_Landwirtschaft = c.getInt(23);
                        Integer Punkte_Familien = c.getInt(24);
                        Integer Punkte_Renten = c.getInt(25);
                        Integer Punkte_Gesundheit = c.getInt(26);
                        Integer Punkte_Verkehr = c.getInt(27);
                        Integer Punkte_Digital = c.getInt(28);
                        Integer Anzahl_Insgesamt = c.getInt(29);
                        Integer Anzahl_Lokal = c.getInt(30);
                        Integer Anzahl_Umwelt = c.getInt(31);
                        Integer Anzahl_AP = c.getInt(32);
                        Integer Anzahl_Satire = c.getInt(33);
                        Integer Anzahl_Drogen = c.getInt(34);
                        Integer Anzahl_Bildung = c.getInt(35);
                        Integer Anzahl_InnenP = c.getInt(36);
                        Integer Anzahl_Wirtschaft = c.getInt(37);
                        Integer Anzahl_Energie = c.getInt(38);
                        Integer Anzahl_Demokratie = c.getInt(39);
                        Integer Anzahl_Justiz = c.getInt(40);
                        Integer Anzahl_Sozial = c.getInt(41);
                        Integer Anzahl_Landwirtschaft = c.getInt(42);
                        Integer Anzahl_Familien = c.getInt(43);
                        Integer Anzahl_Renten = c.getInt(44);
                        Integer Anzahl_Gesundheit = c.getInt(45);
                        Integer Anzahl_Verkehr = c.getInt(46);
                        Integer Anzahl_Digital = c.getInt(47);
                        JSONArray beantworteteThesen = new JSONArray(beantwortete_Thesen);
                        JSONArray begruendungen = new JSONArray(begruendungen2);
                        JSONObject biografie = new JSONObject(biografie2);
                        JSONObject wahlprogramm = new JSONObject(wahlprogramm2);

                        Boolean check = false;

                        if(kategorie.equals(KandidatenTable.COLUMN_NAME_PUNKTE_INSGESAMT)){
                            if(Anzahl_Insgesamt>0)check=true;
                        }
                        if(kategorie.equals(KandidatenTable.COLUMN_NAME_PUNKTE_LOKAL)){
                            if(Anzahl_Lokal>0)check=true;
                        }
                        if(kategorie.equals(KandidatenTable.COLUMN_NAME_PUNKTE_UMWELT)){
                            if(Anzahl_Umwelt>0)check=true;
                        }
                        if(kategorie.equals(KandidatenTable.COLUMN_NAME_PUNKTE_AP)) {
                            if(Anzahl_AP>0)check=true;
                        }
                        if(kategorie.equals(KandidatenTable.COLUMN_NAME_PUNKTE_DrogenP)){
                            if(Anzahl_Drogen>0)check=true;
                        }
                        if(kategorie.equals(KandidatenTable.COLUMN_NAME_PUNKTE_BildungsP)) {
                            if(Anzahl_Bildung>0)check=true;
                        }
                        if(kategorie.equals(KandidatenTable.COLUMN_NAME_PUNKTE_InnenP)) {
                            if(Anzahl_InnenP>0)check=true;
                        }
                        if(kategorie.equals(KandidatenTable.COLUMN_NAME_PUNKTE_WirtschaftP)){
                            if(Anzahl_Wirtschaft>0)check=true;
                        }
                        if(kategorie.equals(KandidatenTable.COLUMN_NAME_PUNKTE_EnergieP)){
                            if(Anzahl_Energie>0)check=true;
                        }
                        if(kategorie.equals(KandidatenTable.COLUMN_NAME_PUNKTE_Demokratie)){
                            if(Anzahl_Demokratie>0)check=true;
                        }
                        if(kategorie.equals(KandidatenTable.COLUMN_NAME_PUNKTE_Justiz)){
                            if(Anzahl_Justiz>0)check=true;
                        }
                        if(kategorie.equals(KandidatenTable.COLUMN_NAME_PUNKTE_SozialP)){
                            if(Anzahl_Sozial>0)check=true;
                        }
                        if(kategorie.equals(KandidatenTable.COLUMN_NAME_PUNKTE_LandwirtschaftP)){
                            if(Anzahl_Landwirtschaft>0)check=true;
                        }
                        if(kategorie.equals(KandidatenTable.COLUMN_NAME_PUNKTE_FamilienP)){
                            if(Anzahl_Familien>0)check=true;
                        }
                        if(kategorie.equals(KandidatenTable.COLUMN_NAME_PUNKTE_RentenP)){
                            if(Anzahl_Renten>0)check=true;
                        }
                        if(kategorie.equals(KandidatenTable.COLUMN_NAME_PUNKTE_GesundheitP)){
                            if(Anzahl_Gesundheit>0)check=true;
                        }
                        if(kategorie.equals(KandidatenTable.COLUMN_NAME_PUNKTE_VerkehrP)){
                            if(Anzahl_Verkehr>0)check=true;
                        }
                        if(kategorie.equals(KandidatenTable.COLUMN_NAME_PUNKTE_DigitalP)){
                            if(Anzahl_Digital>0)check=true;
                        }
                        if(kategorie.equals(KandidatenTable.COLUMN_NAME_PUNKTE_SATIRE)){
                            if(Anzahl_Satire>0)check=true;
                        }

                        Boolean abo = istKandidatAbonniert(kid);
                        if (check) {
                            kandidatenModels.add(new KandidatenModel(kid, vorname, nachname, partei, email, wahlkreis, beantworteteThesen, Punkte_Insgesamt, Punkte_Lokal, Punkte_Umwelt, Punkte_AP,
                                    Punkte_Satire, begruendungen, biografie, wahlprogramm, abo, Punkte_Drogen, Punkte_Bildung, Punkte_InnenP, Punkte_Wirtschaft, Punkte_Energie, Punkte_Demokratie, Punkte_Justiz, Punkte_Sozial,
                                    Punkte_Landwirtschaft, Punkte_Familien, Punkte_Renten, Punkte_Gesundheit, Punkte_Verkehr, Punkte_Digital));
                        }
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
                Cursor  c = db.query(KandidatenTable.TABLE_NAME, new String[]{KandidatenTable.COLUMN_NAME_KID, KandidatenTable.COLUMN_NAME_VORNAME, KandidatenTable.COLUMN_NAME_NACHNAME,
                        KandidatenTable.COLUMN_NAME_PARTEI, KandidatenTable.COLUMN_NAME_EMAIL, KandidatenTable.COLUMN_NAME_WAHLKREIS, KandidatenTable.COLUMN_NAME_BEANTWORTETETHESEN,
                        KandidatenTable.COLUMN_NAME_PUNKTE_INSGESAMT, KandidatenTable.COLUMN_NAME_PUNKTE_LOKAL, KandidatenTable.COLUMN_NAME_PUNKTE_UMWELT, KandidatenTable.COLUMN_NAME_PUNKTE_AP,
                        KandidatenTable.COLUMN_NAME_PUNKTE_SATIRE, KandidatenTable.COLUMN_NAME_BEGRUENDUNGEN, KandidatenTable.COLUMN_NAME_BIOGRAPHIE, KandidatenTable.COLUMN_NAME_WAHLPROGRAMM,
                        KandidatenTable.COLUMN_NAME_PUNKTE_DrogenP,  KandidatenTable.COLUMN_NAME_PUNKTE_BildungsP,  KandidatenTable.COLUMN_NAME_PUNKTE_InnenP, KandidatenTable.COLUMN_NAME_PUNKTE_WirtschaftP,
                        KandidatenTable.COLUMN_NAME_PUNKTE_EnergieP, KandidatenTable.COLUMN_NAME_PUNKTE_Demokratie, KandidatenTable.COLUMN_NAME_PUNKTE_Justiz, KandidatenTable.COLUMN_NAME_PUNKTE_SozialP,
                        KandidatenTable.COLUMN_NAME_PUNKTE_LandwirtschaftP, KandidatenTable.COLUMN_NAME_PUNKTE_FamilienP, KandidatenTable.COLUMN_NAME_PUNKTE_RentenP, KandidatenTable.COLUMN_NAME_PUNKTE_GesundheitP,
                        KandidatenTable.COLUMN_NAME_PUNKTE_VerkehrP, KandidatenTable.COLUMN_NAME_PUNKTE_DigitalP}, "kid = ?", new String[]{kid}, null, null, null);

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

                        Integer Punkte_Insgesamt = c.getInt(7);
                        Integer Punkte_Lokal = c.getInt(8);
                        Integer Punkte_Umwelt = c.getInt(9);
                        Integer Punkte_AP = c.getInt(10);
                        Integer Punkte_Satire = c.getInt(11);
                        Integer Punkte_Drogen = c.getInt(15);
                        Integer Punkte_Bildung = c.getInt(16);
                        Integer Punkte_InnenP = c.getInt(17);
                        Integer Punkte_Wirtschaft = c.getInt(18);
                        Integer Punkte_Energie = c.getInt(19);
                        Integer Punkte_Demokratie = c.getInt(20);
                        Integer Punkte_Justiz = c.getInt(21);
                        Integer Punkte_Sozial = c.getInt(22);
                        Integer Punkte_Landwirtschaft = c.getInt(23);
                        Integer Punkte_Familien = c.getInt(24);
                        Integer Punkte_Renten = c.getInt(25);
                        Integer Punkte_Gesundheit = c.getInt(26);
                        Integer Punkte_Verkehr = c.getInt(27);
                        Integer Punkte_Digital = c.getInt(28);
                        Boolean abo = istKandidatAbonniert(kid);
                        kandidatenModel = new KandidatenModel(kid, vorname, nachname, partei, email, wahlkreis, beantworteteThesen, Punkte_Insgesamt, Punkte_Lokal, Punkte_Umwelt, Punkte_AP,
                                Punkte_Satire, begruendungen, biografie, wahlprogramm, abo, Punkte_Drogen, Punkte_Bildung, Punkte_InnenP, Punkte_Wirtschaft, Punkte_Energie, Punkte_Demokratie, Punkte_Justiz, Punkte_Sozial,
                                Punkte_Landwirtschaft, Punkte_Familien, Punkte_Renten, Punkte_Gesundheit, Punkte_Verkehr, Punkte_Digital);
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
    public void insertLikeThese(String TID, Integer Like){
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase dbread = thesenDbHelper.getReadableDatabase();
        Cursor cursor;
        if (TID != null && Like != null) {
            cursor = dbread.query(ThesenLikesTable.TABLE_NAME, new String[]{ThesenLikesTable.COLUMN_NAME_TID}, "tid=?", new String[]{TID}, null, null, null);

            SQLiteDatabase dbwrite = thesenDbHelper.getWritableDatabase();

            if (cursor.getCount() < 1) {
                try {
                    ContentValues values = new ContentValues();
                    values.put(ThesenLikesTable.COLUMN_NAME_TID, TID);
                    values.put(ThesenLikesTable.COLUMN_NAME_LIKE, Like);
                    dbwrite.insert(ThesenLikesTable.TABLE_NAME, null, values);
                } finally {
                    dbread.close();
                    dbwrite.close();
                }
            } else {
                try {
                    ContentValues values = new ContentValues();
                    values.put(ThesenLikesTable.COLUMN_NAME_TID, TID);
                    values.put(ThesenLikesTable.COLUMN_NAME_LIKE, Like);
                    dbwrite.update(ThesenLikesTable.TABLE_NAME, values, "tid=?", new String[]{TID});
                } finally {
                    dbread.close();
                    dbwrite.close();
                }
            }
        }
    }
    public Integer getLikeThese(String TID){
        ThesenDbHelper thesenDbHelper = new ThesenDbHelper(context);
        SQLiteDatabase dbread = thesenDbHelper.getReadableDatabase();
        Cursor cursor;
        Integer result = 0;
        if (TID != null) {
            try{
                cursor = dbread.query(ThesenLikesTable.TABLE_NAME, new String[]{ThesenLikesTable.COLUMN_NAME_TID, ThesenLikesTable.COLUMN_NAME_LIKE}, "tid=?", new String[]{TID}, null, null, null);
                while(cursor.moveToNext()){
                    result = cursor.getInt(1);
                }
            } finally {
                dbread.close();
            }

        }
//        Log.d("LIKE TEST",  TID + " LIKE: "+ result);
        return result;
    }
}
