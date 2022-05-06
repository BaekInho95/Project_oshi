package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AdminDAO;
import vo.ActionForward;
import vo.AdminRequestVO;

public class YoutuberRegisterProcAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("utf-8");

		String ADMIN_R_BOARD = request.getParameter("board_t_name");
		String ADMIN_R_REQUESTER = request.getParameter("admin_r_requester");
		String how = request.getParameter("how");
		List<AdminRequestVO> getYoutuberRegisterInfo = null; 
		
		AdminDAO dao = AdminDAO.getInstance();
		
		
		
		dao.youtuberChoice(ADMIN_R_BOARD, ADMIN_R_REQUESTER,  how);
		

		getYoutuberRegisterInfo = dao.getYoutuberRegisterInfo();
		
        request.setAttribute("how", how);
        request.setAttribute("getYoutuberRegisterInfo", getYoutuberRegisterInfo);

		ActionForward forward = new ActionForward("youtuberRegister.jsp", false);

		return forward;
	}

}
