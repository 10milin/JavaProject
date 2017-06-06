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
		//주문내역서를 읽어와 토큰화
		StringTokenizer st = new StringTokenizer(order.util.FoodOrder.orderList.getText(),"\n|x ");
		boolean check = false;
		
		//주문내역을 토큰을 이용해 분리
		while(st.hasMoreTokens()) {
			orderList.add(new Food(st.nextToken())); //음식이름
			orderList.get(index).setFoodCount(Integer.parseInt(st.nextToken())); //음식 갯수
			index++;
		}
		
		for(int i = 0; i < orderList.size(); i++) {
			if(orderList.get(i).getFoodName().equals(foodName)) { //선택한 음식과 주문내역에 있는 음식이 같다면
				orderList.get(i).setFoodCount(orderList.get(i).getFoodCount() + 1); //음식의 갯수 증가
				check = true;
			}
		}
		
		if(check != true) { //선택한 음식이 주문내역에 없는 경우
			orderList.add(new Food(foodName)); //음식이름
			orderList.get(index).setFoodCount(1); //음식 갯수 1개
		}
		
		FoodOrder.orderList.setText(""); //주문내역서 초기화
		
		//주문내역서에 만들어둔 ArrayList의 내용을 append
		for(int i = 0; i < orderList.size(); i++) {
			FoodOrder.orderList.append(orderList.get(i).getFoodName() + " x " + orderList.get(i).getFoodCount() + "\n");
		}
	}
	
	//선택한 Type에 해당하는 음식을 버튼에 읽어옴
	public void showFoodList(ArrayList<Food> foodList) {
		for(int i = 0; i < foodList.size(); i++) {
			FoodOrder.foodBtn[i].setText(foodList.get(i).getFoodName());
		}
	}
	
	//서버에서 받아온 주문번호를 리턴
	public String showOrderNumber(String num){
		return "주문번호 : " + num;
	}
}
