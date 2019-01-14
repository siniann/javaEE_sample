package de.uniba.dsg.dsam.backend.beans;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.*;

import de.uniba.dsg.dsam.backend.entities.BeverageEntity;
import de.uniba.dsg.dsam.backend.entities.CustomerOrderEntity;
import de.uniba.dsg.dsam.backend.entities.IncentiveEntity;
import de.uniba.dsg.dsam.customexceptions.BeverageException;
import de.uniba.dsg.dsam.customexceptions.IncentiveException;
import de.uniba.dsg.dsam.model.Beverage;
import de.uniba.dsg.dsam.model.Incentive;
import de.uniba.dsg.dsam.model.PromotionalGift;
import de.uniba.dsg.dsam.model.TrialPackage;
import de.uniba.dsg.dsam.persistence.BeverageManagement;
import de.uniba.dsg.dsam.persistence.IncentiveManagement;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javafx.beans.binding.Bindings.divide;
import static javafx.beans.binding.Bindings.select;

@Stateless
@Remote(BeverageManagement.class)

public class BeverageManagementBean implements BeverageManagement {

    private static Logger logger = Logger.getLogger(BeverageManagementBean.class.getName());
    private final int VALID = 0;
    private final int INVALID = 1;

    @EJB
    IncentiveManagement incentiveManagement;

    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    EntityManager em;

    @Override
    public List<Beverage> getBeverages() throws BeverageException {

        List<BeverageEntity> beverages;
        beverages = em.createNamedQuery("queryGetBeverages").getResultList();
        if (beverages == null) {
            return new ArrayList<Beverage>();
        } else {
            List<Beverage> list = convert(beverages);
            return list;
        }
    }

    @Override
    public List<Beverage> getAvailableBeverages() throws BeverageException {
        List<BeverageEntity> beverages;
        beverages = em.createNamedQuery("allAvailableBeverages").getResultList();
        if (beverages == null) {
            return new ArrayList<Beverage>();
        } else {
            return convert(beverages);
        }
    }

    @Override
    public Beverage getBeveragesById(String id) {
        checkStringVal(id);
        Query beverageEntityQuery = em.createNamedQuery("beverageById");
        beverageEntityQuery.setParameter("beverageId", (Integer.parseInt(id)));
        BeverageEntity beverageEntity = (BeverageEntity) beverageEntityQuery.getSingleResult();
        Beverage convert = null;
        try {
            convert = convert(beverageEntity);
        } catch (IncentiveException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }

        return convert;
    }
    
    @Override
    public void create(String name, String manufacturer, String price, String quantity,
                       String incentive_id) throws BeverageException {

        IncentiveEntity incentiveEntity = null;
        if (incentive_id.length() != 0)
            incentiveEntity = em.find(IncentiveEntity.class, Integer.parseInt(incentive_id));

        /**
         * Validations before beevrage creation
         */
        checkmanufacturer(manufacturer);
        checkprice(price);
        checkquantity(quantity);
        checkname(name);
        BeverageEntity beverageEntity = new BeverageEntity();
        beverageEntity.setManufacturer(manufacturer);
        beverageEntity.setPrice(Float.parseFloat(price));
        beverageEntity.setQuantity(Integer.parseInt(quantity));
        beverageEntity.setName(name);
        beverageEntity.setIncentive(incentiveEntity);

        em.persist(beverageEntity);


    }

    private void checkname(String name) throws BeverageException {
        if (checkStringVal(name) == INVALID)
            throw new BeverageException("Invalid manufacturer");
    }

    private void checkquantity(String quantity) throws BeverageException {
        if (checkQuantityVal(quantity) == INVALID)
            throw new BeverageException("Invalid quantity");
    }

    private int checkQuantityVal(String quantity) {
        try {
            Integer.parseInt(quantity);
            return VALID;
        } catch (Exception e) {
            return INVALID;
        }
    }

    private void checkprice(String price) throws BeverageException {
        if (checkPriceVal(price) == INVALID)
            throw new BeverageException("Invalid price");
    }

    private int checkPriceVal(String price) {
        try {
            Float.parseFloat(price);
            return VALID;
        } catch (Exception e) {
            return INVALID;
        }

    }

    private void checkmanufacturer(String manufacturer) throws BeverageException {
        if (checkStringVal(manufacturer) == INVALID)
            throw new BeverageException("Invalid manufacturer");
    }

    private int checkStringVal(String stringval) {
        if (stringval.length() == 0 || stringval.equals(null))
            return 1;
        else
            return 0;
    }

    @Override
    public void update(int id, String name, String manufacturer, String price,
                       String quantity, String incentiveid) throws BeverageException {
        BeverageEntity beverageEntity;
        beverageEntity = em.find(BeverageEntity.class, id);

        IncentiveEntity incentiveEntity = null;
        if (incentiveid.length() != 0)
            incentiveEntity = em.find(IncentiveEntity.class, Integer.parseInt(incentiveid));

        if (beverageEntity == null) {
            throw new BeverageException("unknown beverage id: " + id);
        } else {
            //validations before update
            checkname(name);
            checkprice(price);
            checkmanufacturer(manufacturer);
            checkquantity(quantity);

            beverageEntity.setName(name);
            beverageEntity.setManufacturer(manufacturer);
            beverageEntity.setQuantity(Integer.parseInt(quantity));
            beverageEntity.setPrice(Float.parseFloat(price));
            beverageEntity.setIncentive(incentiveEntity);
        }
    }


    private List<Beverage> convert(List<BeverageEntity> beverages) {

        List<Beverage> ret = new ArrayList<Beverage>();

        if (beverages != null) {
            for (BeverageEntity r : beverages) {
                try {
                    ret.add(convert(r));
                } catch (IncentiveException e) {
                    e.printStackTrace();
                }
            }
        }

        return ret;
    }

    private Beverage convert(BeverageEntity r) throws IncentiveException {
        Incentive incentive = null;
        if (r.getIncentive() != null)
            incentive = incentiveManagement.convert(r.getIncentive().getIncentiveId());
        Beverage beverage = new Beverage(r.getBeverageId(), r.getName(), r.getManufacturer(), r.getPrice(),
                r.getQuantity(), incentive);
        return beverage;
    }

    @Override
    public boolean delete(int id) {
        BeverageEntity beverageEntity = em.find(BeverageEntity.class, id);
        em.remove(beverageEntity);
        return true;
    }
}
