$(document).ready(initPage);

function initPage() {
	requestByParam();
}

function requestByParam() {
	var sentence = decodeURIComponent(getParam('sentence'));
	console.log(sentence);
	$('#sentencePaneBody').html(sentence);
	printTree(sentence);
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

function printTree(sentence){
	var is_ch = /chrome/.test(navigator.userAgent.toLowerCase()); 
	if(sentence == ""){
		return;
	}
    
	var iscorrect=validateTree(sentence);
	if(iscorrect){
		createJsTree(sentence);
		showEchartsTree(document.getElementById("treePaneBody"),getCompleteEchartsTree(true));
	}
	else{
		alert("句法分析结果有误！")
	} 
}