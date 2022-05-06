package action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDAO;
import vo.ActionForward;
import vo.BoardTypeVO;
import vo.BoardVO;
import vo.CommentsVO;






//글 내용 처리
public class ContentAction implements Action {

   @Override
   public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

	   String board_type = request.getParameter("board_type");
      //해당 글 번호
      int board_no = Integer.parseInt(request.getParameter("board_no"));
      //해당 페이지 번호
      String pageNum = request.getParameter("pageNum");
//      String comments_writer = request.getParameter("comments_writer");
//      String comments_content = request.getParameter("comments_content");
//      int comments_step = Integer.parseInt(request.getParameter("comments_step"));
      
      //유투버 닉네임 받아오기
      BoardTypeVO YoutuberNickname = null;
      //관리자 닉네임 받아오기
      BoardTypeVO ManagerNickname = null;

      
      
      //데이터 처리 빈
      BoardDAO dbPro = BoardDAO.getInstance();
      

      BoardTypeVO info = null;
      info = dbPro.getYoutuberInfo(board_type);
      BoardVO article = dbPro.getArticle(board_no);
      ArrayList<CommentsVO> abcList0 = dbPro.getdat0(board_no,0,100);
      ArrayList<CommentsVO> abcList1 = dbPro.getdat1(board_no,0,100);
      //해당 글 번호에 대한 해당 레코드 
      

      //유투버 닉네임 받아오기
      YoutuberNickname = dbPro.getYoutuberNicknameInfo(board_type);
      //관리자 닉네임 받아오기
      ManagerNickname = dbPro.getManagerNicknameInfo(board_type);
      
      //해당 뷰에서 사용할 속성을 저장

      request.setAttribute("info", info);
      request.setAttribute("board_no", board_no);
      request.setAttribute("pageNum", pageNum);
      request.setAttribute("article", article);

      request.setAttribute("abcList0", abcList0);
      request.setAttribute("abcList1", abcList1);
      request.setAttribute("abcList1", abcList1);

      request.setAttribute("board_no", board_no);

      request.setAttribute("YoutuberNickname", YoutuberNickname);
      request.setAttribute("ManagerNickname", ManagerNickname);
      
      
        ActionForward forward = new ActionForward("individual_read.jsp", false);
         return forward;
      
      
   }

}