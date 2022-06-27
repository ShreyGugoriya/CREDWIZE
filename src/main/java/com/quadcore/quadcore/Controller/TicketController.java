package com.quadcore.quadcore.Controller;

import com.quadcore.quadcore.Entities.*;
import com.quadcore.quadcore.Enum.*;
import com.quadcore.quadcore.Service.CardAllocationService;
import com.quadcore.quadcore.Service.CredentialService;
import com.quadcore.quadcore.Service.EmailSender;
import com.quadcore.quadcore.Service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class TicketController {


    @Autowired
    TicketService ticketService;
    @Autowired
    EmailSender emailSender;

    @Autowired
    CredentialService credentialService;

    @Autowired
    CardAllocationService cardAllocationService;

    @RequestMapping(value = "/ticket/", method = RequestMethod.GET)
    public List<Ticket> getAllTickets()
    {
        return ticketService.getALLTickets();
    }

    @RequestMapping(value = "/ticket/{id}", method = RequestMethod.GET)
    public Ticket getTicketById(@PathVariable("id") int id) {
        return ticketService.getTicketById(id);
    }

    //Get All Tickets by User ID
    @RequestMapping(value = "/ticket/user/{userId}", method = RequestMethod.GET)
    public List<Ticket> getAllTicketsByUserId(@PathVariable("userId") int userId)
    {
        List<Ticket> ticketList = ticketService.getALLTickets();
        List<Ticket> ticketList5 = new ArrayList<>();

        for(Ticket ticket1: ticketList)
        {
            if(ticket1.getCardAllocation().getUser().getEmployee_id()==userId && ticket1.getCardAllocation().getCard().getCardStatus()==true)

            {

                ticketList5.add(ticket1);

            }


        }

        return ticketList5;
    }

    @RequestMapping(value = "/ticket/{cardId}",method = RequestMethod.POST)
    public Ticket createTicket(@RequestBody Ticket ticket, @PathVariable("cardId") int cardId){

        List<Credential> credentialList = credentialService.getCredentials();
        List<Credential> credentials = new ArrayList<>();


        int userId=0;
        List<CardAllocation> cardAllocationList = cardAllocationService.getCardAllocation();
        for(CardAllocation cardAllocation : cardAllocationList){
            if(cardAllocation.getCard().getCardId()==cardId){
                userId=cardAllocation.getUser().getEmployee_id();
            }
        }

        for(Credential credential1 : credentialList )
        {
            if((credential1.getRole()== Role.ROLE_ADMIN || credential1.getRole()==Role.ROLE_BOTH)
                    && (credential1.getUser().getEmployee_id()!=userId))
            {
                credentials.add(credential1);
            }

        }
        long size = credentials.size();

        long min = 0;
        long max = size-1;

//Generate random int value from 50 to 100
        System.out.println("Random value in int from "+min+" to "+max+ ":");
        long random_long = (long)Math.floor(Math.random()*(max-min+1)+min);

        System.out.println("Size - "+ size+"   random = "+random_long);

        Credential credential3 = credentials.get((int) random_long);

        ticket.setCredential(credential3);
        ticket.setDateOpened(LocalDate.now());
        ticket.setDateResolution(LocalDate.now().plusDays(4));
        ticket.setTicketStatus(TicketStatus.OPEN);
        if(ticket.getTicketCategory()== TicketCategory.Forgot_PIN){
            ticket.setTicketPriority(TicketPriority.HIGH);
        } else if (ticket.getTicketCategory()==TicketCategory.Block_the_Card) {
            ticket.setTicketPriority(TicketPriority.HIGH);
        } else if (ticket.getTicketCategory()==TicketCategory.Card_not_working) {
            ticket.setTicketPriority(TicketPriority.HIGH);
        } else if (ticket.getTicketCategory()==TicketCategory.Theft_or_loss_of_card) {
            ticket.setTicketPriority(TicketPriority.HIGH);
        } else if (ticket.getTicketCategory()==TicketCategory.Request_New_Card) {
            ticket.setTicketPriority(TicketPriority.LOW);
        } else if (ticket.getTicketCategory()==TicketCategory.Limit_Modification) {
            ticket.setTicketPriority(TicketPriority.LOW);
        } else if (ticket.getTicketCategory()==TicketCategory.Other) {
            ticket.setTicketPriority(TicketPriority.LOW);
        }

        String detail = ticket.getIssueDetail();
        List<CardAllocation> cardAllocation3 = cardAllocationService.getCardAllocation();
        String cardnumber = null;
        CardAllocation cardAllocation4 = new CardAllocation();
        for(CardAllocation cardAllocation : cardAllocation3)
        {
            if(cardAllocation.getCard().getCardId()==cardId)
            {
                cardAllocation4=cardAllocation;
                cardnumber=cardAllocation.getCard().getCardNumber();
            }
        }

        ticket.setIssueDetail(detail+"\n Card Number is "+cardnumber);
        ticket.setCardAllocation(cardAllocation4);
        ticket.setAssigneeName(credential3.getUser().getName());

        emailSender.sendEmail(credential3.getUsername(), "Ticket Allocation", "Hi "+", \n\nA new ticket has been assigned to you. \nThank you " );

        return ticketService.createTicket(ticket);

    }

    @RequestMapping(value = "/ticket/reply/{id}", method = RequestMethod.PUT)
            public Ticket sendReplyByTicketId(@RequestBody Ticket ticket, @PathVariable("id") int id){
        Ticket ticket1 = ticketService.getTicketById(id);
        ticket1.setTicketStatus(TicketStatus.RESOLVED);
        ticket1.setIssueReply(ticket.getIssueReply());
        return ticketService.createTicket(ticket1);
    }

    @RequestMapping(value = "/ticket-raised/", method = RequestMethod.GET)
    public Long getTicketRaised(){
        return ticketService.getNumberOfTicketRaised();
    }

    @RequestMapping(value = "/ticket-solved/", method = RequestMethod.GET)
    public Long getTicketSolved(){
        return ticketService.getNumberOfTicketSolved();
    }
    @RequestMapping(value = "/ticket-resolved/user/{id}", method = RequestMethod.GET)
    public List<Ticket> getAllTicketResolved(@PathVariable("id") int id){
        return ticketService.getALLResolvedTickets(id);
    }
    @RequestMapping(value = "/ticket-open/user/{id}", method = RequestMethod.GET)
    public List<Ticket> getAllTicketOpen(@PathVariable("id") int id){
        return ticketService.getALLOpenTickets(id);
    }

    @RequestMapping(value = "/ticket/admin/{id}", method = RequestMethod.GET)
    public List<Ticket> getAllTicketAdmin(@PathVariable("id") int id){
        return ticketService.getALLTicketsByAdminId(id);
    }
    @RequestMapping(value = "/ticket-resolved/admin/{id}", method = RequestMethod.GET)
    public List<Ticket> getAllTicketAdminResolved(@PathVariable("id") int id){
        return ticketService.getALLResolvedTicketsAdmin(id);
    }
    @RequestMapping(value = "/ticket-open/admin/{id}", method = RequestMethod.GET)
    public List<Ticket> getAllTicketAdminOpen(@PathVariable("id") int id){
        return ticketService.getALLOpenTicketsAdmin(id);
    }
    @RequestMapping(value = "/ticket-high-priority/admin/{id}", method = RequestMethod.GET)
    public List<Ticket> getAllTicketHighPriorityAdmin(@PathVariable("id") int id){
        return ticketService.getALLHightPriorityTicketsAdmin(id);
    }


    @RequestMapping(value = "/ticket/redeem/{cardId}",method = RequestMethod.POST)
    public Ticket createRedeemTicket(@RequestBody Redeem redeem, @PathVariable("cardId") int cardId){

        List<Credential> credentialList = credentialService.getCredentials();
        List<Credential> credentials = new ArrayList<>();

        int userId=0;
        List<CardAllocation> cardAllocationList = cardAllocationService.getCardAllocation();
        for(CardAllocation cardAllocation : cardAllocationList){
            if(cardAllocation.getCard().getCardId()==cardId){
                userId=cardAllocation.getUser().getEmployee_id();
            }
        }

        for(Credential credential1 : credentialList )
        {
            if((credential1.getRole()== Role.ROLE_ADMIN || credential1.getRole()==Role.ROLE_BOTH)
                    && (credential1.getUser().getEmployee_id()!=userId))
            {
                credentials.add(credential1);
            }

        }
        long size = credentials.size();

        long min = 0;
        long max = size-1;

        System.out.println("Random value in int from "+min+" to "+max+ ":");
        long random_long = (long)Math.floor(Math.random()*(max-min+1)+min);

        System.out.println("Size - "+ size+"   random = "+random_long);

        Credential credential3 = credentials.get((int) random_long);
        Ticket ticket=new Ticket();
        ticket.setCredential(credential3);
        ticket.setDateOpened(LocalDate.now());
        ticket.setDateResolution(LocalDate.now().plusDays(4));
        ticket.setTicketStatus(TicketStatus.OPEN);
        ticket.setTicketPriority(TicketPriority.HIGH);
        ticket.setTicketCategory(TicketCategory.REDEEM);

        String detail = "Want to redeem voucher from "+redeem.getBrand()+" worth "+redeem.getRedeemPoints()+" points";
        List<CardAllocation> cardAllocation3 = cardAllocationService.getCardAllocation();
        String cardnumber = null;
        CardAllocation cardAllocation4 = new CardAllocation();
        for(CardAllocation cardAllocation : cardAllocation3)
        {
            if(cardAllocation.getCard().getCardId()==cardId)
            {
                cardAllocation4=cardAllocation;
                cardnumber=cardAllocation.getCard().getCardNumber();
            }
        }

        ticket.setIssueDetail(detail+"\n Card Number is "+cardnumber);
        ticket.setCardAllocation(cardAllocation4);
        ticket.setAssigneeName(credential3.getUser().getName());

//        UserInfo userInfo = new UserInfo(userService.getUserById(employeeId).getName(), userService.getUserById(employeeId).getEmail(), EmailSubjects.Card_Allocation, 0,cardType,0);


        return ticketService.createTicket(ticket);

    }


}
