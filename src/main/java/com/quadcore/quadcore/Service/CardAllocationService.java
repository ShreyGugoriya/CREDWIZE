package com.quadcore.quadcore.Service;

import com.quadcore.quadcore.Entities.Card;
import com.quadcore.quadcore.Entities.CardAllocation;
import com.quadcore.quadcore.Repo.CardAllocationRepository;
import com.quadcore.quadcore.Repo.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardAllocationService {
    @Autowired
    CardAllocationRepository cardAllocationRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    CardService cardService;
    public List<CardAllocation> getCardAllocation() {
        return cardAllocationRepository.findAll();
    }

    public CardAllocation createCardAllocation(CardAllocation cardAllocation) {
        return cardAllocationRepository.save(cardAllocation);
    }

    public void deallocateCardByCardId(long cardId, Card card) {
        Card card1 = cardService.getCardById(cardId);
        card1.setCardStatus(card.getCardStatus());
        cardRepository.save(card1);

    }

//    public long getTotalRewardPointsByEmployeeID(int employeeId) {
//        return cardAllocationRepository.totalCredits(employeeId);
//    }
}
