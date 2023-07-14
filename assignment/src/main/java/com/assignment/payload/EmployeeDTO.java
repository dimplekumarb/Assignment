package com.assignment.payload;

import com.assignment.entities.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    private Long id;
    private String name;
    private Address address;
    private String gender;
    private String contact;
    private String department;
}

