<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,com.sist.dao.*"%>
<%-- 메모리 할당 
     MovieDAO dao=new MovieDAO();
--%>
<jsp:useBean id="dao" class="com.sist.dao.MovieDAO"></jsp:useBean>
<%
    String cno=request.getParameter("cno");
    if(cno==null)
    	cno="1";
    int index=Integer.parseInt(cno);
    List<MovieBean> list=dao.movieListData(index);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<style type="text/css">

.row{
   width:960px;
   margin: 0px auto;
}
</style>
</head>
<body>
   <div style="height:50px"></div>
   <div class="container">
     <div class="row">
       <%
          for(MovieBean bean:list)
          {
       %>
             <div class="col-md-3">
			    <div class="thumbnail">
			      <a href="#">
			        <img src="<%=bean.getPoster() %>" alt="Lights" style="width:100%">
			        <div class="caption">
			          <p style="font-size: 8pt"><%=bean.getTitle() %></p>
			        </div>
			      </a>
			    </div>
			  </div>
       <%
          }
       %>
     </div>
     <div class="row">
      <h3>최근 방문 영화</h3>
      <hr>
      <%-- 쿠키에 저장된 영화를 보여준다  --%>
     </div>
   </div>
</body>
</html>



