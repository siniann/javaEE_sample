package de.uniba.dsg.dsam.client;

import de.uniba.dsg.dsam.customexceptions.BeverageException;
import de.uniba.dsg.dsam.customexceptions.IncentiveException;
import de.uniba.dsg.dsam.model.Incentive;
import de.uniba.dsg.dsam.persistence.IncentiveManagement;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class IncentiveServlet extends HttpServlet {
    /**
     * Incentive fetch,create,delete,
     */
    @EJB
    private IncentiveManagement incManagement;

    private static Logger logger = Logger.getLogger(CreateOrderServlet.class.getName());

    /**
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Incentive> incentiveList = incManagement.getIncentives();
        List<String> incentiveTypes = incManagement.getIncentiveTypes();
        req.setAttribute("incentiveList", incentiveList);
        req.setAttribute("incentiveTypes", incentiveTypes);
        req.getRequestDispatcher("/incentives.jsp").forward(req, resp);
    }

    /**
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String incentivename = request.getParameter("name");
            String incentivetype = request.getParameter("type");

            if (incentivename.equals(null) || incentivetype.equals(null)) {
                throw new IncentiveException("Missing Incentive details!");
            }

            incManagement.create(incentivename, incentivetype);
        }catch (IncentiveException e) {
            logger.severe("Error creating incentive" + e);
        }
        response.sendRedirect(request.getContextPath() + "/frontend/incentives");
    }

    /**
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.valueOf(req.getParameter("id"));
            boolean check = incManagement.delete(id);
            if (!check)
                resp.getWriter().write("0");
            else
                resp.getWriter().write("1");
        } catch(NumberFormatException e) {
            logger.severe("Invalid data for incentive id: Must be int" + e);
        } catch (IncentiveException e) {
            logger.severe("Error deleting incentive" + e);
        }
    }
}
