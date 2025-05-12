package com.GamingHub.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.GamingHub.config.DbConfig;
import com.GamingHub.model.CustomerModel;
import com.GamingHub.util.PasswordUtil;

/**
 * Service class for handling login operations. Connects to the database,
 * verifies user credentials, and returns login status.
 */
public class LoginService {

	private Connection dbConn;
	private boolean isConnectionError = false;

	public LoginService() {
		try {
			dbConn = DbConfig.getDbConnection();
		} catch (SQLException ex) {
			ex.printStackTrace();
			isConnectionError = true;
		}
	}

	public Boolean loginUser(CustomerModel customerModel) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		String username = customerModel.getUsername();
		String password = customerModel.getPassword();


		
		String query = "SELECT username, password FROM customers WHERE username = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setString(1, username);
			ResultSet result = stmt.executeQuery();

			if (result.next()) {
				return validatePassword(result, customerModel);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return false;
	}

	private boolean validatePassword(ResultSet result, CustomerModel customerModel) throws SQLException {
		String dbUsername = result.getString("username");
		String dbPassword = result.getString("password");

		return dbUsername.equals(customerModel.getUsername())
				&& PasswordUtil.decrypt(dbPassword, dbUsername).equals(customerModel.getPassword());
	}
}