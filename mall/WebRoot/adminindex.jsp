<%@ page contentType="text/html;charset=gb2312" %>
<%@ page import="java.util.*,cn.thxy.www.bean.AdminBean,cn.thxy.www.dbcart.DBcart" %>
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
    <title>欢迎界面</title>
  </head>
  <body>
     <jsp:useBean id="adBean" class="cn.thxy.www.bean.AdminBean" scope="session"/>
    <table width="100%">
      <tr>
        <td><%@ include file="admintop.jsp" %></td>
      </tr>
      <tr>
        <td>
		 <table>
		  <tr>
		    <td>
		      <table>
				<tr>
				  <td><%@ include file="adminsearch.jsp" %></td>
				</tr>
				<tr align="center">
				  <td>
				    <a href="addgoods.jsp">商品添加</a>
				  </td>
				</tr>
				<tr align="center">
				  <td><br/>商品类别</td>
				</tr>
				<tr>
				  <td>
				  <% 
				  	String sql = "select distinct Gclass from GoodsInfo";
				    Vector<String> vclass = DBcart.getInfo(sql);
				    for(String st:vclass)
				    {
				  %>
				    <tr>
				      <td align="center">
				        <a href="AdminServlet?action=search&cname=<%= st %>"><%= st %></a>
				      </td>
				    </tr>
				  <%
				    }
				  %>
				  </td>
				</tr>
		      </table>
		    </td>
		    <td width="100%"><%@ include file="goodsmanage.jsp" %></td>
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