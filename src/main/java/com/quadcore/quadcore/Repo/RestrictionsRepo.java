package com.quadcore.quadcore.Repo;

import com.quadcore.quadcore.Entities.Restrictions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RestrictionsRepo extends JpaRepository<Restrictions, Integer> {

    @Query(value = "Select * from restrictions where card_id=:cardId",nativeQuery = true)
    Restrictions restrictionByCardId(long cardId);
}
