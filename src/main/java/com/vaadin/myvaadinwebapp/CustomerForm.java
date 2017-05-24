package com.vaadin.myvaadinwebapp;

public class CustomerForm extends CustomerFormDesign {
	private CustomerService service = CustomerService.getInstance();
	private Customer customer;
	private MyUI myUI;

	public CustomerForm(MyUI myUI) {
	    this.myUI = myUI;
	    status.setItems(CustomerStatus.values());

	}
}
