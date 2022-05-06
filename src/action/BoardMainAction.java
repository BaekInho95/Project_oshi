package action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BoardTypeDAO;
import vo.ActionForward;
import vo.BoardTypeVO;

public class BoardMainAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward("boardMain.jsp", false);
		HttpSession session = request.getSession();
		
		List<BoardTypeVO> boardTypeList = null;	//1,2,3순위 게시판이 저장된 객체
		List<BoardTypeVO> boardTypeList2 = null; //순위권 밖 게시판이 저장된 객체
		ArrayList<BoardTypeVO> favList = null;//즐겨찾기 목록
		ArrayList<BoardTypeVO> managerList = null;//관리하는 게시판 목록
		
		
		
		BoardTypeDAO dbPro = BoardTypeDAO.getInstance();
		
		boardTypeList = dbPro.getBoardOneTwoThree();
		boardTypeList2 = dbPro.getBoardRest();
		favList = dbPro.getFavList((String)session.getAttribute("member_code"));
		managerList = dbPro.getManagerList((String)session.getAttribute("member_code"));
		
		request.setAttribute("boardTypeList", boardTypeList);
		request.setAttribute("boardTypeList2", boardTypeList2);
		request.setAttribute("favList", favList);
		request.setAttribute("managerList", managerList);
		
		
		return forward;
	}

}
