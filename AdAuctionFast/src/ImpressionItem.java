import java.io.Serializable;
import java.util.Date;

public class ImpressionItem extends Item{

	String gender, age, income, context;
	double impressionCost;
	
	public ImpressionItem(Date date, Double id, String gender, String age, String income,
			String context, double impressionCost){
		super(date, id);
		this.gender = gender;
		this.age = age;
		this.income = income;
		this.context = context;
		this.impressionCost = impressionCost;
	}
	
	public double getCost(){
		return impressionCost;
	}

	public String getGender() {
		return gender;
	}

	public String getAge() {
		return age;
	}

	public String getIncome() {
		return income;
	}

	public String getContext() {
		return context;
	}
	
	public void printData(){
		System.out.println(getDate() + " " + getID()+ " " + getGender()+ " " + getIncome()+ " " + getContext()+ " " + getCost());
	}
}
