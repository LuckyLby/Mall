<%@ page contentType="text/html;charset=gb2312" %>
<%@ page import="java.util.*,cn.thxy.www.bean.AdminBean,cn.thxy.www.dbcart.DBcart" %>
<% 
	if(session.getAttribute("admin")==null||session.getAttribute("level")==null)
	{
		response.sendRedirect("adlogin.jsp");
	}
	else
	{
 %>
<html>
  <head>
    <title>密码重置</title>
    <script language="javascript" src="script/trim.js"></script>
    <script language="javascript">
      function check()
      {
      	var aname = document.addform.aname.value.trim();
      	var apwd = document.addform.apwd.value.trim();
      	var secpwd = document.addform.secpwd.value.trim();
      	if(aname=="")
      	{
      		alert("用户名不能为空!!!");
      		return;
      	}
      	if(apwd==""||apwd.length<6)
      	{
      		alert("密码不合法,长度不得小于6!!!");
      		return;
      	}
      	if(secpwd!=apwd)
      	{
      		alert("两次密码输入不一至!!!");
      		return;
      	}
      	document.addform.submit();
      }
    </script>
  </head>
  <body>
    <table width="100%" cellpadding="0" cellspacing="0">
      <tr>
        <td><%@ include file="admintop.jsp" %></td>
      </tr>
      <tr>
        <td>
          <table width="100%">
            <tr>
              <td width="20%" align="center">
                <table>
                  <tr>
                    <td><br/><br/><a href="adminadd.jsp">管理员添加</a></td>
                  </tr>
                  <tr>
                    <td><br/><a href="">密码重置</a></td>
                  </tr>
                  <tr>
                    <td><br/><a href="adminmanage.jsp">查看所有管理员</a></td>
                  </tr>
                </table>
              </td>
              <td align="center"><br/><br/><br/>
                <table width="50%">
                  <form action="AdminServlet" method="post" name="addform">
                    <tr>
                      <td>请输入管理员用户名:</td>
                      <td><input name="aname"/></td>
                    </tr>
                    <tr>
                      <td><br/>请输入新密码:</td>
                      <td><br/><input type="password" name="apwd"/></td>
                    </tr>
                    <tr>
                      <td><br/>请再次输入新密码:</td>
                      <td><br/><input type="password" name="secpwd"/></td>
                    </tr>
                    <tr>
                      <td align="right">
                        <input type="hidden" name="action" value="resetpwd"/>
                        <br/><input type="button" value="提交" onclick="check()"/>
                      </td>
                      <td><br/><input type="reset" value="重置"/></td>
                    </tr>
                  </form>
                </table>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </body>
</html>
<% 
 	}
  %>