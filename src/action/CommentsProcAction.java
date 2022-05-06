package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BoardDAO;
import vo.ActionForward;
import vo.CommentsVO;

public class CommentsProcAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		int chk = -1;

		String board_type = request.getParameter("board_type");
		String board_no = request.getParameter("board_no");
		String comments_writer = request.getParameter("comments_writer");
		int comments_board_no = Integer.parseInt(request.getParameter("comments_board_no"));
		String comments_depth = request.getParameter("comments_depth");
		String comments_content = request.getParameter("comments_content");

		BoardDAO dao = BoardDAO.getInstance();
		CommentsVO vo = new CommentsVO();
		
		if(request.getParameter("comments_ref") != null) {
			String comments_ref = request.getParameter("comments_ref");
			vo.setComments_ref(comments_ref);
		}
		vo.setComments_board_no(comments_board_no);
		vo.setComments_depth(Integer.parseInt(comments_depth));
		vo.setComments_writer(comments_writer);
		vo.setComments_content(comments_content);
		
		 chk = dao.addComments(vo);
		 
		
		request.setAttribute("vo", vo);
		request.setAttribute("chk", chk);
		
		return new ActionForward("individual_read.oshi?board_type="+board_type+"&board_no="+board_no, true);
	}

}
