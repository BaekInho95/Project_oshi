package vo;

import java.sql.Date;

public class BoardVO {

	private int board_no;
	private String board_writer;
	private String board_title;
	private String board_content;
	private int board_announce;
	private int board_readcount;
	private String board_type;
	private int board_recommand;
	private Date board_regdate;
	private String board_category;
	private String member_nickname;
	
	

	public String getMember_nickname() {
		return member_nickname;
	}

	public void setMember_nickname(String member_nickname) {
		this.member_nickname = member_nickname;
	}

	public int getBoard_no() {
		return board_no;
	}

	public void setBoard_no(int board_no) {
		this.board_no = board_no;
	}

	public String getBoard_writer() {
		return board_writer;
	}

	public void setBoard_writer(String board_writer) {
		this.board_writer = board_writer;
	}

	public String getBoard_title() {
		return board_title;
	}

	public void setBoard_title(String board_title) {
		this.board_title = board_title;
	}

	public String getBoard_content() {
		return board_content;
	}

	public void setBoard_content(String board_content) {
		this.board_content = board_content;
	}

	 public int getBoard_announce() { 
		  return board_announce; 
	 }
	 
	 
    public void setBoard_announce(int board_announce) { 
    	
    	this.board_announce = board_announce;
    }
    
	public int getBoard_readcount() {
		return board_readcount;
	}

	/*
	 * public String getBoard_announce() { return board_announce; }
	 * 
	 * public void setBoard_announce(String board_announce) { this.board_announce =
	 * board_announce; }
	 */
	public void setBoard_readcount(int board_readcount) {
		this.board_readcount = board_readcount;
	}

	public String getBoard_type() {
		return board_type;
	}

	public void setBoard_type(String board_type) {
		this.board_type = board_type;
	}

	public int getBoard_recommand() {
		return board_recommand;
	}

	public void setBoard_recommand(int board_recommand) {
		this.board_recommand = board_recommand;
	}

	public Date getBoard_regdate() {
		return board_regdate;
	}

	public void setBoard_regdate(Date board_regdate) {
		this.board_regdate = board_regdate;
	}

	public String getBoard_category() {
		return board_category;
	}

	public void setBoard_category(String board_category) {
		this.board_category = board_category;
	}

}
