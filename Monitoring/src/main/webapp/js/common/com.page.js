/**
 * Popup Layer Load
 * 
 * @param obj		
 * @param options 	{left:pixel top:pixel}
 * @returns
 */
function jsShowBlockLayer(obj, options) {
	options = options || {};

	var width = $(obj).width();
	var height = $(obj).height();
	var windowHeight = window.document.body.offsetHeight;
	var scrollPos = $(document).scrollTop();

	var left = 0;
	if (options.left) {
		left = options.left;
	} else {
		left = ($(document).width() - width) / 2;
		left = (left > 0) ? left : 0;
	}
	
	var top = 0;
	if (options.top) {
		top = options.top;
	} else {
		top = (windowHeight - height) / 2 + scrollPos;
	}
	
	if (options.addTop) {
		top -= options.addTop
	}
	
	$(obj).bPopup({
		modalClose: false,
	    opacity: 0.6,
	    followSpeed: 1500, //can be a string ('slow'/'fast') or int
	    speed: 450,
	    follow: [false, false], 
	    position: ['auto', 'auto'],
	    escClose: false
	});
}

/**
 * URL을 로드한 후, 레이어 팝업을 생성
 * @param obj
 * @param url
 * @param options
 */
function jsShowBlockLayerWithUrl(obj, url, options) {
	$(obj).empty();
	$(obj).load(url, function() {
		options = options || {};

		var width = $(obj).width();
		var height = $(obj).height();
		var windowHeight = window.document.body.offsetHeight;
		var scrollPos = 0;//$(document).scrollTop();
		
		var left = 0;
		if (options.left) {
			left = options.left;
		} else {
			left = ($(document).width() - width) / 2;
			left = (left > 0) ? left : 0;
		}
		
		var top = 0;
		if (options.top) {
			top = options.top;
		} else {
			top = (windowHeight - height) / 2 + scrollPos;
		}
		
		if (options.addTop) {
			top -= options.addTop
		}
		
		$(obj).bPopup({
			modalClose: false,
		    opacity: 0.6,
		    followSpeed: 1500, //can be a string ('slow'/'fast') or int
		    speed: 450,
		    follow: [false, false], 
		    position: ['auto', 'auto'],
		    escClose: false
		});
	});
}

/**
 * Popup Layer Close
 * 
 * @param obj
 * @returns
 */
function jsHideBlockLayer(obj) {
	$(obj).bPopup().close();
}

/**
 * 페이지 넘버링 설정
 * @param pageNo:현재페이지
 * @param totalCount:전체 항목 수
 * @param pageSize:페이지당 항목 수
 * @returns
 */
function jsPageNumbering(pageNo, totalCount, pageSize) { 
	jsPaging(pageNo, totalCount, pageSize, 10, "jsPage", "divPaging");
} 

/**
 * 페이지 넘버링 설정 : << < 11 12 13 14 15 16 17 18 19 20 > >>
 * 
 * @param pageNo : 현재페이지
 * @param totalCount : 전체 항목 수
 * @param pageSize : 페이지당 항목 수
 * @param pageBlock : 한번에 보여지는 최대 페이지 수
 * @param fn : 페이징 처리 함수명
 * @param id : 페이지 표시 div id
 * @returns
 */
function jsPaging(pageNo, totalCount, pageSize, pageBlock, fn, id) { 
	var totalPageCount = toInt(totalCount / pageSize) + (totalCount % pageSize > 0 ? 1 : 0);
	var totalBlockCount = toInt(totalPageCount / pageBlock) + (totalPageCount % pageBlock > 0 ? 1 : 0);
	var blockNo = toInt(pageNo / pageBlock) + (pageNo % pageBlock > 0 ? 1 : 0);
	var startPageNo = (blockNo - 1) * pageBlock + 1;
	var endPageNo = blockNo * pageBlock;

	//console.log(totalPageCount + " / " + totalBlockCount + " / " + blockNo + " / " + startPageNo + " / " + endPageNo);

	if (endPageNo > totalPageCount) {
		endPageNo = totalPageCount;
	}
	var prevBlockPageNo = (blockNo - 1) * pageBlock;
	var nextBlockPageNo = blockNo * pageBlock + 1;
	
	var strHTML = "<ul>";
	if (totalPageCount > 1 && pageNo != 1 && blockNo > 1) {
		strHTML += "<li><a href='javascript:" + fn + "(1);'>&lt;&lt;</a></li>";
	}
	if (prevBlockPageNo > 0) {
		strHTML += "<li><a href='javascript:" + fn + "(" + prevBlockPageNo + ");'>&lt;</a></li>";
	}
	strHTML += "<li>";
	for (var i = startPageNo; i <= endPageNo; i++) {
		if (i == pageNo) {
			strHTML += "<span class='selected'>"+i+"</span>";
		} else {
			strHTML += "<a href='javascript:" + fn + "(" + i + ");'>" + i + "</a>";
		}
	}
	strHTML += "</li>";
	if (nextBlockPageNo <= totalPageCount) {
		strHTML += "<li><a href='javascript:" + fn + "(" + nextBlockPageNo + ");' class='ml8'>&gt;</a>";
	}
	if (totalPageCount > 1 && pageNo != totalPageCount && blockNo < totalBlockCount) {
		strHTML += "<li><a href='javascript:" + fn + "(" + totalPageCount + ");' class='ml10'>&gt;&gt;</a>";
	}
	strHTML += "</ul>";

	$('#' + id).html(strHTML);
}

function jsCheckboxControl(allCheckboxName, checkboxName, allCheckboxFunc, checkboxFunc) {
	$(document).on("change", "input[type='checkbox'][name='" + allCheckboxName+ "'], input[type='checkbox'][name='" + checkboxName+ "']", function() {
		var _allCheckbox = $("input[name='"+ allCheckboxName + "']");
		var _checkbox = $("input[name='" + checkboxName+ "']");
	
		if ($(this).attr("name") == allCheckboxName) {
			_checkbox.filter(":not([disabled])").prop("checked", this.checked);
		} else {
			if (_checkbox.filter(":not([disabled])").length == _checkbox.filter(":checked").length) {
				_allCheckbox.prop("checked", true);
			} else {
				_allCheckbox.prop("checked", false);
			}
		}
	
		if ($.isFunction(allCheckboxFunc)) {
			allCheckboxFunc(_allCheckbox.prop("checked"));
		}
		if ($.isFunction(checkboxFunc)) {
			checkboxFunc((_checkbox.filter(":checked").length > 0));
		}
	});
}

function jsTrimInputValue(formId) {
	var agent = navigator.userAgent.toLowerCase();
	if (agent.indexOf("msie 8.0") == -1) {				// IE8 trim 미지원
		$("#"+formId+" input").each(function(index, item) {
			$(item).val($(item).val().trim());
		});
	}
}
