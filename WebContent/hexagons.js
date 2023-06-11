const net = require('net');
const client = new net.Socket();
client.connect({ port: 3333}, "localhost", () => {
	client.write({ request: "panelLayout" });
});
client.on("data", (data) => {
	console.log(data);
});

/*
const canvas = document.getElementById("hexagons");
const ctx = canvas.getContext("2d");
const orientationRad = -panelLayout["orientation"] * Math.PI / 180;
const sinO = Math.sin(orientationRad);
const cosO = Math.cos(orientationRad);

const panelDiameter = 20;

var layoutArr = [];
var panelIds = [];
var minX = Number.MAX_SAFE_INTEGER, maxX = Number.MIN_SAFE_INTEGER;
var minY = Number.MAX_SAFE_INTEGER, maxY = Number.MIN_SAFE_INTEGER;
var numPanels = 0;

for (var i in panelLayout["panelLayout"]) {
	var panel = panelLayout["panelLayout"][i];
	
	var x = panel["x"];
	var y = panel["y"];
	
	var newX = x * cosO - y * sinO;
	var newY = y * cosO + x * sinO;
	
	if (newX > maxX) maxX = newX;
	if (newX < minX) minX = newX;
	if (newY > maxY) maxY = newY;
	if (newY < minY) minY = newY;
	
	layoutArr.push(newX, newY);
	panelIds.push(panel["id"]);

	numPanels ++;
}

var centerX = (minX + maxX) / 2;
var centerY = (minY + maxY) / 2;

//include panel size
var sideLength = panelLayout["sideLength"];
console.log(sideLength);
var layoutWidth = (maxX - minX) + 2 * 0.866 * sideLength;
var layoutHeight = (maxY - minY) + 2 * sideLength;
var scaleRatio = Math.min(800 / layoutWidth, 400 / layoutHeight);
sideLength *= scaleRatio;
var dx = 400 - centerX;
var dy = 200 - centerY;

const paddingRatio = 0.95;

for (var i = 0; i < layoutArr.length; i += 2) {
	console.log("HI" + i + " " + layoutArr.length);
	layoutArr[i] += dx;
	layoutArr[i + 1] += dy;
	
	layoutArr[i] = scaleRatio * (layoutArr[i] - 400) + 400;
	layoutArr[i + 1] = scaleRatio * (layoutArr[i + 1] - 200) + 200;
}

function componentToHex(c) {
	var hex = c.toString(16);
	return hex.length == 1 ? "0" + hex : hex;
}

function rgbToHex(r, g, b) {
	return "#" + componentToHex(r) + componentToHex(g) + componentToHex(b);
}

function fillHex(x, y, color) {
	ctx.fillStyle = color;
	ctx.beginPath();
	
	for (var i = 0; i < 6; i ++) {
		var theta = Math.PI / 6 + Math.PI / 3 * i;
		
		var px = x + sideLength * paddingRatio * Math.cos(theta);
		var py = y + sideLength * paddingRatio * Math.sin(theta);
		
		if (i == 0) ctx.moveTo(px, py);
		else ctx.lineTo(px, py);
	}
	
	ctx.closePath();
	ctx.fill();
}


console.log(panelLayout);
console.log(panelColors);

function draw() {
	for (var i = 0; i < panelIds.length; i ++) {
		var color = panelColors["panelColors"][panelIds[i]];
		var hexColor = rgbToHex(color["r"], color["g"], color["b"]);
		fillHex(layoutArr[2 * i], layoutArr[2 * i + 1], hexColor);
	}
}
*/
