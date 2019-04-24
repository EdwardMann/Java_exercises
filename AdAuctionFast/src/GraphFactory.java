import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Tooltip;

public class GraphFactory {

    /**
     * Returns a fully set up LineChart object
     *
     * @param charttitle
     * @param xaxislabel
     * @param yaxislabel
     * @param graphdata
     * @param timestep
     * @return
     */
    @SuppressWarnings("unchecked") // to stop eclipse highlighting the second last line :p
    public static LineChart<String, Number> getLineChart(Metric metric, Map<String, Double> graphdata) {

        ObservableList<String> answer = FXCollections.observableArrayList();
        if (graphdata != null) {
            for (String d : graphdata.keySet()) {
                if (!answer.contains(d.toString())) {
                    answer.add(d.toString());
                }
            }
        }
        
        String yaxislabel = "";
        
        switch (metric){    //intentional fallthrough
        case CPA:
        case CPC:
        case CPM:
        case CTR:
        case TotalCost:
            yaxislabel = metric.toString() + "/£";
            break;
        default:
            yaxislabel = metric.toString();
        }
        
        CategoryAxis xAxis = new CategoryAxis(answer);
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time");
        yAxis.setLabel(yaxislabel);
        xAxis.setTickLabelRotation(270);

        LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);
        lineChart.setTitle(metric.toString());

        XYChart.Series<String, Number> series1 = new Series<String, Number>();
        series1.setName("Campaign 1");
        if (graphdata != null) {
            addData(series1, graphdata);
        }
        lineChart.setAnimated(true);
        lineChart.getData().addAll(series1);
        displayOnHover(lineChart);
        lineChart.getStylesheets().add("Black.css");

        return lineChart;
    }

    /**
     * Adds data to an XYChart series passed to it which can then be added to the Chart
     *
     * @param series
     *            XYChart series that takes (String, Number) per datapoint
     * @param graphdata
     *            A mapping from date (as string) to value (Double)
     */
    public static void addData(XYChart.Series<String, Number> series, Map<String, Double> graphdata) {
        for (Entry<String, Double> entry : graphdata.entrySet()) {
            String key = entry.getKey().toString();
            Double value = entry.getValue();

            series.getData().add(new XYChart.Data<String, Number>(key, value));
        }
    }

    // TODO: fix this mess
    public static void setData(XYChart.Series<String, Number> series, Map<Date, Double> data) {
        for (Map.Entry<Date, Double> entry : data.entrySet()) {
            String key = entry.getKey().toString();
            Double value = entry.getValue();

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    series.setData(FXCollections.observableArrayList(new XYChart.Data<String, Number>(key, value)));
                }
            });
        }
    }
    private static void displayOnHover(LineChart<String, Number> lineChart) { 
    	 
        for (XYChart.Series<String, Number> s : lineChart.getData()) { 
     
          for (final XYChart.Data<String, Number> d : s.getData()) { 
     
            Tooltip.install(d.getNode(), new Tooltip( 
                d.getXValue().toString() + "\n" + 
                    "Number Of Events : " + d.getYValue())); 
     
            //Adding class on hover 
            d.getNode().setOnMouseEntered(new EventHandler<javafx.event.Event>() { 
     
              public void handle(javafx.event.Event event) { 
                d.getNode().getStyleClass().add("onHover"); 
              } 
     
            }); 
     
            //Removing class on exit 
            d.getNode().setOnMouseExited(new EventHandler<javafx.event.Event>() { 
     
              public void handle(javafx.event.Event event) { 
                d.getNode().getStyleClass().remove("onHover"); 
              } 
     
            }); 
     
          } 
     
        } 
     
      } 
}
