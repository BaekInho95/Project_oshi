package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BoardDAO;
import vo.ActionForward;
import vo.BoardVO;

public class DeleteProcAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
         
		HttpSession session = request.getSession();
		String member_code = (String)session.getAttribute("member_code");
		//String board_type = (String) session.getAttribute("board_type");
		 int board_no = Integer.parseInt(request.getParameter("board_no"));
		 String board_type = request.getParameter("board_type");
		
		BoardVO vo = new BoardVO();
		vo.setBoard_writer(member_code);
		vo.setBoard_no(board_no);
		vo.setBoard_type(board_type);
		
		System.out.println(vo.getBoard_type());
		BoardDAO dbPro = BoardDAO.getInstance();
		
		 dbPro.deleteArticle(board_no);
		
		request.setAttribute("member_code", member_code);
		request.setAttribute("board_no", board_no); 
		request.setAttribute("board_type", board_type);
		
		  ActionForward forward = new ActionForward("individual_board.oshi?board_type="+board_type, true);
		  
		  return forward;
		
		
	}

}
