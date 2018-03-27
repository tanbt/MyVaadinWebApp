package com.vaadin.myvaadinwebapp.views;

import com.vaadin.addon.charts.PointClickEvent;
import com.vaadin.addon.charts.PointClickListener;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.Credits;
import com.vaadin.addon.charts.model.Cursor;
import com.vaadin.addon.charts.model.DataLabels;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.PlotOptionsColumn;
import com.vaadin.addon.charts.model.PlotOptionsPie;
import com.vaadin.addon.charts.model.Title;
import com.vaadin.addon.charts.model.Tooltip;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.navigator.View;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

import com.vaadin.addon.charts.Chart;


/**
 * https://demo.vaadin.com/charts/#PieChart
 */
public class HomeView extends VerticalLayout implements View {

    public static final String HOMEVIEW = "home";

    public HomeView() {
        setMargin(true);

        Chart chart1 = getPieChart();
        Chart chart2 = getColumnChart();
        addComponents(chart1, chart2);
    }

    Chart getColumnChart() {
        Chart chart = new Chart(ChartType.COLUMN);
        chart.setWidth("500px");

        Configuration conf = chart.getConfiguration();
        conf.setTitle(new Title("Column chart with negative values"));

        PlotOptionsColumn column = new PlotOptionsColumn();
        column.setMinPointLength(3);
        conf.setPlotOptions(column);

        XAxis xAxis = new XAxis();
        xAxis.setCategories("Apples", "Oranges", "Pears", "Grapes", "Bananas");
        conf.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.getLabels().getStyle().setFontSize("8px");
        yAxis.setTitle("Y Title");
        yAxis.getTitle().getStyle().setFontSize("28px");
        conf.addyAxis(yAxis);

        Tooltip tooltip = new Tooltip();
        tooltip.setFormatter("function() { return ''+ this.series.name +': '+ this.y +'';}");
        conf.setTooltip(tooltip);

        conf.setCredits(new Credits(false));

        conf.addSeries(new ListSeries("John", 5, 0.1, 4, 7, 2));
        conf.addSeries(new ListSeries("Jane", 2, -2, -0.1, 2, 1));
        conf.addSeries(new ListSeries("Joe", 3, 4, 4, -2, 5));

        chart.drawChart(conf);
        return chart;
    }

    Chart getPieChart() {
        Chart chart = new Chart(ChartType.PIE);

        Configuration conf = chart.getConfiguration();

        Tooltip tooltip = new Tooltip();
        tooltip.setValueDecimals(1);
        tooltip.setPointFormat("{series.name}: <b>{point.percentage}%</b>");
        conf.setTooltip(tooltip);

        conf.setTitle("Browser market shares at a specific website, 2010");
        PlotOptionsPie plotOptions = new PlotOptionsPie();
        plotOptions.setAllowPointSelect(true);
        plotOptions.setCursor(Cursor.POINTER);
        plotOptions.setShowInLegend(true);

        DataLabels dataLabels = new DataLabels();
        dataLabels.setEnabled(false);
        plotOptions.setDataLabels(dataLabels);

        conf.setPlotOptions(plotOptions);

        final DataSeries series = new DataSeries();
        series.add(new DataSeriesItem("Firefox", 45.0));
        series.add(new DataSeriesItem("IE", 26.8));
        DataSeriesItem chrome = new DataSeriesItem("Chrome", 12.8);
        series.add(chrome);
        series.add(new DataSeriesItem("Safari", 8.5));
        series.add(new DataSeriesItem("Opera", 6.2));
        series.add(new DataSeriesItem("Others", 0.7));
        conf.setSeries(series);

        chart.addPointClickListener(event -> Notification
                .show("Click: " + series.get(event.getPointIndex()).getName()));
        chart.addLegendItemClickListener(event -> {
            DataSeriesItem item = ((DataSeries) event.getSeries()).get(event.getSeriesItemIndex());
            Notification.show(String.format("%s: %.2f%%", item.getName(), item.getY()));
        });
        chart.drawChart(conf);
        return chart;
    }

}
