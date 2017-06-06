package order.model;

public class Food {
	private String foodName;
	private int foodCount;
	
	public Food(String foodName) {
		this.foodName = foodName;
	}

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public int getFoodCount() {
		return foodCount;
	}

	public void setFoodCount(int foodCount) {
		this.foodCount = foodCount;
	}
}
