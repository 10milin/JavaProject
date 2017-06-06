package order.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static order.db.JdbcUtils.*;
public class FoodDAO {
	Connection conn;
	
	public FoodDAO() {
		conn = getConnection();
	}
	
	//������ ������ Type�� �ش��ϴ� ������ �о���� SQL�� ����
	public ArrayList<Food> getOneFoodType(String foodType) {
		String sql = "select foodName from food where foodType = ?";
		ArrayList<Food> foodList = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Food food = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, foodType);
			rs = pstmt.executeQuery();
			foodList = new ArrayList<Food>();
			
			while(rs.next()) {
				food = new Food(rs.getString("foodName"));
				foodList.add(food);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
			close(conn);
		}
		return foodList;
	}
}
