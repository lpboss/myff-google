<%-- 
    Document   : systemPrivilege
    Created on : 2011-9-13, 11:52:26
    Author     : jerry
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
  <head>
    <title>系统权限</title>
    <script type="text/javascript">
      var currentNode;
      var nodeId;
      var priviliegeId = 0;
      var newPrivilegeModuleWin;
      var newPrivilegeMenuWin;
      var editPrivilegeModuleWin;
      var editPrivilegeMenuWin;
      var newPrivilegeDetailWin;
      var sysPrivilegeDetailDS;
      var sysPrivilegeDetailGrid;
      var sysPrivilegeTree;

      //处理锁定
      function lockPrivilegeDetailFn(id){
        Ext.Ajax.request({
          url : '<%=basePath%>privilege/privilegeDetailLock.htm',
          success : function (result, request) {
            sysPrivilegeDetailDS.load();
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
        //----------------------------------------------------------------------
        //权限树
        var treeStore = Ext.create('Ext.data.TreeStore', {
          proxy: {
            type: 'ajax',
            url: '<%=basePath%>privilege/getSysPrivilegeChildrenById.htm'
          },
          root: {
            text: 'Sys Privileges',
            id: '0',
            expanded: true
          },
          folderSort: true,
          sorters: [{
              property: 'sort_id',
              direction: 'ASC'
            }]
        });
        
        //权限树
        sysPrivilegeTree = Ext.create('Ext.tree.Panel', {
          renderTo:'tree-div',
          title: '权限树',
          height: screenHeight-250,
          width: 220,
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
            //itemclick( Ext.view.View this, Ext.data.Model record, HTMLElement item, Number index, Ext.EventObject e, Object options )
            //'itemclick': function(node, checked){
            'itemclick': function(view,record,item,index,e,options){
              //如果是叶节点，就显示它的全部内容。
              if (record.get('leaf')){
                sysPrivilegeDetailGrid.setTitle("菜单‘" + record.get('text') + "’的详细权限列表");
                sysPrivilegeDetailGrid.setVisible(true);
                priviliegeId = record.get('id');
                /*sysPrivilegeDetailDS.load({params:{
                    'privilege_id':record.get('id')
                  }});*/
                sysPrivilegeDetailDS.proxy.extraParams = {'privilege_id':priviliegeId};
                sysPrivilegeDetailDS.load()
              }else{
                sysPrivilegeDetailDS.removeAll();
                sysPrivilegeDetailGrid.setVisible(false);
              }
            }
          },

          buttons: [{
              text: 'Get Completed Tasks',
              handler: function(){
                var msg = '', selNodes = sysPrivilegeTree.getChecked();
                Ext.each(selNodes, function(node){
                  if(msg.length > 0){
                    msg += ', ';
                  }
                  msg += node.text;
                });
                Ext.Msg.show({
                  title: 'Completed Tasks',
                  msg: msg.length > 0 ? msg : 'None',
                  icon: Ext.Msg.INFO,
                  minWidth: 200,
                  buttons: Ext.Msg.OK
                });
              }
            }]
        });
        
        //这是一次性展开的非常好的方法
        //sysPrivilegeTree.getRootNode().expand(true);
        sysPrivilegeTree.render();

        
        //权限树，右键菜单
        //( Ext.view.View this, Ext.data.Model record, HTMLElement item, Number index, Ext.EventObject e, Object options )
        sysPrivilegeTree.on("itemcontextmenu",function(view,record,item,index,e,options){
          e.preventDefault();
          privilegeRightMenu.showAt(e.getXY());
          //privilegeRightMenu.show(node.ui.getAnchor());
          currentNode =  record;
          nodeId = record.get('id');
          //window.status = currentNode.get('id');
          //alert(typeof(currentNode.get('id'))==="string");
          addPrivilegeModuleItem.setDisabled(false);
          editPrivilegeModuleItem.setDisabled(false);
          addPrivilegeMenuItem.setDisabled(false);
          editPrivilegeMenuItem.setDisabled(false);          
          delPrivilegeMenuItem.setDisabled(false);
          if (record.getDepth() == 0){
            editPrivilegeModuleItem.setDisabled(true);
            addPrivilegeMenuItem.setDisabled(true);
            editPrivilegeMenuItem.setDisabled(true);
            delPrivilegeMenuItem.setDisabled(true);
          } if (record.getDepth() == 1){
            addPrivilegeModuleItem.setDisabled(true);
            editPrivilegeMenuItem.setDisabled(true);
          } if (record.getDepth() == 2){
            addPrivilegeModuleItem.setDisabled(true);
            editPrivilegeModuleItem.setDisabled(true);
            addPrivilegeMenuItem.setDisabled(true);           
          }
        },this);

        //右键菜单
        var addPrivilegeModuleItem = Ext.create('Ext.menu.Item', {
          iconCls: 'addItem',
          text: '添加模块(权限)',
          handler: rightMenuPrivilegeTreeFn
        });
        var editPrivilegeModuleItem = Ext.create('Ext.menu.Item', {
          iconCls: 'editItem',
          text: '编辑模块(权限)',
          handler: rightMenuPrivilegeTreeFn
        });
        var addPrivilegeMenuItem = Ext.create('Ext.menu.Item', {
          iconCls: 'addItem',
          text: '添加菜单(权限)',
          handler: rightMenuPrivilegeTreeFn
        });
        var editPrivilegeMenuItem = Ext.create('Ext.menu.Item', {
          iconCls: 'editItem',
          text: '编辑菜单(权限)',
          handler: rightMenuPrivilegeTreeFn
        });
        var delPrivilegeMenuItem = Ext.create('Ext.menu.Item', {
          iconCls: 'remove',
          text: '删除权限',
          handler: rightMenuPrivilegeTreeFn
        });
        var refreshMenuItem = Ext.create('Ext.menu.Item', {
          iconCls: 'refresh',
          text: '刷新',
          handler: rightMenuPrivilegeTreeFn
        });
        //菜单排序
        var sortUpMenuItem = Ext.create('Ext.menu.Item', {
          iconCls: 'arrow_up',
          text: '上移',
          handler: rightMenuPrivilegeTreeFn
        });
        var sortDownMenuItem = Ext.create('Ext.menu.Item', {
          iconCls: 'arrow_down',
          text: '下移',
          handler: rightMenuPrivilegeTreeFn
        });
        
        var privilegeRightMenu = Ext.create('Ext.menu.Menu', {
          //id: 'privilege_right_menu',
          items: [
            addPrivilegeModuleItem,
            editPrivilegeModuleItem,
            '-',
            addPrivilegeMenuItem,
            editPrivilegeMenuItem,
            '-',
            delPrivilegeMenuItem,
            '-',
            sortUpMenuItem,
            sortDownMenuItem,
            '-',
            refreshMenuItem            
          ]});

        //添加模块（权限）
        //添加菜单（权限）
        function rightMenuPrivilegeTreeFn(item,e){
          if (item.text=="添加模块(权限)"){
            newPrivilegeModuleWin = Ext.create('Ext.window.Window', {
              layout: 'fit',
              width: 350,
              height: 190,
              closeAction: 'destroy',
              plain: true,
              modal: true,
              constrain:true,
              //modal: true,
              title: '添加模块(权限)',
              autoLoad: {
                url: "/privilege/newPrivilegeModule?parent_id=" + currentNode.get('id'),
                scripts: true
              }
            });
            newPrivilegeModuleWin.on("destroy",function(){
              //刷新整个树
              treeStore.load();
            });
            newPrivilegeModuleWin.resizable = false;
            newPrivilegeModuleWin.show();
          }else if (item.text=="编辑模块(权限)"){
            editPrivilegeModuleWin = Ext.create('Ext.window.Window', {
              layout: 'fit',
              width: 350,
              height: 190,
              closeAction: 'destroy',
              plain: true,
              modal: true,
              constrain:true,
              //modal: true,
              title: '编辑模块(权限)',
              autoLoad: {
                url: "/privilege/editPrivilegeModule?id=" + currentNode.get('id'),
                scripts: true
              }
            });
            editPrivilegeModuleWin.on("destroy",function(){
              //刷新整个树
              treeStore.load();
            });
            editPrivilegeModuleWin.resizable = false;
            editPrivilegeModuleWin.show();
          }else if (item.text=="添加菜单(权限)"){
            newPrivilegeMenuWin = Ext.create('Ext.window.Window', {
              layout: 'fit',
              width: 350,
              height: 235,
              closeAction: 'destroy',
              plain: true,
              modal: true,
              constrain:true,
              //modal: true,
              title: '添加菜单(权限)',
              autoLoad: {
                url: "<%=basePath%>privilege/newPrivilegeMenu.htm?parent_id=" + currentNode.get('id'),
                scripts: true
              }
            });
            newPrivilegeMenuWin.on("destroy",function(){
              //刷新整个树
              treeStore.load();
            });
            newPrivilegeMenuWin.resizable = false;
            newPrivilegeMenuWin.show();
          }else if (item.text=="编辑菜单(权限)"){
            editPrivilegeMenuWin = Ext.create('Ext.window.Window', {
              layout: 'fit',
              width: 350,
              height: 265,
              closeAction: 'destroy',
              plain: true,
              modal: true,
              constrain:true,
              //modal: true,
              title: '编辑权限',
              autoLoad: {
                url: "<%=basePath%>privilege/editPrivilegeMenu.htm?id=" + currentNode.get('id'),
                scripts: true
              }
            });
            editPrivilegeMenuWin.on("destroy",function(){
              //刷新整个树
              treeStore.load();
            });
            editPrivilegeMenuWin.resizable = false;
            editPrivilegeMenuWin.show();
          }else if (item.text=="刷新"){
            if (currentNode.isLeaf()){
              alert("是一个Child");
            }else{
              //判断是否导入过，就是是否被人点击过，因为这是Ajax的，一但点击，就会导入数据。
              if (currentNode.isLoaded()){
                //如果导入过，就要刷新后，再expand();
                sysPrivilegeTree.loader.dataUrl = "<%=basePath%>privilege/getSysPrivilegeChildrenById.htm?node=" + currentNode.get('id'); //定义子节点的Loader
                sysPrivilegeTree.loader.load(currentNode,expandCurrentNode);
              }else{
                //如果没有导入过，触发expand事件，就可以导入，相当于刷新。
                currentNode.expand();
              }
              //tree.getRootNode().select();
              window.state = currentNode.get('id');
              //currentNode.load();
            }
          }else if(item.text == "删除权限"){
            Ext.MessageBox.confirm("提示","你确认删除 \""+currentNode.get('text')+"\" 吗？",function(btn){
              if(btn=="yes"){                
                Ext.Ajax.request({
                  url : '/privilege/destroySysPrivilege',
                  method:'GET',
                  success:function(response,opts){
                    var backInfo = Ext.JSON.decode(response.responseText).info;
                    if(backInfo === 'success') {
                      Ext.MessageBox.alert('提示信息', backInfo);
                      treeStore.load();
                    } else {
                      Ext.MessageBox.alert('提示信息', backInfo);
                    }
                  },
                  params: {"id":currentNode.get('id')}
                });
              }else{
                //alert("no");
              }
            });
          }else if (item.text=="上移"){
            Ext.Ajax.request({
              url : '/privilege/sortUp',
              success : function (result, request) {
                treeStore.load({
                  node: currentNode.parentNode
                });
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
                id : currentNode.get('id')
              }
            });
          }else if (item.text=="下移"){
            Ext.Ajax.request({
              url : '/privilege/sortDown',
              success : function (result, request) {
                treeStore.load({
                  node: currentNode.parentNode
                });
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
                id : currentNode.get('id')
              }
            });
          }
        }

        function expandCurrentNode(){
          //alert(currentNode.get('id'));
          currentNode.expand();
          //currentNode.cascade();
        }

        sysPrivilegeTree.on('beforeload',function(node){
          //sysPrivilegeTree.loader.dataUrl='/privilege/getSysPrivilegeChildrenById?node='+node.id;
          //sysPrivilegeTree.getLoad.url='/privilege/getSysPrivilegeChildrenById?node='+node.id;//定义子节点的Loader

          /*treeStore.load({params:{
              'node':node.id
            }});*/

        });

        sysPrivilegeDetailDS =  Ext.create('Ext.data.Store', {
          //autoDestroy : true,
          model : 'SysPrivilegeDetail',
          proxy : {
            type : 'ajax',
            url : '<%=basePath%>privilege/getPrivilegeDetailsById.htm',
            reader : {
              type : 'json',
              root : 'root',// JSON数组对象名
              totalProperty : 'totalProperty'// 数据集记录总数
            }
          }
        });
        
        //锁定用户
        function renderIsLucked(value, cellmeta, record, index, columnIndex, store){
          if (record.get("isLocked")=="1"){
            return "<a style=cursor:pointer onclick=lockPrivilegeDetailFn(" + store.getAt(index).get('id')+")><font color=red>锁定</font></a>";
          }else{
            return "<a style=cursor:pointer onclick=lockPrivilegeDetailFn(" + store.getAt(index).get('id')+")><font color=green>未锁定</font></a>";
          }
        }

        
        //生成要订单的产品的Grid
        sysPrivilegeDetailGrid = Ext.create('Ext.grid.Panel', {
          title:'系统详细权限列表',
          store: sysPrivilegeDetailDS,
          columns  :[Ext.create('Ext.grid.RowNumberer'),{
              header: 'DB',
              dataIndex: 'id',
              width: 50
            },{
              header: '是否锁定',
              dataIndex: 'is_locked',
              renderer: renderIsLucked,
              width: 60
            },{
              header: '名称',
              dataIndex: 'name',
              width: 230
            },{
              header: '控制器',
              dataIndex: 'controller_name',
              width: 120
            },{
              header: '方法',
              dataIndex: 'action_name',
              width: 190
            },{
              header: '描述',
              dataIndex: 'description',
              width: 200
            },{
              header: '日期',
              dataIndex: 'created_at',
              renderer: Ext.util.Format.dateRenderer('Y-m-d H:i'),
              width: 110
            }],
          selModel :Ext.create('Ext.selection.CheckboxModel'),
          width: screenWidth-410,
          height: screenHeight-250,
          frame: true,
          loadMask: true,
          tbar: [{
              text: '添加',
              iconCls: 'addItem',
              handler : function(){
                if(priviliegeId == 0){
                  Ext.Msg.show({
                    title: '提示',
                    msg: "请先选择权限",
                    icon: Ext.Msg.INFO,
                    minWidth: 200,
                    buttons: Ext.Msg.OK
                  });
                }else{
                  newPrivilegeDetailWin = Ext.create('Ext.window.Window', {
                    layout: 'fit',
                    width: 350,
                    height: 295,
                    closeAction: 'destroy',
                    plain: true,
                    modal: true,
                    constrain:true,
                    //modal: true,
                    title: '添加权限细节',
                    autoLoad: {
                      url: "<%=basePath%>privilege/newPrivilegeDetail.htm?privilege_id="+priviliegeId,
                      scripts: true
                    }
                  });
                  newPrivilegeDetailWin.on("destroy",function(){
                    sysPrivilegeDetailDS.load();
                  });
                  newPrivilegeDetailWin.resizable = false;
                  newPrivilegeDetailWin.show();
                }
              }
            },{
              text: '编辑',
              iconCls: 'editItem',
              handler : function(){
                var records = sysPrivilegeDetailGrid.getSelectionModel().getSelection();
                if(records.length==0){
                  Ext.MessageBox.show({
                    title: '提示信息',
                    msg: "请先选中一条记录后，再编辑。",
                    buttons: Ext.MessageBox.OK,
                    icon: Ext.MessageBox.WARNING
                  });
                }else{
                  editPrivilegeDetailWin = Ext.create('Ext.window.Window', {
                    layout: 'fit',
                    width: 350,
                    height: 295,
                    closeAction: 'destroy',
                    plain: true,
                    modal: true,
                    constrain:true,
                    //modal: true,
                    title: '编辑权限细节',
                    autoLoad: {
                      url: "<%=basePath%>privilege/editPrivilegeDetail.htm?id=" + records[0].get('id'),
                      scripts: true
                    }
                  });
                  editPrivilegeDetailWin.on("destroy",function(){
                    sysPrivilegeDetailDS.load();
                  });
                  editPrivilegeDetailWin.resizable = false;
                  editPrivilegeDetailWin.show();
                }
              }
            },{
              text: '删除',
              iconCls: 'remove',
              handler : function(){                
                var records = sysPrivilegeDetailGrid.getSelectionModel().getSelection();
                if(records.length==0){
                  Ext.MessageBox.show({
                    title: '提示信息',
                    msg: "请先选中一条记录后，再编辑。",
                    buttons: Ext.MessageBox.OK,
                    icon: Ext.MessageBox.WARNING
                  });
                }else{
                  Ext.MessageBox.confirm('警告',"确定删除'"+records[0].get('name')+"'权限？",function (button){
                    if(button=='yes'){
                      Ext.Ajax.request({
                        url:'<%=basePath%>privilege/destroySysPrivilegeDetail.htm',
                        method:'GET',
                        success:function(response,opts){
                          var backInfo = Ext.JSON.decode(response.responseText).info;
                          if(backInfo === 'success') {
                            sysPrivilegeDetailDS.load();
                          } else {
                            Ext.MessageBox.alert('提示信息', backInfo);
                          }
                        },
                        params: {"id":records[0].get('id')}
                      });
                    }
                  });
                }
              }
            }]
        });
        sysPrivilegeDetailGrid.setVisible(false);
        //sysPrivilegeDetailDS.load();
        
        var sysPrivilegePanel = Ext.create('Ext.form.Panel', {
          layout: 'column',
          autoScroll:true,
          width: screenWidth-170,
          height: screenHeight-230,
          //renderTo: 'query_workbanch',
          //plain: true,
          //xtype: 'container',
          items:[{
              columnWidth:.18,
              baseCls:'x-plain',
              bodyStyle:'padding:5px 0 5px 5px',
              items:[sysPrivilegeTree]
            },{
              columnWidth:.82,
              baseCls:'x-plain',
              bodyStyle:'padding:5px 0 5px 5px',
              items:[sysPrivilegeDetailGrid]
            }]
          //items: [sysPrivilegeTree,sysPrivilegeDetailGrid]
        });
        sysPrivilegePanel.render("sys_privilege_workbanch");
      })
    </script>
  </head>
  <body>
    <div id="sys_privilege_workbanch"></div>
    <div id="tree-div"></div>
  </body>
</html>


