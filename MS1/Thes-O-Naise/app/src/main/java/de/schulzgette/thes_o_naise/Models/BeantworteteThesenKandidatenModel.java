package de.schulzgette.thes_o_naise.Models;

/**
 * Created by Jessica on 20.11.2016.
 */

public class BeantworteteThesenKandidatenModel {
    String thesentext;
    String tid;
    String positionkandidat;

    public BeantworteteThesenKandidatenModel(String thesentext, String tid, String positionkandidat) {
        this.thesentext = thesentext;
        this.tid = tid;
        this.positionkandidat = positionkandidat;
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
}
