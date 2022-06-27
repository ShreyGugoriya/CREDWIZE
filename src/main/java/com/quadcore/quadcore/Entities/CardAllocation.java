package com.quadcore.quadcore.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quadcore.quadcore.Enum.EmployeeStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardAllocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int allocateId;


    @ManyToOne
    @JoinColumn(name = "employee_id")
    private User user;


    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cardAllocation")
    private List<Ticket> tickets;

}
