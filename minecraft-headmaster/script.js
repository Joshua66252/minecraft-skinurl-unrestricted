var hName = document.getElementById("hName")
var hUrl = document.getElementById("hUrl")
var hFull = document.getElementById("hFull")
var output = document.getElementById("output")
buttonClick = function(){
	let hFullVal = hFull.checked
	let hNameVal = hName.value
	let hUrlVal = hUrl.value
	let strBuild = ""
	let b64Build = ""
	let strPt1 = '/give @p player_head{SkullOwner:{Id:[I;0,0,0,0],Name:"'
	let strPt2 = '",Properties:{textures:[{Value:"'
	let hFullstrPt3 = '"}]}},Tags:["fullbody"]}'
	let strPt3 = '"}]}}}'
	let b64Pt1 = '{"textures":{"SKIN":{"url":"'
	let b64Pt2 = '"}}}'
	strBuild+=strPt1
	strBuild+=hNameVal
	strBuild+=strPt2
	b64Build+=b64Pt1
	b64Build+=hUrlVal
	b64Build+=b64Pt2
	strBuild+=btoa(b64Build)
	if(hFullVal){
		strBuild+=hFullstrPt3
	}else{
		strBuild+=strPt3
	}
	output.innerHTML = strBuild
	//B64
}
clipBoard = function(){
	var copyText = output.innerHTML
	if(copyText != ""){
	    navigator.clipboard.writeText(copyText).then(() => {
	        alert("Copied to clipboard")
	    })
	}else{
		alert("Nothing to copy")
	}
}