<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*"%>
<%
	//1. 한글 입력값: 디코딩
	try{
		request.setCharacterEncoding("UTF-8");
	}catch(Exception e){}
	String name=request.getParameter("name");
	String subject=request.getParameter("subject");
	String content=request.getParameter("content");
	String pwd=request.getParameter("pwd");
	/*
	
	*/
	String no=request.getParameter("no");
	String strPage=request.getParameter("page");
	
	//묶어서 DAO로 전송
	BoardVO vo =new BoardVO();
	vo.setName(name);
	vo.setSubject(subject);
	vo.setContent(content);
	vo.setPwd(pwd);
	vo.setNo(Integer.parseInt(no));
	
	//BoardDAO연결 => 오라클 연결 => 수정
	BoardDAO dao = new BoardDAO(); //오라클 연결 => 요청한 내요을 처리
	/*
		DAO : 오라클 연결 역할 (모든 JSP에서 사용이 가능 : 공통모듈)
	*/
	boolean bCheck = dao.boardUpdate(vo);
	
	if(bCheck==true)
	{
		response.sendRedirect("detail.jsp?no="+no+"&page="+strPage);
	}
	else
	{
%>
		<script>
		alert("비밀번호가 틀립니다!");
		history.back(); //이전화면으로 이동
		</script>
<%
	}
	
%>