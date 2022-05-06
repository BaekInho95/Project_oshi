package action;

import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDAO;
import dao.UrlDAO;
import vo.ActionForward;
import vo.BoardVO;
import vo.CommentsVO;

public class RecommandReadAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		
		//글 정보 가져오기
		int board_no = Integer.parseInt(request.getParameter("board_no"));
		BoardDAO dbPro = BoardDAO.getInstance();
	    ArrayList<CommentsVO> abcList0 = dbPro.getdat0(board_no,0,100);
	    ArrayList<CommentsVO> abcList1 = dbPro.getdat1(board_no,0,100);
		BoardVO article = dbPro.getArticle(board_no);
		
		String board_writer = article.getBoard_writer();
		String board_title = article.getBoard_title();
		String board_content = article.getBoard_content();
		String board_type = article.getBoard_type();
		int board_readcount = article.getBoard_readcount();
		int board_recommand = article.getBoard_recommand();
		Date board_regdate = article.getBoard_regdate();
		String board_category = article.getBoard_category();
		
		
		
		request.setAttribute("board_no",board_no);
		request.setAttribute("board_writer",board_writer);
		request.setAttribute("board_type",board_type);
		request.setAttribute("board_title", board_title);
		request.setAttribute("board_content",board_content);
		request.setAttribute("board_readcount",board_readcount);
		request.setAttribute("board_recommand",board_recommand);
		request.setAttribute("boar_regdate",board_regdate);
		request.setAttribute("board_category",board_category);
	      request.setAttribute("abcList0", abcList0);
	      request.setAttribute("abcList1", abcList1);
	      request.setAttribute("abcList1", abcList1);
		//글 정보 가져오기 끝
		
		//url가져오기
		UrlDAO uvPro = UrlDAO.getInstance();
		String url = uvPro.getUrl(board_no);
		request.setAttribute("url",url);
		
		//url가져오기 끝
		
		
		ActionForward forward = new ActionForward("recommandRead.jsp", false);
	    return forward;
	}

}
