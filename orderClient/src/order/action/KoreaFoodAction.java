package order.action;

import java.util.ArrayList;

import order.model.Food;
import order.svc.KoreaFoodService;
import order.util.ConsoleUtils;

public class KoreaFoodAction implements Action{

	@Override
	public void execute() throws Exception {
		KoreaFoodService foodService = new KoreaFoodService();
		ArrayList<Food> foodList = foodService.getFoodName("�ѽ�");
		ConsoleUtils cu = new ConsoleUtils();
		
		if(foodList != null) {
			cu.showFoodList(foodList); ////DB���� �ѽ� ������ �о�� ��ư�� ������ �Է�
		}
	}
}
