package com.vaadin.myvaadinwebapp.widgets;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.myvaadinwebapp.MyUI;
import com.vaadin.myvaadinwebapp.views.MainView;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class FilterBar extends CssLayout {

    private TextField filterText = new TextField();

    // https://vaadin.com/elements/vaadin-icons/html-examples/icons-basic-demos
    private Button clearFilterText = new Button(VaadinIcons.ERASER);

    public FilterBar() {
        //setStyleName(CLASSNAME);
        setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        filterText.setPlaceholder("Filter by name...");
        filterText.setValueChangeMode(ValueChangeMode.LAZY);

        clearFilterText.setDescription("Clear the current filter");
        clearFilterText.addClickListener(e -> filterText.clear());
        addComponents(filterText, clearFilterText);
    }

    public void addValueChangeListener(MainView myUI) {
        filterText.addValueChangeListener(e -> myUI.updateList(filterText
                .getValue()));
    }
}
