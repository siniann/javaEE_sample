package de.uniba.dsg.dsam.persistence;


import de.uniba.dsg.dsam.customexceptions.BeverageException;
import de.uniba.dsg.dsam.customexceptions.OrderException;
import de.uniba.dsg.dsam.model.CustomerOrder;
import de.uniba.dsg.dsam.model.SelectedItem;

import java.util.List;

public interface SalesManagement {

    public void createOrder(List<SelectedItem> orderedBeverages) throws OrderException;

    public void getAllOrders();

    public void getOrderByIncentives();

}
