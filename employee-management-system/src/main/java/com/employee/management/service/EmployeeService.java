package com.employee.management.service;

import com.employee.management.model.Employee;
import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    Employee saveEmployee(Employee employee);
    Employee getEmployeeById(Long id);
    Employee updateEmployee(Employee employee);
    void deleteEmployeeById(Long id);
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Long id);
}