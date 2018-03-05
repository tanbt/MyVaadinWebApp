package com.vaadin.myvaadinwebapp.widgets;

import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.TextField;

public class FilterText extends TextField {

    public static final String CLASSNAME = "filter-text";

    public FilterText() {
        setStyleName(CLASSNAME);
        setPlaceholder("Filter by name...");
        setValueChangeMode(ValueChangeMode.LAZY);
    }
}
