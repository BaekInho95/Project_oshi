package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDAO;
import dao.ReportsDAO;
import vo.ActionForward;
import vo.BoardVO;
import vo.MemberVO;
import vo.ReportsVO;

public class DeclarationCheckAction implements Action{
@Override
public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	request.setCharacterEncoding("utf-8");
	
	
	
	//String board_writer = request.getParameter("board_writer");
	String reports_reporter = request.getParameter("reports_reporter");
	int reports_board_no = Integer.parseInt(request.getParameter("reports_board_no"));
	int reports_type = Integer.parseInt(request.getParameter("reports_type"));
	String reports_content = request.getParameter("reports_content");
	
	System.out.println(reports_reporter);//세션처리 필요함 m61
	System.out.println(reports_board_no);// 13
	System.out.println(reports_type);//넘어오는데 부모키가없다 2
	System.out.println(reports_content);// 123
		/*
		 * if(reports_reporter==null) { reports_reporter = "M27"; }
		 */
	
	ReportsDAO db = ReportsDAO.getInstance();
	BoardDAO dbPro = BoardDAO.getInstance();
	BoardVO article = dbPro.getArticle(reports_board_no);
	ReportsVO vo =  new ReportsVO();
	
	vo.setReports_reporter(article.getBoard_writer());
	vo.setReports_board_no(reports_board_no);
	vo.setReports_type(reports_type);
	vo.setReports_content(reports_content);
	
	int chk = db.insertdeclaration(reports_reporter,vo);
	
	request.setAttribute("vo", vo);
	request.setAttribute("reports_board_no", reports_board_no);
	request.setAttribute("reports_reporter", reports_reporter);
	request.setAttribute("reports_type", reports_type);
	request.setAttribute("reports_content", reports_content);
	request.setAttribute("chk", chk);
	ActionForward forward = new ActionForward("declarationCheck.jsp", false);
    return forward;
	}
}
