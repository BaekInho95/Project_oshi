package action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardRegisterBoardDAO;
import paging.Paging;
import vo.ActionForward;
import vo.BoardRegisterBoardCommentVO;
import vo.BoardRegisterBoardVO;

public class BoardRegisterBoardContentAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String board_r_no = request.getParameter("board_r_no");
		
		//DAO 
		BoardRegisterBoardDAO dao = BoardRegisterBoardDAO.getInstance();
		//조회수 증가
		int chk = dao.viewCountIncrease(board_r_no);
		
		
		
		
		
		/*-----페이징-------------------------------------*/
		 
		String currentPage = request.getParameter("curPage");
		//현재 페이지 값 초기화
		int curPage = 1;
		if (currentPage != null) {		
			curPage = Integer.parseInt(currentPage);
		}
		// 한 페이지에 표시할 0차댓글 갯수
		int showCount = 10;
		//페이징 공통메소드 처리
		Paging paging = new Paging();
		paging.makeBlock(curPage);
		paging.makeLastPageNum(showCount,board_r_no);
		
		
		//시작 번호, 끝번호
		int startRow = (showCount * (curPage-1)) + 1;
		int endRow = showCount * curPage;
		
		
		
		//블럭의 시작, 끝 페이지
		int blockStartNum = paging.getBlockStartNum();
		int blockEndNum = paging.getBlockLastNum();
		//전체 자료의 실제 마지막 페이지
		int lastPageNum = paging.getLastPageNum();
		
		//마지막 페이지가 블럭 마지막 숫자보다 작으면  블럭 마지막 숫자(표시 숫자)를 마지막 페이지로 바꿈
		if (lastPageNum < blockEndNum) {
			blockEndNum = lastPageNum;
		}
		
		
		/*-----페이징 ----------------------------------*/
		
		
		
		
		
		
		//뿌릴 게시글 정보
		BoardRegisterBoardVO vo = dao.getContent(board_r_no);
		
		//뿌릴 댓글 정보
		//0차를 기준으로 페이징
		ArrayList<BoardRegisterBoardCommentVO> commentsList0 = dao.getCommentsList0(board_r_no, startRow, endRow);
		
		//1차는 페이징 X
		ArrayList<BoardRegisterBoardCommentVO> commentsList1 = dao.getCommentsList1(board_r_no);
		
		
		
		
		
		
		
		
		
		//현재 게시글 번호
		request.setAttribute("board_r_no", board_r_no);
		
		//게시글 정보
		request.setAttribute("content", vo);
		//댓글 정보
		request.setAttribute("commentsList0", commentsList0);
		request.setAttribute("commentsList1", commentsList1);
		
		//페이징 변수
		request.setAttribute("blockStartNum", blockStartNum);
		request.setAttribute("blockEndNum", blockEndNum);
		request.setAttribute("lastPageNum", lastPageNum);
		
		
		
		//System.out.println(blockStartNum);
		//System.out.println(blockEndNum);
		
		
		return new ActionForward("boardRegisterBoardContent.jsp", false);
	}

}
