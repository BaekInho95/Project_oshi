package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BoardDAO;
import dao.UrlDAO;
import vo.ActionForward;
import vo.BoardVO;
import vo.UrlVO;

public class RecommandWriteProcAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		
		
		//글등록
		String member_code = (String)session.getAttribute("member_code");
		String singer = request.getParameter("singer");
		String title = request.getParameter("title");
		String board_title = singer+" - "+title;
		String board_type = "B00";
		
		BoardVO article = new BoardVO();
		article.setBoard_writer(member_code);
		article.setBoard_title(board_title);
		article.setBoard_content(request.getParameter("board_content"));
		article.setBoard_announce(1);
		article.setBoard_type(board_type);
		article.setBoard_category(request.getParameter("board_category"));
		BoardDAO dbPro = BoardDAO.getInstance();
		
		dbPro.insertArticle(article);
		//글등록 끝
		
		
		//URL등록
		UrlVO uv = new UrlVO();
		String url_path = request.getParameter("url_path");
		int url_board_no = dbPro.getBoardNo(); //현재 게시글 번호 가져오기
		uv.setUrl_board_no(url_board_no);
		uv.setUrl_path(url_path);
		UrlDAO uvPro = UrlDAO.getInstance();
		
		uvPro.insertUrl(uv);
		//URL등록 끝
		
		ActionForward forward = new ActionForward("recommand.oshi", true);
	    return forward;
	}

}
