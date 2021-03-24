<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*"%>
<%
     String no=request.getParameter("no");
     String strPage=request.getParameter("page");
     
     // BoardDAO에서 no에 해당되는 데이터를 받는다 
     BoardDAO dao=new BoardDAO();
     BoardVO vo=dao.boardOneRowData(Integer.parseInt(no), 1);
     
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<style type="text/css">
.row{
   width:800px;
   margin: 0px auto;
}
td{
  font-size: 9pt;
  font-family: 맑은 고딕;
}
</style>
</head>
<body>
  <div style="height:30px"></div>
  <div class="container">
   <div class="row">
    <img src="board.png" style="width:800px;height:150px">
    <div style="height:400px">
    <table class="table">
      <tr>
        <th class="text-center danger" width=20%>번호</th>
        <td class="text-center" width=30%><%=vo.getNo() %></td>
        <th class="text-center danger" width=20%>작성일</th>
        <td class="text-center" width=30%><%=vo.getRegdate().toString() %></td>
      </tr>
      <tr>
        <th class="text-center danger" width=20%>이름</th>
        <td class="text-center" width=30%><%=vo.getName() %></td>
        <th class="text-center danger" width=20%>조회수</th>
        <td class="text-center" width=30%><%=vo.getHit() %></td>
      </tr>
      <tr>
        <th class="text-center danger" width=20%>제목</th>
        <td colspan="3"><%=vo.getSubject() %></td>
      </tr>
      <tr>
        <td class="text-left" valign="top" height=200 colspan="4"><pre style="white-space: pre-wrap;border:none;background-color: white"><%=vo.getContent() %></pre>
        </td>
      </tr>
      <tr>
        <td colspan="4" class="text-right">
         <a href="reply.jsp?no=<%=no %>&page=<%=strPage %>" class="btn btn-sm btn-danger">답변</a>
         <a href="update.jsp?no=<%=no %>&page=<%=strPage %>" class="btn btn-sm btn-success">수정</a>
         <a href="#" class="btn btn-sm btn-warning">삭제</a> 
         <a href="list.jsp?page=<%=strPage %>" class="btn btn-sm btn-info">목록</a>
        </td>
      </tr>
      <tr>
      	<td colspan="4" class="text-right">
      		비밀번호: <input type=password name=pwd size=10 class="input-sm">
      				<input type=hidden name=no value="<%=no %>">
      				<input type=button value="삭제" class="btn btn-sm btn-primary">
       	</td>
      </tr>
    </table>
    </div>
   </div>
  </div>
</body>
</html>
