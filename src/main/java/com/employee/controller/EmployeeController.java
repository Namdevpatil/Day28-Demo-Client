package com.employee.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.employee.model.Employee;
import com.employee.repository.EmployeeRepository;



@RestController
public class EmployeeController
{
	

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@PostMapping("/saveEmployee")
	public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee)
	{
		return new ResponseEntity<Employee>(employeeRepository.save(employee), HttpStatus.CREATED);
	}
	
	@GetMapping("/getEmployees")
	public List<Employee> getEmployees()
	{
		return employeeRepository.findAll();
	}
	
	@GetMapping("/getEmployee/{employeeId}")
	public ResponseEntity<Employee> getEmployee(@PathVariable("employeeId") Integer employeeId)
	{
		Optional<Employee> newEmployee = employeeRepository.findById(employeeId);		
		return new ResponseEntity<Employee>(newEmployee.get(), HttpStatus.OK);
	}
	
	@PutMapping("/updateEmployee/{employeeId}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable("employeeId") Integer employeeId, @RequestBody Employee employee)
	{
		Employee newEmployee = employeeRepository.findById(employeeId)
												 .orElseThrow(()-> new RuntimeException("Employee not found"));
		newEmployee.setName(employee.getName());
		newEmployee.setRole(employee.getRole());
		
		Employee updateEmployee = employeeRepository.save(newEmployee);
		
		return new ResponseEntity<Employee>(updateEmployee, HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteEmployee/{employeeId}")
	public ResponseEntity<String> deleteEmployee(@PathVariable("employeeId") Integer employeeId)
	{
		employeeRepository.deleteById(employeeId);
		
		return new ResponseEntity<String>("Employee deleted", HttpStatus.OK);
	}
	
	
}
