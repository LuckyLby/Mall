<%@ page contentType="text/html;charset=gb2312" %>
<%@ page import="java.util.*,cn.thxy.www.dbcart.DBcart" %>
<center>
<table border="0" width="80%" bgcolor="#FFE3C8" height="90%">
  <tr align="center">
    <td><a href="adlogin.jsp">商品管理</a></td>
  </tr>
  <tr align="center">
    <td>商品分类</td>
  </tr>
  <% 
  	String sql = "select distinct Gclass from GoodsInfo";
    Vector<String> vclass = DBcart.getInfo(sql);
    for(String st:vclass)
    {
  %>
    <tr>
      <td align="center">
        <a href="CartServlet?action=search&cname=<%= st %>"><%= st %></a>
      </td>
    </tr>
  <%
    }
  %>
</table>
</center>
