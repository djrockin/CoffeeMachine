import java.util.ArrayList;
import java.util.List;

public class Beverage implements Runnable {
	private String beverageName;
	private List<Ingredient> ingredientList=new ArrayList<>(); ;
	
	public Beverage(String beverageName,List<Ingredient> ingList)
	{
		this.beverageName=beverageName;
		this.ingredientList=ingList;
	}

	public String getBeverageName() {
		return beverageName;
	}

	public void setBeverageName(String beverageName) {
		this.beverageName = beverageName;
	}

	public List<Ingredient> getIngredientList() {
		return ingredientList;
	}

	public void setIngredientList(List<Ingredient> ingredientList) {
		this.ingredientList = ingredientList;
	}

	@Override
	public void run() {
		InventoryStore.getInstance().checkAndUpdate(this);
	}	
}
