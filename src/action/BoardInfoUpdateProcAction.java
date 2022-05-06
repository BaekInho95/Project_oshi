package action;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.BoardDAO;
import dao.BoardRegisterBoardDAO;
import vo.ActionForward;
import vo.BoardRegisterBoardVO;
import vo.BoardTypeVO;

public class BoardInfoUpdateProcAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		

		HttpSession session = request.getSession();
		
		@SuppressWarnings("deprecation")
		String uploadPath = request.getRealPath("upload");
		String encType="utf-8";
		int size =10*1024*1024;//10Mb
		String board_type = null;
		String board_t_name = null;
		String board_t_image = null;
		String board_t_url = null;
		String board_t_desc = null;
		String filename=null;// 저장된 파일
		//String origfilename=null;// 실제파일(사용자가 지정해서 업로드 한 원본파일)
		try {
			MultipartRequest multi = new MultipartRequest(
							request,
							uploadPath,
							size,
							encType,
							new DefaultFileRenamePolicy());// 파일 중복처리를 위한 매개변수
			
			
			
			Enumeration files = multi.getFileNames();
			
			String file = (String)files.nextElement();
			filename = multi.getFilesystemName(file);// 서버에 실제로 저장된 파일을 이름
			//origfilename = multi.getOriginalFileName(file);
			// 클라이언트가 파일 선택창에서 선택한 파일에 이름을 저장함 
			
			
			board_t_image = filename;
			board_t_name = multi.getParameter("board_t_name");
			board_t_url = multi.getParameter("board_t_url");
			board_t_desc = multi.getParameter("board_t_desc");
			board_type = multi.getParameter("board_type");
			BoardTypeVO vo =  new BoardTypeVO();

			vo.setBoard_t_name(board_t_name);
			vo.setBoard_t_url(board_t_url);
			vo.setBoard_t_desc(board_t_desc);
			vo.setBoard_t_image(board_t_image);
			/*---------------------- 파일 업로드 처리 -----------------------------*/
			
			
			BoardDAO dao = BoardDAO.getInstance();
			int chk = dao.updateYoutuberInfo(vo, board_type);
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 유투버 정보를 수정한 게시판으로 갑니다. (리다이렉트, 보드타입 파라미터)
		ActionForward forward = new ActionForward("individual_board.oshi?board_type="+board_type, true);

		return forward;
	}

}
