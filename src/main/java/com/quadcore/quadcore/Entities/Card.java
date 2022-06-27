package com.quadcore.quadcore.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quadcore.quadcore.Enum.CardType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cardId;
    @Enumerated(EnumType.STRING)
    private CardType cardType;
    private int cardLimit;
    private String cardName;//doubt
    private Boolean cardAvailable = true;
    private String cardNumber;
    private Boolean cardStatus = true;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "card")
    private List<Transaction> transactions;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "card")
    private List<CardAllocation> cardAllocations;

    @OneToOne(mappedBy = "card")
    private Restrictions restrictions;
}
