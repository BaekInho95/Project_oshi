package action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDAO;
import vo.ActionForward;
import vo.BoardVO;

public class RecommandListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
	      request.setCharacterEncoding("utf-8");
	      
	        String pageNum = request.getParameter("pageNum");
	        String board_type = null;
	        
	        if(pageNum == null)
	             pageNum = "1";
	          
	         
	          //현재 페이지
	        
	        int pageSize = 10;
	        
	          int currentPage = Integer.parseInt(pageNum);   
	          
	          
	          //int startRow = (currentPage-1) * pageSize+1;
	          //int endRow = currentPage * pageSize;
	          
	          int startRow = (currentPage-1) * pageSize+1;
	          int endRow = currentPage * pageSize;
	      
	      
	          int count = 0;
	          int number =0;
	          
	          
	          
	          
	          String find = null;
	          String find_box = null;
	          
	          find = request.getParameter("find");
	          find_box = request.getParameter("find_box");
	          board_type = "B00";
	          
		/*
		 * if(find_box == null) { find_box = ""; }
		 */
	          
	          
	          
	        List<BoardVO> articleList = null;   
	        ArrayList<BoardVO> noticeArticleList = null;         
	        ArrayList<BoardVO> dailyList = null;          
	        ArrayList<BoardVO> weeklyList = null;             
	        BoardDAO dbPro = BoardDAO.getInstance(); 
	        if(find == null && find_box == null) {
	        	find = "";
	        	find_box = "";
	        	count = dbPro.getArticleCount(board_type);	        	
	        }else{
	        	count = dbPro.getArticleCount(find,find_box,board_type);	
	        }
	        
	        dailyList = (ArrayList<BoardVO>) dbPro.getDailyBestArticles(1, 5, board_type);
	        weeklyList = (ArrayList<BoardVO>) dbPro.getWeekleyBestArticles(1, 5, board_type);
	        if(count > 0) {
	            articleList = dbPro.getArticles(find, find_box, board_type, startRow, endRow);
	            noticeArticleList = (ArrayList<BoardVO>) dbPro.getNoticeArticles(board_type, 1, 5);
	         }           
	            else {
	               articleList = Collections.emptyList();
	            }
	        
	        //글 목록에 표시할 글 번호를 의미함
	        number = count -(currentPage -1) * pageSize;
	      
	        //해당 뷰에서 사용할 속성 저장(recommand.jsp)
	        
	        request.setAttribute("currentPage", currentPage);
	        request.setAttribute("startRow", startRow);
	        request.setAttribute("endRow", endRow);
	        request.setAttribute("count", count);
	        request.setAttribute("pageSize", pageSize);
	        request.setAttribute("number", number);
	        request.setAttribute("find", find);
	        request.setAttribute("find_box", find_box); 
	        request.setAttribute("articleList", articleList);
	        request.setAttribute("noticeArticleList", noticeArticleList);
	        request.setAttribute("daily", dailyList);
	        request.setAttribute("weekly", weeklyList);
	        request.setAttribute("pageNum", pageNum);
	        request.setAttribute("board_type", board_type);
		
		
		ActionForward forward = new ActionForward("recommand.jsp", false);
	      return forward;
	}

}
