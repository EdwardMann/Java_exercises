import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;
import java.util.TreeMap;

public class Controller {
	
    EvaluationTool gui;

    /**
     * Fetches the map containing the mapping of key metrics to their corresponding values
     *
     * @return
     */
    public Map<Metric, Double> getKeyMetrics() {
        return DataInterface.getAllMetrics();
    }

    /**
     * Tells OpenCSV to load the three selected csv files into the database
     *
     * @param clickLog
     *            A path to the location of the click log
     * @param impressionLog
     *            A path to the location of the impression log
     * @param serverLog
     *            A path to the location of the server log
     * @throws IOException
     * @throws ParseException
     * @throws InterruptedException 
     */
    public void openCSV(File clickLog, File impressionLog, File serverLog) throws IOException, ParseException, InterruptedException {
        new OpenCSV(clickLog, impressionLog, serverLog);
    }

    public Map<String, Double> getGraphData(Metric metric) throws ParseException {
        return DataInterface.getGraphData(metric);
    }
    
    public void resetLogs(){
        DataInterface.resetLogs();
    }
    
    public TreeMap<String, Double> getHistogramPairs(){
        return DataInterface.getHistogramPairs();
    }

    public void filterLogs(Filter filter) {
        DataInterface.filterLogs(filter);
    }

    public void setSDF(String string) {
        DataInterface.setSDF(string);
        
    }

    public int getMinSeconds() {
        return DataInterface.getMinSeconds();
    }

    public int getMinPages() {
        return DataInterface.getMinPages();
    }

    public void setMinSeconds(int defaultTime) {
        DataInterface.setMinSeconds(defaultTime);
    }
    
    public void setMinPages(int defaultTime) {
        DataInterface.setMinPages(defaultTime);
    }
}
