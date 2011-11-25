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
            
            //PTZList
                Ext.define('PTZ', {
                    extend : 'Ext.data.Model',
                    fields : [
                        {name: 'id'},
                        { name: 'name'},
                        { name: 'controllUrl'},
                        { name: 'pelcodCommandUrl'},
                        { name: 'visibleCameraUrl'},
                        { name: 'visibleRTSPUrl'},
                        { name: 'infraredRTSPUrl'},
                        { name: 'infraredCameraUrl'},
                        { name: 'infraredCircuitUrl'},
                        { name: 'northMigration'},
                        { name: 'gisMapUrl'},
                        { name: 'visualAngleX'},
                        { name: 'visualAngleY'},
                        { name: 'infraredPixelX'},
                        { name: 'infraredPixelY'},
                        { name: 'brandType'},
                        { name: 'cruiseStep'},
                        { name: 'cruiseRightLimit'},
                        { name: 'cruiseLeftLimit'},
                        { name: 'cruiseUpLimit'},
                        { name: 'cruiseDownLimit'},
                        { name: 'shiftStep'},
                        { name: 'version'},
                        { name: 'isLocked'},
                        { name: 'isAlarm'},
                        { name:"alarmHeatValue"}
                    ]
                });
                
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
                        }, {
                            header: '编码器IP',
                            dataIndex: 'controllUrl',                         
                            width:250
                        }, {
                            header: '通过串口,发pelcod的ip',
                            dataIndex: 'pelcodCommandUrl',
                            width:250
                        }, {
                            header: '可见光摄像机地址',
                            dataIndex: 'visibleCameraUrl',                          
                            width:250
                        }, {
                            header: '可见光RTSP流',
                            dataIndex: 'visibleRTSPUrl',
                            width:90
                             
                        }, {
                            header: '红外RTSP流',
                            dataIndex: 'infraredRTSPUrl',
                            width:90
                        }, {
                            header: '红外摄像机地址',
                            dataIndex: 'infraredCameraUrl',
                            width:90
                        }, {
                            header: '红外电路板设备地址',
                            dataIndex: 'infraredCircuitUrl',
                            width:250
                        },{
                            header: '摄像机0角度与正北的偏移',//。顺时针为正。
                            dataIndex: 'northMigration',
                            width:140
                        },{
                            header: '地图文件存放位置',
                            dataIndex: 'gisMapUrl',
                            width:250
                        },{
                            header: '红外视角X',
                            dataIndex: 'visualAngleX',
                            width:90
                        },{
                            header: '红外视角Y',
                            dataIndex: 'visualAngleY',
                            width:90
                        },{
                            header: '红外摄像机X方向像素',
                            dataIndex: 'infraredPixelX',
                            width:150
                        },{
                            header: '红外摄像机Y方向像素',
                            dataIndex: 'infraredPixelY',
                            width:150
                        },{
                            header: '品牌类型',
                            dataIndex: 'brandType',
                            width:80
                        },{
                            header: '巡航步长',
                            dataIndex: 'cruiseStep',
                            width:80
                        },{
                            header: '巡航右边界',
                            dataIndex: 'cruiseRightLimit',
                            width:80
                        },{
                            header: '巡航左边界',
                            dataIndex: 'cruiseLeftLimit',
                            width:80
                        },{
                            header: '最大上仰角度',
                            dataIndex: 'cruiseUpLimit',
                            width:80
                        },{
                            header: '巡航时最大俯角',
                            dataIndex: 'cruiseDownLimit',
                            width:100
                        },{
                            header: '是否正在报警',
                            dataIndex: 'isAlarm',
                            width:80
                        },{
                            header: '报警最高热值',
                            dataIndex: 'alarmHeatValue',
                            width:80
                        },{
                            header: '云台非巡航状态下默认移动步长',
                            dataIndex: 'shiftStep',
                            width:170
                        },{
                            header: '版本',
                            dataIndex: 'version',
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
                                    width:1200,
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
                                        width:1200,
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
                                }
                                editPTZWin.on("destroy",function(){
                                    PTZDS.load();
                                });
                                editPTZWin.resizable = false;
                                editPTZWin.show();
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
                    
                PTZGrid.on("itemcontextmenu",function(view,record,item,index,e,options){
                    e.preventDefault();
                    PTZMenu.showAt(e.getXY());
                    //privilegeRightMenu.show(node.ui.getAnchor());
                    currentNode =  record;
                    nodeId = record.get('id');
                    //window.status = currentNode.get('id');
                    //alert(typeof(currentNode.get('id'))==="string");
                    AlarmIgnoreAreasItem.setDisabled(false);
                    editPrivilegeModuleItem.setDisabled(false);
                    addPrivilegeMenuItem.setDisabled(false);
                    editPrivilegeMenuItem.setDisabled(false);          
                    delPrivilegeMenuItem.setDisabled(false);
                    if (record.getId() == 0){
                        editPrivilegeModuleItem.setDisabled(true);
                        addPrivilegeMenuItem.setDisabled(true);
                        editPrivilegeMenuItem.setDisabled(true);
                        delPrivilegeMenuItem.setDisabled(true);
                    } if (record.getId() == 1){
                        AlarmIgnoreAreasItem.setDisabled(true);
                        editPrivilegeMenuItem.setDisabled(true);
                    } if (record.getId() == 2){
                        AlarmIgnoreAreasItem.setDisabled(true);
                        editPrivilegeModuleItem.setDisabled(true);
                        addPrivilegeMenuItem.setDisabled(true);           
                    }
                },this);
                //右键菜单
                var AlarmIgnoreAreasItem = Ext.create('Ext.menu.Item', {
                    iconCls: 'addItem',
                    text: '报警忽视地区',
                    handler: rightMenuPTZFn
                });
                var editPrivilegeModuleItem = Ext.create('Ext.menu.Item', {
                    iconCls: 'editItem',
                    text: '编辑模块(权限)',
                    handler: rightMenuPTZFn
                });
                var addPrivilegeMenuItem = Ext.create('Ext.menu.Item', {
                    iconCls: 'addItem',
                    text: '添加菜单(权限)',
                    handler: rightMenuPTZFn
                });
                var editPrivilegeMenuItem = Ext.create('Ext.menu.Item', {
                    iconCls: 'editItem',
                    text: '编辑菜单(权限)',
                    handler: rightMenuPTZFn
                });
                var delPrivilegeMenuItem = Ext.create('Ext.menu.Item', {
                    iconCls: 'remove',
                    text: '删除权限',
                    handler: rightMenuPTZFn
                });
                var refreshMenuItem = Ext.create('Ext.menu.Item', {
                    iconCls: 'refresh',
                    text: '刷新',
                    handler: rightMenuPTZFn
                });
                //菜单排序
                var sortUpMenuItem = Ext.create('Ext.menu.Item', {
                    iconCls: 'arrow_up',
                    text: '上移',
                    handler: rightMenuPTZFn
                });
                var sortDownMenuItem = Ext.create('Ext.menu.Item', {
                    iconCls: 'arrow_down',
                    text: '下移',
                    handler: rightMenuPTZFn
                });
                var PTZMenu = Ext.create('Ext.menu.Menu', {
                    //id: 'privilege_right_menu',
                    items: [
                        AlarmIgnoreAreasItem,
                        editPrivilegeModuleItem,
                        '-',
                        addPrivilegeMenuItem,
                        editPrivilegeMenuItem,
                        '-',
                        delPrivilegeMenuItem,
                        '-',
                        sortUpMenuItem,
                        sortDownMenuItem,
                        '-',
                        refreshMenuItem            
                    ]});
                //添加模块（权限）
                //添加菜单（权限）
                function rightMenuPTZFn(item,e){
                    if (item.text=="报警忽视地区"){
                        newAlarmIgnoreAreasWin = Ext.create('Ext.window.Window', {
                            layout: 'fit',
                            width: 900,
                            height: 350,
                            closeAction: 'destroy',
                            plain: true,
                            modal: true,
                            constrain:true,
                            //modal: true,
                            title: '报警忽视地区',
                            autoLoad: {
                                url: "<%=basePath%>ignoreareas/ignoreAreasList.htm?parent_id=" + currentNode.get('id'),
                                scripts: true
                            }
                        });
                        newAlarmIgnoreAreasWin.on("destroy",function(){
                            //刷新整个树
                            PTZDS.load();
                        });
                        newAlarmIgnoreAreasWin.resizable = false;
                        newAlarmIgnoreAreasWin.show();
                    }else if (item.text=="编辑模块(权限)"){
                        editPrivilegeModuleWin = Ext.create('Ext.window.Window', {
                            layout: 'fit',
                            width: 350,
                            height: 190,
                            closeAction: 'destroy',
                            plain: true,
                            modal: true,
                            constrain:true,
                            //modal: true,
                            title: '编辑模块(权限)',
                            autoLoad: {
                                url: "<%=basePath%>privilege/editPrivilegeModule.htm?id=" + currentNode.get('id'),
                                scripts: true
                            }
                        });
                        editPrivilegeModuleWin.on("destroy",function(){
                            //刷新整个树
                            PTZDS.load();
                        });
                        editPrivilegeModuleWin.resizable = false;
                        editPrivilegeModuleWin.show();
                    }else if (item.text=="添加菜单(权限)"){
                        newPrivilegeMenuWin = Ext.create('Ext.window.Window', {
                            layout: 'fit',
                            width: 350,
                            height: 235,
                            closeAction: 'destroy',
                            plain: true,
                            modal: true,
                            constrain:true,
                            //modal: true,
                            title: '添加菜单(权限)',
                            autoLoad: {
                                url: "<%=basePath%>privilege/newPrivilegeMenu.htm?parent_id=" + currentNode.get('id'),
                                scripts: true
                            }
                        });
                        newPrivilegeMenuWin.on("destroy",function(){
                            //刷新整个树
                            PTZDS.load({
                                node: currentNode.parentNode
                            });
                        });
                        newPrivilegeMenuWin.resizable = false;
                        newPrivilegeMenuWin.show();
                    }else if (item.text=="编辑菜单(权限)"){
                        editPrivilegeMenuWin = Ext.create('Ext.window.Window', {
                            layout: 'fit',
                            width: 350,
                            height: 265,
                            closeAction: 'destroy',
                            plain: true,
                            modal: true,
                            constrain:true,
                            //modal: true,
                            title: '编辑权限',
                            autoLoad: {
                                url: "<%=basePath%>privilege/editPrivilegeMenu.htm?id=" + currentNode.get('id'),
                                scripts: true
                            }
                        });
                        editPrivilegeMenuWin.on("destroy",function(){
                            //刷新整个树
                            PTZDS.load({
                                node: currentNode.parentNode
                            });
                        });
                        editPrivilegeMenuWin.resizable = false;
                        editPrivilegeMenuWin.show();
                    }else if (item.text=="刷新"){
                        if (currentNode.isLeaf()){
                            PTZDS.load({
                                node: currentNode.parentNode
                            });
                        }else{
                            //判断是否导入过，就是是否被人点击过，因为这是Ajax的，一但点击，就会导入数据。
                            if (currentNode.isLoaded()){
                                //如果导入过，就要刷新后，再expand();
                                sysPrivilegeTree.loader.dataUrl = "<%=basePath%>privilege/getSysPrivilegeChildrenById.htm?node=" + currentNode.get('id'); //定义子节点的Loader
                                sysPrivilegeTree.loader.load(currentNode,expandCurrentNode);
                            }else{
                                //如果没有导入过，触发expand事件，就可以导入，相当于刷新。
                                currentNode.expand();
                            }
                            //tree.getRootNode().select();
                            window.state = currentNode.get('id');
                            //currentNode.load();
                        }
                    }else if(item.text == "删除权限"){
                        Ext.MessageBox.confirm("提示","你确认删除 \""+currentNode.get('text')+"\" 吗？",function(btn){
                            if(btn=="yes"){                
                                Ext.Ajax.request({
                                    url : '<%=basePath%>privilege/destroySysPrivilege.htm',
                                    method:'GET',
                                    success:function(response,opts){
                                        var backInfo = Ext.JSON.decode(response.responseText).info;
                                        if(backInfo === 'success') {
                                            PTZDS.load({
                                                node: currentNode.parentNode
                                            });
                                        } else {
                                            Ext.MessageBox.alert('提示信息', backInfo);
                                        }
                                    },
                                    params: {"id":currentNode.get('id')}
                                });
                            }else{
                                //alert("no");
                            }
                        });
                    }else if (item.text=="上移"){
                        Ext.Ajax.request({
                            url : '<%=basePath%>privilege/sortUp.htm',
                            success : function (result, request) {
                                PTZDS.load({
                                    node: currentNode.parentNode
                                });
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
                                id : currentNode.get('id')
                            }
                        });
                    }else if (item.text=="下移"){
                        Ext.Ajax.request({
                            url : '<%=basePath%>privilege/sortDown.htm',
                            success : function (result, request) {
                                PTZDS.load({
                                    node: currentNode.parentNode
                                });
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
                                id : currentNode.get('id')
                            }
                        });
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
