<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
    <head>
        <title>Fire Proofing</title>
        <link href="<%=basePath%>stylesheets/images.css" media="screen" rel="stylesheet" type="text/css" />
        <link href="<%=basePath%>javascripts/ext4/resources/css/ext-all.css" media="screen" rel="stylesheet" type="text/css" />
        <script src="<%=basePath%>javascripts/ext4/ext-all.js" type="text/javascript"></script>
        <script src="<%=basePath%>javascripts/ext4/locale/ext-lang-zh_CN.js" type="text/javascript"></script>

        <script type="text/javascript">
            var pageSize = 10;
            Ext.require([
                'Ext.grid.*',
                'Ext.data.*',
                'Ext.util.*',
                'Ext.state.*',
                'Ext.form.*',
                'Ext.layout.*',
                //'Ext.tree.*',
                'Ext.tab.Panel',
                'Ext.selection.CellModel'
            ]);

            Ext.QuickTips.init();
            //Ext.Ajax.defaultPostHeader += ";charset=UTF-8";
            Ext.data.Connection.prototype.method = 'POST';
            //这是一个小补丁，阻止在IE下按Backspce引起乱跳转页面


            // Add the additional 'advanced' VTypes
            Ext.apply(Ext.form.field.VTypes, {
                daterange: function(val, field) {
                    var date = field.parseDate(val);

                    if (!date) {
                        return false;
                    }
                    if (field.startDateField && (!this.dateRangeMax || (date.getTime() != this.dateRangeMax.getTime()))) {
                        var start = field.up('form').down('#' + field.startDateField);
                        start.setMaxValue(date);
                        start.validate();
                        this.dateRangeMax = date;
                    }
                    else if (field.endDateField && (!this.dateRangeMin || (date.getTime() != this.dateRangeMin.getTime()))) {
                        var end = field.up('form').down('#' + field.endDateField);
                        end.setMinValue(date);
                        end.validate();
                        this.dateRangeMin = date;
                    }
                    /*
                     * Always return true since we're only using this vtype to set the
                     * min/max allowed values (these are tested for after the vtype test)
                     */
                    return true;
                },

                daterangeText: 'Start date must be less than end date',

                password: function(val, field) {
                    if (field.initialPassField) {
                        var pwd = field.up('form').down('#' + field.initialPassField);
                        return (val == pwd.getValue());
                    }
                    return true;
                },

                passwordText: 'Passwords do not match'
            });

            Ext.onReady(function() {

                var logoutButton = Ext.create('Ext.Button', {
                    text: '退出系统',
                    iconCls: 'exit',
                    handler: function(){
                        Ext.Ajax.request({
                            url : '<%=basePath%>index/logout.htm',
                            success : function (result, request) {
                                window.document.location.href = '<%=basePath%>index/login.htm';
                            },
                            failure : function (result, request){
                                Ext.MessageBox.show({
                                    title: '消息',
                                    msg: "通讯失败，请从新操作",
                                    buttons: Ext.MessageBox.OK,
                                    icon: Ext.MessageBox.WARNING
                                });
                            },
                            method : 'POST'
                        });
                    }
                })

                var topPanel = Ext.create('Ext.panel.Panel', {
                    height: 30,
                    bbar: ["<b style='font-size:14px;'>您好：<%=session.getAttribute("userName")%>", '->','-',logoutButton],
                    region:'north'
                });


                //Extjs 4.0

                var accordition = Ext.create('Ext.panel.Panel', {
                    region:'west',
                    margins:'5 0 5 5',
                    split:true,
                    width: 140,
                    collapseMode: 'mini',
                    layout:'accordion'
                });

                //EXTJS 4
                var workTabs = Ext.create('Ext.tab.Panel', {
                    region:'center',
                    //ADD: handler
                    onTitleDbClick:function(e,target,o){
                        accordition.toggleCollapse(true);
                    }
                });

                var reminderPanel = Ext.create('Ext.panel.Panel', {
                    title: '系统提醒',
                    id:'reminder_panel',
                    //html: '&lt;empty panel&gt;',
                    //cls:'cellpadding:10',
          
                    //bodyStyle: 'padding:15px;align:center',
                    listeners: {
                        //expand: showAgileDiagram
                    },
                    loader:{}
                });
                workTabs.add(reminderPanel).show();
        
        
                workTabs.openTab = function(node) {
                    // var id = node.attributes.url;
                    var id = node.attributes.url;
                    var url = node.attributes.url;
                    var tabTitle = node.text;
          
                    workTabs.getItem(0).setTitle(tabTitle);
                    workTabs.getItem(0).load({
                        url: url,
                        scripts: true
                    });
                    //workTabs.getItem(0).getUpdater().update(url);
                    workTabs.getItem(0).show();
                };

                var viewport = Ext.create('Ext.container.Viewport', {
                    layout: 'border',
                    items: [topPanel,accordition, workTabs]
                });

                //添加详细菜单
                function renderMenu(){
                    var menus = eval(<%= request.getAttribute("menus")%>);

                    if (!menus) { return; }
                    for (var i = 0; i < menus.length; i++) {
                        var itemModuleMenu = menus[i];
                        var title = "<div style='background:url(" + itemModuleMenu.image +
                            ") no-repeat;padding-left:20px;'>" +
                            itemModuleMenu.name +
                            "</div>";
                        //Extjs 4,模级菜单
                        var moduleNode = Ext.create('Ext.tree.Panel', {
                            title: title,
                            rootVisible: false,
                            lines: false,
                            autoScroll: true,
                            qtips: itemModuleMenu.qitps,
                            root: {
                                editable: false,
                                expanded: true,
                                text: itemModuleMenu.name,
                                draggable: false,
                                children: itemModuleMenu.children
                            },
                            listeners: {
                                itemclick:{
                                    //Ext.view.View this, Ext.data.Model record, HTMLElement item, Number index, Ext.EventObject e, Object options
                                    fn: function(view, record, item, index, e, options ){
                                        workTabs.getActiveTab().setTitle(record.data.text);
                                        var tab = workTabs.getActiveTab();
                                        var loader = workTabs.getActiveTab().getLoader();
                                        workTabs.getActiveTab().getLoader().load({
                                            url: "<%=basePath%>"+record.data.id,
                                            scripts: true
                                        });
                                        //workTabs.getItem(0).getUpdater().update(url);
                                        workTabs.getActiveTab().show();
                                    }
                                }
                            }
                        });
                        accordition.add(moduleNode);
                    }
                    accordition.doLayout();
                }
                renderMenu();


        
                //员工下拉框模型
                Ext.define('User', {
                    extend : 'Ext.data.Model',
                    fields : [{name: 'id'},
                        { name: 'number'},
                        { name: 'name'},
                        {name: 'loginId'},
                        {name: 'identityCard'},
                        {name: 'phone'},
                        {name: 'email'},
                        {name: 'address'}, 
                        {
                            name: 'role_name',
                            mapping:'role',
                            convert:function(value,record){
                                if(value == ""){
                                    return "";
                                }else{
                                    return value.name;
                                }
                            }
                        },
                        {name: 'roleId'},
                        {name: 'isLocked'},
                        {name: 'createdAt'},
                        {name: 'updatedAt'}
                    ]
                });

                userStore =  Ext.create('Ext.data.Store', {
                    //autoDestroy : true,
                    model : 'User',
                    proxy : {
                        type : 'ajax',
                        url : '<%=basePath%>user/getAllUsers.htm?for_cbb=true',
                        reader : {
                            type : 'json',
                            root : 'root',// JSON数组对象名
                            totalProperty : 'totalProperty'// 数据集记录总数
                        }
                    },
                    pageSize : pageSize
                });


                //员工下拉框模型
                Ext.define('Role', {
                    extend : 'Ext.data.Model',
                    fields : [{name: 'id'},
                        { name: 'number'},
                        { name: 'name'},
                        { name: 'description'},
                        { name: 'isLocked'},
                        {name: 'createdAt'},
                        {name: 'updatedAt'}
                    ]
                });                             

                Ext.define('SysAction', {
                    extend : 'Ext.data.Model',
                    fields : [{name: 'id'},
                        {name: 'name'}
                    ]
                });

                sysActionStore = Ext.create('Ext.data.Store', {
                    //autoDestroy : true,
                    model : 'SysAction',
                    proxy : {
                        type : 'ajax',
                        url : '<%=basePath%>admin/getAllSysActions.htm',
                        reader : {
                            type : 'json',
                            root : 'root',// JSON数组对象名
                            totalProperty : 'totalProperty'// 数据集记录总数
                        }
                    },
                    pageSize:10000
                });

                Ext.define('SysController', {
                    extend : 'Ext.data.Model',
                    fields : [{name: 'id'},
                        {name: 'name'}
                    ]
                });

                sysControllerStore = Ext.create('Ext.data.Store', {
                    //autoDestroy : true,
                    model : 'SysController',
                    proxy : {
                        type : 'ajax',
                        url : '<%=basePath%>admin/getAllSysControllers.htm',
                        reader : {
                            type : 'json',
                            root : 'root',// JSON数组对象名
                            totalProperty : 'totalProperty'// 数据集记录总数
                        }
                    },
                    pageSize:200
                });

                Ext.define('SysPrivilegeDetail', {
                    extend : 'Ext.data.Model',
                    fields : [{name: 'id',type:'int'},
                        {name: 'name'},
                        {name: 'sysControllerId',type:'int',mapping:'sysController',convert:function(value,record){
                                if(value == null){
                                    return "";
                                }else{
                                    return value.id;
                                }
                            }
                        },
                        {name: 'sysActionId',type:'int',mapping:'sysAction',convert:function(value,record){
                                if(value == null){
                                    return "";
                                }else{
                                    return value.id;
                                }
                            }
                        },
                        {name: 'params'},
                        {name: 'sub_type'},
                        {name: 'description'},
                        {name: 'createdAt'},
                        {name: 'isLocked'}
                    ]
                });


                Ext.define('Privilege', {
                    extend : 'Ext.data.Model',
                    fields : [{name: 'id'},
                        {name: 'name'},
                        {name: 'number'},
                        {name: 'sysControllerId',type:'int',mapping:'sysController',convert:function(value,record){
                                if(value == null){
                                    return "";
                                }else{
                                    return value.id;
                                }
                            }
                        },
                        {name: 'sysActionId',type:'int',mapping:'sysAction',convert:function(value,record){
                                if(value == ""||value == null){
                                    return "";
                                }else{
                                    return value.id;
                                }
                            }
                        },
                        {name: 'description'},
                        {name: 'parentId',type:'int'},
                        {name: 'moduleId',mapping: 'parentId'}
                    ]
                });

                moduleStore = Ext.create('Ext.data.Store', {
                    //autoDestroy : true,
                    model : 'Privilege',
                    proxy : {
                        type : 'ajax',
                        url : '<%=basePath%>privilege/getAllModules.htm',
                        reader : {
                            type : 'json',
                            root : 'root',// JSON数组对象名
                            totalProperty : 'totalProperty'// 数据集记录总数
                        }
                    },
                    pageSize:100
                });

                Ext.define('RolesPrivilegeDetail', {
                    extend : 'Ext.data.Model',
                    fields : [{name: 'id'},
                        {name: 'name',mapping:'privilegeDetail.name'},
                        {name: 'description',mapping:'privilegeDetail.description'},
                        {name: 'isLocked'}
                    ]
                });
        
        
        
        
            });      
        </script>
    </head>
    <body>
        <script src="<%=basePath%>javascripts/application.js" type="text/javascript"></script>
    </body>
</html>
