<%-- 
    Document   : editRole
    Created on : 2011-9-20, 16:08:01
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
    <title>编辑角色</title>
  </head>
  <body>
    <script type="text/javascript">
      Ext.onReady(function(){
        var name = Ext.create('Ext.form.field.Text', {
          fieldLabel: '名称',
          allowBlank: false,
          name: 'name',
          anchor: '100%'
        });
        
        var desc = Ext.create('Ext.form.TextArea', {
          fieldLabel: '备注',
          allowBlank: false,
          height:50,
          name: 'description',
          anchor: '100%'
        });

        var roleId = Ext.create('Ext.form.field.Hidden', {
          name: 'id',
          value: '<%= request.getParameter("id")%>'
        });
        
        //提交按钮
        var editRoleButton = Ext.create('Ext.Button', {
          text: '提交',
          iconCls: 'icon-save',
          handler: function(){
            editRoleButton.setDisabled(true);
            if (editRoleFormPanel.form.isValid()) {
              editRoleFormPanel.form.submit({
                success: function(result, resp){
                  if (resp.result.info.indexOf("成功") >= 0) {
                    editRoleWin.destroy();
                  } else {
                    Ext.MessageBox.show({
                      title: '消息',
                      msg: resp.result.info,
                      buttons: Ext.MessageBox.OK,
                      icon: Ext.MessageBox.WARNING
                    });                    
                  }
                  editRoleButton.enable();
                },
                failure: function(result, request){
                  editRoleButton.enable();
                  Ext.MessageBox.show({
                    title: '消息',
                    msg: "通讯失败，请从新操作",
                    buttons: Ext.MessageBox.OK,
                    icon: Ext.MessageBox.WARNING
                  });
                }
              });
            }
            else {
              editRoleButton.enable();
              
            }
          }
        })
        
        var editRoleFormPanel = Ext.create('Ext.form.Panel', {
          fieldDefaults: {
            labelWidth: 165,
            labelAlign: 'right'
          },
          width: 400,
          frame : true,
          url: '<%=path%>admin/updateRole.htm',
          method: 'POST',
          reader: Ext.create('Ext.data.reader.Json',{
            model: 'Role',
            root: 'root'
          }),
          items: [name,desc,roleId],
          buttons: [editRoleButton,{
              text: '关闭',
              iconCls: 'exit',
              handler: function(){
                editRoleWin.destroy();
              }
            }]
        });

        editRoleFormPanel.form.load({
          url: '<%=path%>admin/getRoleById.htm?id=' + roleId.getValue(),
          method : 'GET',
          waitMsg: '正在载入数据...',
          success: function(form, action){
          },
          failure: function(form, action){
            Ext.MessageBox.alert('提示信息', '信息加载失败');
          }
        });
        editRoleFormPanel.render('edit_role_form');
      })
    </script>
    <div id="edit_role_form"></div>
  </body>
</html>
