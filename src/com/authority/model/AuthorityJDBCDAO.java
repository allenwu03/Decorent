package com.authority.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.emp.model.EmpVO;

public class AuthorityJDBCDAO implements AuthorityDAO_interface{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String userid = "DA105G3";
	String passwd = "123456";
	
	private static final String INSERT_STMT = 
			"INSERT INTO AUTHORITY(EMP_NO, FEATURES_NO) VALUES(?, ?)";
	private static final String DELETE = "DELETE FROM AUTHORITY WHERE EMP_NO = ? ";
	private static final String GET_ALL_STMT = "SELECT * FROM AUTHORITY ORDER BY EMP_NO";
	private static final String FIND_AUTHORITY = "SELECT * FROM AUTHORITY WHERE EMP_NO = ?";
	private static final String FIND_BY_PRIMARYKEY = "SELECT * FROM AUTHORITY WHERE EMP_NO=? AND FEATURES_NO=?";
	private static final String GET_BY_EMPNO = "SELECT * FROM AUTHORITY WHERE EMP_NO = ?";
	
	@Override
	public void insert(AuthorityVO authorityVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, authorityVO.getEmp_no());
			pstmt.setString(2, authorityVO.getFeatures_no());
	
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
	public List<AuthorityVO> findByEmpno(String emp_no) {
		List<AuthorityVO> list = new ArrayList<AuthorityVO>();
		AuthorityVO authorityVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
			pstmt = con.prepareStatement(GET_BY_EMPNO);
			
			pstmt.setString(1, emp_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				authorityVO = new AuthorityVO();
				authorityVO.setEmp_no(rs.getString("emp_no"));
				authorityVO.setFeatures_no(rs.getString("features_no"));
				
				list.add(authorityVO); // Store the row in the list
			}

			// Handle any driver errors
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors)
		}catch (SQLException se) {
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

//	public AuthorityVO findByPrimaryKey(String emp_no, String features_no) {
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		AuthorityVO authorityVO = null;
//		
//		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
//			pstmt = con.prepareStatement(FIND_BY_PRIMARYKEY);
//			
//			pstmt.setString(1, emp_no);
//			pstmt.setString(2, features_no);
//			
//			rs = pstmt.executeQuery();
//			
//			if(rs.next()) {
//				authorityVO = new AuthorityVO();
//				authorityVO.setEmp_no(rs.getString("emp_no"));
//				authorityVO.setFeatures_no(rs.getString("features_no"));
//			}
//			
//		}catch(ClassNotFoundException e) {
//			throw new RuntimeException("Could't load database driver " + e.getMessage());	
//		}catch(SQLException se) {
//			throw new RuntimeException("Database error ocurred " + se.getMessage());
//		}finally {
//			if(rs!=null) {
//				try {
//					rs.close();
//				}catch(SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if(pstmt!=null) {
//				try {
//					pstmt.close();
//				}catch(SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if(con!=null) {
//				try {
//					con.close();
//				}catch(Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//		
//		
//		return authorityVO;
//	}

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
	public List<AuthorityVO> getAll() {
		List<AuthorityVO> list = new ArrayList<AuthorityVO>();
		AuthorityVO authorityVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				authorityVO = new AuthorityVO();
				authorityVO.setEmp_no(rs.getString("emp_no"));
				authorityVO.setFeatures_no(rs.getString("features_no"));
				
				list.add(authorityVO); // Store the row in the list
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
	
	
	//尋找員工權限
//	public AuthorityVO findAuthority(String emp_no) {
//		AuthorityVO authVO = null;
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url,userid,passwd);
//			pstmt = con.prepareStatement(FIND_AUTHORITY);
//			
//			pstmt.setString(1, emp_no);
//			rs = pstmt.executeQuery();
//			
//			while(rs.next()) {
//				authVO = new AuthorityVO();
//				authVO.setEmp_no(rs.getString("emp_no"));
//				authVO.setFeatures_no(rs.getString("features_no"));
//			}
//			
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. "
//					+ e.getMessage());
//			// Handle any SQL errors
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. "
//					+ se.getMessage());
//			// Clean up JDBC resources
//		} finally {
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//		return authVO;
//	}


	@Override
	public void insert2(AuthorityVO authorityVO, Connection con) {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, authorityVO.getEmp_no());
			pstmt.setString(2, authorityVO.getFeatures_no());
			
			pstmt.executeUpdate();
			// Handle any SQL errors
		}catch(SQLException se) {
			if(con!=null) {
				try {
					con.rollback();
				}catch(SQLException excep) {
					throw new RuntimeException("Rollback error occured" +
							excep.getMessage()); 
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
		}
		
	}


	public static void main(String[] args) {
		AuthorityVO authority = new AuthorityVO();
		AuthorityJDBCDAO dao = new AuthorityJDBCDAO();
		
		// 新增
//		authority.setEmp_no("E0000002");
//		authority.setFeatures_no("FE000001");
//		
//		dao.insert(authority);
		
		//刪除
//		dao.delete("E0000003");
		
		
//		// 多筆查詢
//			List<AuthorityVO> list = dao.getAll();
//			for (AuthorityVO aAuthority : list) {
//				System.out.print(aAuthority.getEmp_no() + ",");
//				System.out.print(aAuthority.getFeatures_no() );
//				
//				System.out.println();
//	}
		//查詢權限
		List<AuthorityVO> authority2 = dao.findByEmpno("E0000002");
		for(AuthorityVO auth : authority2) {
			System.out.println(auth.getFeatures_no());
			
		}
		

  }
}

