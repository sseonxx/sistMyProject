package com.sist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.*;
import java.sql.*;

/*
 *                요청 내용이 request
 *     클라이언트 ==========> 톰캣  =====> list.jsp(list_jsp.java)
 *            요청(파일)                  컴파일 => 인터프리터 실행 (메모리:HTML)
 *  http://서버주소/board/list.jsp 
 * 
 * 
 */
public class BoardDAO {
	private Connection conn;
	private PreparedStatement ps;
	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";

	public BoardDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception ex) {
		}
	}

	public void getConnection() {
		try {
			conn = DriverManager.getConnection(URL, "hr", "happy");
		} catch (Exception ex) {
		}
	}

	public void disConnection() {
		try {
			if (ps != null)
				ps.close();
			if (conn != null)
				ps.close();
		} catch (Exception ex) {
		}
	}

	// 게시판 기능
	// 목록
	public List<BoardVO> boardListData(int page) {
		// => ArrayList VS Vector : 예상문제
		List<BoardVO> list = new ArrayList<BoardVO>();
		/*
		 * List : 데이터의 중복허용 , 순서가 있다 (인덱스:0) | =================== ArrayList , Vector ,
		 * LinkedList
		 * 
		 * 1. 글올리기 3. => ㄴㄴㄴ 5. => ㅔㅔㅔ => 2. ㄴㄴㄴ 4. => ㄴㄴㄴ
		 */
		try {
			getConnection();
			String sql = "SELECT no,subject,name,regdate,hit,group_tap,num "
					+ "FROM (SELECT no,subject,name,regdate,hit,group_tap,rownum as num "
					+ "FROM (SELECT no,subject,name,regdate,hit,group_tap "
					+ "FROM jspReplyBoard ORDER BY group_id DESC,group_step ASC)) " + "WHERE num BETWEEN ? AND ?";
			// 페이징기법 => 인라인뷰
			ps = conn.prepareStatement(sql);
			// ?에 값을 채운다
			int rowSize = 10;
			int start = (rowSize * page) - (rowSize - 1);
			int end = rowSize * page;
			/*
			 * 1page 1 2page 11
			 */
			ps.setInt(1, start);
			ps.setInt(2, end);

			// 실행
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				BoardVO vo = new BoardVO();
				vo.setNo(rs.getInt(1));
				vo.setSubject(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setRegdate(rs.getDate(4));
				vo.setHit(rs.getInt(5));
				vo.setGroup_tab(rs.getInt(6));
				list.add(vo);
			}
			rs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			disConnection();
		}
		return list;
	}

	// 게시물 총갯수
	public int boardRowCount() {
		int count = 0;
		try 
		{
			getConnection();
			String sql = "SELECT COUNT(*) FROM jspReplyBoard";
			ps=conn.prepareStatement(sql);
			ResultSet rs =ps.executeQuery();
			rs.next();
			count = rs.getInt(1);
			rs.close();

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
		finally {
			disConnection();
		}
		return count;
	}

	// 글쓰기
	public void boardInsert(BoardVO vo) {
		try {
			getConnection();
			String sql = "INSERT INTO jspReplyBoard (no,name,subject,content,pwd,group_id) "
					+ "VALUES(jrb_no_seq.nextval,?,?,?,?," + "(SELECT NVL(MAX(group_id)+1,1) FROM jspReplyBoard))";

			ps = conn.prepareStatement(sql);
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getSubject());
			ps.setString(3, vo.getContent());
			ps.setString(4, vo.getPwd());

			// 실행요청
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disConnection();
		}
	}

	// 내용보기 => SQL:2개
	public BoardVO boardOneRowData(int no,int type)
	{
		BoardVO vo = new BoardVO();
		try {
			getConnection();
			String sql = "";
			//상세보기에만 적용
			if(type==1)
			{
				sql="UPDATE jspReplyBoard SET "
				  + "hit=hit+1 "
				  + "WHERE no=?";
				ps=conn.prepareStatement(sql);
				ps.setInt(1, no);
				ps.executeUpdate();
				
			}
			
			//상세보기, 수정하기 동일하게 적용
			sql="SELECT no,name,subject,content,regdate,hit "
			  + "FROM jspReplyBoard "
			  + "WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs = ps.executeQuery();
			rs.next();
			vo.setNo(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setSubject(rs.getString(3));
			vo.setContent(rs.getString(4));
			vo.setRegdate(rs.getDate(5));
			vo.setHit(rs.getInt(6));
		} 
		catch (Exception e) 
		{	
			e.printStackTrace();
		}
		finally {
			disConnection();
		}
		return vo;
		
	}
	// 답변 => SQL:4개
	public void boardReplyInsert(int root,BoardVO vo)
	{
		try 
		{
			getConnection();
			//commit을 사용하지 않는다
			conn.setAutoCommit(false);
			String sql = "SELECT group_id,group_step,group_tap "
					   + "FROM jspReplyBoard "
					   + "WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, root);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int gi=rs.getInt(1);
			int gs=rs.getInt(2);
			int gt=rs.getInt(3);
			rs.close();
			sql="UPDATE jspReplyBoard SET "
			  + "group_step = group_step+1 "
			  + "WHERE gorup_id=? AND group_step>?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, gi);
			ps.setInt(2, gs);
			ps.executeUpdate();
			//3.INSERT
			sql = "INSERT INTO jspReplyBoard VALUES("
				+ "jrb_no_seq.nextval,?,?,?,?,SYSDATE,0,?,?,?,?,0)";
			ps=conn.prepareStatement(sql);
			
			//
			conn.commit();
			
		} 
		catch (Exception e) 
		{
			try {
				conn.rollback();
			} catch (Exception e2) {}
			e.printStackTrace();
		}
		finally
		{
			try {
				conn.setAutoCommit(true);
			} catch (Exception e2) {}
			disConnection();
		}
	}
	// 수정
	/*
	 * 라이브러리 메소드 : Callback 이 있는 경우도 있다
	 * Callback메소드 : 시스템에 의해서 자동으로 호출되는 메소드
	 * 		main() . doGet().doPost()
	 * 사용자 정의 메소드:Callback이 없다 => 반드시 호출후에 사용
	 */
	public boolean boardUpdate(BoardVO vo)
	{
		boolean bCheck=false;
		try 
		{
			//1.연결
			getConnection();
			//2.SQL문장
			//2-1. 비밀번호 검색
			String sql = "SELECT pwd FROM jspReplyBoard "
					   + "WHERE no=?";
			ps=conn.prepareStatement(sql);
			//?에 값을 채운다
			ps.setInt(1, vo.getNo());
			//결과값을 가지고 온다
			ResultSet rs = ps.executeQuery();
			rs.next();
			String db_pwd = rs.getString(1);
			rs.close();
			//본인 여부 확인
			if(db_pwd.equals(vo.getPwd()))
			{
				bCheck=true;
				//수정하기
				sql="UPDATE jspReplyBoard SET "
				  + "name=? , subject=? , content=? "
				  + "WHERE no=?";
				ps=conn.prepareStatement(sql);
				//?에 값을 채운다
				ps.setString(1, vo.getName());
				ps.setString(2, vo.getSubject());
				ps.setString(3, vo.getContent());
				ps.setInt(4, vo.getNo());
				
				ps.executeUpdate();
				
						
			}
			else
			{
				bCheck=false;
			}
			//2-2. 실제 수정
		} 
		catch (Exception e) 
		{		
			e.printStackTrace();
		}
		finally
		{
			disConnection();
		}
		
		return bCheck;
	}
	// 삭제 => SQL:4개
	public boolean boardDelete(int no,String pwd)
	{
		boolean bCheck = false;
		try {
			getConnection();
			conn.setAutoCommit(false);
			//처리
			//1.비밀번호
			String sql = "SELECT pwd FROM jspReplyBoard "
					   + "WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs = ps.executeQuery();
			rs.next();
			String db_pwd=rs.getString(1);
			rs.close();
			if(db_pwd.equals(pwd))
			{
				bCheck=true;
				sql = "SELECT root,dept FROM jspReplyBoard "
				    + "WHERE no=?";
				ps= conn.prepareStatement(sql);
				ps.setInt(1, no);
				rs=ps.executeQuery();
				rs.next();
				int root = rs.getInt(1);
				int depth = rs.getInt(2);
				rs.close();
				
				if (depth==0)//댓글이 없는 상태
				{
					sql= "DELETE FROM jspReplyBoard "
					   + "WHERE no=?";
					ps=conn.prepareStatement(sql);
					ps.setInt(1, no);
					ps.executeUpdate();
							
				}
				else//댓글이 있는 상태
				{
					String msg = "관리자가 삭제한 게시물입니다";
					sql = "UPDATE jspReplyBoard SET "
						+ "subject = ? , content=? "
						+ "WHERE no=?";
					ps = conn.prepareStatement(sql);
					ps.setString(1, msg);
					ps.setString(2, msg);
					ps.setInt(3, no);
					
					ps.executeUpdate();
					
							
				}
					
				sql="";
			}
			else
			{
				bCheck=false;
			}
			conn.commit();
		} 
		catch (Exception e) 
		{
			try {
			conn.rollback();
			} catch (Exception e2) {
				// TODO: handle exception
			}
			e.printStackTrace();
		}
		finally {
			try {
				conn.setAutoCommit(true);
			} catch (Exception e2) {}
			//종료
			disConnection();
		}
		return bCheck;
	}
	// 찾기

}
