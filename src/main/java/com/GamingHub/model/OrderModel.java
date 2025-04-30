package com.GamingHub.model;

import java.sql.Date;

public class OrderModel {
	private int order_id;
    private Date order_date;
    private Float total_amount;
    private CustomerModel customer;
    
	public OrderModel(int order_id, Date order_date, Float total_amount, CustomerModel customer) {
		super();
		this.order_id = order_id;
		this.order_date = order_date;
		this.total_amount = total_amount;
		this.customer = customer;
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public Date getOrder_date() {
		return order_date;
	}

	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}

	public Float getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(Float total_amount) {
		this.total_amount = total_amount;
	}

	public CustomerModel getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerModel customer) {
		this.customer = customer;
	}
    
    
}
