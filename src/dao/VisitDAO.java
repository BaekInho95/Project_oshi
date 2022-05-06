package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.ConUtil;
import vo.UrlVO;

public class VisitDAO {
	private static VisitDAO instance;

	private VisitDAO() {
	}

	public static VisitDAO getInstance() {
		if (instance == null) {
			instance = new VisitDAO();
		}
		return instance;
	}
	
		//방문자 수 증가
		public void setVisitTotalCount() {
			Connection con = null;
			PreparedStatement pstmt = null;
			
			try {
				con = ConUtil.getConnection();
				pstmt = con.prepareStatement("insert into visit (visit) values (sysdate)");
				pstmt.executeUpdate();
				
			}catch(Exception ee) {
				ee.printStackTrace();			
			}finally {
					if(pstmt!=null)try {pstmt.close();}catch(SQLException ss) {}
					if(con!=null)try {con.close();}catch(SQLException ss) {}
			}		
		}//방문자 수 증가 끝
		
		//전체 방문자 수 가져오기
		public int getVisitTotalCount() {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			int getVisitTotalCount = 0;
			try {
				con = ConUtil.getConnection();			
				pstmt = con.prepareStatement("select count(*) from visit");
				rs = pstmt.executeQuery();

				if (rs.next()) {
					getVisitTotalCount = rs.getInt(1);
				}
				
				
			}catch(Exception ee) {
				ee.printStackTrace();			
			}finally {
					if(pstmt!=null)try {pstmt.close();}catch(SQLException ss) {}
					if(con!=null)try {con.close();}catch(SQLException ss) {}
					if(rs!=null)try {rs.close();}catch(SQLException ss) {}
			}return getVisitTotalCount;		
		}//전체 방문자 수 가져오기 끝
		
		//당일 방문자 수 가져오기
				public int getVisitTodayCount() {
					Connection con = null;
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					int getVisitTodayCount = 0;
					try {
						con = ConUtil.getConnection();			
						pstmt = con.prepareStatement("select count(*) from visit where substr(to_char(visit), 1, 9) = to_date(sysdate, 'yy/MM/dd')");
						rs = pstmt.executeQuery();

						if (rs.next()) {
							getVisitTodayCount = rs.getInt(1);
						}
						
						
					}catch(Exception ee) {
						ee.printStackTrace();			
					}finally {
							if(pstmt!=null)try {pstmt.close();}catch(SQLException ss) {}
							if(con!=null)try {con.close();}catch(SQLException ss) {}
							if(rs!=null)try {rs.close();}catch(SQLException ss) {}
					}return getVisitTodayCount;		
				}//당일 방문자 수 가져오기 끝
		
		
}
