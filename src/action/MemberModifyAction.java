package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MemberDAO;
import vo.ActionForward;
import vo.MemberVO;

public class MemberModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		MemberDAO dao = MemberDAO.getInstance();
		
		MemberVO vo = dao.selectMember((String)session.getAttribute("id"));
		
		request.setAttribute("vo", vo);
		
		ActionForward forward = new ActionForward("memberModifyForm.jsp", false);
		return forward;
	}

}
