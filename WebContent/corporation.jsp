<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="dto.Coporation" %>
<%@ page import="dao.CoporationRepository" %>
<jsp:useBean id="corpDAO" class="dao.CoporationRepository" scope="session" />
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="./resources/css/bootstrap.min.css" />
<title>기업 상세 정보</title>
</head>
<body>
	<jsp:include page="menu.jsp" />
	<p style="text-align: center;">
	<a href="./welcome.jsp">
		<img src="C:/upload/niceBizInfo.png" alt="NICE기업정보 BIZ INFO">
	</a>
	</p>
	<h3>기업목록</h3>
	<%
		String id=request.getParameter("id");
		CoporationRepository dao = CoporationRepository.getInstance();
		Coporation corp=dao.getCopById(id);
	%>
	<div class="container">
		<div class="row">
			<div class="col-md-5">
			<img src="C:/upload/<%=corp.getFileName() %>" style="width: 100%" >
			</div>
			<div class="col-md-6">
				<h3><%=corp.getCname() %></h3>
				<p> <b>설립일자: <%=corp.getFoundationDate() %></b>
				<p> <b>입사지원시, 필요한 자격증:</b><span class="badge badge-danger"><%=corp.getCertification() %></span>
				<p> <b>매출액</b> : <%=corp.getSales() %>
				<p> <b>평균 연봉</b> : <%=corp.getAvgIncome() %>
				<p> <b>종업원 수</b> : <%=corp.getEmployee() %>
				<a href="./corporations.jsp" class="btn btn-secondary">상품 목록&raquo;</a>
			</div>
			<hr>
			<div class="col-md-5">
				<h3>산업 내 위치</h3>
				<img src="C:/upload/<%=corp.getLocation() %>" style="width: 100%" >
			</div>
			<hr>
			<div class="col-md-5">
				<h3>동종 산업군 비교</h3>
				<img src="C:/upload/<%=corp.getComparison() %>" style="width: 100%" >
			</div>
			<hr>
		</div>
		<hr>
	</div>
	<jsp:include page="footer.jsp" />
</body>
</html>