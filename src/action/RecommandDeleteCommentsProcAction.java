package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BoardDAO;
import vo.ActionForward;

public class RecommandDeleteCommentsProcAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		BoardDAO dao = BoardDAO.getInstance();

		String board_type = request.getParameter("board_type");
		String board_no = request.getParameter("board_no");
		
		String comments_code = request.getParameter("comments_code");
		
		int chk = dao.deleteComments(comments_code);
		
		request.setAttribute("chk", chk);
	
		 ActionForward forward = new ActionForward("recommandRead.oshi?board_type="+board_type+"&board_no="+board_no, true);
         return forward;
	}

}
