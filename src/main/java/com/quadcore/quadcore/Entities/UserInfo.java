package com.quadcore.quadcore.Entities;

import com.quadcore.quadcore.Enum.CardType;
import com.quadcore.quadcore.Enum.EmailSubjects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.asm.Advice;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInfo {
    private String name;
    private String email;
    @Enumerated(EnumType.STRING)
    private EmailSubjects subject;
    private int otp;
    @Enumerated(EnumType.STRING)
    private CardType cardType;
    private int employeeId;





    //getter & setter
}