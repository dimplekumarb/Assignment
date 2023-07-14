package com.assignment.entities;

import lombok.*;
import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    private String gender;
    private String contact;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}

