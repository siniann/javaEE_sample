package de.uniba.dsg.dsam.backend.beans;

import de.uniba.dsg.dsam.backend.entities.BeverageEntity;
import de.uniba.dsg.dsam.backend.entities.CustomerOrderEntity;
import de.uniba.dsg.dsam.backend.entities.IncentiveEntity;
import de.uniba.dsg.dsam.backend.entities.SelectedItemEntity;
import de.uniba.dsg.dsam.customexceptions.BeverageException;
import de.uniba.dsg.dsam.customexceptions.OrderException;
import de.uniba.dsg.dsam.model.Beverage;
import de.uniba.dsg.dsam.model.CustomerOrder;
import de.uniba.dsg.dsam.model.Incentive;
import de.uniba.dsg.dsam.model.SelectedItem;
import de.uniba.dsg.dsam.persistence.IncentiveManagement;
import de.uniba.dsg.dsam.persistence.SalesManagement;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Stateless
@Remote(SalesManagement.class)
public class SalesManagementBean implements SalesManagement {

    private static Logger logger = Logger.getLogger(SalesManagement.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    @EJB
    IncentiveManagement incentiveManagement;


    @Override
    public void createOrder(List<SelectedItem> orderedBeverages) throws OrderException {

        if (orderedBeverages != null) {
            //update quantity of Beverage entity and persit Beverage.
            for (SelectedItem item : orderedBeverages) {
                BeverageEntity beverageEntity = entityManager.find(BeverageEntity.class, item.getBeverageId());
                beverageEntity.setQuantity(beverageEntity.getQuantity() - item.getOrderQuantity());
                entityManager.persist(beverageEntity);
            }
            //create a customer order and persist
            CustomerOrderEntity customerOrderEntity = new CustomerOrderEntity();
            customerOrderEntity.setIssueDate(new Date());
            customerOrderEntity.setOrderItems(convert(orderedBeverages));
            entityManager.persist(customerOrderEntity);
            logger.info("New Order persisted");


        }
    }

    private List<SelectedItemEntity> convert(List<SelectedItem> orderedBeverages) {
        List<SelectedItemEntity> selectedItemEntities = new ArrayList<>();

        for (SelectedItem s : orderedBeverages) {
            IncentiveEntity incentive=null;
            if(s.getIncentive()!=null)
            incentive=entityManager.find(IncentiveEntity.class,s.getIncentive().getIncentiveId());

            SelectedItemEntity selectedItemEntity = new SelectedItemEntity();
            selectedItemEntity.setBeverageId(s.getBeverageId());
            selectedItemEntity.setIncentive(incentive);
            selectedItemEntity.setManufacturer(s.getManufacturer());
            selectedItemEntity.setName(s.getName());
            selectedItemEntity.setPrice(s.getPrice());
            selectedItemEntity.setOrderQuantity(s.getOrderQuantity());

            selectedItemEntities.add(selectedItemEntity);
        }
        return selectedItemEntities;
    }


    @Override
    public void getAllOrders() {

    }

    @Override
    public void getOrderByIncentives() {

    }


}
