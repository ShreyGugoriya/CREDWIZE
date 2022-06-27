package com.quadcore.quadcore.Entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.quadcore.quadcore.Enum.TicketCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Restrictions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int restrictionId;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="cardId", nullable=false)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "cardId")
    @JsonIdentityReference(alwaysAsId = true)
    private Card card;
    private Boolean food=false;
    private Boolean health=false;
    private Boolean travel=false;
    private Boolean shopping=false;
}
