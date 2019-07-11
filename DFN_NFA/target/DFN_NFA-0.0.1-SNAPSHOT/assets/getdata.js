//var linkDataArray = [{
//	"from": 1,
//	"text": "ℰ",
//	"to": 2
//}, {
//	"from": 2,
//	"text": "0",
//	"to": 2
//}, {
//	"from": 2,
//	"text": "ℰ",
//	"to": 3
//}, {
//	"from": 3,
//	"text": "ℰ",
//	"to": 4
//}, {
//	"from": 4,
//	"text": "ℰ",
//	"to": 5
//}, {
//	"from": 4,
//	"text": "0",
//	"to": 4
//}, {
//	"from": 4,
//	"text": "1",
//	"to": 6
//}, {
//	"from": 6,
//	"text": "0",
//	"to": 4
//}, {
//	"from": 5,
//	"text": "ℰ",
//	"to": 7
//}, {
//	"from": 7,
//	"text": "0",
//	"to": 7
//}, {
//	"from": 7,
//	"text": "ℰ",
//	"to": 8
//}]
//linkDataArray=[];
function ToNFA() {
	if($('#formula').val() == "" || $('#formula').val() == null)
		alert("正规式不能为空！");
	else {
		$('#sample').html("<h3 class=\"box-title\">状态转换图如下：</h3><div id=\"myDiagramDiv\" style=\"border: solid 1px black; width:800px; height:400px\"></div>");
		//console.log($('#formula').val());
		$.ajax({
			type: "post",
			url: "ToNFA",
			data: {
				formula: $('#formula').val(),
			},
			dataType: "json",
			async: true,
			success: function(data) {
				linkDataArray = data;
				init();
			},
			error: function(data) {
				console.log("操作失败！");
			}
		});
	}
}
function ToDefinedDFA() {
	if($('#formula').val() == "" || $('#formula').val() == null)
		alert("正规式不能为空！");
	else {
		$('#sample').html("<h3 class=\"box-title\">状态转换图如下：</h3><div id=\"myDiagramDiv\" style=\"border: solid 1px black; width:800px; height:400px\"></div>");
		console.log($('#formula').val());
		$.ajax({
			type: "post",
			url: "ToDefinedDFA",
			data: {
				formula: $('#formula').val(),
			},
			dataType: "json",
			async: true,
			success: function(data) {
				linkDataArray = data;
				init();
			},
			error: function(data) {
				console.log("操作失败！");
			}
		});
	}
	
}

function ToMinimumDFA() {
	if($('#formula').val() == "" || $('#formula').val() == null)
		alert("正规式不能为空！");
	else {
		$('#sample').html("<h3 class=\"box-title\">状态转换图如下：</h3><div id=\"myDiagramDiv\" style=\"border: solid 1px black; width:800px; height:400px\"></div>");
		console.log($('#formula').val());
		$.ajax({
			type: "post",
			url: "ToMinimumDFA",
			data: {
				formula: $('#formula').val(),
			},
			dataType: "json",
			async: true,
			success: function(data) {
				linkDataArray = data;
				init();
			},
			error: function(data) {
				console.log("操作失败！");
			}
		});
	}
}

//去重
function unique() {
	var arr = new Array();
	for(var i in linkDataArray) {
		arr.push(linkDataArray[i].from);
		arr.push(linkDataArray[i].to);
	}
	arr.sort(function(num1, num2) {
		return num1 - num2;
	})
	var nodeDataArray = [];
	for(var i = 0; i < arr.length; i++) {
		var data = {};
		for(var j = i + 1; j < arr.length; j++) {
			if(arr[i] === arr[j]) {
				++i;
			}
		}
		data["key"] = arr[i];
		data["text"] = arr[i];
		data["color"] = getSimpleColor();
		nodeDataArray.push(data);
	}
	//	return JSON.stringify(nodeDataArray);
	//		console.log(JSON.stringify(nodeDataArray));
	return nodeDataArray;
}