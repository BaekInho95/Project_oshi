package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import db.ConUtil;
import vo.MemberVO;
import vo.ReportsVO;

public class ReportsDAO {
   private static ReportsDAO dao;
   
   private ReportsDAO() {}
   
   public static ReportsDAO getInstance() {
      if(dao==null) {
         dao = new ReportsDAO();
      }
      return dao;
   }
   public MemberVO declarationMember(String board_writer) {
      MemberVO vo = null;
      Connection con = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      
      
      try {
         con = ConUtil.getConnection();
         String sql = "select * from member where member_code=?";
         pstmt = con.prepareStatement(sql);
         pstmt.setString(1, board_writer);
         
         rs = pstmt.executeQuery();
         
         if (rs.next()) {
            vo = new MemberVO();
            vo.setMember_code(rs.getString("member_code"));
            vo.setMember_id(rs.getString("member_id"));
            vo.setMember_password(rs.getString("member_password"));
            vo.setMember_nickname(rs.getString("member_nickname"));
            vo.setMember_email(rs.getString("member_email"));
         }
         
         
      }catch(Exception ee) {
         ee.printStackTrace();
      }finally {
         if(rs != null) try {rs.close();}catch(SQLException ss) {}
         if(pstmt != null) try {pstmt.close();}catch(SQLException ss) {}
         if(con != null) try {con.close();}catch(SQLException ss) {}
      }
      return vo;
   }
   public ReportsVO getdeclaration(int board_no) {
      
      ReportsVO vo = null;
      Connection con = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      
      try {
         con = ConUtil.getConnection();
         String sql = "select boards.board_no,reports.reports_reporter,report_type.reports_t_name,reports.reports_content from reports join boards on board_no = reports_board_no join report_type on reports_code = reports_t_code where board_no=?;";
         pstmt = con.prepareStatement(sql);
         pstmt.setInt(1, board_no);
         rs = pstmt.executeQuery();
         
         if(rs.next()) {
            vo = new ReportsVO();
            vo.setReports_board_no(rs.getInt("board_no"));
            vo.setReports_reporter(rs.getString("reports_reporter"));
            vo.setReports_content(rs.getString("reports_content"));
            
         }
      }catch(Exception ee) {
         ee.printStackTrace();
      }finally {
         if(rs != null) try {rs.close();}catch(SQLException ss) {}
         if(pstmt != null) try {pstmt.close();}catch(SQLException ss) {}
         if(con != null) try {con.close();}catch(SQLException ss) {}
      }
      
      
      
      return vo;
   }
   public int insertdeclaration(String reports_reporter,ReportsVO vo) {
      Connection con = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      int chk = -1;
      
      try {
         
         con = ConUtil.getConnection();
         pstmt = con.prepareStatement("select * from reports");
         
         rs= pstmt.executeQuery();
         
         if(rs.next()) {
         
         //(reports_code,reports_reporter,reports_board_no,reports_regdate,reports_type,reports_content)
         String sql = "insert into reports values(SEQ_REPORTS.nextval,?,?,sysdate,?,?)";
         pstmt = con.prepareStatement(sql);
         
         pstmt.setString(1, reports_reporter);
         pstmt.setInt(2, vo.getReports_board_no());
         pstmt.setInt(3, vo.getReports_type());
         pstmt.setString(4, vo.getReports_content());
         chk = pstmt.executeUpdate();
         
         }
      }catch(Exception ee) {
         ee.printStackTrace();
      }finally {
         if(rs != null) try {rs.close();}catch(SQLException ss) {}
         if(pstmt != null) try {pstmt.close();}catch(SQLException ss) {}
         if(con != null) try {con.close();}catch(SQLException ss) {}
      }
      return chk;
   }
}