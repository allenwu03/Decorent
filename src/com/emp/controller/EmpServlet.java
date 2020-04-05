package com.emp.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;


import com.authority.model.AuthorityVO;
import com.emp.model.*;
import com.emp.model.EmpService;
import com.emp.model.EmpVO;
import com.myUtil.MailService;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@MultipartConfig
public class EmpServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest req,HttpServletResponse res)
			throws ServletException,IOException{
		doPost(req,res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8"); 
		String action = req.getParameter("action");
		
		HttpSession session = req.getSession();
		
		//員工登出
		if("emp_logout".equals(action)) {
			
			session.invalidate();
			System.out.println("登出成功");
			PrintWriter out = res.getWriter();
			out.println("true");
			
			String url = "/back-end/emp/empLogin.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
			successView.forward(req, res);
		}
		
		
		if ("getOne_For_Display".equals(action)) { // 來自select_emp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("emp_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入員工編號");
				}
				// 輸入錯誤時返回原畫面
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/select_emp.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String emp_no = null;
				try {
					emp_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("員工編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/select_emp.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				EmpService empSvc = new EmpService();
				EmpVO empVO = empSvc.getOneEmp(emp_no);
				if (empVO == null) {
					errorMsgs.add("查無資料");
				}
				// 輸入錯誤時返回原畫面
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/select_emp.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("empVO", empVO); // 資料庫取出的empVO物件,存入req
				String url = "/back-end/emp/listOneEmp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/select_emp.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String emp_no = new String(req.getParameter("emp_no"));
				
				/***************************2.開始查詢資料****************************************/
				EmpService empSvc = new EmpService();
				EmpVO empVO = empSvc.getOneEmp(emp_no);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("empVO", empVO);         // 資料庫取出的empVO物件,存入req
				String url = "/back-end/emp/update_emp_input.jsp";
//				String url = "/back-end/emp/listAllEmp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/emp/listAllEmp.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				
				String emp_no_ori = null;
				EmpVO empVO_ori = (EmpVO)session.getAttribute("empVO");
				emp_no_ori = empVO_ori.getEmp_no();
				
				String emp_no = new String(req.getParameter("emp_no").trim());
				//名稱驗證
				String emp_name = req.getParameter("emp_name");
				String emp_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (emp_name == null || emp_name.trim().length() == 0) {
					errorMsgs.add("員工姓名: 請勿空白");
				} else if(!emp_name.trim().matches(emp_nameReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("員工姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
	            }
				//信箱驗證
				String emp_email = req.getParameter("emp_email");
				if(emp_email == null || emp_email.trim().length() == 0){
					errorMsgs.add("信箱請勿空白");
				}
				//帳號驗證
				String emp_account = req.getParameter("emp_account").trim();
				if (emp_account == null || emp_account.trim().length() == 0) {
					errorMsgs.add("帳號請勿空白");
				}
				//密碼驗證
				String emp_password = req.getParameter("emp_password").trim();
				if (emp_password == null || emp_password.trim().length() == 0) {
					errorMsgs.add("密碼請勿空白");
				}
				//照片驗證
				EmpService empSvc = new EmpService();
				InputStream in = req.getPart("emp_photo").getInputStream();
				byte[] emp_photo = null;
				if(in.available() != 0) {
					emp_photo = new byte[in.available()];
					in.read(emp_photo);
					in.close();
					System.out.println("Here");
				}else {
					emp_photo = empSvc.getOneEmp(emp_no).getEmp_photo();
				}
				EmpVO empVO = new EmpVO();
				empVO.setEmp_no(emp_no);
				empVO.setEmp_photo(emp_photo);
				empVO.setEmp_name(emp_name);
				empVO.setEmp_email(emp_email);
				empVO.setEmp_account(emp_account);
				empVO.setEmp_password(emp_password);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("empVO", empVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/update_emp_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				empSvc.updateEmp(emp_no, emp_photo,emp_name, emp_email, emp_account, emp_password);
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				if(!emp_no_ori.equals(emp_no)) {
					req.setAttribute("empVO", empVO); // 資料庫update成功後,正確的的empVO物件,存入req
				}else {
					session.setAttribute("empVO", empVO);
				}
				String url = "/back-end/emp/listAllEmp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/emp/update_emp_input.jsp");
				failureView.forward(req, res);
			}
		}

		
		 if ("insert".equals(action)) { // 來自addEmp.jsp的請求  
				
				List<String> errorMsgs = new LinkedList<String>();
				// Store this set in the request scope, in case we need to
				// send the ErrorPage view.
				req.setAttribute("errorMsgs", errorMsgs);
				
				try {
					/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
					
					//照片驗證
					InputStream in = req.getPart("emp_photo").getInputStream();
					byte[] emp_photo = null;
					if(in.available() != 0) {
						emp_photo = new byte[in.available()];
						in.read(emp_photo);
						
						in.close();
					}else {
						errorMsgs.add("圖片請上傳");
					}
					//名稱驗證
					String emp_name = req.getParameter("emp_name");
					String emp_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
					if (emp_name == null || emp_name.trim().length() == 0) {
						errorMsgs.add("員工姓名: 請勿空白");
					} else if(!emp_name.trim().matches(emp_nameReg)) { //以下練習正則(規)表示式(regular-expression)
						errorMsgs.add("員工姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
		            }
					//信箱驗證
					String emp_email = req.getParameter("emp_email");
					if(emp_email == null || emp_email.trim().length() == 0){
						errorMsgs.add("信箱請勿空白");
					}
					//帳號驗證
					String emp_account = req.getParameter("emp_account").trim();
					if (emp_account == null || emp_account.trim().length() == 0) {
						errorMsgs.add("帳號請勿空白");
					}
					//密碼驗證 驗證信箱時不要開
//					String emp_password = req.getParameter("emp_password").trim();
//					if (emp_password == null || emp_password.trim().length() == 0) {
//						errorMsgs.add("密碼請勿空白");
//					}
					
					//功能驗證
					String features_no[] = req.getParameterValues("features_no");
					String str = "";
					List<String> features = new ArrayList<String>();
					
					
					List<AuthorityVO> testList = new ArrayList<AuthorityVO>();
					
					if(features_no == null) {
						errorMsgs.add("未勾選功能");
					}else {
						for(int i = 0; i<features_no.length; i++) {
							str =features_no[i];
							AuthorityVO authXX = new AuthorityVO();
							authXX.setFeatures_no(str);
							testList.add(authXX);
						}
					}
					
					
					
					EmpVO empVO = new EmpVO();
					
					empVO.setEmp_photo(emp_photo);
					empVO.setEmp_name(emp_name);
					empVO.setEmp_email(emp_email);
					empVO.setEmp_account(emp_account);
//					empVO.setEmp_password(emp_password);
					
					
					// Send the use back to the form, if there were errors
					if (!errorMsgs.isEmpty()) {
						req.setAttribute("empVO", empVO); // 含有輸入格式錯誤的empVO物件,也存入req
						RequestDispatcher failureView = req
								.getRequestDispatcher("/back-end/emp/addEmp.jsp");
						failureView.forward(req, res);
						return;
					}
					
					
					/***********************信箱內容***************************************/
					
					//要傳入的email帳號
//					 String to = "allenwu032016@gmail.com";
					
					//要傳入的email帳號
					 String to = emp_email;	
					 
				     String subject = "密碼通知";
				     String emp_no = req.getParameter("emp_no");
				     
				     
				     String passRandom = genAuthCode(emp_no);
				     empVO.setEmp_password(passRandom);//放入隨機生成的密碼
				     
				     String messageText = "Hello! " + emp_name + " 請謹記此密碼: " + passRandom + "\n" +" (已經啟用)"; 
				       
				      MailService mailService = new MailService();
				      mailService.sendMail(to, subject, messageText);
														
				      
				      
					/***************************2.開始新增資料***************************************/
					EmpService empSvc = new EmpService();
//					empVO = empSvc.addEmp(emp_photo,emp_name, emp_email,emp_account, passRandom);
//					empVO = empSvc.addEmp(emp_photo,emp_name, emp_email,emp_account, emp_password); //沒信箱驗證碼時開啟	
					empSvc.insertAuthority(empVO, testList);
					/***************************3.新增完成,準備轉交(Send the Success view)***********/
					req.setAttribute("empVO", empVO);
					String url = "/back-end/emp/listAllEmp.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
					successView.forward(req, res);				
					
					/***************************其他可能的錯誤處理**********************************/
				} catch (Exception e) {
					errorMsgs.add(e.getMessage());
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/emp/addEmp.jsp");
					failureView.forward(req, res);
				}
			}
		 
			
			if ("delete".equals(action)) { // 來自listAllEmp.jsp

				List<String> errorMsgs = new LinkedList<String>();
				// Store this set in the request scope, in case we need to
				// send the ErrorPage view.
				req.setAttribute("errorMsgs", errorMsgs);
		
				try {
					/***************************1.接收請求參數***************************************/
					String emp_no = new String(req.getParameter("emp_no"));
					
					/***************************2.開始刪除資料***************************************/
					EmpService empSvc = new EmpService();
					empSvc.deleteEmp(emp_no);
					
					/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
					String url = "/back-end/emp/listAllEmp.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
					successView.forward(req, res);
					
					/***************************其他可能的錯誤處理**********************************/
				} catch (Exception e) {
					errorMsgs.add("刪除資料失敗:"+e.getMessage());
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/emp/listAllEmp.jsp");
					failureView.forward(req, res);
				}
			}
			
			if("emp_account".equals(action)) {
				List<String> errorMsgs = new LinkedList<String>();
				req.setAttribute("errorMsgs", errorMsgs);

				//取得使用者的帳密
				String emp_account = req.getParameter("emp_account");
				String emp_password = req.getParameter("emp_password");
		
				//檢查帳號 是否有效
				if(!checkUserAcccount(emp_account)) {
					//帳號無效
					errorMsgs.add("帳號錯誤");
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/empLogin.jsp");
					failureView.forward(req, res);
				}else if(!checkUserPassword(emp_account,emp_password)) {
					//密碼無效
					errorMsgs.add("密碼錯誤");
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/empLogin.jsp");
					failureView.forward(req, res);
				}else {
					// 帳密有效才執行，才將帳密存入session
					session.setAttribute("emp_account", emp_account); // *工作1: 才在session內做已經登入過的標識
					session.setAttribute("emp_password", emp_password);
					EmpService empSrc = new EmpService();
					EmpVO empVO = empSrc.getOneAccount(emp_account);
					session.setAttribute("empVO", empVO);
					
					try {
						//location在com.myUtil的empLoginFilter裡
						String location = (String) session.getAttribute("location");
						if(location != null) {
							// 假如直接進入被保護的網頁會被要求登入會員
							// 而登入後即可進入被保護的網頁，而不是重回首頁
							session.removeAttribute("location");// *工作2: 看看有無來源網頁 (-->如有來源網頁:則重導至來源網頁)
							res.sendRedirect(location);
							return;
		
						}
					}catch(Exception ignored) {		
					}
					// *工作3:
					// (-->如無來源網頁:則重導至backIndex.jsp)
					res.sendRedirect(req.getContextPath() + "/back-end/backIndex.jsp");
				}
			}
	}
		
//-----------------------------------------自訂方法----------------------------------------------
			// Check if user account correct
				public boolean checkUserAcccount(String emp_account) {

					EmpService empSrc = new EmpService();
					EmpVO empVO = empSrc.getOneAccount(emp_account);

					if (empVO == null) {
						return false;
					} else {
						return true;
					}
				}

			// Check if user password correct
				public boolean checkUserPassword(String emp_account, String emp_password) {

					EmpService empSrc = new EmpService();
					EmpVO empVO = empSrc.getOneAccount(emp_account);
					if (!emp_password.equals(empVO.getEmp_password())) {
						return false;
					} else {
						return true;
					}
				}
				
			// 信箱驗證碼	
				public String genAuthCode(String emp_no) {
					StringBuffer buffer = new StringBuffer();
					int i = 0;
										
					while(i < 8) {
						int x = (int)(Math.random()*123);
						if((x >=48 && x <=57) || (x >=65 && x<=90) || (x >=97 && x<=122)) {
							buffer.append((char)x);
							i++;
						}else {
							continue;
						}
					}
					String authcode = buffer.toString();					
					return authcode;
				}
				
}
