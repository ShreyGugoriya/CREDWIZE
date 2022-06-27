package com.quadcore.quadcore.Service;

import com.quadcore.quadcore.Entities.Ticket;
import com.quadcore.quadcore.Enum.TicketPriority;
import com.quadcore.quadcore.Enum.TicketStatus;
import com.quadcore.quadcore.Repo.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketService {


    @Autowired
    TicketRepository ticketRepository;

    public List<Ticket> getALLTickets() {
        return ticketRepository.findAll();
    }

    public Ticket getTicketById(int id) {
        return ticketRepository.findById(id).get();
    }

    public Ticket createTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public Long getNumberOfTicketRaised() {
        return ticketRepository.getCountTickets();
    }

    public Long getNumberOfTicketSolved() {
        List<Ticket> ticketList = ticketRepository.findAll();
        Long c= Long.valueOf(0);
        for(Ticket ticket1 : ticketList)
        {
            if(ticket1.getTicketStatus()== TicketStatus.RESOLVED)
            {
                c++;
            }

        }

        return c;
    }
    public List<Ticket> getALLResolvedTickets(int id) {

        List<Ticket> ticketList = ticketRepository.findAll();
        List<Ticket> ticket = new ArrayList<>();
        for(Ticket ticket1 : ticketList )
        {
            if(ticket1.getTicketStatus()== TicketStatus.RESOLVED && ticket1.getCardAllocation().getUser().getEmployee_id()==id && ticket1.getCardAllocation().getCard().getCardStatus()==true)
            {
                ticket.add(ticket1);
            }

        }
        return ticket;
    }
    public List<Ticket> getALLOpenTickets(int id) {

        List<Ticket> ticketList = ticketRepository.findAll();
        List<Ticket> ticket = new ArrayList<>();
        for(Ticket ticket1 : ticketList )
        {
            if(ticket1.getTicketStatus()== TicketStatus.OPEN && ticket1.getCardAllocation().getUser().getEmployee_id()==id && ticket1.getCardAllocation().getCard().getCardStatus()==true)
            {
                ticket.add(ticket1);
            }

        }
        return ticket;
    }

    public List<Ticket> getALLTicketsByAdminId(int id) {
        List<Ticket> ticketList = ticketRepository.findAll();
        List<Ticket> ticket = new ArrayList<>();
        for(Ticket ticket1 : ticketList )
        {
            if(ticket1.getCredential().getUser().getEmployee_id()==id && ticket1.getCardAllocation().getCard().getCardStatus()==true)
            {
                ticket.add(ticket1);
            }

        }
        return ticket;
    }

    public List<Ticket> getALLResolvedTicketsAdmin(int id) {
        List<Ticket> ticketList = ticketRepository.findAll();
        List<Ticket> ticket = new ArrayList<>();
        for(Ticket ticket1 : ticketList )
        {
            if(ticket1.getTicketStatus()== TicketStatus.RESOLVED && ticket1.getCredential().getUser().getEmployee_id()==id && ticket1.getCardAllocation().getCard().getCardStatus()==true)

            {
                ticket.add(ticket1);
            }

        }
        return ticket;
    }

    public List<Ticket> getALLOpenTicketsAdmin(int id) {
        List<Ticket> ticketList = ticketRepository.findAll();
        List<Ticket> ticket = new ArrayList<>();
        for(Ticket ticket1 : ticketList )
        {
            if(ticket1.getTicketStatus()== TicketStatus.OPEN && ticket1.getCredential().getUser().getEmployee_id()==id && ticket1.getCardAllocation().getCard().getCardStatus()==true)
            {
                ticket.add(ticket1);
            }

        }
        return ticket;
    }
    public List<Ticket> getALLHightPriorityTicketsAdmin(int id) {
        List<Ticket> ticketList = ticketRepository.findAll();
        List<Ticket> ticket = new ArrayList<>();
        for(Ticket ticket1 : ticketList )
        {
            if(ticket1.getTicketPriority()== TicketPriority.HIGH && ticket1.getCredential().getUser().getEmployee_id()==id && ticket1.getCardAllocation().getCard().getCardStatus()==true)
            {
                ticket.add(ticket1);
            }

        }
        return ticket;
    }
}

