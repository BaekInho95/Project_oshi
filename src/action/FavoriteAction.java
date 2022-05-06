package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FavoriteDAO;
import vo.ActionForward;
import vo.FavoriteVO;

public class FavoriteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String fav_board = request.getParameter("board_t_code");
		String fav_user = request.getParameter("member_code");
		
		
		System.out.println(fav_board);
		System.out.println(fav_user);
		
		FavoriteDAO dbPro = FavoriteDAO.getInstance();
		FavoriteVO vo = new FavoriteVO();
		int chk = dbPro.addfavorite(fav_board, fav_user, vo);
		
		if(chk==1) {
		vo.setFav_board(fav_board);
		vo.setFav_user(fav_user);
		
		}
		
		request.setAttribute("fav_board", fav_board);
		request.setAttribute("fav_user", fav_user);
		request.setAttribute("vo", vo);
		request.setAttribute("chk", chk);
		ActionForward forward = new ActionForward("favoriteCheck.jsp", false);
	    return forward;
	}

}
