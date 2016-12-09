package de.schulzgette.thes_o_naise.Models;

/**
 * Created by Enrico on 09.12.2016.
 */

public class BegrundungenMeinProfilModel {
    String begruendungstext;
    String TID;
    Integer Likes;
    String Richtung;
    String Thesentext;

    public BegrundungenMeinProfilModel(String begruendungstext, String TID, String Thesentext, Integer likes, String richtung) {
        this.begruendungstext = begruendungstext;
        this.TID = TID;
        this.Thesentext = Thesentext;
        this.Likes = likes;
        this.Richtung = richtung;
    }

    public String getThesentext() {
        return Thesentext;
    }

    public String getBegruendungstext() {
        return begruendungstext;
    }

    public String getTID() {
        return TID;
    }

    public Integer getLikes() {
        return Likes;
    }

    public String getRichtung() {
        return Richtung;
    }
}
