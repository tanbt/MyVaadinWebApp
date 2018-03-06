package com.vaadin.myvaadinwebapp.views;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class LoginView extends VerticalLayout implements View {

    public static final String LOGINVIEW = "login";

    public LoginView() {
        final TextField name = new TextField();
        name.setCaption("Type your name here:");

        Button button = new Button("Click Me");
        button.addClickListener(e -> {
            addComponent(
                    new Label("Thanks " + name.getValue() + ", it works!"));
        });

        addComponents(name, button);
    }
}
