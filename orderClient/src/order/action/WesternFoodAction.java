package order.action;

import java.util.ArrayList;

import order.model.Food;
import order.svc.WesternFoodService;
import order.util.ConsoleUtils;

public class WesternFoodAction implements Action{

	@Override
	public void execute() throws Exception {
		WesternFoodService foodService = new WesternFoodService();
		ArrayList<Food> foodList = foodService.getFoodName("양식");
		ConsoleUtils cu = new ConsoleUtils();
		
		if(foodList != null) {
			cu.showFoodList(foodList); //DB에서 양식 음식을 읽어와 버튼에 내용을 입력
		}
	}
}
