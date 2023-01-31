package client;

import client.model.PacketVo;
import client.service.SearchTempListTest;

public class SearchTempListTestMain {
	
//	private static String url = "http://localhost:8088/ccrs";
	private static String url = "http://ppcr-dr.ccrs.or.kr:7105";
	
	public static void main(String[] args) {
		
		PacketVo request = new PacketVo();
		
		request.setBizRgstEmpNo("00001");
		request.setBizRgstBrCd("00001");
		//request.setBizRgstBrNm("test");
		//request.setBizRgstEmpNm("김연희");
		//request.setCorEdocGrpIdxNo(null);
		
		//request.setBizTmpDataRegTime("20190920183000");
		//request.setBizCusNm("김재환");
		
		/////////////searchTempListTest///////////
		new SearchTempListTest(url).run(request);
	}

}
