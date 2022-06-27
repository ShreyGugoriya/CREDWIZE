package com.quadcore.quadcore.Repo;

import com.quadcore.quadcore.Entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    @Query(nativeQuery = true,value ="Select count(*) from ticket")
    Long getCountTickets();

}
