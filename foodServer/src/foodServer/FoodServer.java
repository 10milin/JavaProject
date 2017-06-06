package foodServer;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;

public class FoodServer {
	static HashMap clients;
	
	public static void main(String[] ar) {
		clients = new HashMap();
		Collections.synchronizedMap(clients);
		try {
			ServerSocket serverSocket = new ServerSocket(7700); //�������� ��Ʈ ����
			System.out.println("������ ���۵Ǿ����ϴ�.");
			
			while(true) {
				Socket socket = serverSocket.accept();		
				ManagerThread thread = new ManagerThread(socket);
				thread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
