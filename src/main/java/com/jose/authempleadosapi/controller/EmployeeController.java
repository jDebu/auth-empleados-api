package com.jose.authempleadosapi.controller;

import com.jose.authempleadosapi.entity.Employee;
import com.jose.authempleadosapi.service.EmployeeService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

  private final EmployeeService service;

  public EmployeeController(EmployeeService service) {
    this.service = service;
  }

  @GetMapping
  public List<Employee> listAll() {
    return service.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Employee> getById(@PathVariable Long id) {
    Employee employee = service.findById(id);
    return employee != null ? ResponseEntity.ok(employee) : ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<?> create(@Valid @RequestBody Employee employee) {
    if (service.existsByEmail(employee.getEmail())) {
      return ResponseEntity.badRequest().body("Ya existe un empleado con ese email");
    }
    return ResponseEntity.ok(service.save(employee));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Employee employee) {
    Employee existing = service.findById(id);
    if (existing == null) return ResponseEntity.notFound().build();

    existing.setName(employee.getName());
    existing.setEmail(employee.getEmail());
    existing.setRole(employee.getRole());
    return ResponseEntity.ok(service.save(existing));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    if (service.findById(id) == null) return ResponseEntity.notFound().build();

    service.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
