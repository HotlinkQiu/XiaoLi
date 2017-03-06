$(document).ready(initPage);

var img;

function initPage() {
	requestByParam();
}

function requestByParam() {
	img = getParam('img');
	requestForImg(img);
}

function getParam(paramName) {
	var params = location.href.split('?')[1];
	var paramsArray = params.split('&');
	for(var i = 0; i < paramsArray.length; i ++) {
		var curParam = paramsArray[i];
		if(curParam.split('=')[0] == paramName) {
			return curParam.substring(curParam.indexOf('=') + 1);
		}
	}
	return null;
}

function requestForImg(img) {
	$.getJSON('imagelabel', {img : img}, function callback(json) {
		console.log(json);
		constructLabelPane(json['imgPath'], json['label']);
	});
}

function constructLabelPane(imgPath, label) {
	$('#imgPaneBody').html(imgPath);
	$('#labelPaneBody').html(label);
}