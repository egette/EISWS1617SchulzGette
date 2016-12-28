package de.schulzgette.thes_o_naise.Models;

/**
 * Created by Jessica on 20.12.2016.
 */

public class BenachrichtigungModel {
    String TID;
    String Thesentext;
    String Username;
    String Benachrichtungstext;

    public BenachrichtigungModel(String TID, String thesentext, String username, String benachrichtungstext) {
        this.TID = TID;
        this.Thesentext = thesentext;
        this.Username = username;
        this.Benachrichtungstext = benachrichtungstext;
    }

    public String getTID() {
        return TID;
    }

    public String getUsername() {
        return Username;
    }

    public String getThesentext() {
        return Thesentext;
    }

    public String getBenachrichtungstext() {
        return Benachrichtungstext;
    }
}
