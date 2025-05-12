package com.GamingHub.service;

import com.GamingHub.config.DbConfig;
import com.GamingHub.dao.CustomerDAO;
import com.GamingHub.model.CustomerModel;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserManagementService {

	public List<CustomerModel> getAllCustomers() {
		List<CustomerModel> customers = new ArrayList<>();

		String query = "SELECT * FROM customers";

		try (Connection conn = DbConfig.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(query);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				CustomerModel customer = new CustomerModel(rs.getInt("customer_id"), rs.getString("first_name"),
						rs.getString("last_name"), rs.getString("username"), rs.getDate("dob").toLocalDate(),
						rs.getString("gender"), rs.getString("email"), rs.getString("number"), rs.getString("password"), // not
																															// shown
																															// in
																															// JSP
						rs.getString("imageURL"));
				customers.add(customer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return customers;
	}

	
}
