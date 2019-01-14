package de.uniba.dsg.dsam.backend.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class CustomerOrderEntity {

    //for optimistic locking...
    @Version
    private int version;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int customerOrderId;
    private Date issueDate;
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<SelectedItemEntity> orderItems;


    public CustomerOrderEntity() {
    }

    public CustomerOrderEntity(Date issueDate, List<SelectedItemEntity> orderItems) {
        this.issueDate = issueDate;
        this.orderItems = orderItems;
    }


    public int getVersion() {
        return version;
    }

    protected void setVersion(int version) {
        this.version = version;
    }

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

    public List<SelectedItemEntity> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<SelectedItemEntity> orderItems) {
        this.orderItems = orderItems;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + customerOrderId;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof CustomerOrderEntity))
            return false;
        CustomerOrderEntity other = (CustomerOrderEntity) obj;
        if (customerOrderId != other.customerOrderId)
            return false;
        return true;
    }
}
