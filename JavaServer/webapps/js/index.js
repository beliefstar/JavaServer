/**
 * 
 */

window.onload = function () {
	var xhr = new XMLHttpRequest();
	xhr.open("POST", "http://localhost:8080/my.html", true);
	
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xhr.send("a=123&pwd=456");
	
	xhr.onreadystatechange = function () {
		if (xhr.status == 200 && xhr.readyStatus == 4) {
			alert();
		}
	}
}