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
            var roleId = 0;
            var a =0;
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
          
                //  var roleId = 1;
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
                    loadMask: true,
                    tbar: [{
                            text: '添加',
                            iconCls: 'addItem',
                            handler : function(){
                                newRolePtzWin = Ext.create('Ext.window.Window', {
                                    layout: 'fit',
                                    width:300,
                                    height:180,
                                    closeAction: 'destroy',
                                    plain: true,
                                    modal: true,
                                    constrain:true,
                                    //modal: true,
                                    title: '添加角色云台信息',
                                    autoLoad: {
                                        url: "<%=basePath%>roleptzset/newRolePtz.htm",
                                        scripts: true
                                    }
                                });
                                newRolePtzWin.on("destroy",function(){
                                    roleDS.load();
                                });
                                newRolePtzWin.resizable = false;
                                newRolePtzWin.show();
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
                                    editRolePtzWin = Ext.create('Ext.window.Window', {
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
                                editRolePtzWin.on("destroy",function(){
                                    roleDS.load();
                                });
                                editRolePtzWin.resizable = false;
                                editRolePtzWin.show();
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

                roleGrid.on('itemdblclick', function(gridPanel, record,item,index,e,options){
                    roleName = roleDS.getAt(index).get('name'); 
                    if (a==0){
                        ptzGrid.setTitle("云台列表 (<font color=red>"+roleName+"</font>)"); 
                        ptzGrid.setVisible(true);
                        roleId = record.get('id');
                        PTZDS.proxy.extraParams = {'id':roleId};//把参数roleId传递到PTZDS中                     
                        PTZDS.load()
                        console.info(roleId);
                    }else{
                        PTZDS.removeAll();
                        ptzGrid.setVisible(false);
                    }
                                                                      
                    Ext.Ajax.request({
                        url : '<%=basePath%>roleptzset/getRolePtzs.htm?id='+roleId,
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
