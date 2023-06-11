const canvas = document.getElementById("hexagons");
const width = canvas.getAttribute("width");
const height = canvas.getAttribute("height");
const halfWidth = width / 2;
const halfHeight = height / 2;

const ctx = canvas.getContext("2d");
const paddingRatio = 0.95;

const updateInterval = 250;
const drawInterval = 10;

var panelLayout;
var panelColors;

var panelIds = [];
var sideLength;

var orientationRad;
var sinO;
var cosO;

var layoutArr = [];

var prevColors = {};
var currentColors = {};
var lastUpdateMillis = -1;


function switchEffects() {
	fetch("http://10.2.7.84:3333/effect", {method: "PUT", body: JSON.stringify({name: "flash"})}).then(() => {
		setTimeout(() => {
			fetch("http://10.2.7.84:3333/effect", {method: "PUT", body: JSON.stringify({name: "transition"})}).then(() => {
				setTimeout(switchEffects, 20000);
			})
		}, 20000);
	});
}

fetchLayout().then(newPanelLayout => {
	updatePanelLayout(newPanelLayout);
	setInterval(() => {
		fetchColors().then(newPanelColors => {
			updatePanelColors(newPanelColors);
		})
	}, updateInterval);
});

/*
setInterval(() => {
	console.log("foo!");
	fetch("http://10.2.7.84:3333/effect", {method: "PUT", body: JSON.stringify({name: "flash"})});
}, 20000);

setTimeout(() => {
	setInterval(() => {
		console.log("bar!");
		fetch("http://10.2.7.84:3333/effect", {method: "PUT", body: JSON.stringify({name: "transition"})});
	}, 20000);
}, 10000);
*/

function red() {
	fetch("http://10.2.7.84:3333/effect", {method: "PUT", body: JSON.stringify({name: "flash"})});
}

function green() { 
	fetch("http://10.2.7.84:3333/effect", {method: "PUT", body: JSON.stringify({name: "transition"})});

}



async function fetchLayout() {
	const response = await fetch("http://10.2.7.84:3333/panelLayout", {method: "GET"});
	panelLayout = await response.json();	
	
	return panelLayout;
}

async function fetchColors() {
	const response = await fetch("http://10.2.7.84:3333/panelColors", {method: "GET"});
	panelColors = await response.json();
	
	return panelColors;
}

function lerp(from, to, t) {
	const r = (to["r"] - from["r"]) * t + from["r"];
	const g = (to["g"] - from["g"]) * t + from["g"];
	const b = (to["b"] - from["b"]) * t + from["b"];
	
	return {r: r, g: g, b: b};
}

function updateLayoutArr() {
	var minX = Number.MAX_SAFE_INTEGER, maxX = Number.MIN_SAFE_INTEGER;
	var minY = Number.MAX_SAFE_INTEGER, maxY = Number.MIN_SAFE_INTEGER;
	var numPanels = 0;
	
	for (var i in panelLayout["panelLayout"]) {
		var panel = panelLayout["panelLayout"][i];
		
		var x = panel["x"];
		var y = panel["y"];
		
		var newX = x * cosO + y * sinO;
		var newY = y * cosO - x * sinO;
		
		if (newX > maxX) maxX = newX;
		if (newX < minX) minX = newX;
		if (newY > maxY) maxY = newY;
		if (newY < minY) minY = newY;
		
		layoutArr.push(newX, newY);
		
		numPanels ++;
	}
	
	var centerX = (minX + maxX) / 2;
	var centerY = (minY + maxY) / 2;
	
	var layoutWidth = (maxX - minX) + 2 * 0.866 * sideLength;
	var layoutHeight = (maxY - minY) + 2 * sideLength;
	
	var scaleRatio = Math.min(width / layoutWidth, height / layoutHeight);
	sideLength *= scaleRatio;
	
	var dx = halfWidth - centerX;
	var dy = halfHeight - centerY;
		
	for (var i = 0; i < layoutArr.length; i += 2) {
		layoutArr[i] += dx;
		layoutArr[i + 1] += dy;
		
		layoutArr[i] = scaleRatio * (layoutArr[i] - halfWidth) + halfWidth;
		layoutArr[i + 1] = -scaleRatio * (layoutArr[i + 1] - halfHeight) + halfHeight;
	}
}

function componentToHex(c) {
	c = Math.round(c);
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

function draw() {
	const t = (Date.now() - lastUpdateMillis) / updateInterval;
	
	for (var i = 0; i < panelIds.length; i ++) {
		var color = panelColors["panelColors"][panelIds[i]];
		color = lerp(prevColors[panelIds[i]], color, t);
		currentColors[panelIds[i]] = color; 
		var hexColor = rgbToHex(color["r"], color["g"], color["b"]);
		fillHex(layoutArr[2 * i], layoutArr[2 * i + 1], hexColor);
	}
}

function updatePanelColors(newPanelColors) {
	panelColors = newPanelColors;
	
	if (lastUpdateMillis == -1) {
		for (var i = 0; i < panelIds.length; i ++) {
			prevColors[panelIds[i]] = panelColors["panelColors"][panelIds[i]];
		}
	} else {
		for (var i = 0; i < panelIds.length; i ++) {
			prevColors[panelIds[i]] = currentColors[panelIds[i]];
		}
	}
	
	lastUpdateMillis = Date.now();
	const id = setInterval(draw, drawInterval);
	setTimeout(() => { clearInterval(id) }, updateInterval);
}

function updatePanelLayout(newPanelLayout) {
	panelLayout = newPanelLayout;
	for (var i in panelLayout["panelLayout"]) {
		panelIds.push(panelLayout["panelLayout"][i]["id"]);
	}
	
	orientationRad = panelLayout["orientation"] * Math.PI / 180;
	sinO = Math.sin(orientationRad);
	cosO = Math.cos(orientationRad);
	
	sideLength = panelLayout["sideLength"];
	
	updateLayoutArr();
}
