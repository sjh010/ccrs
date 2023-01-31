package com.mobileleader.image.db.mapper;

import com.mobileleader.image.db.dto.ImageStoreDto;

/**
 * IMAGE_STORE Table DAO
 *  
 * @author bkcho
 * @since 2019.06.24
 *
 */
public interface ImageStoreMapper {
	
	/*
	 * select One
	 */
	public ImageStoreDto selectByPrimaryKey(String mainKey);
	
	/*
	 * mappingCount 값 구하여 update
	 */
	public int updateWithMappingCount(ImageStoreDto imageStoreDto);
	
	/*
	 * mappingCount 값 구하여  insert
	 */
	public int insertWithMappingCount(ImageStoreDto imageStoreDto);

}
