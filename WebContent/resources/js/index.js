$(document).ready(init);

function init() {
	initButton();
	initSample();
	bindEnter();
}

function initButton() {
	$('#runButton').bind('click', function() {
		var paper = $('#paperInput')[0].value;
		var code = getRandomCode();
		if(paper != null && paper != '') {
			runEngine(code, paper);
			window.location.href = './main.html?code='+code+'&paper='+paper;
		}
		
	});
}

function getRandomCode() {
	return Math.random().toString(36).substr(2);
}

function runEngine(code, paper) {
	$.get('run', {code : code, paper : paper}, function() {
	});
}

function initSample() {
	$('a.sample').bind('click', sampleHandler);
}

function sampleHandler() {
	addSampleToPaperInput(this.text);
}

function addSampleToPaperInput(text) {
	$('#paperInput')[0].value = text;
}

function bindEnter() {
	$(document).keydown(function(event) {
		if(event.keyCode == 13) {
			$("#runButton").click();
		}
	});
}