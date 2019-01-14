package de.uniba.dsg.dsam.backend.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "incentiveType")
public abstract class IncentiveEntity implements Serializable {

    @Version
    private int version;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int incentiveId;
    //either "promotionalgift" or "trialpackage"
    private String incentiveType;
    private String name;


    //Default constructor
    public IncentiveEntity() {
    }

    //Custom Constructor with all arguments
    public IncentiveEntity(int incentiveId, String incentiveType, String name) {
        this.incentiveId = incentiveId;
        this.incentiveType = incentiveType;
        this.name = name;
    }

    //Custom Constructor
    public IncentiveEntity(String incentiveType, String name) {
        this.incentiveType = incentiveType;
        this.name = name;
    }

    public int getVersion() {
        return version;
    }

    protected void setVersion(int version) {
        this.version = version;
    }

    public int getIncentiveId() {
        return incentiveId;
    }

    public void setIncentiveId(int incentiveId) {
        this.incentiveId = incentiveId;
    }

    public String getIncentiveType() {
        return incentiveType;
    }

    public void setIncentiveType(String incentiveType) {
        this.incentiveType = incentiveType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + incentiveId;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof IncentiveEntity))
            return false;
        IncentiveEntity other = (IncentiveEntity) obj;
        if (incentiveId != other.incentiveId)
            return false;
        return true;
    }
}
