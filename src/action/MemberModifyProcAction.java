package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDAO;
import vo.ActionForward;
import vo.MemberVO;

public class MemberModifyProcAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberVO vo = new MemberVO();
		
		vo.setMember_code(request.getParameter("member_code"));
		vo.setMember_password(request.getParameter("member_password"));
		vo.setMember_nickname(request.getParameter("member_nickname"));
		vo.setMember_email(request.getParameter("member_email"));
		
		
		//System.out.println(request.getParameter("member_nickname"));
		
		int chk = dao.updateMember(vo);
		
		ActionForward forward = new ActionForward("main.oshi", true);
		return forward;
	}

}
