package com.vaadin.myvaadinwebapp.views;

import com.vaadin.navigator.View;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

public class MainView extends VerticalLayout implements View {
    public static final String MAINVIEW = "main";

    public MainView() {
        setSizeFull();

        Button button = new Button("Go to Login View");
        addComponent(button);
        setComponentAlignment(button, Alignment.MIDDLE_CENTER);
    }
}
