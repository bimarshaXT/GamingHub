package com.GamingHub.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.GamingHub.dao.CustomerDAO;
import com.GamingHub.model.CustomerModel;

public class AddCustomerService {
    private static final Logger LOGGER = Logger.getLogger(AddCustomerService.class.getName());

    private final CustomerDAO customerDAO = new CustomerDAO();

    public Boolean addCustomer(CustomerModel customerModel) {
        try {
            if (customerDAO.exists(customerModel.getUsername(), customerModel.getEmail(), customerModel.getNumber())) {
                LOGGER.log(Level.WARNING, "Attempt to add duplicate customer: {0}", customerModel.getUsername());
                return false;
            }

            boolean inserted = customerDAO.insert(customerModel);

            if (inserted) {
                LOGGER.log(Level.INFO, "Successfully added customer: {0}", customerModel.getUsername());
                return true;
            } else {
                LOGGER.log(Level.WARNING, "Failed to insert customer: {0}", customerModel.getUsername());
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error while adding customer: " + e.getMessage(), e);
            return null;
        }
    }
}
