package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.ConUtil;
import vo.BoardVO;

//메인 페이지 DAO 입니다.
public class MainDAO {
		// 싱글톤으로 선언한다. 실제로 DB에 연결하여 레코드의 트랜잭션을 수행하기 때문에
		// DAO클래스가 여러번 인스턴스화 하면 DB와 프로그램 간의 커넥션이 빈번하게 일어난다.
		// 따라서 싱글톤으로 구현한다. 싱글톤 : 해당 클래스의 인스턴스가 하나만 유지되도록 하는 방법.
		private static MainDAO instance;
		
		private MainDAO() {}
		
		public static MainDAO getInstance() {
			if(instance==null) {
				instance = new MainDAO();
			}
			return instance;
		}



// 메인페이지 총 인기글 모음
public List<BoardVO> getMainBestArticles(int start, int end) {
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	List<BoardVO> getMainBestArticles = null;
	
	try {
		con = ConUtil.getConnection();
		
		pstmt = con.prepareStatement(
				"select * from (select Rownum rnum, board_no, board_title, board_recommand from (select * from boards where board_category != 'BC01' order by board_recommand desc) )where rnum >= ? and rnum <=?"
		);
		pstmt.setInt(1, start);
		pstmt.setInt(2, end);
		
		rs = pstmt.executeQuery();
		
		if(rs.next()) {
			
			getMainBestArticles = new ArrayList<BoardVO>(end - start+1);
			do {
				BoardVO article = new BoardVO();
				
				article.setBoard_title(rs.getString("board_title"));
				article.setBoard_recommand(rs.getInt("board_recommand"));
				article.setBoard_no(rs.getInt("board_no"));
				getMainBestArticles.add(article);
				
			}while(rs.next());
		}
	}catch(Exception e) {
		e.printStackTrace();
	}finally {
		if(rs != null) try {rs.close();}catch(SQLException ss) {}
		if(pstmt != null) try {pstmt.close();}catch(SQLException ss) {}
		if(con != null) try {con.close();}catch(SQLException ss) {}
	}
	return getMainBestArticles;
}

//메인페이지 총 인기글 모음
public List<BoardVO> getMainNoticeArticles(int start, int end) {
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	List<BoardVO> getMainNoticeArticles = null;
	
	try {
		con = ConUtil.getConnection();
		
		pstmt = con.prepareStatement(
				"select * from (select Rownum rnum, board_no, board_title, member_nickname from (select * from boards where board_type = 'A00' order by board_regdate desc)"
				+ "left join member on board_writer = member_code)"
				+ "where rnum >= ? and rnum <=?"
		);
		pstmt.setInt(1, start);
		pstmt.setInt(2, end);
		
		rs = pstmt.executeQuery();
		
		if(rs.next()) {
			
			getMainNoticeArticles = new ArrayList<BoardVO>(end - start+1);
			do {
				BoardVO article = new BoardVO();

				article.setBoard_no(rs.getInt("board_no"));
				article.setBoard_title(rs.getString("board_title"));
				article.setBoard_writer(rs.getString("member_nickname"));
				getMainNoticeArticles.add(article);
				
			}while(rs.next());
		}
	}catch(Exception e) {
		e.printStackTrace();
	}finally {
		if(rs != null) try {rs.close();}catch(SQLException ss) {}
		if(pstmt != null) try {pstmt.close();}catch(SQLException ss) {}
		if(con != null) try {con.close();}catch(SQLException ss) {}
	}
	return getMainNoticeArticles;
}

}