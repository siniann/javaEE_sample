package de.uniba.dsg.dsam.client;

import com.google.gson.Gson;
import de.uniba.dsg.dsam.persistence.IncentiveManagement;
import de.uniba.dsg.dsam.persistence.ReportManagement;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportServlet extends HttpServlet {
    @EJB
    private ReportManagement reportManagement;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date startdate=null,enddate=null;
        try {
             startdate = formatter.parse(req.getParameter("startDate"));
             enddate = formatter.parse(req.getParameter("endDate"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String json = new Gson().toJson(reportManagement.getalldata(startdate,enddate));
        response.setContentType("text/plain");
        response.getWriter().write(json);
    }


}
