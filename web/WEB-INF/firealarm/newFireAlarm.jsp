<%-- 
    Document   : newUser
    Created on : 2011-9-9, 14:14:44
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
        <title>添加用户</title>
        <meta http-equiv="content-type" content="text/html; charset=utf-8">
        <script type="text/javascript">
            Ext.onReady(function(){
                var number = Ext.create('Ext.form.field.Number', {
                    fieldLabel: '编号',
                    allowBlank: false,
                    blankText: "编号不能为空",
                    name: 'id',
                    anchor: '95%'
                });
                

              

                var heatMax =Ext.create('Ext.form.field.Number', {
                    fieldLabel: '最高热值',
                    allowBlank: false,
                    blankText: "最高热值不能为空",
                    name: 'heatMax',
                    anchor: '95%'
                });
                var ptzHAngle = Ext.create('Ext.form.field.Number', {
                    fieldLabel: '水平角度',
                    allowBlank: false,
                    blankText: "水平角度不能为空",
                    name: 'ptzHAngle',
                    anchor: '95%'
                });
                var ptzVAngle = Ext.create('Ext.form.field.Number', {
                    fieldLabel: '垂直角度',
                    allowBlank: false,
                    blankText: "垂直角度不能为空",
                    name: 'ptzVAngle',
                    anchor: '95%'
                });

                var heatMin = Ext.create('Ext.form.field.Number', {
                    fieldLabel: '最低热值',
                    allowBlank: false,
                    blankText: "最低热值不能为空",
                    name: 'heatMin',
                    anchor: '95%'
                });
        
                var heatAvg = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '平均热值',
                    name: 'heatAvg',
                    anchor: '95%'
                });

               

                var actionDate = Ext.create('Ext.form.field.DateTime', {
                    fieldLabel: '火警时间',
                    format : 'Y-m-d H:i:s',
                    name: 'actionDate',
                    anchor: '95%'
                });
                var dealDate = Ext.create('Ext.form.field.DateTime', {
                    fieldLabel: '发配时间',
                    format : 'Y-m-d H:i:s',
                    name: 'dealDate',
                    anchor: '95%'
                });

             

                var description = Ext.create('Ext.form.TextArea', {
                    fieldLabel: '详情',
                    name: 'description',
                    height:110,
                    anchor: '95%'
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
                var user =  Ext.create('Ext.data.Store', {
                    //autoDestroy : true,
                    model : 'User',
                    proxy : {
                        type : 'ajax',
                        url : '<%=basePath%>user/getAllUsers.htm',
                        reader : {
                            type : 'json',
                            root : 'root',// JSON数组对象名
                            totalProperty : 'totalProperty'// 数据集记录总数
                        }
                    },
                    //pageSize : pageSize,
                    autoLoad : true
                });
        
                //供应商
                var userId = Ext.create('Ext.form.ComboBox', {
                    store: user,
                    fieldLabel: '用户ID',
                    allowBlank: false,
                    blankText: "用户ID必须选择",
                    valueField: 'id',
                    displayField: 'name',
                    name: 'userId',//如果不想提交displayField，则在这儿指定要提交的Key，value就是valueField．
                    emptyText: '请选择...',          
                    loadingText: '搜索中...',
                    anchor: '95%',
                    //pageSize: 10,          
                    readOnly:false,
                    minChars: 0,          
                    editable:false
                });
                var ptzId = Ext.create('Ext.form.ComboBox', {
                    store: ptz,
                    fieldLabel: '云台ID',
                    allowBlank: false,
                    blankText: "云台ID必须选择",
                    valueField: 'id',
                    displayField: 'name',
                    name: 'ptzId',//如果不想提交displayField，则在这儿指定要提交的Key，value就是valueField．
                    emptyText: '请选择...',          
                    loadingText: '搜索中...',
                    anchor: '95%',
                    readOnly:false,
                    minChars: 0,          
                    editable:false
                });
               
        
                var newUserForm = Ext.create('Ext.form.Panel', {
                    fieldDefaults: {
                        labelWidth: 55,
                        labelAlign: 'right'
                    },
                    url:'<%=basePath%>firealarm/create.htm',
                    frame:true,
                    bodyStyle:'padding:5px 5px 0',
                    width: 740,
                    height:300,
                    items: [{
                            layout: 'column',
                            xtype: 'container',
                            items: [{
                                    columnWidth: .3,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [number]
                                }]
                        },{
                            layout: 'column',
                            xtype: 'container',
                            items: [{
                                    columnWidth: .3,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [ptzId]
                                }, {
                                    columnWidth: .35,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [heatMin]
                                }, {
                                    columnWidth: .35,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [heatMax]
                                }]
                        },{
                            layout: 'column',
                            xtype: 'container',
                            items: [{
                                    columnWidth: .3,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [heatAvg]
                                },{
                                    columnWidth: .35,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [userId]
                                },{
                                    columnWidth: .35,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [dealDate]
                                }]
                        },{
                            layout: 'column',
                            xtype: 'container',
                            items: [{
                                    columnWidth: .3,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [actionDate]
                                }, {
                                    columnWidth: .35,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [ptzHAngle]
                                }, {
                                    columnWidth: .35,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [ptzVAngle]
                                }]}
                        ,{
                            layout: 'column',
                            xtype: 'container',
                            items: [{
                                    columnWidth: 1,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [description]
                                }]
                        }],
                    buttons: [{
                            text: '提交',
                            iconCls: 'icon-save',
                            handler: function(){
                                // check form value
                                if (newUserForm.form.isValid()) {
                                    this.disable();
                                    newUserForm.form.submit({
                                        method : 'POST',
                                        success: function(result, response){
                                            if (response.result.info == "success") {
                                                //添加成功后，隐藏窗口，并刷新Grid
                                                newUserWin.destroy();
                                            }
                                            else {
                                                Ext.MessageBox.alert('消息', response.result.info);
                                            }
                                        },
                                        failure: function(result, response){
                                            Ext.MessageBox.alert('提示', result.responseText);
                                            newUserWin.destroy();
                                        }
                                    });
                                }
                                else {
                                    Ext.MessageBox.alert('错误提示', '请按要求填写必输选项.');
                                }
                            }
                        },{
                            text: '取消',
                            iconCls: 'exit',
                            handler: function(){
                                newUserWin.destroy();
                            }
                        }]
                });
                newUserForm.render('new_user_form');
            })
        </script>
    </head>
    <body>
        <div id="new_user_form"></div>
    </body>
</html>

