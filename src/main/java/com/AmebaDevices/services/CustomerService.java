package com.AmebaDevices.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AmebaDevices.converter.CustomerConverter;
import com.AmebaDevices.dao.CustomerDAO;
import com.AmebaDevices.dto.CustomerDTO;
import com.AmebaDevices.model.Customer;

@Service
public class CustomerService {

	private CustomerDAO customerDAO;

	@Autowired
	public CustomerService(CustomerDAO customerDAO) {
		this.customerDAO = customerDAO;
	}

	public long login(String username, String password) {
		Customer c = customerDAO.findByUsernameAndPassword(username, password);
		if (c != null) {
			if (c.getId() != 0)
				return c.getId();
		}
		return -1;
	}

	public void insertCustomer(CustomerDTO customer) {
		System.out.println(customer.getId() + customer.getNome() + customer.getCognome() + customer.getDataNascita()
				+ customer.getEmail() + customer.getUsername() + customer.getPassword() + customer.getUser_role());
		customerDAO.save(CustomerConverter.convertToCustomer(customer));
	}

	public List<CustomerDTO> readAll() {
		List<CustomerDTO> customers = new ArrayList<>();
		customerDAO.findAll().forEach(c -> {
			customers.add(CustomerConverter.convertToDto(c));
			System.out.println(c.getNome());
		});
		return customers;
	}

	public void updateCustomer(CustomerDTO customer) {
		customerDAO.save(CustomerConverter.convertToCustomer(customer));
	}

	public CustomerDTO searchCustomer(Long id) {
		return CustomerConverter.convertToDto(customerDAO.findOne(id));
	}

	public void deleteCustomer(Long id) {
		Customer c = customerDAO.findOne(id);
		customerDAO.delete(c);
	}
	
	public CustomerDTO findByUsername(String username) {
		return CustomerConverter.convertToDto(customerDAO.findByUsername(username));
	}
}
