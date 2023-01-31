jQuery.fn.serializeObject = function(){
	var obj = null;
	try{
		if(this[0].tagName && this[0].tagName.toUpperCase() == "FORM"){
			var arr = this.serializeArray();
			if(arr){
				obj = {};
				jQuery.each(arr, function(){
					obj[this.name] = this.value;
				});
			}
		}
	}catch(e){
		alert(e.message);
	}
	
	return obj;
}

function ajaxCall(ajaxType, url, data, callbackFunc){
	$.ajax({
		type : ajaxType,
		url : url,
		data : data,
		dataType : "json",
		contentType : (ajaxType == "POST"? false : "application/json;charset=utf-8"),
		cache : false,
		processData : false,
		success : function(response){callbackFunc(response);},
		error : function(xhr, textStatus, error){handleError(xhr, textStatus, error);}
	});
}

function handleError(xhr, textStatus, error){
	var errorMsg = '요청 오류';
	errorMsg += '\n' + 'statusCode : ' + xhr.status;
	errorMsg += '\n' + 'responseText : ' + xhr.responseText;
	errorMsg += '\n' + 'textStatus : ' + textStatus;
	errorMsg += '\n' + 'error : ' + error;
	console.log(errorMsg);
	alert("요청 중 오류 발생");
}