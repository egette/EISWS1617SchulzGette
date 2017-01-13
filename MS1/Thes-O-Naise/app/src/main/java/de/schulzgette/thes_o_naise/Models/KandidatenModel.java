package de.schulzgette.thes_o_naise.Models;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.schulzgette.thes_o_naise.database.Database;

/**
 * Created by Jessica on 19.11.2016.
 */


public class KandidatenModel {
    String vorname;
    String nachname;
    String partei;
    String kid;
    String email;
    String wahlkreis;
    JSONArray beantworteteThesen;
    Integer Punkte_Insgesamt;
    Integer Punkte_Lokal;
    Integer Punkte_Umwelt;
    Integer Punkte_AP;
    Integer Punkte_Satire;
    Integer Punkte_Drogen;
    Integer Punkte_Bildung;
    Integer Punkte_InnenP;
    Integer Punkte_Wirtschaft;
    Integer Punkte_Energie;
    Integer Punkte_Demokratie;
    Integer Punkte_Justiz;
    Integer Punkte_Sozial;
    Integer Punkte_Landwirtschaft;
    Integer Punkte_Familien;
    Integer Punkte_Renten;
    Integer Punkte_Gesundheit;
    Integer Punkte_Verkehr;
    Integer Punkte_Digital;
    JSONArray Begruendungen;
    JSONObject Biografie;
    JSONObject Wahlprogramm;
    Boolean abo;


    public KandidatenModel(String kid, String vorname, String nachname,  String partei, String email,  String wahlkreis,  JSONArray beantworteteThesen,
                           Integer punkte_Insgesamt,Integer punkte_Lokal, Integer punkte_Umwelt, Integer punkte_AP, Integer punkte_Satire,
                           JSONArray Begruendungen, JSONObject Biografie, JSONObject Wahlprogramm, Boolean abo, Integer Punkte_Drogen, Integer Punkte_Bildung, Integer Punkte_InnenP,
                           Integer Punkte_Wirtschaft, Integer Punkte_Energie, Integer Punkte_Demokratie, Integer Punkte_Justiz, Integer Punkte_Sozial,
                           Integer Punkte_Landwirtschaft, Integer Punkte_Familien, Integer Punkte_Renten, Integer Punkte_Gesundheit, Integer Punkte_Verkehr, Integer Punkte_Digital) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.kid = kid;
        this.email = email;
        this.partei = partei;
        this.wahlkreis = wahlkreis;
        this.Punkte_Insgesamt = punkte_Insgesamt;
        this.beantworteteThesen = beantworteteThesen;
        this.Punkte_Lokal = punkte_Lokal;
        this.Punkte_Umwelt = punkte_Umwelt;
        this.Punkte_AP = punkte_AP;
        this.Punkte_Satire = punkte_Satire;
        this.Begruendungen = Begruendungen;
        this.Biografie = Biografie;
        this.Wahlprogramm = Wahlprogramm;
        this.abo = abo;

        this.Punkte_Drogen = Punkte_Drogen;
        this.Punkte_Bildung = Punkte_Bildung;
        this.Punkte_InnenP = Punkte_InnenP ;
        this.Punkte_Wirtschaft = Punkte_Wirtschaft;
        this.Punkte_Energie = Punkte_Energie;
        this.Punkte_Demokratie = Punkte_Demokratie;
        this.Punkte_Justiz = Punkte_Justiz;
        this.Punkte_Sozial = Punkte_Sozial;
        this.Punkte_Landwirtschaft = Punkte_Landwirtschaft;
        this.Punkte_Familien = Punkte_Familien;
        this.Punkte_Renten = Punkte_Renten;
        this.Punkte_Gesundheit = Punkte_Gesundheit;
        this.Punkte_Verkehr = Punkte_Verkehr ;
        this.Punkte_Digital = Punkte_Digital;
    }

    public Boolean getAbo() {
        return abo;
    }

    public void setAbo(Boolean abo) {
        this.abo = abo;
    }

    public String getGeburtsdatum() throws JSONException {
        String geburtsdatum = Biografie.getString("Geburtsdatum");
        return geburtsdatum;
    }
    public String getBildungsweg() throws JSONException {
        String bildungsweg = Biografie.getString("Bildungsweg");
        return bildungsweg;
    }
    public String getBerufe() throws JSONException {
        String berufe = Biografie.getString("Berufe");
        return berufe;
    }
    public String getMitgliedschaften() throws JSONException {
        String mitgliedschaften = Biografie.getString("Mitgliedschaften");
        return mitgliedschaften;
    }
    public String getWebseite() throws JSONException {
        String webseite = Wahlprogramm.getString("Webseite");
        return webseite;
    }
    public String getBeschreibung() throws JSONException {
        String beschreibung = Wahlprogramm.getString("Text");
        return beschreibung;
    }
    public String getLink() throws JSONException {
        String link = Wahlprogramm.getString("Link");
        return link;
    }

    public JSONArray getBegruendungen() {
        return Begruendungen;
    }

    public JSONObject getBiografie() {
        return Biografie;
    }

    public JSONObject getWahlprogramm() {
        return Wahlprogramm;
    }

    public String getNachname() {
        return nachname;
    }

    public String getVorname() {
        return vorname;
    }

    public String getPartei() {
        return partei;
    }

    public String getKid() {
        return kid;
    }

    public String getEmail() {
        return email;
    }

    public String getWahlkreis() {
        return wahlkreis;
    }

    public JSONArray getBeantworteteThesen() {
        return beantworteteThesen;
    }

    public Integer getPunkte_Insgesamt() {
        return Punkte_Insgesamt;
    }

    public Integer getPunkte_Lokal() {
        return Punkte_Lokal;
    }

    public Integer getPunkte_Umwelt() {
        return Punkte_Umwelt;
    }

    public Integer getPunkte_AP() {
        return Punkte_AP;
    }

    public Integer getPunkte_Satire() {
        return Punkte_Satire;
    }

    public Integer getPunkte_Drogen() {
        return Punkte_Drogen;
    }

    public Integer getPunkte_Wirtschaft() {
        return Punkte_Wirtschaft;
    }

    public Integer getPunkte_Bildung() {
        return Punkte_Bildung;
    }

    public Integer getPunkte_Energie() {
        return Punkte_Energie;
    }

    public Integer getPunkte_Demokratie() {
        return Punkte_Demokratie;
    }

    public Integer getPunkte_Justiz() {
        return Punkte_Justiz;
    }

    public Integer getPunkte_Sozial() {
        return Punkte_Sozial;
    }

    public Integer getPunkte_Landwirtschaft() {
        return Punkte_Landwirtschaft;
    }

    public Integer getPunkte_Familien() {
        return Punkte_Familien;
    }

    public Integer getPunkte_Renten() {
        return Punkte_Renten;
    }

    public Integer getPunkte_Gesundheit() {
        return Punkte_Gesundheit;
    }

    public Integer getPunkte_Verkehr() {
        return Punkte_Verkehr;
    }

    public Integer getPunkte_Digital() {
        return Punkte_Digital;
    }

    public Integer getPunkte_InnenP() {
        return Punkte_InnenP;
    }

    public Integer getAnzahlThesenPositionen() {
        return beantworteteThesen.length();
    }

    public Integer getVerarbeitePositionen(Context mcontext, String Column){
        Database db = new Database(mcontext);
        return db.getVerarbeitetePositionen(getKid(), Column);
    }

    public Integer getAnzahlPositionenZuThesenMitKategorie(String kategorie) {
        Integer result= 0;
        for (int i = 0; i<beantworteteThesen.length(); i++) {
            JSONObject object = null;
            String objectKategorie = null;
            try {
                object = (JSONObject) beantworteteThesen.get(i);
                objectKategorie = object.getString("KATEGORIE");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(objectKategorie.equals(kategorie)) result += 1;
        }
        return result;
    }

    public Double durchschnittlichePunkteProThesen(String kategorie, Context mcontext ){
        Double result = 0.0;
        if(kategorie.equals("punkte_insgesamt")){
            result = (double) getPunkte_Insgesamt()/getVerarbeitePositionen(mcontext, Database.KandidatenTable.COLUMN_NAME_VERARBEITE_POS);
            result = Math.round(1000.0 * result) / 1000.0;
        }else if(kategorie.equals("Lokal")){
            result = (double) getPunkte_Lokal()/getVerarbeitePositionen(mcontext, Database.KandidatenTable.COLUMN_NAME_ANZAHLLOKAL_POS);
            result = Math.round(1000.0 * result) / 1000.0;
        }else if(kategorie.equals("Umwelt")){
            result = (double) getPunkte_Umwelt()/getVerarbeitePositionen(mcontext, Database.KandidatenTable.COLUMN_NAME_ANZAHLUMWELT_POS);
            result = Math.round(1000.0 * result) / 1000.0;
        }else if(kategorie.equals("Aussenpolitik")){
            result = (double) getPunkte_AP()/getVerarbeitePositionen(mcontext, Database.KandidatenTable.COLUMN_NAME_ANZAHLAP_POS);
            result = Math.round(1000.0 * result) / 1000.0;
        }else if(kategorie.equals("Drogenpolitik")){
            result = (double) getPunkte_Drogen()/getVerarbeitePositionen(mcontext, Database.KandidatenTable.COLUMN_NAME_ANZAHLDrogen_POS);
            result = Math.round(1000.0 * result) / 1000.0;
        }else if(kategorie.equals("Bildungspolitik")){
            result = (double) getPunkte_Bildung()/getVerarbeitePositionen(mcontext, Database.KandidatenTable.COLUMN_NAME_ANZAHLBildungs_POS);
            result = Math.round(1000.0 * result) / 1000.0;
        }else if(kategorie.equals("Wirtschaftspolitik")){
            result = (double) getPunkte_Wirtschaft()/getVerarbeitePositionen(mcontext, Database.KandidatenTable.COLUMN_NAME_ANZAHLWirtschaft_POS);
            result = Math.round(1000.0 * result) / 1000.0;
        }else if(kategorie.equals("Energiepolitik")){
            result = (double) getPunkte_Energie()/getVerarbeitePositionen(mcontext, Database.KandidatenTable.COLUMN_NAME_ANZAHLEnergie_POS);
            result = Math.round(1000.0 * result) / 1000.0;
        }else if(kategorie.equals("Demokratie")){
            result = (double) getPunkte_Demokratie()/getVerarbeitePositionen(mcontext, Database.KandidatenTable.COLUMN_NAME_ANZAHLDEMOKRATIE_POS);
            result = Math.round(1000.0 * result) / 1000.0;
        }else if(kategorie.equals("Justiz")){
            result = (double) getPunkte_Justiz()/getVerarbeitePositionen(mcontext, Database.KandidatenTable.COLUMN_NAME_ANZAHLJutiz_POS);
            result = Math.round(1000.0 * result) / 1000.0;
        }else if(kategorie.equals("Sozialpolitik")){
            result = (double) getPunkte_Sozial()/getVerarbeitePositionen(mcontext, Database.KandidatenTable.COLUMN_NAME_ANZAHLSozial_POS);
            result = Math.round(1000.0 * result) / 1000.0;
        }else if(kategorie.equals("Landwirtschaftspolitik")){
            result = (double) getPunkte_Landwirtschaft()/getVerarbeitePositionen(mcontext, Database.KandidatenTable.COLUMN_NAME_ANZAHLLandwirtschaft_POS);
            result = Math.round(1000.0 * result) / 1000.0;
        }else if(kategorie.equals("Familienpolitik")){
            result = (double) getPunkte_Familien()/getVerarbeitePositionen(mcontext, Database.KandidatenTable.COLUMN_NAME_ANZAHLFamilien_POS);
            result = Math.round(1000.0 * result) / 1000.0;
        }else if(kategorie.equals("Rentenpolitik")){
            result = (double) getPunkte_Renten()/getVerarbeitePositionen(mcontext, Database.KandidatenTable.COLUMN_NAME_ANZAHLRenten_POS);
            result = Math.round(1000.0 * result) / 1000.0;
        }else if(kategorie.equals("Innenpolitik")){
            result = (double) getPunkte_InnenP()/getVerarbeitePositionen(mcontext, Database.KandidatenTable.COLUMN_NAME_ANZAHLInnen_POS);
            result = Math.round(1000.0 * result) / 1000.0;
        }else if(kategorie.equals("Gesundheitspolitik")){
            result = (double) getPunkte_Gesundheit()/getVerarbeitePositionen(mcontext, Database.KandidatenTable.COLUMN_NAME_ANZAHLGesundheit_POS);
            result = Math.round(1000.0 * result) / 1000.0;
        }else if(kategorie.equals("Verkehrspolitik")){
            result = (double) getPunkte_Verkehr()/getVerarbeitePositionen(mcontext, Database.KandidatenTable.COLUMN_NAME_ANZAHLVerkehr_POS);
            result = Math.round(1000.0 * result) / 1000.0;
        }else if(kategorie.equals("Digitalpolitik")){
            result = (double) getPunkte_Digital()/getVerarbeitePositionen(mcontext, Database.KandidatenTable.COLUMN_NAME_ANZAHLDigital_POS);
            result = Math.round(1000.0 * result) / 1000.0;
        }else if(kategorie.equals("Satire")){
            result = (double) getPunkte_Satire()/getVerarbeitePositionen(mcontext, Database.KandidatenTable.COLUMN_NAME_ANZAHLSATIRE_POS);
            result = Math.round(1000.0 * result) / 1000.0;
        }

        return result;
    }

}

