<%@ page contentType="text/html;charset=gb2312" %>
<%@ page import="java.util.*,cn.thxy.www.bean.CartBean" %>
<link href="css/generalstyle.css" type="text/css" rel="stylesheet">
<table width="100%" height="100" bgcolor="#F5F9FE">
  <tr height="70" colspan="2">
    <td align="center"><img src="img/other/logo.gif" width="50" height="50"/>购物新天地</td>
  </tr>
  <tr height="15" colspan="2">
    <td><hr color="#FF7F00" width="100%"/></hr></td>
  </tr>
  <tr>
    <td>
      <table width="50%">
        <tr align="center">
          <td><a href="index.jsp">首页</a></td>
          <td><a href="adminindex.jsp">商品管理</a></td>
          <td><a href="ordermanage.jsp">订单管理</a></td>
          <td><a href="AdminServlet?action=adminManage">管理员管理</a></td>
          <td><a href="changepwd.jsp">修改密码</a></td>
          <td><a href="AdminServlet?action=logout">注销</a></td>
        </tr>
      </table>
    </td>
  </tr>
</table>