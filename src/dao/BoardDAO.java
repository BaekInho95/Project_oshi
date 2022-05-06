package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.ConUtil;
import vo.BoardTypeVO;
import vo.BoardVO;
import vo.CommentsVO;

public class BoardDAO {

	private static BoardDAO instance;

	private BoardDAO() {
	}

	public static BoardDAO getInstance() {
		if (instance == null) {
			instance = new BoardDAO();
		}
		return instance;
	}

	/*------------------------------------------------------------------------------------------------*/

	/*
	 * public ArrayList<BoardVO> getArticles(String board_type, int start, int end)
	 * { Connection con = null; PreparedStatement pstmt = null; ResultSet rs = null;
	 * ArrayList<BoardVO> articleList = null;
	 * 
	 * try { con = ConUtil.getConnection();
	 * 
	 * pstmt = con.prepareStatement("select * from boards where board_type = '"
	 * +board_type+"' order by board_no desc");
	 * 
	 * 
	 * rs = pstmt.executeQuery();
	 * 
	 * 
	 * if (rs.next()) {
	 * 
	 * articleList = new ArrayList<BoardVO>(end - start + 1); do { BoardVO article =
	 * new BoardVO();
	 * 
	 * article.setBoard_no(rs.getInt("board_no"));
	 * article.setBoard_writer(rs.getString("board_writer"));
	 * article.setBoard_title(rs.getString("board_title"));
	 * article.setBoard_content(rs.getString("board_content"));
	 * article.setBoard_announce(rs.getInt("board_announce"));
	 * article.setBoard_readcount(rs.getInt("board_readcount"));
	 * article.setBoard_type(rs.getString("board_type"));
	 * article.setBoard_recommand(rs.getInt("board_recommand"));
	 * article.setBoard_regdate(rs.getDate("board_regdate"));
	 * article.setBoard_category(rs.getString("board_category"));
	 * 
	 * articleList.add(article);
	 * 
	 * } while (rs.next());
	 * 
	 * }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } finally {
	 * 
	 * }
	 * 
	 * return articleList; }
	 */

	// 게시글 리스트 출력 (검색 포함)
	public List<BoardVO> getArticles(String find, String find_box, String board_type, int start, int end) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardVO> articleList = null;

		try {
			con = ConUtil.getConnection();

			// 검색 - 제목 검색 부분 쿼리
			StringBuffer sql = new StringBuffer();
			sql.append("select * from ");
			if (find.equals("board_title")) {
	        	 
		         sql.append("(select rownum rnum, board_no, member_nickname, board_title, "
		         		+ "board_content, board_announce, board_readcount, board_type, board_recommand, board_regdate, board_c_name from");

		            sql.append("(select * from(select * from boards where upper(board_title) like '%' || upper('"+ find_box +"') || '%' and board_type = '"+ board_type +"' order by board_regdate desc )"
		            		+ "left join member on board_writer = member_code)"
		            		+ "left join board_category on board_category = board_c_code) "
		            		+ "where rnum >= ? and rnum <= ?");
		            
			            pstmt = con.prepareStatement(sql.toString());
			            pstmt.setInt(1, start);
			            pstmt.setInt(2, end);
	         }

			// 검색 - 제목 작성자 부분 쿼리
			else if (find.equals("board_writer")) {

				sql.append(
						"(select Rownum rnum, b.* from (select  board_no, member_nickname, board_title, board_content, board_announce, board_readcount, board_type, board_recommand, board_regdate, board_c_name ");

				sql.append("from (select * from(select * from boards order by board_regdate desc )"
						+ "left join member on board_writer = member_code) "
						+ "left join board_category on board_category = board_c_code) b where upper(member_nickname) like '%' || upper('"+ find_box + "') || '%' and board_type = '" + board_type + "'"
						+ ") where rnum >= ? and rnum <= ?");

				pstmt = con.prepareStatement(sql.toString());
				pstmt.setInt(1, start);
				pstmt.setInt(2, end);

			}
			// 검색 - 제목 내용 부분 쿼리
			else if (find.equals("board_content")) {

				sql.append("(select rownum rnum, board_no, member_nickname, board_title, "
						+ "board_content, board_announce, board_readcount, board_type, board_recommand, board_regdate, board_c_name from");

				sql.append("(select * from (select * from boards where upper(board_content) like '%' || upper('" + find_box+ "') || '%' and board_type = '" + board_type + "' order by board_regdate desc ) "
						+ "left join member on board_writer = member_code)"
						+ "left join board_category on board_category = board_c_code) "
						+ "where rnum >= ? and rnum <= ? ");

				pstmt = con.prepareStatement(sql.toString());
				pstmt.setInt(1, start);
				pstmt.setInt(2, end);

			} else {

				sql.append("(select rownum rnum, board_no, member_nickname, board_title, "
						+ "board_content, board_announce, board_readcount, board_type, board_recommand, board_regdate, board_c_name from");

				sql.append("(select * from(select * from boards where board_type = '" + board_type
						+ "' order by board_regdate desc) " + "left join member on board_writer = member_code)"
						+ "left join board_category on board_category = board_c_code) "
						+ "where rnum >= ? and rnum <= ? ");
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setInt(1, start);
				pstmt.setInt(2, end);

			}

			rs = pstmt.executeQuery();

			if (rs.next()) {

				articleList = new ArrayList<BoardVO>(end - start + 1);
				do {
					BoardVO article = new BoardVO();

					article.setBoard_no(rs.getInt("board_no"));
					article.setBoard_writer(rs.getString("member_nickname"));
					article.setBoard_title(rs.getString("board_title"));
					article.setBoard_content(rs.getString("board_content"));
					article.setBoard_announce(rs.getInt("board_announce"));
					article.setBoard_readcount(rs.getInt("board_readcount"));
					article.setBoard_type(rs.getString("board_type"));
					article.setBoard_recommand(rs.getInt("board_recommand"));
					article.setBoard_regdate(rs.getDate("board_regdate"));
					article.setBoard_category(rs.getString("board_c_name"));

					articleList.add(article);

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

		return articleList;
	}// end List

	public int getArticleCount(String find, String find_box, String board_type) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int x = 0;

		try {
			con = ConUtil.getConnection();

			if (find.equals("board_title")) {
				pstmt = con.prepareStatement("select count(*) from boards where upper(board_title) like '%' || upper('"+ find_box +"') || '%' and board_type='" + board_type + "'");

			} else if

			(find.equals("board_writer")) {
				pstmt = con.prepareStatement(
						"select count(*) from (select * from boards left join member on board_writer = member_code) where upper(member_nickname) like '%' || upper('"+ find_box + "') || '%'and board_type='" + board_type + "'");

			} else if

			(find.equals("board_content")) {
				pstmt = con.prepareStatement("select count(*) from boards where upper(board_content) like '%' || upper('" + find_box+ "') || '%' and board_type = '" + board_type + "' ");

			} else {

				pstmt = con.prepareStatement("select count(*) from boards where board_type = '" + board_type + "'");
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

	}// end getArticleCount()

	public int getArticleCount(String board_type) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int x = 0;

		try {
			con = ConUtil.getConnection();
			pstmt = con.prepareStatement("select count(*) from boards where board_type='" +board_type +"'");
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

	/* 데일리 베스트 */
	public List<BoardVO> getDailyBestArticles(int start, int end, String board_type) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardVO> getDailyBestArticles = null;

		try {
			con = ConUtil.getConnection();

			pstmt = con.prepareStatement(
					"select * from (select Rownum rnum, board_title, board_recommand, board_no,board_type ,board_regdate from (select * from (select * from boards where board_regdate >= TO_CHAR(SYSDATE-2,'YYYYMMDD') )where board_recommand >= 3  and board_type = '"
							+ board_type + "' order by board_recommand desc))where rnum >= ? and rnum <=? ");
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);

			rs = pstmt.executeQuery();

			if (rs.next()) {

				getDailyBestArticles = new ArrayList<BoardVO>(end - start + 1);
				do {
					BoardVO article = new BoardVO();

					article.setBoard_title(rs.getString("board_title"));
					article.setBoard_recommand(rs.getInt("board_recommand"));
					article.setBoard_no(rs.getInt("board_no"));
					article.setBoard_type(rs.getString("board_type"));
					getDailyBestArticles.add(article);

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
		return getDailyBestArticles;
	}

	/* 위클리 베스트 */
	public List<BoardVO> getWeekleyBestArticles(int start, int end, String board_type) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardVO> getWeekleyBestArticles = null;

		try {
			con = ConUtil.getConnection();

			pstmt = con.prepareStatement(
					"select * from (select Rownum rnum, board_title, board_recommand, board_no, board_type, board_regdate from (select * from (select * from boards where board_regdate >= TO_CHAR(SYSDATE-7,'YYYYMMDD') )where board_recommand >= 7  and board_type = '"
							+ board_type + "' order by board_recommand desc))where rnum >= ? and rnum <=? ");
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);

			rs = pstmt.executeQuery();

			if (rs.next()) {

				getWeekleyBestArticles = new ArrayList<BoardVO>(end - start + 1);
				do {
					BoardVO article = new BoardVO();

					article.setBoard_title(rs.getString("board_title"));
					article.setBoard_recommand(rs.getInt("board_recommand"));
					article.setBoard_no(rs.getInt("board_no"));
					article.setBoard_type(rs.getString("board_type"));
					getWeekleyBestArticles.add(article);

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
		return getWeekleyBestArticles;
	}



//게시물 클릭 했을떄 조회수 1 증가하는거

	public BoardVO getArticle(int board_no) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardVO article = null;

		try {

			con = ConUtil.getConnection();
			pstmt = con.prepareStatement("update boards set board_readcount=board_readcount+1 where board_no=?");
			pstmt.setInt(1, board_no);
			pstmt.executeUpdate();
			
			pstmt.close();

			pstmt = con.prepareStatement("select * from (select * from (select * from boards) left join member on board_writer = member_code )"
					+ "left join board_category on BOARD_CATEGORY = BOARD_C_CODE where board_no = ?");
			pstmt.setInt(1, board_no);
			rs = pstmt.executeQuery();

			if (rs.next()) {

				article = new BoardVO();

				article.setBoard_no(rs.getInt("board_no"));
				article.setBoard_writer(rs.getString("board_writer"));
				article.setBoard_title(rs.getString("board_title"));
				article.setBoard_content(rs.getString("board_content"));
				article.setBoard_announce(rs.getInt("board_announce"));
				article.setBoard_readcount(rs.getInt("board_readcount"));
				article.setBoard_type(rs.getString("board_type"));
				article.setBoard_recommand(rs.getInt("board_recommand"));
				article.setBoard_regdate(rs.getDate("board_regdate"));
				article.setBoard_category(rs.getString("BOARD_C_NAME"));
				article.setMember_nickname(rs.getString("MEMBER_NICKNAME"));

			}

		} catch (Exception ex) {
			ex.printStackTrace();
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

		return article;

	}// end getArticle

	// 글 상세보기
	public BoardVO readGetArticle(int board_no) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardVO article = null;

		try {
			con = ConUtil.getConnection();
			pstmt = con.prepareStatement("select * from boards where board_no=?");
			pstmt.setInt(1, board_no);
			rs = pstmt.executeQuery(); // ��°� ��� ���°� num

			if (rs.next()) { // �ִ��� Ȯ��
				article = new BoardVO(); // ��ü�� �����

				article.setBoard_no(rs.getInt("board_no"));
				article.setBoard_writer(rs.getString("board_writer"));
				article.setBoard_title(rs.getString("board_title"));
				article.setBoard_content(rs.getString("board_content"));
				article.setBoard_announce(rs.getInt("board_announce"));
				article.setBoard_readcount(rs.getInt("board_readcount"));
				article.setBoard_type(rs.getString("board_type"));
				article.setBoard_recommand(rs.getInt("board_recommand"));
				article.setBoard_regdate(rs.getDate("board_regdate"));
				article.setBoard_category(rs.getString("board_category"));

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

		return article;

	}
	
	//글등록
	public void insertArticle(BoardVO vo) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "";
		try {
			con = ConUtil.getConnection();
						
			sql = "insert into boards(board_no, board_writer, board_title, board_content, board_announce, board_type, board_regdate, board_category) values(seq_boards.nextval,?,?,?,?,?,sysdate,?)";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getBoard_writer());
			pstmt.setString(2, vo.getBoard_title());
			pstmt.setString(3, vo.getBoard_content());
			pstmt.setInt(4, vo.getBoard_announce());
			pstmt.setString(5, vo.getBoard_type());
			pstmt.setString(6, vo.getBoard_category());
				
			pstmt.executeUpdate();
		}catch(Exception ee) {
			ee.printStackTrace();			
		}finally {
				if(rs!=null)try {rs.close();}catch(SQLException ss) {}
				if(pstmt!=null)try {pstmt.close();}catch(SQLException ss) {}
				if(con!=null)try {con.close();}catch(SQLException ss) {}
		}		
	}//글등록 끝
	
	//마지막 글 번호 가져오기(현재 작성된 글)
	public int getBoardNo() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int x = 0;

		try {
			con = ConUtil.getConnection();

			pstmt = con.prepareStatement("select max(board_no) from boards");

			rs = pstmt.executeQuery();

			if (rs.next()) {
				x = rs.getInt(1);
			}
		}catch(Exception ee) {
			ee.printStackTrace();			
		}finally {
				if(rs!=null)try {rs.close();}catch(SQLException ss) {}
				if(pstmt!=null)try {pstmt.close();}catch(SQLException ss) {}
				if(con!=null)try {con.close();}catch(SQLException ss) {}
		}
		return x;
	}//글번호 가져오기 끝

	//4.23이후 추가-------------
	//글수정
	public void updateArticle(BoardVO vo) {
	Connection con = null;
	PreparedStatement pstmt = null;
	
	System.out.println("타이틀 : "+vo.getBoard_title());
	System.out.println("콘텐트 : "+vo.getBoard_content());
	System.out.println("넘버 : "+vo.getBoard_no());
	
	String sql = "";
	try {
		con = ConUtil.getConnection();
					
		sql = "update boards set board_title=?,board_content=? where board_no=?";
		
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, vo.getBoard_title());
		pstmt.setString(2, vo.getBoard_content());
		pstmt.setInt(3, vo.getBoard_no());
			
		pstmt.executeUpdate();
	}catch(Exception ee) {
		ee.printStackTrace();			
	}finally {
			if(pstmt!=null)try {pstmt.close();}catch(SQLException ss) {}
			if(con!=null)try {con.close();}catch(SQLException ss) {}
	}		
}//글등록 끝
	
	

	//게시판 수 가져오기
			public Date recentArticle() {
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				Date individualArticle = null;
				Date registerArticle = null;
				Date recentArticle = null;
				try {
					con = ConUtil.getConnection();
	
					pstmt = con.prepareStatement("select max(board_regdate) from boards");
					rs = pstmt.executeQuery();
					
					if (rs.next()) {
						individualArticle = rs.getDate(1);
					}
					
					pstmt = con.prepareStatement("select max(board_r_regdate) from board_register");
					rs = pstmt.executeQuery();
					
					if (rs.next()) {
						registerArticle = rs.getDate(1);
					}
					
					if(registerArticle.after(individualArticle)) {
						recentArticle = registerArticle;
					}else {
						recentArticle = individualArticle;
					}
					
					
				} catch(Exception ee) {
					ee.printStackTrace();
				}finally {
					if(rs != null) try {rs.close();}catch(SQLException ss) {}
					if(pstmt != null) try {pstmt.close();}catch(SQLException ss) {}
					if(con != null) try {con.close();}catch(SQLException ss) {}
				}		
				return recentArticle;
			}	//게시판 수 가져오기 끝
			
			 //0차 댓글
			   public ArrayList<CommentsVO> getdat0(int board_no,int startrow,int endrow) {
			       Connection con = null;
			          PreparedStatement pstmt = null;
			          ResultSet rs = null;
			          ArrayList<CommentsVO> abcList = null;
			         
			               
			          try {
			             con = ConUtil.getConnection();
			             pstmt = con.prepareStatement("select * from (select rownum rnum, comments_code, comments_writer, member_nickname as commenter, " + 
			                   "comments_board_no, comments_content, comments_regdate, comments_ref, comments_depth, comments_step " + 
			                   "from comments left join boards on board_no = comments_board_no join member on member_code = comments_writer where comments_board_no=? and comments_depth=0 order by comments_regdate asc ) where rnum>=? and rnum<=?");
			                
			             pstmt.setInt(1, board_no);
			             pstmt.setInt(2, startrow);
			             pstmt.setInt(3, endrow);
			             rs = pstmt.executeQuery();

			             abcList = new ArrayList<CommentsVO>();
			             while(rs.next()) {
			           
			                   

			                CommentsVO abc = new CommentsVO();
			               abc.setComments_code(rs.getInt("comments_code"));
			               abc.setComments_writer(rs.getString("comments_writer"));
			               abc.setComments_board_no(rs.getInt("comments_board_no"));
			               abc.setComments_content(rs.getString("comments_content"));
			               abc.setComments_regdate(rs.getDate("comments_regdate"));
			               abc.setComments_ref(rs.getString("comments_ref"));
			               abc.setComments_depth(rs.getInt("comments_depth"));
			               abc.setComments_step(rs.getInt("comments_step"));
			               abc.setRnum(rs.getInt("rnum"));
			               abc.setCommenter(rs.getString("commenter"));
			               abcList.add(abc);
			             }
			          }catch(Exception e) {
			               e.printStackTrace();
			            }finally {
			               if(rs != null) try {rs.close();}catch(SQLException ss) {}
			               if(pstmt != null) try {pstmt.close();}catch(SQLException ss) {}
			               if(con != null) try {con.close();}catch(SQLException ss) {}
			            }
			      return abcList;
			   }
			   //1차 댓글
			      public ArrayList<CommentsVO> getdat1(int board_no,int startrow,int endrow) {
			          Connection con = null;
			             PreparedStatement pstmt = null;
			             ResultSet rs = null;
			             ArrayList<CommentsVO> abcList = null;
			            
			                  
			             try {
			                con = ConUtil.getConnection();
			                pstmt = con.prepareStatement("select * from (select rownum rnum, comments_code, comments_writer, member_nickname as commenter, " + 
			                      "comments_board_no, comments_content, comments_regdate, comments_ref, comments_depth, comments_step " + 
			                      "from comments join boards on board_no = comments_board_no join member on member_code = comments_writer where comments_board_no=? and comments_depth=1 order by comments_regdate asc) where rnum>=? and rnum<=?");
			                   
			                pstmt.setInt(1, board_no);
			                pstmt.setInt(2, startrow);
			                pstmt.setInt(3, endrow);
			                rs = pstmt.executeQuery();
			                abcList = new ArrayList<CommentsVO>();

			                while(rs.next()) {
			              
			                   CommentsVO abc = new CommentsVO();
			                  abc.setComments_code(rs.getInt("comments_code"));
			                  abc.setComments_writer(rs.getString("comments_writer"));
			                  abc.setComments_board_no(rs.getInt("comments_board_no"));
			                  abc.setComments_content(rs.getString("comments_content"));
			                  abc.setComments_regdate(rs.getDate("comments_regdate"));
			                  abc.setComments_ref(rs.getString("comments_ref"));
			                  abc.setComments_depth(rs.getInt("comments_depth"));
			                  abc.setComments_step(rs.getInt("comments_step"));
			                  abc.setRnum(rs.getInt("rnum"));
			                  abc.setCommenter(rs.getString("commenter"));
			                  abcList.add(abc);
			                
			                }
			             }catch(Exception e) {
			                  e.printStackTrace();
			               }finally {
			                  if(rs != null) try {rs.close();}catch(SQLException ss) {}
			                  if(pstmt != null) try {pstmt.close();}catch(SQLException ss) {}
			                  if(con != null) try {con.close();}catch(SQLException ss) {}
			               }
			         return abcList;
			      }
			      //댓글작성
			       public int addComments(CommentsVO vo) {
			          int chk =0; 
			          Connection con = null;
			            PreparedStatement pstmt = null;
			            ResultSet rs = null;

			            
			            try {
			               con = ConUtil.getConnection();
			               
			               if(vo.getComments_ref()==null) {
			                  pstmt = con.prepareStatement("insert into comments values (seq_comments.nextval, ?, ?, ?, sysdate, null, ?, null)");
			                  pstmt.setString(1, vo.getComments_writer());
			                  pstmt.setInt(2, vo.getComments_board_no());
			                  pstmt.setString(3, vo.getComments_content());
			                  pstmt.setInt(4, vo.getComments_depth());
			               }else {
			                  pstmt = con.prepareStatement("insert into comments values (seq_comments.nextval, ?, ?, ?, sysdate, ?, ?, null)");
			                  pstmt.setString(1, vo.getComments_writer());
			                  pstmt.setInt(2, vo.getComments_board_no());
			                  pstmt.setString(3, vo.getComments_content());
			                  pstmt.setString(4, vo.getComments_ref());
			                  pstmt.setInt(5, vo.getComments_depth());
			               }
			               
			               chk = pstmt.executeUpdate();
			            }catch(Exception e) {
			                  e.printStackTrace();
			               }finally {
			                  if(rs != null) try {rs.close();}catch(SQLException ss) {}
			                  if(pstmt != null) try {pstmt.close();}catch(SQLException ss) {}
			                  if(con != null) try {con.close();}catch(SQLException ss) {}
			               }
			            
			            return chk;
			       }
			     //댓글 삭제
			       public int deleteComments(String comments_code){
			          int chk =-1; 
			          Connection con = null;
			            PreparedStatement pstmt = null;
			         
			          
			            try {
			              con = ConUtil.getConnection();
			              pstmt = con.prepareStatement("delete from comments where comments_code=?");
			              pstmt.setString(1, comments_code);
			              chk = pstmt.executeUpdate();
			            }catch(Exception e) {
			                  e.printStackTrace();
			               }finally {
			                 // if(rs != null) try {rs.close();}catch(SQLException ss) {}
			                  if(pstmt != null) try {pstmt.close();}catch(SQLException ss) {}
			                  if(con != null) try {con.close();}catch(SQLException ss) {}
			               }
			            
			            return chk;
			       }
			//글 작성
			
			 
		    public int writeArticle(BoardVO vo) {
		  	  
		      int chk = 0;
		  	  Connection con = null;
		  	  PreparedStatement pstmt = null;
		  	   ResultSet rs = null;
		  	  
		  	try {
				  con = ConUtil.getConnection();
				  pstmt = con.prepareStatement("insert into boards(board_no, board_writer, board_title, board_content, board_announce, board_type, board_regdate, board_category) values(seq_boards.nextval,?,?,?,?,?,sysdate,?)");
				
				     pstmt.setString(1, vo.getBoard_writer());
			         pstmt.setString(2, vo.getBoard_title());
			         pstmt.setString(3, vo.getBoard_content());
			         pstmt.setInt(4, vo.getBoard_announce());
			         pstmt.setString(5, vo.getBoard_type());
			         pstmt.setString(6, vo.getBoard_category());

		  		  
		  		  
		  		 chk = pstmt.executeUpdate();
		  		  
		  		  
		  		  
		 
		  		  
		  	  }catch(Exception e) {
		  		  e.printStackTrace();
		  	  }finally {
		  		  if(rs!=null)try {rs.close();}catch(SQLException ss) {}
		  		  if(pstmt != null) try {pstmt.close();}catch(SQLException ss) {}
		  		  if(con != null) try {con.close();}catch(SQLException ss) {}
		  	  }
		  	  
		  	  
		  	  return chk;
		    }
		    
		    

		    //공지사항 글 쿼리
		      public List<BoardVO> getNoticeArticles(String board_type,  int start, int end) {
		          Connection con = null;
		          PreparedStatement pstmt = null;
		          ResultSet rs = null;
		          List<BoardVO> noticeArticleList = null;

		          try {
		             con = ConUtil.getConnection();

		             StringBuffer sql = new StringBuffer();
		             
		             //공지사항의 경우 board_category값을 공지인 BC01로 고정합니다.
		             sql.append("select * from(select Rownum rnum, n.* from(select board_no, member_nickname, board_title, board_content,");
		             sql.append("board_announce, board_readcount, board_type, board_recommand, board_regdate, board_c_name from"
		                   
		                            + "(select * from(select * from boards where board_category = 'BC01' and board_type = '"+board_type+"' order by board_regdate desc )");
		                     sql.append("left join member on board_writer = member_code)"
		                           + "left join board_category on board_category = board_c_code)n )"
		                           + "where rnum >= ?  and rnum <= ?");
		                     
		                        pstmt = con.prepareStatement(sql.toString());
		             

		                     pstmt.setInt(1, start);
		                     pstmt.setInt(2, end);
		             

		             rs = pstmt.executeQuery();

		             if (rs.next()) {

		                noticeArticleList = new ArrayList<BoardVO>(1);
		                do {
		                   BoardVO noticeArticle = new BoardVO();

		                   noticeArticle.setBoard_no(rs.getInt("board_no"));
		                   noticeArticle.setBoard_writer(rs.getString("member_nickname"));
		                   noticeArticle.setBoard_title(rs.getString("board_title"));
		                   noticeArticle.setBoard_content(rs.getString("board_content"));
		                   noticeArticle.setBoard_announce(rs.getInt("board_announce"));
		                   noticeArticle.setBoard_readcount(rs.getInt("board_readcount"));
		                   noticeArticle.setBoard_type(rs.getString("board_type"));
		                   noticeArticle.setBoard_recommand(rs.getInt("board_recommand"));
		                   noticeArticle.setBoard_regdate(rs.getDate("board_regdate"));
		                   noticeArticle.setBoard_category(rs.getString("board_c_name"));
		                   
		                   noticeArticleList.add(noticeArticle);

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

		          return noticeArticleList;
		       }
		      
		      
		    //유투버의 정보를 받아옵니다.
		      public BoardTypeVO getYoutuberInfo(String board_t_code){
		         
		              Connection con = null;
		            PreparedStatement pstmt = null;
		            ResultSet rs = null;
		            BoardTypeVO info = null;
		            
		               try {
		                     con = ConUtil.getConnection();
		                     pstmt = con.prepareStatement("select * from board_type where board_t_code=?");
		                     pstmt.setString(1, board_t_code);
		                     rs = pstmt.executeQuery(); 
		                     
		                           if(rs.next()) { 
		                            info = new BoardTypeVO(); 
		                                           
		                            
		                            info.setBoard_t_code(rs.getString("board_t_code"));
		                            info.setBoard_t_desc(rs.getString("board_t_desc"));
		                            info.setBoard_t_image(rs.getString("board_t_image"));
		                            info.setBoard_t_name(rs.getString("board_t_name"));
		                            info.setBoard_t_url(rs.getString("board_t_url"));
		                            info.setBoard_t_manager(rs.getString("board_t_manager"));
		                              
		                           }
		               
		             
		                  } catch (Exception e) {
		                     e.printStackTrace();
		                  }finally {
		                       if(rs != null) try {rs.close();}catch(SQLException ss) {}
		                       if(pstmt != null) try {pstmt.close();}catch(SQLException ss) {}
		                       if(con != null) try {con.close();}catch(SQLException ss) {}
		                       
		                  }
		               
		               return info;
		               
		            }
		      
		    //유투버의 닉네임 정보를 받아옵니다.
		      public BoardTypeVO getYoutuberNicknameInfo(String board_t_code){
		         
		              Connection con = null;
		            PreparedStatement pstmt = null;
		            ResultSet rs = null;
		            BoardTypeVO getYoutuberNickname = null;
		            
		               try {
		                     con = ConUtil.getConnection();
		                     pstmt = con.prepareStatement("select * from (select * from board_type "
		                     		+ "left join member on board_t_youtuber = member_code )"
		                     		+ "where board_t_code = ?");
		                     pstmt.setString(1, board_t_code);
		                     rs = pstmt.executeQuery(); 
		                     
		                           if(rs.next()) { 
		                        	   getYoutuberNickname = new BoardTypeVO(); 
		                                           
		                        	   getYoutuberNickname.setYoutuber_nickname(rs.getString("MEMBER_NICKNAME"));
		                              
		                           }
		               
		             
		                  } catch (Exception e) {
		                     e.printStackTrace();
		                  }finally {
		                       if(rs != null) try {rs.close();}catch(SQLException ss) {}
		                       if(pstmt != null) try {pstmt.close();}catch(SQLException ss) {}
		                       if(con != null) try {con.close();}catch(SQLException ss) {}
		                       
		                  }
		               
		               return getYoutuberNickname;
		               
		            }
		      
		      //관리자의 닉네임 정보를 받아옵니다.
		      public BoardTypeVO getManagerNicknameInfo(String board_t_code){
		         
		              Connection con = null;
		            PreparedStatement pstmt = null;
		            ResultSet rs = null;
		            BoardTypeVO getManagerNickname = null;
		            
		               try {
		                     con = ConUtil.getConnection();
		                     pstmt = con.prepareStatement("select * from (select * from board_type "
		                     		+ "left join member on board_t_manager = member_code )"
		                     		+ "where board_t_code = ?");
		                     pstmt.setString(1, board_t_code);
		                     rs = pstmt.executeQuery(); 
		                     
		                           if(rs.next()) { 
		                        	   getManagerNickname = new BoardTypeVO(); 
		                                           
		                        	   getManagerNickname.setManager_nickname(rs.getString("MEMBER_NICKNAME"));
		                              
		                           }
		               
		             
		                  } catch (Exception e) {
		                     e.printStackTrace();
		                  }finally {
		                       if(rs != null) try {rs.close();}catch(SQLException ss) {}
		                       if(pstmt != null) try {pstmt.close();}catch(SQLException ss) {}
		                       if(con != null) try {con.close();}catch(SQLException ss) {}
		                       
		                  }
		               
		               return getManagerNickname;
		               
		            }
		   
		   
		      
		      
		      
		      
		      //유투버 신청시 관리자의 유투버 신청 게시판으로 글을 입력
		      public int YoutuberCheck(String board_type , String member_code) {
		         int cnt = -1;
		         Connection con = null;
		         PreparedStatement pstmt = null;
		         try {
		            con = ConUtil.getConnection();
		            
		            String sql = "insert into admin_request (ADMIN_R_NO, ADMIN_R_BOARD, ADMIN_R_REQUESTER, ADMIN_R_AUTHORITY_TYPE, ADMIN_R_APPROVED)"
		                  + " values (seq_admin_r.nextval,?,?,1, 0)";
		            pstmt = con.prepareStatement(sql);
		            
		            pstmt.setString(1, board_type);
		            pstmt.setString(2, member_code);
		            
		            
		            cnt = pstmt.executeUpdate();
		            
		         } catch(Exception ee) {
		            ee.printStackTrace();
		         }finally {
		            if(pstmt != null) try {pstmt.close();}catch(SQLException ss) {}
		            if(con != null) try {con.close();}catch(SQLException ss) {}
		         }
		         
		         //System.out.println("cnt값 : "+ cnt);
		         
		         
		         return cnt;
		      }
		      
		      //게시판 관리자 신청시 총관리자의 관리자 신청 게시판으로 글을 입력
		  		public int AdminCheck(String board_type , String member_code) {
		  			int cnt = -1;
		  			Connection con = null;
		  			PreparedStatement pstmt = null;
		  			try {
		  				con = ConUtil.getConnection();
		  				
		  				String sql = "insert into admin_request (ADMIN_R_NO, ADMIN_R_BOARD, ADMIN_R_REQUESTER, ADMIN_R_AUTHORITY_TYPE, ADMIN_R_APPROVED)"
		  						+ " values (seq_admin_r.nextval,?,?,2, 0)";
		  				pstmt = con.prepareStatement(sql);
		  				
		  				pstmt.setString(1, board_type);
		  				pstmt.setString(2, member_code);
		  				
		  				
		  				cnt = pstmt.executeUpdate();
		  				
		  			} catch(Exception ee) {
		  				ee.printStackTrace();
		  				
		  			}finally {
		  				if(pstmt != null) try {pstmt.close();}catch(SQLException ss) {}
		  				if(con != null) try {con.close();}catch(SQLException ss) {}
		  			}
		  			
		  			return cnt;
		  		}
		  		
		  		
		  		// 게시판 관리자가 유투버 게시판의 정보를 수정할때 쓰는거 - 이노
		  		public int updateYoutuberInfo (BoardTypeVO vo,String board_type) {
		  			int chk = 0;

		  			Connection con = null;
		  			PreparedStatement pstmt  = null;
		  			try {
		  				con = ConUtil.getConnection();
		  				String sql = "UPDATE board_type SET BOARD_T_NAME = ?, BOARD_T_URL = ?, BOARD_T_DESC = ?, BOARD_T_IMAGE = ? WHERE BOARD_T_CODE = ?";
		  				
		  				pstmt = con.prepareStatement(sql);
		  				pstmt.setString(1, vo.getBoard_t_name());
		  				pstmt.setString(2, vo.getBoard_t_url());
		  				pstmt.setString(3, vo.getBoard_t_desc());
		  				pstmt.setString(4, vo.getBoard_t_image());
		  				pstmt.setString(5, board_type);
		  				
		  				
		  				chk = pstmt.executeUpdate();

		  		
		  			} catch (Exception e) {
		  				e.printStackTrace();
		  			} finally {
		  				if(con!=null)try{con.close();}catch(SQLException e) {}
		  				if(pstmt!=null)try {pstmt.close();}catch(SQLException e) {}
		  			}
		  			
		  			
		  			return chk;
		  		}
		  		
		  		
		  		public List<CommentsVO> getdat(int board_no) {
		  	       Connection con = null;
		  	          PreparedStatement pstmt = null;
		  	          ResultSet rs = null;
		  	          List<CommentsVO> abcList = null;
		  	         
		  	               
		  	          try {
		  	             con = ConUtil.getConnection();
		  	             pstmt = con.prepareStatement("select * from comments join boards on board_no = comments_board_no where comments_board_no=? order by comments_regdate desc");
		  	                
		  	             pstmt.setInt(1, board_no);
		  	             rs = pstmt.executeQuery();

		  	             if(rs.next()) {
		  	                abcList = new ArrayList<CommentsVO>();
		  	                do {
		  	                   

		  	                CommentsVO abc = new CommentsVO();
		  	               abc.setComments_code(rs.getInt("comments_code"));
		  	               abc.setComments_writer(rs.getString("comments_writer"));
		  	               abc.setComments_board_no(rs.getInt("board_no"));
		  	               abc.setComments_content(rs.getString("comments_content"));
		  	               abc.setComments_regdate(rs.getDate("comments_regdate"));
		  	               abc.setComments_ref(rs.getString("comments_ref"));
		  	               abc.setComments_depth(rs.getInt("comments_depth"));
		  	               abc.setComments_step(rs.getInt("comments_step"));
		  	               abcList.add(abc);
		  	             }while(rs.next()); 
		  	                
		  	             }
		  	          }catch(Exception e) {
		  	               e.printStackTrace();
		  	            }finally {
		  	               if(rs != null) try {rs.close();}catch(SQLException ss) {}
		  	               if(pstmt != null) try {pstmt.close();}catch(SQLException ss) {}
		  	               if(con != null) try {con.close();}catch(SQLException ss) {}
		  	            }
		  	      return abcList;
		  	   }
		  		
		  		public void likeC(int board_no) {
		  	    	 
		      	  Connection con = null;
		        	  PreparedStatement pstmt = null;
		        	  ResultSet rs = null;
		      	 
		        	  try {
		        		  
		        		 con = ConUtil.getConnection();
		        		  pstmt = con.prepareStatement(
		        				  "update boards set board_recommand=board_recommand+1 where board_no=?");
		        		  pstmt.setInt(1, board_no);
		        		  pstmt.executeUpdate();
		        		  
		        		  
		        	  }catch(Exception e) {
		         		  e.printStackTrace();
		         	  }finally {
		         		  if(rs != null) try {rs.close();}catch(SQLException ss) {}
		         		  if(pstmt != null) try {pstmt.close();}catch(SQLException ss) {}
		         		  if(con != null) try {con.close();}catch(SQLException ss) {}
		         	  }
		       }
		       
		  	// 글 삭제
		  		 public int deleteArticle(int board_no) throws ClassNotFoundException {
		  	        Connection con = null;
		  	         PreparedStatement pstmt = null;
		  	         ResultSet rs = null;
		  	         int result=0;
		  	         
		  	         
		  	         try {
		  	          con = ConUtil.getConnection();
		  	          String sql ="delete from boards where board_no=?";
		  	           pstmt = con.prepareStatement(sql);
		  	           pstmt.setInt(1, board_no);
		  	           result = pstmt.executeUpdate();
		  	           
		  	         }catch(SQLException e) {
		  	            e.printStackTrace();
		  	         }finally {
		  	            if(rs != null) try {rs.close();}catch(SQLException ss) {}
		  	             if(pstmt != null) try {pstmt.close();}catch(SQLException ss) {}
		  	             if(con != null) try {con.close();}catch(SQLException ss) {}
		  	             
		  	         }
		  	       return result;
		  	    }
	
}
