package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDAO;
import dao.MemberDAO;
import dao.ReportsDAO;
import vo.ActionForward;
import vo.BoardVO;
import vo.MemberVO;

public class DeclarationAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int board_no = Integer.parseInt(request.getParameter("board_no"));
		String board_writer = request.getParameter("board_writer");	
		
		BoardDAO dbPro = BoardDAO.getInstance();
		BoardVO article = dbPro.getArticle(board_no);
		ReportsDAO db = ReportsDAO.getInstance();
		MemberVO mem = db.declarationMember(board_writer);
		
		
		request.setAttribute("board_writer", board_writer);
		request.setAttribute("board_no", board_no);
		request.setAttribute("article", article);
		request.setAttribute("mem", mem);
		
		ActionForward forward = new ActionForward("declaration.jsp", false);
	      return forward;
	}

}
