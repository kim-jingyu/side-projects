<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="java.util.*" %>
<%@ page import="dto.Coporation" %>
<%@ page import="dao.CoporationRepository" %>
<jsp:useBean id="corpDAO" class="dao.CoporationRepository" scope="session" />
<!DOCTYPE html>
<html>
<head>
	<title>기업 목록</title>
	<meta charset="utf-8">
  	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
</head>
<body data-spy="scroll" data-target="#myScrollspy" data-offset="1">
	<jsp:include page="menu.jsp" />
	<p style="text-align: center;">
	<a href="./welcome.jsp">
		<img src="C:/upload/niceBizInfo.png" alt="NICE기업정보 BIZ INFO">
	</a>
	</p>
	<footer class="container">
	<DIV style="position:relative; left:0; top:180; padding:70px 30px 10px 0px; font-size:22px; font-weight:bold;">검색결과</div>
</footer>
	
	<%
		CoporationRepository dao = CoporationRepository.getInstance();
		ArrayList<Coporation> listOfcorps = dao.getAllCops();
	%>
	<div class="container">
		<div class="row">
			<%
					for(int i=0;i<listOfcorps.size();i++){
						Coporation corp=listOfcorps.get(i);
			%>
			
			<div class="col-md-5">
				<img src="C:/upload/<%=corp.getFileName() %>" class="rounded mx-auto d-block" style="width: 100%" >
			</div>
			<div class="col-md-6">
				<h3><%=corp.getCname() %></h3>
				<p> <b><%=corp.getRepresentative() %></b>
				<p> <b>본사주소: <%=corp.getOfficeAddress() %></b>
				<p> <b>기업형태: <%=corp.getFormOfBusiness() %></b>
				<p> <b>산업명: <%=corp.getIndustry() %></b>
				<p> <a href="./corporation.jsp?id=<%=corp.getCopId() %> " class="btn btn-info" role="button">상세정보 &raquo;></a>
			</div>
			<hr>
			<%
				}		
			%>
		</div>
		<hr>
	</div>
	
	<jsp:include page="footer.jsp" />
</body>
</html>