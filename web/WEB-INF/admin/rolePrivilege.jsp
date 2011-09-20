<%-- 
    Document   : rolePrivilege
    Created on : 2011-9-16, 17:53:13
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
    <title>角色设置</title>
    <script type="text/javascript">
      var currentNode;
      var nodeId;
      var priviliegeId;
      var newRoleWin;
      var roleDS;
      var newPrivilegeWin;
      var newPrivilegeDetailWin;
      var rolePrivilegeDetailDS;
      var sysPrivilegeTree;
      var roleId = 0;
      var roleName = "";

      //处理权限锁定
      function lockRolePrivilegeDetailFn(id){
        Ext.Ajax.request({
          url : '<%=basePath%>admin/rolePrivilegeDetailLock.htm',
          success : function (result, request) {
            rolePrivilegeDetailDS.load();
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
      
      //处理角色
      function lockRoleFn(id){
        Ext.Ajax.request({
          url : '<%=basePath%>admin/roleLock.htm',
          success : function (result, request) {
            roleDS.load();
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

        //锁定角色
        function renderRoleIsLucked(value, cellmeta, record, index, columnIndex, store){
          if (record.get("isLocked")=="1"){
            return "<a style=cursor:pointer onclick=lockRoleFn(" + store.getAt(index).get('id')+")><font color=red>锁定</font></a>";
          }else{
            return "<a style=cursor:pointer onclick=lockRoleFn(" + store.getAt(index).get('id')+")><font color=green>未锁定</font></a>";
          }
        }

        var roleGrid = Ext.create('Ext.grid.Panel', {
          title:'角色列表',
          store: roleDS,
          columns : [Ext.create('Ext.grid.RowNumberer'),
            {
              header: '是否锁定',
              dataIndex: 'isLocked',
              renderer: renderRoleIsLucked,
              width: 60
            },{
              header: '名称',
              dataIndex: 'name',
              width: 80
            },{
              header: '描述',
              dataIndex: 'description',
              width: 180
            }],
          selModel : Ext.create('Ext.selection.CheckboxModel'),
          width: 430,
          height: screenHeight-250,
          frame: true,
          loadMask: true,
          tbar: [{
              text: '添加角色',
              iconCls: 'addItem',
              handler : function(){
                newRoleWin = Ext.create('Ext.window.Window', {
                  layout: 'fit',
                  width: 417,
                  height: 210,
                  closeAction: 'destroy',
                  plain: true,
                  modal: true,
                  constrain:true,
                  //modal: true,
                  title: '添加角色',
                  autoLoad: {
                    url: "<%=basePath%>admin/newRole.htm",
                    scripts: true
                  }
                });
                newRoleWin.on("destroy",function(){
                  roleDS.load();
                });
                newRoleWin.resizable = false;
                newRoleWin.show();
              }
            },'-',{
              text: '编辑',
              iconCls: 'editItem',
              handler : function(){
                var records = roleGrid.getSelectionModel().getSelection();
                if(records.length==0){
                  Ext.MessageBox.show({
                    title: '提示信息',
                    msg: "请先选中一条记录后，再编辑。",
                    buttons: Ext.MessageBox.OK,
                    icon: Ext.MessageBox.WARNING
                  });
                }else{
                  //把表单添加到窗口中
                  editRoleWin = Ext.create('Ext.window.Window', {
                    title: '编辑角色',
                    layout:'fit',
                    width: 417,
                    height: 210,
                    closeAction:'destroy',
                    constrain:true,
                    plain: true,
                    modal: true,
                    autoLoad: {
                      url: "<%=basePath%>admin/editRole.htm?id=" + records[0].get('id'),
                      scripts: true
                    }
                  });
                }
                editRoleWin.on("destroy",function(){
                  roleDS.load();
                });
                editRoleWin.resizable = false;
                editRoleWin.show();
              }
            }]
        });

        roleGrid.on('itemdblclick', function(gridPanel, record,item,index,e,options){
          roleId = roleDS.getAt(index).get('id');
          roleName = roleDS.getAt(index).get('name');
          rolePrivilegeDetailDS.removeAll();
          rolePrivilegeDetailGrid.setTitle('详细权限列表');
          sysPrivilegeTree.setTitle("权限分类 (<font color=red>"+roleName+"</font>)");
        });
        //----------------------------------------------------------------------
        //权限树
        var treeStore = Ext.create('Ext.data.TreeStore', {
          proxy: {
            type: 'ajax',
            url: '<%=basePath%>admin/getRolePrivilegeById.htm'
          },
          root: {
            text: '权限分类',
            id: '0',
            expanded: true
          },
          folderSort: true,
          sorters: [{
              property: 'sort_id',
              direction: 'ASC'
            }]
        });
        
        sysPrivilegeTree = Ext.create('Ext.tree.Panel', {
          renderTo:'tree-div',
          title: '权限分类',
          //width: screenWidth-1100,
          width: 230,
          height: screenHeight-250,
          useArrows:true,
          autoScroll:true,
          animate:true,
          enableDD:true,
          containerScroll: true,
          rootVisible: true,
          frame: true,
          //root: {
          //  id:0,
          //  nodeType: 'async'
          //},
          store:treeStore,

          listeners: {
            'checkchange': function(node, checked){
              if(checked){
                node.getUI().addClass('complete');
              }else{
                node.getUI().removeClass('complete');
              }
            },
            'itemclick': function(view,record,item,index,e,options){
              //如果是叶节点，就显示它的全部内容。
              if (record.get('leaf')){
                rolePrivilegeDetailGrid.setTitle("菜单‘" + record.get('text') + "’的详细权限列表");
                rolePrivilegeDetailGrid.setVisible(true);
                priviliegeId = record.get('id');
                //如果不为零则更新权限列表。
                if (roleId != 0){
                  rolePrivilegeDetailDS.proxy.extraParams.role_id = roleId;
                  rolePrivilegeDetailDS.proxy.extraParams.privilege_id = record.get('id');
                  rolePrivilegeDetailDS.proxy.extraParams.parent_privilege_id = record.parentNode.get('id');
                  rolePrivilegeDetailDS.load();
                }
              }else{
                rolePrivilegeDetailDS.removeAll();
                rolePrivilegeDetailGrid.setVisible(false);
              }
            }
          }
        });

        //这是一次性展开的非常好的方法
        sysPrivilegeTree.render();

        sysPrivilegeTree.on('beforeload',
        function(node){
          //sysPrivilegeTree.loader.dataUrl='/privilege/getSysPrivilegeChildrenById?node='+node.id; //定义子节点的Loader
        });

        rolePrivilegeDetailDS =  Ext.create('Ext.data.Store', {
          //autoDestroy : true,
          model : 'RolesPrivilegeDetail',
          proxy : {
            type : 'ajax',
            url : '<%=basePath%>admin/getRolePrivilegeDetailsById.htm',
            reader : {
              type : 'json',
              root : 'root',// JSON数组对象名
              totalProperty : 'totalProperty'// 数据集记录总数
            }
          }
        });
        
        
        //锁定用户
        function renderPrivilegeDetailIsLucked(value, cellmeta, record, index, columnIndex, store){
          if (record.get("isLocked")=="1"){
            return "<a style=cursor:pointer onclick=lockRolePrivilegeDetailFn(" + store.getAt(index).get('id')+")><font color=red>未选择</font></a>";
          }else{
            return "<a style=cursor:pointer onclick=lockRolePrivilegeDetailFn(" + store.getAt(index).get('id')+")><font color=green>已经选择</font></a>";
          }
        }
        

        //生成要订单的产品的Grid
        var rolePrivilegeDetailGrid = Ext.create('Ext.grid.Panel', {
          title:'详细权限列表',
          store: rolePrivilegeDetailDS,
          columns : [Ext.create('Ext.grid.RowNumberer'),
            {
              header: '选择',
              dataIndex: 'isLocked',
              renderer: renderPrivilegeDetailIsLucked,
              width: 60
            },{
              header: '名称',
              dataIndex: 'name',
              width: 190
            },{
              header: '描述',
              dataIndex: 'description',
              width: 260
            }],
          selModel : Ext.create('Ext.selection.CheckboxModel'),
          width:570,
          height: screenHeight-250,
          frame: true,
          loadMask: true          
        });
        //rolePrivilegeDetailDS.load();

        var sysPrivilegePanel = Ext.create('Ext.form.Panel', {
          layout: 'column',
          autoScroll:true,
          width: screenWidth-160,
          height: screenHeight-230,
          //renderTo: 'query_workbanch',
          //plain: true,
          //xtype: 'container',
          items:[{
              columnWidth:.343,
              baseCls:'x-plain',
              bodyStyle:'padding:5px 0 5px 5px',
              items:[roleGrid]
            },{
              columnWidth:.19,
              baseCls:'x-plain',
              bodyStyle:'padding:5px 0 5px 5px',
              items:[sysPrivilegeTree]
            },{
              columnWidth:.46,
              baseCls:'x-plain',
              bodyStyle:'padding:5px 0 5px 5px',
              items:[rolePrivilegeDetailGrid]
            }]
          //items: [sysPrivilegeTree,rolePrivilegeDetailGrid]
        });
        sysPrivilegePanel.render("role_workbanch");
      })
    </script>
  </head>
  <body>
    <div id="role_workbanch"></div>
    <div id="tree-div"></div>
  </body>
</html>
