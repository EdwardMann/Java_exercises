import java.io.Serializable;
import java.util.Date;

public class ClickItem extends Item{
	
	double clickCost;
	
	public ClickItem(Date date, Double id, double clickCost){
		super(date, id);
		this.clickCost = clickCost;
	}
	
	public double getCost(){
	// Convert the cost to pounds
		return clickCost / 100;
	}
}
