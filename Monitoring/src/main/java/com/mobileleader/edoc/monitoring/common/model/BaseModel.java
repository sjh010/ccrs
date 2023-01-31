package com.mobileleader.edoc.monitoring.common.model;
 

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
 

public class BaseModel implements Serializable {
	
	/**
	 *
	 */
	private static final long serialVersionUID = -5647709141399889059L;
	/**
	 *
	 * Table 공통 column 변수 정의
	 */
	
	
	
	//jQuery 관련 변수
	
	private int startIndex;
	private int endIndex;
	private String sortIndex;
	private String sortOrder;

	/** 현재페이지 */
	private int pageIndex = 1;

	/** 페이지갯수 */
	private int pageUnit = 10;

	/** 페이지사이즈 */
	private int pageSize = 10;

	/** firstIndex */
	private int firstIndex = 1;

	/** lastIndex */
	private int lastIndex = 1;

	/** recordCountPerPage */
	private int recordCountPerPage = 10;
	
	/**
	 * 검색날짜
	 */
	
	private String regStartDate;
	private String regEndDate;
	private String modStartDate;
	private String modEndDate;

	/**
	 * @return the pageIndex
	 */
	public int getPageIndex() {
		return pageIndex;
	}

	/**
	 * @param pageIndex the pageIndex to set
	 */
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	/**
	 * @return the pageUnit
	 */
	public int getPageUnit() {
		return pageUnit;
	}

	/**
	 * @param pageUnit the pageUnit to set
	 */
	public void setPageUnit(int pageUnit) {
		this.pageUnit = pageUnit;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the firstIndex
	 */
	public int getFirstIndex() {
		return firstIndex;
	}

	/**
	 * @param firstIndex the firstIndex to set
	 */
	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}

	/**
	 * @return the lastIndex
	 */
	public int getLastIndex() {
		return lastIndex;
	}

	/**
	 * @param lastIndex the lastIndex to set
	 */
	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}

	/**
	 * @return the recordCountPerPage
	 */
	public int getRecordCountPerPage() {
		return recordCountPerPage;
	}

	/**
	 * @param recordCountPerPage the recordCountPerPage to set
	 */
	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
	}

	/**
	 *
	 * 0 값과 null 값을 구분하기 위하여 프로그램 로직에서 사용하는 null 값 xsql에서는 #{nullValueInt}로 참조
	 * 하면된다.
	 */
	public static final int nullValueInt = -999999;

	public static final long nullValueLong = -999999;

	public static int getNvlInt(Integer obj) {
		if (obj == null) {
			return nullValueInt;
		} else {
			return obj.intValue();
		}
	}

	public static long getNvlLong(Long obj) {
		if (obj == null) {
			return nullValueInt;
		} else {
			return obj.longValue();
		}
	}

	public static int getNvlInt(Integer obj, int defaultValue) {
		if (obj == null) {
			return defaultValue;
		} else {
			return obj.intValue();
		}
	}

	public static long getNvlLong(Long obj, long defaultValue) {
		if (obj == null) {
			return defaultValue;
		} else {
			return obj.longValue();
		}
	}

	public static String getNvlString(String obj, String defaultValue) {
		if (obj == null || obj.length() <= 0) {
			return defaultValue;
		} else {
			return obj;
		}
	}



	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static long getNullValueLong() {
		return nullValueLong;
	}

	public static int getNullValueInt() {
		return nullValueInt;
	}

	public static String arrayToString(long[] array) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < array.length; i++) {
			if (i > 0) {
				stringBuilder.append(";");
			}
			stringBuilder.append(String.valueOf(array[i]));
		}
		return stringBuilder.toString();
	}
	
	

	/**
	 * @return the startIndex
	 */
	public int getStartIndex() {
		return startIndex;
	}

	/**
	 * @param startIndex the startIndex to set
	 */
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	/**
	 * @return the endIndex
	 */
	public int getEndIndex() {
		return endIndex;
	}

	/**
	 * @param endIndex the endIndex to set
	 */
	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	/**
	 * @return the sortIndex
	 */
	public String getSortIndex() {
		return sortIndex;
	}

	/**
	 * @param sortIndex the sortIndex to set
	 */
	public void setSortIndex(String sortIndex) {
		this.sortIndex = sortIndex;
	}

	/**
	 * @return the sortOrder
	 */
	public String getSortOrder() {
		return sortOrder;
	}

	/**
	 * @param sortOrder the sortOrder to set
	 */
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Override
	public String toString() {
		return getReflectionToString(this);
	}

	/**
	 * Model class 의 field 를 문자열로 정리하여 반환함.
	 *
	 * @param object
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String getReflectionToString(Object object) {
		Class clazz = object.getClass();
		Field[] fields = clazz.getDeclaredFields();
		StringBuilder sb = new StringBuilder();
		for (Field field : fields) {
			field.setAccessible(true);
			sb.append(field.getName());
			sb.append(" = ");
			try {
				boolean needBLR = (!field.getType().isPrimitive()
						&& !field.getType().equals(String.class)
						&& !field.getType().equals(Date.class) && !field
						.getType().equals(BigDecimal.class));

				if (needBLR) {
					sb.append("{ ");
				}
				sb.append(field.get(object));
				if (needBLR) {
					sb.append(" }");
				}
			} catch (IllegalArgumentException e) {
				sb.append("IllegalArgumentException occured!!");
				sb.append(e.toString());
			} catch (IllegalAccessException e) {
				sb.append("IllegalAccessException occured!!");
				sb.append(e.toString());
			}
			sb.append(";").append("\n");
		}
		return sb.toString();
	}

	/**
	 * @return the regStartDate
	 */
	public String getRegStartDate() {
		return regStartDate;
	}

	/**
	 * @param regStartDate the regStartDate to set
	 */
	public void setRegStartDate(String regStartDate) {
		this.regStartDate = regStartDate;
	}

	/**
	 * @return the regEndDate
	 */
	public String getRegEndDate() {
		return regEndDate;
	}

	/**
	 * @param regEndDate the regEndDate to set
	 */
	public void setRegEndDate(String regEndDate) {
		this.regEndDate = regEndDate;
	}

	/**
	 * @return the modStartDate
	 */
	public String getModStartDate() {
		return modStartDate;
	}

	/**
	 * @param modStartDate the modStartDate to set
	 */
	public void setModStartDate(String modStartDate) {
		this.modStartDate = modStartDate;
	}

	/**
	 * @return the modEndDate
	 */
	public String getModEndDate() {
		return modEndDate;
	}

	/**
	 * @param modEndDate the modEndDate to set
	 */
	public void setModEndDate(String modEndDate) {
		this.modEndDate = modEndDate;
	}


}
