$(document).ready(init);

var code;
var paper;
var mainStep;
var selProblemNo;
var saQuestionNo;
var pollId;
var pollIdTry;
var tryPollTime;

function init() {
	clearPage();
	requestByParam();
	console.log(code);
	console.log(paper);
	runEngine();
}

function clearPage() {
	$('#processPaneBody').html(null);
	$('#selProblemDiv').html(null);
	$('#saQuestionDiv').html(null);
	$('#problemPane').css('display', 'none');
	$('#infoPane').css('display', 'none');
	$('#solverDiv').css('display', 'none');
	$('#checkButton').attr('disabled', true);
	mainStep = 0;
	selProblemNo = 0;
	saQuestionNo = 0;
}

function requestByParam() {
	code = getParam('code');
	paper = getParam('paper');
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

function runEngine() {
	$.get('run', {code : code, paper : paper}, function() {
	});
	pollId = setInterval(poll, 1000);
}

function poll() {
	$.getJSON('poll', {code : code, paper : paper}, function callback(json) {
		parseLogJSON(json);
		parseProJSON(json);
	});
}

function parseLogJSON(json) {
	var mainLogInfo = json['mainLog'];
	updateMainLogInfo(mainLogInfo);
	
	var mainStepTemp = json['mainStep'];
	if(mainStep != mainStepTemp) {
		mainStep = mainStepTemp;
		var mainStepName = json['msName'];
		updateMainStepPane(mainStepName);
	}
	
	if(mainStep >= 10) {
		clearInterval(pollId);
		mainStep = 0;
		bindCheckButton();
		$('#runButton').attr('disabled', false);
		
		tryPollTime = 0;
		pollIdTry = setInterval(tryPoll, 1000);
	}
}

function tryPoll() {
	console.log("try poll");
	tryPollTime ++;
	if(tryPollTime == 2) {
		clearInterval(pollIdTry);
	}
	$.getJSON('poll', {code : code, paper : paper}, function callback(json) {
		parseProJSON(json);
	});
}

function updateMainLogInfo(mainLogInfo) {
	$('#processPaneBody').html(mainLogInfo);
}

function updateMainStepPane(mainStepName) {
	$('#mainStep').html(mainStepName);
	var progress = mainStep*10+'%';
	$('#progBar').css('width', progress);
}

function parseProJSON(json) {
	var selProblemNoTemp = json['selNo'];
	var saQuestionNoTemp = json['saNo'];
	
	if(selProblemNoTemp != selProblemNo) {
		selProblemNo = selProblemNoTemp;
		constructSelButtons(selProblemNo);
	}
	
	if(saQuestionNoTemp != saQuestionNo) {
		saQuestionNo = saQuestionNoTemp;
		constructSaButtons(saQuestionNo);
	}
	
	var selProblemStatus = json['selStatus'];
	updateSelProblemStatus(selProblemStatus);
	var saQuestionStatus = json['saStatus'];
	updateSaQuestionStatus(saQuestionStatus);
}

function constructSelButtons(selProblemNo) {
	var divElement = $('#selProblemDiv');

	var spanElement = $('<span>', {
		text : '选择题：',
	});
	spanElement.css('display', 'block');
	divElement.append(spanElement);

	for(var i = 1; i <= selProblemNo; i ++) {
		var buttonElement = $('<a>', {
			class : 'btn btn-default disabled',
			id : 'selProblem'+i,
			text : i
		});
		buttonElement.css('width', '50px');
		buttonElement.css('margin', '5px 10px 5px 0px');
		buttonElement.click(function() {
			var problemNo = $(this).attr('id').substring(10);
			$.getJSON('probleminfo', {problemNo : problemNo} , function(json) {
				constructProblemInfo(json['info']);
				constructProblemSolver(json['solver'], json['el']);
			});	
		});
		divElement.append(buttonElement);
	}
}

function constructSaButtons(saQuestionNo) {
	var divElement = $('#saQuestionDiv');

	var spanElement = $('<span>', {
		text : '简答题：'
	});
	spanElement.css('display', 'block');
	divElement.append(spanElement);

	for(var i = 1; i <= saQuestionNo; i ++) {
		var buttonElement = $('<a>', {
			class : 'btn btn-default disabled',
			id : 'saQuestion'+i,
			text : i
		})
		buttonElement.click(function() {
			var problemNo = parseInt($(this).attr('id').substring(10))+selProblemNo;
			$.getJSON('probleminfo', {problemNo : problemNo} , function(json) {
				constructProblemInfo(json['info']);
				constructProblemSolver(json['solver'], json['el']);
			});	
		});;
		buttonElement.css('width', '50px');
		buttonElement.css('margin', '5px 10px 5px 0px');
		divElement.append(buttonElement);
	}
}

function updateSelProblemStatus(selProblemStatus) {
	for(var i = 1; i <= selProblemStatus.length; i ++) {
		if(selProblemStatus[i-1] == 1) {
			var buttonElement = $('#selProblem'+i);
			buttonElement.removeClass();
			buttonElement.addClass('btn btn-warning disabled');
		}
		if(selProblemStatus[i-1] == 2) {
			var buttonElement = $('#selProblem'+i);
			buttonElement.removeClass();
			buttonElement.addClass('btn btn-primary');
		}
	}
}

function updateSaQuestionStatus(saQuestionStatus) {
	for(var i = 1; i <= saQuestionStatus.length; i ++) {
		if(saQuestionStatus[i-1] == 1) {
			var buttonElement = $('#saQuestion'+i);
			buttonElement.removeClass();
			buttonElement.addClass('btn btn-warning disabled');
		}
		if(saQuestionStatus[i-1] == 2) {
			var buttonElement = $('#saQuestion'+i);
			buttonElement.removeClass();
			buttonElement.addClass('btn btn-primary');
		}
	}	
}

function constructProblemInfo(info) {
	$('#problemPane').css('display', 'block')
	$('#problemPaneBody').html(info);
}

function constructProblemSolver(solver, el) {
	$('#infoPane').css('display', 'block');
	$('#solverDiv').css('display', 'block');
	$('#solverPane').html(solver);
	$('#elPane').html(el);
}

function parseResultJSON(json) {
	var results = json['results'];
	for(var i = 1; i <= results.length; i ++) {
		var buttonElement = $('#selProblem'+i);
		if(results[i-1] == 1) {
			buttonElement.removeClass();
			buttonElement.addClass('btn btn-success');
		} else if(results[i-1] == -1) {
			buttonElement.removeClass();
			buttonElement.addClass('btn btn-danger');
		} else if(results[i-1] == 0) {
			buttonElement.removeClass();
			buttonElement.addClass('btn btn-info');
		}
	}
	
	var info = json['info'];
	updateMainLogInfo(info);
}

function bindCheckButton() {
	$('#checkButton').attr('disabled', false);
	$('#checkButton').bind('click', function() {
		$.getJSON('problemresult', null, function callback(json) {
			parseResultJSON(json);
		});
	});
}