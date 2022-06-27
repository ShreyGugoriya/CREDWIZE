package com.quadcore.quadcore.Repo;

import com.quadcore.quadcore.Entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    @Query(nativeQuery = true, value = "Select card_type,count(*) from card where card_available = false and card_status = true group by card_type ")
    List<?> getCardDistribution();

}
