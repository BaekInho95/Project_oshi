package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AdminDAO;
import vo.ActionForward;
import vo.AdminRequestVO;
import vo.BoardTypeVO;
import vo.MemberVO;

public class AdminRegisterAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("utf-8");

		AdminDAO dao = AdminDAO.getInstance();
		
		List<AdminRequestVO> getAdminRegisterInfo = null; 
		
		getAdminRegisterInfo = dao.getAdminRegisterInfo();
		request.setAttribute("getAdminRegisterInfo", getAdminRegisterInfo);

		ActionForward forward = new ActionForward("adminRegister.jsp", false);

		return forward;
	}

}
