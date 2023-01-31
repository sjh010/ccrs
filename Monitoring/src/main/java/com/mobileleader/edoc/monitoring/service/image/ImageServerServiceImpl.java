package com.mobileleader.edoc.monitoring.service.image;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobileleader.edoc.monitoring.common.model.request.ImageSearchReqeust;
import com.mobileleader.edoc.monitoring.db.mapper.ImageStoreMapper;

@Service
public class ImageServerServiceImpl implements ImageServerService {

	private static Logger logger = LoggerFactory.getLogger(ImageServerServiceImpl.class);
	
	@Autowired
	ImageStoreMapper imageStoreMapper;
	
	@Override
	public HashMap<String, Object> searchImageStoreList(ImageSearchReqeust request) {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		int totalCount = 0;
		
		try {
			totalCount = imageStoreMapper.totalCount(request);
			
			if (totalCount > 0) {
				map.put("list", imageStoreMapper.selectList(request));
			}
			
			map.put("result", "success");
			map.put("totalCount", totalCount);
		} catch (SQLException e) {
			logger.error("SQLException", e);
			map.put("result", "fail");
		} catch (Exception e) {
			map.put("result", "fail");
			logger.error("Exception", e);
		}
		
		return map;
	}
	
	@Override
	public List<HashMap<String, Object>> getChannelStatisticsFromImageServer(String mode, String param) {
		
		try {
			if ("month".equalsIgnoreCase(mode)) {
				return imageStoreMapper.selectMonthlyChannel(param);
			} else if ("day".equalsIgnoreCase(mode)) {
				return imageStoreMapper.selectDailyChannel(param);
			}
		} catch (SQLException e) {
			logger.error("SQLException", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		
		return null;
	}
	
	@Override
	public List<HashMap<String, Object>> getBranchStatisticsFromImageServer(String mode, String param) {
		
		try {
			if ("month".equalsIgnoreCase(mode)) {
				return imageStoreMapper.selectMonthlyRank(param);

			} else if ("day".equalsIgnoreCase(mode)) {
				return imageStoreMapper.selectDailyRank(param);
			}
		} catch (SQLException e) {
			logger.error("SQLException", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}

		return null;
	}

	@Override
	public ArrayList<HashMap<String, Object>> getDepartmentList() {
		ArrayList<HashMap<String, Object>> result = new ArrayList<HashMap<String,Object>>();
		
		try {
			 result = imageStoreMapper.selectDepartmentList();
		} catch (SQLException e) {
			logger.error("SQLException", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		
		return result;
	}

}
