package com.authority.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class AuthorityDAO  implements AuthorityDAO_interface {
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
				"INSERT INTO AUTHORITY(EMP_NO, FEATURES_NO) VALUES(?, ?)";
		private static final String DELETE = "DELETE FROM AUTHORITY WHERE EMP_NO = ?";
		private static final String GET_ALL_STMT = "SELECT * FROM AUTHORITY ORDER BY EMP_NO";
		private static final String GET_BY_EMPNO = "SELECT * FROM AUTHORITY WHERE EMP_NO = ?";
		
		
		@Override
		public void insert(AuthorityVO authorityVO) {
			Connection con = null;
			PreparedStatement pstmt = null;
			
			try {
				
				con = ds.getConnection();
				pstmt = con.prepareStatement(INSERT_STMT);
				
				pstmt.setString(1, authorityVO.getEmp_no());
				pstmt.setString(2, authorityVO.getFeatures_no());
		
				pstmt.executeUpdate();
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
				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ALL_STMT);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					
					authorityVO = new AuthorityVO();
					authorityVO.setEmp_no(rs.getString("emp_no"));
					authorityVO.setFeatures_no(rs.getString("features_no"));
					
					list.add(authorityVO); // Store the row in the list
				}

				// Handle any driver errors
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
		
		
		@Override
		public List<AuthorityVO> findByEmpno(String emp_no) {
			List<AuthorityVO> list = new ArrayList<AuthorityVO>();
			AuthorityVO authorityVO = null;

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {
				con = ds.getConnection();
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
}
