package de.uniba.dsg.dsam.model;

import java.io.Serializable;

public class TrialPackage extends Incentive implements Serializable {

    public TrialPackage(int incentiveId, String name, String incentiveType) {
        super(incentiveId,name,incentiveType);
    }
}
