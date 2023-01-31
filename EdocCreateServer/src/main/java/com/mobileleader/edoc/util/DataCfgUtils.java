package com.mobileleader.edoc.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.inzisoft.xml.parser.XmlObject;
import com.inzisoft.xml.parser.XmlParser;
import com.mobileleader.edoc.exception.EdocServerException;
import com.mobileleader.edoc.model.data.DataCfgVo;
import com.mobileleader.edoc.model.data.FldrDataVo;
import com.mobileleader.edoc.model.data.GuidFormcodeLinkVo;
import com.mobileleader.edoc.model.data.GuidFormcodeMainFormcodeVo;

/**
 * Data.cfg 파일에 대한 유틸리티 클래스
 *
 */
public class DataCfgUtils {

	/**
	 * Data.cfg 파일 DataCfg 객체로 파싱
	 * 
	 * @param 	dataCfgFilePath		data.cfg파일 경로(파일이름 제외한 폴더 경로) 
	 * @return	DataCfg
	 * @throws IOException 
	 */
	public static DataCfgVo parse(String dataCfgFilePath) throws IOException {
		
		StringBuilder jsonStr = new StringBuilder();
		BufferedReader reader = null;
		DataCfgVo dataCfgVo = null;
		
		try {
			reader = new BufferedReader(
						new InputStreamReader(
							new FileInputStream(dataCfgFilePath + "Data.cfg"), "UTF-8"));
			
			String lineStr = null;
			
			while((lineStr = reader.readLine()) != null) {
				jsonStr.append(lineStr);
			}
			
			dataCfgVo = JsonUtils.fromJson(jsonStr.toString(), DataCfgVo.class);
			
		} catch(IOException e){
			dataCfgVo = null;
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				throw e;
			}
		}
		return dataCfgVo;
	}


	/**
	 * xml파일에서 strName태그의 value값 검색
	 * 
	 * @param	filename	xml파일 이름(full path)
	 * @param	strName		태그 이름
	 * @return	value
	 */
	public static String getValueXml(String filename, String strName) throws Exception{
		String value = null;

		XmlParser xmlParser = new XmlParser();
		xmlParser.parse(filename);

		XmlObject root = xmlParser.getRoot();
		XmlObject object = root.find(strName);
		value = object.getText();

		return value;
	}
	
	/**
	 * Data.cfg 파일의 GUID_FORMCODE_LINK 항목으로부터 GUID ~ 서식코드 ~ 메인서식코드 관계 획득<br>
	 * <br>
	 * 참고1 : GUID_FORMCODE_LINK 목록의 각 element의 값(GUID, FORMCODE, EX_PAGE_FORMCODE) 결정 구조 
	 * <pre>
	 * (1) PC전자문서프로그램에서 계정계 통합화면으로부터 서식에 대한 인자 정보를 받을 때 만들어진다.
	 * (2) (1)에서 기본적으로 GUID ~ FORMCODE 연결 관계가 만들어진다. EX_PAGE_FORMCODE 항목의 값은 비어있게 된다.
	 * (3) (1)에서 서식에 인자할 공간이 부족한 경우 빈 서식을 자동적으로 추가하게 된다. 이 경우
	 * (4) GUID ~ FORMCODE ~ EX_PAGE_FORMCODE 연결 관계가 만들어진다.
	 * (5) (4)의 FORMCODE 항목의 값은 본래 서식의 서식코드이고, EX_PAGE_FORMCODE 항목의 값은 추가된 빈 서식의 서식코드이다.
	 * </pre>
	 * 참고2 : GUID ~ FORMCODE 연결 관계가 가지는 의미
	 * <pre>
	 * (1) 한명의 고객에 대해 여러가지 거래가 연결되는 연결거래(동시거래)가 발생할 수 있다.
	 * (2) (1)의 경우 각 거래별로 서로 다른 GUID가 발생하고, 서식에도 GUID 별로 서로 다른 서식에 서로 다른 인자가 될 수 있다.
	 * (3) 각 거래에 대한 정보는 계정계에서 deferred 방식으로 BPR로 제공되고, BPR은 GUID ~ 서식코드에 대해 PPR로 이미지 입고 요청을 보낸다.
	 * (4) PPR에서는 (3)의 이유로 GUID와 서식코드의 연결 관계를 PC전자문서프로그램으로부터 받아서 DB에 보관하고 있게 된다.
	 * </pre>
	 * 참고3 : 인자가 되지 않은 서식의 처리(GUID ~ FORMCODE 연결 관계가 없음)
	 * <pre>
	 * (1) 인자가 되지 않은 서석에 대해서는 인자가 된 서식에 첨부되는 서식으로 간주한다.
	 * (2) 인자가 된 서식은 BPR로부터 GUID ~ 서식코드 쌍이 지정되어 입고 요청된다.
	 * (3) 인자가 되지 않은 서식은 인자가 된 서식의 BPR 입고 시 첨부로 같이 입고한다.
	 * (4) 연결거래인 경우 GUID와의 연결 관계가 없는 서식은 GUID ~ 서식코드 ~ 메인서식코드 관계 생성 시 GUID의 값을 null로 한다.
	 * (5) (4)에서 만들어진 GUID 값이 null인 서식에 대해서는 연결거래로 묶이는 모든 거래에 대해 첨부로 처리하게 된다.
	 * </pre>
	 * 
	 * @param	dataCfgVo	Data.cfg 파일을 파싱한 결과
	 * @return	GUID ~ 서식코드 ~ 메인서식코드 연결 관계 목록
	 * @throws Exception 
	 * @throws	EdocServerException
	 */
	public static List<GuidFormcodeMainFormcodeVo> generateGuidFormcodeMainFormcodes(DataCfgVo dataCfgVo) {
		
		List<GuidFormcodeMainFormcodeVo> guidFormcodeMainFormcodeVoList = new ArrayList<GuidFormcodeMainFormcodeVo>();

		// Data.cfg의 GUID_FORMCODE_LINK 항목에 GUID와 서식 연결 관계가 명시된 경우 처리
		for(GuidFormcodeLinkVo guidFormcodeLinkVo : dataCfgVo.getGuidFormcodeLinks()) {
			
			GuidFormcodeMainFormcodeVo guidFormcodeMainFormcodeVo = new GuidFormcodeMainFormcodeVo();
			
			String guid           = guidFormcodeLinkVo.getGuid();
			String formcode       = guidFormcodeLinkVo.getFormcode();
			String exPageFormcode = guidFormcodeLinkVo.getExPageFormcode();
			
			if((guid == null || "".equals(guid)) || (formcode == null || "".equals(formcode))) {
				return null;
			}
			
			exPageFormcode = (exPageFormcode == null || "".equals(exPageFormcode))? formcode : exPageFormcode;
			
			guidFormcodeMainFormcodeVo.setGuid(guid);
			guidFormcodeMainFormcodeVo.setMainFormcode(formcode);
			guidFormcodeMainFormcodeVo.setFormcode(exPageFormcode);
			
			guidFormcodeMainFormcodeVoList.add(guidFormcodeMainFormcodeVo);
		}

		// Data.cfg에서 GUID와의 연결 관계가 명시되지 않은 서식은 Data.cfg의 모든 GUID에 대한 입고 시 첨부로 묶여야 한다.
		// 이를 위해 EDS_ELEC_DOC_FILE_PROCS_MGMT(전자문서파일처리관리) 테이블에는 모든 전자문서키 별로 GUID 필드의 값이 null인 레코드를 추가한다.
		// 참고 : 연결거래의 경우 전자문서키는 GUID 별로 다르게 부여된다.		
		for(FldrDataVo fldrDataVo : dataCfgVo.getFldrDatas()) {
			boolean found = false;
			for(GuidFormcodeMainFormcodeVo guidFormcodeMainFormcodeVo : guidFormcodeMainFormcodeVoList){
				if(fldrDataVo.getFormcode().equals(guidFormcodeMainFormcodeVo.getFormcode())){
					found = true;
					break;
				}
			}
			//연결관계 명시o인경우.
			if(found) {
				continue;
			}

			//연결관계 명시x
			GuidFormcodeMainFormcodeVo newGuidFormcodeMainFormcodeVo = new GuidFormcodeMainFormcodeVo();
			newGuidFormcodeMainFormcodeVo.setGuid(null);
			newGuidFormcodeMainFormcodeVo.setFormcode(fldrDataVo.getFormcode());
			newGuidFormcodeMainFormcodeVo.setMainFormcode(fldrDataVo.getFormcode());
			guidFormcodeMainFormcodeVoList.add(newGuidFormcodeMainFormcodeVo);
		}

		return guidFormcodeMainFormcodeVoList;
	}
}
