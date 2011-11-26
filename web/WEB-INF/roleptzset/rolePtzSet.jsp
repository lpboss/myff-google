<%-- 
    Document   : rolePtzSet
    Created on : 2011-11-25, 15:17:42
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
        <title>角色云台设置</title>
        <script type="text/javascript">
            
            Ext.define('rolePTZEdit', {
                extend : 'Ext.data.Model',
                fields : [
                    { name: 'id'},
                    { name: 'name'},
                    { name: 'isDefault'},
                ]
            });

            //处理是否是默认云台
            function lockPtzDetailFn(id){
                Ext.Ajax.request({
                    url : '<%=basePath%>roleptzset/rolePtzSetLock.htm?id='+id,
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
                    method : 'GET',
                    params : {
                        id : id
                    }
                });
            }
      
            Ext.onReady(function(){
          
                //-----------------角色Grid----------------------------
                roleDS =  Ext.create('Ext.data.Store', {
                    model : 'Role',
                    proxy : {
                        type : 'ajax',
                        url : '<%=basePath%>roleptzset/getAllRoles.htm',
                        reader : {
                            type : 'json',
                            root : 'root',// JSON数组对象名
                            totalProperty : 'totalProperty'// 数据集记录总数
                        }
                    },
                    autoLoad : true
                });

                var roleGrid = Ext.create('Ext.grid.Panel', {
                    title:'角色列表',
                    store: roleDS,
                    columns : [Ext.create('Ext.grid.RowNumberer'),
                        {
                            header: '名称',
                            dataIndex: 'name',
                            width: 80
                        },{
                            header: '描述',
                            dataIndex: 'description',
                            width: 180
                        }],
                    selModel : Ext.create('Ext.selection.CheckboxModel'),
                    width: 320,
                    height: screenHeight-400,
                    frame: true,
                    loadMask: true        
                });

                roleGrid.on('itemdblclick', function(gridPanel, record,item,index,e,options){
                    roleId = roleDS.getAt(index).get('id');
                    console.info(roleId);
                    roleName = roleDS.getAt(index).get('name');
                    ptzGrid.setTitle("云台列表 (<font color=red>"+roleName+"</font>)");
                });
        
                //-----------------云台Grid----------------------------
                PTZDS =  Ext.create('Ext.data.Store', {
                    model : 'rolePTZEdit',
                    proxy : {
                        type : 'ajax',
                        url : '<%=basePath%>roleptzset/getAllPTZs.htm',
                        reader : {
                            type : 'json',
                            root : 'root',// JSON数组对象名
                            totalProperty : 'totalProperty'// 数据集记录总数
                        }
                    },
                    pageSize : pageSize,
                    autoLoad : true
                });
                
                //是否是默认云台
                function renderPtzDetailIsLucked(value, cellmeta, record, index, columnIndex, store){
                    if (record.get("isDefault")== "1"){
                        return "<a style=cursor:pointer onclick=lockPtzDetailFn(" + store.getAt(index).get('id')+")><font color=red>是</font></a>";
                    }else{
                        return "<a style=cursor:pointer onclick=lockPtzDetailFn(" + store.getAt(index).get('id')+")><font color=blue>否</font></a>";
                    }
                }
                
                var ptzGrid = Ext.create('Ext.grid.Panel', {
                    title:'云台列表',
                    store: PTZDS,
                    columns : [Ext.create('Ext.grid.RowNumberer'),
                        {
                            header: '名称',
                            dataIndex: 'name',
                            width: 170
                        },{
                            header: '是否是默认云台',
                            dataIndex: 'isDefault',
                            renderer: renderPtzDetailIsLucked,
                            width: 170
                        }],
                    
                    selModel : Ext.create('Ext.selection.CheckboxModel'),
                    width: 400,
                    height: screenHeight-300,
                    frame: true,
                    loadMask: true
                });
                

                var rolePtzSetPanel  = Ext.create('Ext.form.Panel', {
                    layout: 'column',
                    autoScroll:true,
                    width: screenWidth-160,
                    height: screenHeight-230,
                    items:[{
                            columnWidth:.343,
                            baseCls:'x-plain',
                            bodyStyle:'padding:5px 0 5px 5px',
                            items:[roleGrid]
                        },{
                            columnWidth:.343,
                            baseCls:'x-plain',
                            bodyStyle:'padding:5px 0 5px 5px',
                            items:[ptzGrid]
                        }]
                });
                rolePtzSetPanel.render("role_Ptz_Set_Panel");  
            })
        </script>
    </head>
    <body>
        <div id="role_Ptz_Set_Panel"></div>
    </body>
</html>
