package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDAO;
import vo.ActionForward;
import vo.BoardVO;

public class LikeCheckAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		
		int board_no = Integer.parseInt((request.getParameter("board_no"))); 
		
		BoardDAO dbPro = BoardDAO.getInstance();
		
		dbPro.likeC(board_no);
		
		request.setAttribute("board_no", board_no);
		
		
		ActionForward forward = new ActionForward("likeCheck.jsp", false);
	      return forward;
	}

}
