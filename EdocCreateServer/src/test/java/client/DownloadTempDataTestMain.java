package client;

import client.model.PacketVo;
import client.service.SearchTempDataTest;

public class DownloadTempDataTestMain {
	
	private static String url = "http://localhost:8088/ccrs";
//	private static String url = "http://ppcr-dr.ccrs.or.kr:7105";
	
	private static String downloadPath = "data/download/";
	
	public static void main(String[] args) {
		
		String edocKey = "E19092618000024";
		String mappingKey = "NOMAP";
		
		PacketVo request = new PacketVo();
		
		request.setBizRgstEmpNo("00001");
		request.setBizRgstBrCd("00001");
		//request.setBizRgstBrNm("test");
		//request.setBizRgstEmpNm("김연희");
		request.setCorEdocGrpIdxNo(edocKey);
		request.setBizMappingKey(mappingKey);
		
		new SearchTempDataTest(url).run(request, downloadPath);
	}

}
