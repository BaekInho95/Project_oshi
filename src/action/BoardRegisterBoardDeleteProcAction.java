package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardRegisterBoardDAO;
import vo.ActionForward;

public class BoardRegisterBoardDeleteProcAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		BoardRegisterBoardDAO dao = BoardRegisterBoardDAO.getInstance();
		
		String board_r_no = request.getParameter("board_r_no");
		
		int chk = dao.deleteContent(board_r_no);
		
		//페이지 분기용
		String chkPage = request.getParameter("chkPage");
		
		
		
		
		ActionForward forward = null;

		
		if (chkPage.equals("1")) {
			forward = new ActionForward("adminBoardRegister.oshi", true);
		}else {
			forward = new ActionForward("boardRegisterBoard.oshi", true);
		}
		
		
		return forward;
	}

}
