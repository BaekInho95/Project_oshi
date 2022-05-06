package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BoardRegisterBoardDAO;
import vo.ActionForward;

public class BoardRegisterBoardRecommendProcAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		
		HttpSession session = request.getSession();
		String member_code = (String)session.getAttribute("member_code");
		String board_r_no = request.getParameter("board_r_no");
		
		//DAO
		BoardRegisterBoardDAO dao = BoardRegisterBoardDAO.getInstance();
		
		//추천수 1증가
		int chk = dao.increaseRecc(member_code, board_r_no);
		
		//chk = 1이면 추천수 증가, chk=-1이면 중복추천
		request.setAttribute("chk", chk);
		
		
		
		
		return new ActionForward("boardRegisterBoardContent.oshi?board_r_no="+board_r_no, true);
	}

}
