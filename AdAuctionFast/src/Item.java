import java.util.Date;

public class Item{

    Date date;
    Double id; 
    
    public Item(Date date, Double id){
        this.date = date;
        this.id = id;
    }

    public Date getDate() {
        return date;
    }
    
    /**
     * Hacky - to make ServerItems easier to work with
     */
    public Date getEndDate() {
        return date;
    }

    public Double getID() {
        return id;
    }
    
    public void printData(){
        System.out.println(getDate() + " " + getID());
    }
}
