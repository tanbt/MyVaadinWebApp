package com.vaadin.myvaadinwebapp.views;

import com.vaadin.navigator.View;
import com.vaadin.ui.Composite;
import com.vaadin.ui.Label;

public class HomeView extends Composite implements View {

    public static final String HOMEVIEW = "home";

    public HomeView() {
        setCompositionRoot(new Label("The home page"));
    }

}
