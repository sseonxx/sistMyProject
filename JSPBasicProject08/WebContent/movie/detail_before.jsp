<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
    <%--쿠키 저장해준담에 화면으로 이동해야한다  --%>
<%

	String mno = request.getParameter("mno");
	

	//쿠키전송
	/*
		1. 쿠키 생성
		
		2. 쿠키 저장
		3. 쿠키를 클라이언트로 전송
		
		
	*/
	// 1.쿠키 생성 : 내장객체가 아니다  키  , 값 ==> MAP방식
	Cookie cookie = new Cookie("m"+mno,mno);//값은 문자열만 저장 가능
	cookie.setMaxAge(24*60*60);//초단위=> 24시간
	response.addCookie(cookie);
	
	response.sendRedirect("detail.jsp?mno="+mno);

%>