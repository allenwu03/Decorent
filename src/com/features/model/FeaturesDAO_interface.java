package com.features.model;

import java.util.*;

public interface FeaturesDAO_interface {
	 public void insert(FeaturesVO featuresVO);
     public void update(FeaturesVO featuresVO);
     public void delete(String features_no);
     public FeaturesVO findByPrimaryKey(String features_no);
     public List<FeaturesVO> getAll();
}
