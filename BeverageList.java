import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BeverageList{
	private static BeverageList beverageListInstance = new BeverageList();
	//private HashMap<String,Beverage> beverageList=new HashMap<String,Beverage>();
	private List<Beverage> beverageList=new ArrayList<Beverage>();
	private BeverageList() {}
	public static BeverageList getInstance()
	{
		return beverageListInstance;
	}
	
	public synchronized void addBeverage(Beverage beverage)
	{
		beverageList.add(beverage);
	}
	public List<Beverage> getBeverageList()
	{
		return beverageList;
	}
	public static void reset()
	{
		//reset the instance variable
		beverageListInstance = new BeverageList();
	}
}
