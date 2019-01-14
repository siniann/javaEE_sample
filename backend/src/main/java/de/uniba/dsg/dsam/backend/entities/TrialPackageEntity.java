package de.uniba.dsg.dsam.backend.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@DiscriminatorValue("trialpackage")
public class TrialPackageEntity extends IncentiveEntity implements Serializable{

    //Default constructor
    public TrialPackageEntity() {
    }

    //custom constructor
    public TrialPackageEntity( String incentiveType, String name) {
        super(incentiveType, name);
    }
}
