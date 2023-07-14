package com.assignment.service.impl;

import com.assignment.entities.Address;
import com.assignment.entities.Department;
import com.assignment.entities.Employee;
import com.assignment.payload.AddressDTO;
import com.assignment.payload.EmployeeDTO;
import com.assignment.repository.DepartmentRepository;
import com.assignment.repository.EmployeeRepository;
import com.assignment.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        logger.info("Fetching all employees");
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(this::mapToEmployeeDTO).collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) throws Exception {
        try {
            Employee employee = mapToEmployee(employeeDTO);
            Department department = new Department();
            department.setDepartmentName(employeeDTO.getDepartment());
            employee.setDepartment(department);
            departmentRepository.save(department); // Save the department first
            Employee savedEmployee = employeeRepository.save(employee);
            return mapToEmployeeDTO(savedEmployee);
        } catch (Exception e) {
            logger.error("Error occurred while adding an employee: {}", e.getMessage());
            throw new Exception("Error occurred while adding an employee");
        }
    }

    @Override
    public List<EmployeeDTO> getEmployeesByDepartment(String departmentName) {
        logger.info("Fetching employees for department: {}", departmentName);
        List<Employee> employees = employeeRepository.findByDepartment_DepartmentName(departmentName);
        return employees.stream().map(this::mapToEmployeeDTO).collect(Collectors.toList());
    }

    private Employee mapToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setGender(employeeDTO.getGender());
        employee.setContact(employeeDTO.getContact());

        Address address = new Address();
        address.setId(employeeDTO.getAddress().getId());
        address.setStreet1(employeeDTO.getAddress().getStreet1());
        address.setStreet2(employeeDTO.getAddress().getStreet2());
        address.setCity(employeeDTO.getAddress().getCity());
        address.setState(employeeDTO.getAddress().getState());

        employee.setAddress(address);

        return employee;
    }

    private EmployeeDTO mapToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setGender(employee.getGender());
        employeeDTO.setContact(employee.getContact());
        employeeDTO.setDepartment(employee.getDepartment().getDepartmentName());

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet1(employee.getAddress().getStreet1());
        addressDTO.setStreet2(employee.getAddress().getStreet2());
        addressDTO.setCity(employee.getAddress().getCity());
        addressDTO.setState(employee.getAddress().getState());

        employeeDTO.setAddress(addressDTO);

        return employeeDTO;
    }
}
