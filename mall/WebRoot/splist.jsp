<%@ page contentType="text/html;charset=gb2312"%>
<%@ page import="cn.thxy.www.bean.CartBean,cn.thxy.www.dbcart.DBcart,java.util.*"%>
<table border="0" width="100%">
<% 
	Vector<String[]> vgoods = (Vector<String[]>)request.getAttribute("vgoods");
	if(vgoods==null)
	{
		mycart.setCurPage(1);
		int nowpage = mycart.getCurPage();
        String gsql = "select Gimgurl,Gname,Gintro,Gclass,"+
        			"Gprice,Glook,Gid from GoodsInfo";
        int totalpage = DBcart.getTotalPage("select count(*) from GoodsInfo");
        mycart.setTotalPage(totalpage);
        mycart.setSql(gsql);
		vgoods = DBcart.getPageContent(nowpage,gsql);
	}
	for(int i=0;i<vgoods.size();i++)
	{
		String[] str = vgoods.get(i);
 %>
  <tr height="160">
    <td width="150" align="center">
      <a href="CartServlet?action=getDetail&gid=<%= str[6] %>">
        <img src="<%= str[0] %>" height="150" border="1"/>      
      </a>
    </td>
    <td colspan="2">
      <table width="100%">
        <tr>
          <td bgcolor="#E4EDFA">
            <a href="CartServlet?action=getDetail&gid=<%= str[6] %>"><%= str[1] %></a>
          </td>
        </tr>
        <tr>
          <td><font><%= mycart.manageStr(str[2]) %></font></td>
        </tr>
        <tr>
          <td bgcolor="#E4EDFA"><font>所属分类:<%= str[3] %></font></td>
        </tr>
        <tr>
          <td><font>定价:￥<%= str[4] %></font></td>
        </tr>
        <tr>
          <td bgcolor="#E4EDFA"><font>浏览次数:<%= str[5] %></font></td>
        </tr>
        <tr>
          <td>
          <% 
          	String temp = "select Gamount from GoodsInfo where Gamount<1 and Gid="+str[6];
          	boolean flag = DBcart.isLegal(temp);
          	if(!flag)
          	{
          %>
            <a href="CartServlet?action=buy&flag=0&gid=<%= str[6] %>">
              <img src="img/other/buy.gif" border="0"/>
            </a>
          <%
          	}
          	else
          	{
           %>
           	<font>缺货</font>
		   <% 
		  	}
		   %>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <% 
  	}
   %>
   <tr>
     <td colspan="3"><hr/></td>
   </tr>
   <tr align="center">
     <% 
    	int curPage = mycart.getCurPage();
    	int totalPage = mycart.getTotalPage();
     %>
      <td>
      <% 
      	if(curPage>1)
      	{
      %>
      	<a href="CartServlet?action=pageChange&curPage=<%= curPage-1%>"><<上一页</a>
      <%
      	}
      %>
      </td>
      <form action="CartServlet" method="post">
      <td align="right" width="200">
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
      	  <input type="hidden" name="action" value="pageChange"/>
      	</td>
      	</form>
      <td align="left" width="40%">
      	<% 
      		if(curPage<totalPage)
      		{
      	 %>
      	 	<a href="CartServlet?action=pageChange&curPage=<%= curPage+1%>">下一页>></a>
      	 <% 
      	 	}
      	  %>
      </td>
    </tr>
</table>