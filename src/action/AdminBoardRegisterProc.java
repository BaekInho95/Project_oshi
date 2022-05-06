package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardRegisterBoardDAO;
import vo.ActionForward;
import vo.BoardRegisterBoardVO;

public class AdminBoardRegisterProc implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String board_r_no = request.getParameter("board_r_no");
		String board_r_approved = request.getParameter("board_r_approved");
		
		BoardRegisterBoardDAO dao = BoardRegisterBoardDAO.getInstance();
		//게시판 신청 상태 업데이트
		int chk= dao.AccDecBoard(board_r_no, board_r_approved);
		
		//승인시 실제로 게시판 생성
		if (board_r_approved.equals("1")) {
			
			BoardRegisterBoardVO addVO = dao.getContent(board_r_no);
			
			chk = dao.addBoardType(addVO);
		}
		
		
		return new ActionForward("adminBoardRegister.oshi", true);
	}

}
