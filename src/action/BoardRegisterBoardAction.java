package action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardRegisterBoardDAO;
import paging.Paging;
import vo.ActionForward;
import vo.BoardRegisterBoardVO;

public class BoardRegisterBoardAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		
		
		String currentPage = request.getParameter("curPage");
		//검색태그, 검색어
		String tag = request.getParameter("tag");
		String kwd = request.getParameter("kwd");

		//한 페이지에 표시할 게시글 갯수
		int showCount = 8;
		
		//현재 페이지 값 초기화
		int curPage = 1;
		if (currentPage != null) {		
			curPage = Integer.parseInt(currentPage);
			
		}
		
		//페이징 공통메소드 처리
		Paging paging = new Paging();
		paging.makeBlock(curPage);
		paging.makeLastPageNum(tag, kwd, showCount);
		
		
		
		//시작 번호, 끝번호
		int startRow = (showCount * (curPage-1)) + 1;
		int endRow = showCount * curPage;
		
		
		
		
		
		//블럭의 시작, 끝 페이지
		int blockStartNum = paging.getBlockStartNum();
		int blockEndNum = paging.getBlockLastNum();
		//전체 자료의 실제 마지막 페이지
		int lastPageNum = paging.getLastPageNum();
		
		//System.out.println(blockStartNum);
		//System.out.println(blockEndNum);
		//System.out.println(lastPageNum);
		
		//마지막 페이지가 블럭 마지막 숫자보다 작으면  블럭 마지막 숫자(표시 숫자)를 마지막 페이지로 바꿈
		if (lastPageNum < blockEndNum) {
			blockEndNum = lastPageNum;
		}
		
		
		BoardRegisterBoardDAO dao = BoardRegisterBoardDAO.getInstance();
		
		ArrayList<BoardRegisterBoardVO> arrList = dao.getRegisterBoardList(startRow, endRow, tag, kwd);
		ArrayList<BoardRegisterBoardVO> announceList = dao.getAnnounceList();
		
		request.setAttribute("dummy", "member_id");
		request.setAttribute("blockStartNum", blockStartNum);
		request.setAttribute("blockEndNum", blockEndNum);
		request.setAttribute("lastPageNum", lastPageNum);
		request.setAttribute("arrList", arrList);
		request.setAttribute("announceList", announceList);
		
		return new ActionForward("boardRegisterBoard.jsp", false);
	}

}
