package action;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.BoardRegisterBoardDAO;
import vo.ActionForward;
import vo.BoardRegisterBoardVO;

public class BoardRegisterBoardModifyProcAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String board_r_no = request.getParameter("board_r_no");
		
		HttpSession session = request.getSession();
		
		@SuppressWarnings("deprecation")
		String uploadPath = request.getRealPath("upload");
		String encType="utf-8";
		int size =10*1024*1024;//10Mb
		
		String board_r_title = null;
		String board_r_profile_image = null;
		String board_r_url = null;
		String board_r_profile_desc = null;
		String board_r_content = null;
		String board_r_announce = null;
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
			
			
			board_r_profile_image = filename;
			board_r_title = multi.getParameter("board_r_title");
			board_r_url = multi.getParameter("board_r_url");
			board_r_profile_desc = multi.getParameter("board_r_profile_desc");
			board_r_content = multi.getParameter("board_r_content");
			board_r_announce = multi.getParameter("board_r_announce");
			
			
			
			  if (board_r_announce == null) { board_r_announce = "0"; }
			 
			
			
				/*
				 * System.out.println(board_r_title); System.out.println(board_r_announce);
				 * System.out.println(board_r_content);
				 */
			
			
			
			
			
			
			
			BoardRegisterBoardVO article =  new BoardRegisterBoardVO();
			article.setBoard_r_announce(Integer.parseInt(board_r_announce));
			article.setBoard_r_approved(0);
			article.setBoard_r_boardmanager((String)session.getAttribute("member_code"));
			article.setBoard_r_content(board_r_content);
			article.setBoard_r_profile_desc(board_r_profile_desc);
			article.setBoard_r_profile_image(board_r_profile_image);
			article.setBoard_r_title(board_r_title);
			article.setBoard_r_url(board_r_url);
			article.setBoard_r_register((String)session.getAttribute("member_code"));
			article.setBoard_r_no(Integer.parseInt(board_r_no));
			
			/*---------------------- 파일 업로드 처리 -----------------------------*/
			
			
			BoardRegisterBoardDAO dao = BoardRegisterBoardDAO.getInstance();
			
			//수정 메소드
			int chk = dao.modifyContent(article);
			
			System.out.println("chk :" + chk);
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
		return new ActionForward("boardRegisterBoardContent.oshi?board_r_no="+board_r_no, true);
	}

}
