package com.quadcore.quadcore.Entities;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.quadcore.quadcore.Enum.CardType;
import com.quadcore.quadcore.Enum.EFinance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDate;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Analytics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long analyticsId;
    private LocalDate duration;
    private CardType cardname;
    private String designation;
    private String department;

}

