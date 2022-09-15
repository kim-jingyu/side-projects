<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="./resources/css/bootstrap.min.css" />
<link rel="stylesheet" href="./resources/css/bootstrap.min4.css" />
<script type="text/javascript" src="./resources/js/validation.js"></script>
<script type="text/javascript" src="./resources/js/fileUpload.js"></script>
<title>관리자-기업정보등록</title>
</head>
<body>
	<fmt:setLocale value='<%=request.getParameter("language") %>' />
	<fmt:bundle basename="bundle.lang" >
	<jsp:include page="menu.jsp" />
	<p style="text-align: center;">
	<a href="./welcome.jsp">
		<img src="C:/upload/niceBizInfo.png" alt="NICE기업정보 BIZ INFO">
	</a>
	</p>
	<div class="container">
		<div class="text-right">
			<a href="?language=ko" >Korean</a>/<a href="?language=en">English</a>
			<a href="logout.jsp" class="btn btn-sm btn-success pull-right">로그아웃</a>
		</div>
		<form name="newCorporation" action="./processAddCorporation.jsp" class="form-horizontal" method="post" enctype="multipart/form-data">
			<div class="form-group row">
				<label class="col-sm-2"><fmt:message key="copId"></fmt:message></label>
				<div class="col-sm-3">
					<input type="text" id="copId" name="copId" class="form-control">
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2"><fmt:message key="cname"></fmt:message></label>
				<div class="col-sm-3">
					<input type="text" id="cname" name="cname" class="form-control">
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2"><fmt:message key="representative"></fmt:message></label>
				<div class="col-sm-3">
					<input type="text" name="representative" class="form-control">
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2"><fmt:message key="officeAddress"></fmt:message></label>
				<div class="col-sm-3">
					<input type="text" name="officeAddress" class="form-control">
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2"><fmt:message key="formOfBusiness"></fmt:message></label>
				<div class="col-sm-3">
					<input type="text" name="formOfBusiness" class="form-control">
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2"><fmt:message key="industry"></fmt:message></label>
				<div class="col-sm-3">
					<input type="text" name="industry" class="form-control">
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2"><fmt:message key="foundationDate"></fmt:message></label>
				<div class="col-sm-3">
					<input type="text" id="foundationDate" name="foundationDate" class="form-control">
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2"><fmt:message key="certification"></fmt:message></label>
				<div class="col-sm-3">
					<input type="text" id="certification" name="certification" class="form-control">
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2"><fmt:message key="sales"></fmt:message></label>
				<div class="col-sm-3">
					<input type="text" name="sales" class="form-control">
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2"><fmt:message key="avgIncome"></fmt:message></label>
				<div class="col-sm-3">
					<input type="text" name="avgIncome" class="form-control">
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2"><fmt:message key="employee"></fmt:message></label>
				<div class="col-sm-3">
					<input type="text" name="employee" class="form-control">
				</div>
			</div>
			
			<div class="form-group row">
				<label class="col-sm-2"><fmt:message key="location"></fmt:message></label>
				<div class="col-sm-5">
					<input type="file" name="filename" class="form-control">
				</div>
			</div>

			<div class="form-group row">
				<label class="col-sm-2"><fmt:message key="location"></fmt:message></label>
				<div class="col-sm-5">
					<input type="file" name="location" class="form-control">
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2"><fmt:message key="comparison"></fmt:message></label>
				<div class="col-sm-5">
					<input type="file" name="comparison" class="form-control">
				</div>
			</div>
			<div class="form-group row">
				<div class="col-sm-offset-2 col-sm-10">
					<input type="button" class="btn btn-primary" value="<fmt:message key="button"></fmt:message>" onclick="CheckAddCorporation()">
				</div>
			</div>
		</form>
	</div>
	</fmt:bundle>
</body>
</html>