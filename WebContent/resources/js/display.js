$(document).ready(init);

var mainStep;
var selProblemNo;
var saQuestionNo;
var pollId;
var pollIdTry;
var tryPollTime;

function init() {
	clearPage();
	initButton();
}

function initButton() {
	$('#runButton').bind('click', function() {
		var paper = $('#paperInput')[0].value;
		clearPage();
		$.get('run', {paper : paper, run : 0}, function() {
		});
		$('#runButton').attr('disabled', true);
		pollId = setInterval(poll, 1000);
	});
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

function poll() {
	var paper = $('#paperInput')[0].value;
	$.getJSON('logprint', {paper : paper, run : 0}, function callback(json) {
		parseLogJSON(json);
	});
	
	$.getJSON('problemstatus', null, function callback(json) {
		parseProJSON(json);
	});
}

function parseLogJSON(json) {
	var mainLogInfo = json['mainLogInfo'];
	updateMainLogInfo(mainLogInfo);
	
	var mainStepTemp = json['mainStep'];
	if(mainStep != mainStepTemp) {
		mainStep = mainStepTemp;
		var mainStepName = json['mainStepName'];
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
	$.getJSON('problemstatus', null, function callback(json) {
		parseProJSON(json);
	});
}

function updateMainLogInfo(mainLogInfo) {
	var content = $('#processPaneBody').html();
	$('#processPaneBody').html(mainLogInfo+content);
}

function updateMainStepPane(mainStepName) {
	$('#mainStep').html(mainStepName);
	var progress = mainStep*10+'%';
	$('#progBar').css('width', progress);
}

function parseProJSON(json) {
	var selProblemNoTemp = json['selProblemNo'];
	var saQuestionNoTemp = json['saQuestionNo'];
	
	if(selProblemNoTemp != selProblemNo) {
		selProblemNo = selProblemNoTemp;
		constructSelButtons(selProblemNo);
	}
	
	if(saQuestionNoTemp != saQuestionNo) {
		saQuestionNo = saQuestionNoTemp;
		constructSaButtons(saQuestionNo);
	}
	
	var selProblemStatus = json['selProblemStatus'];
	updateSelProblemStatus(selProblemStatus);
	var saQuestionStatus = json['saQuestionStatus'];
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
//		$('#checkButton').attr('disabled', true);
	});
}