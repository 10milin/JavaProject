package order.action;

import java.util.ArrayList;

import order.model.Food;
import order.svc.WesternFoodService;
import order.util.ConsoleUtils;

public class WesternFoodAction implements Action{

	@Override
	public void execute() throws Exception {
		WesternFoodService foodService = new WesternFoodService();
		ArrayList<Food> foodList = foodService.getFoodName("���");
		ConsoleUtils cu = new ConsoleUtils();
		
		if(foodList != null) {
			cu.showFoodList(foodList); //DB���� ��� ������ �о�� ��ư�� ������ �Է�
		}
	}
}
