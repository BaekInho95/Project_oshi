package action;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AdminDAO;
import dao.BoardBestDAO;
import vo.ActionForward;
import vo.BoardTypeVO;
import vo.BoardVO;

public class NoticeListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	   
		request.setCharacterEncoding("utf-8");
		
		//pageNum값이 있다면 받아온다
		String pageNum = request.getParameter("pageNum");
		
		//없으면 기본값 1로 해준다.
        if(pageNum == null)
             pageNum = "1";
		
        // 한 페이지당 글 갯수 10개
        int pageSize = 10;
        
        // 지금 보고있는 페이지 currentPage
        int currentPage = Integer.parseInt(pageNum);
        
        // 페이징 시 현재 페이지에 따른 시작 글의 번호
        // 예를 들어 현재 페이지가 1이면 startRow = 1
        // 2면 11, 3이면 21
        int startRow = (currentPage-1) * pageSize+1;
        // 페이징 시 현재 페이지에 따른 끝 글의 번호
        // 예를 들어 현재 페이지가 1이면 endRow = 10
        // 2면 20 3이면 30
        int endRow = currentPage * pageSize;
     // 글의 갯수 count
        int count = 0;
        // 글의 번호 number
        int number =0;
        // 검색 종류 find
        String find = null;
        // 검색한 값 find_box
        String find_box = null;
        // 파라미터값이 있다면 받아온다.
        find = request.getParameter("find");
        find_box = request.getParameter("find_box");
        // 검색값이 없다면 공백으로 처리
        if(find == null) {
            find = "";
         }
         if(find_box == null) {
            find_box = "";
         }   
         // 게시판 글 리스트
         List<BoardVO> articleList = null; 
         // boardDAO 사용을 위해 dbPro로 선언
         AdminDAO dbPro = AdminDAO.getInstance(); 
         // count 값을 받아온다.
         count = dbPro.getArticleCount(find, find_box);
         // count가 존재한다면 boardDAO에서 공지사항 리스트 메서드 실행
         if(count > 0) {
         	 // 글 리스트 메서드
             articleList = dbPro.getArticles(find, find_box, startRow, endRow);
             
             }
         else{
         	 // count가 없다면 글 리스트를 비운다.
         	 articleList = Collections.emptyList();
             }
         
         //글 목록에 표시할 글 번호를 의미함
         number = count -(currentPage -1) * pageSize;
         
         //해당 뷰에서 사용할 속성 저장
         request.setAttribute("currentPage", currentPage);
         request.setAttribute("startRow", startRow);
         request.setAttribute("endRow", endRow);
         request.setAttribute("count", count);
         request.setAttribute("pageSize", pageSize);
         request.setAttribute("number", number);
         request.setAttribute("find", find);
         request.setAttribute("find_box", find_box); 
         request.setAttribute("articleList", articleList);
         request.setAttribute("pageNum", pageNum);

         // ActionForward를 이용해서 jsp에 뿌린다, false 로 함으로서 포워드로 설정, 값을 인계한다.
         ActionForward forward = new ActionForward("notice_board.jsp", false);
         return forward;
	}

}
