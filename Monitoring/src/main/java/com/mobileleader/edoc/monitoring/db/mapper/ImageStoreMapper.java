package com.mobileleader.edoc.monitoring.db.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mobileleader.edoc.monitoring.common.model.request.ImageSearchReqeust;
import com.mobileleader.image.db.dto.ImageStoreDto;

public interface ImageStoreMapper {
	
	public int totalCount(ImageSearchReqeust request) throws Exception;
	
	public ArrayList<ImageStoreDto> selectList(ImageSearchReqeust request) throws Exception;

	public List<HashMap<String, Object>> selectMonthlyRank(@Param("month") String month) throws Exception;
	
	public List<HashMap<String, Object>> selectDailyRank(@Param("day") String day) throws Exception;
	
	public List<HashMap<String, Object>> selectMonthlyChannel(@Param("month") String month) throws Exception;
	
	public List<HashMap<String, Object>> selectDailyChannel(@Param("day") String day) throws Exception;

	public ArrayList<HashMap<String, Object>> selectDepartmentList() throws Exception;
}
