package com.quadcore.quadcore.Repo;

import com.quadcore.quadcore.Entities.CardAllocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CardAllocationRepository extends JpaRepository<CardAllocation,Integer> {
//    @Query(nativeQuery = true,
//            value = "select t.reward_points from transaction t" +
//                    "inner join card_allocation c on t.card_id=c.card_id" +
//                    "where c.employee_id=:id  ")
//    long totalCredits(int id);

}
