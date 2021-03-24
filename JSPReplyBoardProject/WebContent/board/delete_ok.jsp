<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*"%>
<%    
/*

*/
	String pwd = request.getParameter("pwd");
	String no = request.getParameter("no");
	String strPage = request.getParameter("page");
	
	BoardDAO dao =new BoardDAO();
	boolean cCheck = dao.boardDelete(Integer.paseInt(no),pwd);
	//3.이동
	if(bCheck ==true)
	{
		response.sendRedirect("list.jsp?page="+strPage);
		
	}else
	{
%>

		<script>
		alert("비밀번호가 다릅니다");
		history.back();
		</script>
<%		
	}
		

%>