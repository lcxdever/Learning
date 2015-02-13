function getTime(){
	var date=new Date();
	var month=(date.getMonth()+1);
	var day=date.getDate();
	var hour=date.getHours();
	var mi=date.getMinutes();
	if(month<10)month="0"+month;
	if(day<10)day="0"+day;
	if(hour<10)hour="0"+hour;
	if(mi<10)mi="0"+mi;
	$(".now").html(date.getFullYear()+"-"+month+"-"+day+" "+hour+":"+mi);
}
getTime();
window.setInterval("getTime()",60000 );