<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
	/*
		- 알림창 호출
		msg : 알림창에 넣을 메세지
		callBack : 버튼을 눌렀을 경우 실행 될 콜백함수
		btnTxt : 버튼에 들어갈 텍스트, default : 확인
		
		호출 예시 : callAlert('검색어는 2장 이상 입력하시기 바랍니다.', '', '확인');
	*/
	function callAlert(msg, callBack, btnTxt){
		$("#pAlertContents").text(msg);
		if(btnTxt === null || btnTxt === undefined) btnTxt = "확인";
		$("#alertBtn").text(btnTxt);
		jsShowBlockLayer('#divAlert');
		
		$("#alertBtn").unbind('click');
		$("#alertBtn").bind('click',function() {
	    	$(this).unbind('click'); 
	    	jsHideBlockLayer('#divAlert');
	        if(callBack && typeof(callBack) === 'function') callBack();
	    });
	}
	
	/*
		- 확인창 호출
		msg : 확인창에 넣을 메세지
		callBack1 : 좌측 버튼을 눌렀을 경우 실행 될 콜백함수
		callBack2 : 우측 버튼을 눌렀을 경우 실행 될 콜백함수
		btnTxt1 : 좌측 버튼에 들어갈 텍스트, default : 예
		btnTxt2 : 우측 버튼에 들어갈 텍스트, default : 아니오
		
		호출 예시 : callConfirm('저장하시겠습니까?', function(){alert('저장이 완료되었습니다.');}, '', '예', '아니오');
	*/
	function callConfirm(msg, callBack1, callBack2, btnTxt1, btnTxt2){
		$("#pConfirmCOntents").text(msg);
		if(btnTxt1 === null || btnTxt1 === undefined) btnTxt1 = "예";
		if(btnTxt2 === null || btnTxt2 === undefined) btnTxt2 = "아니오";
		$("#confirmBtn1").text(btnTxt1);
		$("#confirmBtn2").text(btnTxt2);
		jsShowBlockLayer('#divConfirm');
		
		$("#confirmBtn1, #confirmBtn2").unbind('click');
		$("#confirmBtn1, #confirmBtn2").bind('click',function() {
	    	$(this).unbind('click'); 
	    	jsHideBlockLayer('#divConfirm');
	    	if($(this).attr('id') == 'confirmBtn1'){
	    		if(callBack1 && typeof(callBack1) === 'function') callBack1();
	    	}else{
	    		if(callBack2 && typeof(callBack2) === 'function') callBack2();
	    	}
	    });
	}
</script>
<div id="divAlert" class="popup-wrapper" style="width:260px; display: none;">
	<div class="popup_top" style="float:left; border-bottom:1px solid #114493; width:260px">
		<h2>알림</h2>	
	</div>
	<p id="pAlertContents" style="clear:both; margin-top:70px; margin-bottom:20px; text-align: center; font-size:14px"></p>
	<div class="popupbutton" style="float:left; padding-top:10px; margin-left:86px">
		<span id="alertBtn" class="download" style="width:90px !important"></span>
	</div>
</div>

<div id="divConfirm" class="popup-wrapper" style="width:260px; display: none;">
	<div class="popup_top" style="float:left; border-bottom:1px solid #114493; width:260px">
		<h2>확인</h2>	
	</div>
	<p id="pConfirmCOntents" style="clear:both; margin-top:70px; margin-bottom:20px; text-align: center; font-size:14px"></p>
	<div class="popupbutton" style="float:left; padding-top:10px; margin-left:26px">
		<span id="confirmBtn1" class="download" style="width:90px !important"></span>
		<span id="confirmBtn2" class="close"></span>
	</div>
</div>

<!-- 비밀번호 변경 팝업 -->
<div id="divModifyPasswordPopup" class="popup-wrapper" style="width:380px; display: none;"></div>