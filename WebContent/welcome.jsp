<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="./resources/css/bootstrap.min.css">
<link rel="stylesheet" href="./resources/css/bootstrap.min2.css">
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
<title>Welcome</title>
</head>
<body>
	<%@ include file="menu.jsp" %>
	
	<div class="container">
		<div class="text-center">
		
			<div id="s100">
			<h1>
			<div id="s102">
			<p style="text-align: center;">
			    <img src="C:/upload/niceBizInfo.png" alt="NICE기업정보 BIZ INFO">
			</p>
			</div>
			</h1>
			<div class="box">
  				<div class="container-3">
      			
      			<form method="post" action="./corporations2.jsp">
      			<div id="s101">
      				<span class="icon"><i class="fa fa-search"></i></span>
					<input type="text"  id="search" name="search" placeholder="기업명을 입력하세요" /> 
					</div>
      				<input type="submit" value="검색" style="display:none;"/>
      			</form>
  				</div>
			</div>
			<br>
		</div>
		</div>
		<hr>
	</div>
	<%@ include file="footer.jsp" %>
</body>
</html>