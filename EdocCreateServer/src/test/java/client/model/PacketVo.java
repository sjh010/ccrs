package client.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mobileleader.edoc.db.dto.EdocGrpTmpStrgMgmtDto;
import com.mobileleader.edoc.util.JsonUtils;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PacketVo {
	
	@JsonProperty("MainKey")
	private String mainKey;					// 통합 전자문서 키								
	
	@JsonProperty("TaskKey")
	private String taskKey;					// 업무키
	
	@JsonProperty("CustomerName")
	private String customerName;
	
	@JsonProperty("InsourceId")
	private String insourceId;				//업무명
	
	@JsonProperty("InsourceTitle")
	private String insourceTitle;			//업무코드
	
	@JsonProperty("Memo")
	private String memo;					// 주민 등록 번호
	
	private String corFnCd			= null;
	/**
	 * 전자문서그룹인덱스번호(전자문서키)
	 */
	private String corEdocGrpIdxNo	= null;
	/**
	 * 전자문서키(전자문서그룹인덱스번호) 생성 시각. java.util.Date 타입
	 */
	private Date   corCrtTs			= null;
	/**
	 * 클라이언트 요청 기능 수행 결과 코드
	 */
	private String corCode			= null;
	/**
	 * 클라이언트 요청 기능 수행 결과 메시지
	 */
	private String corMsg			= null;
	////////////////////////////////////////////////////////////////////////////

	////////////////////////////////////////////////////////////////////////////
	// 업무 필수/선택 항목들
	private String bizJobCd				= null;	// 업무코드
	private String bizJobNm				= null;	// 업무명
	private String bizRgstBrCd			= null;	// 등록지점코드
	private String bizRgstBrNm			= null;	// 등록지점명
	private String bizRgstEmpNo			= null;	// 등록직원번호
	private String bizRgstEmpNm			= null;	// 등록직원명
	private String bizEdocNewYN			= null;	// 전자문서신규YN
	private String bizScrnNo			= null;	// 화면번호
	private String bizSubScrnNo			= null;	// 부화면번호
	private String bizCusNo				= null;	// 고객번호
	private String bizCusNm				= null;	// 고객명
	private String bizRefNo				= null;	// 참조번호(수신-계좌번호, 카드-카드번호, 여신-여신번호 등)
	private String bizImgGrpIdxNo		= null;	// 이미지그룹인덱스번호(이미지키)

	/**
	 * 매핑키 :<br>
	 * GUID의 일부분이다.
	 * GUID에서 거래시간(hhmmss)에 해당하는 값이다.
	 * 미전송(미입고) 된 건에 대하여 전자문서서버에서 입고 요청된 GUID로부터 매핑키를 뽑아내고,
	 * 그 매핑키로 임시저장된 전자문서키를 찾는 용도로 사용한다.
	 */
	private String bizMappingKey		= null;	// 매핑키
	/**
	 * 단말번호 :<br>
	 * GUID를 구성하는 값. GUID로부터 획득한 단말번호와 매핑키를 이용하여 임시저장된 전자문서건을 찾는다.
	 * InetAddress.getLocalHost().getHostName() 함수를 이용하여 획득할 수 있다.
	 */
	private String bizTerminalNo		= null;	// 단말번호

	private String bizProcessType		= null;	// 프로세스유형 : "PPR", "BDT"
	private String bizTmpDataMemo		= null;	// PC APP에서 임시저장 시 텔러 입력 메모
	private String bizTmpDataRegTime	= null;	// 임시정보 서버 저장 시각 "yyyyMMddHHmmss"
	private String bizTmpDataStrgType	= null;	// 임시정보저장유형 : "T' - 텔러 선택 저장, "A" - 자동 저장

	private String bizRecvBrCd			= null;	// 수관지점코드
	private String bizRecvBrNm			= null;	// 수관지점명
	private String bizRecvEmpNo			= null;	// 수관직원번호
	private String bizRecvEmpNm			= null;	// 수관직원명
	private String bizCtrlBrCd			= null;	// 이관지점코드
	private String bizCtrlBrNm			= null;	// 이관지점명
	private String bizCtrlEmpNo			= null;	// 이관직원번호
	private String bizCtrlEmpNm			= null;	// 이관직원명
	
	private List<EdocGrpTmpStrgMgmtDto> bizTmpDataList = null;	// 임시정보 목록

	private String bizGuid				= null;	// GUID : BDT 프로세스 BPR 이미지 입고 등록 요청 시
	private String bizTxInfo			= null;	// 거래 정보 : BDT 프로세스 BPR 이미지 입고 등록 요청 시
	private String bizFormCode			= null;	// 서식 코드 : BDT 프로세스 BPR 이미지 입고 등록 요청 시

	private String bizOldGuid			= null;	// 임시전자문서 GUID : BDT 프로세스 최종전자문서 생성 시 명시
	////////////////////////////////////////////////////////////////////////////

	////////////////////////////////////////////////////////////////////////////
	// 첨부 파일을 위한 항목
	private String attachedFilePath		= null;	// 첨부파일경로

	// 2018.03.09
	////////////////////////////////////////////////////////////////////////////
	// 클라이언트 IP
	private String clientIp         = null;
	////////////////////////////////////////////////////////////////////////////

	// 2018.04.26
	////////////////////////////////////////////////////////////////////////////
	// First GUID
	// 전자문서키를 발급할 때 연결거래에 발급하는 경우 First GUID를 미리 설정하도록 하여
	// 백그라운드데몬(PDF생성데몬)에서 BPR에 입고 시 작성하는 GUID 목록(REF_GUID)를 만들 수 있도록 한다.
	private String firstGUID        = null;
	///////////////////////////////////////////////////////////////////////////	
	
	public String getCorFnCd() {
		return corFnCd;
	}

	public String getMainKey() {
		return mainKey;
	}

	public void setMainKey(String mainKey) {
		this.mainKey = mainKey;
	}

	public String getTaskKey() {
		return taskKey;
	}

	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getInsourceId() {
		return insourceId;
	}

	public void setInsourceId(String insourceId) {
		this.insourceId = insourceId;
	}

	public String getInsourceTitle() {
		return insourceTitle;
	}

	public void setInsourceTitle(String insourceTitle) {
		this.insourceTitle = insourceTitle;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public void setCorFnCd(String corFnCd) {
		this.corFnCd = corFnCd;
	}
	public String getCorEdocGrpIdxNo() {
		return corEdocGrpIdxNo;
	}
	public void setCorEdocGrpIdxNo(String corEdocGrpIdxNo) {
		this.corEdocGrpIdxNo = corEdocGrpIdxNo;
	}
	/**
	 * 전자문서키(전자문서그룹인덱스번호) 생성 시각을 획득한다.
	 * @return	java.util.Date 클래스 인스턴스
	 */
	public Date getCorCrtTs() {
		return corCrtTs;
	}
	/**
	 * 전자문서키(전자문서그룹인덱스번호) 생성 시각을 설정한다.
	 * @param	corCrtTs	전자문서키(전자문서그룹인덱스번호) 생성 시각. (예제) setCorCrtTs(new java.util.Date());
	 */
	public void setCorCrtTs(Date corCrtTs) {
		this.corCrtTs = corCrtTs;
	}
	public String getCorCode() {
		return corCode;
	}
	public void setCorCode(String corCode) {
		this.corCode = corCode;
	}

	public String getCorMsg() {
		return corMsg;
	}
	public void setCorMsg(String corMsg) {
		this.corMsg = corMsg;
	}

	public String getBizJobCd() {
		return bizJobCd;
	}
	public void setBizJobCd(String bizJobCd) {
		this.bizJobCd = bizJobCd;
	}
	public String getBizJobNm() {
		return bizJobNm;
	}
	public void setBizJobNm(String bizJobNm) {
		this.bizJobNm = bizJobNm;
	}
	public String getBizRgstBrCd() {
		return bizRgstBrCd;
	}
	public void setBizRgstBrCd(String bizRgstBrCd) {
		this.bizRgstBrCd = bizRgstBrCd;
	}
	public String getBizRgstBrNm() {
		return bizRgstBrNm;
	}
	public void setBizRgstBrNm(String bizRgstBrNm) {
		this.bizRgstBrNm = bizRgstBrNm;
	}
	public String getBizRgstEmpNo() {
		return bizRgstEmpNo;
	}
	public void setBizRgstEmpNo(String bizRgstEmpNo) {
		this.bizRgstEmpNo = bizRgstEmpNo;
	}
	public String getBizRgstEmpNm() {
		return bizRgstEmpNm;
	}
	public void setBizRgstEmpNm(String bizRgstEmpNm) {
		this.bizRgstEmpNm = bizRgstEmpNm;
	}
	public String getBizEdocNewYN() {
		return bizEdocNewYN;
	}
	public void setBizEdocNewYN(String bizEdocNewYN) {
		this.bizEdocNewYN = bizEdocNewYN;
	}
	public String getBizScrnNo() {
		return bizScrnNo;
	}
	public void setBizScrnNo(String bizScrnNo) {
		this.bizScrnNo = bizScrnNo;
	}
	public String getBizSubScrnNo() {
		return bizSubScrnNo;
	}
	public void setBizSubScrnNo(String bizSubScrnNo) {
		this.bizSubScrnNo = bizSubScrnNo;
	}
	public String getBizCusNo() {
		return bizCusNo;
	}
	public void setBizCusNo(String bizCusNo) {
		this.bizCusNo = bizCusNo;
	}
	public String getBizCusNm() {
		return bizCusNm;
	}
	public void setBizCusNm(String bizCusNm) {
		this.bizCusNm = bizCusNm;
	}
	public String getBizRefNo() {
		return bizRefNo;
	}
	public void setBizRefNo(String bizRefNo) {
		this.bizRefNo = bizRefNo;
	}
	public String getBizImgGrpIdxNo() {
		return bizImgGrpIdxNo;
	}
	public void setBizImgGrpIdxNo(String bizImgGrpIdxNo) {
		this.bizImgGrpIdxNo = bizImgGrpIdxNo;
	}

	/** 매핑키 */
	public String getBizMappingKey() {
		return bizMappingKey;
	}
	/** 매핑키 */
	public void setBizMappingKey(String bizMappingKey) {
		this.bizMappingKey = bizMappingKey;
	}
	/** 단말번호 */
	public String getBizTerminalNo() {
		return bizTerminalNo;
	}
	/** 단말번호 */
	public void setBizTerminalNo(String bizTerminalNo) {
		this.bizTerminalNo = bizTerminalNo;
	}

	public String getBizProcessType() {
		return bizProcessType;
	}
	public void setBizProcessType(String bizProcessType) {
		this.bizProcessType = bizProcessType;
	}
	public String getBizTmpDataMemo() {
		return bizTmpDataMemo;
	}
	public void setBizTmpDataMemo(String bizTmpDataMemo) {
		this.bizTmpDataMemo = bizTmpDataMemo;
	}
	public String getBizTmpDataRegTime() {
		return bizTmpDataRegTime;
	}
	public void setBizTmpDataRegTime(String bizTmpDataRegTime) {
		this.bizTmpDataRegTime = bizTmpDataRegTime;
	}
	/** 임시정보저장유형 : "T' - 텔러 선택 저장, "A" - 자동 저장 */
	public String getBizTmpDataStrgType() {
		return bizTmpDataStrgType;
	}
	/** 임시정보저장유형 : "T' - 텔러 선택 저장, "A" - 자동 저장 */
	public void setBizTmpDataStrgType(String bizTmpDataStrgType) {
		this.bizTmpDataStrgType = bizTmpDataStrgType;
	}


	public String getBizRecvBrCd() {
		return bizRecvBrCd;
	}
	public void setBizRecvBrCd(String bizRecvBrCd) {
		this.bizRecvBrCd = bizRecvBrCd;
	}
	public String getBizRecvBrNm() {
		return bizRecvBrNm;
	}
	public void setBizRecvBrNm(String bizRecvBrNm) {
		this.bizRecvBrNm = bizRecvBrNm;
	}
	public String getBizRecvEmpNo() {
		return bizRecvEmpNo;
	}
	public void setBizRecvEmpNo(String bizRecvEmpNo) {
		this.bizRecvEmpNo = bizRecvEmpNo;
	}
	public String getBizRecvEmpNm() {
		return bizRecvEmpNm;
	}
	public void setBizRecvEmpNm(String bizRecvEmpNm) {
		this.bizRecvEmpNm = bizRecvEmpNm;
	}
	public String getBizCtrlBrCd() {
		return bizCtrlBrCd;
	}
	public void setBizCtrlBrCd(String bizCtrlBrCd) {
		this.bizCtrlBrCd = bizCtrlBrCd;
	}
	public String getBizCtrlBrNm() {
		return bizCtrlBrNm;
	}
	public void setBizCtrlBrNm(String bizCtrlBrNm) {
		this.bizCtrlBrNm = bizCtrlBrNm;
	}
	public String getBizCtrlEmpNo() {
		return bizCtrlEmpNo;
	}
	public void setBizCtrlEmpNo(String bizCtrlEmpNo) {
		this.bizCtrlEmpNo = bizCtrlEmpNo;
	}
	public String getBizCtrlEmpNm() {
		return bizCtrlEmpNm;
	}
	public void setBizCtrlEmpNm(String bizCtrlEmpNm) {
		this.bizCtrlEmpNm = bizCtrlEmpNm;
	}
	
	public List<EdocGrpTmpStrgMgmtDto> getBizTmpDataList() {
		return bizTmpDataList;
	}
	public void setBizTmpDataList(List<EdocGrpTmpStrgMgmtDto> bizTmpDataList) {
		this.bizTmpDataList = bizTmpDataList;
	}


	/** GUID from BPR입고요청 */
	public String getBizGuid() {
		return bizGuid;
	}
	/** GUID from BPR입고요청 */
	public void setBizGuid(String bizGuid) {
		this.bizGuid = bizGuid;
	}
	/** 거래 정보 from BPR입고요청 */
	public String getBizTxInfo() {
		return bizTxInfo;
	}
	/** 거래 정보 from BPR입고요청 */
	public void setBizTxInfo(String bizTxInfo) {
		this.bizTxInfo = bizTxInfo;
	}
	/** 서식 코드 from BPR입고요청 */
	public String getBizFormCode() {
		return bizFormCode;
	}
	/** 서식 코드 from BPR입고요청 */
	public void setBizFormCode(String bizFormCode) {
		this.bizFormCode = bizFormCode;
	}

	/** 임시전자문서 GUID : BDT 프로세스 최종전자문서 생성 시 명시 */
	public String getBizOldGuid() {
		return bizOldGuid;
	}
	/** 임시전자문서 GUID : BDT 프로세스 최종전자문서 생성 시 명시 */
	public void setBizOldGuid(String bizOldGuid) {
		this.bizOldGuid = bizOldGuid;
	}

	public String getAttachedFilePath() {
		return attachedFilePath;
	}
	public void setAttachedFilePath(String attachedFilePath) {
		this.attachedFilePath = attachedFilePath;
	}

	// 2018.03.09
	////////////////////////////////////////////////////////////////////////////
	/** 클라이언트 IP */
	public String getClientIp() {
		return clientIp;
	}
	/** 클라이언트 IP */
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
	////////////////////////////////////////////////////////////////////////////

	// 2018.04.26
	////////////////////////////////////////////////////////////////////////////
	/** First GUID */
	public String getFirstGUID() {
		return firstGUID;
	}
	/** First GUID */
	public void setFirstGUID(String firstGUID) {
		this.firstGUID = firstGUID;
	}
	
	public String toString() {
		return JsonUtils.ObjectPrettyPrint(this);
	}

}
