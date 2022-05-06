package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.ConUtil;
import vo.BoardTypeVO;
import vo.BoardVO;

// 개념글의 DAO
public class BoardBestDAO {
	
	// 싱글톤으로 선언한다. 실제로 DB에 연결하여 레코드의 트랜잭션을 수행하기 때문에
	// DAO클래스가 여러번 인스턴스화 하면 DB와 프로그램 간의 커넥션이 빈번하게 일어난다.
	// 따라서 싱글톤으로 구현한다. 싱글톤 : 해당 클래스의 인스턴스가 하나만 유지되도록 하는 방법.
	private static BoardBestDAO instance;
	
	private BoardBestDAO() {}
	
	public static BoardBestDAO getInstance() {
		if(instance==null) {
			instance = new BoardBestDAO();
		}
		return instance;
	}
	
		// 개념글 게시글 리스트 출력 (검색 포함) 파라미터로 검색 종류, 검색값, 게시판 타입, 시작과 끝 글의 번호를 받습니다.
		// 기본 글 리스트 출력에서 board_recommand >= 3 만 추가됬습니다.
		public List<BoardVO> getArticles(String find, String find_box, String board_type, int start, int end) {
		    
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
		    		  sql.append("(select * from(select * from boards where upper(board_title) like '%' || upper('"+ find_box +"') || '%' and board_type = '"+ board_type +"' and board_recommand >= 3 order by board_regdate desc )"
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
		        			 + "left join board_category on board_category = board_c_code) b where upper(member_nickname) like '%' || upper('"+find_box+"') || '%' and board_type = '"+board_type+"' and board_recommand >= 3"
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
		        	  
		        	  sql.append("(select * from (select * from boards where board_content like '%' || upper('"+ find_box +"') || '%' and board_type = '"+ board_type +"' and board_recommand >= 3 order by board_regdate desc ) "
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
		        	  
		        	  sql.append("(select * from(select * from boards where board_type = '"+ board_type +"' and board_recommand >= 3 order by board_regdate desc) "
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
		
		// 게시물의 갯수를 구하는 메서드입니다. 
		public int getArticleCount(String find, String find_box, String board_type) {
			
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			int x = 0;
			
			try {
				con = ConUtil.getConnection();
				
				if
				(find.equals("board_title")) {
					
					pstmt = con.prepareStatement("select count(*) from boards where upper(board_title) like '%' || upper('"+find_box+"') || '%' and board_type = '"+ board_type +"' and board_recommand >= 3 ");
					
				} else if
				
				(find.equals("board_writer")) {
					
					pstmt = con.prepareStatement("select count(*) from (select * from boards left join member on board_writer = member_code) where upper(member_nickname) like '%' || upper('"+find_box+"') || '%' and board_recommand >= 3");
					
				} else if
				
				(find.equals("board_content")) {
					
					pstmt = con.prepareStatement("select count(*) from boards where upper(board_content) like '%' || upper('"+find_box+"') || '%' and board_recommand >= 3");
					
				} else {
					
					pstmt = con.prepareStatement("select count(*) from boards where board_type = '"+board_type+"' and board_recommand >= 3");
					
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
	
		   //검색값 없을때 카운트
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

}
