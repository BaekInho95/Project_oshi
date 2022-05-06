package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardRegisterBoardDAO;
import vo.ActionForward;
import vo.BoardRegisterBoardVO;

public class BoardRegisterBoardModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String board_r_no = request.getParameter("board_r_no");
		
		BoardRegisterBoardDAO dao = BoardRegisterBoardDAO.getInstance();
		
		BoardRegisterBoardVO content = dao.getContent(board_r_no);
		
		
		request.setAttribute("content", content);
		
		return new ActionForward("boardRegisterBoardModifyForm.jsp", false);
	}

}
