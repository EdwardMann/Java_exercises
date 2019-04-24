import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.swing.JFrame;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 * Created by Douglas on 21/02/17.
 */
public class DataInterface {
//    private static Connection connection = null;
	public static List<ClickItem> clicks;
	public static List<ImpressionItem> impressions;
	public static List<ServerItem> servers;
	public static List<ClickItem> clicksBackup;
	public static List<ImpressionItem> impressionsBackup;
	public static List<ServerItem> serversBackup;
	SimpleDateFormat parser = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static int minSeconds = 20; 
    private static int minPages = 1;

    private static Double getMetric(Metric metric){ //needs to work with filters
        switch (metric){
        case NumberOfImpressions:
        	return (double) impressions.size();
        case NumberOfClicks:
        	return (double) clicks.size();
        case NumberOfUniques:
        	HashSet<ClickItem> uniques = new HashSet<>(clicks);
        	return (double) uniques.size();
        
        case NumberOfBouncesTime:
            int bounces = 0;
            for(ServerItem cl : servers){
                if(cl.getSecondsSpent() <= minSeconds){
                    bounces++;
                }
            }
            return (double) bounces;
        case NumberOfBouncesPagesViewed:
        	bounces = 0;
        	for(ServerItem cl : servers){
        		if(cl.getPagesViewed() <= minPages){
        			bounces++;
        		}
        	}
        	return (double) bounces;
        case NumberOfConversions:
            int count = 0;
            for(ServerItem item : servers){
            	if(item.isConverted()){
            		count++;
            	}
            }
        	return (double) count;
        case TotalCost:
        	double totalCost = 0.0;
        	for(ClickItem c : clicks){
        		totalCost += c.getCost();
        	}
        	for(ImpressionItem i : impressions){
                totalCost += i.getCost();
            }
        	return totalCost;
        case CTR:
        	return getMetric(Metric.NumberOfClicks)
        			/ getMetric(Metric.NumberOfImpressions);
        case CPA:
            return getMetric(Metric.TotalCost)
                    / getMetric(Metric.NumberOfConversions);
        case CPC:
            return getMetric(Metric.TotalCost)
                    / getMetric(Metric.NumberOfClicks);
        case CPM:
            return (getMetric(Metric.TotalCost) * 1000)
                    / getMetric(Metric.NumberOfImpressions);
        case BounceRateTime:
        	return getMetric(Metric.NumberOfBouncesTime)
        			/ getMetric(Metric.NumberOfUniques);
        case BounceRatePagesViewed:
        	return getMetric(Metric.NumberOfBouncesPagesViewed)
        			/ getMetric(Metric.NumberOfUniques);
        default:
        	return 0.0;
        }

    }

    /**
     * Returns a hashmap mapping all metrics defined in the Metric enum class to their corresponding values in the
     * database
     *
     */
    public static Map<Metric, Double> getAllMetrics(){
        Map<Metric, Double> metrics = new HashMap<>();
        for (Metric m : Metric.values()) {
            metrics.put(m, getMetric(m));
        }
        return metrics;
    }

    public static Map<String, Double> getGraphData(Metric metric) throws ParseException {
        // use linked hashmap to preserve insert ordering
        Map<String, Double> graphData = new LinkedHashMap<>();
        String date;

        double previouscost = 0;
        double currentcost = 0;

        switch (metric) {
        case NumberOfImpressions:
            for (ImpressionItem impression : impressions) {
                date = sdf.format(impression.getDate());
                if (!graphData.containsKey(date))
                    graphData.put(date, 1.0);
                else
                    graphData.put(date, graphData.get(date) + 1.0);
            }
            break;
        case NumberOfClicks:
            for (ClickItem click : clicks) {
                date = sdf.format(click.getDate());
                if (!graphData.containsKey(date))
                    graphData.put(date, (double) 1);
                else
                    graphData.put(date, graphData.get(date) + 1);
            }
            break;
        case NumberOfUniques:
            // Remove any duplicates by added to hash set
            LinkedHashSet<ClickItem> uniques = new LinkedHashSet<ClickItem>(clicks);

            for (ClickItem click : uniques) {

                date = sdf.format(click.getDate());

                if (!graphData.containsKey(date))
                    graphData.put(date, (double) 1);
                else
                    graphData.put(date, graphData.get(date) + 1);

            }
            break;
        case NumberOfBouncesTime:
            for (ServerItem server : servers) {
                date = sdf.format(server.getDate());
                if (!graphData.containsKey(date))
                    graphData.put(date, (double) 1);
                else
                    graphData.put(date, graphData.get(date) + 1);
            }
            break;
        case NumberOfConversions:
            for (ServerItem server : servers) {
                date = sdf.format(server.getDate());
                if (server.isConverted()) {
                    if (!graphData.containsKey(date))
                        graphData.put(date, (double) 1);
                    else
                        graphData.put(date, graphData.get(date) + 1);
                }
            }
            break;
        case TotalCost:
            for (ClickItem click : clicks) {
                date = sdf.format(click.getDate());
                currentcost = click.getCost();
                
                if (!graphData.containsKey(date)) {
                    graphData.put(date, currentcost + previouscost);
                } else
                    graphData.put(date, graphData.get(date) + currentcost);
                
                previouscost += currentcost;
            }

            previouscost = 0;   // assuming the arrays are in date order
            for (ImpressionItem impression : impressions) {
                date = sdf.format(impression.getDate());
                currentcost = impression.getCost();
                
                if (!graphData.containsKey(date)) {
                        graphData.put(date, currentcost + previouscost);
                } else
                    graphData.put(date, graphData.get(date) + currentcost);
                
                previouscost += currentcost;
            }
            break;
        default:
            break;

        }
        return graphData;
    }
    
    public static TreeMap<String, Double> getHistogramPairs(){
		int temp;

		LinkedHashMap<String, Double> costPairs = new LinkedHashMap<String, Double>();
		String date;

		for (ClickItem click : clicks) {
			temp = (int) (click.getCost() * 100);
			date = String.valueOf(temp);

			if (!costPairs.containsKey(date)) {
				costPairs.put(date, 1.0);
			}
			else
				costPairs.put(date, costPairs.get(date) + 1.0);
		}

		TreeMap<String,Double> costDivi = new TreeMap<String,Double>(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				Double a = Double.parseDouble(o1);
				Double b = Double.parseDouble(o2);
				return a.compareTo(b);
			}
		});

		costDivi.putAll(costPairs);
		return costDivi;

	}
    
    public static void setSDF(String s){
    	DataInterface.sdf = new SimpleDateFormat(s);
    }

    private static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); // minus number would decrement the days
        return cal.getTime();
    }

    // these four methods are for changing the definition of a bounce
	public static void setMinSeconds(int defaultTime) {
		minSeconds = defaultTime;
	}

	public static void setMinPages(int defaultPage) {
		minPages = defaultPage;
	}
	
	static int getMinSeconds(){
		return minSeconds;
	}
	
	static int getMinPages(){
		return minPages;
	}
	
    public static void filterLogs(Filter filter) {
        filterImpressionLogs(filter);
        
        // make sure clicks and servers are filtered by the same criteria as impressions
        HashSet<Double> idSet = new HashSet<Double>();
        for (ImpressionItem impressionLog : impressions) {
            idSet.add(impressionLog.getID());
        }
        filterClickLogs(filter, idSet);
        filterServerLogs(filter, idSet);
    }

	private static void filterImpressionLogs(Filter filter){
	    impressions = filter.getImpressions(impressionsBackup);
	}

	private static void filterClickLogs(Filter filter, Set<Double> idSet) {
        clicks = filter.getClicks(clicksBackup, idSet);
    }

	private static void filterServerLogs(Filter filter, Set<Double> idSet) {
		servers = filter.getServers(serversBackup, idSet);
	}

	public static void resetLogs() {
		servers = serversBackup;
		impressions = impressionsBackup;
		clicks = clicksBackup;
	}
}
