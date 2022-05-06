package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BoardDAO;
import vo.ActionForward;
import vo.BoardTypeVO;
import vo.BoardVO;
import vo.CommentsVO;

public class NoticeReadAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		request.setCharacterEncoding("utf-8");

	    String board_type = request.getParameter("board_type");
		String member_code = (String) session.getAttribute("member_code");
	    //해당 글 번호
	    int board_no = Integer.parseInt(request.getParameter("board_no"));
	    
	    List<CommentsVO> abcList = null;
	    
	    // boardDAO 사용을 위해 dbPro로 선언
	    BoardDAO dbPro = BoardDAO.getInstance();
	    
	    //해당 글 번호에 대한 해당 레코드 
	    BoardVO article = dbPro.getArticle(board_no);
	    
	    //댓글
	    abcList = dbPro.getdat(board_no);
	    
        // 유투버 정보 보기
        BoardTypeVO info = null;
        
        // 유투버 정보 메서드
        info = dbPro.getYoutuberInfo(board_type);
        
        request.setAttribute("article", article);
        request.setAttribute("abcList", abcList);
        request.setAttribute("info", info);
        request.setAttribute("member_code", member_code);
        
        // ActionForward를 이용해서 jsp에 뿌린다, false 로 함으로서 포워드로 설정, 값을 인계한다.
        ActionForward forward = new ActionForward("notice_read.jsp", false);	
        
		return forward;
	}

}
