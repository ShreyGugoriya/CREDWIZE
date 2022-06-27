package com.quadcore.quadcore.Service;

import com.quadcore.quadcore.Entities.RestrictionResponse;
import com.quadcore.quadcore.Entities.Restrictions;
import com.quadcore.quadcore.Repo.RestrictionsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestrictionService {

    @Autowired
    RestrictionsRepo restrictionsRepo;

    public List<Restrictions> getAllRestriction(){
        return restrictionsRepo.findAll();
    }
    public Restrictions getRestrictionByCardId(long cardId){
        return restrictionsRepo.restrictionByCardId(cardId);
    }
    public Restrictions createRestrictions(Restrictions restrictions) {
        return restrictionsRepo.save(restrictions);
    }

    public Restrictions updateRestrictionByCardId(RestrictionResponse restrictionResponse,long cardId){
        Restrictions restrictions = getRestrictionByCardId(cardId);
        if(restrictionResponse.getFood()!=null) restrictions.setFood(restrictionResponse.getFood());
        if(restrictionResponse.getHealth()!=null) restrictions.setHealth(restrictionResponse.getHealth());
        if(restrictionResponse.getShopping()!=null) restrictions.setShopping(restrictionResponse.getShopping());
        if(restrictionResponse.getTravel()!=null) restrictions.setTravel(restrictionResponse.getTravel());
        return restrictionsRepo.save(restrictions);
    }
}
