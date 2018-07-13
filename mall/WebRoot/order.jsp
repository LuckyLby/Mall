<%@ page contentType="text/html;charset=gb2312" %>
<%@ page import="java.util.*,cn.thxy.www.bean.CartBean" %>
<jsp:useBean id="mycart" class="cn.thxy.www.bean.CartBean" scope="session"/>
<% 
	String[] recMsg = (String[])session.getAttribute("recMsg");
	if(recMsg==null)
	{
		response.sendRedirect("index.jsp");
	}
	else
	{
 %>
<html>
  <head>
    <title>订单确认</title>
    <script language="javascript" src="script/trim.js"></script>
    <script language="javascript">
      function checkMsg()
      {
      	var recname = document.mfrec.recname.value;
      	var recadr = document.mfrec.recadr.value;
      	var rectel = document.mfrec.rectel.value;
      	if(recname.trim()=="")
      	{
      		alert("收货人不能为空!!!");
      		return;
      	}
      	if(recadr.trim()=="")
      	{
      		alert("收货人地址不能为空!!!");
      		return;
      	}
      	if(rectel.trim()=="")
      	{
      		alert("电话号码不能为空!!!");
      		return;
      	}
      	document.mfrec.submit();
      }
    </script>
  </head>
  <body>
    <center>
      <table width="100%">
        <tr>
          <td><%@ include file="top.jsp" %></td>
        </tr>
        <tr>
          <td>
            <center>
            <table width="80%">
              <tr>
 	        	<th>商品名称</th>
	        	<th>商品价格</th>
	        	<th>商品数量</th>
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
				  <td><%= arr[0] %></td>
				  <td>￥<%= arr[1] %></td>
				  <td><%= arr[2] %></td>
				</tr>
			<% 
				}
			 %>
			   <tr>
			     <td colspan="3" align="right"><b>商品价格总计:￥<%= mycart.getAccount() %></b></td>
			   </tr>
            </table>
            </center>
          </td>
        </tr>
        <form action="CartServlet" method="post" name="mfrec">
        <tr align="center">
          <td><br/>收货人姓名:&nbsp;<input name="recname" value="<%= recMsg[0] %>"/></td>
        </tr>
        <tr align="center">
          <td><br/>收货人地址:&nbsp;<input name="recadr" value="<%= recMsg[1] %>"/></td>
        </tr>
        <tr align="center">
          <td><br/>收货人电话:&nbsp;<input name="rectel" value="<%= recMsg[2] %>"/></td>
        </tr>
        <tr align="center">
          <td>
          	<input type="hidden" name="action" value="recModify"/>
            <br/><input type="button" value="收货人信息修改" onclick="checkMsg()"/>&nbsp;&nbsp;&nbsp;
            <a href="CartServlet?action=orderConfirm">订单确认</a>
          </td>
        </tr>     
        </form>
      </table>
    </center>
  </body>
</html>
<% 
	}
 %>