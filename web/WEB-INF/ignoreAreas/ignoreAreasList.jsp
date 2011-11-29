<%-- 
    Document   : alarmIgnoreAreasList
    Created on : 2011-11-21, 11:38:08
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
        <title>报警忽视地区</title>
        <script type="text/javascript">         
            
            //处理锁定状态，
            function lockIgnoreAreasFn(id){              
                Ext.Ajax.request({                     
                    url : '<%=basePath%>ignoreareas/ignoreareasLock.htm',
                    success : function (result, request) {
                        alarmIgnoreAreasDS.load();
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
                
                var userId = <%=request.getParameter("parent_id")%>;
                
                alarmIgnoreAreasDS =  Ext.create('Ext.data.Store', {
                    //autoDestroy : true,
                    model : 'alarmIgnoreAreasList',
                    proxy : {
                        type : 'ajax',                      
                        url: "<%=basePath%>ignoreareas/getAllIgnoreAreases.htm?id=" + userId,
                        reader : {
                            type : 'json',
                            root : 'root',// JSON数组对象名
                            totalProperty : 'totalProperty'// 数据集记录总数
                        }
                    },
                    pageSize : pageSize,
                    autoLoad : true
                });
                
                function renderFireAlarmIsLucked(value, cellmeta, record, index, columnIndex, store){
                    if (record.get("isLocked")=="1"){
                        return "<a style=cursor:pointer onclick=lockIgnoreAreasFn(" + store.getAt(index).get('id')+")><font color=red>启用</font></a>";
                    }else{
                        return "<a style=cursor:pointer onclick=lockIgnoreAreasFn(" + store.getAt(index).get('id')+")><font color=green>未启用</font></a>";
                    }
                }

                var alarmIgnoreAreasGrid =  Ext.create('Ext.grid.Panel',
                
                {
                    store: alarmIgnoreAreasDS,
                    width: screenWidth-190,
                    height: screenHeight-285, 
                    layout:'fit',                     
                    columns : [Ext.create('Ext.grid.RowNumberer'), {
                            header: '云台的编号',
                            dataIndex: 'ptzId',
                            width:80
                        }, {
                            header: '火警时云台的水平角度',
                            dataIndex: 'ptzAngelX',                         
                            width:130
                        }, {
                            header: '火警时云台的Y角度',
                            dataIndex: 'ptzAngelY',
                            width:110
                        }, {
                            header: '热成像起火面积值',
                            dataIndex: 'ccdArea',                          
                            width:100
                        }, {
                            header: '最大热值',
                            dataIndex: 'heatMax',
                            width:60                           
                        }, {
                            header: '火警时间范围(开始)',
                            dataIndex: 'beginDate',
                            width:120
                        }, {
                            header: '火警时间范围(结束)',
                            dataIndex: 'endDate',
                            width:120
                        }, {
                            header: '版本',
                            dataIndex: 'version',
                            width:60
                        }, {
                            header: '状态',
                            dataIndex: 'isLocked',  
                            renderer: renderFireAlarmIsLucked,
                            width:60
                        }],
       
                    selModel :Ext.create('Ext.selection.CheckboxModel'),                                 
                    iconCls: 'icon-grid',
                    bbar: Ext.create('Ext.PagingToolbar', {
                        pageSize: pageSize + 15,
                        store: alarmIgnoreAreasDS,
                        displayInfo: true,
                        displayMsg: "显示第 {0} 条到 {1} 条记录，一共 {2} 条",
                        emptyMsg: "没有记录"
                    }),
                      
                    tbar: [{
                            text: '添加',
                            iconCls: 'addItem',
                            handler : function(){
                                newAlarmIgnoreAreasWin = Ext.create('Ext.window.Window', {
                                    layout: 'fit',
                                    width:700,
                                    height:210,
                                    closeAction: 'destroy',
                                    plain: true,
                                    modal: true,
                                    constrain:true,
                                    //modal: true,
                                    title: '添加报警忽视地区信息',
                                    autoLoad: {
                                        url: "<%=basePath%>ignoreareas/newIgnoreAreas.htm?parent_id=" + userId,
                                        scripts: true
                                    }
                                });
                                newAlarmIgnoreAreasWin.on("destroy",function(){
                                    alarmIgnoreAreasDS.load();
                                });
                                newAlarmIgnoreAreasWin.resizable = false;
                                newAlarmIgnoreAreasWin.show();
                            }
                        },'-',{
                            text: '编辑',
                            iconCls: 'editItem',
                            handler : function(){
                                var records = alarmIgnoreAreasGrid.getSelectionModel().getSelection();
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
                                    editAlarmIgnoreAreasWin = Ext.create('Ext.window.Window', {
                                        title: '编辑报警忽视地区',
                                        layout:'fit',
                                        width:700,
                                        height:210,
                                        closeAction:'destroy',
                                        constrain:true,
                                        plain: true,
                                        modal: true,
                                        autoLoad: {
                                            url: "<%=basePath%>ignoreareas/editIgnoreAreas.htm?id=" + ptzId+'&iddd='+userId,
                                            scripts: true
                                        }
                                    });
                                }
                                editAlarmIgnoreAreasWin.on("destroy",function(){
                                    alarmIgnoreAreasDS.load();
                                });
                                editAlarmIgnoreAreasWin.resizable = false;
                                editAlarmIgnoreAreasWin.show();
                            }
                        },'-',{                    
                            text: '删除',
                            width: 50,
                            iconCls: 'remove',
                            handler:function(){
                                var records = alarmIgnoreAreasGrid.getSelectionModel().getSelection();
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
                                        console.info(ids) //前台显示logger信息
                                    
                                        if(button == 'yes'){
                                            Ext.Ajax.request({
                                                url:"<%=basePath%>ignoreareas/deleteIgnoreAreas.htm?key="+ids,
                                                method:'post',
                                                success:function(response,opts){
                                                    var data = Ext.JSON.decode(response.responseText);
                                                    if(data.success&&data.info=='success') {
                                                        alarmIgnoreAreasDS.load();
                                                        Ext.MessageBox.alert('提示信息', '已成功删除报警忽视地区信息。');
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
                      
                alarmIgnoreAreasGrid.render('alarm_Ignore_Areas_list');               
            })
        </script>
    </head>
    <body>
        <div id="alarm_Ignore_Areas_list"></div>
    </body>
</html>
