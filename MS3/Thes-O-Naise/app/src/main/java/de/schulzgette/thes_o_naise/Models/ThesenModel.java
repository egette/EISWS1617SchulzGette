package de.schulzgette.thes_o_naise.Models;

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
    Integer time;
    JSONArray KPro;
    JSONArray KNeutral;
    JSONArray KContra;
    JSONArray WPro;
    JSONArray WNeutral;
    JSONArray WContra;
    JSONArray KPosition;
    String TID;
    String Kategorie;
    String Wahlkreis;
    Boolean Abo;
    String Position;


    public ThesenModel(String TID, String thesentext, String Kategorie, String Wahlkreis, Integer likes, Integer Pro, Integer Neutral, Integer Contra, Integer Time, JSONArray KPro, JSONArray KNeutral, JSONArray KContra, JSONArray WPro, JSONArray WNeutral, JSONArray WContra, JSONArray KPosition, Boolean Abo, String Position) {
        this.TID = TID;
        this.thesentext = thesentext;
        this.likes = likes;
        this.Kategorie = Kategorie;
        this.Wahlkreis = Wahlkreis;
        this.Pro = Pro;
        this.Neutral = Neutral;
        this.Contra = Contra;
        this.time = Time;
        this.KPro = KPro;
        this.KNeutral = KNeutral;
        this.KContra = KContra;
        this.WPro = WPro;
        this.WNeutral = WNeutral;
        this.WContra = WContra;
        this.KPosition = KPosition;
        this.Abo = Abo;
        this.Position = Position;
    }

    public Integer getTime() {
        return time;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public Boolean getAbo() {
        return Abo;
    }

    public void setAbo(Boolean abo) {
        Abo = abo;
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

    public JSONArray getKPro() {
        return KPro;
    }

    public JSONArray getKNeutral() {
        return KNeutral;
    }

    public JSONArray getKContra() {
        return KContra;
    }

    public JSONArray getWPro() {
        return WPro;
    }

    public JSONArray getWNeutral() {
        return WNeutral;
    }

    public JSONArray getWContra() {
        return WContra;
    }

    public JSONArray getKPosition() {
        return KPosition;
    }

    public String getWahlkreis(){
        return Wahlkreis;
    }

    public String getKategorie(){
        return Kategorie;
    }
}
