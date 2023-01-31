package com.mobileleader.edoc.service;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mobileleader.edoc.db.dto.EdocFileProcsMgmtDto;
import com.mobileleader.edoc.db.dto.EdocGrpBzwkInfoDto;
import com.mobileleader.edoc.db.dto.EdocGrpProcsMgmtDto;
import com.mobileleader.edoc.db.mapper.EdocFileProcsMgmtMapper;
import com.mobileleader.edoc.exception.EdocServerException;
import com.mobileleader.edoc.exception.EdocServerStatus;
import com.mobileleader.edoc.model.data.DataCfgVo;
import com.mobileleader.edoc.model.data.FldrDataVo;
import com.mobileleader.edoc.model.data.GuidFormcodeMainFormcodeVo;
import com.mobileleader.edoc.model.response.UploadResponse;
import com.mobileleader.edoc.properties.EdocServiceProp;
import com.mobileleader.edoc.type.ProcsStepCd;
import com.mobileleader.edoc.type.ProcsStepMsgCd;
import com.mobileleader.edoc.type.ProcsStepStCd;
import com.mobileleader.edoc.util.DataCfgUtils;
import com.mobileleader.edoc.util.DateUtils;
import com.mobileleader.edoc.util.StringUtils;
import com.mobileleader.edoc.util.ZipUtils;


/**
 * 전자문서그룹 생성 및 처리 Class
 * 
 * 1. 전자문서그룹 생성 및 처리 : 클라이언트의 요청을 받아서 DB에 저장하는 작업
 * 2. PC전자문서작성기에서 업로드 된 ZIP 파일 처리 : 압축 해제. Data.cfg 파일과 결과XML의 서식코드 비교.
 * 3. ZIP 파일 구성 : Data.cfg + 결과XML들(전자서식에 입력된 정보) + 첨부파일들
 * 4. 연결거래(전자서식 하나에 여러개의 GUID가 연결) 처리 : GUID 별로 별도의 전자문서키 발급하여 별도의 전자문서건으로 처리.
 * 
 * @author yh.kim
 *
 */

@Service("uploadEdocData")
public class UploadEdocDataService extends UploadServiceAbstract{
	
	private Logger logger = LoggerFactory.getLogger(UploadEdocDataService.class);
	
	@Autowired
	private EdocFileProcsMgmtMapper fileProcsMgmtMapper;
	

	/*
	 * 업로드된 ZIP 파일 저장 경로
	 * $(DATA_HOME_PATH)/[yyyyMMdd]/[부점코드]/[전자문서키]/
	 */
	private String folderPath(EdocGrpBzwkInfoDto bzwkInfoDto) {
		String folderPath = EdocServiceProp.DATA_HOME_PATH()	
							+ DateUtils.toString(bzwkInfoDto.getRegTime(), null)	+ File.separator
							+ bzwkInfoDto.getDsrbCd()								+ File.separator
							+ bzwkInfoDto.getElecDocGroupInexNo()					+ File.separator
							;
		return folderPath;
	}
	
	
	@Override
	@Transactional(isolation=Isolation.READ_COMMITTED, propagation=Propagation.NESTED, rollbackFor=Exception.class)
	public UploadResponse upload(EdocGrpBzwkInfoDto bzwkInfoVo, MultipartFile file) throws Exception {
		logger.info("[Upload EdocData Service - START]");
		
		UploadResponse response = new UploadResponse();
		String edocIdxNo = bzwkInfoVo.getElecDocGroupInexNo();
		
		bzwkInfoVo.setRegTime(new Date());																// 파일 업로드 시각 (서버에 파일 저장한 시각)
		
		try {
			// 1. 전자문서그룹 처리상태 확인
			EdocGrpProcsMgmtDto procsMgmtVo = chkEdocProcessingStatus(edocIdxNo);			
			procsMgmtVo.setProcsStepStTime(new Date());													// 프로세스 시작시각
			
			// 2. ZIP 파일 처리 
			String resultXmlPath = processZipFile(bzwkInfoVo, file);
			
			// 3. Data.cfg 처리
			DataCfgVo dataCfg = processDataCfg(resultXmlPath);
			
			// 4. GUID~서식코드 연결 관계 확인 & 연결 관계 지정되지 않은 서식코드는 모든 GUID와 연결
			List<GuidFormcodeMainFormcodeVo> guidFormcodeMainFormcodeVos = DataCfgUtils.generateGuidFormcodeMainFormcodes(dataCfg);
			if(guidFormcodeMainFormcodeVos == null) {
				throw new EdocServerException(EdocServerStatus.INVALID_GUID_FORMCODE_LINK);
			}
			
			// 전자문서 그룹 관리정보저장 하기위해 procsMgmtVo에 처리 결과 set
			procsMgmtVo.setProcsStepCd       	(ProcsStepCd.INPUT_DATA_VERIFICATION.getCdVal());		// 처리단계코드 		PROCS_STEP_CD : 10
			procsMgmtVo.setProcsStepStcd		(ProcsStepStCd.SUCCESS.getCdVal());						// 처리단계상태코드	PROCS_STEP_STCD : 1
			procsMgmtVo.setProcsStepMsgCd    	(ProcsStepMsgCd.DEFAULT.getCdVal());					// 처리단계메시지코드 	PROCS_STEP_MSG_CD : AAAAAA
			procsMgmtVo.setSvrIp			 	(InetAddress.getLocalHost().getHostAddress());			// 서버 IP
			procsMgmtVo.setElecDocGroupInexNo	(edocIdxNo);
			procsMgmtVo.setCrtnTime			 	(bzwkInfoVo.getRegTime());								// 생성시각		CRTN_TIME
			procsMgmtVo.setProcsStepEdTime		(new Date());											// 프로세스 완료 시각
			
			// 5. DB Update
			// 전자문서그룹 관리정보테이블에 파일 처리정보 등록
			updateProcsMgmtTable(procsMgmtVo);

			// 전자문서그룹업무정보 테이블 등록 (bzwk)
			updateBzwkInfoTable(bzwkInfoVo);
			
			// 파일처리관리 테이블 & 첨부파일정보 테이블  기존 파일 정보 삭제 & 신규 파일 정보 등록 
			updateFileProcsMgmtTable(edocIdxNo, dataCfg, guidFormcodeMainFormcodeVos);
			
		}catch(UnknownHostException e) {
			throw new EdocServerException(EdocServerStatus.UNKNOWN_HOST_ERROR);
		}
		
		logger.info("[Upload EdocData Service - END]");

		response.setEdocGrpIdxNo(edocIdxNo);
		response.setResult(EdocServerStatus.OK);
		
		return response;
	}

	/*
	 * 전자문서 생성 및 처리 요청한 전자문서 건이 이미 처리중인지 확인한다.
	 */
	protected EdocGrpProcsMgmtDto chkEdocProcessingStatus(String elecDocGroupIndexNo) {
		
		EdocGrpProcsMgmtDto grpProcsMgmtVo = super.chkEdocProcessingStatus(elecDocGroupIndexNo);

		// 전자문서그룹 처리 단계가 "PDF생성(20)" 이상인 경우 "이미 처리중"으로 처리한다.
		// "PDF생성(20)" 이상인 경우에도 "PDF생성(20)" 단계에서 단계메시지코드값이 "MAXERR"인 경우는 "이미 처리중"이 아니다.
		// "입력데이타검증(10)" 단계의 상태가 "실패(9)"인 경우에는 "이미 처리중"이 아니다.
		String procsStepCd = grpProcsMgmtVo.getProcsStepCd();
		if(procsStepCd.compareTo(ProcsStepCd.INPUT_DATA_VERIFICATION.getCdVal()) < 0) {							//처리단계가 "시작(00)" 인 경우
			return grpProcsMgmtVo;
		}else if(grpProcsMgmtVo.getProcsStepCd().compareTo(ProcsStepCd.INPUT_DATA_VERIFICATION.getCdVal()) == 0
				&& grpProcsMgmtVo.getProcsStepStcd().equals(ProcsStepStCd.FAIL.getCdVal())) {					//"입력데이타검증(10)" 단계의 상태가 "실패(9)"인 경우
			return grpProcsMgmtVo;
		}else if(procsStepCd.equals(ProcsStepCd.PDF_CREATE.getCdVal())
				&& grpProcsMgmtVo.getProcsStepMsgCd().equals(ProcsStepMsgCd.MAXERR.getCdVal())){			//"PDF생성(20)" 단계에서 단계메시지코드값이 "MAXERR"인 경우
			return grpProcsMgmtVo;
		}
		
		//위에서 걸러지지 않은 것들은 이미 처리중인 상태. -> Exception
		logger.error("[REQUESTED EDOCGRPIDXNO ALREADY PROCESSING] edocGrpIdxNo = {}", elecDocGroupIndexNo);
		throw new EdocServerException(EdocServerStatus.REQUEST_ALREADY_PROCESSING);
	}
	
	/*
	 * ZIP 파일 처리 프로세스
	 * 1. 업로드된 ZIP파일 서버에 저장
	 * 2. ZIP파일 압축 해제
	 * 3. Data.cfg 처리
	 */
	private String processZipFile(EdocGrpBzwkInfoDto bzwkInfoVo, MultipartFile file) {
		logger.info("[Zip File Process - Start]");
		
		// 1. zip파일 저장
		String zipFilePath = transferFile(folderPath(bzwkInfoVo), bzwkInfoVo.getElecDocGroupInexNo(), file);
		
		logger.debug("ZIP File Path = {}", zipFilePath);
		
		// zip파일 암호화여부 확인 및 복호화 -> 사용 x
//		try {
//			// 복호화파일 경로
//			String decZipFilePath = folderPath(bzwkInfoVo) + bzwkInfoVo.getElecDocGroupInexNo() + "_dec.zip";
//			if(FileUtils.decrypt(zipFilePath, decZipFilePath)) {
//				logger.debug("decrypted ZIP File Path = {}", zipFilePath);
//				zipFilePath = decZipFilePath;
//			}
//		} catch(Exception e) {
//			throw new EdocServerException(EdocServerStatus.DECRYPT_ERROR, e);
//		}
		
		// 2. 압축 해제
		String resultXmlPath = folderPath(bzwkInfoVo) + EdocServiceProp.XML_FILE_PATH(); 				//압축 해제 후 xml파일 저장할 폴더 경로
		
		try {
			ZipUtils.unzip(zipFilePath, resultXmlPath);
			
			//zip파일 삭제(암호화 파일의 경우, 복호화한 파일만 삭제됨. 원본파일은 삭제x)
			File zipFile = new File(zipFilePath);
			if(zipFile.exists()) {
				zipFile.delete();
			}
		}catch(Exception e) {
			throw new EdocServerException(EdocServerStatus.FILE_DECOMPRESS_ERROR, e);
		}
		
		logger.info("[Zip File Process - End] Decompressed File Path = {}", resultXmlPath);

		return resultXmlPath;
	}
	
	/*
	 * Data.cfg 파일 처리 프로세스
	 * 1. Data.cfg 파일 파싱
	 * 2. DataCfg 모델 검증 : Data.cfg에 지정된 서식코드와 결과XML파일 내부의 서식코드 일치 여부 확인
	 */
	private DataCfgVo processDataCfg(String resultXmlPath) {
		logger.info("[Data.Cfg Process Start]");
		
		DataCfgVo mainDataCfg = null;

		try {
			// 1. 전자문서압축파일(Data.cfg) 파싱
			mainDataCfg = DataCfgUtils.parse(resultXmlPath);
			
			if(mainDataCfg == null) {
				throw new EdocServerException(EdocServerStatus.INVALID_CFG_FILE, "Data.cfg Parsing Failed");
			}
			
			logger.debug("DataCfg = {}", mainDataCfg.toString());
			
			// 2. Data.cfg 데이터 검증 
			for(FldrDataVo folderDataVo : mainDataCfg.getFldrDatas()) {
				validateXml(folderDataVo, resultXmlPath);
			}
			
		} catch(IOException e) {
			throw new EdocServerException(EdocServerStatus.FILE_READ_ERROR, "Data.cfg Parsing Error", e);
		}
		
		logger.info("[Data.Cfg Process End]");
		
		return mainDataCfg;
	}
	
	/*
	 * Data.cfg 데이터 검증  프로세스
	 * 1. 서식폴더데이터의 결과 xml파일 존재 여부 확인 : Data.cfg -> "FLDR_DATA" -> "XML_NM"
	 * 2. 서식폴더데이터의 서식코드와 1.에서 확인한 결과 xml파일에 정의되어진 서식코드(formname)가 일치하는지 확인: Data.cfg -> "FLDR_DATA" -> "FORMCODE"
	 */
	private void validateXml(FldrDataVo fldrData, String resultXmlPath) {
		logger.debug("fldrData = {}", fldrData.toString());
		
		String xmlFilePath = resultXmlPath + fldrData.getFldrNm() + File.separator + fldrData.getXmlNm();						//서식폴더데이터에 지정된 결과 xml파일 경로
		
		File xmlFile = new File(xmlFilePath);
		if(!xmlFile.exists()) {
			throw new EdocServerException(EdocServerStatus.XML_FILE_NOT_FOUND, 
					"[Result XML FILE not EXIST in Zip File.] XmlFilePath in Zip = " + fldrData.getFldrNm() + File.separator + fldrData.getXmlNm()) ;
		}
		
		logger.debug("Result XmlFilePath = {}", xmlFilePath);
		
		String xmlFormCode = "";
		try {
			xmlFormCode = DataCfgUtils.getValueXml(xmlFilePath, "FORMNAME");													//xml파일 내 FORMNAME태그의 value값
		}catch(Exception e) {
			throw new EdocServerException(EdocServerStatus.INVALID_XML_FILE, "Could't Get \"<FORMNAME>\" Value from Result XML File.", e);
		}
		
		logger.debug("[<FORMNAME> value in Result XMLFile = {}]", xmlFormCode);

		xmlFormCode = StringUtils.cutExtension(xmlFormCode);																	// 확장자 제외한 FormCode값
		
		if(!StringUtils.nvl(fldrData.getFormcode()).equals(xmlFormCode)) {
			throw new EdocServerException(EdocServerStatus.INVALID_XML_FILE, 
					"[Formcode Validation Failed.] Data.cfg FORM CODE = " + StringUtils.nvl(fldrData.getFormcode()) + "," +"XML FORM CODE = " + xmlFormCode);
		}
	}

	/*
	 * EDS_ELEC_DOC_FILE_PROCS_MGMT table update
	 */
	private void updateFileProcsMgmtTable(String edocIdxNo, DataCfgVo dataCfgVo,
								List<GuidFormcodeMainFormcodeVo> guidFormcodeMainFormcodeVos) {
		
		logger.info("[Update FileProcsMgmt Table - Start]");

		// 기존에 등록된 파일들은 제거.
		EdocFileProcsMgmtDto delCondition = new EdocFileProcsMgmtDto();
		delCondition.setElecDocGroupInexNo(edocIdxNo);
		
		fileProcsMgmtMapper.deleteGeneral(delCondition);

		// 전자문서그룹파일처리관리 테이블 등록
		for(FldrDataVo fldrData : dataCfgVo.getFldrDatas()) {
			// 현재 전자문서키와 GUID에 대해서  FLDR_DATA의 서식의 서식코드가 GUID_FORMCODE_LINK 항목에서 GUID~서식코드 연결 관계가 명시되어 있거나,
			// 서식 코드가 어떤 GUID와의 연결 관계도 명시되지 않은 경우 파일처리관리 테이블에 레코드 등록한다.
				// G - F 관계 명시된 경우        : E - G - F 관계로 파일처리관리 테이블에 레코드 등록
				// G - F 관계 명시되지 않은 경우 : E -   - F 관계로 파일처리관리 테이블에 레코드 등록
			GuidFormcodeMainFormcodeVo gfmVo = null;
			for(GuidFormcodeMainFormcodeVo guidFormcodeMainFormcodeVo : guidFormcodeMainFormcodeVos) {
				if(fldrData.getFormcode().equals(guidFormcodeMainFormcodeVo.getFormcode())) {
					if(guidFormcodeMainFormcodeVo.getGuid() == null || dataCfgVo.getGuid().equals(guidFormcodeMainFormcodeVo.getGuid())) {
						gfmVo = guidFormcodeMainFormcodeVo;
						break;
					}
				}
			}
			if(gfmVo == null) {
				continue;
			}
			
			int fileSeqNo = Integer.parseInt(fldrData.getFldrNm());
			
			EdocFileProcsMgmtDto fileProcsMgmtVo = new EdocFileProcsMgmtDto();
			fileProcsMgmtVo.setElecDocGroupInexNo(edocIdxNo);										// 전자문서그룹인덱스번호	ELEC_DOC_GROUP_INEX_NO
			fileProcsMgmtVo.setFileSeqNo(fileSeqNo);												// 파일정렬 순서 FILE_SEQ_NO
			fileProcsMgmtVo.setLefrmCd(fldrData.getFormcode());										// 서식코드	LEFRM_CD
			fileProcsMgmtVo.setLefrmNm(fldrData.getFormname());										// 서식명		LEFRM_NM
			fileProcsMgmtVo.setXmlFileNm(fldrData.getXmlNm());										// XML파일명	XML_FILE_NM
			fileProcsMgmtVo.setPdfFileNm(fldrData.getPdfNm());										// PDF파일명	PDF_FILE_NM
			fileProcsMgmtVo.setPageCnt((int) fldrData.getPageCnt());								// 페이지수	PAGE_CNT
			fileProcsMgmtVo.setProcsStepCd(ProcsStepCd.INPUT_DATA_VERIFICATION.getCdVal());			// 처리단계코드	PROCS_STEP_CD
			fileProcsMgmtVo.setProcsStepStcd(ProcsStepStCd.SUCCESS.getCdVal());						// 처리단계상태코드	PROCS_STEP_STCD
			fileProcsMgmtVo.setCrtnTime(new Date());												// 생성시각	CRTN_TIME
			fileProcsMgmtVo.setLefrmVer(fldrData.getFormver());										// 서식버전	LEFRM_VER
			
			logger.debug("edocFileProcsMgmtVo = {}", fileProcsMgmtVo);
			
			int result = fileProcsMgmtMapper.insert(fileProcsMgmtVo);
			if(result <= 0) {
				throw new EdocServerException(EdocServerStatus.DB_INSERT_ERROR, "EdsElecDocFileProcsMgmt Table Update Failed.");
			}
		}	
	}
}
