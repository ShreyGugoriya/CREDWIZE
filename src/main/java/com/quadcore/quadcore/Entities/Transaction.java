package com.quadcore.quadcore.Entities;

import com.fasterxml.jackson.annotation.*;
import com.quadcore.quadcore.Enum.Etype;
import com.quadcore.quadcore.Enum.TransactionMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionId;
//    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.PERSIST,
//            CascadeType.MERGE, CascadeType.REFRESH})
//    @JoinColumn(name = "cardId",referencedColumnName = "cardId")
//    private Card card;


    @ManyToOne(optional=false)
    @JoinColumn(name="cardId", nullable=false)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "cardId")
    @JsonIdentityReference(alwaysAsId = true)
    private Card card;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate transactionDate;
    private Long transactionAmount;
    @Enumerated(EnumType.STRING)
    private TransactionMethod transactionMethod;
    private String transactionDescription;
    private Long rewardPoints;
    @Enumerated(EnumType.STRING)
    private Etype expenditureType;
}

