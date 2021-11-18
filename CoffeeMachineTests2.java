import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class CoffeeMachineTests2 {
	
	public static void extractDataFromJSON(String fileName) throws Exception
	{
	    	//extract the input 
		   String data = new String(Files.readAllBytes(Paths.get(fileName)));
		   JSONObject inputData = new JSONObject(data);
		   //machine data
		   JSONObject machineData=inputData.getJSONObject("machine");
		   //get the number of Outlets
		   JSONObject outlets=machineData.getJSONObject("outlets");
		   int outletCount=outlets.getInt("count_n");
		   //create the Machine instance
		   Machine.initMachine(outletCount);
		   
		   //Get the ingredients data
		   JSONObject ingredients=machineData.getJSONObject("total_items_quantity");
		   //add the ingredients to the InventoryStore
		   ingredients.keySet().stream().forEach(item -> {
      int quantity = ingredients.getInt(item);
      Ingredient ingredient = new Ingredient(item, quantity);
      InventoryStore inventoryStore = InventoryStore.getInstance();
      inventoryStore.addItemsToInventory(ingredient);
  });
	  //get the list of beverages
	  JSONObject beverageList=machineData.getJSONObject("beverages");
		 beverageList.keySet().stream().forEach(beverageName -> {
      JSONObject ingredientsInput = beverageList.getJSONObject(beverageName);
      List<Ingredient> ingredients1 = ingredientsInput.keySet().stream().map(ingredientInput -> {
          int quantity = ingredientsInput.getInt(ingredientInput);
          Ingredient ingredient = new Ingredient(ingredientInput, quantity);
          return ingredient;
      }).collect(Collectors.toList());
      Beverage beverage = new Beverage(beverageName, ingredients1);
      BeverageList beverageList1 = BeverageList.getInstance();
      beverageList1.addBeverage(beverage);
  }); 
	}
	
	@Test
	public void testFourOutletWithPartialSuccessful() throws Exception
	{
		//For this input all the beverages get processed
		String fileName="src/input1.txt";
		extractDataFromJSON(fileName);
		//process the beverage list
		List<Beverage> beverages=BeverageList.getInstance().getBeverageList();
		for(Beverage bvg:beverages)
		{
			//add the requests 
			Machine.getInstance().process(bvg);
		}
		Thread.sleep(2000);
		
		Assert.assertEquals(4, InventoryStore.getInstance().getAcceptedBeverageList().size());
		Assert.assertEquals(0, InventoryStore.getInstance().getRejectedBeverageList().size());
		Assert.assertEquals(2, 2);
		//reset beverageList
		BeverageList.getInstance().reset();
		//reset the inventory store
		InventoryStore.getInstance().reset();
		//reset the machine
		Machine.getInstance().reset();
	}
}
