package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import vo.ActionForward;

public class DeleteAction implements Action{
     
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		     
		HttpSession session = request.getSession();
		String member_code = (String)session.getAttribute("member_code");
		 int board_no = Integer.parseInt(request.getParameter("board_no"));
		 String board_type = request.getParameter("board_type");
		 
		 
		 request.setAttribute("member_code", member_code);
		 request.setAttribute("board_no", board_no);
		request.setAttribute("board_type", board_type);
		
		ActionForward forward = new ActionForward("individual_delete.jsp?board_type"+board_type, false);
		return forward;
	}
}
