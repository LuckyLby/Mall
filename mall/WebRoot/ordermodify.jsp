<%@ page contentType="text/html;charset=gb2312" %>
<%@ page import="java.util.*,cn.thxy.www.bean.AdminBean" %>
<% 
	if(session.getAttribute("admin")==null)
	{
		response.sendRedirect("adlogin.jsp");
	}
	else
	{
		Vector<String[]> vordergoods = 
					(Vector<String[]>)request.getAttribute("vordergoods");
		Vector<String[]> vorderinfo = 
					(Vector<String[]>)request.getAttribute("vorderinfo");
		String[] str = vorderinfo.get(0);
%>
<html>
  <head>
    <title>订单详情</title>
  </head>
  <body>
    <table width="100%" cellpadding="0" cellspacing="0">
      <tr>
        <td><%@ include file="admintop.jsp" %></td>
      </tr>
      <tr align="center">
        <td>
          <table width="60%">
            <tr>
	          <th>货物名称</td>
	          <th>货物数量</td>
	          <th>货物总价</td>          
            </tr>
          <% 
          	  int i=0;
          	  for(String[] temp:vordergoods)
          	  {
          	  	  i++;
			   	  if(i%2==0)
				  {
			   		 out.println("<tr align='center'>");
				  }
				  else
				 {
					 out.println("<tr align='center' bgcolor='#F5F9FE'>");
				 }
           %>
              <td><%= temp[0] %></td>
              <td><%= temp[1] %></td>
              <td><%=  temp[2]%></td>
            </tr>
           <% 
           	   }
            %>
          </table>
        </td>
      </tr>
      <tr>
        <td><hr/></hr></td>
      </tr>
      <tr align="center">
        <td>
          <table>
            <tr>
              <td>收货人姓名:<%= str[0] %></td>
            </tr>
            <tr>
              <td>收货人地址:<%= str[1] %></td>
            </tr>
            <tr>
              <td>收货人电话:<%= str[2] %></td>
            </tr>
            <tr>
              <td>订购日期:<%= str[3] %></td>
            </tr>
            <tr>
              <td>订单发送状态:<%= str[4] %></td>
            </tr>
          </table>
        </td>
      </tr>
      <tr>
        <td><hr/></hr></td>
      </tr>
      <tr align="center">
        <td>
          <a href="AdminServlet?action=orderEnsure&oid=<%= str[5]%>">订单发送</a>
          <a href="AdminServlet?action=orderDelete&oid=<%= str[5]%>">订单删除</a>
          <a href="javascript:history.back()">返回</a>
        </td>
      </tr>
    </table>
  </body>
</html>
<% 
	}
%>