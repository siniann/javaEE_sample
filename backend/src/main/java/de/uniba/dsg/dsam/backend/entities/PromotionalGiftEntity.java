package de.uniba.dsg.dsam.backend.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@DiscriminatorValue("promotionalgift")
public class PromotionalGiftEntity extends IncentiveEntity  implements Serializable{

    //default constructor
    public PromotionalGiftEntity(){
    }

    public PromotionalGiftEntity(String incentiveType, String incentiveName) {
        super(incentiveType, incentiveName);
    }
}
