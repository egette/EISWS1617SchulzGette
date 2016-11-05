package de.schulzgette.thes_o_naise;

import org.json.JSONArray;

/**
 * Created by Jessica on 02.11.2016.
 */

public class ThesenModel {


    String thesentext;
    Integer likes;
    Integer Pro;
    Integer Neutral;
    Integer Contra;
    JSONArray PositionPro;
    JSONArray PositionNeutral;
    JSONArray PositionContra;
    String TID;
    String Kategorie;
    String Wahlkreis;


    public ThesenModel(String TID, String thesentext, String Kategorie, String Wahlkreis, Integer likes, Integer Pro, Integer Neutral, Integer Contra, JSONArray PositionPro, JSONArray PositionNeutral, JSONArray PositionContra) {
        this.TID = TID;
        this.thesentext = thesentext;
        this.likes = likes;
        this.Kategorie = Kategorie;
        this.Wahlkreis = Wahlkreis;
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

   public Integer getLikes() {
       return likes;
    }

    public Integer getPro() {
        return Pro;
    }
    public Integer getNeutral() {
        return Neutral;
    }
    public Integer getContra() {
        return Neutral;
    }
    public JSONArray getPositionPro(){
        return PositionPro;
    }
    public JSONArray getPositionNeutral(){
        return PositionNeutral;
    }
    public JSONArray getPositionContra(){
        return PositionContra;
    }

    public String getWahlkreis(){
        return Wahlkreis;
    }

    public String getKategorie(){
        return Kategorie;
    }
}
