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

function ajaxGetCall(url, data, callbackFunc){
	ajaxCall("GET", url, data, false, callbackFunc);
}
function ajaxPostCall(url, data, isFile, callbackFunc){
	ajaxCall("POST", url, data, isFile, callbackFunc);
}

function ajaxCall(ajaxType, url, data, isFile, callbackFunc){
	$.ajax({
		type : ajaxType,
		//async : true,
		url : url,
		data : data,
		dataType : "json",
		contentType : (isFile? false : "application/json;charset=utf-8"),
		//contentType : "application/json;charset=utf-8",
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