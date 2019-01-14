package de.uniba.dsg.dsam.client;


import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.uniba.dsg.dsam.customexceptions.IncentiveException;
import de.uniba.dsg.dsam.model.Incentive;
import de.uniba.dsg.dsam.persistence.IncentiveManagement;

public class EditIncentivesServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(EditIncentivesServlet.class.getName());
    /**
     * Incentive edit
     */
    // IncentiveManagementBean
    @EJB
    private IncentiveManagement incMgmt;

    /**
     *
     * @param req
     * @param res
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        try {
            int id = Integer.valueOf(req.getParameter("id"));
            String name = req.getParameter("name");
            String type = req.getParameter("type");


            incMgmt.update(id, name, type);
        } catch (NumberFormatException e) {
            logger.severe("Invalid id for incentive" + e);
        } catch (IncentiveException e) {//should catch persistance exception here
            logger.severe("Error updating incentive :" + e);
        }
        req.getRequestDispatcher("/incentives.jsp").forward(req, res);
    }
}
