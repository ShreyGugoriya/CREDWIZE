package com.quadcore.quadcore.Entities;
import com.quadcore.quadcore.Enum.CardType;
import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GraphResult {
    private CardType cardType;
    private int month;
    private long amount;
}