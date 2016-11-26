package de.schulzgette.thes_o_naise.Models;

/**
 * Created by Jessica on 20.11.2016.
 */

public class BeantworteteThesenKandidatenModel {
    String thesentext;
    String tid;
    String positionkandidat;
    String userposition;

    public BeantworteteThesenKandidatenModel(String thesentext, String tid, String positionkandidat, String Userposition) {
        this.thesentext = thesentext;
        this.tid = tid;
        this.positionkandidat = positionkandidat;
        this.userposition = Userposition;
    }

    public String getThesentext() {
        return thesentext;
    }

    public String getTid() {
        return tid;
    }

    public String getPositionkandidat() {
        return positionkandidat;
    }

    public String getUserposition() {
        return userposition;
    }
}
