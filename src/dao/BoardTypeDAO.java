package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.ConUtil;
import vo.BoardTypeVO;


public class BoardTypeDAO {
	private static BoardTypeDAO dao;
	
	private BoardTypeDAO() {}
	
	public static BoardTypeDAO getInstance() {
		if(dao==null) {
			dao = new BoardTypeDAO();
		}
		return dao;
	}
	
	
	//즐겨찾기 1,2,3순위 게시판
	   public List<BoardTypeVO> getBoardOneTwoThree(){
	      
	      Connection con = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      List<BoardTypeVO> boardTypeList = null;
	    
	      try {
	         con = ConUtil.getConnection();
	         pstmt = con.prepareStatement
	               ("select c.* from (select b.board_t_code,b.board_t_name,b.board_t_image,count(f.fav_user) as board_t_count, rank() over(order by count(f.fav_user) desc) as board_t_ranking from board_type b join favorite f on b.board_t_code=f.fav_board where not b.board_t_code='B00' group by b.board_t_code,b.board_t_name, b.board_t_image) c where c.board_t_ranking<=3"); 
	         rs = pstmt.executeQuery();
	       
	       if(rs.next()) {
	          
	          boardTypeList = new ArrayList<BoardTypeVO>();
	          do {
	             BoardTypeVO boardType = new BoardTypeVO();
	            
	            boardType.setBoard_t_code(rs.getString("board_t_code"));
	            boardType.setBoard_t_name(rs.getString("board_t_name"));
	            boardType.setBoard_t_image(rs.getString("board_t_image"));
	            boardType.setBoard_t_count(rs.getInt("board_t_count"));
	            //System.out.println(boardType.getBoard_t_code());
	            //System.out.println(boardType.getBoard_t_name());
	            
	            boardTypeList.add(boardType);
	            
	          }while(rs.next());
	       }
	    }catch(Exception e) {
	       e.printStackTrace();
	    }finally {
	       if(con!=null)try{con.close();}catch(SQLException e) {}
	       if(pstmt!=null)try {pstmt.close();}catch(SQLException e) {}
	       if(rs!=null)try {rs.close();}catch(SQLException e) {}
	    }
	    return boardTypeList;
	   }
	   //순위권 밖 게시판목록
	    public List<BoardTypeVO> getBoardRest(){
	         
	         Connection con = null;
	         PreparedStatement pstmt = null;
	         ResultSet rs = null;
	         List<BoardTypeVO> boardTypeList2 = null;
	       
	         try {
	            con = ConUtil.getConnection();
	            pstmt = con.prepareStatement
	                  ("select c.* from (select b.board_t_code,b.board_t_name,b.board_t_image,count(f.fav_user) as board_t_count, rank() over(order by count(f.fav_user) desc) as board_t_ranking from board_type b left join favorite f on b.board_t_code=f.fav_board where not b.board_t_code='B00' group by b.board_t_code,b.board_t_name, b.board_t_image) c where c.board_t_ranking>3 order by board_t_name");
	            rs = pstmt.executeQuery();
	          
	          if(rs.next()) {
	             
	             boardTypeList2 = new ArrayList<BoardTypeVO>();
	             do {
	                BoardTypeVO boardType = new BoardTypeVO();
	               
	               boardType.setBoard_t_code(rs.getString("board_t_code"));
	               boardType.setBoard_t_name(rs.getString("board_t_name"));
	               boardType.setBoard_t_image(rs.getString("board_t_image"));
	               boardType.setBoard_t_count(rs.getInt("board_t_count"));
	               boardTypeList2.add(boardType);
	               
	             }while(rs.next());
	          }
	       }catch(Exception e) {
	          e.printStackTrace();
	       }finally {
	          if(con!=null)try{con.close();}catch(SQLException e) {}
	          if(pstmt!=null)try {pstmt.close();}catch(SQLException e) {}
	          if(rs!=null)try {rs.close();}catch(SQLException e) {}
	       }
	       return boardTypeList2;
	    }
	 // 특정 게시판 정보 가져오기
	 public BoardTypeVO getBoardInfo(String board_t_code) {
		 
		 BoardTypeVO vo = null;
		 Connection con = null;
		 PreparedStatement pstmt = null;
		 ResultSet rs = null;
		 
		 try {
				con = ConUtil.getConnection();
				pstmt = con.prepareStatement
						("select board_t_code, board_t_name, board_t_image, board_t_desc, board_t_manager,board_t_youtuber,board_t_url, (select count(*) from favorite where fav_board=?) as count from board_type where board_t_code=?");
				pstmt.setString(1, board_t_code);
				pstmt.setString(2, board_t_code);
				rs = pstmt.executeQuery();
			 
			 if(rs.next()) {
				 
				 vo = new BoardTypeVO();
				 do {
					 
					 vo.setBoard_t_code(rs.getString("board_t_code"));
					 vo.setBoard_t_name(rs.getString("board_t_name"));
					 vo.setBoard_t_image(rs.getString("board_t_image"));
					 vo.setBoard_t_desc(rs.getString("board_t_desc"));
					 vo.setBoard_t_manager(rs.getString("board_t_manager"));
					 vo.setBoard_t_youtuber(rs.getString("board_t_youtuber"));
					 vo.setBoard_t_url(rs.getString("board_t_url"));
					 vo.setBoard_t_count(rs.getInt("count"));
					 
				 }while(rs.next());
			 }
		 }catch(Exception e) {
			 e.printStackTrace();
		 }finally {
			 if(con!=null)try{con.close();}catch(SQLException e) {}
			 if(pstmt!=null)try {pstmt.close();}catch(SQLException e) {}
			 if(rs!=null)try {rs.close();}catch(SQLException e) {}
		 }	
		 
		 
		 
		 return vo;
	 }
	 
	 
	 
	 // 즐겨찾기 리스트 뿌리기
	 public ArrayList<BoardTypeVO> getFavList(String member_code){
		 
		 ArrayList<BoardTypeVO> favList = null;
		 Connection con = null;
		 PreparedStatement pstmt = null;
		 ResultSet rs = null;
			
		 try {
				con = ConUtil.getConnection();
				pstmt = con.prepareStatement
						("select fav_board from favorite where fav_user=?");
				pstmt.setString(1, member_code);
				rs = pstmt.executeQuery();
			 
			 if(rs.next()) {
				 
				 favList = new ArrayList<BoardTypeVO>();
				 do {
					 String fav_board_code = rs.getString(1);
					 BoardTypeVO boardType = getBoardInfo(fav_board_code);
					 favList.add(boardType);
					
					
				 }while(rs.next());
			 }
		 }catch(Exception e) {
			 e.printStackTrace();
		 }finally {
			 if(con!=null)try{con.close();}catch(SQLException e) {}
			 if(pstmt!=null)try {pstmt.close();}catch(SQLException e) {}
			 if(rs!=null)try {rs.close();}catch(SQLException e) {}
		 }	
			
		return favList;
	 }
	 
	 // 즐겨찾기 삭제 메소드
	 public int deleteFav(String member_code, String board_t_code) {
		 int chk = 0;
		 Connection con = null;
	       PreparedStatement pstmt = null;
	       ResultSet rs = null;
	         
	       try {
	            con = ConUtil.getConnection();
	            pstmt = con.prepareStatement
	                  ("delete " + 
	                  		"from " + 
	                  		"favorite " + 
	                  		"where fav_board = ? and fav_user= ?");
	            pstmt.setString(1, board_t_code);
	            pstmt.setString(2, member_code);
	            rs = pstmt.executeQuery();
	          
	          
	          
	       }catch(Exception e) {
	          e.printStackTrace();
	       }finally {
	          if(pstmt!=null)try {pstmt.close();}catch(SQLException e) {}
	          if(con!=null)try{con.close();}catch(SQLException e) {}
	       }  	       
		 
		 
		 return chk;
	 }
	 
	 // 로그인한 유저가 관리하는 게시판 리스트
	    public ArrayList<BoardTypeVO> getManagerList(String member_code) {
	       
	       ArrayList<BoardTypeVO> managerList = null;
	       Connection con = null;
	       PreparedStatement pstmt = null;
	       ResultSet rs = null;
	         
	       try {
	            con = ConUtil.getConnection();
	            pstmt = con.prepareStatement
	                  ("select board_t_code from board_type where board_t_manager=?");
	            pstmt.setString(1, member_code);
	            rs = pstmt.executeQuery();
	          
	          if(rs.next()) {
	             
	             managerList = new ArrayList<BoardTypeVO>();
	             do {
	                //코드얻어서
	                String board_t_code = rs.getString("board_t_code");
	                //각 게시판 정보얻고
	                BoardTypeVO vo = getBoardInfo(board_t_code);
	                //리스트에 넣기
	                //System.out.println(vo.getBoard_t_code());
	                managerList.add(vo);
	                
	               
	             }while(rs.next());
	          }
	       }catch(Exception e) {
	          e.printStackTrace();
	       }finally {
	          if(con!=null)try{con.close();}catch(SQLException e) {}
	          if(pstmt!=null)try {pstmt.close();}catch(SQLException e) {}
	          if(rs!=null)try {rs.close();}catch(SQLException e) {}
	       }  	       
	       return managerList;
	    }
	    
	  //4.23 이후 추가------
		//게시판 수 가져오기
		public int countBoardType() {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			int countBoardType = 0;
			try {
				con = ConUtil.getConnection();
				
				
				pstmt = con.prepareStatement("select count(*) from board_type");
				rs = pstmt.executeQuery();
				
				if (rs.next()) {
					countBoardType = rs.getInt(1);
				}
				
				
			} catch(Exception ee) {
				ee.printStackTrace();
			}finally {
				if(rs != null) try {rs.close();}catch(SQLException ss) {}
				if(pstmt != null) try {pstmt.close();}catch(SQLException ss) {}
				if(con != null) try {con.close();}catch(SQLException ss) {}
			}		
			return countBoardType;
		}	//게시판 수 가져오기 끝
	 
}
