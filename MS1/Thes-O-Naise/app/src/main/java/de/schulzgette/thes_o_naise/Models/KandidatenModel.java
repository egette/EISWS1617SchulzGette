package de.schulzgette.thes_o_naise.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jessica on 19.11.2016.
 */

//TODO MEHR KATEGORIEN ?
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


    public KandidatenModel(String kid, String vorname, String nachname,  String partei, String email,  String wahlkreis,  JSONArray beantworteteThesen, Integer punkte_Insgesamt,Integer punkte_Lokal, Integer punkte_Umwelt, Integer punkte_AP, Integer punkte_Satire) {
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

    public Integer getAnzahlThesenPositionen() {
        return beantworteteThesen.length();
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

}

