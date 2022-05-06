package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BoardDAO;
import vo.ActionForward;
import vo.BoardTypeVO;
import vo.BoardVO;

public class UpdateProcAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		
		HttpSession session = request.getSession();
		 String member_code = (String) session.getAttribute("member_code");
		 String board_category = request.getParameter("board_category");
		 String board_type = request.getParameter("board_type");
		 int board_no = Integer.parseInt(request.getParameter("board_no"));
		 String board_title = request.getParameter("board_title");
		 String board_content = request.getParameter("board_content");
		 
		 System.out.println("액션에서의 타이틀 : "+board_title);
		 System.out.println("액션에서의 콘텐트 : "+board_content);
		 
		 
		 
		 BoardTypeVO info = null;
		 
		 int board_announce = 1;
			/*
			 * //공지인 경우 if (board_category.equals("BC01")) { board_announce=0; }
			 */
		    	    
		    BoardVO vo = new BoardVO();
		    vo.setBoard_no(board_no);
			vo.setBoard_writer(member_code);
			vo.setBoard_title(board_title);
			vo.setBoard_content(board_content);
			vo.setBoard_announce(board_announce);		
			vo.setBoard_type(board_type);
		    vo.setBoard_category(board_category);

		
		    BoardDAO dbPro = BoardDAO.getInstance();

			info = dbPro.getYoutuberInfo(board_type);
			 
		
		dbPro.updateArticle(vo);

		request.setAttribute("info", info);
		request.setAttribute("board_type", board_type);
		
		ActionForward forward = new ActionForward("individual_read.oshi?board_type="+board_type+"&board_no="+board_no, true);
		
		
		
		return forward;
		
	}

}
