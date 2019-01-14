package de.uniba.dsg.dsam.backend.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User: sini_ann
 * Date: 09/12/18 2:07 PM
 */
@Entity
public class SelectedItemEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int selectedItemId;
    private int beverageId;
    private String manufacturer;
    private String name;
    private double price;

    @OneToOne
    private IncentiveEntity incentive;
    private int orderQuantity;

    public SelectedItemEntity() {

    }

    public SelectedItemEntity(int beverageId, String manufacturer, String name, double price, IncentiveEntity incentive, int orderQuantity) {
        this.beverageId = beverageId;
        this.manufacturer = manufacturer;
        this.name = name;
        this.price = price;
        this.incentive = incentive;
        this.orderQuantity = orderQuantity;
    }

    public int getSelectedItemId() {
        return selectedItemId;
    }

    public void setSelectedItemId(int selectedItemId) {
        this.selectedItemId = selectedItemId;
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

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + selectedItemId;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof SelectedItemEntity))
            return false;
        SelectedItemEntity other = (SelectedItemEntity) obj;
        if (selectedItemId != other.selectedItemId)
            return false;
        return true;
    }
}
