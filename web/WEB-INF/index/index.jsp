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
<script type="text/javascript" src="<%=basePath%>javascripts/sound/soundmanager2.js"></script>
<script type="text/javascript">

var ptz_id = 0;	//当前所监控的云台节点ID
var assignedStep=20;	//云台转动速度，默认步�
var alarmSound ;

soundManager.useFlashBlock = false;
soundManager.url = '<%=basePath%>javascripts/sound/'; // path to SoundManager2 SWF files (note trailing slash)
soundManager.debugMode = false;
soundManager.consoleOnly = false;
soundManager.onready(function(oStatus) {
	if (!oStatus.success) {
		return false;
	}
	
	alarmSound = soundManager.createSound({
		id:'alarm_sound',
		url:'<%=basePath%>javascripts/sound/alarmsound.mp3'
	});
	
});
Ext.onReady(function() {
	getPTZAlarmsInfo = function(){
		Ext.Ajax.request({
			url : '<%=basePath%>ptz/getIsAlarmPTZs.htm',
			success : function (result, request) {
				var alarmJSON = result.responseText;
				alarmJSON =  Ext.JSON.decode(alarmJSON);
				if (alarmJSON.totalProperty > 0){
					//alert(alarmJSON.totalProperty);
					document.getElementById("map").setAlertMessage("当前状态："+alarmJSON.totalProperty+"处火警！");
					alarmSound.play({
						onfinish: function() {
							loopSound(alarmSound);
						}
					});
				}else{
					document.getElementById("map").setAlertMessage("当前状态：0处报警，0处火�);
					alarmSound.stop(alarmSound);
				}
			},
			failure : function (result, request){
			},
			method : 'GET'
		});
	}
	
	//定时查询云台报警状�
	var task = {
		run: function(){
			getPTZAlarmsInfo();
		},
		interval: 10000
	}
	Ext.TaskManager.start(task);

});

//控制云台动作
function ptzAction(ptzActionStr){		
	Ext.Ajax.request({
		url : '<%=basePath%>ptz/ptzAction.htm',
		success : function (result, request) {
			//turningDirection = ptzActionStr;
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
			ptz_id: ptz_id,
			action_type : ptzActionStr,
			assignedStep: assignedStep
		}
	});
}

//调整云台转动速度，根据pelco-d协议，速度值范围为0-63�55为turbo速度，这里设最低值为10
function ptzSpeed(speed){
	if(assignedStep==255 && speed<0){
		assignedStep=63;
	}else{
		assignedStep=assignedStep+speed;
		if(assignedStep>63){
			assignedStep=255;
		}else if(assignedStep<10){
			assignedStep=10;
		}
	}
}

//显示当前系统时间
function showTime(){
	var myDate = new Date();
	var Today = ["星期�,"星期一","星期�,"星期�,"星期�,"星期�,"星期�];
	//获取当前�
	var Years = myDate.getFullYear();
	//获取当前�
	var Months = myDate.getMonth() + 1;
	//获取当前�
	var Dates = myDate.getDate();
	//获取当前天是当前周的第几�
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

//根据窗口大小自动等比例缩放控件大�
function resizeWindow(){
	var clientWidth=document.body.clientWidth;
	if(clientWidth == window.screen.width){//有些时候在某些分辨率下，clientWidth会与屏幕分辨率相同，很奇�
		clientWidth=clientWidth-18;//减掉滚动条的宽度
	}
	
	document.getElementById("map").style.width=parseInt(clientWidth*0.256)+"px";
	document.getElementById("map").style.height=parseInt(clientWidth*0.256*0.818)+"px";
	
	document.getElementById("infraredPlayer").style.width=parseInt(clientWidth*0.256)+"px";
	document.getElementById("infraredPlayer").style.height=parseInt(clientWidth*0.256*0.818)+"px";
	document.getElementById("visiblePlayer").style.width=parseInt(clientWidth*0.744)+"px";
	document.getElementById("visiblePlayer").style.height=parseInt(clientWidth*0.744*0.563)+"px";
}

//切换监控通道
function setChannel(id,ptzControlIP,ptzAlertIP,visibleRTSPUrl,infraredRTSPUrl,gisMapUrl){
	ptz_id=id;
	document.getElementById("map").setChannel(ptzControlIP,ptzAlertIP,"../images/"+gisMapUrl);
	
	document.getElementById('infraredPlayer').URL=infraredRTSPUrl;
	document.getElementById('visiblePlayer').URL=visibleRTSPUrl;
	
	/*
	document.getElementById("infraredPlayer").playlist.add(infraredRTSPUrl,infraredRTSPUrl, " :rtsp-caching=500");
	document.getElementById("infraredPlayer").playlist.play();
	
	document.getElementById("visiblePlayer").playlist.add(visibleRTSPUrl,visibleRTSPUrl, " :rtsp-caching=500");
	document.getElementById("visiblePlayer").playlist.play();
	*/
}

//启动客户端报警，本方法由flex调用
var alarming=true;
function startAlarm(){	
	if(!document.getElementById("alarmPlayer").Played && alarming){
		document.getElementById("alarmPlayer").play();
    }
}

//关闭客户端报�
function switchAlarm(obj){
	if(alarming){
		alarming=false;
		document.getElementById("alarmPlayer").stop();
		obj.innerText="开启报�;
    }else{
		alarming=true;
		obj.innerText="关闭报警";
	}
}

//打开关闭雨刷
function switchWiper(obj){
	if(obj.innerText=="开启雨�){
		ptzAction("wiper_on");
		obj.innerText="关闭雨刷";
    }else{
		ptzAction("wiper_off");
		obj.innerText="开启雨�;
	}
}
/*begin云台节点左右翻屏*/
var Speed_1 = 25; //速度(毫秒)
var Space_1 = 20; //每次移动(px)
var PageWidth_1 = 582; //翻页宽度
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
          <li><a href="<%=basePath%>index/index2.htm" title="用户管理" rel="gb_page[1200, 700]">用户管理</a></li>
          <li><a href="<%=basePath%>index/index2.htm" title="系统设置" rel="gb_page[1200, 700]">系统设置</a></li>
          <li><a href="<%=basePath%>ptz/PTZList.htm" title="什么事" rel="gb_page[1000, 600]">什么事</a></li>
        </ul>
      </div>
        <div class="denglu">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="11%" align="right"><img src="<%=basePath%>images/star.png" width="18" height="19"  /></td>
              <td width="62%" height="37">当前用户：Admin</td>
              <td width="8%" align="right"><img src="<%=basePath%>images/x.png" width="18" height="19" /></td>
              <td width="19%">退�/td>
            </tr>
          </table>
        </div>
    </div>
    <div class="center">
      <div class="content_left">
         <div class="cleft1">
            <object classid="clsid:6BF52A52-394A-11d3-B153-00C04F79FAA6" id="infraredPlayer">
                <param name="URL" value=""><!--海康编码器或IPC的视频流地址-->
            	<param name="autoStart" value="true"><!--自动播放-->
            	<param name="uiMode" value="none"><!--精简模式-->
            	<param name="enableContextMenu" value="false"><!--不显示右键菜�->
           </object>
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
            <object classid="clsid:6BF52A52-394A-11d3-B153-00C04F79FAA6" id="visiblePlayer">
                <param name="URL" value=""><!--海康编码器或IPC的视频流地址-->
            	<param name="autoStart" value="true"><!--自动播放-->
            	<param name="uiMode" value="none"><!--精简模式-->
            	<param name="enableContextMenu" value="false"><!--不显示右键菜�->
           </object>         
    </div>
</div>

<div class="foot">
  <div class="f_left">
    <div class="blk_29">
      <div class="LeftBotton" id="LeftArr" title="向前翻页"><a href="javascript:void(0);" onmousedown="ISL_GoUp_1()" onmouseup="ISL_StopUp_1()" onmouseout="ISL_StopUp_1()"></a></div>
      <div class="Cont" id="ISL_Cont_1">      
      <div class="ScrCont">      
      <div id="List1_1">
      	<c:forEach items="${ptzs}" var="ptz"> 
        <div class=box><a href="javascript:setChannel(${ptz.id},'${ptz.pelcodCommandUrl}','${ptz.infraredCircuitUrl}','${ptz.visibleRTSPUrl}','${ptz.infraredRTSPUrl}','${ptz.gisMapUrl}');">${ptz.name}</a></div>
        </c:forEach>
        <div class=box><a href="#" target=_blank>洪山坡小�</a> </div>
        <div class=box><a href="#" target=_blank>洪山坡小�</a> </div>
        <div class=box><a href="#" target=_blank>洪山坡小�</a> </div>
        <div class=box><a href="#" target=_blank>洪山坡小�</a> </div>
        <div class=box><a href="#" target=_blank>洪山坡小�</a> </div>
        <div class=box><a href="#" target=_blank>洪山坡小�</a> </div>
        <div class=box><a href="#" target=_blank>洪山坡小�</a> </div>
        <div class=box><a href="#" target=_blank>洪山坡小�</a> </div>
        <div class=box><a href="#" target=_blank>洪山坡小�</a> </div>
        <div class=box><a href="#" target=_blank>洪山坡小�0</a> </div>
      </div>   
      <div id="List2_1"></div>
      </div>        
      </div>
      <div class="RightBotton" id="RightArr" title="向后翻页"><a href="javascript:void(0);" onmousedown="ISL_GoDown_1()" onmouseup="ISL_StopDown_1()" onmouseout="ISL_StopDown_1()"></a></div>
    </div>
  </div>
<div class="f_sifangxiang">
	<table width="69" height="68" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="18" ></td>
        <td  class="zximg" ><a href="javascript:void(0);" title="云台向上" onmousedown="javascript:ptzAction('up');" onmouseup="javascript:ptzAction('stop');"><img src="<%=basePath%>images/up.png" width="19" height="18" border="0" /></a></td>
        <td></td>
      </tr>
      <tr>
        <td height="28" align="right" ><a href="javascript:void(0);" title="云台左转" onmousedown="javascript:ptzAction('left');" onmouseup="javascript:ptzAction('stop');"><img src="<%=basePath%>images/left.png" width="19" height="18" border="0" /></a></td>
        <td width="27" height="28"><a href="javascript:ptzAction('cruise');" title="断点巡航"><img src="<%=basePath%>images/zhongxin.png" width="28" height="28" border="0" /></a></td>
        <td><a href="javascript:void(0);" title="云台右转" onmousedown="javascript:ptzAction('right');" onmouseup="javascript:ptzAction('stop');"><img src="<%=basePath%>images/right.png" width="19" height="18" border="0" /></a></td>
      </tr>
      <tr>
        <td></td>
        <td height="23" class="zximg"><a href="javascript:void(0);" title="云台向下" onmousedown="javascript:ptzAction('down');" onmouseup="javascript:ptzAction('stop');"><img src="<%=basePath%>images/down.png" width="19" height="18" border="0" /></a></td>
        <td></td>
      </tr>
    </table>
</div>
<div class="f_sianniu">
  <table width="100%" height="62" border="0" cellpadding="0" cellspacing="2">
    <tr>
      <td width="12%" ><a href="javascript:void(0);" title="镜头拉近"><img src="<%=basePath%>images/jujiao1.gif" width="24" height="23" border="0" onmouseover="this.src='<%=basePath%>images/jujiao_1.gif'" onmouseout="this.src='<%=basePath%>images/jujiao1.gif'" onmousedown="javascript:ptzAction('visible_in');" onmouseup="javascript:ptzAction('visible_in_stop');" /></a></td>
      <td width="26%" align="center" nowrap="nowrap">可见光变�/td>
      <td width="12%"><a href="javascript:void(0);" title="镜头拉远"><img src="<%=basePath%>images/jujiao2.gif" width="24" height="23" border="0" onmouseover="this.src='<%=basePath%>images/jujiao_2.gif'" onmouseout="this.src='<%=basePath%>images/jujiao2.gif'" onmousedown="javascript:ptzAction('visible_out');" onmouseup="javascript:ptzAction('visible_out_stop');" /></a></td>
      <td width="13%"><a href="javascript:void(0);" title="光圈变小"><img src="<%=basePath%>images/guangquan1.gif" width="24" height="23" border="0" onmouseover="this.src='<%=basePath%>images/guangquan_1.gif'" onmouseout="this.src='<%=basePath%>images/guangquan1.gif'" onmousedown="javascript:ptzAction('aperture_in');" onmouseup="javascript:ptzAction('stop');" /></a></td>
      <td width="24%" align="center" nowrap="nowrap">光圈调节</td>
      <td width="13%"><a href="javascript:void(0);" title="光圈变大"><img src="<%=basePath%>images/guangquan2.gif" width="24" height="23" border="0" onmouseover="this.src='<%=basePath%>images/guangquan_2.gif'" onmouseout="this.src='<%=basePath%>images/guangquan2.gif'" onmousedown="javascript:ptzAction('aperture_out');" onmouseup="javascript:ptzAction('stop');" /></a></td>
    </tr>
    <tr>
      <td><a href="javascript:void(0);" title="向后聚焦"><img src="<%=basePath%>images/bianjiao1.gif" width="24" height="23" border="0" onmouseover="this.src='<%=basePath%>images/bianjiao_1.gif'" onmouseout="this.src='<%=basePath%>images/bianjiao1.gif'" onmousedown="javascript:ptzAction('infrared_in');" onmouseup="javascript:ptzAction('stop');" /></a></td>
      <td align="center" nowrap="nowrap">热成像聚�/td>
      <td><a href="javascript:void(0);" title="向前聚焦"><img src="<%=basePath%>images/bianjiao2.gif" width="24" height="23" border="0" onmouseover="this.src='<%=basePath%>images/bianjiao_2.gif'" onmouseout="this.src='<%=basePath%>images/bianjiao2.gif'" onmousedown="javascript:ptzAction('infrared_out');" onmouseup="javascript:ptzAction('stop');" /></a></td>
      <td><a href="javascript:ptzSpeed(-10);" title="速度减小"><img src="<%=basePath%>images/sudu1.gif" width="24" height="23" border="0" onmouseover="this.src='<%=basePath%>images/sudu_1.gif'" onmouseout="this.src='<%=basePath%>images/sudu1.gif'" /></a></td>
      <td align="center" nowrap="nowrap">云台速度</td>
      <td><a href="javascript:ptzSpeed(10);" title="速度增大"><img src="<%=basePath%>images/sudu2.gif" width="24" height="23" border="0" onmouseover="this.src='<%=basePath%>images/sudu_2.gif'" onmouseout="this.src='<%=basePath%>images/sudu2.gif'" /></a></td>
    </tr>
  </table>
</div>
<div class="f_c"> 
<table width="100%" height="69" border="0" cellpadding="0" cellspacing="1">
  <tr>
    <td width="96" height="24"><span class="apple"><a href="javascript:ptzAction('cruise');">削苹果皮</a></span></td>
    <td width="96" ><span class="yushua"><a id="wiperSwitch" href="javascript:switchWiper(document.getElementById('wiperSwitch'));">开启雨�/a></span></td>
  </tr>
  <tr>
    <td><span class="luoxuansaomiao"><a href="javascript:ptzAction('cruise');">螺旋扫描</a></span></td>
    <td><span class="duijiang"><a id="alarmSwitch" href="javascript:ptzAction('stop_fire_alarm');">关闭报警</a></span></td>
  </tr>
</table></div>
</div>
</body>
</html>
<script type="text/javascript">
</script>
