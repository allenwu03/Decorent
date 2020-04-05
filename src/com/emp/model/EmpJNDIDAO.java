package com.emp.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.authority.model.AuthorityJDBCDAO;
import com.authority.model.AuthorityVO;


public class EmpJNDIDAO implements EmpDAO_interface {

	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = 
			"INSERT INTO EMPLOYEE(EMP_NO, EMP_PHOTO,EMP_NAME, EMP_EMAIL, EMP_ACCOUNT, EMP_PASSWORD) VALUES('E'||LPAD(to_char(employee_seq.NEXTVAL), 7, '0'), ?, ?, ?, ?, ?)";
	private static final String UPDATE = 
			"UPDATE EMPLOYEE SET EMP_PHOTO=? ,EMP_NAME = ?, EMP_EMAIL = ?, EMP_ACCOUNT = ?, EMP_PASSWORD = ? WHERE EMP_NO = ?";
	private static final String DELETE = 
			"DELETE FROM EMPLOYEE WHERE EMP_NO = ?";
	private static final String GET_ONE_STMT = 
			"SELECT * FROM EMPLOYEE WHERE EMP_NO = ?";
	private static final String GET_ACCOUNT = 
			"SELECT * FROM EMPLOYEE WHERE EMP_ACCOUNT = ?";
	private static final String GET_ALL_STMT = 
			"SELECT * FROM EMPLOYEE ORDER BY EMP_NO";

	@Override
	public void insert(EmpVO empVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setBytes(1, empVO.getEmp_photo());
			pstmt.setString(2, empVO.getEmp_name());		
			pstmt.setString(3, empVO.getEmp_email());
			pstmt.setString(4, empVO.getEmp_account());
			pstmt.setString(5, empVO.getEmp_password());
			pstmt.executeUpdate();
			
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void update(EmpVO empVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setBytes(1, empVO.getEmp_photo());
			pstmt.setString(2, empVO.getEmp_name());		
			pstmt.setString(3, empVO.getEmp_email());
			pstmt.setString(4, empVO.getEmp_account());
			pstmt.setString(5, empVO.getEmp_password());
			pstmt.setString(6, empVO.getEmp_no());

			pstmt.executeUpdate();

		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void delete(String emp_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {			
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, emp_no);

			pstmt.executeUpdate();

			// Handle any driver errors
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public EmpVO findByPrimaryKey(String empno) {
		EmpVO empVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, empno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				empVO = new EmpVO();
				empVO.setEmp_no(rs.getString("emp_no"));
				empVO.setEmp_photo(rs.getBytes("emp_photo"));
				empVO.setEmp_name(rs.getString("emp_name"));
				empVO.setEmp_email(rs.getString("emp_email"));
				empVO.setEmp_account(rs.getString("emp_account"));
				empVO.setEmp_password(rs.getString("emp_password"));
			}

			// Handle any driver errors
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return empVO;
	}
	
	@Override
	public EmpVO findByAccount(String emp_account) {
		EmpVO empVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ACCOUNT);
			
			pstmt.setString(1, emp_account);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				empVO = new EmpVO();
				empVO.setEmp_no(rs.getString("emp_no"));
				empVO.setEmp_photo(rs.getBytes("emp_photo"));
				empVO.setEmp_name(rs.getString("emp_name"));
				empVO.setEmp_email(rs.getString("emp_email"));
				empVO.setEmp_account(rs.getString("emp_account"));
				empVO.setEmp_password(rs.getString("emp_password"));
			}
			
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return empVO;
	}	


	@Override
	public List<EmpVO> getAll() {
		List<EmpVO> list = new ArrayList<EmpVO>();
		EmpVO empVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
	
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				empVO = new EmpVO();
				empVO.setEmp_no(rs.getString("emp_no"));
				empVO.setEmp_photo(rs.getBytes("emp_photo"));
				empVO.setEmp_name(rs.getString("emp_name"));
				empVO.setEmp_email(rs.getString("emp_email"));			
				empVO.setEmp_account(rs.getString("emp_account"));
				empVO.setEmp_password(rs.getString("emp_password"));				
				list.add(empVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
	
	@Override
	public void insertAuthority(EmpVO empVO, List<AuthorityVO> list) {
		Connection con = null;
		PreparedStatement pstmt = null;
	
		try {
			con = ds.getConnection();
			
			con.setAutoCommit(false);
			//先新增員工
			String[] cols = {"EMP_NO"};
			pstmt = con.prepareStatement(INSERT_STMT,cols);			
			pstmt.setBytes(1, empVO.getEmp_photo());
			pstmt.setString(2, empVO.getEmp_name());
			pstmt.setString(3, empVO.getEmp_email());
			pstmt.setString(4, empVO.getEmp_account());
			pstmt.setString(5, empVO.getEmp_password());			
			pstmt.executeUpdate();
			//選取對應的自增主鍵值
			String next_empno = null;
			ResultSet rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				next_empno = rs.getString(1);
				System.out.println("自增主鍵值=" + next_empno + "(剛新增成功的員工編號)");
			}else {
				System.out.println("未取得自增主鍵值");
			}
			rs.close();
			// 在同時新增權限
			AuthorityJDBCDAO dao = new AuthorityJDBCDAO();
			System.out.println("list.size()-A="+list.size());
			for(AuthorityVO aAuth : list) {
				aAuth.setEmp_no(new String(next_empno));
				dao.insert2(aAuth, con);
			}
			
			// 2●設定於 pstm.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);
			System.out.println("list.size()-B="+list.size());
			System.out.println("新增員工編號" + next_empno + "時,共有權限" + list.size() + "個被新增");
			

		}catch(SQLException se) {
			if(con!=null) {
				try {
					con.rollback();
				}catch(SQLException excep) {
					throw new RuntimeException("Rollback error ocurred " 
							+ excep.getMessage());
				}
			}
			throw new RuntimeException("Database error ocurred " 
					+ se.getMessage());
		}finally{
			if(pstmt!=null) {
				try {
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con!=null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}
}

