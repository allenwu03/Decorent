package com.emp.model;

import java.util.List;

import com.authority.model.AuthorityVO;

public class EmpService {
	
	private EmpDAO_interface dao;
	
	public EmpService() {
		dao = new EmpJDBCDAO();
	}
	
	public EmpVO addEmp(byte[] emp_photo,String emp_name,String emp_email, String emp_account, String emp_password) {
		
		EmpVO empVO = new EmpVO();
		
		empVO.setEmp_photo(emp_photo);
		empVO.setEmp_name(emp_name);
		empVO.setEmp_email(emp_email);
		empVO.setEmp_account(emp_account);
		empVO.setEmp_password(emp_password);
		dao.insert(empVO);
		
		return empVO;
	}
	
	public EmpVO updateEmp(String emp_no,byte[] emp_photo,String emp_name, String emp_email,String emp_account, String emp_password) {
		EmpVO empVO = new EmpVO();
		
		empVO.setEmp_no(emp_no);
		empVO.setEmp_photo(emp_photo);
		empVO.setEmp_name(emp_name);
		empVO.setEmp_email(emp_email);
		empVO.setEmp_account(emp_account);
		empVO.setEmp_password(emp_password);
		dao.update(empVO);
		
		
		return empVO;
	}
	
	public void deleteEmp(String emp_no) {
		dao.delete(emp_no);
	}

	public EmpVO getOneEmp(String emp_no) {
		return dao.findByPrimaryKey(emp_no);
	}
	
	public EmpVO getOneAccount(String emp_account) {
		return dao.findByAccount(emp_account);
	}
	
	public List<EmpVO> getAll() {
		return dao.getAll();
	}
	
	//自增主鍵 新增員工的權限
	public void insertAuthority(EmpVO empVO,List<AuthorityVO> list) {
		dao.insertAuthority(empVO, list);
	}
	
	public static void main(String[] args) {
//		EmpService dao = new EmpService();
//		
//		EmpVO empVO = dao.getOneEmp("E0000001");
//		System.out.println(empVO.getEmp_no());
//		System.out.println(empVO.getEmp_name());
//		
	}
}
