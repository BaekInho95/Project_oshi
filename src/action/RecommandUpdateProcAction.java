package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BoardDAO;
import dao.UrlDAO;
import vo.ActionForward;
import vo.BoardVO;
import vo.UrlVO;

public class RecommandUpdateProcAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		
		
		//글수정
		String member_code = (String)session.getAttribute("member_code");
		String singer = request.getParameter("singer");
		String title = request.getParameter("title");
		int board_no = Integer.parseInt(request.getParameter("board_no"));
		String board_title = singer+" - "+title;
		String board_type = "B00";
		
		BoardVO article = new BoardVO();
		article.setBoard_no(board_no);
		article.setBoard_writer(member_code);
		article.setBoard_title(board_title);
		article.setBoard_content(request.getParameter("board_content"));
		article.setBoard_announce(1);
		article.setBoard_type(board_type);
		article.setBoard_category(request.getParameter("board_category"));
		BoardDAO dbPro = BoardDAO.getInstance();
		
		dbPro.updateArticle(article);
		//글수정 끝
		
		
		//URL수정
		UrlVO uv = new UrlVO();
		String url_path = request.getParameter("url_path");
		uv.setUrl_board_no(board_no);
		uv.setUrl_path(url_path);
		UrlDAO uvPro = UrlDAO.getInstance();
		
		uvPro.updateUrl(uv);
		//URL수정 끝
		
		ActionForward forward = new ActionForward("recommand.oshi", true);
	    return forward;
	}
}
