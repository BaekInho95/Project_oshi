package action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDAO;
import dao.MainDAO;
import dao.UrlDAO;
import vo.ActionForward;
import vo.BoardVO;

public class MainAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
		request.setCharacterEncoding("utf-8");
		
		//인기 추천곡의 정보 가져오기
	      UrlDAO uvPro = UrlDAO.getInstance();
	      BoardDAO dbPro = BoardDAO.getInstance();
	      
	      String favRecommandUrl = uvPro.favRecommandUrl();
	      
	      int favRecommandBoardNo = uvPro.favRecommandBoardNo();
	      
	      MainDAO maindao = MainDAO.getInstance();
	      BoardVO vo = dbPro.readGetArticle(favRecommandBoardNo);
	      String favRecommandBoardTitle = vo.getBoard_title();
	      
	      
	      // 인덱스 페이지의 공지, 인기글
	      ArrayList<BoardVO> getMainBestArticles = null;
	      ArrayList<BoardVO> getMainNoticeArticles = null;
	      
	      getMainBestArticles = (ArrayList<BoardVO>) maindao.getMainBestArticles(1, 5);
	      getMainNoticeArticles = (ArrayList<BoardVO>) maindao.getMainNoticeArticles(1, 5);
	      
	      request.setAttribute("getMainBestArticles", getMainBestArticles);
	      request.setAttribute("getMainNoticeArticles", getMainNoticeArticles);
	      //////////////인덱스 페이지의 공지, 인기글///////////
	      
	      request.setAttribute("favRecommandUrl", favRecommandUrl);
	      request.setAttribute("favRecommandBoardNo", favRecommandBoardNo);
	      request.setAttribute("favRecommandBoardTitle", favRecommandBoardTitle);
	      //인기 추천곡 끝
	      
	      
	      //신규 추천곡의 정보 가져오기
	      String newRecommandUrl = uvPro.newRecommandUrl(); //URL
	      
	      int newRecommandBoardNo = uvPro.newRecommandBoardNo(); //글번호
	      
	      
	      vo = dbPro.readGetArticle(newRecommandBoardNo);
	      String newRecommandBoardTitle = vo.getBoard_title(); //글제목
	      
	      request.setAttribute("newRecommandUrl", newRecommandUrl);
	      request.setAttribute("newRecommandBoardNo", newRecommandBoardNo);
	      request.setAttribute("newRecommandBoardTitle", newRecommandBoardTitle);
	      //신규 추천곡 끝
		
		ActionForward forward = new ActionForward("index.jsp", false);
		
		return forward;
	}

}
