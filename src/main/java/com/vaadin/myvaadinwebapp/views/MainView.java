package com.vaadin.myvaadinwebapp.views;

import java.util.List;

import com.vaadin.myvaadinwebapp.Customer;
import com.vaadin.myvaadinwebapp.CustomerForm;
import com.vaadin.myvaadinwebapp.CustomerService;
import com.vaadin.myvaadinwebapp.widgets.FilterBar;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class MainView extends VerticalLayout implements View {
    public static final String MAINVIEW = "main";

    private CustomerService service = CustomerService.getInstance();
    private Grid<Customer> grid = new Grid<>(Customer.class);
    private FilterBar filterBar = new FilterBar();
    private CustomerForm form = new CustomerForm(this);

    public MainView() {
        setSizeFull();
        setStyleName("my-theme-body");

        filterBar.addValueChangeListener(this);

        Button addCustomerBtn = new Button("Add new customer");
        addCustomerBtn.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setCustomer(new Customer());
        });

        HorizontalLayout toolbar = new HorizontalLayout(filterBar,
                addCustomerBtn);

        grid.setColumns("firstName", "lastName", "email");
        updateList();
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() == null) {
                form.setVisible(false);
            } else {
                form.setCustomer(event.getValue());
            }
        });

        form.setVisible(false);
        HorizontalLayout main = new HorizontalLayout(grid, form);
        main.setSizeFull();
        grid.setSizeFull();
        form.setSizeFull();
        //main.setExpandRatio(grid, 1);		//This line will hide the form (the second element)

        addComponents(toolbar, main);
    }

    public void updateList(String keyword) {
        List<Customer> customers = service.findAll(keyword);
        grid.setItems(customers);
    }

    public void updateList() {
        List<Customer> customers = service.findAll();
        grid.setItems(customers);
    }
}
