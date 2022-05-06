package action;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDAO;
import dao.UrlDAO;
import vo.ActionForward;
import vo.BoardVO;

public class RecommandUpdateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		//글 정보 가져오기
		String board_no = request.getParameter("board_no");
		BoardDAO dbPro = BoardDAO.getInstance();
		BoardVO article = dbPro.getArticle(Integer.parseInt(board_no));
		
		String board_writer = article.getBoard_writer();
		String board_content = article.getBoard_content();
		
		request.setAttribute("board_no",board_no);
		request.setAttribute("board_writer",board_writer);
		request.setAttribute("board_content",board_content);
		//글 정보 가져오기 끝
		
		//url가져오기
		UrlDAO uvPro = UrlDAO.getInstance();
		String url = uvPro.getUrl(Integer.parseInt(board_no));
		request.setAttribute("url",url);
		
		//url가져오기 끝
		
		
		ActionForward forward = new ActionForward("recommandUpdate.jsp", false);
	    return forward;
	}

}
