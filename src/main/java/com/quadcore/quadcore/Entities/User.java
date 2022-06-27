package com.quadcore.quadcore.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quadcore.quadcore.Enum.EmployeeStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employee_id;
    private String name;
    private String phone_number;
    private String email;
    private String designation;
    private String department;
    @Enumerated(EnumType.STRING)
    private EmployeeStatus employee_status;
//    @OneToOne (cascade = CascadeType.ALL)
//    private Credential credential;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<CardAllocation> cardAllocations;

    @OneToOne(mappedBy = "user")
    private Credential credential;

}
