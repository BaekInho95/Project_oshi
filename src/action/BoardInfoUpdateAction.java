package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import vo.ActionForward;

public class BoardInfoUpdateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String board_type = request.getParameter("board_type");

		request.setAttribute("board_type", board_type);
	
		//보드타입값을 파라미터로 넘겨줍니다.
		ActionForward forward = new ActionForward("updateYoutuberInfo.jsp", false);

		return forward;
	}

}
