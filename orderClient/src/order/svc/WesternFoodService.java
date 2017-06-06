package order.svc;

import java.util.ArrayList;

import order.model.Food;
import order.model.FoodDAO;

public class WesternFoodService {
	public ArrayList<Food> getFoodName(String foodType) {
	      ArrayList<Food> foodList = null;
	      FoodDAO dao = new FoodDAO();
	      
	      foodList = dao.getOneFoodType(foodType); //DB에서 양식에 해당하는 음식을 가져온다
	      
	      return foodList;
	}
}
