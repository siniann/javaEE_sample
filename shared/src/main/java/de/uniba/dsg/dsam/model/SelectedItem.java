package de.uniba.dsg.dsam.model;

import java.io.Serializable;
import java.util.List;

/**
 * User: sini_ann
 * Date: 09/12/18 1:35 PM
 */
public class SelectedItem implements Serializable {

    private int beverageId;
    private String manufacturer;
    private String name;
    private double price;
    private Incentive incentive;
    private int orderQuantity;


    public SelectedItem() {
    }

    public SelectedItem(int beverageId, String manufacturer, String name, double price, Incentive incentive, int orderQuantity) {
        this.beverageId = beverageId;
        this.manufacturer = manufacturer;
        this.name = name;
        this.price = price;
        this.incentive = incentive;
        this.orderQuantity = orderQuantity;
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

    public Incentive getIncentive() {
        return incentive;
    }

    public void setIncentive(Incentive incentive) {
        this.incentive = incentive;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }
}