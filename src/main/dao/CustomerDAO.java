package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import main.ConnectionSingleton;
import main.controller.GestoreEccezioni;
import main.model.Customer;

public class CustomerDAO {

	private final String QUERY_INSERT="insert into amebadevicesdb.customer(nome,cognome,datanascita,username,password) values(?,?,?,?,?)";
	private final String QUERY_READ="select * from amebadevicesdb.customer";
	private final String QUERY_SEARCH="select * from amebadevicesdb.customer where idcustomer=?";
	private final String QUERY_UPDATE="update amebadevicesdb.customer set nome=?,cognome=?,datanascita=?,username=?,password=? where idcustomer=?";
	private final String QUERY_DELETE="delete from amebadevicesdb.customer where idcustomer=?";
	
	public CustomerDAO() {}
	
	public boolean insertCustomer(Customer customer) {
		Connection connection=ConnectionSingleton.getInstance();
		
		
		try {
			PreparedStatement preparedStatement=connection.prepareStatement(QUERY_INSERT);
			preparedStatement.setString(1, customer.getNome());
			preparedStatement.setString(2, customer.getCognome());
			preparedStatement.setString(3,customer.getDataNascita());
			preparedStatement.setString(4, customer.getUsername());
			preparedStatement.setString(5,customer.getPassword());
			preparedStatement.execute();
			return true;
			
		} catch (SQLException e) {
			GestoreEccezioni.getInstance().gestisciEccezione(e);
			return false;
		}
	}
	
	public List<Customer> readAll(){
		Connection connection=ConnectionSingleton.getInstance();
		List<Customer> customers= new ArrayList<>();
		  try {
	           Statement statement = connection.createStatement();
	           ResultSet resultSet = statement.executeQuery(QUERY_READ);
	           while (resultSet.next()) {
	        	   int id= resultSet.getInt("idcustomer");
	               String nome = resultSet.getString("nome");
	               String cognome = resultSet.getString("cognome");
	               String datanascita=resultSet.getString("datanascita");  
	               String username= resultSet.getString("username");
	               String password= resultSet.getString("password");
	               Customer customer=new Customer(nome,cognome,datanascita,username,password);
	               customer.setId(id);
	               customers.add(customer);
	           
	           }
	           
	        }
		  
	        catch (SQLException e) {
	            e.printStackTrace();
	        }
		return customers;
		}
	
	public Customer searchCustomer(int id) {
		Connection connection=ConnectionSingleton.getInstance();
		Customer customer= null;
		try {
			PreparedStatement preparedStatement=connection.prepareStatement(QUERY_SEARCH);
			preparedStatement.setInt(1, id);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()) {
	               String nome = resultSet.getString("nome");
	               String cognome = resultSet.getString("cognome");
	               String datanascita=resultSet.getString("datanascita");  
	               String username= resultSet.getString("username");
	               String password= resultSet.getString("password");
	               customer=new Customer(nome,cognome,datanascita,username,password);
	               customer.setId(id);
			}
		} catch (SQLException e) {
			GestoreEccezioni.getInstance().gestisciEccezione(e);
		}
		return customer;
	}
	
	public void updateCustomer( Customer customer) {
		Connection connection=ConnectionSingleton.getInstance();
		try {
			PreparedStatement preparedStatement=connection.prepareStatement(QUERY_UPDATE);
			preparedStatement.setString(1, customer.getNome());
			preparedStatement.setString(2, customer.getCognome());
			preparedStatement.setString(3,customer.getDataNascita());
			preparedStatement.setString(4, customer.getUsername());
			preparedStatement.setString(5,customer.getPassword());
			preparedStatement.setInt(6, customer.getId());
			preparedStatement.execute();
			
			
		} catch (SQLException e) {
			GestoreEccezioni.getInstance().gestisciEccezione(e);
		
		}
	}
	
	public void deleteCustomer(int id) {
		Connection connection=ConnectionSingleton.getInstance();
		try {
			PreparedStatement preparedStatement=connection.prepareStatement(QUERY_DELETE);
			preparedStatement.setInt(1, id);
			preparedStatement.execute();
			
		} catch (SQLException e) {
			GestoreEccezioni.getInstance().gestisciEccezione(e);
		}
	}
	
	}
	
	

