package dto;

import java.io.Serializable;

public class Coporation implements Serializable {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5172686590827527119L;

	private String copId;
	private String cname;
	private String representative;
	private String officeAddress;
	private String formOfBusiness;
	private String industry;
	private Integer foundationDate;
	private String certification;
	private String fileName;
	private String sales;
	private String location;
	private String avgIncome;
	private String comparison;
	private String employee;
	
	
	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public String getSales() {
		return sales;
	}

	public void setSales(String sales) {
		this.sales = sales;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAvgIncome() {
		return avgIncome;
	}

	public void setAvgIncome(String avgIncome) {
		this.avgIncome = avgIncome;
	}

	public String getComparison() {
		return comparison;
	}

	public void setComparison(String comparison) {
		this.comparison = comparison;
	}

	public Coporation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Coporation(String copId, String cname) {
		this.copId = copId;
		this.cname = cname;
	}

	public String getCopId() {
		return copId;
	}

	public void setCopId(String copId) {
		this.copId = copId;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getRepresentative() {
		return representative;
	}

	public void setRepresentative(String representative) {
		this.representative = representative;
	}

	public String getOfficeAddress() {
		return officeAddress;
	}

	public void setOfficeAddress(String officeAddress) {
		this.officeAddress = officeAddress;
	}

	public String getFormOfBusiness() {
		return formOfBusiness;
	}

	public void setFormOfBusiness(String formOfBusiness) {
		this.formOfBusiness = formOfBusiness;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public Integer getFoundationDate() {
		return foundationDate;
	}

	public void setFoundationDate(Integer foundationDate) {
		this.foundationDate = foundationDate;
	}

	public String getCertification() {
		return certification;
	}

	public void setCertification(String certification) {
		this.certification = certification;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}	
	
	
}
