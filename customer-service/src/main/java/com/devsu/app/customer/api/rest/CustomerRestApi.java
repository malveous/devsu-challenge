package com.devsu.app.customer.api.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.app.customer.model.Customer;
import com.devsu.app.customer.service.CustomerService;

@RestController
@RequestMapping("/api/customer")
public class CustomerRestApi {

	@Autowired
	private CustomerService customerService;
	
	@GetMapping
	public List<Customer> listAllCustomers() {
		return this.customerService.listAllCustomers();
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<Customer> findCustomerById(@PathVariable(value = "id") int id) {
		return ResponseEntity.ok(this.customerService.findCustomerById(id));
	}
	
	@GetMapping("/personalId/{personalId}")
	public ResponseEntity<Customer> findCustomerByPersonalId(@PathVariable(value = "personalId") String personalId) {
		return ResponseEntity.ok(this.customerService.findCustomerByPersonalId(personalId));
	}
	
	@GetMapping("/customerId/{customerId}")
	public ResponseEntity<Customer> findCustomerByCustomerId(@PathVariable(value = "customerId") String customerId) {
		return ResponseEntity.ok(this.customerService.findCustomerByCustomerId(customerId));
	}
	
	@PostMapping("/newCustomer")
	public ResponseEntity<Integer> register(@RequestBody Customer newCustomer) {
		return ResponseEntity.ok(this.customerService.register(newCustomer));
	}
	
	@PatchMapping
	public ResponseEntity<Integer> update(@RequestBody Customer existingCustomer) {
		return ResponseEntity.ok(this.customerService.update(existingCustomer));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") int id) {
		this.customerService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
