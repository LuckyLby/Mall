<%@ page contentType="text/html;charset=gb2312"%>
<% 
	if(session.getAttribute("admin")!=null)
	{
		response.sendRedirect("adminindex.jsp");
	}
	else
	{
 %>
<html>
  <head>
    <title>管理员登录</title>
    <script language="javascript" src="script/trim.js"></script>
    <script language="javascript">
      function check()
      {
          var aname = document.adform.aname.value.trim();
          var apwd = document.adform.apwd.value.trim();
          if(aname=="")
          {
          	alert("用户名不能为空!!!");
          	return;
          }
          if(apwd=="")
          {
            alert("密码不能为空!!!");
            return;
          }
          document.adform.submit();
      }
    </script>
  </head>
  <body>
    <table width="100%">
      <tr>
        <td><%@ include file="top.jsp" %></td>
      </tr>
      <tr align="center">
        <td><h2>管理员请登录</h2></td>
      </tr>
      <form action="AdminServlet" method="post" name="adform">
      <tr align="center">
        <td><br/>用户名:<input name="aname"/></td>
      </tr>
      <tr align="center">
        <td><br/>密&nbsp;码:<input type="password" name="apwd"/></td>
      </tr>
      <tr align="center">
        <td>
          <input type="hidden" name="action" value="login"/>
          <br/><input type="button" value="登录" onclick="check()"/>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <input type="reset" name="重置"/>
        </td>
      </tr>
      </form>
    </table>
  </body>
</html>
<% 
	}
 %>