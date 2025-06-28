package com.employee.management.controller;

import com.employee.management.model.Employee;
import com.employee.management.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EmployeeController {
    
    @Autowired
    private EmployeeService employeeService;
    
    // Display list of employees
    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("listEmployees", employeeService.getAllEmployees());
        return "index";
    }
    
    // Show form for adding new employee
    @GetMapping("/showNewEmployeeForm")
    public String showNewEmployeeForm(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "create-employee";
    }
    
    // Save employee
    @PostMapping("/saveEmployee")
    public String saveEmployee(@Valid @ModelAttribute("employee") Employee employee,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        
        // Check for validation errors
        if (bindingResult.hasErrors()) {
            return "create-employee";
        }
        
        // Check if email already exists
        if (employeeService.existsByEmail(employee.getEmail())) {
            bindingResult.rejectValue("email", "error.employee", "Email already exists");
            return "create-employee";
        }
        
        employeeService.saveEmployee(employee);
        redirectAttributes.addFlashAttribute("successMessage", "Employee created successfully!");
        return "redirect:/";
    }
    
    // Show form for updating employee
    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "update-employee";
    }
    
    // Update employee
    @PostMapping("/updateEmployee")
    public String updateEmployee(@Valid @ModelAttribute("employee") Employee employee,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        
        // Check for validation errors
        if (bindingResult.hasErrors()) {
            return "update-employee";
        }
        
        // Check if email already exists for another employee
        if (employeeService.existsByEmailAndIdNot(employee.getEmail(), employee.getId())) {
            bindingResult.rejectValue("email", "error.employee", "Email already exists");
            return "update-employee";
        }
        
        employeeService.updateEmployee(employee);
        redirectAttributes.addFlashAttribute("successMessage", "Employee updated successfully!");
        return "redirect:/";
    }
    
    // Delete employee
    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable(value = "id") long id,
                               RedirectAttributes redirectAttributes) {
        employeeService.deleteEmployeeById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Employee deleted successfully!");
        return "redirect:/";
    }
}