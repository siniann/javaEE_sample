package de.uniba.dsg.dsam.persistence;

import de.uniba.dsg.dsam.customexceptions.IncentiveException;
import de.uniba.dsg.dsam.model.Incentive;

import java.util.List;

public interface IncentiveManagement {
    /**
     *
     * @param name
     * @param type
     * @return
     */
    public Incentive create(String name,String type) throws IncentiveException;

    /**
     *
     * @return List<Incentive>
     */
    public List<Incentive> getIncentives();

    /**
     *
     * @param incentives
     */
    public void deleteIncentives(List<Incentive> incentives) throws IncentiveException;

    /**
     *
     * @param "incentive_id"
     * @return
     */
    Incentive getIncentives(int id);
//added extra parameter type

    /**
     *
     * @param id
     * @param name
     * @param type
     */
    void update(int id, String name,String type) throws IncentiveException;

    /**
     *
     * @return list of string which contains the names of subclasses implementing incentive
     */
    List<String> getIncentiveTypes();

    /**
     *
     * @param id
     * @return
     * @throws IncentiveException
     */
    boolean delete(int id) throws IncentiveException;

    /**
     *
     * @param id
     * @return
     */
    Incentive convert(int id) throws IncentiveException;
}
