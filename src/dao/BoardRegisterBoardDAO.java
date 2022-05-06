package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.ConUtil;
import vo.BoardRegisterBoardCommentVO;
import vo.BoardRegisterBoardVO;

public class BoardRegisterBoardDAO {
	private static BoardRegisterBoardDAO dao;
	
	private BoardRegisterBoardDAO() {}
	
	public static BoardRegisterBoardDAO getInstance() {
		if(dao==null) {
			dao = new BoardRegisterBoardDAO();
		}
		return dao;
	}
	
	
	
	//리스트 갯수 구하는 메소드
	public int getCount() {
		int res = 0;
		Connection con = null;
		PreparedStatement pstmt  = null;
		ResultSet rs = null;
		
		try {
			
			con = ConUtil.getConnection();
			pstmt = con.prepareStatement("select count(*) from board_register");
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				res = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(con!=null)try{con.close();}catch(SQLException e) {}
			if(pstmt!=null)try {pstmt.close();}catch(SQLException e) {}
			if(rs!=null)try {rs.close();}catch(SQLException e) {}
		}
		
		return res;
	}
	
	
	//검색 시 리스트 갯수 구하는 메소드
	public int getCount(String tag, String kwd) {
		int res = 0;
		Connection con = null;
		PreparedStatement pstmt  = null;
		ResultSet rs = null;
		
		//검색값 없을 경우 처리
		if (tag == null) {
			tag="member_id";
		}
		if (kwd == null) {
			kwd="";
		}
		
		try {
			
			con = ConUtil.getConnection();
			
			String sql  = "select count(*) from board_register br left join member m on br.board_r_register = m.member_code where " + tag +  " like '%" + kwd +"%'";
			
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				res = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(con!=null)try{con.close();}catch(SQLException e) {}
			if(pstmt!=null)try {pstmt.close();}catch(SQLException e) {}
			if(rs!=null)try {rs.close();}catch(SQLException e) {}
		}
		
		return res;
	}
	
	
	// 게시글 리스트 출력 메소드
	public ArrayList<BoardRegisterBoardVO> getRegisterBoardList(int startRow, int endRow, String tag, String kwd){
		ArrayList<BoardRegisterBoardVO> arrList = new ArrayList<BoardRegisterBoardVO>();
		Connection con = null;
		PreparedStatement pstmt  = null;
		ResultSet rs = null;
		
		//검색값 없을 경우 처리
		if (tag == null) {
			tag="member_id";
		}
		if (kwd == null) {
			kwd="";
		}
		
		
		
		try {
			
			con = ConUtil.getConnection();
			
			String sql  = "select *  " + 
					"from " + 
					"( " + 
					"select rownum rnum, board_r_no, board_r_writer_code, board_r_writer , board_r_title, board_r_content, board_r_announce, board_r_readcount, board_r_regdate, board_r_recommand, board_r_approved, board_r_profile_desc, board_r_profile_image, board_r_url, board_r_boardmanager, board_r_youtuber " + 
					"from " + 
					"( " + 
					"    select  board_r_no, board_r_register as board_r_writer_code, member_id as board_r_writer , board_r_title, board_r_content, board_r_announce, board_r_readcount, board_r_regdate, board_r_recommand, board_r_approved, board_r_profile_desc, board_r_profile_image, board_r_url, board_r_boardmanager, board_r_youtuber  " + 
					"    from " + 
					"    board_register br left join member m " + 
					"    on br.board_r_register = m.member_code " + 
					"    where " + tag +" like '%' || ? || '%' and  board_r_announce != 1 " + 
					"    order by board_r_no desc " + 
					")  " + 
					") where rnum >=? and rnum <=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kwd);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				BoardRegisterBoardVO vo = new BoardRegisterBoardVO();
				
				vo.setRnum(rs.getInt("rnum"));
				vo.setBoard_r_no(rs.getInt("board_r_no"));
				vo.setBoard_r_writer_code(rs.getString("board_r_writer_code"));
				vo.setBoard_r_writer(rs.getString("board_r_writer"));
				vo.setBoard_r_title(rs.getString("board_r_title"));
				vo.setBoard_r_content(rs.getString("board_r_content"));
				vo.setBoard_r_announce(rs.getInt("board_r_announce"));
				vo.setBoard_r_readcount(rs.getInt("board_r_readcount"));
				vo.setBoard_r_regdate(rs.getDate("board_r_regdate"));
				vo.setBoard_r_recommand(rs.getInt("board_r_recommand"));
				vo.setBoard_r_approved(rs.getInt("board_r_approved"));
				vo.setBoard_r_profile_desc(rs.getString("board_r_profile_desc"));
				vo.setBoard_r_profile_image(rs.getString("board_r_profile_image"));
				vo.setBoard_r_url(rs.getString("board_r_url"));
				vo.setBoard_r_boardmanager(rs.getString("board_r_boardmanager"));
				vo.setBoard_r_youtuber(rs.getString("board_r_youtuber"));
				
				
				arrList.add(vo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(con!=null)try{con.close();}catch(SQLException e) {}
			if(pstmt!=null)try {pstmt.close();}catch(SQLException e) {}
			if(rs!=null)try {rs.close();}catch(SQLException e) {}
		}
		
		
		return arrList;
	}
	
	
	// 게시글 리스트 출력 메소드
		public ArrayList<BoardRegisterBoardVO> getRegisterBoardList(){
			ArrayList<BoardRegisterBoardVO> arrList = new ArrayList<BoardRegisterBoardVO>();
			Connection con = null;
			PreparedStatement pstmt  = null;
			ResultSet rs = null;
			
		
			
			
			try {
				
				con = ConUtil.getConnection();
				
				String sql  = "select *  " + 
						"from " + 
						"( " + 
						"select rownum rnum, board_r_no, board_r_writer_code, board_r_writer , board_r_title, board_r_content, board_r_announce, board_r_readcount, board_r_regdate, board_r_recommand, board_r_approved, board_r_profile_desc, board_r_profile_image, board_r_url, board_r_boardmanager, board_r_youtuber " + 
						"from " + 
						"( " + 
						"    select  board_r_no, board_r_register as board_r_writer_code, member_id as board_r_writer , board_r_title, board_r_content, board_r_announce, board_r_readcount, board_r_regdate, board_r_recommand, board_r_approved, board_r_profile_desc, board_r_profile_image, board_r_url, board_r_boardmanager, board_r_youtuber  " + 
						"    from " + 
						"    board_register br left join member m " + 
						"    on br.board_r_register = m.member_code " + 
						"    where  board_r_announce != 1 " + 
						"    order by board_r_no desc " + 
						")  " + 
						") ";
				
				pstmt = con.prepareStatement(sql);
				
				
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					BoardRegisterBoardVO vo = new BoardRegisterBoardVO();
					
					vo.setRnum(rs.getInt("rnum"));
					vo.setBoard_r_no(rs.getInt("board_r_no"));
					vo.setBoard_r_writer_code(rs.getString("board_r_writer_code"));
					vo.setBoard_r_writer(rs.getString("board_r_writer"));
					vo.setBoard_r_title(rs.getString("board_r_title"));
					vo.setBoard_r_content(rs.getString("board_r_content"));
					vo.setBoard_r_announce(rs.getInt("board_r_announce"));
					vo.setBoard_r_readcount(rs.getInt("board_r_readcount"));
					vo.setBoard_r_regdate(rs.getDate("board_r_regdate"));
					vo.setBoard_r_recommand(rs.getInt("board_r_recommand"));
					vo.setBoard_r_approved(rs.getInt("board_r_approved"));
					vo.setBoard_r_profile_desc(rs.getString("board_r_profile_desc"));
					vo.setBoard_r_profile_image(rs.getString("board_r_profile_image"));
					vo.setBoard_r_url(rs.getString("board_r_url"));
					vo.setBoard_r_boardmanager(rs.getString("board_r_boardmanager"));
					vo.setBoard_r_youtuber(rs.getString("board_r_youtuber"));
					
					
					arrList.add(vo);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(con!=null)try{con.close();}catch(SQLException e) {}
				if(pstmt!=null)try {pstmt.close();}catch(SQLException e) {}
				if(rs!=null)try {rs.close();}catch(SQLException e) {}
			}
			
			
			return arrList;
		}
	
	
	
	
	// 공지글 리스트 가져오는 메소드
	public ArrayList<BoardRegisterBoardVO> getAnnounceList(){
		ArrayList<BoardRegisterBoardVO> announceList = new ArrayList<BoardRegisterBoardVO>();
		Connection con = null;
		PreparedStatement pstmt  = null;
		ResultSet rs = null;
		
		try {
			con = ConUtil.getConnection();
			
			String sql = "select  board_r_no, board_r_register as board_r_writer_code, member_id as board_r_writer , board_r_title, board_r_content, board_r_announce, board_r_readcount, board_r_regdate, board_r_recommand, board_r_approved, board_r_profile_desc, board_r_profile_image, board_r_url, board_r_boardmanager, board_r_youtuber  " + 
					"from " + 
					"board_register br left join member m " + 
					"on br.board_r_register = m.member_code " + 
					"where board_r_announce = 1";
			
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			
			while (rs.next()) {
				BoardRegisterBoardVO vo = new BoardRegisterBoardVO();
				
				vo.setBoard_r_no(rs.getInt("board_r_no"));
				vo.setBoard_r_writer_code(rs.getString("board_r_writer_code"));
				vo.setBoard_r_writer(rs.getString("board_r_writer"));
				vo.setBoard_r_title(rs.getString("board_r_title"));
				vo.setBoard_r_content(rs.getString("board_r_content"));
				vo.setBoard_r_announce(rs.getInt("board_r_announce"));
				vo.setBoard_r_readcount(rs.getInt("board_r_readcount"));
				vo.setBoard_r_regdate(rs.getDate("board_r_regdate"));
				vo.setBoard_r_recommand(rs.getInt("board_r_recommand"));
				vo.setBoard_r_approved(rs.getInt("board_r_approved"));
				vo.setBoard_r_profile_desc(rs.getString("board_r_profile_desc"));
				vo.setBoard_r_profile_image(rs.getString("board_r_profile_image"));
				vo.setBoard_r_url(rs.getString("board_r_url"));
				vo.setBoard_r_boardmanager(rs.getString("board_r_boardmanager"));
				vo.setBoard_r_youtuber(rs.getString("board_r_youtuber"));
				
				
				announceList.add(vo);
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(con!=null)try{con.close();}catch(SQLException e) {}
			if(pstmt!=null)try {pstmt.close();}catch(SQLException e) {}
			if(rs!=null)try {rs.close();}catch(SQLException e) {}
		}
		
		
		
		return announceList;
	}
	
	// 글 작성 메소드(유튜버가 자신의 게시판의 유튜버 인증은 나중에 따로 신청해야함)
	public int insertRegister(BoardRegisterBoardVO vo) {
		int chk = 0;
		
		
		
		Connection con = null;
		PreparedStatement pstmt  = null;
		
		try {
			con = ConUtil.getConnection();
			String sql = "insert into board_register values"
+ "(seq_board_r.nextval, ?, ?, ?, ?, 0, sysdate, 0, 0, ?, ?, ?, ?, null)";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getBoard_r_register());
			pstmt.setString(2, vo.getBoard_r_title());
			pstmt.setString(3, vo.getBoard_r_content());
			pstmt.setInt(4, vo.getBoard_r_announce());
			pstmt.setString(5, vo.getBoard_r_profile_desc());
			pstmt.setString(6, vo.getBoard_r_profile_image());
			pstmt.setString(7, vo.getBoard_r_url());
			pstmt.setString(8, vo.getBoard_r_register());
			
			chk = pstmt.executeUpdate();
	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(con!=null)try{con.close();}catch(SQLException e) {}
			if(pstmt!=null)try {pstmt.close();}catch(SQLException e) {}
		}
		
		
		return chk;
	}
	
	
	//특정 게시글 가져오기(내용보기)
	public BoardRegisterBoardVO getContent(String board_r_no) {
		
		Connection con = null;
		PreparedStatement pstmt  = null;
		ResultSet rs = null;
		BoardRegisterBoardVO vo = new BoardRegisterBoardVO();
		
		
		try {
			
			con = ConUtil.getConnection();
			
			
			String sql  = "select *  " + 
					"from " + 
					"( " + 
					"select rownum rnum, board_r_no, board_r_writer_code, board_r_writer , board_r_title, board_r_content, board_r_announce, board_r_readcount, board_r_regdate, board_r_recommand, board_r_approved, board_r_profile_desc, board_r_profile_image, board_r_url, board_r_boardmanager, board_r_youtuber " + 
					"from " + 
					"( " + 
					"    select  board_r_no, board_r_register as board_r_writer_code, member_id as board_r_writer , board_r_title, board_r_content, board_r_announce, board_r_readcount, board_r_regdate, board_r_recommand, board_r_approved, board_r_profile_desc, board_r_profile_image, board_r_url, board_r_boardmanager, board_r_youtuber  " + 
					"    from " + 
					"    board_register br left join member m " + 
					"    on br.board_r_register = m.member_code " + 
				
					")  " + 
					") where board_r_no = ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, board_r_no);
			
			rs = pstmt.executeQuery();
			
			
			
			if (rs.next()) {
				vo.setRnum(rs.getInt("rnum"));
				vo.setBoard_r_no(rs.getInt("board_r_no"));
				vo.setBoard_r_writer_code(rs.getString("board_r_writer_code"));
				vo.setBoard_r_writer(rs.getString("board_r_writer"));
				vo.setBoard_r_title(rs.getString("board_r_title"));
				vo.setBoard_r_content(rs.getString("board_r_content"));
				vo.setBoard_r_announce(rs.getInt("board_r_announce"));
				vo.setBoard_r_readcount(rs.getInt("board_r_readcount"));
				vo.setBoard_r_regdate(rs.getDate("board_r_regdate"));
				vo.setBoard_r_recommand(rs.getInt("board_r_recommand"));
				vo.setBoard_r_approved(rs.getInt("board_r_approved"));
				vo.setBoard_r_profile_desc(rs.getString("board_r_profile_desc"));
				vo.setBoard_r_profile_image(rs.getString("board_r_profile_image"));
				vo.setBoard_r_url(rs.getString("board_r_url"));
				vo.setBoard_r_boardmanager(rs.getString("board_r_boardmanager"));
				vo.setBoard_r_youtuber(rs.getString("board_r_youtuber"));
			}
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(con!=null)try{con.close();}catch(SQLException e) {}
			if(pstmt!=null)try {pstmt.close();}catch(SQLException e) {}
			if(rs!=null)try {rs.close();}catch(SQLException e) {}
		}
		
		
		return vo;
	}
	
	
	//0차 댓글 리스트 가져오기
	public ArrayList<BoardRegisterBoardCommentVO> getCommentsList0(String board_r_no, int startRow, int endRow){
		Connection con = null;
		PreparedStatement pstmt  = null;
		ResultSet rs = null;
		ArrayList<BoardRegisterBoardCommentVO> commentsList = new ArrayList<BoardRegisterBoardCommentVO>();
		
		
		try {
			
			con = ConUtil.getConnection();
			
			
			String sql  = "select * " + 
					"from " + 
					"( " + 
					"    select rownum rnum, register_b_c_code  ,register_b_c_member_code, member_id as register_b_c_member_id, register_b_c_board_no, register_b_c_content, register_b_c_regdate, register_b_c_ref, register_b_c_depth,register_b_c_step " + 
					"    from " + 
					"    register_board_comments rc left join member m " + 
					"    on rc.register_b_c_member_code = m.member_code " + 
					"    where register_b_c_board_no=? and register_b_c_depth=0 " + 
					"    order by register_b_c_code " + 
					") " + 
					"where rnum >=? and rnum <=?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, Integer.parseInt(board_r_no));
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			
			rs = pstmt.executeQuery();
			
			int repetitionCount = 1;
			
			while (rs.next()) {
				BoardRegisterBoardCommentVO vo = new BoardRegisterBoardCommentVO();
				
				
				
				
				vo.setRegister_b_c_board_no(rs.getInt("register_b_c_board_no"));
				vo.setRegister_b_c_code(rs.getInt("register_b_c_code"));
				vo.setRegister_b_c_content(rs.getString("register_b_c_content"));
				vo.setRegister_b_c_depth(rs.getInt("register_b_c_depth"));
				vo.setRegister_b_c_member_code(rs.getString("register_b_c_member_code"));
				vo.setRegister_b_c_member_id(rs.getString("register_b_c_member_id"));
				vo.setRegister_b_c_ref(rs.getInt("register_b_c_ref"));
				vo.setRegister_b_c_regdate(rs.getDate("register_b_c_regdate"));
				vo.setRegister_b_c_step(rs.getInt("register_b_c_step"));
				vo.setRnum(rs.getInt("rnum"));
				
				
				
				
				commentsList.add(vo);
			}
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(con!=null)try{con.close();}catch(SQLException e) {}
			if(pstmt!=null)try {pstmt.close();}catch(SQLException e) {}
			if(rs!=null)try {rs.close();}catch(SQLException e) {}
		}
		
		
		return commentsList;
	}
	
	
	//1차 댓글 리스트 가져오기
		public ArrayList<BoardRegisterBoardCommentVO> getCommentsList1(String board_r_no){
			Connection con = null;
			PreparedStatement pstmt  = null;
			ResultSet rs = null;
			ArrayList<BoardRegisterBoardCommentVO> commentsList = new ArrayList<BoardRegisterBoardCommentVO>();
			
			
			try {
				
				con = ConUtil.getConnection();
				
				
				String sql  = "select * " + 
						"from " + 
						"( " + 
						"    select rownum rnum, register_b_c_code  ,register_b_c_member_code, member_id as register_b_c_member_id, register_b_c_board_no, register_b_c_content, register_b_c_regdate, register_b_c_ref, register_b_c_depth, register_b_c_step " + 
						"    from " + 
						"    register_board_comments rc left join member m " + 
						"    on rc.register_b_c_member_code = m.member_code " + 
						"    where register_b_c_board_no=? and register_b_c_depth=1 " + 
						"    order by register_b_c_code " + 
						") " ;
				
				
				pstmt = con.prepareStatement(sql);
				
				pstmt.setInt(1, Integer.parseInt(board_r_no));
			
				
				
				rs = pstmt.executeQuery();
				
				
				
				while (rs.next()) {
					BoardRegisterBoardCommentVO vo = new BoardRegisterBoardCommentVO();
					
					vo.setRegister_b_c_board_no(rs.getInt("register_b_c_board_no"));
					vo.setRegister_b_c_code(rs.getInt("register_b_c_code"));
					vo.setRegister_b_c_content(rs.getString("register_b_c_content"));
					vo.setRegister_b_c_depth(rs.getInt("register_b_c_depth"));
					vo.setRegister_b_c_member_code(rs.getString("register_b_c_member_code"));
					vo.setRegister_b_c_member_id(rs.getString("register_b_c_member_id"));
					vo.setRegister_b_c_ref(rs.getInt("register_b_c_ref"));
					vo.setRegister_b_c_regdate(rs.getDate("register_b_c_regdate"));
					vo.setRegister_b_c_step(rs.getInt("register_b_c_step"));
					vo.setRnum(rs.getInt("rnum"));
					
					
					
					
					commentsList.add(vo);
				}
				
				
				
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(con!=null)try{con.close();}catch(SQLException e) {}
				if(pstmt!=null)try {pstmt.close();}catch(SQLException e) {}
				if(rs!=null)try {rs.close();}catch(SQLException e) {}
			}
			
			
			return commentsList;
		}
	
	
	
		
		//조회수 증가
		public int viewCountIncrease(String board_r_no) {
			int chk = 0;
			Connection con = null;
			PreparedStatement pstmt  = null;
			
			try {
				con = ConUtil.getConnection();
				String sql = "update board_register set board_r_readcount = board_r_readcount+1 where board_r_no = ?";
				
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, board_r_no);
				
				chk = pstmt.executeUpdate();
		
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(con!=null)try{con.close();}catch(SQLException e) {}
				if(pstmt!=null)try {pstmt.close();}catch(SQLException e) {}
			}
			
			
			
			return chk;
		}
		
		
		// 댓글 작성(ref가 있는지 없는지 분기 후 처리)
		public int insertComment(BoardRegisterBoardCommentVO vo) {
			int chk = 0;
			Connection con = null;
			PreparedStatement pstmt  = null;
			
			try {
				con = ConUtil.getConnection();
				
				
				String sql = "";
				
				//대댓글이 아닌 경우
				if (vo.getRegister_b_c_ref() == 0) {
					sql = "insert into register_board_comments values " + 
							"(seq_register_b_comments.nextval, ?, ?, ?, sysdate, null, ?, null)";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, vo.getRegister_b_c_member_code());
					pstmt.setInt(2, vo.getRegister_b_c_board_no());
					pstmt.setString(3, vo.getRegister_b_c_content());
					pstmt.setInt(4, vo.getRegister_b_c_depth());
					
					
				}else{//대댓글인 경우
					sql = "insert into register_board_comments values " + 
							"(seq_register_b_comments.nextval, ?, ?, ?, sysdate, ?, ?, null)";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, vo.getRegister_b_c_member_code());
					pstmt.setInt(2, vo.getRegister_b_c_board_no());
					pstmt.setString(3, vo.getRegister_b_c_content());
					pstmt.setInt(4, vo.getRegister_b_c_ref());
					pstmt.setInt(5, vo.getRegister_b_c_depth());
					
					
				}
				
				
				
				
				
				
				
				
				chk = pstmt.executeUpdate();
		
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(con!=null)try{con.close();}catch(SQLException e) {}
				if(pstmt!=null)try {pstmt.close();}catch(SQLException e) {}
			}
			
			
			
			return chk;
			
			
			
		}
		
		//
		public int getCountComment(String board_r_no) {
			int count = -1;
			Connection con = null;
			PreparedStatement pstmt  = null;
			ResultSet rs = null;
			
			
			try {
				
				con = ConUtil.getConnection();
				
				
				String sql  = "select count(*)  " + 
						"from " + 
						"register_board_comments " + 
						"where register_b_c_board_no=? and register_b_c_depth=0";
				
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, board_r_no);
				
				rs = pstmt.executeQuery();
				
				
				
				if (rs.next()) {
					count = rs.getInt(1);
				}
				
				
				
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(con!=null)try{con.close();}catch(SQLException e) {}
				if(pstmt!=null)try {pstmt.close();}catch(SQLException e) {}
				if(rs!=null)try {rs.close();}catch(SQLException e) {}
			}
			
			
			return count;
		}
		
		
		
		//추천수 증가(중복체크 후, 넣어도 되면 업데이트)
		public int increaseRecc(String member_code, String board_r_no) {
			int dupChk = -1;
			int chk = -1;
			Connection con = null;
			PreparedStatement pstmt  = null;
			
			try {
				con = ConUtil.getConnection();
				
				
				String sql = "INSERT INTO REGISTER_RECCOMEND_DUPLICATE_CHECK VALUES (SEQ_REGISTER_COMMENT_DUPLECHECK.NEXTVAL, ?, ?)";
				
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, member_code);
				pstmt.setString(2, board_r_no);
				
				//중복 아닐 시 dupChk = 1 , 중복일 시 dupChk = -1 그대로 
				dupChk = pstmt.executeUpdate();
				
				//중복이 아닌경우 추천수 1증가
				if (dupChk != -1) {
					sql = "update board_register set board_r_recommand = board_r_recommand+1 where board_r_no = ?";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, board_r_no);
					chk = pstmt.executeUpdate();
				}
		
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(con!=null)try{con.close();}catch(SQLException e) {}
				if(pstmt!=null)try {pstmt.close();}catch(SQLException e) {}
			}
			
			
			return chk;
		}
		
		
		// 글 삭제
		public int deleteContent(String board_r_no) {
			int chk = -1;
			
			Connection con = null;
			PreparedStatement pstmt  = null;
			
			try {
				con = ConUtil.getConnection();
				
				
				String sql = "delete from board_register where board_r_no = ?";
				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, board_r_no);
				
				chk = pstmt.executeUpdate();
		
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(con!=null)try{con.close();}catch(SQLException e) {}
				if(pstmt!=null)try {pstmt.close();}catch(SQLException e) {}
			}
			
			
			
			
			return chk;
		}
		
		
		
		//글 수정
		public int modifyContent(BoardRegisterBoardVO vo) {
			int chk = -1;
			
			Connection con = null;
			PreparedStatement pstmt  = null;
			
			try {
				con = ConUtil.getConnection();
				
				
				String sql = "update board_register " + 
						"set board_r_title = ?, board_r_content=?, board_r_announce = ?, board_r_profile_desc=?, board_r_profile_image=?, board_r_url=? " + 
						"where board_r_no= ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, vo.getBoard_r_title());
				pstmt.setString(2, vo.getBoard_r_content());
				pstmt.setInt(3, vo.getBoard_r_announce());
				pstmt.setString(4, vo.getBoard_r_profile_desc());
				pstmt.setString(5, vo.getBoard_r_profile_image());
				pstmt.setString(6, vo.getBoard_r_url());
				pstmt.setInt(7, vo.getBoard_r_no());
				chk = pstmt.executeUpdate();
		
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(con!=null)try{con.close();}catch(SQLException e) {}
				if(pstmt!=null)try {pstmt.close();}catch(SQLException e) {}
			}
			
			
			
			
			return chk;
		}
		
		// 댓글 삭제
		public int commentDelete(String register_b_c_code) {
			int chk = -1;
			
			Connection con = null;
			PreparedStatement pstmt  = null;
			
			try {
				con = ConUtil.getConnection();
				
				//일단 삭제를 해봄
				String sql = "delete from register_board_comments where register_b_c_code=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, register_b_c_code);
				chk = pstmt.executeUpdate();
				
				
				
				//삭제 실패했을경우
				if (chk == -1) {
					//register_b_c_step 가 null이 아닐때 삭제된 게시글이라고 출력
					sql = "update register_board_comments " + 
							"set register_b_c_step = 1"+ 
							"where register_b_c_code = ?";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, register_b_c_code);
					
					
					chk = pstmt.executeUpdate();
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(con!=null)try{con.close();}catch(SQLException e) {}
				if(pstmt!=null)try {pstmt.close();}catch(SQLException e) {}
			}
			
			
			return chk;
		}
		
		
		
		//게시판 신청 승인 각하 처리(업데이트   1승인 2각하)
		public int AccDecBoard(String board_r_no, String board_r_approved) {
			int chk = -1;
			
			Connection con = null;
			PreparedStatement pstmt  = null;
			
			try {
				con = ConUtil.getConnection();
				
				//승인 미승인 업데이트
				String sql = "update board_register " + 
						"set board_r_approved = ? " + 
						"where board_r_no = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, board_r_approved);
				pstmt.setString(2, board_r_no);
				chk = pstmt.executeUpdate();
				
				
				
				
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(con!=null)try{con.close();}catch(SQLException e) {}
				if(pstmt!=null)try {pstmt.close();}catch(SQLException e) {}
			}
			
			
			return chk;
			
			
		}
		
		
		//게시판 신청 승인 시 게시판 추가
		public int addBoardType(BoardRegisterBoardVO vo) {
			int chk = -1;
			
			Connection con = null;
			PreparedStatement pstmt  = null;
			
			try {
				con = ConUtil.getConnection();
				
				//승인 시 게시판 추가
				String sql = "insert into board_type(board_t_code, board_t_name, board_t_image, board_t_desc, board_t_url, board_t_manager, board_t_youtuber) " + 
						"values('B'||SEQ_BOARD_T.nextval, ?, ?, ?, ?, ?, ?)";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, vo.getBoard_r_title());
				pstmt.setString(2, vo.getBoard_r_profile_image());
				pstmt.setString(3, vo.getBoard_r_profile_desc());
				pstmt.setString(4, vo.getBoard_r_url());
				pstmt.setString(5, vo.getBoard_r_writer_code());
				pstmt.setString(6, vo.getBoard_r_writer_code());
				
				chk = pstmt.executeUpdate();
				
				
				
				
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(con!=null)try{con.close();}catch(SQLException e) {}
				if(pstmt!=null)try {pstmt.close();}catch(SQLException e) {}
			}
			
			
			return chk;
			
			
		}
		
}
