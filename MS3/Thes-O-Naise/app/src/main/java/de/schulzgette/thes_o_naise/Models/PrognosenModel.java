package de.schulzgette.thes_o_naise.Models;

/**
 * Created by Enrico on 13.01.2017.
 */

public class PrognosenModel {
    String KID;
    Integer Platz1;
    Integer Platz2;
    Integer Platz3;
    Integer Platz4;
    Integer Platz5;
    Integer Platz6;
    Integer Platz7;
    Integer Platz8;
    Integer Platz9;
    Integer Platz10;
    KandidatenModel kandidatenModel;

    public PrognosenModel(String KID, Integer platz1, Integer platz2, Integer platz3, Integer platz4, Integer platz5, Integer platz6, Integer platz7, Integer platz8, Integer platz9, Integer platz10, KandidatenModel kandidatenModel) {
        this.KID = KID;
        Platz1 = platz1;
        Platz2 = platz2;
        Platz3 = platz3;
        Platz4 = platz4;
        Platz5 = platz5;
        Platz6 = platz6;
        Platz7 = platz7;
        Platz8 = platz8;
        Platz9 = platz9;
        Platz10 = platz10;
        this.kandidatenModel = kandidatenModel;
    }

    public String getKID() {
        return KID;
    }

    public KandidatenModel getKandidatenModel() {
        return kandidatenModel;
    }

    public Integer getPlatz1() {
        return Platz1;
    }

    public Integer getPlatz2() {
        return Platz2;
    }

    public Integer getPlatz3() {
        return Platz3;
    }

    public Integer getPlatz4() {
        return Platz4;
    }

    public Integer getPlatz5() {
        return Platz5;
    }

    public Integer getPlatz6() {
        return Platz6;
    }

    public Integer getPlatz7() {
        return Platz7;
    }

    public Integer getPlatz8() {
        return Platz8;
    }

    public Integer getPlatz9() {
        return Platz9;
    }

    public Integer getPlatz10() {
        return Platz10;
    }
}
