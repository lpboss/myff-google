<%-- 
    Document   : userList
    Created on : 2011-9-9, 11:55:39
    Author     : jerry
--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>火警信息</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript">
            var newFireAlarmWin;
            var editFireAlarmWin;
            var fireAlarmId;
            var fireAlarmDS;
            //处理员工锁定
            function lockFireAlarmFn(id){              
                Ext.Ajax.request({                     
                    url : '<%=basePath%>firealarm/fireAlarmLock.htm',
                    success : function (result, request) {
                        fireAlarmDS.load();
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
            
                //用户设置---------------------------------------------------------
                //生成有关用户的Grid
                fireAlarmDS =  Ext.create('Ext.data.Store', {
                    //autoDestroy : true,
                    model : 'FireAlarm',
                    proxy : {
                        type : 'ajax',
                        url : '<%=basePath%>firealarm/getAllFireAlarm.htm',
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
                        return "<a style=cursor:pointer onclick=lockFireAlarmFn(" + store.getAt(index).get('id')+")><font color=red>锁定</font></a>";
                    }else{
                        return "<a style=cursor:pointer onclick=lockFireAlarmFn(" + store.getAt(index).get('id')+")><font color=green>未锁定</font></a>";
                    }
                }
        
                var fireAlarmGrid =  Ext.create('Ext.grid.Panel', {
                 
               

                    stripeRows: true,
                    

                    store: fireAlarmDS,
                    columns : [Ext.create('Ext.grid.RowNumberer'),{
                            header: 'id',
                            dataIndex: 'id',
                            width: 30
                        }, {
                            header: '云台ID',
                            dataIndex: 'ptzId',
                            autoWidth: true
                        }, {
                            header: '火警时间',
                            sortable : true,
                            dataIndex: 'actionDate',
                            width:150
                        }, {
                            header: '最高热值',
                            dataIndex: 'heatMax',
                            width:70
                        }, {
                            header: '最低热值',
                            dataIndex: 'heatMin'
                        }, {
                            header: '平均热值',
                            dataIndex: 'heatAvg'
                        }, {
                            header: '水平',
                            dataIndex: 'ptzAngleX'
                        }, {
                            header: '垂直',
                            dataIndex: 'ptzAngleY'
                        }, {
                            header: '用户ID',
                            sortable : true,
                            dataIndex: 'userId',
                            width:100
                        }, {
                           
                            header: '处理日期',
                            sortable : true,

                            dataIndex: 'dealDate',
                            width:150
                        }, {
                            header: '详情',
                            dataIndex: 'description',
                            width:100
                        },{
                            header: '是否锁定',
                            dataIndex: 'isLocked',
                            renderer: renderFireAlarmIsLucked,
                            width: 110
                        }],
                    selModel :Ext.create('Ext.selection.CheckboxModel'),
                    width: screenWidth-190,
                    height: screenHeight-285,
                    iconCls: 'icon-grid',
                    //collapsible: true,
                    //animCollapse: false,
                    //frame: true,
                    //title: '用户列表',
                    bbar: Ext.create('Ext.PagingToolbar', {
                        pageSize: pageSize + 15,
                        store: fireAlarmDS,
                        displayInfo: true,
                        displayMsg: "显示第 {0} 条到 {1} 条记录，一共 {2} 条",
                        emptyMsg: "没有记录"
                    }),
                    tbar: [{
                            text: '添加',
                            iconCls: 'addItem',
                            handler : function(){
                                newFireAlarmWin = Ext.create('Ext.window.Window', {
                                    layout: 'fit',
                                    width:740,
                                    height:330,
                                    closeAction: 'destroy',
                                    plain: true,
                                    modal: true,
                                    constrain:true,
                                    //modal: true,
                                    title:     '添加火警信息',
                                    autoLoad: {
                                        url: "<%=basePath%>firealarm/newFireAlarm.htm",
                                        scripts: true
                                    }
                                });
                                newFireAlarmWin.on("destroy",function(){
                                    fireAlarmDS.load();
                                });
                                newFireAlarmWin.resizable = false;
                                newFireAlarmWin.show();
                            }
                        },'-',{
                            text: '编辑',
                            iconCls: 'editItem',
                            handler : function(){
                                var records = fireAlarmGrid.getSelectionModel().getSelection();
                                if(records.length==0){
                                    Ext.MessageBox.show({
                                        title: '提示信息',
                                        msg: "请先选中一条记录后，再编辑。",
                                        buttons: Ext.MessageBox.OK,
                                        icon: Ext.MessageBox.WARNING
                                    });
                                }else{
                                    //把表单添加到窗口中
                                    fireAlarmId = records[0].get('id');
                                    editFireAlarmWin = Ext.create('Ext.window.Window', {
                                        title: '编辑火警信息',
                                        layout:'fit',
                                        width:740,
                                        height:330,
                                        closeAction:'destroy',
                                        constrain:true,
                                        plain: true,
                                        modal: true,
                                        autoLoad: {
                                            url: "<%=basePath%>firealarm/editFireAlarm.htm?id="+fireAlarmId,
                                            scripts: true
                                        }
                                    });
                                    editFireAlarmWin.on("destroy",function(){
                                        fireAlarmDS.load();
                                    });
                                    editFireAlarmWin.resizable = false;
                                    editFireAlarmWin.show();
                                }
                             
                            }
                        },'-',{
                            text: '删除',
                            iconCls: 'remove',
                            handler : function(){
                                var records = fireAlarmGrid.getSelectionModel().getSelection();
                                var ids=[];
                                var fireAlarmID='';
                                for(var i=0;i<records.length;i++){
                                    var data=records[i].data;
                                    ids.push(data.id);
                                    fireAlarmID+=data.id+'<br>';
                                }
                              
                                
                                if(records.length==0){
                                    Ext.MessageBox.show({
                                        title: '提示信息',
                                        msg: "请先选中一条记录后，再编辑。",
                                        buttons: Ext.MessageBox.OK,
                                        icon: Ext.MessageBox.WARNING
                                    });
                                }else{
                                    //把表单添加到窗口中
                                    var ids='';
                                    var fireAlarmID='';
                                    for(var i=0;i<records.length;i++){
                                      
                                        var data=records[i].data;
                                        ids=ids+data.id+",";
                                      
                                        fireAlarmID+=data.id+'<br>';
                                    }
                                   
                                    Ext.MessageBox.confirm('警告','确定删除以下火警信息？<br><font color="red">' + fireAlarmID + '</font>',function (button){
                                        if(button=='yes'){
                                                                                                                               
                                            Ext.Ajax.request({
                                                url:"<%=basePath%>firealarm/deleteFireAlarm.htm?id="+ids,
                                                method:'post',
                                                success:function(response,opts){
                                                    var data = Ext.JSON.decode(response.responseText);
                                                    if(data.success&&data.info=='success') {
                                                        fireAlarmDS.load();
                                                        Ext.MessageBox.alert('提示信息', '已成功删除火警信息。');
                                                    } else {
                                                        Ext.MessageBox.alert('提示信息', data.info);
                                                    }
                                                }
                                             
                                            });

                                        }
                                    });
                
                
                                   
                                }
                               
                               
                            }
                        }]
                });
                var ptz =  Ext.create('Ext.data.Store', {
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
                    //pageSize : pageSize,
                    autoLoad : true
                });
           
                var ptzId = Ext.create('Ext.form.ComboBox', {
                    store: ptz,
                    fieldLabel: '云台ID',
                    allowBlank: false,
                    blankText: "云台ID必须选择",
                    valueField: 'id',
                    displayField: 'name',
                    name: 'ptzId',//如果不想提交displayField，则在这儿指定要提交的Key，value就是valueField．
                    id:"ptzId",
                    emptyText: '请选择...',          
                    loadingText: '搜索中...',
                    anchor: '95%',
                    readOnly:false,
                    minChars: 0,          
                    editable:false
                });
                var BeginTime = Ext.create('Ext.form.field.DateTime', {
                    fieldLabel: '火警开始时间',
                    format : 'Y-m-d H:i:s',
                    id:"beginTime",
                    name: 'beginTime',
                    anchor: '95%'
                });
             
                var EndTime = Ext.create('Ext.form.field.DateTime', {
                    fieldLabel: '火警结束时间',
                    format : 'Y-m-d H:i:s',
                    id:'endTime',
                    name: 'endTime',
                    anchor: '95%'
                });
                var searchButton = Ext.create((Ext.Button),{
                    text:'搜索',
                    fieldLabel:'点击搜索',
                    iconCls: 'searchItem',
                    handler: function(){
                        alert(Ext.getCmp('beginTime').getValue());
                        fireAlarmDS.load({
                            params:{
                             
                                start : 0,
                                limit : pageSize,
                                PTZId: Ext.getCmp('ptzId').getValue(),
                                BeginTime: Ext.getCmp('beginTime').getRawValue(),
                                EndTime: Ext.getCmp('endTime').getRawValue()
                            }
                        })
                    }
                });
                var resetButton = Ext.create((Ext.Button),{
                    text:'重置',
                    fieldLabel:'点击重置',
                    iconCls: 'refresh',

                    handler: function(){
                       
                        newFireAlarmForm.form.reset();
                    }
                });
                var newFireAlarmForm = Ext.create('Ext.form.Panel', {
                    fieldDefaults: {
                        labelWidth: 55,
                        labelAlign: 'right'
                    },
                    url:'<%=basePath%>firealarm/create.htm',
                    frame:true,
                    bodyStyle:'padding:5px 5px 0',
                    width: screenWidth-190,
                    items: [{      
                            layout: 'column',
                            xtype: 'container',
                            items: [{
                                    columnWidth: .2,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [BeginTime]
                                }, {
                                    columnWidth: .2,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [EndTime]
                                }, {
                                    columnWidth: .2,
                                    layout: 'column',
                                    xtype: 'container',
                                    items: [ptzId]
                                },{
                                    columnWidth: .1,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [searchButton]
                                },{
                                    columnWidth: .1,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [resetButton]
                                }]
                        },{
                            layout: 'column',
                            xtype: 'container',
                            items: []
                        }
                    ]
                })
                var workbenchPanel = Ext.create('Ext.panel.Panel', {
                    style: {
                        marginLeft: 'auto',
                        marginRight: 'auto'
                    },
                    frame:true,
                    autoScroll:true,
                    width: screenWidth-100,
                    //                    height: totalHeight,autoScroll:true,
                    frame:false,
                    layout: {
                        type: 'table',
                        columns:1
                    },
                    items:[newFireAlarmForm,fireAlarmGrid]        });

                workbenchPanel.render('fireAlarm_list');
            })
        </script>
    </head>
    <body>
        <div id="fireAlarm_list"></div>
    </body>
</html>
