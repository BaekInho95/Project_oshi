package action;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDAO;
import dao.BoardTypeDAO;
import dao.MemberDAO;
import vo.ActionForward;

public class AdminMainAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//회원 수
		MemberDAO memPro = MemberDAO.getInstance();
		int countMember = memPro.countMember();
		
		request.setAttribute("countMember", countMember);
		//회원 수 끝
		
		//게시판 수
		BoardTypeDAO btPro = BoardTypeDAO.getInstance();
		int countBoardType = btPro.countBoardType();
		
		request.setAttribute("countBoardType", countBoardType);
		//게시판 수 끝
		
		//마지막 게시글 등록일
		BoardDAO dbPro = BoardDAO.getInstance();
		Date recentArticle = dbPro.recentArticle();
		
		request.setAttribute("recentArticle", recentArticle);
		//
		
		
		ActionForward forward = new ActionForward("adminMain.jsp", false);
		return forward;
	}

}
