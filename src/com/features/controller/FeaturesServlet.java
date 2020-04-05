package com.features.controller;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;


import com.features.model.FeaturesService;
import com.features.model.FeaturesVO;

public class FeaturesServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) { // 來自select_features.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("features_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入功能編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/features/select_features.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				String features_no = null;
				try {
					features_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("功能編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/features/select_features.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				FeaturesService featuresSvc = new FeaturesService();
				FeaturesVO featuresVO = featuresSvc.getOneFeatures(features_no);
				if (featuresVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/features/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("featuresVO", featuresVO); // 資料庫取出的featuresVO物件,存入req
				String url = "/back-end/features/listOneFeatures.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOnefeatures.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/features/select_features.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllFeatures.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String features_no = new String(req.getParameter("features_no"));

				/*************************** 2.開始查詢資料 ****************************************/
				FeaturesService featuresSvc = new FeaturesService();
				FeaturesVO featuresVO = featuresSvc.getOneFeatures(features_no);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("featuresVO", featuresVO); // 資料庫取出的featuresVO物件,存入req
				String url = "/back-end/features/update_features.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_features_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/features/listAllFeatures.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) { // 來自update_features_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String features_no = new String(req.getParameter("features_no").trim());
				System.out.println("features_no");

				String features = req.getParameter("features");
				System.out.println("features");
				String featuresReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (features == null ||features.trim().length() == 0) {
					errorMsgs.add("功能名稱: 請勿空白");
				} else if (!features.trim().matches(featuresReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("功能名稱: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
				}

				FeaturesVO featuresVO = new FeaturesVO();
				featuresVO.setFeatures_no(features_no);
				featuresVO.setFeatures(features);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("featuresVO", featuresVO); // 含有輸入格式錯誤的featuresVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/features/update_features_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				FeaturesService featuresSvc = new FeaturesService();
				featuresVO = featuresSvc.updateFeatures(features_no, features);

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("featuresVO", featuresVO); // 資料庫update成功後,正確的的featuresVO物件,存入req
				String url = "/back-end/features/listOneFeatures.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneFeatures.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/features/update_features_input.jsp");
				failureView.forward(req, res);
			}
		}
		
		 if ("insert".equals(action)) { // 來自addFeatures.jsp的請求  
				
				List<String> errorMsgs = new LinkedList<String>();
				// Store this set in the request scope, in case we need to
				// send the ErrorPage view.
				req.setAttribute("errorMsgs", errorMsgs);
				
				try {
					/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
					String features = req.getParameter("features");
					String featuresReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
					if (features == null || features.trim().length() == 0) {
						errorMsgs.add("功能名稱: 請勿空白");
					} else if(!features.trim().matches(featuresReg)) { //以下練習正則(規)表示式(regular-expression)
						errorMsgs.add("功能名稱: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
		            }
									

					FeaturesVO featuresVO = new FeaturesVO();
					featuresVO.setFeatures(features);

					// Send the use back to the form, if there were errors
					if (!errorMsgs.isEmpty()) {
						req.setAttribute("featuresVO",featuresVO); // 含有輸入格式錯誤的featuresVO物件,也存入req
						RequestDispatcher failureView = req
								.getRequestDispatcher("/back-end/features/addFeatures.jsp");
						failureView.forward(req, res);
						return;
					}
					
					/***************************2.開始新增資料***************************************/
					FeaturesService featuresSvc = new FeaturesService();
					featuresVO = featuresSvc.addFeatures(features);
					
					/***************************3.新增完成,準備轉交(Send the Success view)***********/
					String url = "/back-end/features/listAllFeatures.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllFeatures.jsp
					successView.forward(req, res);				
					
					/***************************其他可能的錯誤處理**********************************/
				} catch (Exception e) {
					errorMsgs.add(e.getMessage());
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/features/addFeatures.jsp");
					failureView.forward(req, res);
				}
			}
		 
		 if ("delete".equals(action)) { // 來自listAllFeatures.jsp

				List<String> errorMsgs = new LinkedList<String>();
				// Store this set in the request scope, in case we need to
				// send the ErrorPage view.
				req.setAttribute("errorMsgs", errorMsgs);
		
				try {
					/***************************1.接收請求參數***************************************/
					String features_no = new String(req.getParameter("features_no"));
					
					/***************************2.開始刪除資料***************************************/
					FeaturesService featuresSvc = new FeaturesService();
					featuresSvc.deleteFeatures(features_no);
					
					/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
					String url = "/back-end/features/listAllFeatures.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
					successView.forward(req, res);
					
					/***************************其他可能的錯誤處理**********************************/
				} catch (Exception e) {
					errorMsgs.add("刪除資料失敗:"+e.getMessage());
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/features/listAllFeatures.jsp");
					failureView.forward(req, res);
				}
			}
	}
}
