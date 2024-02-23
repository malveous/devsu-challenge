package com.devsu.app.customer.test.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devsu.app.customer.exceptions.CustomerNotFoundException;
import com.devsu.app.customer.exceptions.CustomerRequiredDataException;
import com.devsu.app.customer.model.Customer;
import com.devsu.app.customer.persistence.CustomerRepository;
import com.devsu.app.customer.service.impl.CustomerServiceImpl;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

	@Mock
	private CustomerRepository customerRepository;
	
	@InjectMocks
	private CustomerServiceImpl customerService;
	
	@Test
	void list_all_customers_when_success() {
		var anotherCustomer = Customer.builder()
				.id(1)
				.name("Charlie")
				.address("House next to redhead girl")
				.phoneNumber("98789981")
				.password("SnoopyBF")
				.status(Boolean.TRUE)
				.build();
		
		Mockito.when(customerRepository.findAll()).thenReturn(List.of(this.buildCustomerDataCompleted(), anotherCustomer));
		
		var customerList = this.customerService.listAllCustomers();
		
		Assertions.assertThat(customerList).isNotNull().isNotEmpty();
	}
	
	@Test
	void list_all_customers_when_no_data_available_and_null() {
		Mockito.when(customerRepository.findAll()).thenReturn(null);
		CustomerNotFoundException thrown = assertThrows(CustomerNotFoundException.class, () -> this.customerService.listAllCustomers());
		
		Assertions.assertThat(thrown).isNotNull();
		Assertions.assertThat(thrown.getMessage()).isNotNull();
	}
	
	@Test
	void list_all_customers_when_no_data_available_and_empty() {
		Mockito.when(customerRepository.findAll()).thenReturn(new ArrayList<>());
		CustomerNotFoundException thrown = assertThrows(CustomerNotFoundException.class, () -> this.customerService.listAllCustomers());
		
		Assertions.assertThat(thrown).isNotNull();
		Assertions.assertThat(thrown.getMessage()).isNotNull();
	}
	
	@Test
	void find_customer_by_id_when_success() {
		var testCustomer = this.buildCustomerDataCompleted(); 
		Mockito.when(customerRepository.findById(testCustomer.getId())).thenReturn(Optional.of(testCustomer));
		
		var customerFound = this.customerService.findCustomerById(testCustomer.getId());
		
		Assertions.assertThat(customerFound).isNotNull();
		Assertions.assertThat(customerFound.getId()).isEqualTo(testCustomer.getId());
	}
	
	@Test
	void find_customer_by_id_when_no_entry_found() {
		Mockito.when(customerRepository.findById(100)).thenReturn(Optional.empty());
		CustomerNotFoundException thrown = assertThrows(CustomerNotFoundException.class, () -> this.customerService.findCustomerById(100));
		
		Assertions.assertThat(thrown).isNotNull();
		Assertions.assertThat(thrown.getMessage()).isNotNull();
	}
	
	@Test
	void find_customer_by_personal_id_when_success() {
		var testCustomer = this.buildCustomerDataCompleted(); 
		Mockito.when(customerRepository.findByPersonalId(testCustomer.getPersonalId())).thenReturn(Optional.of(testCustomer));
		
		var customerFound = this.customerService.findCustomerByPersonalId(testCustomer.getPersonalId());
		
		Assertions.assertThat(customerFound).isNotNull();
		Assertions.assertThat(customerFound.getPersonalId()).isEqualTo(testCustomer.getPersonalId());
	}
	
	@Test
	void find_customer_by_personal_id_when_no_entry_found() {
		Mockito.when(customerRepository.findByPersonalId("100")).thenReturn(Optional.empty());
		CustomerNotFoundException thrown = assertThrows(CustomerNotFoundException.class, () -> this.customerService.findCustomerByPersonalId("100"));
		
		Assertions.assertThat(thrown).isNotNull();
		Assertions.assertThat(thrown.getMessage()).isNotNull();
	}
	
	@Test
	void find_customer_by_customer_id_when_success() {
		var testCustomer = this.buildCustomerDataCompleted(); 
		Mockito.when(customerRepository.findByCustomerId(testCustomer.getCustomerId())).thenReturn(Optional.of(testCustomer));
		
		var customerFound = this.customerService.findCustomerByCustomerId(testCustomer.getCustomerId());
		
		Assertions.assertThat(customerFound).isNotNull();
		Assertions.assertThat(customerFound.getCustomerId()).isEqualTo(testCustomer.getCustomerId());
	}
	
	@Test
	void find_customer_by_customer_id_when_no_entry_found() {
		Mockito.when(customerRepository.findByCustomerId("1111-2222-3333-4444")).thenReturn(Optional.empty());
		CustomerNotFoundException thrown = assertThrows(CustomerNotFoundException.class, () -> this.customerService.findCustomerByCustomerId("1111-2222-3333-4444"));
		
		Assertions.assertThat(thrown).isNotNull();
		Assertions.assertThat(thrown.getMessage()).isNotNull();
	}
	
	@Test
	void register_customer_when_success() {
		var testCustomer = this.buildCustomerDataCompleted(); 
		Mockito.when(customerRepository.save(testCustomer)).thenReturn(testCustomer);
		
		var newCustomerCode = this.customerService.register(testCustomer);
		
		Assertions.assertThat(newCustomerCode).isPositive();
	}
	
	@Test
	void register_customer_when_age_is_not_valid() {
		var testCustomer = this.buildCustomerDataCompleted(); 
		testCustomer.setAge(10);
		
		CustomerRequiredDataException thrown = assertThrows(CustomerRequiredDataException.class, () -> this.customerService.register(testCustomer));
		
		Assertions.assertThat(thrown).isNotNull();
		Assertions.assertThat(thrown.getMessage()).isNotNull();
	}
	
	@Test
	void register_customer_when_personal_id_is_not_present() {
		var testCustomer = this.buildCustomerDataCompleted(); 
		testCustomer.setPersonalId(null);
		
		CustomerRequiredDataException thrown = assertThrows(CustomerRequiredDataException.class, () -> this.customerService.register(testCustomer));
		
		Assertions.assertThat(thrown).isNotNull();
		Assertions.assertThat(thrown.getMessage()).isNotNull();
	}
	
	@Test
	void register_customer_when_name_is_not_present() {
		var testCustomer = this.buildCustomerDataCompleted(); 
		testCustomer.setName("");
		
		CustomerRequiredDataException thrown = assertThrows(CustomerRequiredDataException.class, () -> this.customerService.register(testCustomer));
		
		Assertions.assertThat(thrown).isNotNull();
		Assertions.assertThat(thrown.getMessage()).isNotNull();
	}
	
	@Test
	void register_customer_when_password_is_not_present() {
		var testCustomer = this.buildCustomerDataCompleted(); 
		testCustomer.setPassword(null);
		
		CustomerRequiredDataException thrown = assertThrows(CustomerRequiredDataException.class, () -> this.customerService.register(testCustomer));
		
		Assertions.assertThat(thrown).isNotNull();
		Assertions.assertThat(thrown.getMessage()).isNotNull();
	}
	
	@Test
	void register_customer_when_address_is_not_present() {
		var testCustomer = this.buildCustomerDataCompleted(); 
		testCustomer.setAddress("");
		
		CustomerRequiredDataException thrown = assertThrows(CustomerRequiredDataException.class, () -> this.customerService.register(testCustomer));
		
		Assertions.assertThat(thrown).isNotNull();
		Assertions.assertThat(thrown.getMessage()).isNotNull();
	}
	
	@Test
	void register_customer_when_phone_is_not_present() {
		var testCustomer = this.buildCustomerDataCompleted(); 
		testCustomer.setPhoneNumber("");
		
		CustomerRequiredDataException thrown = assertThrows(CustomerRequiredDataException.class, () -> this.customerService.register(testCustomer));
		
		Assertions.assertThat(thrown).isNotNull();
		Assertions.assertThat(thrown.getMessage()).isNotNull();
	}
	
	@Test
	void register_customer_when_gender_is_not_present() {
		var testCustomer = this.buildCustomerDataCompleted(); 
		testCustomer.setGender(null);
		
		CustomerRequiredDataException thrown = assertThrows(CustomerRequiredDataException.class, () -> this.customerService.register(testCustomer));
		
		Assertions.assertThat(thrown).isNotNull();
		Assertions.assertThat(thrown.getMessage()).isNotNull();
	}
	
	@Test
	void update_customer_when_success() {
		var testCustomer = this.buildCustomerDataCompleted();
		Mockito.when(this.customerRepository.findCustomerBySpecificIdsIfExist(testCustomer.getCustomerId(), testCustomer.getPersonalId())).thenReturn(Optional.of(testCustomer));
		Mockito.when(customerRepository.save(testCustomer)).thenReturn(testCustomer);
		
		var newCustomerCode = this.customerService.update(testCustomer);
		
		Assertions.assertThat(newCustomerCode).isPositive();
	}
	
	private Customer buildCustomerDataCompleted() {
		return Customer.builder()
				.id(1)
				.name("Snoopy")
				.gender("Male")
				.age(18)
				.personalId("12345678")
				.customerId("0000-1111-2222-3333-4444")
				.address("House outside backyard")
				.phoneNumber("98978172")
				.password("CharlieBF")
				.status(Boolean.TRUE)
				.build();
	}
	
}
