package de.uniba.dsg.dsam.model;


import java.io.Serializable;
import java.util.Date;

public class ReportModel implements Serializable{

    private String incentive_type;
    private String incentive_name;
    private int cus_orderid;
    private Date orderdate;
    private String manufacturer;
    private String name;
    private int quantity;
    private double price;

    public ReportModel(String incentive_type, String incentive_name, int cus_orderid, Date orderdate, String manufacturer, String name, int quantity, double price) {
        this.incentive_type = incentive_type;
        this.incentive_name = incentive_name;
        this.cus_orderid = cus_orderid;
        this.orderdate = orderdate;
        this.manufacturer = manufacturer;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
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

    public String getIncentive_type() {
        return incentive_type;
    }

    public void setIncentive_type(String incentive_type) {
        this.incentive_type = incentive_type;
    }

    public String getIncentive_name() {
        return incentive_name;
    }

    public void setIncentive_name(String incentive_name) {
        this.incentive_name = incentive_name;
    }

    public int getCus_orderid() {
        return cus_orderid;
    }

    public void setCus_orderid(int cus_orderid) {
        this.cus_orderid = cus_orderid;
    }

    public Date getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(Date orderdate) {
        this.orderdate = orderdate;
    }
}
