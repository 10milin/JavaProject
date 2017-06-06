package order.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcUtils {
	static {
		// Ŭ������ �ε��� �� �ѹ� ȣ��Ǵ� ����
		// Class.forName() : Ư�� Ŭ������ �� �޸𸮷� �ε��ϴ� �޼���
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("����̹� �ε� ����");
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		// DB �۾��� �ʿ��� Connection ��ü�� �������ִ� �޼���
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String user = "java_app";
		String password = "1234";
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			System.out.println("DB ���� ����");
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public static void close(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Statement stmt) {
		try {
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(PreparedStatement pstmt) {
		try {
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Resource�� �ݾ��ִ� �޼��� - �����ε�(Overloading)
	public static void close(ResultSet rs) {
		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// transaction ó���ϴ� �޼���
	public static void commit(Connection conn) {
		try {
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void rollback(Connection conn) {
		try {
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
