package action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDAO;
import vo.ActionForward;
import vo.BoardTypeVO;

public class WriteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		String board_type=request.getParameter("board_type");
		
	    // boardDAO 사용을 위해 dbPro로 선언
	    BoardDAO dbPro = BoardDAO.getInstance();
	    
        // 유투버 정보 보기
        BoardTypeVO info = null;
        
        // 유투버 정보 메서드
        info = dbPro.getYoutuberInfo(board_type);
		
		
		request.setAttribute("board_type", board_type);
        request.setAttribute("info", info);
	
		ActionForward forward = new ActionForward("individual_write.jsp", false);
		return forward;
		 
	}

}
