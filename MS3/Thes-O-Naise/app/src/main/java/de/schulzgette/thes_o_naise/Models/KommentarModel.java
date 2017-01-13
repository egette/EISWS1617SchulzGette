package de.schulzgette.thes_o_naise.Models;

/**
 * Created by Enrico on 06.12.2016.
 */

public class KommentarModel {
    String Username;
    String Kommentartext;
    String UID;

    public KommentarModel(String kommentartext, String UID, String username) {
        this.Kommentartext = kommentartext;
        this.UID = UID;
        this.Username = username;
    }

    public String getKommentartext() {
        return Kommentartext;
    }

    public String getUID() {
        return UID;
    }

    public String getUsername() {
        return Username;
    }

    public void setKommentartext(String kommentartext) {
        Kommentartext = kommentartext;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public void setUsername(String username) {
        Username = username;
    }
}
