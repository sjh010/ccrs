/**
 * 문자열을 정수로 변환
 * 
 * @param str	문자열
 * @returns	변환된 정수
 */
function toInt(str) {
	var n = null;
	try {
		n = parseInt(str, 10);
	} catch (e) {
	}
	return n;
}

function fileSize(bytes) {
	if (typeof bytes !== 'number') {
        return '';
    }
    if (bytes >= 1024 * 1024 * 1024) {
        return (bytes / 1024 / 1024/ 1024).toFixed(2) + 'GB';
    }
    if (bytes >= 1024 * 1024) {
        return (bytes / 1024 / 1024).toFixed(1) + 'MB';
    }
    if (bytes >= 1024) {
    	return (bytes / 1024).toFixed(1) + 'KB';
    }
    return bytes + 'bytes';
}