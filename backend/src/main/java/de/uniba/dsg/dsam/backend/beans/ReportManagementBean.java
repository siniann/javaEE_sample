package de.uniba.dsg.dsam.backend.beans;

import de.uniba.dsg.dsam.model.*;
import de.uniba.dsg.dsam.persistence.ReportManagement;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Stateless
@Remote(ReportManagement.class)
public class ReportManagementBean implements ReportManagement {

    private static Logger logger = Logger.getLogger(ReportManagement.class.getName());

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public List<ReportModel> getalldata(Date startdate,Date enddate) {
        List<ReportModel> objs;
        Query q1 = entityManager.createNamedQuery("getDataWithIncentive");
        q1.setParameter("startdate", startdate);
        q1.setParameter("enddate", enddate);
        objs = q1.getResultList();

        Query q2 = entityManager.createNamedQuery("getDataWithOutIncentive");
        q2.setParameter("startdate", startdate);
        q2.setParameter("enddate", enddate);
        objs.addAll(q2.getResultList());
        return objs;
    }
}
