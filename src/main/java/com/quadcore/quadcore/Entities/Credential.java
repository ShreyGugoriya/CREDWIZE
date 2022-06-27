package com.quadcore.quadcore.Entities;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.quadcore.quadcore.Enum.Role;
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
public class Credential {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="employee_id", nullable=false)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "employee_id")
    @JsonIdentityReference(alwaysAsId = true)
    private User user;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;


    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "credential")
    private List<Ticket> tickets;

}