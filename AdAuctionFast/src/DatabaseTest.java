import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

public class DatabaseTest extends TestCase {
//     private String clickpath = "../Testing/2_week_campaign_2_short/click_log.csv";
//     private String impressionpath = "../Testing/2_week_campaign_2_short/impression_log.csv";
//     private String serverpath = "../Testing/2_week_campaign_2_short/server_log.csv";

    private String clickpath = "D:/Documents/Programming/Java/Workspace/adauction24/Testing/2_week_campaign_2_medium/click_log.csv";
    private String impressionpath = "D:/Documents/Programming/Java/Workspace/adauction24/Testing/2_week_campaign_2_medium/impression_log.csv";
    private String serverpath = "D:/Documents/Programming/Java/Workspace/adauction24/Testing/2_week_campaign_2_medium/server_log.csv";

    private File clickfile = new File(clickpath);
    private File impressionfile = new File(impressionpath);
    private File serverfile = new File(serverpath);

    /**
     * Tests if OpenCSV and DataInterface work with correct inputs (for mediums campaign)
     *
     * @throws IOException
     * @throws SQLException 
     * @throws ParseException 
     * @throws InterruptedException 
     */
    public void testWorks() throws IOException, SQLException, ParseException, InterruptedException {
        new OpenCSV(clickfile, impressionfile, serverfile);

        Map<Metric, Double> results = DataInterface.getAllMetrics();

        Map<Metric, Double> answers = new HashMap<>();
        answers.put(Metric.NumberOfImpressions,         24.0);
        answers.put(Metric.NumberOfClicks,              13.0);
        answers.put(Metric.NumberOfUniques,             13.0);
        answers.put(Metric.NumberOfBouncesPagesViewed,  5.0);
        answers.put(Metric.NumberOfBouncesTime,         1.0);
        answers.put(Metric.NumberOfConversions,         5.0);
        answers.put(Metric.TotalCost,                   0.7769504900000002);
        answers.put(Metric.CTR,                         0.5416666666666666);
        answers.put(Metric.CPA,                         0.15539009800000003);
        answers.put(Metric.CPC,                         0.05976542230769232);
        answers.put(Metric.CPM,                         32.37293708333334);
        answers.put(Metric.BounceRateTime,              0.07692307692307693);
        answers.put(Metric.BounceRatePagesViewed,       0.38461538461538464);

        for (Metric m : Metric.values()) {
            assertEquals(answers.get(m), results.get(m));
        }
    }
    
    public void testAgeFilter() throws IOException, SQLException, ParseException {
        new OpenCSV(clickfile, impressionfile, serverfile);

        ArrayList<String> age = new ArrayList<>(Arrays.asList("<25", ">54"));
        Filter filter = new Filter(null, null, age, null, null, null);
        
        DataInterface.filterLogs(filter);

        Map<Metric, Double> results = DataInterface.getAllMetrics();

        Map<Metric, Double> answers = new HashMap<>();
        answers.put(Metric.NumberOfImpressions,         6.0);
        answers.put(Metric.NumberOfClicks,              2.0);
        answers.put(Metric.NumberOfUniques,             2.0);
        answers.put(Metric.NumberOfBouncesPagesViewed,  0.0);
        answers.put(Metric.NumberOfBouncesTime,         0.0);
        answers.put(Metric.NumberOfConversions,         1.0);
        answers.put(Metric.TotalCost,                   0.09870699999999999);
        answers.put(Metric.CTR,                         0.3333333333333333);
        answers.put(Metric.CPA,                         0.09870699999999999);
        answers.put(Metric.CPC,                         0.049353499999999995);
        answers.put(Metric.CPM,                         16.451166666666666);
        answers.put(Metric.BounceRateTime,              0.0);
        answers.put(Metric.BounceRatePagesViewed,       0.0);

        for (Metric m : Metric.values()) {
            assertEquals(answers.get(m), results.get(m));
        }
    }
    
    public void testGenderFilter() throws IOException, SQLException, ParseException {
        new OpenCSV(clickfile, impressionfile, serverfile);

        ArrayList<String> gender = new ArrayList<>(Arrays.asList("Female"));
        Filter filter = new Filter(null, null, null, gender, null, null);
        
        DataInterface.filterLogs(filter);

        Map<Metric, Double> results = DataInterface.getAllMetrics();

        Map<Metric, Double> answers = new HashMap<>();
        answers.put(Metric.NumberOfImpressions,         16.0);
        answers.put(Metric.NumberOfClicks,              10.0);
        answers.put(Metric.NumberOfUniques,             10.0);
        answers.put(Metric.NumberOfBouncesPagesViewed,  4.0);
        answers.put(Metric.NumberOfBouncesTime,         1.0);
        answers.put(Metric.NumberOfConversions,         5.0);
        answers.put(Metric.TotalCost,                   0.6429490700000002);
        answers.put(Metric.CTR,                         0.625);
        answers.put(Metric.CPA,                         0.12858981400000005);
        answers.put(Metric.CPC,                         0.06429490700000003);
        answers.put(Metric.CPM,                         40.184316875000015);
        answers.put(Metric.BounceRateTime,              0.1);
        answers.put(Metric.BounceRatePagesViewed,       0.4);

        for (Metric m : Metric.values()) {
            assertEquals(answers.get(m), results.get(m));
        }
    }
    
    
    public void testIncomeFilter() throws IOException, SQLException, ParseException {
        new OpenCSV(clickfile, impressionfile, serverfile);

        ArrayList<String> income = new ArrayList<>(Arrays.asList("Low"));
        Filter filter = new Filter(null, null, null, null, income, null);
        
        DataInterface.filterLogs(filter);

        Map<Metric, Double> results = DataInterface.getAllMetrics();

        Map<Metric, Double> answers = new HashMap<>();
        answers.put(Metric.NumberOfImpressions,         6.0);
        answers.put(Metric.NumberOfClicks,              4.0);
        answers.put(Metric.NumberOfUniques,             4.0);
        answers.put(Metric.NumberOfBouncesPagesViewed,  3.0);
        answers.put(Metric.NumberOfBouncesTime,         0.0);
        answers.put(Metric.NumberOfConversions,         1.0);
        answers.put(Metric.TotalCost,                   0.062065);
        answers.put(Metric.CTR,                         0.6666666666666666);
        answers.put(Metric.CPA,                         0.062065);
        answers.put(Metric.CPC,                         0.01551625);
        answers.put(Metric.CPM,                         10.344166666666668);
        answers.put(Metric.BounceRateTime,              0.0);
        answers.put(Metric.BounceRatePagesViewed,       0.75);

        for (Metric m : Metric.values()) {
            assertEquals(answers.get(m), results.get(m));
        }
    }
    
    public void testContextFilter() throws IOException, SQLException, ParseException {
        new OpenCSV(clickfile, impressionfile, serverfile);
        
        ArrayList<String> context = new ArrayList<>(Arrays.asList("Shopping", "Social Media"));
        Filter filter = new Filter(null, null, null, null, null, context);
        
        DataInterface.filterLogs(filter);
        
        Map<Metric, Double> results = DataInterface.getAllMetrics();
        
        Map<Metric, Double> answers = new HashMap<>();
        answers.put(Metric.NumberOfImpressions,         11.0);
        answers.put(Metric.NumberOfClicks,              6.0);
        answers.put(Metric.NumberOfUniques,             6.0);
        answers.put(Metric.NumberOfBouncesPagesViewed,  3.0);
        answers.put(Metric.NumberOfBouncesTime,         1.0);
        answers.put(Metric.NumberOfConversions,         3.0);
        answers.put(Metric.TotalCost,                   0.34730981);
        answers.put(Metric.CTR,                         0.5454545454545454);
        answers.put(Metric.CPA,                         0.11576993666666667);
        answers.put(Metric.CPC,                         0.057884968333333335);
        answers.put(Metric.CPM,                         31.573619090909087);
        answers.put(Metric.BounceRateTime,              0.16666666666666666);
        answers.put(Metric.BounceRatePagesViewed,       0.5);
        
        for (Metric m : Metric.values()) {
            assertEquals(answers.get(m), results.get(m));
        }
    }

    public void testMultipleFilters() throws IOException, SQLException, ParseException {
        new OpenCSV(clickfile, impressionfile, serverfile);

        ArrayList<String> age = new ArrayList<>(Arrays.asList("25-34", "35-44", "45-54"));
        ArrayList<String> gender = new ArrayList<>(Arrays.asList("Female"));
        ArrayList<String> income = new ArrayList<>(Arrays.asList("Medium", "High"));
        ArrayList<String> context = new ArrayList<>(Arrays.asList("Blog", "News"));
        
        Filter filter = new Filter(null, null, age, gender, income, context);
        
        DataInterface.filterLogs(filter);

        Map<Metric, Double> results = DataInterface.getAllMetrics();

        Map<Metric, Double> answers = new HashMap<>();
        answers.put(Metric.NumberOfImpressions,         4.0);
        answers.put(Metric.NumberOfClicks,              3.0);
        answers.put(Metric.NumberOfUniques,             3.0);
        answers.put(Metric.NumberOfBouncesPagesViewed,  1.0);
        answers.put(Metric.NumberOfBouncesTime,         0.0);
        answers.put(Metric.NumberOfConversions,         1.0);
        answers.put(Metric.TotalCost,                   0.25189925999999996);
        answers.put(Metric.CTR,                         0.75);
        answers.put(Metric.CPA,                         0.25189925999999996);
        answers.put(Metric.CPC,                         0.08396641999999999);
        answers.put(Metric.CPM,                         62.97481499999999);
        answers.put(Metric.BounceRateTime,              0.0);
        answers.put(Metric.BounceRatePagesViewed,       0.3333333333333333);

        for (Metric m : Metric.values()) {
            assertEquals(answers.get(m), results.get(m));
        }
    }
    
    /**
     * Tests data for graph. Only uses one metric for the sake of developer time
     * 
     * @throws IOException
     * @throws SQLException
     * @throws ParseException 
     */
    public void testGraph() throws IOException, SQLException, ParseException {
        new OpenCSV(clickfile, impressionfile, serverfile);
        
        Map<String, Double> results = DataInterface.getGraphData(Metric.NumberOfClicks);
        
        Map<String, Double> answers = new HashMap<String, Double>();
        answers.put("2015-01-01", 4.0);
        answers.put("2015-01-02", 4.0);
        answers.put("2015-01-03", 4.0);
        answers.put("2015-01-04", 1.0);
        
        // unnecessary check - for clarity if test not passed
        for (String s : results.keySet() ) {
            assertEquals(answers.get(s), results.get(s));
        }
        // check truly equal
        assertTrue(answers.equals(results));
    }
    
    /**
     * Test histogram data method
     * 
     * @throws IOException
     * @throws SQLException
     * @throws ParseException
     */
    public void testHistogram() throws IOException, SQLException, ParseException {
        new OpenCSV(clickfile, impressionfile, serverfile);
        
        Map<String, Double> results = DataInterface.getHistogramPairs();
        
        Map<String, Double> answers = new HashMap<String, Double>();
        answers.put("0", 5.0);
        answers.put("1", 1.0);
        answers.put("4", 1.0);
        answers.put("7", 1.0);
        answers.put("9", 1.0);
        answers.put("11", 3.0);
        answers.put("13", 1.0);
        
        // unnecessary check - for clarity if test not passed
        for (String s : results.keySet() ) {
            assertEquals(answers.get(s), results.get(s));
        }
        // check truly equal
        assertTrue(answers.equals(results));
    }

    /**
     * Test result of if user chooses the files in the wrong order
     * 
     * @throws IOException
     * @throws ParseException 
     */
    public void testWrongOrderFiles() throws IOException, ParseException {
        try {
            new OpenCSV(serverfile, clickfile, impressionfile);
        } catch (IOException e) {
            fail("Failed to handle wrong order");
        }
    }

}
