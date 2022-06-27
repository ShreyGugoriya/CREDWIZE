package com.quadcore.quadcore.Service;

import com.quadcore.quadcore.Entities.Card;
import com.quadcore.quadcore.Entities.CardAllocation;
import com.quadcore.quadcore.Entities.Transaction;
import com.quadcore.quadcore.Repo.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;
//
//    @Autowired
//    CardAllocation cardAllocation;

    @Autowired
    CardAllocationService cardAllocationService;

    public List<Transaction> getAlldata(int id, String startDate, String endDate) {
        return transactionRepository.getFilteredTransactionListByCardId(id, startDate, endDate);
    }

    public List<Transaction> getAlldataByCardId(int id) {
        return transactionRepository.getTransactionListByCardId(id);
    }

    public List<Transaction> getAllTransaction() {
//        return transactionRepository.getOrderByTransa()
        return transactionRepository.findAll(Sort.by(Sort.Direction.DESC, "transactionDate"));
    }

    public List<Transaction> getAllTransactionByMonth(int id, int month,int year) {
//
        return transactionRepository.getTransactionListByMonth(id, month,year);
    }

    public Long getAllAmountByMonth(int id) {
//
        return transactionRepository.getAmountByMonth(id);
    }

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public List<?> getTransactionPerMonth(int card_id,int year) {
        return transactionRepository.getSumAmountPerMonth(card_id,year);
    }

    public List<?> getTransactionPerEtype(int card_id) {
        return transactionRepository.getSumAmountPerEType(card_id);
    }

    public List<?> getTransactionPerYearPerCard(int card_id, int year) {
        return transactionRepository.getTotalSumAmountPerCard(card_id, year);
    }

    public Long getRewardsPerCard(int card_id) {
        return transactionRepository.getTotalRewardSumPerCard(card_id);
    }

    public Long getPendingPayments() {
        return transactionRepository.getPendingPaymentsAll();
    }


    public List<?> getCardExpenditureOfAdmin() {
        return transactionRepository.getCardExpenditure();
    }

    public List<?> getShareOfExpenseOfAdmin() {
        return transactionRepository.getShareOfExpense();
    }

    public List<Transaction> getAllTransactionOfLast6Month(int userId) {

        List<List<Transaction>> transactionByUser = new ArrayList<>();

        List<CardAllocation> cardAllocationUser = cardAllocationService.getCardAllocation();
        List<Card> cardByUSer = new ArrayList<>();

        for (CardAllocation cardAllocation : cardAllocationUser) {
            if (cardAllocation.getUser().getEmployee_id() == userId) {
                cardByUSer.add(cardAllocation.getCard());
            }
        }

        for (Card card : cardByUSer) {
            transactionByUser.add(card.getTransactions());
        }

        List<Transaction> transactionResult = new ArrayList<>();

        for (List<Transaction> transactions1 : transactionByUser) {
            for (Transaction transaction2 : transactions1) {
                System.out.println("\nGet Month = "+transaction2.getTransactionDate().getMonth());
                System.out.println("Local = "+LocalDate.now().getMonth().minus(6));

                if (transaction2.getTransactionDate().compareTo(LocalDate.now().minusMonths(6)) > -1 && transaction2.getTransactionDate().compareTo(LocalDate.now()) < 1) {
                    System.out.println("11212121212121");
                    transactionResult.add(transaction2);
                }
            }
        }
        return transactionResult;
    }

    public List<?> getShareOfExpenseOfUserByMonth(int card_id, int month,int year) {
        return transactionRepository.getShareOfExpenseOfUserByMonth(card_id,month,year);
    }
}
