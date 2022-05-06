package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardRegisterBoardDAO;
import vo.ActionForward;

public class BoardRegisterBoardContentCommentDeleteProc implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String board_r_no = request.getParameter("board_r_no");
		String register_b_c_code = request.getParameter("register_b_c_code");
		
		BoardRegisterBoardDAO dao = BoardRegisterBoardDAO.getInstance();
		
		int chk = dao.commentDelete(register_b_c_code);
		
		
		
		
		
		
		return new ActionForward("boardRegisterBoardContent.oshi?board_r_no="+board_r_no, true);
	}

}
