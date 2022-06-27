package com.quadcore.quadcore.Controller;

import com.quadcore.quadcore.Entities.Card;
import com.quadcore.quadcore.Entities.CardAllocation;
import com.quadcore.quadcore.Entities.User;
import com.quadcore.quadcore.Service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CardController {

    @Autowired
    CardService cardService;

    @RequestMapping(value = "/card/", method = RequestMethod.GET)
    public ResponseEntity<?> getCard() {
        try {

            return ResponseEntity.ok(cardService.getCard());
        } catch (Exception exception) {
            System.out.println(exception);
            return ResponseEntity.ok(exception.getMessage());
        }
    }

    // Get all the employee
    @RequestMapping(value = "/card/{id}", method = RequestMethod.GET)
    public Card getCardById(@PathVariable("id") long id) {
        try {

            return cardService.getCardById(id);
        } catch (Exception exception) {
            System.out.println(exception);
            return null;
        }
    }

    @RequestMapping(value = "/card/{id}", method = RequestMethod.PUT)
    public Card updateCardById(@RequestBody Card card, @PathVariable("id") int id) {
        try {

            return cardService.updateCardById(card, id );
        } catch (Exception exception) {
            System.out.println(exception);
            return null;
        }
    }

    @RequestMapping(value = "/card-limit/{id}", method = RequestMethod.GET)
    public long getCardLimitByCardId(@PathVariable("id") int id) {
        List<Card> cardList = cardService.getCard();
        for (Card card : cardList) {
            if (card.getCardId() == id) {
                return card.getCardLimit();
            }


        }
        return 0;
    }
    @RequestMapping(value = "/card/",method = RequestMethod.POST)
    public Card createCard(@RequestBody Card card){
        return  cardService.createCard(card);

    }

    @RequestMapping(value="/first-graph/admin/", method = RequestMethod.GET)
    public List<?> getFirstCardDistribution(){
        return cardService.getFirstCardDistribution();
    }
}
