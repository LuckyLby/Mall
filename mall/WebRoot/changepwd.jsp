<%@ page contentType="text/html;charset=gb2312" %>
<%@ page import="java.util.*,cn.thxy.www.bean.AdminBean" %>
<% 
	if(session.getAttribute("admin")==null)
	{
		response.sendRedirect("adlogin.jsp");
	}
	else
	{
 %>
<html>
  <head>
    <title>修改密码</title>
    <script language="javascript" src="script/trim.js"></script>
    <script language="javascript">
      function pwdCheck()
      {
      	var oldpwd = document.pwdform.oldpwd.value.trim();
      	var firpwd = document.pwdform.firpwd.value.trim();
      	var secpwd = document.pwdform.secpwd.value.trim();
      	if(oldpwd==""||oldpwd.length<6)
      	{
      		alert("旧密码输入不合法,长度不得小于6!!!");
      		return;
      	}
      	if(firpwd==""||firpwd.length<6)
      	{
      		alert("新密码输入不合法,长度不得小于6!!!");
      		return;
      	}
      	if(secpwd==""||secpwd.length<6)
      	{
      		alert("第二次新密码输入不合法,长度不得小于6!!!");
      		return;
      	}
      	if(firpwd!=secpwd)
      	{
      		alert("新密码两次输入不相同!!!");
      		return;
      	}
      	document.pwdform.submit();
      }
    </script>
  </head>
  <body>
    <table width="100%" hight="100%" cellpadding="0" cellspacing="0">
      <tr>
        <td><%@ include file="admintop.jsp" %></td>
      </tr>
      <tr align="center">
        <td>
          <table>
            <form name="pwdform" action="AdminServlet" method="post">
              <tr>
              <% 
              	String name = (String)session.getAttribute("admin");
               %>
                <td><br/><br/><br/>用户名:</td>
                <td><br/><br/><br/><%= name %></td>
              </tr>
              <tr>
                <td><br/>请输入旧密码:</td>
                <td><br/><input type="password" name="oldpwd"/></td>
              </tr>
              <tr>
                <td><br/>请输入新密码:</td>
                <td><br/><input type="password" name="firpwd"/></td>
              </tr>
              <tr>
                <td><br/>请再次输入新密码:</td>
                <td><br/><input type="password" name="secpwd"/></td>
              </tr>
              <tr>
                <td align="right">
                  <br/><input type="button" value="提交" onclick="pwdCheck()"/>
                  <input type="hidden" name="action" value="changePwd"/>
                </td>
                <td align="left"><br/><input type="reset" value="重置"/></td>
              </tr>
            </form>
          </table>
        </td>
      </tr>
    </table>
  </body>
</html> 
 <% 
	}
%>