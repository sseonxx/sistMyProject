<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.bean.*"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<%--
	MemberBean bean =new MemberBean(); //메모리 할당
	//빈이라는 객체가 생성됨
	
	jsp에서 지원하는 액션 태그 <jsp:
	1. 클래스 메모리 할당 => 싱글톤 <jsp:useBean id="객체명" class="패키지명.클래스명">
		Class.forName("패키지명.클래스명"); => 클래스를 등록 메모리 할당 요청 => 패키지명. 클래스명
	2. 클라이언트가 전송한 데이터를 받아서 setXxx ()에 저장하는 태그 
	<jsp:setProperty name="" property="변수명"/> 전체는 *
 	3. 빈에 저장된 데이터를 읽어서 출력 : <jsp:getPropery name="객체명" property="변수명">
 	4. request를 공유하면서 JSP여러개를 조립 : <jsp:include page = "파일명">
 	5. request 를 공유하면 화면을 이동 : <jsp:forward page="">
 	
 	Bean을 만들 경우 : 데이터를 모아서 한번에 전송할 목적
 	==============
 		변수 : private => 읽기 / 쓰기
 		메소드 : public
 			boolean => isXxx
 --%>
<jsp:useBean id="bean" class="com.sist.bean.MemberBean">
	<jsp:setProperty name="bean" property="*"/> 

</jsp:useBean>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	아이디 : <jsp:getProperty name="bean" property="id"/><br>
	<%-- 액션 태그 --%>
	이름 : <%= bean.getName() %><br>
	성별 : <%=bean.getSex() %><br>
	주소 : <%=bean.getAddr() %><br>
 	번호 : <%= bean.getTel() %>
 	
 	<%
 		try
 		{
 			Class clsName=Class.forName("com.sist.bean.MemberBean");
 			MemberBean obj = (MemberBean)clsName.newInstance();
 			
 		}catch(Exception ex){}
 	%>
</body>
</html>