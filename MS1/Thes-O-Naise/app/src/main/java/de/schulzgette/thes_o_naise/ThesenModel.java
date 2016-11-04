package de.schulzgette.thes_o_naise;

import org.json.JSONArray;

/**
 * Created by Jessica on 02.11.2016.
 */

public class ThesenModel {


    String thesentext;
    Integer anzahl_bewertung;
    Integer Pro;
    Integer Neutral;
    Integer Contra;
    JSONArray PositionPro;
    JSONArray PositionNeutral;
    JSONArray PositionContra;
    String TID;


    public ThesenModel(String TID, String thesentext,  Integer Pro, Integer Neutral, Integer Contra, JSONArray PositionPro, JSONArray PositionNeutral, JSONArray PositionContra) {
        this.TID = TID;
        this.thesentext = thesentext;
       // this.anzahl_bewertung = anzahl_bewertung;
        this.Pro = Pro;
        this.Neutral = Neutral;
        this.Contra = Contra;
        this.PositionPro = PositionPro;
        this.PositionNeutral = PositionNeutral;
        this.PositionContra = PositionContra;
    }

    public String getThesentext() {
        return thesentext;
    }

    public String getTID() {
        return TID;
    }

//    public Integer getAnzahlbewertungen() {
//        return anzahl_bewertung;
//    }

    public Integer getPro() {
        return Pro;
    }
    public Integer getNeutral() {
        return Neutral;
    }
    public Integer getContra() {
        return Neutral;
    }



}
