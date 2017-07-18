package com.vaadin.myvaadinwebapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.TreeData;
import com.vaadin.data.provider.DataProviderListener;
import com.vaadin.data.provider.HierarchicalDataProvider;
import com.vaadin.data.provider.HierarchicalQuery;
import com.vaadin.data.provider.TreeDataProvider;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.Registration;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.TreeGrid;
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
public class MyUI extends UI {

    private CustomerService service = CustomerService.getInstance();
    private Grid<Customer> grid = new Grid<>(Customer.class);
    private TextField filterText = new TextField();
    private CustomerForm form = new CustomerForm(this);
    Random random = new Random();
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setStyleName("my-theme-body");
        
        /*final TextField name = new TextField();
        name.setCaption("Type your name here:");

        Button button = new Button("Click Me");
        button.addClickListener( e -> {
            layout.addComponent(new Label("Thanks " + name.getValue() 
                    + ", it works!"));
        });
        
        layout.addComponents(name, button);*/
        
        
        /*filterText.setPlaceholder("Filter by name...");
        filterText.addValueChangeListener(e -> updateList());
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        Button clearFilterTextBtn = new Button(FontAwesome.TIMES);
        clearFilterTextBtn.setDescription("Clear the current filter");
        clearFilterTextBtn.addClickListener(e -> filterText.clear());
        
        Button addCustomerBtn = new Button("Add new customer");
        addCustomerBtn.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setCustomer(new Customer());
        });
        
        CssLayout filtering = new CssLayout();
        filtering.addComponents(filterText, clearFilterTextBtn);
        filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        
        HorizontalLayout toolbar = new HorizontalLayout(filtering, addCustomerBtn);
        
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

        layout.addComponents(toolbar, main);
        */

        /*
        // An initial planet tree
        Tree<String> tree = new Tree<>();
        TreeData<String> treeData = new TreeData<>();

        // Couple of childless root items
        treeData.addItem(null,"Mercury");
        treeData.addItem(null,"Venus");

        // Items with hierarchy
        treeData.addItem(null,"Earth");
        treeData.addItem("Earth","The Moon");
        treeData.addItem("Earth","The Stars");

        TreeDataProvider inMemoryDataProvider = new TreeDataProvider<>
                (treeData);
        tree.setDataProvider(inMemoryDataProvider);
        tree.expand("Earth"); // Expand programmatically
        layout.addComponent(tree);
        */

        // Tree Grid
        TreeGrid<Project> treeGrid = new TreeGrid<>();
        treeGrid.setItems(generateProjectsForYears(2010, 2016), Project::getSubProjects);

        treeGrid.addColumn(Project::getName).setCaption("Project Name").setId("name-column");
        treeGrid.addColumn(Project::getHoursDone).setCaption("Hours Done");
        treeGrid.addColumn(Project::getLastModified).setCaption("Last Modified");

        treeGrid.addCollapseListener(event -> {
            Notification.show(
                    "Project '" + event.getCollapsedItem().getName() + "' collapsed.",
                    Notification.Type.TRAY_NOTIFICATION);
        });
        treeGrid.addExpandListener(event -> {
            Notification.show(
                    "Project '" + event.getExpandedItem().getName()+ "' expanded.",
                    Notification.Type.TRAY_NOTIFICATION);
        });
        layout.addComponent(treeGrid);

        setContent(layout);
    }

    private List<Project> generateProjectsForYears(int startYear, int endYear) {
        List<Project> projects = new ArrayList<>();

        for (int year = startYear; year <= endYear; year++) {
            Project yearProject = new Project("Year " + year);

            for (int i = 1; i < 2 + random.nextInt(5); i++) {
                Project customerProject = new Project("Customer Project " + i);
                customerProject.setSubProjects(Arrays.asList(
                        new LeafProject("Implementation", random.nextInt(100), year),
                        new LeafProject("Planning", random.nextInt(10), year),
                        new LeafProject("Prototyping", random.nextInt(20), year)));
                yearProject.addSubProject(customerProject);
            }
            projects.add(yearProject);
        }
        return projects;
    }

    public void updateList() {
        
        List<Customer> customers = service.findAll(filterText.getValue());
        grid.setItems(customers);
	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}

class LeafProject extends Project {

    private int hoursDone;
    private Date lastModified;
    Random random = new Random();

    public LeafProject(String name, int hoursDone, int year) {
        super(name);
        this.hoursDone = hoursDone;
        lastModified = new Date(year - 1900, random.nextInt(12),
                random.nextInt(10));
    }

    @Override
    public int getHoursDone() {
        return hoursDone;
    }

    @Override
    public Date getLastModified() {
        return lastModified;
    }
}

class Project {

    private List<Project> subProjects = new ArrayList<>();
    private String name;

    public Project(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Project> getSubProjects() {
        return subProjects;
    }

    public void setSubProjects(List<Project> subProjects) {
        this.subProjects = subProjects;
    }

    public void addSubProject(Project subProject) {
        subProjects.add(subProject);
    }

    public int getHoursDone() {
        return getSubProjects().stream()
                .map(project -> project.getHoursDone())
                .reduce(0, Integer::sum);
    }

    public Date getLastModified() {
        return getSubProjects().stream()
                .map(project -> project.getLastModified())
                .max(Date::compareTo).orElse(null);
    }
}