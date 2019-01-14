package de.uniba.dsg.dsam.backend.entities;

import java.io.Serializable;
import javax.persistence.*;


@Entity
public class BeverageEntity implements Serializable{
    @Version
    private int version;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int beverageId;

    private String manufacturer;
    private String name;
    private int quantity;
    private double price;
    //many beverage can have one incentive . Default early fetch is changed
    @ManyToOne(fetch = FetchType.LAZY)
    private IncentiveEntity incentive;


    public int getVersion() {
        return version;
    }

    protected void setVersion(int version) {
        this.version = version;
    }

    public int getBeverageId() {
        return beverageId;
    }

    public void setBeverageId(int beverageId) {
        this.beverageId = beverageId;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public IncentiveEntity getIncentive() {
        return incentive;
    }

    public void setIncentive(IncentiveEntity incentive) {
        this.incentive = incentive;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + beverageId;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof BeverageEntity))
            return false;
        BeverageEntity other = (BeverageEntity) obj;
        if (beverageId != other.beverageId)
            return false;
        return true;
    }


}
