package com.assignment.service;

import com.assignment.payload.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDTO> getAllEmployees();
    EmployeeDTO addEmployee(EmployeeDTO employeeDTO) throws Exception;
    List<EmployeeDTO> getEmployeesByDepartment(String departmentName);
}
