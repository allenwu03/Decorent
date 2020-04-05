package com.authority.model;

import java.sql.Connection;
import java.util.*;

public interface AuthorityDAO_interface {
	public void insert(AuthorityVO authorityVO); // 新增權限
	public void delete(String emp_no); // 刪除權限
	public List<AuthorityVO> getAll(); // 查詢所有權限
	public List<AuthorityVO> findByEmpno(String emp_no); // 查詢所有權限
	
	
	public void insert2(AuthorityVO authorityVO,Connection con);
}
