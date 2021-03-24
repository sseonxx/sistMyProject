package com.sist.dao;
import java.util.*;
import javax.sql.*;
import java.sql.*;
import javax.naming.*;
//결합성이 낮은 프로그램 => 다른 클래스에 영향이 없는 프로그램

public class MovieDAO {
	private Connection conn;
	private PreparedStatement ps;
	//미리 생성된 Connection 객체를 얻어온다
	public void getConnection()
	{
		try {
			//1.탐색기를 연다 (JNDI = rmi)
			Context init = new InitialContext();
			//2. c드라이버를 클릭  java://comp/env
			Context c = (Context)init.lookup("java://comp/env");
			//3. 폴더를 찾는다 jdbc/oracle
			DataSource ds =(DataSource)c.lookup("jdbc/oracle");
			//4. 원하는 파일을 가지고온다 => 미리 생성된 connection의 주소 얻기
			conn = ds.getConnection();
			
		} catch (Exception e) {
		}
	}
	// Connection 사용 후에 반환 => 재사용
	public void disConnection()
	{
		try {
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		} catch (Exception e) {
		}
	}
	
	//기능 처리
	public List<MovieBean> movieListData(int cno)
	{
		List<MovieBean> list = new ArrayList<MovieBean>();
		
		try 
		{
			getConnection(); 
			//미리 생성된 conn의 주소를 얻어온다
			//연결에 소비되는 시간은 단축할 수 있다
			//단점 : 웹에서 작동한다
			
			String sql = "SELECT mno,poster,title "
					   + "FROM daum_movie "
					   + "WHERE cno=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, cno);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				MovieBean bean = new MovieBean();
				bean.setMno(rs.getInt(1));
				bean.setPoster(rs.getString(2));
				bean.setTitle(rs.getString(3));
				
				list.add(bean);
			}
			
					
		} catch (Exception e) 
		{	
			e.printStackTrace();	
		}
		finally
		{
			disConnection();
		}
		
		return list;
	}
	
}
