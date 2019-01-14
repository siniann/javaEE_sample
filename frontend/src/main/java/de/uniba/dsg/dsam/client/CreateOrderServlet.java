package de.uniba.dsg.dsam.client;

import de.uniba.dsg.dsam.customexceptions.BeverageException;
import de.uniba.dsg.dsam.customexceptions.OrderException;
import de.uniba.dsg.dsam.model.Beverage;
import de.uniba.dsg.dsam.model.CustomerOrder;
import de.uniba.dsg.dsam.model.SelectedItem;
import de.uniba.dsg.dsam.persistence.BeverageManagement;
import de.uniba.dsg.dsam.persistence.SalesManagement;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jms.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User: sini_ann
 * Date: 08/12/18 10:07 PM
 */
@WebServlet(name = "CreateOrderServlet")
@Stateless
public class CreateOrderServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(CreateOrderServlet.class.getName());

    @Resource(mappedName = "BeverageStoreCF")
    private QueueConnectionFactory queueConnectionFactory;
    @Resource(mappedName = "BeverageStoreQueue")
    private Queue queue;


    @EJB
    BeverageManagement beverageManagement;

    @EJB
    SalesManagement salesManagement;


    /*
     * doGET method passes on the request dispatcher to the appropriate jsp page.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.setLevel(Level.ALL);
        logger.info("Initiated doGet() ");
        // get all available beverages for a new order
        try {
            refreshList(request, response);
        } catch (BeverageException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        request.getRequestDispatcher("/createOrder.jsp").forward(request, response);
    }

    /*
     * refreshList method fetches data from database about the available beverages
     * so that customers can choose from all the available options from UI.
     */
    private void refreshList(HttpServletRequest request, HttpServletResponse response) throws BeverageException {

        List<Beverage> availableBeverageList = new ArrayList<Beverage>();
        availableBeverageList = beverageManagement.getAvailableBeverages();
        logger.info("Fetching a list of all available beverages");
        request.setAttribute("availableBeverageList", availableBeverageList);

    }

    /**
     * doPOST method collects an order from the jsp page and send an object message to the message driven bean
     * using the JMS Queue.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        logger.setLevel(Level.ALL);
        logger.info("doPost method initiated.");
        ArrayList<SelectedItem> selectedItemList = new ArrayList<>();
        String[] beverageIds = request.getParameterValues("selectedBeverage");
        try {
            if (beverageIds.length == 0) {
                throw new OrderException("No items selected while creating the order!");
            }
        } catch (OrderException e) {
            logger.warning(e.getMessage());

        }
        if (beverageIds.length > 0) {
            logger.info("Beverage ids received");
            for (int i = 0; i < beverageIds.length; i++) {
                String id = beverageIds[i];
                String countId = id + "_quantity";
                String itemcount = request.getParameter(countId);
                try {
                    if (itemcount == null)
                        throw new OrderException("No quantity selection made while creating order");
                } catch (OrderException e) {
                    logger.log(Level.WARNING, e.getMessage());
                }

                String[] count = itemcount.split("_");
                int quantity = Integer.parseInt(count[0]);

                if (quantity > 0) {

                    Beverage beverage = beverageManagement.getBeveragesById(beverageIds[i]);
                    SelectedItem selectedItem = new SelectedItem();

                    selectedItem.setName(beverage.getName());
                    selectedItem.setManufacturer(beverage.getManufacturer());
                    selectedItem.setBeverageId(beverage.getBeverageId());
                    selectedItem.setIncentive(beverage.getIncentive());
                    selectedItem.setPrice(beverage.getPrice());
                    selectedItem.setOrderQuantity(quantity);

                    selectedItemList.add(selectedItem);

                }
            }
            logger.info("Creating a new customer order");
            CustomerOrder customerOrder = new CustomerOrder();
            customerOrder.setIssueDate(new Date());
            customerOrder.setOrderItems(selectedItemList);
            logger.info("Sending the customer order to the queue");
            sendOrderToQueue(customerOrder);
        }
        request.setAttribute("selectedItems", selectedItemList);
        request.getRequestDispatcher("/confirmOrder.jsp").forward(request, response);

    }

    /**
     * Method sendOrderMessage establishes a connection with the JMS Queue and sends an order to the
     * message driven bean.
     */
    private void sendOrderToQueue(CustomerOrder customerOrder) {
        logger.info("Creating the JMS queue connection");

        //Establishing a connection to the queue.
        QueueConnection queueConnection = null;

        try (Connection connection = queueConnectionFactory.createConnection()) {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(queue);
            ObjectMessage message = session.createObjectMessage(customerOrder);
            producer.send(message);
            logger.info("Object message created and sent to the queue.");

        } catch (JMSException e) {
            logger.log(Level.SEVERE, "We have encountered JMSException" + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            try {
                if (queueConnection != null) {
                    queueConnection.close();
                }
            } catch (JMSException e) {
                e.getMessage();
            }
        }

    }


}
