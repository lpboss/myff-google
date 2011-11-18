<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>浩海森林防火预警平台</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>stylesheets/style.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>javascripts/greybox/gb_styles.css" media="all" />
<script type="text/javascript">var GB_ROOT_DIR = "<%=basePath%>javascripts/greybox/";</script>
<script type="text/javascript" src="<%=basePath%>javascripts/greybox/AJS.js"></script>
<script type="text/javascript" src="<%=basePath%>javascripts/greybox/AJS_fx.js"></script>
<script type="text/javascript" src="<%=basePath%>javascripts/greybox/gb_scripts.js"></script>
<script type="text/javascript" src="<%=basePath%>javascripts/ext4/ext-all.js"></script>
<script type="text/javascript" src="<%=basePath%>javascripts/ext4/locale/ext-lang-zh_CN.js"></script>
<style type="text/css">
<!--
#apDiv3 {
	position:absolute;
	width:132px;
	min-height:132px;
	z-index:100;
	right: 15px;
	top: 120px;
	overflow:visible;
}
#apDiv4 {
	position:absolute;
	width:89px;
	min-height:87px;
	right: 20px;
	top: 25px;
	z-index:200;
	overflow:visible;
}
.blk_29 {
 overflow:hidden;
 width:685px;
}
.blk_29 .pcont {
 width:630px;
 float:left;
 overflow:hidden;
}
.blk_29 .ScrCont {
 width:32766px;
}
.blk_29 #List1_1, .blk_18 #List2_1 {
 float:left;
}

-->
</style>
<script type="text/javascript"> 

//当前云台是否转动。
var isTurning = false;
//当前正在转动的方向
var turningDirection = "stop";

//控制云台动作
function ptzAction(ptzActionStr){
	//如果方向相同，就要停止转动。
	//console.warn("Before:   ptzActionStr:",ptzActionStr,",turningDirection:",turningDirection,",isTurning:"+isTurning);
	if (ptzActionStr == turningDirection){
		isTurning = false;
		turningDirection = "stop";
		ptzActionStr = "stop";
	}
				
	Ext.Ajax.request({
		url : '<%=basePath%>ptz/ptzAction.htm',
		success : function (result, request) {
			turningDirection = ptzActionStr;
			if(ptzActionStr == "stop"){
				isTurning = false;
			}else{
				isTurning = true;
			}
			//console.warn("After :   ptzActionStr:",ptzActionStr,",turningDirection:",turningDirection,",isTurning:"+isTurning);
		},
		failure : function (result, request){
			Ext.MessageBox.show({
				title: '消息',
				msg: "通讯失败，请从新操作",
				buttons: Ext.MessageBox.OK,
				icon: Ext.MessageBox.WARNING
			});
		},
		method : 'GET',
		params : {
			action_type : ptzActionStr
		}
	});
}

//切换监控通道
function setChannel(ptzControlIP,ptzAlertIP,visibleRTSPUrl,infraredRTSPUrl,gisMapUrl){
	document.getElementById("map").setChannel(ptzControlIP,ptzAlertIP,"../images/"+gisMapUrl);

	document.getElementById("infraredPlayer").playlist.add(infraredRTSPUrl,infraredRTSPUrl, " :rtsp-caching=200");
	document.getElementById("infraredPlayer").playlist.play();
	
	document.getElementById("visiblePlayer").playlist.add(visibleRTSPUrl,visibleRTSPUrl, " :rtsp-caching=200");
	document.getElementById("visiblePlayer").playlist.play();
}

//显示当前系统时间
function showTime(){
	var myDate = new Date();
	var Today = ["星期日","星期一","星期二","星期三","星期四","星期五","星期六"];
	//获取当前年
	var Years = myDate.getFullYear();
	//获取当前月
	var Months = myDate.getMonth() + 1;
	//获取当前日
	var Dates = myDate.getDate();
	//获取当前天是当前周的第几天
	var Days = Today[myDate.getDay()];
	//获取当前的时
	var Hours = myDate.getHours();
	//获取当前的分
	var Minutes = myDate.getMinutes();
	//获取当前的秒
	var Seconds = myDate.getSeconds();

	Months = Months < 10 ? '0' + Months : Months; 
	Dates = Dates < 10 ? '0' + Dates : Dates;
	Hours = Hours < 10 ? '0' + Hours : Hours;
	Minutes = Minutes < 10 ? '0' + Minutes : Minutes;
	Seconds = Seconds < 10 ? '0' + Seconds : Seconds;

	var timeValue=Years+"-"+Months+"-"+Dates+" "+Hours+":"+Minutes+":"+Seconds+" "+Days;

	//输出
	document.getElementById("sysTime").innerHTML=timeValue;
	setTimeout("showTime()","1000");

}

//根据窗口大小自动等比例缩放控件大小
function resizeWindow(){
	var clientWidth=document.body.clientWidth;
	document.getElementById("infraredPlayer").style.width=parseInt(clientWidth*0.256)+"px";
	document.getElementById("infraredPlayer").style.height=parseInt(clientWidth*0.256*0.818)+"px";
	document.getElementById("map").style.width=parseInt(clientWidth*0.256)+"px";
	document.getElementById("map").style.height=parseInt(clientWidth*0.256*0.818)+"px";
	document.getElementById("visiblePlayer").style.width=parseInt(clientWidth*0.744)+"px";
	document.getElementById("visiblePlayer").style.height=parseInt(clientWidth*0.744*0.563)+"px";
	//alert(clientWidth+document.getElementById("infraredPlayer").style.width+document.getElementById("visiblePlayer").style.width);
}

//启动客户端报警，本方法由flex调用
var alarming=true;
function startAlarm(){	
	if(!document.getElementById("alarmPlayer").Played && alarming){
		document.getElementById("alarmPlayer").play();
    }
}

//关闭客户端报警
function switchAlarm(obj){
	if(alarming){
		alarming=false;
		document.getElementById("alarmPlayer").stop();
		obj.innerText="开启报警";
    }else{
		alarming=true;
		obj.innerText="关闭报警";
	}
}

/*begin云台节点左右翻屏*/
var Speed_1 = 25; //速度(毫秒)
var Space_1 = 20; //每次移动(px)
var PageWidth_1 = 625; //翻页宽度
var fill_1 = 0; //整体移位
var MoveLock_1 = false;
var MoveTimeObj_1;
var MoveWay_1="right";
var Comp_1 = 0;

function ISL_GoUp_1(){
	if(MoveLock_1) return;
	MoveLock_1=true;
	MoveWay_1="left";
	MoveTimeObj_1=setInterval('ISL_ScrUp_1();',Speed_1);
}

function ISL_StopUp_1(){
	if(MoveWay_1 == "right"){
		return
	};
	clearInterval(MoveTimeObj_1);
	if((document.getElementById('ISL_Cont_1').scrollLeft-fill_1)%PageWidth_1!=0){
		Comp_1=fill_1-(document.getElementById('ISL_Cont_1').scrollLeft%PageWidth_1);
		CompScr_1()
	}else{
		MoveLock_1=false
	}
}
function ISL_ScrUp_1(){
	if(document.getElementById('ISL_Cont_1').scrollLeft<=0){
		document.getElementById('ISL_Cont_1').scrollLeft=document.getElementById('ISL_Cont_1').scrollLeft+document.getElementById('List1_1').offsetWidth
	}
	document.getElementById('ISL_Cont_1').scrollLeft-=Space_1
}
function ISL_GoDown_1(){
	clearInterval(MoveTimeObj_1);
	if(MoveLock_1) return;
	MoveLock_1=true;
	MoveWay_1="right";
	ISL_ScrDown_1();
	MoveTimeObj_1=setInterval('ISL_ScrDown_1()',Speed_1)
}
function ISL_StopDown_1(){
	if(MoveWay_1 == "left") return;
	clearInterval(MoveTimeObj_1);
	if(document.getElementById('ISL_Cont_1').scrollLeft%PageWidth_1-(fill_1>=0?fill_1:fill_1+1)!=0){
		Comp_1=PageWidth_1-document.getElementById('ISL_Cont_1').scrollLeft%PageWidth_1+fill_1;
		CompScr_1()
	}else{
		MoveLock_1=false
	}
}
function ISL_ScrDown_1(){
	if(document.getElementById('ISL_Cont_1').scrollLeft>=document.getElementById('List1_1').scrollWidth){
		document.getElementById('ISL_Cont_1').scrollLeft=document.getElementById('ISL_Cont_1').scrollLeft-document.getElementById('List1_1').scrollWidth
	}
	document.getElementById('ISL_Cont_1').scrollLeft+=Space_1
}
function CompScr_1(){
	if(Comp_1==0){
		MoveLock_1=false;
		return
	}
	var num,TempSpeed=Speed_1,TempSpace=Space_1;
	if(Math.abs(Comp_1)<PageWidth_1/2){
		TempSpace=Math.round(Math.abs(Comp_1/Space_1));
		if(TempSpace<1) TempSpace=1
	}
	if(Comp_1<0){
		if(Comp_1<-TempSpace){
			Comp_1+=TempSpace;
			num=TempSpace
		}else{
			num=-Comp_1;
			Comp_1=0
		}
		document.getElementById('ISL_Cont_1').scrollLeft-=num;
		setTimeout('CompScr_1()',TempSpeed)
	}else{
		if(Comp_1>TempSpace){
			Comp_1-=TempSpace;
			num=TempSpace
		}else{
			num=Comp_1;
			Comp_1=0
		}
		document.getElementById('ISL_Cont_1').scrollLeft+=num;
		setTimeout('CompScr_1()',TempSpeed)
	}
}
function picrun_init(){
	document.getElementById("List2_1").innerHTML=document.getElementById("List1_1").innerHTML;
	document.getElementById('ISL_Cont_1').scrollLeft=fill_1>=0?fill_1:document.getElementById('List1_1').scrollWidth-Math.abs(fill_1);
}
/*end云台节点左右翻屏*/

</script>   
</head>
<body onresize="resizeWindow();" onload="showTime();picrun_init();resizeWindow();">
<div class="top" >
  <div class="top1"><img src="<%=basePath%>images/toplname.jpg" width="315" height="69" /></div>
  <div class="top2"><embed id="alarmPlayer" src="<%=basePath%>images/alarm.wav" type="audio/mpeg" autostart="false" loop="true" hidden="true"></embed></div>
  <div id="sysTime" class="top3">2011-11-11 11:11:11 星期一</div>
</div>

<div class="nav">
    <div class="daohang">  
      <ul>
          <li><a href="<%=basePath%>index/index.htm" class="daohang_h">监控预览</a> </li>
          <li><a href="<%=basePath%>fireAlarm/fireAlarmList.htm" title="防火事件" rel="gb_page[800, 600]">防火事件</a></li>
          <li><a href="<%=basePath%>record/recordSearch.htm" title="录像查询" rel="gb_page[800, 600]">录像查询</a></li>
          <li><a href="<%=basePath%>index/index2.htm" title="用户管理" rel="gb_page[1000, 600]">用户管理</a></li>
          <li><a href="<%=basePath%>index/index2.htm" title="系统设置" rel="gb_page[1000, 600]">系统设置</a></li>
        </ul>
      </div>
        <div class="denglu">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="11%" align="right"><img src="<%=basePath%>images/star.png" width="18" height="19"  /></td>
              <td width="62%" height="37">当前用户：Admin</td>
              <td width="8%" align="right"><img src="<%=basePath%>images/x.png" width="18" height="19" /></td>
              <td width="19%">退出</td>
            </tr>
          </table>
        </div>
    </div>
    <div class="center">
      <div class="content_left">
         <div class="cleft1">
            <OBJECT classid="clsid:9BE31822-FDAD-461B-AD51-BE1D1C159921" width="100%" height="100%" id="infraredPlayer" events="True">
                <param name="Src" value="" />
                <param name="ShowDisplay" value="True" />
                <param name="AutoLoop" value="False" />
                <param name="AutoPlay" value="true" />
            </OBJECT>
            </div>
        <div class="cleft2">
            <object id="map" type="application/x-shockwave-flash" data="<%=basePath%>images/map.swf" width="300" height="270">
                <param name="movie" value="<%=basePath%>images/map.swf" />
                <param name="quality" value="high" />
                <param name="allowScriptAccess" value="sameDomain" />
                <param name="allowFullScreen" value="true" />
                <param name="FlashVars" value="port=8004" />
            </object>
       </div>
      </div>
      <div class="content_right">
            <OBJECT classid="clsid:9BE31822-FDAD-461B-AD51-BE1D1C159921" width="100%" height="100%" id="visiblePlayer" events="True">
                <param name="Src" value="" />
                <param name="ShowDisplay" value="True" />
                <param name="AutoLoop" value="False" />
                <param name="AutoPlay" value="true" />
            </OBJECT>    
         <div id="apDiv3" style="background-color:black">
           <iframe id='iframebar' src="about:blank" frameBorder="0" marginHeight="0" marginWidth="0" style=" z-index:-1;position:absolute;top:0px;left:0px;height:100%;width:100%;filter:alpha(opacity=0);"></iframe>
          <table width="100%" height="132" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td  class="up1png">&nbsp;</td>
              <td class="up2png">&nbsp;</td>
            </tr>
            <tr>
              <td class="down1png">&nbsp;</td>
              <td class="down2png">&nbsp;</td>
            </tr>
          </table>      
          <div id="apDiv4">
          <div class="cfangxiang">
            <table width="89" height="87" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="24" height="23"></td>
                <td width="41" height="23" class="zximg"><a href="javascript:ptzAction('up');"><img src="<%=basePath%>images/up.png" width="27" height="23" border="0" /></a></td>
                <td width="24" height="23"></td>
              </tr>
              <tr>
                <td width="24" height="40"><a href="javascript:ptzAction('left');"><img src="<%=basePath%>images/left.png" width="24" height="26" border="0" /></a></td>
                <td width="41" height="41"><a href="javascript:ptzAction('cruise');"><img src="<%=basePath%>images/zhongxin.png" width="41" height="41" border="0" /></a></td>
                <td width="24"><a href="javascript:ptzAction('right');"><img src="<%=basePath%>images/right.png" width="24" height="26" border="0" /></a></td>
              </tr>
              <tr>
                <td width="24"></td>
                <td width="41" height="23" class="zximg"><a href="javascript:ptzAction('down');"><img src="<%=basePath%>images/down.png" width="27" height="23" border="0" /></a></td>
                <td width="24"></td>
              </tr>
            </table>
            </div>
          </div> 
        </div>
    </div>
</div>

<div class="foot">
  <div class="f_left">
    <div class=blk_29>
      <div class=LeftBotton id=LeftArr><a href="javascript:void(0);" onmousedown="ISL_GoUp_1()" onmouseup="ISL_StopUp_1()" onmouseout="ISL_StopUp_1()"></a></div>
      <div class=Cont id=ISL_Cont_1>      
      <div class="ScrCont">      
      <div id="List1_1">
      	<c:forEach items="${ptzs}" var="ptz"> 
        <div class=box><a href="javascript:setChannel('${ptz.pelcodCommandUrl}','${ptz.infraredCircuitUrl}','${ptz.visibleRTSPUrl}','${ptz.infraredRTSPUrl}','${ptz.gisMapUrl}');">${ptz.name}</a></div>
        </c:forEach>
        <div class=box><a href="#" target=_blank>洪山坡小区1</a> </div>
        <div class=box><a href="#" target=_blank>洪山坡小区2</a> </div>
        <div class=box><a href="#" target=_blank>洪山坡小区3</a> </div>
        <div class=box><a href="#" target=_blank>洪山坡小区4</a> </div>
        <div class=box><a href="#" target=_blank>洪山坡小区5</a> </div>
        <div class=box><a href="#" target=_blank>洪山坡小区6</a> </div>
        <div class=box><a href="#" target=_blank>洪山坡小区7</a> </div>
        <div class=box><a href="#" target=_blank>洪山坡小区8</a> </div>
        <div class=box><a href="#" target=_blank>洪山坡小区9</a> </div>
        <div class=box><a href="#" target=_blank>洪山坡小区10</a> </div>
      </div>   
      <div id="List2_1"></div>
      </div>        
      </div>
      <div class=RightBotton id=RightArr><a href="javascript:void(0);" onmousedown="ISL_GoDown_1()" onmouseup="ISL_StopDown_1()" onmouseout="ISL_StopDown_1()"></a></div>
    </div>
  </div>
<div class="f_c"> 
<table width="520" height="69" border="0" cellpadding="0" cellspacing="1">
  <tr>
    <td width="96" height="24" class="yushua" ><a href="#">雨刷</a></td>
    <td width="96" height="24" class="sudu"><a href="#">速度</a></td>
    <td width="96" height="24" class="luoxuansaomiao"><a href="#">螺旋扫描</a></td>
    <td width="96"  class="piczhuapai"><a href="#">图像抓拍</a></td>
    <td width="96"  class="moshi"><a href="#">显示模式</a></td>
  </tr>
  <tr>
    <td  class="xunhang"><a href="#">巡航</a></td>
    <td height="24" class="apple"><a href="#">苹果皮</a></td>
    <td class="touwu"><a href="#">透雾</a></td>
    <td class="jianshi"><a href="#">轮循监视</a></td>
    <td class="duijiang"><a id="alarmSwitch" href="javascript:switchAlarm(document.getElementById('alarmSwitch'));">关闭报警</a></td>
  </tr>
</table></div>
</div>
</body>
</html>
<script type="text/javascript">


</script>