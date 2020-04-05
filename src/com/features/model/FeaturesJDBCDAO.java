package com.features.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.emp.model.EmpJDBCDAO;
import com.emp.model.EmpVO;

public class FeaturesJDBCDAO implements FeaturesDAO_interface{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String userid = "DA105G3";
	String passwd = "123456";
	
	private static final String INSERT_STMT = 
			"INSERT INTO FEATURES(FEATURES_NO, FEATURES) VALUES('FE'||LPAD(to_char(features_seq.NEXTVAL), 6, '0'),?)";
	private static final String UPDATE = "UPDATE FEATURES SET FEATURES = ? WHERE FEATURES_NO = ?";
	private static final String DELETE = "DELETE FROM FEATURES WHERE FEATURES_NO = ?";
	private static final String GET_ONE_STMT = "SELECT FEATURES_NO, FEATURES FROM FEATURES WHERE FEATURES_NO = ?";
	private static final String GET_ALL_STMT = "SELECT FEATURES_NO, FEATURES FROM FEATURES ORDER BY FEATURES_NO";
	

	@Override
	public void insert(FeaturesVO featuresVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, featuresVO.getFeatures());
			
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
	public void update(FeaturesVO featuresVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
					
			pstmt.setString(1, featuresVO.getFeatures());
			pstmt.setString(2, featuresVO.getFeatures_no());

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
	public void delete(String features_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, features_no);

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
	public FeaturesVO findByPrimaryKey(String features_no) {
		FeaturesVO featuresVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, features_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// featuresVO 也稱為 Domain objects
				featuresVO = new FeaturesVO ();
				featuresVO.setFeatures_no(rs.getString("features_no"));
				featuresVO.setFeatures(rs.getString("features"));
				
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
		return featuresVO;
	}

	@Override
	public List<FeaturesVO> getAll() {
		List<FeaturesVO> list = new ArrayList<FeaturesVO>();
		FeaturesVO featuresVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				featuresVO = new FeaturesVO();
				featuresVO.setFeatures_no(rs.getString("features_no"));
				featuresVO.setFeatures(rs.getString("features"));
			
				list.add(featuresVO); // Store the row in the list
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
	
	public static void main(String[] args) {
		FeaturesJDBCDAO dao = new FeaturesJDBCDAO();
		
		// 新增
//		FeaturesVO featuresVO1 = new FeaturesVO();
//		featuresVO1.setFeatures("會員系統");
//	
//		dao.insert(featuresVO1);
		
		// 修改
//		FeaturesVO featuresVO2 = new FeaturesVO();
//		featuresVO2.setFeatures_no("FE000010");
//		featuresVO2.setFeatures("員工系統");
//	
//		dao.update(featuresVO2);

		
//		// 刪除
//		dao.delete("FE000010");
//		
//		// 查詢
		FeaturesVO featuresVO3 = dao.findByPrimaryKey("FE000001");
		System.out.print(featuresVO3.getFeatures_no() + ",");
		System.out.println(featuresVO3.getFeatures());
		
		System.out.println("---------------------");
//		
//		// 查詢
		List<FeaturesVO> list = dao.getAll();
		for (FeaturesVO aFEA : list) {
			System.out.print(aFEA.getFeatures_no() + ",");
			System.out.print(aFEA.getFeatures() );

			System.out.println();
		}	
	}	
}
