<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
<link rel="stylesheet" href="./resources/css/bootstrap.min.css" />
<link rel="stylesheet" href="./resources/css/bootstrap.min2.css">
<title>Login</title>
</head>
<body>
	<jsp:include page="menu.jsp" />

	<p style="text-align: center;">
	<a href="./welcome.jsp">
		<img src="C:/upload/niceBizInfo.png" alt="NICE기업정보 BIZ INFO">
	</a>
	</p>
		
	<div class="container" align="center">
		<div class="col-md-4 col-md-offset-4">
			<h3 class="form-signin-heading">관리자 로그인</h3>

			<%
				String error=request.getParameter("error");
				if(error!=null){
					out.println("<div class='alert alert-danger'>");
					out.println("아이디 혹은 비밀번호를 잘못 입력하셨습니다.");
					out.println("</div>");
				}
			%>
			<form class="form-signin" action="j_security_check" method="post">
				<div class="form-group">
					<label for="inputUserName" class="sr-only">아이디
					</label>
					<input type="text" class="form-control" placeholder="ID" name='j_username' required autofocus>
				</div>
				<div class="form-group">
					<label for="inputPassword" class="sr-only">비밀번호</label>
					<input type="password" class="form-control" placeholder="Password" name='j_password' required>
				</div>
				<button type="submit" class="btn btn-lg btn-primary btn-block">로그인</button>
			</form>
		</div>
	</div>
</body>
</html>