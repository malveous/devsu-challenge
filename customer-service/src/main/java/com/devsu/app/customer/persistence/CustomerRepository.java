package com.devsu.app.customer.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.devsu.app.customer.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	Optional<Customer> findByPersonalId(String personalId);
	
	@Query("SELECT c FROM Customer c WHERE c.customerId =?1 or c.personalId = ?2")
	Optional<Customer> findCustomerBySpecificIdsIfExist(String customerId, String personalId);
	
	Optional<Customer> findByCustomerId(String customerId);
}
