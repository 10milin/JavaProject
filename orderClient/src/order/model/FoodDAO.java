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
	
	//선택한 음식의 Type에 해당하는 음식을 읽어오는 SQL문 실행
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
