package de.uniba.dsg.dsam.model;


import java.io.Serializable;
import java.util.List;

public class Beverage implements Serializable{

    private int beverageId;
    private String manufacturer;
    private String name;
    private int quantity;
    private double price;
    private Incentive incentive;

    private List<CustomerOrder> customerOrderList;

    public Beverage(int beverageId, String name, String manufacturer, double price, int quantity,Incentive incentive) {
        this.beverageId=beverageId;
        this.name=name;
        this.manufacturer=manufacturer;
        this.price=price;
        this.quantity=quantity;
        this.incentive=incentive;

    }

    public Beverage() {

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

    public Incentive getIncentive() {
        return incentive;
    }

    public void setIncentive(Incentive incentive) {
        this.incentive = incentive;
    }

    public List<CustomerOrder> getCustomerOrderList() {
        return customerOrderList;
    }

    public void setCustomerOrderList(List<CustomerOrder> customerOrderList) {
        this.customerOrderList = customerOrderList;
    }


}
