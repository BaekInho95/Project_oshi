package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDAO;
import vo.ActionForward;
import vo.MemberVO;

public class registerProcAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		MemberVO vo = new MemberVO();
		MemberDAO dao = MemberDAO.getInstance();
		
		String member_id = request.getParameter("member_id");
		String member_password = request.getParameter("member_password");
		String member_nickname = request.getParameter("member_nickname");
		String member_email = request.getParameter("member_email");
		int member_grade = Integer.parseInt(request.getParameter("member_grade"));
		
		
		vo.setMember_id(member_id);
		vo.setMember_password(member_password);
		vo.setMember_nickname(member_nickname);
		vo.setMember_email(member_email);
		vo.setMember_grade(member_grade);
		
		int chk = dao.insertMember(vo);
		
		if (chk == 1) {
			System.out.println("회원가입 성공");
		}else {
			System.out.println("회원가입 실패");
		}
		
		
		ActionForward forward = new ActionForward("main.oshi", true);
		return forward;
	}

}
