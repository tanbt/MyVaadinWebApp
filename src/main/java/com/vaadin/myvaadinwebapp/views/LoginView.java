package com.vaadin.myvaadinwebapp.views;

import com.vaadin.navigator.View;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

public class LoginView extends VerticalLayout implements View {

    public static final String LOGINVIEW = "login";

    public LoginView() {
        setSizeFull();

        Button button = new Button("Go to Main View");
        addComponent(button);
        setComponentAlignment(button, Alignment.MIDDLE_CENTER);
    }
}
