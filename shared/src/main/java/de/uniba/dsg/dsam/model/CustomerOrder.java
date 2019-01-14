package de.uniba.dsg.dsam.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CustomerOrder  implements Serializable{

    private int customerOrderId;
    private Date issueDate;
    //list of beverages with quantity in the order
    private List<SelectedItem> orderItems;



    public int getCustomerOrderId() {
        return customerOrderId;
    }

    public void setCustomerOrderId(int customerOrderId) {
        this.customerOrderId = customerOrderId;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public List<SelectedItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<SelectedItem> orderItems) {
        this.orderItems = orderItems;
    }
}
