package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.ConUtil;
import vo.BoardVO;
import vo.UrlVO;

public class UrlDAO {

	private static UrlDAO instance;

	private UrlDAO() {
	}

	public static UrlDAO getInstance() {
		if (instance == null) {
			instance = new UrlDAO();
		}
		return instance;
	}
	
	//URL 등록
	public void insertUrl(UrlVO uv) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		String sql = "";
		try {
			con = ConUtil.getConnection();
						
			sql = "insert into url values(SEQ_URL.nextval,?,?)";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, uv.getUrl_board_no());
			pstmt.setString(2, uv.getUrl_path());

			pstmt.executeUpdate();
			
		}catch(Exception ee) {
			ee.printStackTrace();			
		}finally {
				if(pstmt!=null)try {pstmt.close();}catch(SQLException ss) {}
				if(con!=null)try {con.close();}catch(SQLException ss) {}
		}		
	}//URL 등록 끝
	
	// 글번호 지정 url 가져오기
	public String getUrl(int board_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String url = null;
		try {
			con = ConUtil.getConnection();			
			pstmt = con.prepareStatement("select url_path from url where url_board_no=(select board_no from boards where board_no = "+board_no+")");
			rs = pstmt.executeQuery();

			if (rs.next()) {
				url = rs.getString(1);
			}
			
			
		}catch(Exception ee) {
			ee.printStackTrace();			
		}finally {
				if(pstmt!=null)try {pstmt.close();}catch(SQLException ss) {}
				if(con!=null)try {con.close();}catch(SQLException ss) {}
		}return url;		
	}
	
	//신규 추천곡의 url 가져오기
	public String newRecommandUrl() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String newRecommandUrl = null;
		try {
			con = ConUtil.getConnection();			
			pstmt = con.prepareStatement("select url_path from url where url_code=(select max(url_code) from url)");
			rs = pstmt.executeQuery();

			if (rs.next()) {
				newRecommandUrl = rs.getString(1);
			}
			
			
		}catch(Exception ee) {
			ee.printStackTrace();			
		}finally {
				if(pstmt!=null)try {pstmt.close();}catch(SQLException ss) {}
				if(con!=null)try {con.close();}catch(SQLException ss) {}
		}return newRecommandUrl;		
	}
	//신규 추천곡의 url 가져오기 끝

	
	
	//신규 추천곡의 글번호 가져오기
	public int newRecommandBoardNo() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int newRecommandBoardNo = 0;
		try {
			con = ConUtil.getConnection();			
			pstmt = con.prepareStatement("select url_board_no from url where url_code=(select max(url_code) from url)");
			rs = pstmt.executeQuery();

			if (rs.next()) {
				newRecommandBoardNo = rs.getInt(1);
			}
			
			
		}catch(Exception ee) {
			ee.printStackTrace();			
		}finally {
				if(pstmt!=null)try {pstmt.close();}catch(SQLException ss) {}
				if(con!=null)try {con.close();}catch(SQLException ss) {}
		}return newRecommandBoardNo;		
	}
	//신규 추천곡의 글번호 가져오기 끝
	
	
	//인기 추천곡의 url 가져오기
		public String favRecommandUrl() {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String favRecommandUrl = null;
			try {
				con = ConUtil.getConnection();			
				pstmt = con.prepareStatement("select url_path from url where url_board_no=(select board_no from boards  where  board_type = 'B00' and board_recommand = (select max(board_recommand) from boards where  board_type = 'B00'))");
				rs = pstmt.executeQuery();

				if (rs.next()) {
					favRecommandUrl = rs.getString(1);
				}
				
				
			}catch(Exception ee) {
				ee.printStackTrace();			
			}finally {
					if(pstmt!=null)try {pstmt.close();}catch(SQLException ss) {}
					if(con!=null)try {con.close();}catch(SQLException ss) {}
			}return favRecommandUrl;		
		}
		//인기 추천곡의 url 가져오기 끝
	
		//인기 추천곡의 글번호 가져오기
		public int favRecommandBoardNo() {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			int favRecommandBoardNo = 0;
			try {
				con = ConUtil.getConnection();			
				pstmt = con.prepareStatement("select board_no from boards  where  board_type = 'B00' and board_recommand = (select max(board_recommand) from boards where  board_type = 'B00')");
				rs = pstmt.executeQuery();

				if (rs.next()) {
					favRecommandBoardNo = rs.getInt(1);
				}
				
				
			}catch(Exception ee) {
				ee.printStackTrace();			
			}finally {
					if(pstmt!=null)try {pstmt.close();}catch(SQLException ss) {}
					if(con!=null)try {con.close();}catch(SQLException ss) {}
			}return favRecommandBoardNo;		
		}
		//인기 추천곡의 글번호 가져오기 끝
		
		//--------------4.23추가
		//url 수정
		public void updateUrl(UrlVO uv) {
			Connection con = null;
			PreparedStatement pstmt = null;
			
			String sql = "";
			try {
				con = ConUtil.getConnection();
							
				sql = "update url set url_path=? where url_board_no=?";
				
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, uv.getUrl_path());
				pstmt.setInt(2, uv.getUrl_board_no());

				pstmt.executeUpdate();
				
			}catch(Exception ee) {
				ee.printStackTrace();			
			}finally {
				if(pstmt!=null)try {pstmt.close();}catch(SQLException ss) {}
				if(con!=null)try {con.close();}catch(SQLException ss) {}
			}		
		}//url 수정 끝
	
}
