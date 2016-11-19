package de.schulzgette.thes_o_naise.Models;

import org.json.JSONArray;

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

    public KandidatenModel(String kid, String vorname, String nachname, String partei,  String email, String wahlkreis, JSONArray beantworteteThesen) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.partei = partei;
        this.kid = kid;
        this.email = email;
        this.wahlkreis = wahlkreis;
        this.beantworteteThesen = beantworteteThesen;
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
}
