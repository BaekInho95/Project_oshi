package action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardRegisterBoardDAO;
import vo.ActionForward;
import vo.BoardRegisterBoardVO;

public class AdminBoardRegisterAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		BoardRegisterBoardDAO dao = BoardRegisterBoardDAO.getInstance();
		
		ArrayList<BoardRegisterBoardVO> arrList = dao.getRegisterBoardList();
		
		
		request.setAttribute("arrList", arrList);
		
		
		
		
		
		
		ActionForward forward = new ActionForward("adminBoardRegister.jsp", false);
		return forward;
	}

}
