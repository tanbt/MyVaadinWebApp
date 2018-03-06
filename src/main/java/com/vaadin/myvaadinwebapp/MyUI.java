package com.vaadin.myvaadinwebapp;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.myvaadinwebapp.views.HomeView;
import com.vaadin.myvaadinwebapp.views.LoginView;
import com.vaadin.myvaadinwebapp.views.MainView;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.PushStateNavigation;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@PushStateNavigation
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        Button linkHomePage = new Button("Home",
                e -> getNavigator().navigateTo(HomeView.HOMEVIEW));
        linkHomePage .addStyleNames(ValoTheme.BUTTON_LINK, ValoTheme.MENU_ITEM);
        Button linkLoginPage =new Button("Login", e -> getNavigator().navigateTo
                (LoginView.LOGINVIEW));
        linkLoginPage.addStyleNames(ValoTheme.BUTTON_LINK, ValoTheme.MENU_ITEM);
        Button linkMainPage =new Button("Main", e -> getNavigator().navigateTo
                (MainView.MAINVIEW));
        linkMainPage.addStyleNames(ValoTheme.BUTTON_LINK, ValoTheme.MENU_ITEM);

        CssLayout menu =new CssLayout(linkHomePage, linkLoginPage,
                linkMainPage);
        menu.addStyleName(ValoTheme.MENU_ROOT);

        CssLayout viewContainer = new CssLayout();
        viewContainer.setSizeFull();

        VerticalLayout mainLayout = new VerticalLayout(menu, viewContainer);
        setContent(mainLayout);

        Navigator navigator = new Navigator(this, viewContainer);
        navigator.addView("", HomeView.class);
        navigator.addView(HomeView.HOMEVIEW, HomeView.class);
        navigator.addView(MainView.MAINVIEW, MainView.class);
        navigator.addView(LoginView.LOGINVIEW, LoginView.class);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
