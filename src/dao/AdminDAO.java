package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.ConUtil;
import vo.AdminRequestVO;
import vo.BoardTypeVO;
import vo.BoardVO;
import vo.MemberVO;

public class AdminDAO {
	private static AdminDAO instance;

	private AdminDAO() {
	}

	public static AdminDAO getInstance() {
		if (instance == null) {
			instance = new AdminDAO();
		}
		return instance;
	}

///////////////////////////////유투버///////////////////////////////

//유투버 리퀘스트 게시판의 출력
//유투버는 ADMIN_R_AUTHORITY_TYPE = 1
	public List<AdminRequestVO> getYoutuberRegisterInfo() {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<AdminRequestVO> getYoutuberRegisterInfo = null;

		try {
			con = ConUtil.getConnection();

			pstmt = con.prepareStatement(
					"SELECT * FROM(SELECT Rownum rnum, ADMIN_R_NO, ADMIN_R_REQUESTER, ADMIN_R_BOARD, BOARD_T_NAME, BOARD_T_MANAGER, BOARD_T_YOUTUBER, MEMBER_NICKNAME, ADMIN_R_APPROVED, ADMIN_R_AUTHORITY_TYPE FROM(SELECT * FROM (SELECT * FROM ADMIN_REQUEST WHERE ADMIN_R_APPROVED != 2) LEFT JOIN MEMBER ON ADMIN_R_REQUESTER = MEMBER_CODE ORDER BY ADMIN_R_APPROVED ASC ) LEFT JOIN BOARD_TYPE ON ADMIN_R_BOARD = BOARD_T_CODE WHERE ADMIN_R_AUTHORITY_TYPE = 1 )where rnum >= 1 and rnum <=9999 ");

			rs = pstmt.executeQuery();

			if (rs.next()) {

				getYoutuberRegisterInfo = new ArrayList<AdminRequestVO>();
				do {
					AdminRequestVO ad = new AdminRequestVO();
					ad.setAdmin_r_no(Integer.parseInt(rs.getString("ADMIN_R_NO")));
					ad.setAdmin_r_board(rs.getString("ADMIN_R_BOARD"));
					ad.setAdmin_r_requester(rs.getString("ADMIN_R_REQUESTER"));
					ad.setAdmin_r_approved(rs.getInt("ADMIN_R_APPROVED"));
					ad.setAdmin_r_authority_type(rs.getInt("ADMIN_R_AUTHORITY_TYPE"));
					ad.setMember_nickname(rs.getString("MEMBER_NICKNAME"));
					ad.setBoard_t_name(rs.getString("BOARD_T_NAME"));
					ad.setBoard_t_manager(rs.getString("BOARD_T_MANAGER"));
					ad.setBoard_t_youtuber(rs.getString("BOARD_T_YOUTUBER"));
					getYoutuberRegisterInfo.add(ad);

				} while (rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ss) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ss) {
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException ss) {
				}
		}
		return getYoutuberRegisterInfo;

	}

//신청 / 거절 버튼 클릭시 작동하는 메서드
	public int youtuberChoice(String ADMIN_R_BOARD, String ADMIN_R_REQUESTER, String how) {

		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;

		int x = 0;

		try {
			con = ConUtil.getConnection();

			if (how.equals("ok")) {

//유투버 신청자 테이블의 승인됨 표시 ADMIN_R_APPROVED : 1
				String sql = "UPDATE ADMIN_REQUEST SET ADMIN_R_APPROVED = 1 WHERE ADMIN_R_REQUESTER = ? and ADMIN_R_BOARD = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, ADMIN_R_REQUESTER);
				pstmt.setString(2, ADMIN_R_BOARD);

//승인되면 보드타입에 유투버를 교체함
				String sql2 = "UPDATE BOARD_TYPE SET BOARD_T_YOUTUBER = ? WHERE BOARD_T_CODE = ?";

				pstmt2 = con.prepareStatement(sql2);

				pstmt2.setString(1, ADMIN_R_REQUESTER);
				pstmt2.setString(2, ADMIN_R_BOARD);

				x = pstmt.executeUpdate();
				x = pstmt2.executeUpdate();

			} else if

//거절되면 유투버 신청자 테이블의 거절됨 표시 ADMIN_R_APPROVED: 2
			(how.equals("no")) {

				String sql = "UPDATE ADMIN_REQUEST SET ADMIN_R_APPROVED = 2 WHERE ADMIN_R_REQUESTER = ? and ADMIN_R_BOARD = ?";
				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, ADMIN_R_REQUESTER);
				pstmt.setString(2, ADMIN_R_BOARD);

				x = pstmt.executeUpdate();

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ss) {
				}
			if (pstmt2 != null)
				try {
					pstmt.close();
				} catch (SQLException ss) {
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException ss) {
				}
		}

		return x;
	}

////////////////////////////////관리자///////////////////////////////

//관리자 리퀘스트 게시판의 출력
//관리자는 ADMIN_R_AUTHORITY_TYPE = 2
	public List<AdminRequestVO> getAdminRegisterInfo() {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<AdminRequestVO> getAdminRegisterInfo = null;

		try {
			con = ConUtil.getConnection();

			pstmt = con.prepareStatement(
					"SELECT * FROM(SELECT Rownum rnum, ADMIN_R_NO, ADMIN_R_REQUESTER, ADMIN_R_BOARD, BOARD_T_NAME, BOARD_T_MANAGER, BOARD_T_YOUTUBER, MEMBER_NICKNAME, ADMIN_R_APPROVED, ADMIN_R_AUTHORITY_TYPE FROM(SELECT * FROM (SELECT * FROM ADMIN_REQUEST WHERE ADMIN_R_APPROVED != 2) LEFT JOIN MEMBER ON ADMIN_R_REQUESTER = MEMBER_CODE ORDER BY ADMIN_R_APPROVED ASC ) LEFT JOIN BOARD_TYPE ON ADMIN_R_BOARD = BOARD_T_CODE WHERE ADMIN_R_AUTHORITY_TYPE = 2 )where rnum >= 1 and rnum <=9999 ");

			rs = pstmt.executeQuery();

			if (rs.next()) {

				getAdminRegisterInfo = new ArrayList<AdminRequestVO>();
				do {
					AdminRequestVO ad = new AdminRequestVO();
					ad.setAdmin_r_no(Integer.parseInt(rs.getString("ADMIN_R_NO")));
					ad.setAdmin_r_board(rs.getString("ADMIN_R_BOARD"));
					ad.setAdmin_r_requester(rs.getString("ADMIN_R_REQUESTER"));
					ad.setAdmin_r_approved(rs.getInt("ADMIN_R_APPROVED"));
					ad.setAdmin_r_authority_type(rs.getInt("ADMIN_R_AUTHORITY_TYPE"));
					ad.setMember_nickname(rs.getString("MEMBER_NICKNAME"));
					ad.setBoard_t_name(rs.getString("BOARD_T_NAME"));
					ad.setBoard_t_manager(rs.getString("BOARD_T_MANAGER"));
					ad.setBoard_t_youtuber(rs.getString("BOARD_T_YOUTUBER"));
					getAdminRegisterInfo.add(ad);

				} while (rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ss) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ss) {
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException ss) {
				}
		}
		return getAdminRegisterInfo;

	}

//신청 / 거절 버튼 클릭시 작동하는 메서드
	public int adminChoice(String ADMIN_R_BOARD, String ADMIN_R_REQUESTER, String how) {

		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;

		int x = 0;

		try {
			con = ConUtil.getConnection();

			if (how.equals("ok")) {

//관리자 신청자 테이블의 승인됨 표시 ADMIN_R_APPROVED : 1
				String sql = "UPDATE ADMIN_REQUEST SET ADMIN_R_APPROVED = 1 WHERE ADMIN_R_REQUESTER = ? and ADMIN_R_BOARD = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, ADMIN_R_REQUESTER);
				pstmt.setString(2, ADMIN_R_BOARD);

//승인되면 보드타입에 관리자를 교체함
				String sql2 = "UPDATE BOARD_TYPE SET BOARD_T_MANAGER = ? WHERE BOARD_T_CODE = ?";

				pstmt2 = con.prepareStatement(sql2);

				pstmt2.setString(1, ADMIN_R_REQUESTER);
				pstmt2.setString(2, ADMIN_R_BOARD);

				x = pstmt.executeUpdate();
				x = pstmt2.executeUpdate();

			} else if

//거절되면 관리자 신청자 테이블의 거절됨 표시 ADMIN_R_APPROVED: 2
			(how.equals("no")) {

				String sql = "UPDATE ADMIN_REQUEST SET ADMIN_R_APPROVED = 2 WHERE ADMIN_R_REQUESTER = ? and ADMIN_R_BOARD = ?";
				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, ADMIN_R_REQUESTER);
				pstmt.setString(2, ADMIN_R_BOARD);

				x = pstmt.executeUpdate();

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ss) {
				}
			if (pstmt2 != null)
				try {
					pstmt.close();
				} catch (SQLException ss) {
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException ss) {
				}
		}

		return x;
	}
	
	
	
	// 공지사항 게시글 리스트 출력 (검색 포함) 파라미터로 검색 종류, 검색값, 게시판 타입, 시작과 끝 글의 번호를 받습니다.
	   public List<BoardVO> getArticles(String find, String find_box, int start, int end) {
	       
	      //쿼리 수행을 위한 변수들
	      Connection con = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      List<BoardVO> articleList = null;
	      
	         try {
	            // DB 주소 연결을 위해 실행하는 메서드
	            con = ConUtil.getConnection();
	            // String을 +로 문자열을 계속 더하면 계속 새로운 객체가 생성되기 때문에 가비지 컬렉션의 대상이 된다.
	            // 그러므로 StringBuffer로 한다.
	            StringBuffer sql = new StringBuffer();
	            // StringBuffer에서 문자열을 더하는 메서드 append를 사용한다.
	            //공통 쿼리 부분
	            sql.append("select * from ");
	            
	            //검색 - 제목 검색 쿼리
	            if (find.equals("board_title")) {
	               // 글 정렬과 출력을 위한 rownum 선언 및 다른 테이블의 컬럼을 가져오기 위해 left join을 사용했습니다.
	               sql.append("(select rownum rnum, board_no, member_nickname, board_title, "
	               + "board_content, board_announce, board_readcount, board_type, board_recommand, board_regdate, board_c_name from");
	               // 검색의 정확성 향상을 위해 %' '% 형태와 영문 대소문자 구별을 없애기 위해 upper 사용했습니다.
	               // 그리고 글은 전체적으로 작성일자 기준으로 내림차순으로 정렬되어있습니다.
	               sql.append("(select * from(select * from boards where upper(board_title) like '%' || upper('"+ find_box +"') || '%' and board_type = 'A00' order by board_regdate desc )"
	                        + "left join member on board_writer = member_code)"
	                        + "left join board_category on board_category = board_c_code) "
	                        // 이 부분은 항상 쿼리의 가장 바깥에 있어야 합니다.
	                        + "where rnum >= ? and rnum <= ?");
	               
	               // 변수 pstmt에 위 쿼리를 저장합니다.
	               pstmt = con.prepareStatement(sql.toString());
	               // rnum값에 ListAction에서 받아온 startRow와 endRow를 넣어줍니다.
	               pstmt.setInt(1, start);
	               pstmt.setInt(2, end);
	            }
	            
	            //검색 - 작성자 검색 쿼리
	            else if  (find.equals("board_writer")) {
	               // 작성자 검색은 조금 복잡한데, 검색 부분이 boards 테이블이 아닌 member 테이블에 있기 때문에
	               // 검색값을 비교하는 부분이 조인 바깥에 있습니다.
	               sql.append("(select Rownum rnum, b.* from (select  board_no, member_nickname, board_title, board_content, board_announce, board_readcount, board_type, board_recommand, board_regdate, board_c_name ");
	               
	               sql.append("from (select * from(select * from boards order by board_regdate desc )"
	                     + "left join member on board_writer = member_code) "
	                     + "left join board_category on board_category = board_c_code) b where upper(member_nickname) like '%' || upper('"+find_box+"') || '%' and board_type = 'A00'"
	                     + ") where rnum >= ? and rnum <= ?");
	               
	               pstmt = con.prepareStatement(sql.toString());
	               pstmt.setInt(1, start);
	               pstmt.setInt(2, end);

	            } 
	            //검색 - 내용 검색 쿼리
	             else if (find.equals("board_content")) {
	                // 내용 검색의 경우 제목 검색과 동일합니다.
	                sql.append("(select rownum rnum, board_no, member_nickname, board_title, "
	                       + "board_content, board_announce, board_readcount, board_type, board_recommand, board_regdate, board_c_name from");
	                
	                sql.append("(select * from (select * from boards where upper(board_content) like '%' || upper('"+ find_box +"') || '%' and board_type = 'A00' order by board_regdate desc ) "
	                     + "left join member on board_writer = member_code)"
	                     + "left join board_category on board_category = board_c_code) "
	                     + "where rnum >= ? and rnum <= ? ");

	                pstmt = con.prepareStatement(sql.toString());
	                pstmt.setInt(1, start);
	                pstmt.setInt(2, end);
	                
	             } 
	            
	            //검색값이 없을 경우 기본 게시글 출력 부분입니다.
	             else {
	                
	                sql.append("(select rownum rnum, board_no, member_nickname, board_title, "
	                      + "board_content, board_announce, board_readcount, board_type, board_recommand, board_regdate, board_c_name from");
	                
	                sql.append("(select * from(select * from boards where board_type = 'A00' order by board_regdate desc) "
	                     + "left join member on board_writer = member_code)"
	                     + "left join board_category on board_category = board_c_code) "
	                     + "where rnum >= ? and rnum <= ? ");
	                pstmt = con.prepareStatement(sql.toString());
	                pstmt.setInt(1, start);
	                pstmt.setInt(2, end);
	                
	             }
	            // 결과값을 쿼리 실행 후 rs에 저장합니다.
	            rs = pstmt.executeQuery();
	            
	            if (rs.next()) {
	               // 파라미터는 글 갯수입니다.
	               articleList = new ArrayList<BoardVO>(end - start + 1);
	               
	               do {
	                  // BoardVO의 생성자 article을 생성합니다.
	                  BoardVO article = new BoardVO();
	                  // 쿼리 실행 값을 article에 저장합니다.
	                  article.setBoard_no(rs.getInt("board_no"));
	                  // board_writer가 아닌 member_nickname인것에 주의!
	                  article.setBoard_writer(rs.getString("member_nickname"));
	                  article.setBoard_title(rs.getString("board_title"));
	                  article.setBoard_content(rs.getString("board_content"));
	                  article.setBoard_announce(rs.getInt("board_announce"));
	                  article.setBoard_readcount(rs.getInt("board_readcount"));
	                  article.setBoard_type(rs.getString("board_type"));
	                  article.setBoard_recommand(rs.getInt("board_recommand"));
	                  article.setBoard_regdate(rs.getDate("board_regdate"));
	                  // board_category가 아닌 board_c_name인것에 주의!
	                  article.setBoard_category(rs.getString("board_c_name"));
	                  
	                  //각 article의 값을 articleList에 저장합니다.
	                  articleList.add(article);
	                  } while (rs.next());
	            }
	            
	            // 에러 발생시 예외처리합니다.
	         } catch (Exception e) {
	            e.printStackTrace();
	            // 각 변수를 종료시켜줍니다.
	         } finally {
	            if (rs != null)
	               try {
	                  rs.close();
	               } catch (SQLException ss) {
	               }
	            if (pstmt != null)
	               try {
	                  pstmt.close();
	               } catch (SQLException ss) {
	               }
	            if (con != null)
	               try {
	                  con.close();
	               } catch (SQLException ss) {
	               }
	            
	         }
	         // 마지막으로 articleList를 반환합니다.
	         return articleList;
	      }
	
	// 공지사항 게시물의 갯수를 구하는 메서드입니다. 
	   public int getArticleCount(String find, String find_box) {
	      
	      Connection con = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      
	      int x = 0;
	      
	      try {
	         con = ConUtil.getConnection();
	         
	         if
	         (find.equals("board_title")) {
	            
	            pstmt = con.prepareStatement("select count(*) from boards where upper(board_title) like '%' || upper('"+find_box+"') || '%' and board_type = 'A00'");
	            
	         } else if
	         
	         (find.equals("board_writer")) {
	            
	            pstmt = con.prepareStatement("select count(*) from (select * from boards left join member on board_writer = member_code) where upper(member_nickname) like '%' || upper('"+find_box+"') || '%' and board_type = 'A00'");
	            
	         } else if
	         
	         (find.equals("board_content")) {
	            
	            pstmt = con.prepareStatement("select count(*) from boards where upper(board_content) like '%' || upper('"+find_box+"') || '%' and board_type = 'A00'");
	            
	         } else {
	            
	            pstmt = con.prepareStatement("select count(*) from boards where board_content like '%' || upper('"+ find_box +"') || '%' and board_type = 'A00'");
	            
	         }
	         
	         rs = pstmt.executeQuery();
	         
	         if (rs.next()) {
	            
	            x = rs.getInt(1);
	            
	         }
	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      } finally {
	         if (rs != null)
	            try {
	                  rs.close();
	               } catch (SQLException ss) {
	               }
	            if (pstmt != null)
	               try {
	                  pstmt.close();
	               } catch (SQLException ss) {
	               }
	            if (con != null)
	               try {
	                  con.close();
	               } catch (SQLException ss) {
	               }
	         }
	      
	      return x;
	      }
	

}
