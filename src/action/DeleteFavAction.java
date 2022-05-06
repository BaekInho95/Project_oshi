package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardTypeDAO;
import dao.MemberDAO;
import vo.ActionForward;

public class DeleteFavAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String member_code = request.getParameter("member_code");
		String board_t_code = request.getParameter("board_t_code");
		
		
		BoardTypeDAO dao = BoardTypeDAO.getInstance();
		
		int chk = dao.deleteFav(member_code, board_t_code);
		ActionForward forward = new ActionForward("boardMain.oshi", true);
		return forward;
	}

}
