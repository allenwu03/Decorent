package com.features.model;

import java.util.List;

public class FeaturesService {
	private FeaturesDAO_interface dao;
	
	public FeaturesService() {
		dao = new FeaturesDAO();
	}
	
	public FeaturesVO addFeatures(String features) {
		FeaturesVO featuresVO = new FeaturesVO();
		
		featuresVO.setFeatures(features);
		dao.insert(featuresVO);
		
		return featuresVO;
	}
	
	public FeaturesVO updateFeatures(String features_no,String features) {
		FeaturesVO featuresVO = new FeaturesVO();
		
		featuresVO.setFeatures_no(features_no);
		featuresVO.setFeatures(features);
		dao.update(featuresVO);
		
		return featuresVO;
	}
	
	public void deleteFeatures(String features_no) {
		dao.delete(features_no);
	}
	
	public FeaturesVO getOneFeatures(String features_no) {
		return dao.findByPrimaryKey(features_no);
	}
	
	public List<FeaturesVO> getAll(){
		return dao.getAll();
	}
}
