package com.mobileleader.edoc.monitoring.service;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import com.mobileleader.edoc.monitoring.common.Config;
import com.mobileleader.edoc.monitoring.common.form.EdocProcListForm;
import com.mobileleader.edoc.monitoring.common.model.EdocProcAggrItem;
import com.mobileleader.edoc.monitoring.common.type.GroupCode;
import com.mobileleader.edoc.monitoring.common.type.ProcsStepCode;
import com.mobileleader.edoc.monitoring.db.dto.EdocFileProcsMgmtDto;
import com.mobileleader.edoc.monitoring.db.dto.EdocGroupProcsErrHstrDto;
import com.mobileleader.edoc.monitoring.db.dto.EdocGroupProcsMgmtAggrDto;
import com.mobileleader.edoc.monitoring.db.dto.EdocGroupProcsMgmtDto;
import com.mobileleader.edoc.monitoring.db.dto.EdocGroupProcsStatisticsDto;
import com.mobileleader.edoc.monitoring.db.dto.InfoCodeDto;
import com.mobileleader.edoc.monitoring.db.mapper.EdocFileProcsMgmtMapper;
import com.mobileleader.edoc.monitoring.db.mapper.EdocGroupProcsErrHstrMapper;
import com.mobileleader.edoc.monitoring.db.mapper.EdocGroupProcsMgmtMapper;
import com.mobileleader.edoc.monitoring.db.mapper.InfoCodeMgmtMapper;
import com.mobileleader.edoc.monitoring.utils.DateTimeUtil;
import com.mobileleader.edoc.monitoring.utils.Tools;

@Service
public class EdocGroupProcsMgmtServiceImpl implements EdocGroupProcsMgmtService {
	
	private static Logger logger = LoggerFactory.getLogger(EdocGroupProcsMgmtServiceImpl.class);
	
	@Autowired
	private EdocGroupProcsMgmtMapper edocGroupProcsMgmtMapper;
	
	@Autowired
	private EdocGroupProcsErrHstrMapper edocGroupProcsErrHstrMapper;
	
	@Autowired
	private EdocFileProcsMgmtMapper edocFileProcsMgmtMapper;
	
	@Autowired
	private InfoCodeMgmtMapper infoCodeMgmtMapper;

	@Override
	@Transactional(readOnly=true)
	public int getProcTotalCount(EdocProcListForm edocProcListForm) {
		if (edocProcListForm.getWorkType() != null && !edocProcListForm.getWorkType().equals("")) {
			edocProcListForm.setWorkTypeStartIndex(Integer.parseInt(edocProcListForm.getWorkType())*10);
			edocProcListForm.setWorkTypeEndIndex((Integer.parseInt(edocProcListForm.getWorkType())+1)*10);
		}
		return edocGroupProcsMgmtMapper.count(edocProcListForm);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<EdocGroupProcsMgmtDto> getProcList(EdocProcListForm edocProcListForm) {
		if (edocProcListForm.getWorkType() != null && !edocProcListForm.getWorkType().equals("")) {
			edocProcListForm.setWorkTypeStartIndex(Integer.parseInt(edocProcListForm.getWorkType())*10);
			edocProcListForm.setWorkTypeEndIndex((Integer.parseInt(edocProcListForm.getWorkType())+1)*10);
		}
		return edocGroupProcsMgmtMapper.selectAll(edocProcListForm);
	}
	
	@Override
	@Transactional(readOnly=true)
	public EdocGroupProcsMgmtDto getProcDetail(String elecDocGroupInexNo) {
		return edocGroupProcsMgmtMapper.select(elecDocGroupInexNo);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<EdocProcAggrItem> getProcAggregate(EdocProcListForm edocProcListForm) {
		
		List<EdocGroupProcsMgmtAggrDto> aggrInfoList = edocGroupProcsMgmtMapper.aggregate(edocProcListForm);
		
		List<EdocProcAggrItem> aggrItemList = new ArrayList<EdocProcAggrItem>();
//		EdocProcAggrItem aggrItem00 = new EdocProcAggrItem(ProcsStepCode.STEP00); //
		EdocProcAggrItem aggrItem10 = new EdocProcAggrItem(ProcsStepCode.STEP10);
		EdocProcAggrItem aggrItem20 = new EdocProcAggrItem(ProcsStepCode.STEP20);
		EdocProcAggrItem aggrItem30 = new EdocProcAggrItem(ProcsStepCode.STEP30);
//		EdocProcAggrItem aggrItem40 = new EdocProcAggrItem(ProcsStepCode.STEP40); //
//		EdocProcAggrItem aggrItem50 = new EdocProcAggrItem(ProcsStepCode.STEP50); //
//		EdocProcAggrItem aggrItem60 = new EdocProcAggrItem(ProcsStepCode.STEP60); //
		EdocProcAggrItem aggrItem70 = new EdocProcAggrItem(ProcsStepCode.STEP70);
		EdocProcAggrItem aggrItemTotal = new EdocProcAggrItem("99", "합계");
		
//		aggrItemList.add(aggrItem00); //
		aggrItemList.add(aggrItem10);
		aggrItemList.add(aggrItem20);
		aggrItemList.add(aggrItem30);
//		aggrItemList.add(aggrItem40); //
//		aggrItemList.add(aggrItem50); //
//		aggrItemList.add(aggrItem60); //
		aggrItemList.add(aggrItem70);
		aggrItemList.add(aggrItemTotal);
		
		for(EdocGroupProcsMgmtAggrDto aggrInfo : aggrInfoList) {
			String stepCd = aggrInfo.getProcsStepCd();
			String stepStCd = aggrInfo.getProcsStepStcd();
			int itemCount = aggrInfo.getItemCount();
			
			ProcsStepCode stepCode = ProcsStepCode.getByCode(stepCd);
			switch (stepCode) {
//			case STEP00: aggrItem00.setCountByStCd(stepStCd, itemCount); break; //
			case STEP10: aggrItem10.setCountByStCd(stepStCd, itemCount); break;
			case STEP20: aggrItem20.setCountByStCd(stepStCd, itemCount); break;
			case STEP30: aggrItem30.setCountByStCd(stepStCd, itemCount); break;
//			case STEP40: aggrItem40.setCountByStCd(stepStCd, itemCount); break; //
//			case STEP50: aggrItem50.setCountByStCd(stepStCd, itemCount); break; //
//			case STEP60: aggrItem60.setCountByStCd(stepStCd, itemCount); break; //
			case STEP70: aggrItem70.setCountByStCd(stepStCd, itemCount); break;
			default:
				logger.warn("Unknown StepCode: {}", stepCd);
				break;
			}
			
		}
		
		int countReady = 0;
		int countSuccess = 0;
		int countFail = 0;
		int countTotal = 0;
		for(EdocProcAggrItem aggrItem : aggrItemList) {
			countReady += aggrItem.getCountReady();
			countSuccess += aggrItem.getCountSuccess();
			countFail += aggrItem.getCountFail();
			countTotal += aggrItem.getCountTotal();
		}
		aggrItemTotal.setCountReady(countReady);
		aggrItemTotal.setCountSuccess(countSuccess);
		aggrItemTotal.setCountFail(countFail);
		aggrItemTotal.setCountTotal(countTotal);
		
		return aggrItemList;
	}
	
	@Override
	@Transactional(readOnly=true)
	public int getProcErrorHistoryTotalCount(EdocProcListForm edocProcListForm) {
		return edocGroupProcsErrHstrMapper.count(edocProcListForm);
	}

	@Override
	@Transactional(readOnly=true)
	public List<EdocGroupProcsErrHstrDto> getProcErrorHistoryList(EdocProcListForm edocProcListForm) {
		return edocGroupProcsErrHstrMapper.selectAll(edocProcListForm);
	}

	@Override
	@Transactional(readOnly=true)
	public int getProcFileTotalCount(EdocProcListForm edocProcListForm) {
		return edocFileProcsMgmtMapper.count(edocProcListForm);
	}

	@Override
	@Transactional(readOnly=true)
	public List<EdocFileProcsMgmtDto> getProcFileList(EdocProcListForm edocProcListForm) {
		return edocFileProcsMgmtMapper.selectAll(edocProcListForm);
	}

	@Override
	@Transactional
	public int retryProcessing(List<String> edocIdxList) {
		int result = -1;
	
		for (String elecDocGroupInexNo : edocIdxList) {
			EdocGroupProcsMgmtDto detail = edocGroupProcsMgmtMapper.select(elecDocGroupInexNo);
			logger.debug("retryProcessing() detail toString : {}", detail.toString()); 
			
			int procsStepCd = Tools.sTOi(detail.getProcsStepCd());
			logger.debug("retryProcessing() procsStepCd : {}", procsStepCd);
			
			// 취소거래일 경우, 별도 URL 체크  
			if (procsStepCd == 60) {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				List<MediaType> listMediaType = new ArrayList<MediaType>();
				listMediaType.add(MediaType.APPLICATION_JSON);
				headers.setAccept(listMediaType);
				List<Charset> listCharset = new ArrayList<Charset>();
				listCharset.add(Charset.forName("UTF-8"));
				headers.setAcceptCharset(listCharset);
				headers.setCacheControl("no-cache");
				headers.setPragma("no-cache");
				
				HttpEntity<String> entity = new HttpEntity<String>(headers);
				
				RestTemplate restTemplate = new RestTemplate();
				
				String url = Config.getRetryCheckUrl()+"?goto"+Config.getRetryCheckGoto()+"&guid=";
				logger.debug("retryProcessing() check url : {}", url); 
				
				ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class, new HashMap<String, String>());
				
				HttpStatus responseStatus = responseEntity.getStatusCode();
				logger.debug("retryProcessing() response status code : {}", responseStatus);
		
				// check validResponse
				if (responseStatus != null && responseStatus.value() >= HttpStatus.OK.value() && responseStatus.value() < HttpStatus.MULTIPLE_CHOICES.value()) {
					String responseBody = responseEntity.getBody();	
					logger.debug("retryProcessing() responseEntity.getBody() : {}", responseBody);
					if (responseBody.indexOf("guid") != -1) {
						logger.debug("retryProcessing() retry check guid exist.");
						// 신복위
						//result = edocGroupProcsMgmtMapper.updateBprTrnmYnToReprocessing(detail.getGuid());	
					}
					
				}
			} 
			
			result = edocGroupProcsMgmtMapper.updateProcsStepToReprocessing(elecDocGroupInexNo);
		}
		
		return result;
	}

	@Override
	@Transactional(readOnly=true)
	public List<EdocGroupProcsStatisticsDto> getStatisticsList(EdocProcListForm edocProcListForm) {
		return edocGroupProcsMgmtMapper.selectStatistics(edocProcListForm);
	}

	@Override
	public HashMap<String, Object> downloadExcel(EdocProcListForm edocProcListForm) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<EdocGroupProcsStatisticsDto> list = edocGroupProcsMgmtMapper.selectStatistics(edocProcListForm);
		
		Workbook workbook = makeStatisticsExcel(list, edocProcListForm);
		map.put("workbook", workbook);
		map.put("filename", "edoc_monitoring_statistics_"+DateTimeUtil.getCurrentTime(0));
		
		return map;
	}
	
	private Workbook makeStatisticsExcel(List<EdocGroupProcsStatisticsDto> list, EdocProcListForm edocProcListForm) {
		Workbook wb = new XSSFWorkbook();
		
		// create a new sheet
		XSSFSheet sheet = (XSSFSheet) wb.createSheet("statistics");
		
		// declare a row object reference
		XSSFRow row = null;
		
		// declare a cell object reference
		XSSFCell cell = null;
		
		// options
		boolean isCheckedDsrbCd = edocProcListForm.getDsrbCdChk() != null && !edocProcListForm.getDsrbCdChk().equals("") && edocProcListForm.getDsrbCdChk().equalsIgnoreCase("on") ? true : false;				// 영업점
		boolean isCheckedHndrNo = edocProcListForm.getHndrNoChk() != null && !edocProcListForm.getHndrNoChk().equals("") && edocProcListForm.getHndrNoChk().equalsIgnoreCase("on") ? true : false;				// 직원번호
		boolean isCheckedWorkType = edocProcListForm.getWorkTypeChk() != null && !edocProcListForm.getWorkTypeChk().equals("") && edocProcListForm.getWorkTypeChk().equalsIgnoreCase("on") ? true : false;			// 업무유형
		boolean isCheckedBzwkDvcd = edocProcListForm.getInsourceIdChk() != null && !edocProcListForm.getInsourceIdChk().equals("") && edocProcListForm.getInsourceIdChk().equalsIgnoreCase("on") ? true : false;			// 업무
		
		
		//
		isCheckedBzwkDvcd = false;
		//
		
		
		
		int cellWidthIdx = 0;		// 컬럼
		int rowIdx = 0;
		int cellIdx = 0;
		
		// column width
		cellWidthIdx = 0;
		if (isCheckedDsrbCd) {
			sheet.setColumnWidth(cellWidthIdx++, 4000);
			sheet.setColumnWidth(cellWidthIdx++, 6000);
		}
		if (isCheckedHndrNo) sheet.setColumnWidth(cellWidthIdx++, 4000);
		if (isCheckedWorkType) sheet.setColumnWidth(cellWidthIdx++, 5000);
		if (isCheckedBzwkDvcd) sheet.setColumnWidth(cellWidthIdx++, 5000);
		sheet.setColumnWidth(cellWidthIdx++, 3000);		// 총건수
		sheet.setColumnWidth(cellWidthIdx++, 3000);		// 처리완료
		sheet.setColumnWidth(cellWidthIdx++, 3000);		// 진행중
		sheet.setColumnWidth(cellWidthIdx++, 3000);		// 에러
		
		// font style 생성
		Font titleFont = wb.createFont();
		titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		titleFont.setFontHeightInPoints((short)24);

		Font headerFont = wb.createFont();
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

		Font periodFont = wb.createFont();

		Font infoFont = wb.createFont();

		Font infoTotalFont = wb.createFont();
		infoTotalFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

		Font dateFont = wb.createFont();

		Font numberFont = wb.createFont();

		Font valueFont = wb.createFont();

		Font percentFont = wb.createFont();

		Font numberTotalFont = wb.createFont();
		numberTotalFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

		Font valueTotalFont = wb.createFont();
		valueTotalFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

		Font percentTotalFont = wb.createFont();
		percentTotalFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

		Font timeFont = wb.createFont();

		DataFormat dataFormat = wb.createDataFormat();

		// cell style 생성
		int borderColor = IndexedColors.GREY_80_PERCENT.getIndex();

		CellStyle title = wb.createCellStyle();
		title.setFont(titleFont);
		title.setAlignment(CellStyle.ALIGN_CENTER);

		CellStyle searchCond = wb.createCellStyle();
		searchCond.setFont(periodFont);
		searchCond.setAlignment(CellStyle.ALIGN_LEFT);

		CellStyle searchData = wb.createCellStyle();
		searchData.setFont(periodFont);
		searchData.setAlignment(CellStyle.ALIGN_LEFT);

		CellStyle header = wb.createCellStyle();
		header.setFont(headerFont);
		header.setAlignment(CellStyle.ALIGN_CENTER);
		header.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		header.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
		header.setFillPattern(CellStyle.SOLID_FOREGROUND);
		header.setBorderTop(CellStyle.BORDER_THIN);
		header.setTopBorderColor((short)borderColor);
		header.setBorderLeft(CellStyle.BORDER_THIN);
		header.setLeftBorderColor((short)borderColor);
		header.setBorderBottom(CellStyle.BORDER_THIN);
		header.setBottomBorderColor((short)borderColor);
		header.setBorderRight(CellStyle.BORDER_THIN);
		header.setRightBorderColor((short)borderColor);

		CellStyle info = wb.createCellStyle();
		info.setFont(infoFont);
		info.setAlignment(CellStyle.ALIGN_CENTER);
		info.setBorderTop(CellStyle.BORDER_THIN);
		info.setTopBorderColor((short)borderColor);
		info.setBorderLeft(CellStyle.BORDER_THIN);
		info.setLeftBorderColor((short)borderColor);
		info.setBorderBottom(CellStyle.BORDER_THIN);
		info.setBottomBorderColor((short)borderColor);
		info.setBorderRight(CellStyle.BORDER_THIN);
		info.setRightBorderColor((short)borderColor);

		CellStyle val = wb.createCellStyle();
		val.setFont(infoFont);
		val.setAlignment(CellStyle.ALIGN_RIGHT);
		val.setBorderTop(CellStyle.BORDER_THIN);
		val.setTopBorderColor((short)borderColor);
		val.setBorderLeft(CellStyle.BORDER_THIN);
		val.setLeftBorderColor((short)borderColor);
		val.setBorderBottom(CellStyle.BORDER_THIN);
		val.setBottomBorderColor((short)borderColor);
		val.setBorderRight(CellStyle.BORDER_THIN);
		val.setRightBorderColor((short)borderColor);

		CellStyle infoTotal = wb.createCellStyle();
		infoTotal.setFont(infoTotalFont);
		infoTotal.setAlignment(CellStyle.ALIGN_CENTER);
		infoTotal.setBorderTop(CellStyle.BORDER_THIN);
		infoTotal.setTopBorderColor((short)borderColor);
		infoTotal.setBorderLeft(CellStyle.BORDER_THIN);
		infoTotal.setLeftBorderColor((short)borderColor);
		infoTotal.setBorderBottom(CellStyle.BORDER_THIN);
		infoTotal.setBottomBorderColor((short)borderColor);
		infoTotal.setBorderRight(CellStyle.BORDER_THIN);
		infoTotal.setRightBorderColor((short)borderColor);
		infoTotal.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		infoTotal.setFillPattern(CellStyle.SOLID_FOREGROUND);

		CellStyle date = wb.createCellStyle();
		date.setFont(dateFont);
		date.setBorderTop(CellStyle.BORDER_THIN);
		date.setTopBorderColor((short)borderColor);
		date.setBorderLeft(CellStyle.BORDER_THIN);
		date.setLeftBorderColor((short)borderColor);
		date.setBorderBottom(CellStyle.BORDER_THIN);
		date.setBottomBorderColor((short)borderColor);
		date.setBorderRight(CellStyle.BORDER_THIN);
		date.setRightBorderColor((short)borderColor);

		CellStyle number = wb.createCellStyle();
		number.setFont(numberFont);
		number.setDataFormat(dataFormat.getFormat("#,##0"));
		number.setBorderTop(CellStyle.BORDER_THIN);
		number.setTopBorderColor((short)borderColor);
		number.setBorderLeft(CellStyle.BORDER_THIN);
		number.setLeftBorderColor((short)borderColor);
		number.setBorderBottom(CellStyle.BORDER_THIN);
		number.setBottomBorderColor((short)borderColor);
		number.setBorderRight(CellStyle.BORDER_THIN);
		number.setRightBorderColor((short)borderColor);

		CellStyle value = wb.createCellStyle();
		value.setFont(valueFont);
		value.setAlignment(CellStyle.ALIGN_RIGHT);
		value.setBorderTop(CellStyle.BORDER_THIN);
		value.setTopBorderColor((short)borderColor);
		value.setBorderLeft(CellStyle.BORDER_THIN);
		value.setLeftBorderColor((short)borderColor);
		value.setBorderBottom(CellStyle.BORDER_THIN);
		value.setBottomBorderColor((short)borderColor);
		value.setBorderRight(CellStyle.BORDER_THIN);
		value.setRightBorderColor((short)borderColor);
		value.setDataFormat(dataFormat.getFormat("#,##0"));
		
		CellStyle percent = wb.createCellStyle();
		percent.setFont(percentFont);
		percent.setDataFormat(dataFormat.getFormat("0.00"));
		percent.setBorderTop(CellStyle.BORDER_THIN);
		percent.setTopBorderColor((short)borderColor);
		percent.setBorderLeft(CellStyle.BORDER_THIN);
		percent.setLeftBorderColor((short)borderColor);
		percent.setBorderBottom(CellStyle.BORDER_THIN);
		percent.setBottomBorderColor((short)borderColor);
		percent.setBorderRight(CellStyle.BORDER_THIN);
		percent.setRightBorderColor((short)borderColor);

		CellStyle numberTotal = wb.createCellStyle();
		numberTotal.setFont(numberTotalFont);
		numberTotal.setDataFormat(dataFormat.getFormat("#,##0"));
		numberTotal.setBorderTop(CellStyle.BORDER_THIN);
		numberTotal.setTopBorderColor((short)borderColor);
		numberTotal.setBorderLeft(CellStyle.BORDER_THIN);
		numberTotal.setLeftBorderColor((short)borderColor);
		numberTotal.setBorderBottom(CellStyle.BORDER_THIN);
		numberTotal.setBottomBorderColor((short)borderColor);
		numberTotal.setBorderRight(CellStyle.BORDER_THIN);
		numberTotal.setRightBorderColor((short)borderColor);
		numberTotal.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		numberTotal.setFillPattern(CellStyle.SOLID_FOREGROUND);

		CellStyle valueTotal = wb.createCellStyle();
		valueTotal.setFont(valueTotalFont);
		valueTotal.setAlignment(CellStyle.ALIGN_RIGHT);
		valueTotal.setBorderTop(CellStyle.BORDER_THIN);
		valueTotal.setTopBorderColor((short)borderColor);
		valueTotal.setBorderLeft(CellStyle.BORDER_THIN);
		valueTotal.setLeftBorderColor((short)borderColor);
		valueTotal.setBorderBottom(CellStyle.BORDER_THIN);
		valueTotal.setBottomBorderColor((short)borderColor);
		valueTotal.setBorderRight(CellStyle.BORDER_THIN);
		valueTotal.setRightBorderColor((short)borderColor);

		valueTotal.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		valueTotal.setFillPattern(CellStyle.SOLID_FOREGROUND);

		CellStyle percentTotal = wb.createCellStyle();
		percentTotal.setFont(percentTotalFont);
		percentTotal.setDataFormat(dataFormat.getFormat("0.00"));
		percentTotal.setBorderTop(CellStyle.BORDER_THIN);
		percentTotal.setTopBorderColor((short)borderColor);
		percentTotal.setBorderLeft(CellStyle.BORDER_THIN);
		percentTotal.setLeftBorderColor((short)borderColor);
		percentTotal.setBorderBottom(CellStyle.BORDER_THIN);
		percentTotal.setBottomBorderColor((short)borderColor);
		percentTotal.setBorderRight(CellStyle.BORDER_THIN);
		percentTotal.setRightBorderColor((short)borderColor);
		percentTotal.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		percentTotal.setFillPattern(CellStyle.SOLID_FOREGROUND);

		CellStyle time = wb.createCellStyle();
		time.setFont(timeFont);
		
		// 공통 코드
		List<InfoCodeDto> procsTypeCodeList = infoCodeMgmtMapper.selectGroupCodeList(GroupCode.PROCESS_TYPE_CODE.getCode());
		List<InfoCodeDto> workTypeCodeList = infoCodeMgmtMapper.selectGroupCodeList(GroupCode.WORK_TYPE_CODE.getCode());
		List<InfoCodeDto> workCodeList = infoCodeMgmtMapper.selectGroupCodeList(GroupCode.WORK_CODE.getCode());

		HashMap<String, String> procsTypeCodeMap = new HashMap<String, String>();
		for (InfoCodeDto procsTypeCode : procsTypeCodeList) {
			procsTypeCodeMap.put(procsTypeCode.getCdVal(), procsTypeCode.getCdNm());
		}
		
		HashMap<String, String> workTypeCodeMap = new HashMap<String, String>();
		for (InfoCodeDto workTypeCode : workTypeCodeList) {
			workTypeCodeMap.put(workTypeCode.getCdVal(), workTypeCode.getCdNm());
		}
		
		HashMap<String, String> workCodeMap = new HashMap<String, String>();
		for (InfoCodeDto workCode : workCodeList) {
			workCodeMap.put(workCode.getCdVal(), workCode.getCdNm());
		}
		
		// 변수 초기화 
		rowIdx = 0;
		cellIdx = 0;
		
		// 타이틀
		row = sheet.createRow(rowIdx);
		sheet.addMergedRegion(new CellRangeAddress(rowIdx, rowIdx, 0, cellWidthIdx-1));

		cell = row.createCell(cellIdx);
		cell.setCellValue("전자문서 처리통계 ");
		cell.setCellStyle(title);
		rowIdx++;		// add row

		rowIdx++;		// add row
		
		// 검색 조건
		// 조회 기간
		cellIdx = 0;
		row = sheet.createRow(rowIdx);
		cell = row.createCell(cellIdx);
		StringBuffer sbPeriod = new StringBuffer();
		sbPeriod.append("조회기간 : ");
		sbPeriod.append(edocProcListForm.getStartDate().substring(0, 8));
		sbPeriod.append(" ~ ");
		sbPeriod.append(edocProcListForm.getEndDate().substring(0, 8));
		cell.setCellValue(sbPeriod.toString());
		cell.setCellStyle(searchCond);
		sheet.addMergedRegion(new CellRangeAddress(rowIdx, rowIdx, 0, 1));
		rowIdx++;
		
		// 영업점
		if (isCheckedDsrbCd && !Tools.isNullOrEmpty(edocProcListForm.getDsrbCd())) {
			row = sheet.createRow(rowIdx);
			cell = row.createCell(cellIdx);
			cell.setCellValue("영업점 : "+edocProcListForm.getDsrbCd());
			cell.setCellStyle(searchCond);
			sheet.addMergedRegion(new CellRangeAddress(rowIdx, rowIdx, 0, 1));
			rowIdx++;
		}
		
		// 직원번호
		if (isCheckedHndrNo && !Tools.isNullOrEmpty(edocProcListForm.getHndrNo())) {
			row = sheet.createRow(rowIdx);
			cell = row.createCell(cellIdx);
			cell.setCellValue("직원번호 : "+edocProcListForm.getHndrNo());
			cell.setCellStyle(searchCond);
			sheet.addMergedRegion(new CellRangeAddress(rowIdx, rowIdx, 0, 1));
			rowIdx++;
		}
		
		// 업무유형
		if (isCheckedWorkType && !Tools.isNullOrEmpty(edocProcListForm.getWorkType())) {
			row = sheet.createRow(rowIdx);
			cell = row.createCell(cellIdx);
			cell.setCellValue("업무 : "+workTypeCodeMap.get(edocProcListForm.getWorkType()));
			//cell.setCellValue("업무 : "+workTypeCodeMap.get(edocProcListForm.getBzwkDvcd()));
			cell.setCellStyle(searchCond);
			sheet.addMergedRegion(new CellRangeAddress(rowIdx, rowIdx, 0, 1));
			rowIdx++;
		}
		
		// 업무
		if (isCheckedBzwkDvcd && !Tools.isNullOrEmpty(edocProcListForm.getInsourceId())) {
			row = sheet.createRow(rowIdx);
			cell = row.createCell(cellIdx);
			cell.setCellValue("업무 : "+workCodeMap.get(edocProcListForm.getInsourceId()));
			cell.setCellStyle(searchCond);
			sheet.addMergedRegion(new CellRangeAddress(rowIdx, rowIdx, 0, 1));
			rowIdx++;
		}
		
		rowIdx++;	// add row
		
		// 테이블 헤더
		row = sheet.createRow(rowIdx);
		cellIdx = 0;
		
		if (isCheckedDsrbCd) {
			cell = row.createCell(cellIdx);
			cell.setCellValue("영업점 코드");
			cell.setCellStyle(header);
			//sheet.addMergedRegion(new CellRangeAddress(rowIdx, rowIdx+1, cellIdx, cellIdx));
			cellIdx++;
			
			cell = row.createCell(cellIdx);
			cell.setCellValue("영업점 명");
			cell.setCellStyle(header);
			//sheet.addMergedRegion(new CellRangeAddress(rowIdx, rowIdx+1, cellIdx, cellIdx));
			cellIdx++;
		}
		
		if (isCheckedHndrNo) {
			cell = row.createCell(cellIdx);
			cell.setCellValue("직원번호");
			cell.setCellStyle(header);
			//sheet.addMergedRegion(new CellRangeAddress(rowIdx, rowIdx+1, cellIdx, cellIdx));
			cellIdx++;
		}
		
		if (isCheckedWorkType) {
			cell = row.createCell(cellIdx);
			cell.setCellValue("업무");
			cell.setCellStyle(header);
			//sheet.addMergedRegion(new CellRangeAddress(rowIdx, rowIdx+1, cellIdx, cellIdx));
			cellIdx++;
		}
		
		if (isCheckedBzwkDvcd) {
			cell = row.createCell(cellIdx);
			cell.setCellValue("업무");
			cell.setCellStyle(header);
			//sheet.addMergedRegion(new CellRangeAddress(rowIdx, rowIdx+1, cellIdx, cellIdx));
			cellIdx++;
		}
		
//		cell = row.createCell(cellIdx);
//		cell.setCellValue("PPR");
//		cell.setCellStyle(header);
//		sheet.addMergedRegion(new CellRangeAddress(rowIdx, rowIdx, cellIdx, cellIdx+3));
//		
//		rowIdx++;
//		row = sheet.createRow(rowIdx);
//		
//		cellIdx = 0;
//		if (isCheckedDsrbCd) {
//			cellIdx++;
//			cellIdx++;
//		}
//		if (isCheckedHndrNo) cellIdx++;
//		if (isCheckedWorkType) cellIdx++;
//		if (isCheckedBzwkDvcd) cellIdx++;
		
		cell = row.createCell(cellIdx);
		cell.setCellValue("총 건수");
		cell.setCellStyle(header);
		cellIdx++;
		
		cell = row.createCell(cellIdx);
		cell.setCellValue("처리완료");
		cell.setCellStyle(header);
		cellIdx++;
		
		cell = row.createCell(cellIdx);
		cell.setCellValue("진행중");
		cell.setCellStyle(header);
		cellIdx++;
		
		cell = row.createCell(cellIdx);
		cell.setCellValue("에러");
		cell.setCellStyle(header);
		cellIdx++;
		
		rowIdx++;	// add row
		
		// 데이터
		if (list != null && list.size() > 0) {
			int mergedRegion = 0;
			int totalPprCntTotal = 0;
			int totalPprCnt1 = 0;
			int totalPprCnt0 = 0;
			int totalPprCnt9 = 0;
			for (int i = 0; i < list.size(); i++) {
				EdocGroupProcsStatisticsDto item = list.get(i);
				
				row = sheet.createRow(i + rowIdx);
				cellIdx = 0;
				
				// create cell 
				if (isCheckedDsrbCd) {
					cell = row.createCell(cellIdx++);
					cell.setCellValue(item.getDsrbCd());
					cell.setCellStyle(value);
					
					cell = row.createCell(cellIdx++);
					cell.setCellValue(item.getDsrbNm());
					cell.setCellStyle(value);
				}
				
				if (isCheckedHndrNo) {
					cell = row.createCell(cellIdx++);
					cell.setCellValue(item.getHndrNm() + "(" + item.getHndrNo() + ")");
					cell.setCellStyle(value);
				}
				
				if (isCheckedWorkType) {
					cell = row.createCell(cellIdx++);
					cell.setCellValue(workTypeCodeMap.get(item.getInsourceId()));
					cell.setCellStyle(value);
				}
				
				if (isCheckedBzwkDvcd) {
					cell = row.createCell(cellIdx++);
					cell.setCellValue(workCodeMap.get(item.getInsourceId()));
					cell.setCellStyle(value);
				}
				
				cell = row.createCell(cellIdx++);
				cell.setCellValue(item.getPprCntTotal());
				cell.setCellStyle(number);
				
				cell = row.createCell(cellIdx++);
				cell.setCellValue(item.getPprCnt1());
				cell.setCellStyle(number);
				
				cell = row.createCell(cellIdx++);
				cell.setCellValue(item.getPprCnt0());
				cell.setCellStyle(number);
				
				cell = row.createCell(cellIdx++);
				cell.setCellValue(item.getPprCnt9());
				cell.setCellStyle(number);
				
				// 합계 계산
				totalPprCntTotal += item.getPprCntTotal();
				totalPprCnt1 += item.getPprCnt1();
				totalPprCnt0 += item.getPprCnt0();
				totalPprCnt9 += item.getPprCnt9();
			}
			
			if (isCheckedDsrbCd) {
				mergedRegion++;
				mergedRegion++;
			}
			if (isCheckedHndrNo) mergedRegion++;
			if (isCheckedWorkType) mergedRegion++;
			if (isCheckedBzwkDvcd) mergedRegion++;
			
			// 합계
			row = sheet.createRow(rowIdx+list.size());
			sheet.addMergedRegion(new CellRangeAddress(rowIdx+list.size(), rowIdx+list.size(), 0, mergedRegion-1));
			cellIdx = 0;
			cell = row.createCell(cellIdx);
			cell.setCellValue("합계");
			cell.setCellStyle(infoTotal);
			
			cellIdx = mergedRegion;
			cell = row.createCell(cellIdx++);
			cell.setCellValue(totalPprCntTotal);
			cell.setCellStyle(numberTotal);
			
			cell = row.createCell(cellIdx++);
			cell.setCellValue(totalPprCnt1);
			cell.setCellStyle(numberTotal);
			
			cell = row.createCell(cellIdx++);
			cell.setCellValue(totalPprCnt0);
			cell.setCellStyle(numberTotal);
			
			cell = row.createCell(cellIdx++);
			cell.setCellValue(totalPprCnt9);
			cell.setCellStyle(numberTotal);
		}
		
		return wb;
	}
	
	@Override
	@Transactional
	public int getRegistEcmProcTotalCount(EdocProcListForm edocProcListForm) {
		if (edocProcListForm.getWorkType() != null && !edocProcListForm.getWorkType().equals("")) {
			edocProcListForm.setWorkTypeStartIndex(Integer.parseInt(edocProcListForm.getWorkType())*10);
			edocProcListForm.setWorkTypeEndIndex((Integer.parseInt(edocProcListForm.getWorkType())+1)*10);
		}
		return edocGroupProcsMgmtMapper.countRegistEcm(edocProcListForm);
	}

	@Override
	@Transactional
	public List<EdocGroupProcsMgmtDto> getRegistEcmProcList(EdocProcListForm edocProcListForm) {
		if (edocProcListForm.getWorkType() != null && !edocProcListForm.getWorkType().equals("")) {
			edocProcListForm.setWorkTypeStartIndex(Integer.parseInt(edocProcListForm.getWorkType())*10);
			edocProcListForm.setWorkTypeEndIndex((Integer.parseInt(edocProcListForm.getWorkType())+1)*10);
		}
		return edocGroupProcsMgmtMapper.selectAllRegistEcm(edocProcListForm);
	}

	@Override
	public EdocFileProcsMgmtDto getProcFile(String elecDocGroupInexNo, int fileSeqNo) {
		return edocFileProcsMgmtMapper.select(elecDocGroupInexNo, fileSeqNo);
	}

    @Override
    @Transactional
    public String getLocalStoragePdfFilePath(String fileId) {
        logger.info("getLocalStorageFilePath() fileId={}", fileId);
        String[] _fileId = fileId.split("_");
        String bizType = _fileId[0];
        logger.info("getLocalStorageFilePath() bizType={}", bizType);
        
        String pdfFilePath = null;
        if ("EDOC".equalsIgnoreCase(bizType)) {
            String elecDocGroupInexNo = _fileId[1];
            int fileSeqNo = Integer.parseInt(_fileId[2]);
            
            logger.info("getLocalStorageFilePath() elecDocGroupInexNo={}", elecDocGroupInexNo);
            logger.info("getLocalStorageFilePath() fileSeqNo={}", fileSeqNo);
            
            EdocGroupProcsMgmtDto detail = edocGroupProcsMgmtMapper.select(elecDocGroupInexNo);
            EdocFileProcsMgmtDto procFile = edocFileProcsMgmtMapper.select(elecDocGroupInexNo, fileSeqNo);
            
            String dsrbCd = (detail != null ? detail.getDsrbCd() : "");
            if (detail != null && procFile != null) {
                // 로컬파일경로 정책 - /PPRDATA/EDS/WORK/ 생성일(yyyymmdd)/지점번호/업무키/pdf/파일seq/파일명
                Date crtnTime = procFile.getCrtnTime();
                String crtnDate = DateTimeUtil.getDateString(crtnTime);
                String pdfFileName = procFile.getPdfFileNm();
                
                pdfFilePath = crtnDate + File.separator + dsrbCd 
                        + File.separator + elecDocGroupInexNo + File.separator + "pdf" + File.separator + fileSeqNo + File.separator + pdfFileName;
                logger.debug("pdfFilePath={}", pdfFilePath);
            } else {
                logger.error("DB result has no data.");
            }
        } else {
            // TODO : 새로운 테이블 정보에서 구성하는 경우
            // 1) fileId 포맷 -> [bizType]_[searchKey]....
            // 2) DTO 추가
            // 3) mapper 추가
            // 4) 상단에 mapper @Autowired 추가
            // 5) select 실행
            // 6) 조회된 정보로 path 구성
        }

        return pdfFilePath;
    }
    
	/*
	@Override
	public String getLocalStoragePdfFilePath(String fileId) {
		logger.info("getLocalStorageFilePath() fileId={}", fileId);
		String[] _fileId = fileId.split("_");			// fileId = elecDocGroupInexNo_fileSeqNo
		
		String elecDocGroupInexNo = _fileId[0];
		int fileSeqNo = Integer.parseInt(_fileId[1]);
		logger.info("getLocalStorageFilePath() elecDocGroupInexNo={}", elecDocGroupInexNo);
		logger.info("getLocalStorageFilePath() fileSeqNo={}", fileSeqNo);
		
		String pdfFilePath = null;
		if (elecDocGroupInexNo != null && !elecDocGroupInexNo.equals("") && fileSeqNo > 0) {
			EdocFileProcsMgmtDto procFile = edocFileProcsMgmtMapper.select(elecDocGroupInexNo, fileSeqNo);
			
			String crtnDate = null;
			String dsrbCd = null;
			if (procFile != null) {
				// [로컬파일경로 정책] 
				// 년월일(8)/지점코드(4)/전자문서키(32)/pdf/파일일련번호/전자문서키_파일일련번호_서식코드.pdf
				// 문서키구성 - 년월일(8) EDS 지점코드(4) 직원번호(7) DB일련번호(10) 	ex. 20190212EDS0005d1801930000000102
				String pdfFileName = procFile.getPdfFileNm();
				crtnDate = elecDocGroupInexNo.substring(0, 8);
				dsrbCd = elecDocGroupInexNo.substring(11, 15);
				
				pdfFilePath = crtnDate + File.separator + dsrbCd + File.separator + elecDocGroupInexNo + File.separator + 
						"pdf" + File.separator + fileSeqNo + File.separator + pdfFileName;
				logger.debug("pdfFilePath={}", pdfFilePath);
			} else {
				logger.error("DB result has no data.");
			}
		}

		return pdfFilePath;
	}
    */
}
