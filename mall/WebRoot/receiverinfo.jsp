<%@ page contentType="text/html;charset=gb2312" %>
<%@ page import="java.util.*,cn.thxy.www.bean.CartBean" %>
<jsp:useBean id="mycart" class="cn.thxy.www.bean.CartBean" scope="session"/>
<% 
	if(session.getAttribute("user")==null)
	{
		String msg = "<h1>请你先登陆!!!</h1><a href='index.jsp'>[登录]</a>";
		request.setAttribute("msg",msg);
		String url = "/error.jsp";
		ServletContext sc = getServletContext(); 
		RequestDispatcher rd = sc.getRequestDispatcher(url); 
		rd.forward(request,response);
	}
	else
	{
 %>
<html>
  <head>
    <title>订单信息</title>
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
      <table width="100%" cellpadding="0" cellspacing="0">
        <tr>
          <td><%@ include file="top.jsp" %></td>
        </tr>
        <tr>
          <td>
            <center>
            <table>
              <caption><b><br/>收货人信息</b></caption>
              <form action="CartServlet" method="post" name="mfrec">
                <tr>
                  <td><br/>收货人姓名:</td>
                  <td><br/><input name="recname"/></td>
                </tr>
                <tr>
                  <td><br/>收货人地址:</td>
                  <td><br/><input name="recadr"/></td>
                </tr>
                <tr>
                  <td><br/>收货人电话:</td>
                  <td><br/><input name="rectel"/></td>
                </tr>
                <tr>
                  <td align="center" colspan="2">
                    <font color="red" size="">
                      <br/>请你务必填写正确的信息,以保证你的货物能顺利收到.
                    </font>
                  </td>
                </tr>
                <tr>
                  <td colspan="2" align="right">
                  	<input type="hidden" name="action" value="saveRec"/>
                    <br/><input type="button" value="确认" onclick="checkMsg()"/>
                  </td>
                </tr>
              </form>
            </table>            
            </center>
          </td>
        </tr>
      </table>
    </center>
  </body>
</html>
<% 
	}
 %>