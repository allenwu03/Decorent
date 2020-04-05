package com.emp.model;

import java.util.*;

import com.authority.model.AuthorityDAO;
import com.authority.model.AuthorityDAO_interface;
import com.authority.model.AuthorityJDBCDAO;
import com.authority.model.AuthorityVO;
import com.emp.model.EmpJDBCDAO;
import com.emp.model.EmpVO;

import java.sql.*;

public class EmpJDBCDAO implements EmpDAO_interface{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String userid = "DA105G3";
	String passwd = "123456";

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
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setBytes(1, empVO.getEmp_photo());
			pstmt.setString(2, empVO.getEmp_name());		
			pstmt.setString(3, empVO.getEmp_email());
			pstmt.setString(4, empVO.getEmp_account());
			pstmt.setString(5, empVO.getEmp_password());
			pstmt.executeUpdate();
			
		}catch (ClassNotFoundException e){
			throw new RuntimeException("Couldn't load database driver." + e.getMessage());
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		}finally {
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setBytes(1, empVO.getEmp_photo());
			pstmt.setString(2, empVO.getEmp_name());		
			pstmt.setString(3, empVO.getEmp_email());
			pstmt.setString(4, empVO.getEmp_account());
			pstmt.setString(5, empVO.getEmp_password());
			pstmt.setString(6, empVO.getEmp_no());

			pstmt.executeUpdate();

		
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, emp_no);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public EmpVO findByPrimaryKey(String emp_no) {
		EmpVO empVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, emp_no);

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
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ACCOUNT);

			pstmt.setString(1, emp_account);

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
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
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
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
			
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
			

		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver " 
					+ e.getMessage());
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
	
	
	
	public static void main(String[] args) {
		EmpJDBCDAO dao = new EmpJDBCDAO();
//------------自增主鍵值綁定--------------------//			
		EmpVO empVO = new EmpVO();
		empVO.setEmp_photo(null);
		empVO.setEmp_name("吳222");
		empVO.setEmp_email("222@email.com");
		empVO.setEmp_account("222");
		empVO.setEmp_password("222");
		
		//準備置入多筆權限
		List<AuthorityVO> testList = new ArrayList<AuthorityVO>();
		AuthorityVO authXX = new AuthorityVO(); // 權限POJO1
		authXX.setFeatures_no("FE000007");
		
		AuthorityVO authYY = new AuthorityVO(); // 權限POJO2
		authYY.setFeatures_no("FE000008");
		
		AuthorityVO authZZ = new AuthorityVO(); // 權限POJO3
		authZZ.setFeatures_no("FE000009");
		
		testList.add(authXX);
		testList.add(authYY);
		testList.add(authZZ);
		
		dao.insertAuthority(empVO, testList);
		
//------------------------------------------------//		
		// 新增
//		EmpVO empVO1 = new EmpVO();
//		empVO1.setEmp_photo(null);
//		empVO1.setEmp_name("吳9");
//		empVO1.setEmp_email("999@aaa.com");
//		empVO1.setEmp_account("999");
//		empVO1.setEmp_password("999");
//		dao.insert(empVO1);
		
		// 修改
//		EmpVO empVO2 = new EmpVO();
//		empVO2.setEmp_no("E0000002");
//		empVO2.setEmp_photo(null);
//		empVO2.setEmp_email("222@abc.com");
//		empVO2.setEmp_name("吳神2");
//		empVO2.setEmp_account("222");
//		empVO2.setEmp_password("222");
//		dao.update(empVO2);
		
//		// 刪除
//		dao.delete("E0000004");
//		
//		// 單筆查詢
//		EmpVO empVO3 = dao.findByPrimaryKey("E0000001");
//		System.out.print(empVO3.getEmp_no() + ",");
//		System.out.println(empVO3.getEmp_photo() + ",");
//		System.out.print(empVO3.getEmp_email() + ",");
//		System.out.print(empVO3.getEmp_name() + ",");
//		System.out.print(empVO3.getEmp_account() + ",");
//		System.out.println(empVO3.getEmp_password() );
//		System.out.println("---------------------");
		
//		// 多筆查詢
//		List<EmpVO> list = dao.getAll();
//		for (EmpVO aEmp : list) {
//			System.out.print(aEmp.getEmp_no() + ",");
//			System.out.println(aEmp.getEmp_photo() + ",");
//			System.out.print(aEmp.getEmp_email() + ",");
//			System.out.print(aEmp.getEmp_name() + ",");
//			System.out.print(aEmp.getEmp_account() + ",");
//			System.out.print(aEmp.getEmp_password());
//			System.out.println();
//		}	
      }
	}	
