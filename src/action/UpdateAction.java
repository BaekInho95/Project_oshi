package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BoardDAO;
import vo.ActionForward;
import vo.BoardVO;

public class UpdateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		

		  BoardDAO dao = BoardDAO.getInstance();
		
		  HttpSession session = request.getSession();
		  
		  String member_code = (String) session.getAttribute("member_code"); // 
		  String  board_category = request.getParameter("board_category"); 
		  String board_type = request.getParameter("board_type"); 
		  int board_no =Integer.parseInt(request.getParameter("board_no"));
		  
		  
		  request.setAttribute("member_code", member_code);
		  request.setAttribute("board_category", board_category);
		  request.setAttribute("board_type", board_type);
		  request.setAttribute("board_no", board_no);
		 
		 
		  BoardVO aa = dao.readGetArticle(board_no);
		  request.setAttribute("a", aa);
		
		ActionForward forward = new ActionForward("individual_update.jsp", false);
		return forward;
	}

}
