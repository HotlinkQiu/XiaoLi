$(document).ready(initPage);

var paper;
var num;

function initPage() {
	requestByParam();
}

function requestByParam() {
	paper = getParam('paper');
	num = getParam('num');
	requestForImg();
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

function requestForImg() {
	$.getJSON('imglabel', {paper : paper, num : num}, function callback(json) {
		console.log(json);
		constructLabelPane(json['img'], json['label']);
	});
}

function constructLabelPane(img, label) {
	$('#imgPaneBody').html(img);
	$('#labelPaneBody').html(label);
}