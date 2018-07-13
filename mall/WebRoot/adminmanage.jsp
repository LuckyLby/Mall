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
    <title>管理员管理</title>
  </head>
  <body>
    <table width="100%" cellspacing="0" cellpadding="0">
      <tr>
        <td><%@ include file="admintop.jsp" %></td>
      </tr>
      <tr>
        <td align="center"><br/><br/><br/>
          <table width="100%">
            <tr>
              <td width="20%" align="center">
                <table>
                  <tr>
                    <td><a href="adminadd.jsp">管理员添加</a></td>
                  </tr>
                  <tr>
                    <td><br/><a href="resetpwd.jsp">密码重置</a></td>
                  </tr>
                  <tr>
                    <td><br/><a href="adminmanage.jsp">查看所有管理员</a></td>
                  </tr>
                </table>
              </td> 
              <td>
                <table width="70%" hight="100%">
                  <tr>
      			    <th>管理员ID</th>
				    <th>用户名</th>
				    <th>管理员级别</th>
				    <th>管理员删除</th>           
                  </tr>
                  <% 
                  	String sql = "select Aid,Aname,Alevel from AdminInfo";
                  	Vector<String[]> vadmin = DBcart.getInfoArr(sql);
                  	int i=0;
					for(String[] str:vadmin)
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
                   		<td><%= str[0] %></td>
                   		<td><%= str[1] %></td>
                   		<td><%= str[2] %></td>
                   		<td><a href="AdminServlet?action=adminDelete&aid=<%= str[0] %>">删除</a></td>
                   <% 
                   	} 
                    %>
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