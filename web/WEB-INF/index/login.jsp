<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*,ff.model.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
    <head>
        <title>Fire Proofing</title>        
        <meta http-equiv="content-type" content="text/html; charset=utf-8">
        <link href="<%=basePath%>javascripts/ext4/resources/css/ext-all.css" media="screen" rel="stylesheet" type="text/css" />
        <script src="<%=basePath%>javascripts/ext4/ext-all.js" type="text/javascript"></script>
        <script src="<%=basePath%>javascripts/ext4/locale/ext-lang-zh_CN.js" type="text/javascript"></script>

        <script type="text/javascript">
            Ext.onReady(function(){
                Ext.QuickTips.init();
                var userName = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '登录名称',
                    name: 'user_name',
                    allowBlank: false,
                    blankText: "登录名不能为空",
                    anchor: '95%',
                    listeners: {
                        scope: this,
                        keypress: function(field, e) {
                            if (e.getKey() == 13) {
                                loginPwd.focus(true);
                            }
                        }
                    }
                })

                var companyId = Ext.create('Ext.form.field.Text', {
                    name: 'company_id',
                    allowBlank: false,
                    hidden:true,
                    value:1
                })

                var password = Ext.create('Ext.form.field.Text', {
                    fieldLabel: '登录密码',
                    name: 'pwd',
                    inputType: 'password',
                    allowBlank: false,
                    blankText: "登录密码不能为空",
                    anchor: '95%',
                    listeners: {
                        specialkey:function(field,e){
                            if (e.getKey()==Ext.EventObject.ENTER){
                                loginButton.handler();
                            }
                        }
                    }
                })

                var loginButton = Ext.create('Ext.Button', {
                    text: '    登  录    ',
                    handler: function(){
                        loginButton.setDisabled(true);
                        userName.setReadOnly(true);
                        password.setReadOnly(true);
                        if (loginForm.form.isValid()) {
                            loginForm.getForm().submit({
                                success: function(result, resp){
                                    if (resp.result.info.indexOf("success") >= 0) {
                                        window.document.location.href = '<%=basePath%>index/index.htm';
                                        //window.location.replace('/');
                                    } else {
                                        Ext.MessageBox.show({
                                            title: '消息',
                                            msg: resp.result.info,
                                            buttons: Ext.MessageBox.OK,
                                            icon: Ext.MessageBox.WARNING
                                        });
                                        loginButton.enable();
                                        userName.setReadOnly(false);
                                        password.setReadOnly(false);
                                    }
                                },
                                failure: function(result, request){
                                    loginButton.enable();
                                    Ext.MessageBox.show({
                                        title: '消息',
                                        msg: "登录过程中和服务器的通信失败!",
                                        buttons: Ext.MessageBox.OK,
                                        icon: Ext.MessageBox.WARNING
                                    });
                                }
                            });
                        }
                        else {
                            loginButton.enable();
                            Ext.MessageBox.show({
                                title: '消息',
                                msg: "请输入必输选项",
                                buttons: Ext.MessageBox.OK,
                                icon: Ext.MessageBox.WARNING
                            });
                        }
                    }
                })

                var loginForm = Ext.create('Ext.form.Panel', {
                    fieldDefaults: {
                        labelWidth: 65,
                        labelAlign: 'right'
                    },
                    url:'<%=basePath%>index/validateUser.htm',
                    frame:true,
                    method: 'GET',
                    width: 440,
                    layout: 'anchor',
                    buttonAlign: 'center',
                    style: {
                        border: '0px'
                    },
                    buttons: [loginButton],
                    items: [{
                            id: 'formImg',
                            bodyStyle: Ext.isIE ? 'padding:15px 0 5px 15px;' : 'padding:10px 15px;border:hidden',
                            xtype: 'panel',
                            frame:true,
                            style: {
                                border: '0px'
                            },
                            html: '<img border="0" src = "<%=basePath%>images/welcome.png" width="400" height="266"/>'
                        },{
                            id: 'formTable',
                            xtype: 'panel',
                            fieldDefaults: {
                                labelWidth: 70,
                                labelAlign: 'right'
                            },
                            layout: 'column',
                            style: {
                                border: '0px'
                            },
                            frame:true,
                            items: [{
                                    columnWidth:.5,
                                    layout:'anchor',
                                    xtype: 'container',
                                    items:[userName]                //这两个一人一半
                                }, {
                                    columnWidth:.5,
                                    layout:'anchor',
                                    xtype: 'container',
                                    items:[password]                //这两个一人一半
                                },companyId
                            ]}
                    ]
                });

                var loginWin = Ext.create('Ext.window.Window', {
                    closable: false,
                    draggable:  false,
                    height: 397,
                    width: 450,
                    style: {
                        border: '0px'
                    },
                    region: 'center',
                    resizable: false,                    
                    items: [loginForm],
                    title: '系统登录'
                });
                loginWin.show();
            }
        )
        </script>
    </head>
    <body>
    </body>
</html>