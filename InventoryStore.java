import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InventoryStore {
	private static InventoryStore inventoryStoreInstance=null;
	private HashMap<String,Ingredient> ingredientStore=new HashMap<String,Ingredient>();
	private ArrayList<Beverage> rejectedBeverage=new ArrayList<Beverage>();
	private ArrayList<Beverage> acceptedBeverage=new ArrayList<Beverage>();
	
	//making the class si
	private InventoryStore() {}
	
	public static InventoryStore getInstance()
	{
		if(inventoryStoreInstance==null)
		{
			inventoryStoreInstance=new InventoryStore(); 
		}
		return inventoryStoreInstance;
	}	
	//making this synchronized to make it thread safe
	public synchronized void addItemsToInventory(Ingredient ingredient)
	{
		this.ingredientStore.put(ingredient.getIngredientName(),ingredient);
	}
	//making the method synchronized to make it thread safe 
	public synchronized void checkAndUpdate(Beverage beverage)
	{
		//get the ingredient list
		 List<Ingredient> IngredientList=beverage.getIngredientList();
		 for(Ingredient ingredient:IngredientList)
		 {
			 //check if the ingredient is present
			 if(ingredientStore.containsKey(ingredient.getIngredientName()))
			 { 
				 //check if the ingredient is sufficient
				if( ingredientStore.get(ingredient.getIngredientName()).getQuantity() < ingredient.getQuantity())
				{
					 //display the message that the beverage cannot be prepared because the ingredient is not sufficient
					System.out.println(beverage.getBeverageName()+Consts.CANNOT_PREPARE+" because item "+ingredient.getIngredientName() + Consts.NOT_SUFFICIENT); 
					rejectedBeverage.add(beverage);
					return;
				}
			 }
			 else
			 { 
				 //display the message that the beverage cannot be prepared because the ingredient is not available
				 System.out.println(beverage.getBeverageName()+Consts.CANNOT_PREPARE +" because "+ingredient.getIngredientName() +Consts.NOT_AVAILABLE);
				 rejectedBeverage.add(beverage);
				 return;
			  }
		 }
		 //if we have reached here it means we can prepare the beverage
		 //update the ingredient
		 for(Ingredient ingredient:IngredientList)
		 {
			 
			 int q=ingredientStore.get(ingredient.getIngredientName()).getQuantity();
			 ingredientStore.get(ingredient.getIngredientName()).setQuantity(q-ingredient.getQuantity());
		 }
		 acceptedBeverage.add(beverage);
		 //display the message that beverage can be prepared
		 System.out.println(beverage.getBeverageName()+Consts.PREPARED);
	}
	public ArrayList<Beverage> getAcceptedBeverageList()
	{
		return acceptedBeverage;
	}
	public ArrayList<Beverage> getRejectedBeverageList()
	{
		return rejectedBeverage;
	}
	public static void reset()
	{
		//reset the instance variable
		inventoryStoreInstance = null;
	}
}
