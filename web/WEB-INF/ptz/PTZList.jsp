<%-- 
   Document   : PTZList
   Created on : 2011-11-9, 20:16:13
   Author     : Haoqingmeng
--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>云台信息设置</title>

        <script type="text/javascript">
            var editPTZWin;
            //处理PTZ是否锁定
            function lockPTZFn(id){              
                Ext.Ajax.request({                     
                    url : '<%=basePath%>ptz/ptzLock.htm',
                    success : function (result, request) {
                        PTZDS.load();
                    },
                    failure : function (result, request){
                        Ext.MessageBox.show({
                            title: '消息',
                            msg: "通讯失败，请从新操作",
                            buttons: Ext.MessageBox.OK,
                            icon: Ext.MessageBox.WARNING
                        });
                    },
                    method : 'POST',
                    params : {
                        id : id
                    }
                });
            }
            
            Ext.onReady(function(){       
              
                PTZDS =  Ext.create('Ext.data.Store', {
                    //autoDestroy : true,
                    model : 'PTZ',
                    proxy : {
                        type : 'ajax',
                        url : '<%=basePath%>ptz/getAllPTZs.htm',
                        reader : {
                            type : 'json',
                            root : 'root',// JSON数组对象名
                            totalProperty : 'totalProperty'// 数据集记录总数
                        }
                    },
                    pageSize : pageSize,
                    autoLoad : true
                });
                
                function renderPTZIsLucked(value, cellmeta, record, index, columnIndex, store){
                    if (record.get("isLocked")=="1"){
                        return "<a style=cursor:pointer onclick=lockPTZFn(" + store.getAt(index).get('id')+")><font color=red>启用</font></a>";
                    }else{
                        return "<a style=cursor:pointer onclick=lockPTZFn(" + store.getAt(index).get('id')+")><font color=blue>未启用</font></a>";
                    }
                }

                var PTZGrid =  Ext.create('Ext.grid.Panel',
                
                {
                    store: PTZDS,
                    width: screenWidth-190,
                    height: screenHeight-285, 
                    layout:'fit',  
                    
                    columns : [Ext.create('Ext.grid.RowNumberer'), {
                            header: '名字',
                            dataIndex: 'name',
                            width:90
                        },{
                            header: '云台品牌',
                            dataIndex: 'brandType',
                            width:80
                        }, {
                            header: '编码器IP',
                            dataIndex: 'controllUrl',                         
                            width:260
                        }, {
                            header: '通过串口,发pelcod的ip',
                            dataIndex: 'pelcodCommandUrl',
                            width:260
                        },{
                            header: '地图文件存放位置',
                            dataIndex: 'gisMapUrl',
                            width:260
                        },{
                            header: '巡航步长',
                            dataIndex: 'cruiseStep',
                            width:80
                        },{
                            header: '非巡航状态下移动步长',
                            dataIndex: 'shiftStep',
                            width:120
                        },{
                            header: '巡航右边界',
                            dataIndex: 'cruiseRightLimit',
                            width:80
                        },{
                            header: '巡航左边界',
                            dataIndex: 'cruiseLeftLimit',
                            width:80
                        },{
                            header: '巡航时最大仰角',
                            dataIndex: 'cruiseUpLimit',
                            width:90
                        },{
                            header: '巡航时最大俯角',
                            dataIndex: 'cruiseDownLimit',
                            width:90
                        },{
                            header: '巡航方向',
                            dataIndex: 'cruiseFromTo',  
                            width:70
                        },{
                            header: '巡航上扬角度步长',
                            dataIndex: 'cruiseAngleYStep',  
                            width:100
                        }, {
                            header: '红外RTSP流',
                            dataIndex: 'infraredRTSPUrl',
                            width:260
                        }, {
                            header: '红外摄像机地址',
                            dataIndex: 'infraredCameraUrl',
                            width:260
                        }, {
                            header: '红外电路板设备地址',
                            dataIndex: 'infraredCircuitUrl',
                            width:260
                        },{
                            header: '红外视角X',
                            dataIndex: 'visualAngleX',
                            width:70
                        },{
                            header: '红外视角Y',
                            dataIndex: 'visualAngleY',
                            width:70
                        },{
                            header: '红外摄像机X方向像素',
                            dataIndex: 'infraredPixelX',
                            width:120
                        },{
                            header: '红外摄像机Y方向像素',
                            dataIndex: 'infraredPixelY',
                            width:120
                        },{
                            header: '播放类型',
                            dataIndex: 'playType',                          
                            width:70
                        },{
                            header: '可见光预览用户',
                            dataIndex: 'visualUser',                          
                            width:120
                        },{
                            header: '可见光预览密码',
                            dataIndex: 'visualPassword',                          
                            width:120
                        }, {
                            header: '可见光摄像机地址',
                            dataIndex: 'visibleCameraUrl',                          
                            width:260
                        }, {
                            header: '可见光RTSP流',
                            dataIndex: 'visibleRTSPUrl',
                            width:260
                        },{
                            header: '云台0角度与正北的偏移',//。顺时针为正。
                            dataIndex: 'northMigration',
                            width:130
                        },{
                            header: '报警最高热值',
                            dataIndex: 'alarmHeatValue',
                            width:80
                        },{
                            header: '是否正在报警',
                            dataIndex: 'isAlarm',
                            width:80
                        },{
                            header: '状态',
                            dataIndex: 'isLocked',  
                            renderer: renderPTZIsLucked,
                            width:80
                        }],
       
                    selModel :Ext.create('Ext.selection.CheckboxModel'),                                 
                    iconCls: 'icon-grid',
                    bbar: Ext.create('Ext.PagingToolbar', {
                        pageSize: pageSize + 15,
                        store: PTZDS,
                        displayInfo: true,
                        displayMsg: "显示第 {0} 条到 {1} 条记录，一共 {2} 条",
                        emptyMsg: "没有记录"
                    }),
                      
                    tbar: [{
                            text: '添加',
                            iconCls: 'addItem',
                            handler : function(){
                                newPTZWin = Ext.create('Ext.window.Window', {
                                    layout: 'fit',
                                    width:1300,
                                    height:320,
                                    closeAction: 'destroy',
                                    plain: true,
                                    modal: true,
                                    constrain:true,
                                    //modal: true,
                                    title: '添加云台信息',
                                    autoLoad: {
                                        url: "<%=basePath%>ptz/newPTZ.htm",
                                        scripts: true
                                    }
                                });
                                newPTZWin.on("destroy",function(){
                                    PTZDS.load();
                                });
                                newPTZWin.resizable = false;
                                newPTZWin.show();
                            }
                        },'-',{
                            text: '编辑',
                            iconCls: 'editItem',
                            handler : function(){
                                var records = PTZGrid.getSelectionModel().getSelection();
                                if(records.length==0){
                                    Ext.MessageBox.show({
                                        title: '提示信息',
                                        msg: "请先选中一条记录后，再编辑。",
                                        buttons: Ext.MessageBox.OK,
                                        icon: Ext.MessageBox.WARNING
                                    });
                                }else{
                                    //把表单添加到窗口中
                                    ptzId = records[0].get('id');
                                    editPTZWin = Ext.create('Ext.window.Window', {
                                        title: '编辑云台',
                                        layout:'fit',
                                        width:1300,
                                        height:320,
                                        closeAction:'destroy',
                                        constrain:true,
                                        plain: true,
                                        modal: true,
                                        autoLoad: {
                                            url: "<%=basePath%>ptz/editPTZ.htm?id=" + ptzId,
                                            scripts: true
                                        }
                                    });
                                    editPTZWin.on("destroy",function(){
                                        PTZDS.load();
                                    });
                                    editPTZWin.resizable = false;
                                    editPTZWin.show();
                                }
                               
                            }
                        },{                    
                            text: '删除',
                            width: 50,
                            iconCls: 'remove',
                            handler:function(){
                                var records = PTZGrid.getSelectionModel().getSelection();
                                if(records.length==0){
                                    Ext.MessageBox.show({
                                        title: '提示信息',
                                        msg: "请先选中一条记录后，再删除。",
                                        buttons: Ext.MessageBox.OK,
                                        icon: Ext.MessageBox.WARNING
                                    });
                                }else{
                                    Ext.MessageBox.confirm('警告', '确定要删除该信息？',function(button){
                                        var ids = [];
                                        var name = '';
                                        for(var i = 0 ; i < records.length ; i++){
                                            var data = records[i].data
                                            ids.push(data.id);
                                            name += data.name + '<br />'
                                        }                                      
                                        console.info(ids)
                                        //    var keys = Ext.util.JSON.encode(ids)                                    
                                        if(button == 'yes'){
                                            Ext.Ajax.request({
                                                url:"<%=basePath%>ptz/deletePTZ.htm?key="+ids,
                                                method:'post',
                                                success:function(response,opts){
                                                    var data = Ext.JSON.decode(response.responseText);
                                                    if(data.success&&data.info=='success') {
                                                        PTZDS.load();
                                                        Ext.MessageBox.alert('提示信息', '已成功删除PTZ信息。');
                                                    } else {
                                                        Ext.MessageBox.alert('提示信息', data.info);
                                                    }
                                                },
                                                params:{
                                                    ids:ids
                                                }
                                            });
                                        }
                                    });
          
                                }
                            }       
                        }]
                });
                    
                //右键菜单跳到--报警忽视地区
                PTZGrid.on("itemcontextmenu",function(view,record,item,index,e,options){
                    e.preventDefault();
                    Menu.showAt(e.getXY());
                    currentNode =  record;
                    nodeId = record.get('id');
                     console.info(nodeId);
                    AlarmIgnoreAreasItem.setDisabled(false); 
                },this);
                //右键菜单
                var AlarmIgnoreAreasItem = Ext.create('Ext.menu.Item', {
                    iconCls: 'addItem',
                    text: '报警忽视地区',
                    handler: rightMenuPTZFn
                });
             
                var Menu = Ext.create('Ext.menu.Menu', {
                    //id: 'privilege_right_menu',
                    items: [
                        AlarmIgnoreAreasItem,      
                        '-'                
                    ]});
                function rightMenuPTZFn(item,e){
                    if (item.text=="报警忽视地区"){  
                        newAlarmIgnoreAreasWin = Ext.create('Ext.window.Window', {
                            layout: 'fit',
                            width: 1190,
                            height: 350,
                            closeAction: 'destroy',
                            plain: true,
                            modal: true,
                            constrain:true,
                            //modal: true,
                            title: '报警忽视地区',
                            autoLoad: {
                                url: "<%=basePath%>ignoreareas/ignoreAreasList.htm?parent_id=" + nodeId,
                                scripts: true
                            }
                        });
                        newAlarmIgnoreAreasWin.on("destroy",function(){
                            //刷新整个树
                            PTZDS.load();
                        });
                        newAlarmIgnoreAreasWin.resizable = false;
                        newAlarmIgnoreAreasWin.show();
                    }
                }
   
                PTZGrid.render('PTZ_list');
                
            })
        </script>
    </head>
    <body>
        <div id="PTZ_list"></div>
    </body>
</html>
