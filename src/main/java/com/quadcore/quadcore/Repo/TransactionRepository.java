package com.quadcore.quadcore.Repo;

import com.quadcore.quadcore.Entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Query(nativeQuery = true, value = " Select * from transaction t where t.card_id=:id" +
            "            and (t.transaction_date >= :startDate and t.transaction_date <= :endDate) order by t.transaction_date desc")
    List<Transaction> getFilteredTransactionListByCardId(int id, String startDate, String endDate);

    @Query(nativeQuery = true, value = " Select * from transaction t where t.card_id=:id order by t.transaction_date desc")
    List<Transaction> getTransactionListByCardId(int id);

    @Query(nativeQuery = true, value = " Select * from transaction t where t.card_id=:id and Month(t.transaction_date)=:month and year(t.transaction_date)=:year")
    List<Transaction> getTransactionListByMonth(int id, int month,int year);

    @Query(nativeQuery = true, value = " Select SUM(transaction_amount) from transaction t where t.card_id=:id and Month(t.transaction_date)=Month(current_date)  group by t.card_id ")
    Long getAmountByMonth(int id);

    @Query(nativeQuery = true, value = " Select month(transaction_date),SUM(transaction_amount)" +
            " from transaction where card_id=:id and year(transaction_date)=:year group by month(transaction_date)")

    List<?> getSumAmountPerMonth(int id,int year);
    @Query(nativeQuery = true, value = " Select expenditure_type,sum(transaction_amount)" +
            " from transaction where card_id=:id group by expenditure_type ")

    List<?> getSumAmountPerEType(int id);
    @Query(nativeQuery = true, value = " Select card_id ,sum(transaction_amount)" +
            " from transaction where card_id=:id and year(transaction_date)=:year")

    List<?> getTotalSumAmountPerCard(int id, int year);
    @Query(nativeQuery = true, value = " Select sum(reward_points)" +
            " from transaction where card_id=:id")

    Long getTotalRewardSumPerCard(int id);
    @Query(nativeQuery = true, value = " Select sum(transaction_amount)" +
            " from transaction")
    Long getPendingPaymentsAll();

    @Query(nativeQuery = true, value = "Select c.card_type, sum(t.transaction_amount) from card c inner join transaction t on c.card_id = t.card_id group by c.card_type")
    List<?> getCardExpenditure();

    @Query(nativeQuery = true, value = " Select expenditure_type,sum(transaction_amount)" +
            " from transaction group by expenditure_type ")
    List<?> getShareOfExpense();

    @Query(nativeQuery = true, value = " Select expenditure_type,sum(transaction_amount)" +
            " from transaction where card_id=:card_id and month(transaction_date)=:month and year(transaction_date)=:year group by expenditure_type ")
    List<?> getShareOfExpenseOfUserByMonth(int card_id, int month,int year);
}
