package de.uniba.dsg.dsam.backend.beans;

import de.uniba.dsg.dsam.customexceptions.OrderException;
import de.uniba.dsg.dsam.model.CustomerOrder;
import de.uniba.dsg.dsam.model.SelectedItem;
import de.uniba.dsg.dsam.persistence.SalesManagement;

import javax.ejb.*;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@MessageDriven(mappedName = "BeverageStoreQueue",
        activationConfig = {
                @ActivationConfigProperty(
                        propertyName = "destinationType", propertyValue = "javax.jms.Queue"
                )})
public class OrderMessageDrivenBean implements MessageListener {

    private static Logger logger = Logger.getLogger(OrderMessageDrivenBean.class.getName());

    @EJB
    SalesManagement salesManagement;

    //default constructor
    public OrderMessageDrivenBean() {
    }

    //This method is invoked when a message reaches the registered queue
    public void onMessage(Message message) {
        if (message == null) {
            logger.log(Level.WARNING, "No message received from the queue");
            return;
        }
        if (message instanceof ObjectMessage) {
            try {
                Object object = ((ObjectMessage) message).getObject();
                if (validateObject(object)) {
                    CustomerOrder orderMessage = (CustomerOrder) object;
                    List<SelectedItem> orderedBeverageNames = orderMessage.getOrderItems();
                    if (salesManagement != null) {
                        salesManagement.createOrder(orderedBeverageNames);
                        logger.info("Order is created successfully.");
                    } else {
                        logger.log(Level.SEVERE, "Failed order.Please retry!");
                    }
                }
            } catch (JMSException e) {
                logger.log(Level.SEVERE, "Encountered error" + e.getCause());
            } catch (OrderException e) {
                new OrderException("Beverage management encountered exception!");
            }
        } else {
            logger.log(Level.WARNING, "Received message of unexpected JMS Type");
        }
    }

    private boolean validateObject(Object object) {
        if (object == null || !(object instanceof CustomerOrder)) {
            logger.log(Level.SEVERE, "Received unexpected message type.");
            return false;
        }
        CustomerOrder orderMessage = (CustomerOrder) object;
        if (orderMessage.getOrderItems() != null) {
            return true;
        } else {
            return false;
        }
    }
}
