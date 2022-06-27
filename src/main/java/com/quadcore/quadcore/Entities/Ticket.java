package com.quadcore.quadcore.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quadcore.quadcore.Enum.TicketCategory;
import com.quadcore.quadcore.Enum.TicketPriority;
import com.quadcore.quadcore.Enum.TicketStatus;
import jdk.jfr.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ticketId;
    private TicketCategory ticketCategory;
    private String issueDetail;
    private String issueReply;
    private LocalDate dateOpened;
    private LocalDate dateResolution;
    private TicketStatus ticketStatus;
    private TicketPriority ticketPriority;
    private String assigneeName;

    @ManyToOne
    @JoinColumn(name = "allocateId")
    private CardAllocation cardAllocation;

    @ManyToOne
    @JoinColumn(name="id" )
    private Credential credential;



}
