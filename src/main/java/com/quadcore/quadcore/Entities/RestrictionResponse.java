package com.quadcore.quadcore.Entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestrictionResponse {
    private long card;
    private Boolean food;
    private Boolean health;
    private Boolean travel;
    private Boolean shopping;
}
