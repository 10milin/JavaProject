package order.controller;

import order.action.Action;

public class FrontController {
	public void requestProcess(Action action) {
		try {
			action.execute(); //액션을 실행
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
