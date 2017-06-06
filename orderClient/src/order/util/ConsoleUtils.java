package order.util;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.StringTokenizer;

import order.model.Food;
import order.thread.ClientSender;

public class ConsoleUtils {
		
	public void showFoodOrder(String foodName) {
		ArrayList<Food> orderList = new ArrayList<Food>();
		int index = 0;
		//�ֹ��������� �о�� ��ūȭ
		StringTokenizer st = new StringTokenizer(order.util.FoodOrder.orderList.getText(),"\n|x ");
		boolean check = false;
		
		//�ֹ������� ��ū�� �̿��� �и�
		while(st.hasMoreTokens()) {
			orderList.add(new Food(st.nextToken())); //�����̸�
			orderList.get(index).setFoodCount(Integer.parseInt(st.nextToken())); //���� ����
			index++;
		}
		
		for(int i = 0; i < orderList.size(); i++) {
			if(orderList.get(i).getFoodName().equals(foodName)) { //������ ���İ� �ֹ������� �ִ� ������ ���ٸ�
				orderList.get(i).setFoodCount(orderList.get(i).getFoodCount() + 1); //������ ���� ����
				check = true;
			}
		}
		
		if(check != true) { //������ ������ �ֹ������� ���� ���
			orderList.add(new Food(foodName)); //�����̸�
			orderList.get(index).setFoodCount(1); //���� ���� 1��
		}
		
		FoodOrder.orderList.setText(""); //�ֹ������� �ʱ�ȭ
		
		//�ֹ��������� ������ ArrayList�� ������ append
		for(int i = 0; i < orderList.size(); i++) {
			FoodOrder.orderList.append(orderList.get(i).getFoodName() + " x " + orderList.get(i).getFoodCount() + "\n");
		}
	}
	
	//������ Type�� �ش��ϴ� ������ ��ư�� �о��
	public void showFoodList(ArrayList<Food> foodList) {
		for(int i = 0; i < foodList.size(); i++) {
			FoodOrder.foodBtn[i].setText(foodList.get(i).getFoodName());
		}
	}
	
	//�������� �޾ƿ� �ֹ���ȣ�� ����
	public String showOrderNumber(String num){
		return "�ֹ���ȣ : " + num;
	}
}
