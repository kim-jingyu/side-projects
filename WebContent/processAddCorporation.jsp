<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.oreilly.servlet.*" %>
<%@ page import="com.oreilly.servlet.multipart.*" %>
<%@ page import="java.util.*" %>
<%@ page import="dto.Coporation" %>
<%@ page import="dao.CoporationRepository" %>

<%
	request.setCharacterEncoding("utf-8");

	String filename="";
	String location="";
	String comparison="";
	String realFolder="C:\\upload";
	int maxSize=5 * 1024 * 1024;
	String encType = "utf-8";
	
	MultipartRequest multi = new MultipartRequest(request, realFolder, maxSize, encType, new DefaultFileRenamePolicy());
	
	String copId=multi.getParameter("copId");
	String cname=multi.getParameter("cname");
	String representative=multi.getParameter("representative");
	String officeAddress=multi.getParameter("officeAddress");
	String formOfBusiness=multi.getParameter("formOfBusiness");
	String industry=multi.getParameter("industry");
	String foundationDate=multi.getParameter("foundationDate");
	String certification=multi.getParameter("certification");
	String sales=multi.getParameter("sales");
	String avgIncome=multi.getParameter("avgIncome");
	String employee=multi.getParameter("employee");
	
	String cpId;
	
	if(copId.isEmpty()){
		cpId="ForeignCorporation";
	}else{
		cpId=copId;
	}
	
	
	Integer fDate;
	
	if(foundationDate.isEmpty()){
		fDate=0;
	}else{
		fDate=Integer.valueOf(foundationDate);
	}
	
	
	
	Enumeration files = multi.getFileNames();
	String fname = (String) files.nextElement();
	String fileName = multi.getFilesystemName(fname);
	
	CoporationRepository dao = CoporationRepository.getInstance();
	
	Coporation newCorp = new Coporation();
	newCorp.setCopId(cpId);
	newCorp.setCname(cname);
	newCorp.setRepresentative(representative);
	newCorp.setOfficeAddress(officeAddress);
	newCorp.setFormOfBusiness(formOfBusiness);
	newCorp.setIndustry(industry);
	newCorp.setFoundationDate(fDate);
	newCorp.setCertification(certification);
	newCorp.setAvgIncome(avgIncome);
	newCorp.setSales(sales);
	newCorp.setEmployee(employee);
	newCorp.setFileName(fileName);
	newCorp.setLocation(location);
	newCorp.setComparison(comparison);
	
	dao.addCorporation(newCorp);
	
	response.sendRedirect("corporations.jsp");
%>