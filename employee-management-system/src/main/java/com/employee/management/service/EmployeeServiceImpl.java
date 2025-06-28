package com.employee.management.service;

import com.employee.management.model.Employee;
import com.employee.management.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    
    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
    
    @Override
    public Employee getEmployeeById(Long id) {
        Optional<Employee> optional = employeeRepository.findById(id);
        Employee employee = null;
        if (optional.isPresent()) {
            employee = optional.get();
        } else {
            throw new RuntimeException("Employee not found for id :: " + id);
        }
        return employee;
    }
    
    @Override
    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
    
    @Override
    public void deleteEmployeeById(Long id) {
        employeeRepository.deleteById(id);
    }
    
    @Override
    public boolean existsByEmail(String email) {
        return employeeRepository.existsByEmail(email);
    }
    
    @Override
    public boolean existsByEmailAndIdNot(String email, Long id) {
        Optional<Employee> employee = employeeRepository.findByEmail(email);
        return employee.isPresent() && !employee.get().getId().equals(id);
    }
}