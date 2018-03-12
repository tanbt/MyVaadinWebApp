package com.vaadin.myvaadinwebapp.views;

import com.vaadin.addon.charts.PointClickEvent;
import com.vaadin.addon.charts.PointClickListener;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.Cursor;
import com.vaadin.addon.charts.model.DataLabels;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.PlotOptionsPie;
import com.vaadin.addon.charts.model.Tooltip;
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

        addPieChart();
    }

    void addPieChart() {
        Chart chart = new Chart(ChartType.PIE);
        addComponent(chart);

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
    }

}
