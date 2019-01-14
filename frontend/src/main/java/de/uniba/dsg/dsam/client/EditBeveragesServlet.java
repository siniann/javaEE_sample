package de.uniba.dsg.dsam.client;

import de.uniba.dsg.dsam.customexceptions.BeverageException;
import de.uniba.dsg.dsam.persistence.BeverageManagement;
import de.uniba.dsg.dsam.persistence.IncentiveManagement;

import java.io.IOException;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditBeveragesServlet extends HttpServlet {
    @EJB
    private BeverageManagement beverageManagement;

    private static final Logger logger = Logger.getLogger
            (EditIncentivesServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        try {
            int id = Integer.valueOf(req.getParameter("id"));
            String name = req.getParameter("name");
            String manufacturer = req.getParameter("manufacturer");
            String price = req.getParameter("price");
            String quantity = req.getParameter("quantity");
            String incentiveid = req.getParameter("incentive_id");


            beverageManagement.update(id, name, manufacturer, price, quantity,
                    incentiveid);
        } catch (NumberFormatException e) {
            logger.severe("Invalid id" + e.getMessage());
        } catch (BeverageException e) {
            logger.severe("Error updating beverage" + e.getMessage());
        }
        req.getRequestDispatcher("/incentives.jsp").forward(req, res);
    }
}
