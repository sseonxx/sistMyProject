<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%--값을 보내고 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<style type="text/css">
.row{
	width:450px;
	margin: 0px auto;
}
</style>
</head>
<body>
	<div class="container">
		<h1 class="text-center">회원 정보</h1>
		<div class="row">
		<form  method="post" action="output1.jsp">
			<table class="table">
				<tr>
					<td width=20% class="text-right">ID</td>
					<td width=80% >
					<input type="text" name=id size=15 class="input-sm">
					</td>
				</tr>
				<tr>
					<td width=20% class="text-right">이름</td>
					<td width=80% >
					<input type="text" name=name size=15 class="input-sm">
					</td>
				</tr>
				
				<tr>
					<td width=20% class="text-right">성별</td>
					<td width=80% >
					<input type=radio name=sex value="남자" checked="checked" >남자
					<input type=radio name=sex value="여자"  >여자
					</td>
				</tr>
				
				<tr>
					<td width=20% class="text-right">주소</td>
					<td width=80% >
					<input type="text" name=addr size=30 class="input-sm">
					</td>
				</tr>
				
				<tr>
					<td width=20% class="text-right">전화번호</td>
					<td width=80% >
					<input type="text" name=tel size=20 class="input-sm">
					</td>
				</tr>
				
				<tr>
					<td colspan="2" class="text-center">
						<button class="btn btn-sm btn-primary">회원가입</button>
					</td>
				</tr>
			</table>
			</form>
		</div>
	</div>
</body>
</html>