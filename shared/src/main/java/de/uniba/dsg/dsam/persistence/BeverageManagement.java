package de.uniba.dsg.dsam.persistence;

import de.uniba.dsg.dsam.customexceptions.BeverageException;
import de.uniba.dsg.dsam.customexceptions.IncentiveException;
import de.uniba.dsg.dsam.model.Beverage;
import de.uniba.dsg.dsam.model.Incentive;

import java.util.List;

public interface BeverageManagement {

    //all beverages offered including 0 quantity
    /**
     *
     * @return
     * @throws BeverageException
     */
    public List<Beverage> getBeverages() throws BeverageException;

    //all beverages offered  quantity>0
    /**
     *
     * @return
     * @throws BeverageException
     */
    public List<Beverage> getAvailableBeverages() throws BeverageException;

    public Beverage getBeveragesById(String id);

    /**
     *
     * @param name
     * @param manufacturer
     * @param price
     * @param quantity
     * @param incentive_id
     */
    void create(String name, String manufacturer, String price, String quantity, String incentive_id)
    throws BeverageException;

    /**
     *
     * @param id
     * @param name
     * @param manufacturer
     * @param price
     * @param quantity
     * @param incentiveid
     */
    void update(int id, String name, String manufacturer, String price,
                String quantity, String incentiveid)throws BeverageException;

    /**
     *
     * @param id
     * @return
     */
    boolean delete(int id) throws BeverageException;
}
