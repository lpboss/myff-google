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
            var newUserWin;
            var editUserWin;
            var userId;
            var fireAlarmDS;
            //处理员工锁定
          
      
            Ext.onReady(function(){
                Ext.define('FireAlarm', {
                    extend : 'Ext.data.Model',
                    fields : [{name: 'id'},
                        { name: 'PTZId'},
                        { name: 'actionDate'},
                        {name: 'ptzHAngle'},
                        {name: 'ptzVAngle'},
                        {name: 'heatMax'},
                        {name: 'heatMin'},
                        {name: 'heatAvg'}, 
                        {name: 'description'},
                        {name: 'userId'},
                        {name: 'dealDate'},
                        {name: 'updatedAt'},
                        {name: 'createdAt'},
                        {name: 'version'},
                        {name: 'isLocked'}
                           
                    ]
                });
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
            
              
        
                var userGrid =  Ext.create('Ext.grid.Panel', {
                    store: fireAlarmDS,
                    columns : [Ext.create('Ext.grid.RowNumberer'),{
                            header: 'id',
                            dataIndex: 'id',
                            width: 30
                        }, {
                            header: '云台ID',
                            dataIndex: 'PTZId',
            
                            width: 60
                        }, {
                            header: '火警时间',
                            dataIndex: 'actionDate',
                            width:45
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
                            header: '详情',
                            dataIndex: 'description',
                            width:100
                        }, {
                            header: '用户ID',
                            dataIndex: 'userId',
                            width:100
                        }, {
                            header: '发配时间',
                            dataIndex: 'dealDate',
           
                            width:100
                        },{
                            header: '修改日期',
                            dataIndex: 'updatedAt',
                            width: 110
                        },{
                            header: '创建日期',
                            dataIndex: 'createdAt',
                            width: 110
                        },{
                            header: '版本',
                            dataIndex: 'version',
                            width: 110
                        },{
                            header: '是否锁定',
                            dataIndex: 'isLocked',
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
                                newUserWin = Ext.create('Ext.window.Window', {
                                    layout: 'fit',
                                    width:617,
                                    height:330,
                                    closeAction: 'destroy',
                                    plain: true,
                                    modal: true,
                                    constrain:true,
                                    //modal: true,
                                    title:     '添加用户',
                                    autoLoad: {
                                        url: "<%=basePath%>firealarm/newFireAlarm.htm",
                                        scripts: true
                                    }
                                });
                                newUserWin.on("destroy",function(){
                                    fireAlarmDS.load();
                                });
                                newUserWin.resizable = false;
                                newUserWin.show();
                            }
                        },'-',{
                            text: '编辑',
                            iconCls: 'editItem',
                            handler : function(){
                                var records = userGrid.getSelectionModel().getSelection();
                                if(records.length==0){
                                    Ext.MessageBox.show({
                                        title: '提示信息',
                                        msg: "请先选中一条记录后，再编辑。",
                                        buttons: Ext.MessageBox.OK,
                                        icon: Ext.MessageBox.WARNING
                                    });
                                }else{
                                    //把表单添加到窗口中
                                    userId = records[0].get('id');
                                    editUserWin = Ext.create('Ext.window.Window', {
                                        title: '编辑用户',
                                        layout:'fit',
                                        width:617,
                                        height:335,
                                        closeAction:'destroy',
                                        constrain:true,
                                        plain: true,
                                        modal: true,
                                        autoLoad: {
                                            url: "<%=basePath%>user/editUser.htm?id=" + userId,
                                            scripts: true
                                        }
                                    });
                                }
                                editUserWin.on("destroy",function(){
                                    fireAlarmDS.load();
                                });
                                editUserWin.resizable = false;
                                editUserWin.show();
                            }
                        }]
                });
              
                userGrid.render('user_list');
            })
        </script>
    </head>
    <body>
        <div id="user_list"></div>
    </body>
</html>
