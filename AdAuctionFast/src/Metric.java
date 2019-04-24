/**
 * Created by Douglas on 27/02/17.
 */
public enum Metric {
    NumberOfUniques("Number Of Uniques"),
    NumberOfImpressions("Number Of Impressions"), 
    NumberOfClicks("Number Of Clicks"),
    NumberOfBouncesPagesViewed("Number Of Bounces (Pages)"), 
    NumberOfBouncesTime("Number Of Bounces (Time)"), 
    NumberOfConversions("Number Of Conversions"), 
    TotalCost("Total Cost"), CTR("Click-Through-Rate"), 
    CPA("Cost-per-Acquisition"), 
    CPC("Cost-per-Click"), 
    CPM("Cost-per-Thousand Impressions"), 
    BounceRateTime("Bounce Rate (Time)"), 
    BounceRatePagesViewed("Bounce Rate (Pages)");

    private final String name;

    private Metric(String s) {
        name = s;
    }

    public String toString() {
        return this.name;
    }
}
