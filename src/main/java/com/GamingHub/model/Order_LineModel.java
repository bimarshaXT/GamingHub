package com.GamingHub.model;

public class Order_LineModel {
	private int order_line_id;
    private OrderModel order_id;
    private ProductModel product_id;
    private int quantity;
    
	public Order_LineModel(int order_line_id, OrderModel order_id, ProductModel product_id, int quantity) {
		super();
		this.order_line_id = order_line_id;
		this.order_id = order_id;
		this.product_id = product_id;
		this.quantity = quantity;
	}

	public int getOrder_line_id() {
		return order_line_id;
	}

	public void setOrder_line_id(int order_line_id) {
		this.order_line_id = order_line_id;
	}

	public OrderModel getOrder_id() {
		return order_id;
	}

	public void setOrder_id(OrderModel order_id) {
		this.order_id = order_id;
	}

	public ProductModel getProduct_id() {
		return product_id;
	}

	public void setProduct_id(ProductModel product_id) {
		this.product_id = product_id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
    
	
}
