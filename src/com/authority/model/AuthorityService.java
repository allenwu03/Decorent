package com.authority.model;

import java.util.List;

public class AuthorityService {
	private AuthorityDAO_interface dao;
	
	public AuthorityService() {
		dao = new AuthorityDAO();
	}
	
	//新增權限
	public AuthorityVO addAuthority(String emp_no,String features_no) {
		AuthorityVO authorityVO = new AuthorityVO();
		authorityVO.setEmp_no(emp_no);
		authorityVO.setFeatures_no(features_no);
		
		dao.insert(authorityVO);
		return authorityVO;
	}
	
	//刪除權限
	public void deleteAuthority(String emp_no) {
		dao.delete(emp_no);
	}
	
	
	public List<AuthorityVO> getAll(){
		return dao.getAll();
	}
	
	//透過員工編號找權限
	public List<AuthorityVO> findByEmpno(String emp_no){
		return dao.findByEmpno(emp_no);
	}
	
	
}
