<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>HappyERP</title>
    <script src="<%=basePath%>javascripts/sound/soundmanager2.js" type="text/javascript"></script>

    <script type="text/javascript">
      
      // flash version URL switch (for this demo page)
      var winLoc = window.location.toString();
      console.info("fuck.............................................");
      console.info(winLoc);
      soundManager.preferFlash = (winLoc.match(/usehtml5audio=1/i) ? false : true);
      if (winLoc.match(/flash9/i)) {
        soundManager.flashVersion = 9;
        if (winLoc.match(/highperformance/i)) {
          soundManager.useHighPerformance = true;
          soundManager.useFastPolling = true;
        }
      } else if (winLoc.match(/flash8/i)) {
        soundManager.flashVersion = 8;
      }

      soundManager.useFlashBlock = true;
      soundManager.url = '<%=basePath%>javascripts/sound/'; // path to SoundManager2 SWF files (note trailing slash)
      soundManager.debugMode = true;
      soundManager.consoleOnly = false;

    </script>
  </head>
  <body>
    <button onclick="soundManager.play('mySound0','<%=basePath%>javascripts/sound/CHINA_1.mp3')">Do this</button>
    <button onclick="soundManager.play('mySound1','<%=basePath%>javascripts/sound/alarmsound.mp3')">Alarm</button>
    <script type="text/javascript">
      soundManager.onready(function(oStatus) {
        if (!oStatus.success) {
          return false;
        }
        // soundManager is initialised, ready to use. Create a sound for this demo page.
        soundManager.play('mySound3','<%=basePath%>javascripts/sound/alarmsound.mp3');
        console.info("Fuck.....You.....................................");
      });
    </script>
  </body>
</html>
