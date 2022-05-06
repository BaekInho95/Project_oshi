package paging;

import dao.BoardRegisterBoardDAO;

public class Paging {
	private final static int pageCount = 5;
	private int blockStartNum = 0;
	private int blockLastNum = 0;
	private int lastPageNum = 0;
	public int getBlockStartNum() {
		return blockStartNum;
	}
	public void setBlockStartNum(int blockStartNum) {
		this.blockStartNum = blockStartNum;
	}
	public int getBlockLastNum() {
		return blockLastNum;
	}
	public void setBlockLastNum(int blockLastNum) {
		this.blockLastNum = blockLastNum;
	}
	public int getLastPageNum() {
		return lastPageNum;
	}
	public void setLastPageNum(int lastPageNum) {
		this.lastPageNum = lastPageNum;
	}
	public static int getPagecount() {
		return pageCount;
	}
	
	// block을 생성
	// 현재 페이지가 속한 block의 시작 번호, 끝 번호를 계산
	public void makeBlock(int curPage) {
		int blockNum = 0; // 1~5 => 0블럭    6~10 => 1블럭    11~15 => 2블럭 ..................
		
		blockNum = (curPage-1)/pageCount; 
		
		blockStartNum = (blockNum * pageCount) + 1; 
		blockLastNum = blockStartNum + (pageCount - 1) ;
	}
	
	// 댓글 마지막 페이지 구하기
	public void makeLastPageNum(int showCount,String board_r_no) {
		BoardRegisterBoardDAO dao = BoardRegisterBoardDAO.getInstance();
		int total = dao.getCountComment(board_r_no);
		
		
		lastPageNum = total % showCount == 0 ?  
				(total/showCount) : (total/showCount) + 1 ;
		
	}
	
	// 일반 게시글 마지막 페이지 구하기(검색 시 총 페이지의 마지막 번호)
	public void makeLastPageNum(String tag, String kwd, int showCount) {
		BoardRegisterBoardDAO dao = BoardRegisterBoardDAO.getInstance();
		int total = dao.getCount(tag, kwd);
		//System.out.println(total);
		
		lastPageNum = total % showCount == 0 ?  
				(total/showCount) : (total/showCount) + 1 ;
		
	}
	
	
}
