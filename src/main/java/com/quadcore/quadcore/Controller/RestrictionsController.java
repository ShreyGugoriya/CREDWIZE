package com.quadcore.quadcore.Controller;

import com.quadcore.quadcore.Entities.Card;
import com.quadcore.quadcore.Entities.RestrictionResponse;
import com.quadcore.quadcore.Entities.Restrictions;
import com.quadcore.quadcore.Service.RestrictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestrictionsController {
    @Autowired
    RestrictionService restrictionService;

    @RequestMapping(value = "/restriction/", method = RequestMethod.GET)
    public List<Restrictions> getAllRestrictions()
    {
        return restrictionService.getAllRestriction();
    }
    @RequestMapping(value = "/restriction/{cardId}/", method = RequestMethod.GET)
    public Restrictions getRestrictionsByCardId(@PathVariable("cardId") long cardId)
    {
        return restrictionService.getRestrictionByCardId(cardId);
    }
    @RequestMapping(value = "/restriction/", method = RequestMethod.POST)
    public Restrictions setRestrictions(@RequestBody Restrictions restrictions)
    {
        return restrictionService.createRestrictions(restrictions);
    }

    @RequestMapping(value = "/restriction/{cardId}", method = RequestMethod.PUT)
    public Restrictions updateRestriction(@RequestBody RestrictionResponse restrictionResponse,@PathVariable("cardId") long cardId) {
        try {

            return restrictionService.updateRestrictionByCardId(restrictionResponse,cardId);
        } catch (Exception exception) {
            return null;
        }
    }
}
