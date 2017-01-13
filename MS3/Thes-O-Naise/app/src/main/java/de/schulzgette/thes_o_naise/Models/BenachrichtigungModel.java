package de.schulzgette.thes_o_naise.Models;

/**
 * Created by Jessica on 20.12.2016.
 */

public class BenachrichtigungModel {
    String TID;
    String Thesentext;
    String UID;
    String Benachrichtungstext;

    public BenachrichtigungModel(String TID, String thesentext, String UID, String benachrichtungstext) {
        this.TID = TID;
        this.Thesentext = thesentext;
        this.UID = UID;
        this.Benachrichtungstext = benachrichtungstext;
    }

    public String getTID() {
        return TID;
    }

    public String getUID() {
        return UID;
    }

    public String getThesentext() {
        return Thesentext;
    }

    public String getBenachrichtungstext() {
        return Benachrichtungstext;
    }
}
