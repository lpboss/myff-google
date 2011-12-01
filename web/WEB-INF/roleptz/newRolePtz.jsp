<%-- 
    Document   : newRolePtz
    Created on : 2011-11-28, 11:24:45
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
        <title>添加角色云台信息</title>
        <script type="text/javascript">
            Ext.onReady(function(){
                
                 var ptz =  Ext.create('Ext.data.Store', {
                    //autoDestroy : true,
                    model : 'PTZEdit',
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
                    model : 'Role',
                    proxy : {
                        type : 'ajax',
                        url : '<%=basePath%>admin/getAllRoles.htm',
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
                    name: 'ptz',//如果不想提交displayField，则在这儿指定要提交的Key，value就是valueField．
                    emptyText: '请选择...',          
                    loadingText: '搜索中...',
                    anchor: '95%',
                    readOnly:false,
                    minChars: 0,          
                    editable:false
                });
                
                var newRolePtzForm = Ext.create('Ext.form.Panel', {
                    fieldDefaults: {
                        labelWidth: 55,
                        labelAlign: 'right'
                    },
                    url:'<%=basePath%>roleptz/create.htm',
                    frame:true,
                    bodyStyle:'padding:5px 5px 0',
                    width: 300,
                    height: 150,
                    items: [{
                            layout: 'column',
                            xtype: 'container',
                            items: [{
                                    columnWidth: .9,
                                    layout: 'anchor',
                                    xtype: 'container',
                                    items: [userId,ptzId]
                                }]
                        }
                        ],
                    buttons: [{
                            text: '提交',
                            iconCls: 'icon-save',
                            handler: function(){
                                // check form value
                                if (newRolePtzForm.form.isValid()) {
                                    this.disable();
                                    newRolePtzForm.form.submit({
                                        method : 'POST',
                                        success: function(result, response){
                                            if (response.result.info == "success") {
                                                //添加成功后，隐藏窗口，并刷新Grid
                                                newRolePtzWin.destroy();
                                            }
                                            else {
                                                Ext.MessageBox.alert('消息', response.result.info);
                                            }
                                        },
                                        failure: function(result, response){
                                            Ext.MessageBox.alert('提示', result.responseText);
                                            newRolePtzWin.destroy();
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
                                newRolePtzWin.destroy();
                            }
                        }]
                });
                newRolePtzForm.render('new_Role_Ptz_Form');
                
            })
        </script>
    </head>
    <body>
            <div id="new_Role_Ptz_Form"></div>
       
    </body>
</html>
