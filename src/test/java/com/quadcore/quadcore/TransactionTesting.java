package com.quadcore.quadcore;

import com.quadcore.quadcore.Entities.Card;
import com.quadcore.quadcore.Entities.Transaction;
import com.quadcore.quadcore.Enum.CardType;
import com.quadcore.quadcore.Enum.TransactionMethod;
import com.quadcore.quadcore.Repo.CardRepository;
import com.quadcore.quadcore.Repo.TransactionRepository;
import com.quadcore.quadcore.Service.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
public class TransactionTesting {

    @Test
    void contextLoads() {
    }
    @Autowired
    TransactionService transactionService;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    CardRepository cardRepository;

    @Test
    public void SaveTransactionTest() {
        Card card = new Card();
        card.setCardId(2);
        card.setCardName("HDFC");
        card.setCardType(CardType.Platinum);
        card.setCardLimit(10000);

        cardRepository.save(card);

        Transaction transaction = new Transaction();
        transaction.setTransactionId(1);
        transaction.setTransactionAmount(500L);
        transaction.setTransactionDescription("Amazon");
        transaction.setTransactionMethod(TransactionMethod.Credit);
        transaction.setTransactionDate(LocalDate.ofEpochDay(2022 - 05 - 15));
        transaction.setCard(card);

        transactionRepository.save(transaction);

        Optional<Transaction> byId = transactionRepository.findById(1);
//        Optional<Card> cardById = cardRepository.findById(1);
        Assertions.assertTrue(byId.isPresent(), "Entity on present");
    }

    @Test
    public void getTransaction(){
        Optional<Transaction> byId = transactionRepository.findById(1);
        Assertions.assertTrue(byId.get().getTransactionId()==1, "Department Entity is present");
    }

    @Test
    public void getTransactionByCardId(){
        Optional<Transaction> byId = transactionRepository.findById(1);
        Assertions.assertTrue(byId.get().getCard().getCardId() == 1, "Department Entity is present");

    }

    @Test
    public void getSumOfRewardByCardId(){
        Optional<Transaction> byId = transactionRepository.findById(1);
        Assertions.assertEquals(transactionService.getAllAmountByMonth(1), 1800, "Reward sum is calculated");

    }

}
