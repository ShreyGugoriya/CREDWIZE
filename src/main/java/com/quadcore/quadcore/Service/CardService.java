package com.quadcore.quadcore.Service;

import com.quadcore.quadcore.Entities.Card;
import com.quadcore.quadcore.Repo.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class CardService {
    @Autowired
    CardRepository cardRepository;
    public List<Card> getCard() {
        return cardRepository.findAll();
    }

    public Card getCardById(long id) {
        return cardRepository.findById(id).get();
    }

    public Card createCard(Card card) {
        return cardRepository.save(card);
    }

    public Card updateCardById(Card card, int id) throws Exception {
        Card bill=getCardById(id);
        if(bill==null){
            throw new Exception("Card does not exist");
        }
        if(card.getCardLimit()!=0) bill.setCardLimit(card.getCardLimit());
        if(card.getCardName()!=null) bill.setCardName(card.getCardName());
        if(card.getCardType()!=null) bill.setCardType(card.getCardType());

        return cardRepository.save(bill);
    }

    public List<?> getFirstCardDistribution() {
        return cardRepository.getCardDistribution();
    }
}
