package order.svc;

import java.util.ArrayList;

import order.model.Food;
import order.model.FoodDAO;

public class KoreaFoodService {
	public ArrayList<Food> getFoodName(String foodType) {
	      ArrayList<Food> foodList = null;
	      FoodDAO dao = new FoodDAO();
	      
	      foodList = dao.getOneFoodType(foodType); //DB���� �ѽĿ� �ش��ϴ� ������ �����´�
	      
	      return foodList;
	}
}
