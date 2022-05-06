package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BoardDAO;
import vo.ActionForward;
import vo.BoardVO;

public class NoticeWriteProcAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		     
		
		HttpSession session = request.getSession();
		 String member_code = (String) session.getAttribute("member_code");
		 String board_type = request.getParameter("board_type");
		 
		 
		 
		 int board_announce = 1;
		 
		    BoardVO vo = new BoardVO();
			vo.setBoard_writer(member_code);
			vo.setBoard_title(request.getParameter("Board_title"));
			vo.setBoard_content(request.getParameter("Board_content"));
			vo.setBoard_announce(board_announce);		
			vo.setBoard_type("A00");
		    vo.setBoard_category(request.getParameter("Board_category"));

		
		    BoardDAO dbPro = BoardDAO.getInstance();
		
		
		int chk = dbPro.writeArticle(vo);
		
		request.setAttribute("chk", chk);
		request.setAttribute("board_type", board_type);
		
		ActionForward forward = new ActionForward("NoticeList.oshi", true);
		
		
		
		return forward;
	}

}
