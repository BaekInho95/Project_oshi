package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MemberDAO;
import vo.ActionForward;
import vo.MemberVO;

public class LoginProcAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		HttpSession session = request.getSession();
		String member_id = request.getParameter("member_id");
		String member_password= request.getParameter("member_password");
		
		
		MemberDAO dao = MemberDAO.getInstance();
		
		
		//로그인 성공시 멤버vo얻기
		MemberVO member = dao.memberLogin(member_id, member_password);
		
		//세션 유지 1시간
		session.setMaxInactiveInterval(60*60);
		
		//로그인 성공 시
		
		if (member != null) {
			session.setAttribute("vo", member);
			session.setAttribute("member_id", member.getMember_id());
			session.setAttribute("member_code", member.getMember_code());
		}
		
		ActionForward forward = new ActionForward("main.oshi", true);
		return forward;
	}

}
