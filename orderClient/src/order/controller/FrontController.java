package order.controller;

import order.action.Action;

public class FrontController {
	public void requestProcess(Action action) {
		try {
			action.execute(); //�׼��� ����
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
