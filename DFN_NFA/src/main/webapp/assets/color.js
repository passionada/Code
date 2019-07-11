	//color array
	colorBox = [ "skyblue", "cyan", "lightcyan", "red", "darkseagreen",
			"palevioletred", "coral" ];
	colorReplace = [];

	function getSimpleColor() {
		if (colorBox.length > 0) {

			var col1_num = Math.floor((Math.random() * colorBox.length));
			//选中的颜色
			var col1 = colorBox[col1_num];
			colorReplace.push(col1);
			colorBox[col1_num] = colorBox[colorBox.length - 1];
			colorBox = colorBox.splice(0, colorBox.length - 1);
			return col1;
		} else {
			temp = colorReplace;
			colorReplace = [];
			colorBox = temp;
			//重复上边操作
			var col1_num = Math.floor((Math.random() * colorBox.length));
			//选中的颜色
			var col1 = colorBox[col1_num];
			colorReplace.push(col1);
			colorBox[col1_num] = colorBox[colorBox.length - 1];
			colorBox = colorBox.splice(0, colorBox.length - 1);
			return col1;
		}
	}