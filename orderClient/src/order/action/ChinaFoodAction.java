package order.action;

import java.util.ArrayList;

import order.model.Food;
import order.svc.ChinaFoodService;
import order.util.ConsoleUtils;

public class ChinaFoodAction implements Action{

	@Override
	public void execute() throws Exception {
		ChinaFoodService foodService = new ChinaFoodService();
		ArrayList<Food> foodList = foodService.getFoodName("�߽�");
		ConsoleUtils cu = new ConsoleUtils();
		
		if(foodList != null) {
			cu.showFoodList(foodList); //DB���� �߽� ������ �о�� ��ư�� ������ �Է�
		}
	}
}
