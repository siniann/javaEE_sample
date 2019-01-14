package de.uniba.dsg.dsam.client;

import de.uniba.dsg.dsam.customexceptions.BeverageException;
import de.uniba.dsg.dsam.model.Beverage;
import de.uniba.dsg.dsam.model.Incentive;
import de.uniba.dsg.dsam.persistence.BeverageManagement;
import de.uniba.dsg.dsam.persistence.IncentiveManagement;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BeveragesServlet extends HttpServlet {
    /**
     * Beverage fetch,create,delete
     */
    @EJB
    private BeverageManagement bevManagement;
    @EJB
    private IncentiveManagement incManagement;

    private static Logger logger = Logger.getLogger
            (CreateOrderServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        List<Beverage> beverageList = null;
        try {
            beverageList = bevManagement.getBeverages();
            List<Incentive> incentiveList = incManagement.getIncentives();
            req.setAttribute("incentiveList", incentiveList);
            req.setAttribute("beverageList", beverageList);
        } catch (BeverageException e) {
            logger.severe("Error fetching beverages" + e.getMessage());
        }
        req.getRequestDispatcher("/beverages.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        try {
            String name = request.getParameter("name");
            String manufacturer = request.getParameter("manufacturer");
            String price = request.getParameter("price");
            String quantity = request.getParameter("quantity");
            String incentive_id = request.getParameter("incentive_id");

            bevManagement.create(name, manufacturer, price, quantity, incentive_id);
            response.getWriter().write(1);
        } catch (BeverageException e) {
            logger.severe("Error creating beverages" + e.getMessage());
            response.getWriter().write(0);
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        try {
            int id = Integer.valueOf(req.getParameter("id"));
            boolean check = bevManagement.delete(id);
            if (!check)
                res.getWriter().write("0");
            else
                res.getWriter().write("1");
        } catch(NumberFormatException e){
            logger.severe("Invalid beverage id" + e.getMessage());
        }catch (BeverageException e) {
            logger.severe("Error deleting beverage" + e.getMessage());
        }
    }
}
