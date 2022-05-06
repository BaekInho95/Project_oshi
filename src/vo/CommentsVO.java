package vo;

import java.sql.Date;

public class CommentsVO {
    
	
	private int comments_code;       
	private String comments_writer;  //답 글쓴이
	private int comments_board_no;  //답변 게시물 번호
	private String comments_content; // 답변 내용
	private Date comments_regdate;   // 답변 날짜
	private String comments_ref;       
	private int comments_depth; 
	private int comments_step;
	private int rnum;
	 private String commenter;
	 
	 
	public int getRnum() {
		return rnum;
	}
	public void setRnum(int rnum) {
		this.rnum = rnum;
	}
	public String getCommenter() {
		return commenter;
	}
	public void setCommenter(String commenter) {
		this.commenter = commenter;
	}
	public int getComments_code() {
		return comments_code;
	}
	public void setComments_code(int comments_code) {
		this.comments_code = comments_code;
	}
	public String getComments_writer() {
		return comments_writer;
	}
	public void setComments_writer(String comments_writer) {
		this.comments_writer = comments_writer;
	}
	public int getComments_board_no() {
		return comments_board_no;
	}
	public void setComments_board_no(int comments_board_no) {
		this.comments_board_no = comments_board_no;
	}
	public String getComments_content() {
		return comments_content;
	}
	public void setComments_content(String comments_content) {
		this.comments_content = comments_content;
	}
	public Date getComments_regdate() {
		return comments_regdate;
	}
	public void setComments_regdate(Date comments_regdate) {
		this.comments_regdate = comments_regdate;
	}
	public String getComments_ref() {
		return comments_ref;
	}
	public void setComments_ref(String comments_ref) {
		this.comments_ref = comments_ref;
	}
	public int getComments_depth() {
		return comments_depth;
	}
	public void setComments_depth(int comments_depth) {
		this.comments_depth = comments_depth;
	}
	public int getComments_step() {
		return comments_step;
	}
	public void setComments_step(int comments_step) {
		this.comments_step = comments_step;
	}
	
	
	
	
}
