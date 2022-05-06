package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.eclipse.jdt.internal.compiler.ast.MemberValuePair;

import db.ConUtil;
import vo.MemberVO;

public class MemberDAO {
	
	
	private static MemberDAO dao;
	
	private MemberDAO() {}
	
	public static MemberDAO getInstance() {
		if(dao==null) {
			dao = new MemberDAO();
		}
		return dao;
	}
	
	/*------------------------------------------------------------------------------------------------*/
	
	//로그인 성공 후 MemberVO 객체 얻는 메소드
	public MemberVO memberLogin(String id, String password) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberVO member = null;
		
		
		try {
			con = ConUtil.getConnection();
			String sql = "select * from member where member_id=? and member_password=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				member = new MemberVO();
				member.setMember_code(rs.getString("member_code"));
				member.setMember_id(rs.getString("member_id"));
				member.setMember_password(rs.getString("member_password"));
				member.setMember_nickname(rs.getString("member_nickname"));
				member.setMember_email(rs.getString("member_email"));
				member.setMember_grade(rs.getInt("member_grade"));
			}
			
			
		}catch(Exception ee) {
			ee.printStackTrace();
		}finally {
			if(rs != null) try {rs.close();}catch(SQLException ss) {}
			if(pstmt != null) try {pstmt.close();}catch(SQLException ss) {}
			if(con != null) try {con.close();}catch(SQLException ss) {}
		}

		
		
		
		
		
		return member;
	}
	
	
	

		
		
		

	
	
	
	
	//회원가입
	public int insertMember(MemberVO vo){
		Connection con = null;
		PreparedStatement pstmt = null;
		int check = 0;
		
		
		try {
			con = ConUtil.getConnection();
			
			pstmt = con.prepareStatement("insert into member values('M' || seq_member.nextval, ?, ?, ?, ?, ?)");
			pstmt.setString(1, vo.getMember_id());
			pstmt.setString(2, vo.getMember_password());
			pstmt.setString(3, vo.getMember_nickname());
			pstmt.setString(4, vo.getMember_email() );
			pstmt.setInt(5, vo.getMember_grade());
			
			check = pstmt.executeUpdate();
			
			
			
			
		} catch(Exception ee) {
			ee.printStackTrace();
		}finally {
			if(pstmt != null) try {pstmt.close();}catch(SQLException ss) {}
			if(con != null) try {con.close();}catch(SQLException ss) {}
		}
		
		return check;
	}
	
	
	//멤버 정보 select
	
	public MemberVO selectMember(String member_code) {
		MemberVO vo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {
			con = ConUtil.getConnection();
			String sql = "select * from member where member_code=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member_code);
			
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
	
	
	
	//회원정보 수정
	public int updateMember(MemberVO vo) {
		
		int chk = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ConUtil.getConnection();
			
			pstmt = con.prepareStatement("update member set member_password = ?, member_nickname = ?, member_email =? where member_code = ?");
			pstmt.setString(1, vo.getMember_password());
			pstmt.setString(2, vo.getMember_nickname());
			pstmt.setString(3, vo.getMember_email() );
			pstmt.setString(4, vo.getMember_code());
			
			
			
			System.out.println(vo.getMember_nickname());
			
			
			chk = pstmt.executeUpdate();
			
			
			
			
		} catch(Exception ee) {
			ee.printStackTrace();
		}finally {
			if(pstmt != null) try {pstmt.close();}catch(SQLException ss) {}
			if(con != null) try {con.close();}catch(SQLException ss) {}
		}
		
		
		return chk;
	}

	
	
	//회원탈퇴(실제론 비번을 바꿔서 접근 불가능하게바꿈)-튜플 실제 삭제하면 그 사람이 작성한 컨텐츠들이 fk없어서 오류뜸
	public int quitMember(String id, String password) {
		int chk = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ConUtil.getConnection();
			
			
			String sql = "update member set member_password = '#*NFJ@*#FJDFJ@(#G' where member_id = ? and member_password =?";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			
			
			
			chk = pstmt.executeUpdate();
			
		} catch(Exception ee) {
			ee.printStackTrace();
		}finally {
			
			if(pstmt != null) try {pstmt.close();}catch(SQLException ss) {}
			if(con != null) try {con.close();}catch(SQLException ss) {}
		}
		
		
		
		
		
		
		return chk;
	}
	
	
	
	//회원가입 아이디 중복 체크
	public int idDuplicateCheck(String id) {
		int cnt = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConUtil.getConnection();
			
			
			String sql = "select count(*) from member where member_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			
			if (rs.next()) {
				cnt = rs.getInt(1);
			}
			
			
		} catch(Exception ee) {
			ee.printStackTrace();
		}finally {
			if(rs != null) try {rs.close();}catch(SQLException ss) {}
			if(pstmt != null) try {pstmt.close();}catch(SQLException ss) {}
			if(con != null) try {con.close();}catch(SQLException ss) {}
		}
		
		
		return cnt;
	}
	
	//4.23 이후 추가------
	//회원수 가져오기
	public int countMember() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int countMember = 0;
		try {
			con = ConUtil.getConnection();
			
			
			pstmt = con.prepareStatement("select count(*) from member");
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				countMember = rs.getInt(1);
			}
			
			
		} catch(Exception ee) {
			ee.printStackTrace();
		}finally {
			if(rs != null) try {rs.close();}catch(SQLException ss) {}
			if(pstmt != null) try {pstmt.close();}catch(SQLException ss) {}
			if(con != null) try {con.close();}catch(SQLException ss) {}
		}		
		return countMember;
	}	//회원수 가져오기 끝
}
