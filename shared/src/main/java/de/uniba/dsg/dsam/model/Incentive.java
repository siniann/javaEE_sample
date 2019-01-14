package de.uniba.dsg.dsam.model;

import java.io.Serializable;
import java.util.List;

public abstract class Incentive implements Serializable{

    private int incentiveId;
    private String name;
    private String incentiveType;

  /*  private List<Incentive> incentiveList;*/

    public Incentive(int incentiveId, String name, String incentiveType) {
        this.incentiveId=incentiveId;
        this.name=name;
        this.incentiveType=incentiveType;
    }

    public int getIncentiveId() {
        return incentiveId;
    }

    public void setIncentiveId(int incentiveId) {
        this.incentiveId= incentiveId;
    }

    public String getIncentiveType() {
        return incentiveType;
    }

    public void setIncentiveType(String incentiveType) { this.incentiveType = incentiveType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
