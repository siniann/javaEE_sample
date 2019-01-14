package de.uniba.dsg.dsam.persistence;

import de.uniba.dsg.dsam.model.ReportModel;
import de.uniba.dsg.dsam.model.samplemodel;

import java.util.Date;
import java.util.List;

public interface ReportManagement {
    public List<ReportModel> getalldata(Date startdate,Date enddate);
}
