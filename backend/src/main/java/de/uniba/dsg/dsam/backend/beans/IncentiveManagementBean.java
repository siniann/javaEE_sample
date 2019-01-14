package de.uniba.dsg.dsam.backend.beans;

import de.uniba.dsg.dsam.backend.entities.*;
import de.uniba.dsg.dsam.customexceptions.BeverageException;
import de.uniba.dsg.dsam.customexceptions.IncentiveException;
import de.uniba.dsg.dsam.model.Incentive;
import de.uniba.dsg.dsam.model.PromotionalGift;
import de.uniba.dsg.dsam.model.TrialPackage;
import de.uniba.dsg.dsam.persistence.IncentiveManagement;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * implements incentive management methods
 */
@Stateless
@Remote(IncentiveManagement.class)
public class IncentiveManagementBean implements IncentiveManagement {

    private static Logger logger = Logger.getLogger(IncentiveManagement.class.getName());
    private final int INVALID=1;
    private final int VALID=0;
    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager;
    private List<String> incentiveTypes = new ArrayList<String>();

    /**
     * default constructor
     */
    public IncentiveManagementBean() {
        incentiveTypes.add("trialpackage");
        incentiveTypes.add("promotionalgift");
    }

    /**
     * @param name
     * @param type
     * @return
     */
    @Override
    public Incentive create(String name, String type) throws IncentiveException {
        checkIncentiveName(name);
        checkIncentiveType(type);
        IncentiveEntity d = null;
        if (type.equals(incentiveTypes.get(0))) {
            d = new TrialPackageEntity(type, name);
        } else if (type.equals(incentiveTypes.get(1))) {
            d = new PromotionalGiftEntity(type, name);
        }
        if (d != null)
            entityManager.persist(d);
        else throw new IncentiveException("Could not create incentive entity");
        Incentive incentive = convert(d);
        return incentive;
    }

    private void checkIncentiveType(String type) throws IncentiveException {
        if(checkTypeVal(type)==INVALID)
            throw new IncentiveException("Invalid Name");
    }

    private int checkTypeVal(String type) {
        for(String typename:incentiveTypes){
            if(typename.equals(type))
                return VALID;
        }
        return INVALID;
    }

    private void checkIncentiveName(String stringval) throws IncentiveException {
        if(checkStringVal(stringval)==INVALID)
            throw new IncentiveException("Invalid Name");
    }
    private int checkStringVal(String stringval) {
        if(stringval.length()==0 || stringval.equals(null))
            return INVALID;
        else
            return VALID;
    }

    @Override
    public List<Incentive> getIncentives() {

        List<IncentiveEntity> incentives;
        incentives = entityManager.createNamedQuery("queryGetIncentives").getResultList();
        if (incentives == null) {
            return new ArrayList<Incentive>();
        } else {
            List<Incentive> list = convert(incentives);
            return list;
        }

    }

    /**
     *
     * @param incentives
     */
    @Override
    public void deleteIncentives(List<Incentive> incentives) throws IncentiveException {
        if (incentives == null)
            return;
        for (Incentive r : incentives) {
            checkIncentive(r.getIncentiveId());
            entityManager.remove(r);
        }

    }

    private void checkIncentive(int r) throws IncentiveException {
        IncentiveEntity incentiveEntity=entityManager.find(IncentiveEntity.class,r);
        if(incentiveEntity==null)
            throw new IncentiveException("Invalid id");
    }

    private List<Incentive> convert(List<IncentiveEntity> incentives) {
        List<Incentive> ret = new ArrayList<Incentive>();

        if (incentives != null) {
            for (IncentiveEntity r : incentives) {
                ret.add(convert(r));
            }
        }

        return ret;
    }

    private Incentive convert(IncentiveEntity r) {
        Incentive incentive = null;
        incentive = convert(r.getIncentiveId());
        return incentive;
    }


    /**
     * @param id
     * @return
     */
    @Override
    public Incentive getIncentives(int id) {
        IncentiveEntity incentive;
        incentive = entityManager.find(IncentiveEntity.class, id);
        if (incentive == null) {
            try {
                throw new BeverageException("incentive not found");
            } catch (BeverageException e) {
                e.printStackTrace();
            }
        }
        return convert(incentive);
    }

    /**
     * @param id
     * @param name
     * @param type
     */
    @Override
    public void update(int id, String name, String type) throws IncentiveException {
        checkIncentiveName(name);
        checkIncentiveType(type);
        IncentiveEntity incentives;
        incentives = entityManager.find(IncentiveEntity.class, id);
        if (incentives == null) {
            throw new IllegalArgumentException("unknown incentive id: " + id);
        } else {
            incentives.setName(name);
            incentives.setIncentiveType(type);

        }

    }

    /**
     * @return
     */
    @Override
    public List<String> getIncentiveTypes() {
        return incentiveTypes;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public boolean delete(int id) throws IncentiveException {
        checkIncentive(id);
        IncentiveEntity incentiveEntity = entityManager.find(IncentiveEntity.class, id);
        List<CustomerOrderEntity> orderEntityListist = getRelatedOrders(incentiveEntity);
        List<BeverageEntity> beverageEntities = getRelatedBeverages(incentiveEntity);
        if (orderEntityListist.isEmpty()) {
            if (!beverageEntities.isEmpty()) {
                for (BeverageEntity beverageEntity : beverageEntities) {
                    beverageEntity.setIncentive(null);
                }
            }
            entityManager.remove(incentiveEntity);
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Incentive convert(int id) {
        Incentive incentive = null;
        IncentiveEntity entity = entityManager.find(IncentiveEntity.class, id);
        try {
            if (entity.getIncentiveType().equals("trialpackage")) {
                incentive = new TrialPackage(entity.getIncentiveId(), entity.getName(), entity.getIncentiveType());
            } else {
                incentive = new PromotionalGift(entity.getIncentiveId(), entity.getName(), entity.getIncentiveType());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return incentive;
    }

    /**
     * @param incentiveEntity
     * @return
     */
    private List<CustomerOrderEntity> getRelatedOrders(IncentiveEntity incentiveEntity) {
        List<CustomerOrderEntity> list = null;
        list = entityManager.createNamedQuery("selecteditembyincentive")
                .setParameter("incentiveId", incentiveEntity.getIncentiveId())
                .getResultList();
        return list;
    }

    /**
     * @param incentiveEntity
     * @return
     */
    private List<BeverageEntity> getRelatedBeverages(IncentiveEntity incentiveEntity) {

        List<BeverageEntity> list = null;
        list = entityManager.createNamedQuery("getBeverageByIncentive")
                .setParameter("incentiveId", incentiveEntity.getIncentiveId())
                .getResultList();

        return list;
    }
}
