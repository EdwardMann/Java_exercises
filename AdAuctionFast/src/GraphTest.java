import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.Chart;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Displays the graph in a very simple window to visually check if it is working correctly with dummy data
 */
public class GraphTest extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Test");

        BorderPane root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(
            Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

     // use linked hashmap to preserve insert ordering
        Map<String, Double> graphdata = new LinkedHashMap<String, Double>();
        graphdata.put("2015-06-12", 1.0);
        graphdata.put("2015-08-12", 1.5);
        graphdata.put("2015-10-12", 3.0);

        Chart lineChart = GraphFactory.getLineChart(Metric.NumberOfImpressions, graphdata);
        root.setCenter(lineChart);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}