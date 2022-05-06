package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BoardRegisterBoardDAO;
import vo.ActionForward;
import vo.BoardRegisterBoardCommentVO;

public class BoardRegisterBoardContentProcAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		//인서트 확인용 정수
		int chk = -1;
		//VO
		BoardRegisterBoardCommentVO comment = new BoardRegisterBoardCommentVO();
		
		//DAO
		BoardRegisterBoardDAO dao = BoardRegisterBoardDAO.getInstance();
		
		//파라미터 가져오기
		String board_r_no = request.getParameter("board_r_no");	
		String board_c_depth = request.getParameter("board_c_depth");	
		String register_b_c_content = request.getParameter("register_b_c_content");
		//ref가 null아니면 가져와서 vo set
		if (request.getParameter("board_c_ref") != null) {
			String board_c_ref = request.getParameter("board_c_ref");
			comment.setRegister_b_c_ref(Integer.parseInt(board_c_ref));
		}
		
		//vo set
		comment.setRegister_b_c_board_no(Integer.parseInt(board_r_no));
		comment.setRegister_b_c_depth(Integer.parseInt(board_c_depth));
		comment.setRegister_b_c_member_code((String)session.getAttribute("member_code"));
		comment.setRegister_b_c_content(register_b_c_content);
		
		//댓글 insert
		chk = dao.insertComment(comment);
		
		
		
		
		
		
		
		
		
		
		return new ActionForward("boardRegisterBoardContent.oshi?board_r_no=" + board_r_no, true);
	}

}
