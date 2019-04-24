import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Parameter;

public class FilterSystem {
    public static Predicate<ClickItem> hasUnique(Function<? super ClickItem, ?> predicate) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return p -> seen.putIfAbsent(predicate.apply(p), Boolean.TRUE) == null;
    }
    
    public static List<ImpressionItem> insertNameHere(ArrayList<ImpressionItem> impressions, Filter filter){
    	//ArrayList<Predicate<ImpressionItem>> age = filter.getAge();
    	List<ImpressionItem> list = impressions.stream().collect(Collectors.<ImpressionItem>toList());
    	return list;
    }
    
//    public static Predicate<ServerItem> isBefore(Date endDate) {
//    	return p -> p.getEntryDate().before(endDate);
//    }
//
//    public static Predicate<ServerItem> isAfter(Date startDate) {
//    	return p -> p.getEntryDate().after(startDate);
//    }

    public static Predicate<ServerItem> isTimeBounce(int minSeconds){
    	return p -> (p.getExitDate().getTime() - p.getEntryDate().getTime()) / 1000 <= minSeconds;
    }
    
    public static Predicate<ServerItem> isPageBounce(int minPages){
    	return p -> p.getPagesViewed() <= minPages;
    }
    
    public static Predicate<ServerItem> isConversion(){
    	return p -> p.getConversion().toLowerCase().equals("Yes");
    }
    
    public static List<ServerItem> filterServers(ArrayList<ServerItem> servers, Predicate<ServerItem> predicate){
		return servers.stream().filter(predicate).collect(Collectors.<ServerItem>toList());
    }
    
    public static List<ClickItem> filterClicks(ArrayList<ClickItem> clicks, Predicate<ClickItem> predicate){
        return clicks.stream().filter(predicate).collect(Collectors.<ClickItem>toList());
    }
        
    public static List<ClickItem> filterClicks(ArrayList<ClickItem> clicks, Predicate<ClickItem> predicate1, Predicate<ClickItem> predicate2){
        return clicks.stream().filter(predicate1.and(predicate2)).collect(Collectors.<ClickItem>toList());
    }
    
    public static List<ImpressionItem> filterImpressions(ArrayList<ImpressionItem> impressions, Filter filter){
		return null;
    }
}
