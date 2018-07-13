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
    <title>订单管理</title>
  </head>
  <body>
    <jsp:useBean id="adBean" class="cn.thxy.www.bean.AdminBean" scope="session"/>
    <table width="100%" cellpadding="0" cellspacing="0">
      <tr>
        <td><%@ include file="admintop.jsp" %></td>
      </tr>
      <tr>
        <td>
          <table>
            <tr>
              <td>
                <table>
                  <tr align="center">
                    <td><a href="AdminServlet?action=orderSearch&type=all">查看所有定单</a></td>
                  </tr>
                  <tr align="center">
                    <td><a href="AdminServlet?action=orderSearch&type=yes"><br/>查看已发货定单</a></td>
                  </tr>
                  <tr align="center">
                    <td><a href="AdminServlet?action=orderSearch&type=no"><br/>查看未发货定单</a></td>
                  </tr>
                  <tr>
                    <td align="left"><br/>订单号搜索:</td>
                  </tr>
                  <tr>
                    <td><%@ include file="ordersearch.jsp" %></td>
                  </tr>
                </table>
              </td>
              <td align="center" width="100%"><%@ include file="orderlist.jsp" %></td>
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