package de.schulzgette.thes_o_naise.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jessica on 16.11.2016.
 */

public class BegruendungModel {
    String Username;
    String Typ;
    String Begruendungstext;
    Integer Likes;
    String UID;
    ArrayList<KommentarModel> Kommentare;
    Integer AnzahlKommentare;

    public BegruendungModel(ArrayList<KommentarModel> kommentare, String begruendungstext, Integer likes, String UID, String typ, String username) {
        this.Kommentare = kommentare;
        this.Begruendungstext = begruendungstext;
        this.Likes = likes;
        this.UID = UID;
        this.Typ = typ;
        this.Username = username;
    }

    public Integer getAnzahlKommentare() {
        return Kommentare.size();
    }

    public String getUID() {
        return UID;
    }

    public String getUsername() {
        return Username;
    }

    public String getTyp() {
        return Typ;
    }

    public String getBegruendungstext() {
        return Begruendungstext;
    }

    public Integer getLikes() {
        return Likes;
    }

    public List<KommentarModel> getKommentare() {
        return Kommentare;
    }

    public void setKommentare(ArrayList<KommentarModel> kommentare) {
        Kommentare = kommentare;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public void setTyp(String typ) {
        Typ = typ;
    }

    public void setBegruendungstext(String begruendungstext) {
        Begruendungstext = begruendungstext;
    }

    public void setLikes(Integer likes) {
        Likes = likes;
    }
}
