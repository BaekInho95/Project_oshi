package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.ConUtil;
import vo.FavoriteVO;

public class FavoriteDAO {
	private static FavoriteDAO instance = null;
	
	public FavoriteDAO() {}

	public static FavoriteDAO getInstance() {		
			if(instance==null) {
				instance = new FavoriteDAO();
			}
			return instance;
		}
	public int addfavorite(String board_type,String member_code,FavoriteVO vo) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		int chk = -1;
		
		try {
			con = ConUtil.getConnection();
			pstmt = con.prepareStatement("select * from favorite");
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
			pstmt = con.prepareStatement("insert into favorite values(?,?)");
			pstmt.setString(1, board_type);
			pstmt.setString(2, member_code);
			
			vo.setFav_board(board_type);
			vo.setFav_user(member_code);
			chk = pstmt.executeUpdate();
			
			}
		}catch(Exception e) {
     		  e.printStackTrace();
     	  }finally {
     		 // if(rs != null) try {rs.close();}catch(SQLException ss) {}
     		  if(pstmt != null) try {pstmt.close();}catch(SQLException ss) {}
     		  if(con != null) try {con.close();}catch(SQLException ss) {}
     	  }
		return chk;
	}
	
}