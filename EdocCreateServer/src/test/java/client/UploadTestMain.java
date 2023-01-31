package client;

import java.io.File;

import client.model.PacketVo;
import client.service.EdocGrpIdxNoTest;
import client.service.UploadEdocDataTest;
import client.service.UploadTempDataTest;

public class UploadTestMain {
	
//	private static String url = "http://localhost:8088/ccrs";
	private static String url = "http://ppcr-dr.ccrs.or.kr:7105";
	
//	private static String url = "https://ppcr.ccrs.or.kr";
	
	private static String testFilePath = "data/sample/test2.zip";
	
	public static void main(String[] args) {
		
		PacketVo request = new PacketVo();

		request.setBizRgstBrCd("001");
		request.setBizRgstBrNm("xxx지부");
		request.setBizRgstEmpNm("김연희");
		request.setBizRgstEmpNo("123456");
		request.setBizProcessType("PPR");
		request.setBizTerminalNo("172.30.30.37");
		request.setBizScrnNo("A00002");
		request.setMainKey("010101010101");
		request.setTaskKey("TaskKey01");
		request.setCustomerName("김재환");
		request.setInsourceId("0001");
		request.setInsourceTitle("신용회복");
		request.setMemo("9402012481414");
	
		String edocKey = "E19092312000960";
		
		///////////Generate edocGrpIdxNo Test///////////////
		PacketVo response = new EdocGrpIdxNoTest(url).run(request);
		edocKey = response.getCorEdocGrpIdxNo();
		////////////////////////////////////////////////////
		
		request.setCorEdocGrpIdxNo(edocKey);
		request.setBizMappingKey(edocKey+"2");	
		
		request.setBizTmpDataStrgType("T");
		request.setBizTmpDataMemo("임시저장메모");
		
		//System.out.println(request.toString());
		
		///////////Upload TempData Test///////////
		new UploadTempDataTest(url).run(request, new File(testFilePath), false);
		//////////////////////////////////////////
		

		///////////Upload Edoc Test Start///////////
		new UploadEdocDataTest(url).run(request, new File(testFilePath), false);
		////////////////////////////////////////////
	}
}
