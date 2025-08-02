package com.jose.authempleadosapi.service;

import com.jose.authempleadosapi.entity.Employee;
import com.jose.authempleadosapi.repository.EmployeeRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

  private final EmployeeRepository repository;

  public EmployeeService(EmployeeRepository repository) {
    this.repository = repository;
  }

  public List<Employee> findAll() {
    return repository.findAll();
  }

  public Employee findById(Long id) {
    return repository.findById(id).orElse(null);
  }

  public Employee save(Employee employee) {
    return repository.save(employee);
  }

  public void deleteById(Long id) {
    repository.deleteById(id);
  }

  public boolean existsByEmail(String email) {
    return repository.existsByEmail(email);
  }
}
