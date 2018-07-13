<%@ page contentType="text/html;charset=gb2312"%>
<%@ page import="cn.thxy.www.bean.AdminBean,cn.thxy.www.dbcart.DBcart,java.util.*"%>
<table width="100%" border="0">
  <tr>
    <th>订单号</th>
    <th>订购人</th>
    <th>定购日期</th>
    <th>订购状态</th>
    <th>订单详细信息</th>
  </tr>
  <% 
  	Vector<String[]> vorder = (Vector<String[]>)request.getAttribute("vorder");
  	if(vorder==null)
  	{
  		DBcart.setSpan(10);
  		int nowpage = adBean.getCurPage();
  		String sql = "select Oid,Uname,Odate,Ostate from OrderInfo,UserInfo"+
  				" where Ostate='未发货' and OrderInfo.Uid=UserInfo.Uid order by Oid";
  		String sqlpage = "select count(*) from OrderInfo where Ostate='未发货'";
  		sqlpage = new String(sqlpage.getBytes(),"ISO-8859-1");
  		int totalpage = DBcart.getTotalPage(sqlpage);
  		sql = new String(sql.getBytes(),"ISO-8859-1");
  		adBean.setSql(sql);
  		//记住当前总页数
  		adBean.setTotalPage(totalpage);
		vorder = DBcart.getPageContent(nowpage,sql);
		DBcart.setSpan(2);
  	}
	for(int i=0;i<vorder.size();i++)
	{
		String[] str = vorder.get(i);
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
   		<td><%= str[3] %></td>
   		<td><a href="AdminServlet?action=ordermanage&oid=<%= str[0]%>">查看/管理</a></td>
   	 </tr>
   <% 
   	}
   %>
   <tr>
     <td colspan="5"><hr/></hr></td>
   </tr>
   <tr>
     <% 
    	int curPage = adBean.getCurPage();
    	int totalPage = adBean.getTotalPage();
      %>
     <td align="right">
     <% 
     	if(curPage>1)
     	{
     %>
     	<a href="AdminServlet?action=orderPageChange&curPage=<%= curPage-1%>"><<上一页</a>
     <%
     	}
     %>
     </td>
     <form action="AdminServlet" method="post">
       <td colspan="3" align="center">
      	  <select onchange="this.form.submit()" name="selPage">
      	   <% 
      	   		for(int i=1;i<=totalPage;i++)
      	   		{
      	   			String flag = "";
      	   			if(i==curPage)
      	   			{
      	   				flag = "selected";
      	   			}
      	    %>
      	    	<option value="<%=i%>" <%= flag %>>第<%= i %>页</option>
      	    <% 
      	    	}
      	     %>
      	  </select>
       </td>
       <input type="hidden" name="action" value="orderPageChange"/>
     </form>
     <td align="left">
     <% 
     	if(curPage<totalPage)
     	{
      %>
      	<a href="AdminServlet?action=orderPageChange&curPage=<%= curPage+1%>">下一页>></a>
      <% 
      	}
      %>
     </td>
   </tr>
</table>