package com.GamingHub.model;

import java.time.LocalDate;

public class CustomerModel {
    private int customer_id;
    private String first_name;
    private String last_name;
    private String username;
    private LocalDate dob;
    private String gender;
    private String email;
    private String number;
    private String password;
    private String imageURL;

    public CustomerModel(String username, String password) {
		this.username = username;
		this.password = password;
	}

    public CustomerModel(int customer_id, String first_name, String last_name, String username, 
                        LocalDate dob, String gender, String email, String number, 
                        String password, String imageURL) {
        this.customer_id = customer_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.dob = dob;
        this.gender = gender;
        this.email = email;
        this.number = number;
        this.password = password;
        this.imageURL = imageURL;
    }

    // Getters and Setters
    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

}

	
