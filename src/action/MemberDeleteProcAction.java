package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MemberDAO;
import vo.ActionForward;

public class MemberDeleteProcAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		String member_id = (String)session.getAttribute("member_id");
		String member_password = request.getParameter("member_password");
		
		System.out.println(member_id);
		System.out.println(member_password);
		
		MemberDAO dao = MemberDAO.getInstance();
		
		int chk = dao.quitMember(member_id, member_password);

		if (chk == 1) {
			System.out.println("탈퇴 성공");
			session.invalidate();
		}else {
			System.out.println("탈퇴 실패");
		}
		
		
		
		
		ActionForward forward = new ActionForward("main.oshi", true);
		return forward;
	}

}
