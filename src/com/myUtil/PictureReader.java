package com.myUtil;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.designer.model.DesignerService;
import com.designer_portfolio_picture.model.Designer_portfolio_pictureService;
import com.designer_portfolio_picture.model.Designer_portfolio_pictureVO;
import com.emp.model.EmpService;
import com.mem.model.MemService;
import com.product.model.ProductService;
import com.productphoto.model.ProductPhotoService;
//import com.product.model.ProductService;
//import com.rent.model.RentService;
import com.rent.model.RentService;

public class PictureReader extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("image/gif");
		ServletOutputStream out = response.getOutputStream(); 
		HttpSession session = request.getSession();
		
		try {
			String primaryKey = "";
			byte[] buf = null;
			
			Enumeration<String> primaryKeyE = request.getParameterNames();
			if(primaryKeyE.hasMoreElements()) {
				primaryKey = primaryKeyE.nextElement();
			}
			
			String value = request.getParameter(primaryKey);
			
			switch(primaryKey) {
				case "wrongImg" :  // 當需要存錯誤圖片時候
					buf = (byte[])session.getAttribute("wrongImg");
					break;
				case "mem_no" : 
					MemService memSvc = new MemService();
					buf = memSvc.getOneMem(value).getMem_photo();
					break;
				case "productno" :
					ProductService productSvc = new ProductService();
					buf = productSvc.getPrimarykey(value).getProductphoto();
					break;	
				case "productPhotoNo" :
					ProductPhotoService productPhotoSvc = new ProductPhotoService();
					buf = productPhotoSvc.findByPrimaryKey(value).getProductPhoto();
					break;	
				case "rent_no" :
					RentService rentSvc = new RentService();
					buf = rentSvc.getOneRent(value).getRent_image();
					break;
				case "designer_no":
					DesignerService designerSvc = new DesignerService();
					buf = designerSvc.getOneDesigner(value).getDesigner_verify_info();
					break;
				case "des_polio_pic_no"://單圖
					Designer_portfolio_pictureService des_polio_picSvc = new Designer_portfolio_pictureService();
					buf = des_polio_picSvc.getOneDes_polio_pic(value).getDes_polio_pic();
					break;
				case "emp_no" : 
					EmpService empSvc = new EmpService();
					//System.out.println(value);
					buf = empSvc.getOneEmp(value).getEmp_photo();
					break;
				case "des_polio_no": //多圖
					Designer_portfolio_pictureService des_polio_picSvc2 = new Designer_portfolio_pictureService();
					List<Designer_portfolio_pictureVO> multiBuf = des_polio_picSvc2.findByDesPoliono(value);
					Integer pic_index = null;
					String index = request.getParameter("index");
					try {
						pic_index = new Integer(index);
					}catch(NumberFormatException nfe) {
						nfe.printStackTrace();
					}
					buf = multiBuf.get(pic_index).getDes_polio_pic();
					break;
			}
			
			// 壓縮圖片
			String shrink = request.getParameter("shrink");
			if(shrink!=null) {
				try {
					int shrinkScale = new Integer(shrink);
					buf = shrink(buf,shrinkScale);
				}catch(Exception e) {
					System.out.println("Shrink error");
				}
			}
			
			out.write(buf);
			
		}catch(IOException | NullPointerException e) {
			System.out.println("error" + e.getMessage());
			String path = getServletContext().getRealPath("/front-end/article/images/none.jpg");
			BufferedInputStream br = new BufferedInputStream(new FileInputStream(path));
			byte[] buf = new byte[br.available()];
			int len = 0;
			if((len=br.read(buf))!=-1) {
				out.write(buf);
			}
		}
	}
	
	// 小吳老師的壓縮圖片的工具, 直接導來用, 先測試
	public static byte[] shrink(byte[] srcImageData, int scaleSize) {
		ByteArrayInputStream bais = new ByteArrayInputStream(srcImageData);
		// 縮小比例，4代表除以4
		int sampleSize = 1;
		int imageWidth = 0;
		int imageHeight = 0;

		// 如果imageSize為0、負數(代表錯誤)或1(1代表與原圖一樣大小)，就直接回傳原圖資料
		if (scaleSize <= 1) {
			return srcImageData;
		}

		try {
			BufferedImage srcBufferedImage = ImageIO.read(bais);
			// 如果無法識別圖檔格式(TYPE_CUSTOM)就回傳TYPE_INT_ARGB；否則回傳既有格式
			int type = srcBufferedImage.getType() == BufferedImage.TYPE_CUSTOM ? BufferedImage.TYPE_INT_RGB
					: srcBufferedImage.getType();
			imageWidth = srcBufferedImage.getWidth();
			imageHeight = srcBufferedImage.getHeight();
			if (imageWidth == 0 || imageHeight == 0) {
				return srcImageData;
			}
			// 只要圖檔較長的一邊超過指定長度(desireSize)，就計算欲縮小的比例
			// 並將圖檔寬高都除以欲縮小比例
			int longer = Math.max(imageWidth, imageHeight);
			if (longer > scaleSize) {
				sampleSize = longer / scaleSize;
				imageWidth = srcBufferedImage.getWidth() / sampleSize;
				imageHeight = srcBufferedImage.getHeight() / sampleSize;
			}
			BufferedImage scaledBufferedImage = new BufferedImage(imageWidth,
					imageHeight, type);
			Graphics graphics = scaledBufferedImage.createGraphics();
			graphics.drawImage(srcBufferedImage, 0, 0, imageWidth, imageHeight,
					null);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(scaledBufferedImage, "jpg", baos);
			return baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			return srcImageData;
		}
	}

}
