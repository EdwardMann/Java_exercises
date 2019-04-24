import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Wrapper for any filters that should be applied
 */
public class Filter {
    private Date startDate = null;
    private Date endDate = null;
    private ArrayList<String> age = null;
    private ArrayList<String> gender = null;
    private ArrayList<String> income = null;
    private ArrayList<String> context = null;

    /**
     * Can pass parameters as null if no options present
     * 
     */
    public Filter(Date startDate, Date endDate, ArrayList<String> age, ArrayList<String> gender, ArrayList<String> income,
            ArrayList<String> context) {
        if (startDate != null) {
            this.startDate = startDate;
        }
        if (endDate != null) {
            this.endDate = endDate;
        }
        if (age != null && age.size() > 0) {
            this.age = age;
        }
        if (gender != null && gender.size() > 0) {
            this.gender = gender;
        }
        if (income != null && income.size() > 0) {
            this.income = income;
        }
        if (context != null && context.size() > 0) {
            this.context = context;
        }
    }

    /**
     * Leaves filters as null. Use if you know no filters should be applied
     */
    public Filter() {}
    
    private Predicate<? extends Item> getStartDatePredicate() {
        if (startDate == null) {
            return imp -> true;
        } else {
            return imp -> imp.getDate().after(startDate);
        }
    }
    
    private Predicate<? extends Item> getEndDatePredicate() {
        if (endDate == null) {
            return imp -> true;
        } else {
            Calendar c = Calendar.getInstance();
            c.setTime(endDate);
            c.add(Calendar.DATE, 1);
            Date newDate = c.getTime();
            return imp -> imp.getEndDate().before(newDate);
        }
    }
    
    private Predicate<? extends Item> getIDPredicate(Set<Double> idSet) {
        if (idSet == null) {
            return imp -> true;
        } else {
            return imp -> idSet.contains(imp.getID());
        }
    }

    private Predicate<ImpressionItem> getAgePredicate() {
        if (age == null) {
            return imp -> true;
        } else {
            return imp -> age.contains(imp.getAge());
        }
    }
    
    private Predicate<ImpressionItem> getGenderPredicate() {
        if (gender == null) {
            return imp -> true;
        } else {
            return imp -> gender.contains(imp.getGender());
        }
    }
    
    private Predicate<ImpressionItem> getIncomePredicate() {
        if (income == null) {
            return imp -> true;
        } else {
            return imp -> income.contains(imp.getIncome());
        }
    }
    
    private Predicate<ImpressionItem> getContextPredicate() {
        if (context == null) {
            return imp -> true;
        } else {
            return imp -> context.contains(imp.getContext());
        }
    }
    
    
    /**
     * Returns a filtered list
     * 
     * @param impressionLogs
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<ImpressionItem> getImpressions(List<ImpressionItem> impressionLogs) {
        return impressionLogs.parallelStream()
                .filter((Predicate<? super Item>) getStartDatePredicate())
                .filter((Predicate<? super Item>) getEndDatePredicate())
                .filter(getGenderPredicate())
                .filter(getAgePredicate())
                .filter(getIncomePredicate())
                .filter(getContextPredicate())
                .collect(Collectors.<ImpressionItem>toList());
    }
    
    /**
     * Returns a filtered list
     * 
     * @param impressionLogs
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<ClickItem> getClicks(List<ClickItem> clickLogs, Set<Double> idSet) {
        return clickLogs.parallelStream()
                .filter((Predicate<? super Item>) getStartDatePredicate())
                .filter((Predicate<? super Item>) getEndDatePredicate())
                .filter((Predicate<? super Item>) getIDPredicate(idSet))
                .collect(Collectors.<ClickItem>toList());
    }
    
    /**
     * Returns a filtered list
     * 
     * @param impressionLogs
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<ServerItem> getServers(List<ServerItem> serverLogs, Set<Double> idSet) {
        return serverLogs.parallelStream()
                .filter((Predicate<? super Item>) getStartDatePredicate())
                .filter((Predicate<? super Item>) getEndDatePredicate())
                .filter((Predicate<? super Item>) getIDPredicate(idSet))
                .collect(Collectors.<ServerItem>toList());
    }
}
