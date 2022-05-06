package vo;

import java.sql.Date;

public class ReportsVO {
	
	private String reports_code;
	private String reports_reporter;
	private int reports_board_no;
	private Date reports_regdate;
	private int reports_type;
	private String reports_content;
	
	public String getReports_code() {
		return reports_code;
	}
	public void setReports_code(String reports_code) {
		this.reports_code = reports_code;
	}
	public String getReports_reporter() {
		return reports_reporter;
	}
	public void setReports_reporter(String reports_reporter) {
		this.reports_reporter = reports_reporter;
	}
	public int getReports_board_no() {
		return reports_board_no;
	}
	public void setReports_board_no(int reports_board_no) {
		this.reports_board_no = reports_board_no;
	}
	public Date getReports_regdate() {
		return reports_regdate;
	}
	public void setReports_regdate(Date reports_regdate) {
		this.reports_regdate = reports_regdate;
	}
	public int getReports_type() {
		return reports_type;
	}
	public void setReports_type(int reports_type) {
		this.reports_type = reports_type;
	}
	public String getReports_content() {
		return reports_content;
	}
	public void setReports_content(String reports_content) {
		this.reports_content = reports_content;
	}
	
	
}
