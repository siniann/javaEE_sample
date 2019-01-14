package de.uniba.dsg.dsam.model;


import java.io.Serializable;

public class PromotionalGift extends Incentive implements Serializable{

    public PromotionalGift(int incentiveId, String name, String incentiveType) {
        super(incentiveId,name,incentiveType);
    }
}
