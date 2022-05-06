package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDAO;
import vo.ActionForward;

public class RecommandDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//삭제할 글 번호 가져오기
		int board_no = Integer.parseInt(request.getParameter("board_no"));
		
		BoardDAO dbPro = BoardDAO.getInstance();
		
		dbPro.deleteArticle(board_no);		
		
		ActionForward forward = new ActionForward("recommand.oshi", true);
	    return forward;
	}

}
