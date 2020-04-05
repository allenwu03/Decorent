package com.authority.model;

import java.io.Serializable;

public class AuthorityVO implements Serializable{
	private String emp_no;
	private String features_no;
	
	public String getEmp_no() {
		return emp_no;
	}
	public void setEmp_no(String emp_no) {
		this.emp_no = emp_no;
	}
	public String getFeatures_no() {
		return features_no;
	}
	public void setFeatures_no(String features_no) {
		this.features_no = features_no;
	}	
}
