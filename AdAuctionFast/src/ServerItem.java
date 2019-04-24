import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ServerItem extends Item {

    Date exitDate;
    int pagesViewed;
    String conversion;

    public ServerItem(Date entryDate, Double id, Date exitDate, int pagesViewed, String conversion) {
        super(entryDate, id);
        this.exitDate = exitDate;
        this.pagesViewed = pagesViewed;
        this.conversion = conversion;
    }

    public Date getExitDate() {
        return exitDate;
    }

    public int getPagesViewed() {
        return pagesViewed;
    }

    public int getSecondsSpent() {
        long duration = exitDate.getTime() - date.getTime();
        long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
        try {
            return Math.toIntExact(diffInSeconds);
        } catch (ArithmeticException e) {
            return Integer.MAX_VALUE;
        }
    }

    public boolean isConverted() {
        return conversion.equals("Yes");
    }

    public String getConversion() {
        return conversion;
    }
}
