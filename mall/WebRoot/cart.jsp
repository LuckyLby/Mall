<%@ page contentType="text/html;charset=gb2312" %>
<%@ page import="java.util.*,cn.thxy.www.bean.CartBean" %>

<html>
  <head>
    <title>我的商城</title>
	<script language="javascript" src="script/trim.js"></script>
    <script language="javascript">
    	function checkNum(num)
    	{
    		var reg = /^[1-9][0-9]*$/;
    		if(reg.test(num.trim()))
    		{
    			return true;
    		}
    		else
    		{
    			alert("商品数量只能为数字且不能为0！！！");
    			return false;
    		}
    	}
    </script>
  </head>
  <body>
    <jsp:useBean id="mycart" class="cn.thxy.www.bean.CartBean" scope="session"/>
    <center>
    <table width="100%">
      <tr>
        <td><%@ include file="top.jsp" %></td>
      </tr>
      <tr align="center">
        <td>
        <% 
    	if(mycart.getCart().size()==0)
    	{
    		out.println("<b>你还没有购买商品</b>");
    	}
    	else
    	{
         %>
	        <table border="0" width="100%">
	         <tr>
	        	<th>商品名称</th>
	        	<th>商品价格</th>
	        	<th>商品数量</th>
	        	<th>删除</th>
	      	</tr>
			<% 
				Vector<String[]> vgoods = mycart.getCartContent();
				int i = 0;
				for(String[] arr:vgoods)
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
				  <td align="left"><%= arr[0] %></td>
				  <td>￥<%= arr[1] %></td>
				  <form action="CartServlet" method="post"
				  		onsubmit="return checkNum(document.all.gnum<%= arr[3] %>.value);">
				  <td>
				    <input type="text" id="gnum<%= arr[3] %>" name="gnum" value="<%= arr[2] %>" size="10"/>
				    <input type="submit" value="修改"/>
				    <input type="hidden" name="gid" value="<%= arr[3]%>"/>
				    <input type="hidden" name="action" value="changeNum"/>
				  </td>
				  </form>
				  <td><a href="CartServlet?action=delete&gid=<%= arr[3]%>">删除</a></td>
				</tr>
			<%
				}
			 %>
			   <tr>
			     <td colspan="2"></td>
			     <td align="center"><b>商品价格总计:￥<%= mycart.getAccount() %></b></td>
			     <td align="center">
			     <% 
			     	if(session.getAttribute("recMsg")==null)
			     	{
			      %>
			       <a href="CartServlet?action=balance">
			         <img src="img/other/balance.gif" border="0"/>
			       </a>
			      <% 
			      	}
			      	else
			      	{
			       %>
			       <a href="order.jsp">
			         <img src="img/other/balance.gif" border="0"/>
			       </a>			       	
			       <% 
			       	}
			        %>
			     </td>
			   </tr>
	        </table>
        <% 
        	}
         %>     
        </td>
      </tr>
    </table>    
    </center>
  </body>
</html>