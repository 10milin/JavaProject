package foodServer;

import java.net.*;
import java.io.*;

public class ManagerThread extends Thread{
	DataInputStream in;
	DataOutputStream out;
	int orderNum = 1;
	
	ManagerThread(Socket socket) {
		try {
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
		}catch(Exception e){}
	}
	
	@Override
	public void run() {
		String name = "";
		String msg = "";
		try {
			name = in.readUTF();
			FoodServer.clients.put(name, out); //HashMap�� Ŭ���̾�Ʈ �߰�
			
			while(in != null) {
				msg = in.readUTF();
				if(msg.charAt(2) == '.' || msg.charAt(3) == '.') {
					send(Integer.toString(orderNum));
					orderNumSend(msg, orderNum);
				}else {
					send(msg); //Ŭ���̾�Ʈ�� ���� �޽����� ���Ź޾� ����
				}
			}
		}catch(Exception e) {
			
		}finally {
			FoodServer.clients.remove(name); //HashMap���� Ŭ���̾�Ʈ ����
		}
	}
	
	public void send(String msg) {
		try{
			DataOutputStream out = (DataOutputStream)FoodServer.clients.get("receiver");
			out.writeUTF(msg);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void orderNumSend(String name, int index) {
		try{
			DataOutputStream out = (DataOutputStream)FoodServer.clients.get(name);
			out.writeUTF(Integer.toString(index));
			orderNum++;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
