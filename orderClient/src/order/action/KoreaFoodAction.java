package order.action;

import java.util.ArrayList;

import order.model.Food;
import order.svc.KoreaFoodService;
import order.util.ConsoleUtils;

public class KoreaFoodAction implements Action{

	@Override
	public void execute() throws Exception {
		KoreaFoodService foodService = new KoreaFoodService();
		ArrayList<Food> foodList = foodService.getFoodName("한식");
		ConsoleUtils cu = new ConsoleUtils();
		
		if(foodList != null) {
			cu.showFoodList(foodList); ////DB에서 한식 음식을 읽어와 버튼에 내용을 입력
		}
	}
}
