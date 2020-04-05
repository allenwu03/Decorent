package com.emp.model;

import java.util.*;

import com.authority.model.AuthorityVO;

public interface EmpDAO_interface {
	 public void insert(EmpVO empVO);
     public void update(EmpVO empVO);
     public void delete(String emp_no);
     public EmpVO findByPrimaryKey(String emp_no);
     public EmpVO findByAccount(String emp_account);
     public List<EmpVO> getAll();
     
     //同時新增員工與權限
     public void insertAuthority(EmpVO empVO,List<AuthorityVO> list);
}

