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
    <title>用户设置</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script type="text/javascript">
      var newUserWin;
      var editUserWin;
      var userId;
      var userDS;
      //处理员工锁定
      function lockUserFn(id){
        Ext.Ajax.request({
          url : '<%=basePath%>user/userLock',
          success : function (result, request) {
            userDS.load();
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
        userDS =  Ext.create('Ext.data.Store', {
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
          pageSize : pageSize,
          autoLoad : true
        });

        //锁定用户
        function renderUserIsLucked(value, cellmeta, record, index, columnIndex, store){
          if (record.get("isLocked")=="1"){
            return "<a style=cursor:pointer onclick=lockUserFn(" + store.getAt(index).get('id')+")><font color=red>锁定</font></a>";
          }else{
            return "<a style=cursor:pointer onclick=lockUserFn(" + store.getAt(index).get('id')+")><font color=green>未锁定</font></a>";
          }
        }
        
        var userGrid =  Ext.create('Ext.grid.Panel', {
          store: userDS,
          columns : [Ext.create('Ext.grid.RowNumberer'),{
              header: 'id',
              dataIndex: 'id',
              width: 30
            }, {
              header: '是否锁定',
              dataIndex: 'isLocked',
              renderer: renderUserIsLucked,
              width: 60
            }, {
              header: '编号',
              dataIndex: 'number',
              width:45
            }, {
              header: '登录Id',
              dataIndex: 'loginId',
              width:70
            }, {
              header: '名称',
              dataIndex: 'name',
              width:60
            }, {
              header: '角色',
              dataIndex: 'role_name',
              width:70
            }, {
              header: '身份证',
              dataIndex: 'identityCard'
            }, {
              header: '电话',
              dataIndex: 'phone'
            }, {
              header: '地址',
              dataIndex: 'address',
              width:230
            }, {
              header: '电子邮箱',
              dataIndex: 'email',
              width:180
            }, {
              header: '描述',
              dataIndex: 'description',
              hidden:true,
              width:230
            },{
              header: '修改日期',
              dataIndex: 'updatedAt',
              //renderer: Ext.util.Format.dateRenderer('Y-m-d H:i'),
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
            store: userDS,
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
                  title: '添加用户',
                  autoLoad: {
                    url: "<%=basePath%>user/newUser.htm",
                    scripts: true
                  }
                });
                newUserWin.on("destroy",function(){
                  userDS.load();
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
                  userDS.load();
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
