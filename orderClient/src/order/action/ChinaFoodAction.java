package order.action;

import java.util.ArrayList;

import order.model.Food;
import order.svc.ChinaFoodService;
import order.util.ConsoleUtils;

public class ChinaFoodAction implements Action{

	@Override
	public void execute() throws Exception {
		ChinaFoodService foodService = new ChinaFoodService();
		ArrayList<Food> foodList = foodService.getFoodName("중식");
		ConsoleUtils cu = new ConsoleUtils();
		
		if(foodList != null) {
			cu.showFoodList(foodList); //DB에서 중식 음식을 읽어와 버튼에 내용을 입력
		}
	}
}
