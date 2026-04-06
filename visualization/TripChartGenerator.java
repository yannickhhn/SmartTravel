//----------------------------------------------
//Assignment 1
//Package visualization
//Written by Hantaniaina Yannick H.N 40306516
//----------------------------------------------
package visualization;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import Travel.Trip;

/**
 * Utility class for generating charts from lists of Trip objects.
 * Supports Bar, Pie, and Line charts.
 */
public class TripChartGenerator {

	/**
	 * Applies consistent font styling to charts.
	 *
	 * @param chart the chart to style
	 */
	private static void applyChartStyling(JFreeChart chart) {

		Font titleFont = new Font("Bookman Old Style", Font.BOLD, 36);
        Font axisLabelFont = new Font("Bookman Old Style", Font.BOLD, 27);
        Font tickLabelFont = new Font("Bookman Old Style", Font.PLAIN, 18);
        Font legendFont = new Font("Bookman Old Style", Font.BOLD, 23);
        Font pieLabelFont = new Font("Bookman Old Style", Font.ITALIC, 23);
        
	    // Title
	    chart.getTitle().setFont(titleFont);

	    // Legend
	    if (chart.getLegend() != null) {
	        chart.getLegend().setItemFont(legendFont);
	    }

	    // Category & Line charts
	    if (chart.getPlot() instanceof CategoryPlot) {
	        CategoryPlot plot = (CategoryPlot) chart.getPlot();

	        CategoryAxis domainAxis = plot.getDomainAxis();
	        ValueAxis rangeAxis = plot.getRangeAxis();

	        domainAxis.setLabelFont(axisLabelFont);
	        domainAxis.setTickLabelFont(tickLabelFont);

	        rangeAxis.setLabelFont(axisLabelFont);
	        rangeAxis.setTickLabelFont(tickLabelFont);
	    }

	    // Pie charts
	    if (chart.getPlot() instanceof PiePlot) {
	    	@SuppressWarnings("unchecked")
	    	PiePlot<String> plot = (PiePlot<String>) chart.getPlot();

	    	plot.setLabelFont(pieLabelFont);

	        plot.setBackgroundPaint(Color.WHITE);
	        plot.setOutlineVisible(false);
	        plot.setSectionOutlinesVisible(false);
	        plot.setShadowPaint(null);
	        plot.setLabelBackgroundPaint(new Color(245, 245, 245, 180));
	    	
	    }
	}

	
	/**
     * Generates a Bar chart showing total cost per trip.
     *
     * @param trips list of Trip objects
     * @throws IOException if PNG file cannot be written
     */
    public static void generateCostBarChart(List<Trip> trips) throws IOException {
        
        if (trips == null || trips.isEmpty()) {
            System.out.println("No trips available for cost bar chart");
            return;
        }
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 0; i < trips.size(); i++) {
            if (trips.get(i) != null) {
                dataset.addValue(trips.get(i).calculateTotalCost(), "Total Cost", trips.get(i).getID());
            }
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Trip Costs",
                "Trip ID",
                "Cost ($)",
                dataset
        );
        
        applyChartStyling(chart);
        
        CategoryPlot plot = chart.getCategoryPlot();
        ValueAxis rangeAxis = plot.getRangeAxis();
        rangeAxis.setAutoRange(true);
        
        ChartUtils.saveChartAsPNG(new File("A3_249/output/charts/trip_cost_bar_chart.png"), chart, 800, 600);
    }

    /**
     * Generates a Pie chart showing distribution of trips per destination.
     *
     * @param trips list of Trip objects
     * @throws IOException if PNG file cannot be written
     */
    public static void generateDestinationPieChart(List<Trip> trips) throws IOException {
        
        if (trips == null || trips.isEmpty()) {
            System.out.println("No trips available for destination pie chart");
            return;
        }
        
    	DefaultPieDataset<String> dataset = new DefaultPieDataset<>();

        for (int i = 0; i < trips.size(); i++) {
            if (trips.get(i) != null) {
                String destination = trips.get(i).getDestination();
                if (dataset.getIndex(destination) != -1) {
                    double value = dataset.getValue(destination).doubleValue();
                    dataset.setValue(destination, value + 1);
                } else {
                    dataset.setValue(destination, 1);
                }
            }
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Trips per Destination",
                dataset,
                true,
                true,
                false
        );
        applyChartStyling(chart);
        ChartUtils.saveChartAsPNG(new File("A3_249/output/charts/trips_per_destination_pie.png"), chart, 800, 600);
    }

    /**
     * Generates a Line chart showing trip duration over Trip IDs.
     *
     * @param trips list of Trip objects
     * @throws IOException if PNG file cannot be written
     */
    public static void generateDurationLineChart(List<Trip> trips) throws IOException {
        
        if (trips == null || trips.isEmpty()) {
            System.out.println("No trips available for duration line chart");
            return;
        }
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 0; i < trips.size(); i++) {
            if (trips.get(i) != null) {
                dataset.addValue(trips.get(i).getDuration(), "Duration (days)", trips.get(i).getID());
            }
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Trip Duration",
                "Trip ID",
                "Duration (days)",
                dataset
        );
        applyChartStyling(chart);
        
        CategoryPlot plot = chart.getCategoryPlot();
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesStroke(0, new BasicStroke(3.0f));
        renderer.setSeriesPaint(0, Color.MAGENTA);
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesShapesFilled(0, true);
        
        ChartUtils.saveChartAsPNG(new File("A3_249/output/charts/trip_duration_line_chart.png"), chart, 800, 600);
    }
}
