<%@ page contentType="text/html;charset=gb2312"%>
<%@ page import="cn.thxy.www.bean.AdminBean,cn.thxy.www.dbcart.DBcart,java.util.*"%>
<% 
	if(session.getAttribute("admin")==null)
	{
		response.sendRedirect("adlogin.jsp");
	}
	else
	{
		Vector<String[]> vgoods = (Vector<String[]>)request.getAttribute("vgoods");
		String[] str = vgoods.get(0);
 %>
 <html>
   <head>
     <title>商品管理</title>
     <script language="javascript" src="script/trim.js"></script>
     <script language="javascript">
       function modifyGoods()
       {
		 document.myform.action.value="modify";
         if(document.myform.gname.value.trim()=="")
         {
         	alert("商品名称不能为空!!!");
         	return;
         }
         if(document.myform.gprice.value.trim()=="")
         {
         	alert("商品价格不能为空!!!");
         	return;
         }
         if(isNaN(document.myform.gprice.value*1))
         {
         	alert("商品价格只能是数字!!!");
         	return;
         }
         if(document.myform.gamount.value.trim()=="")
         {
         	alert("商品数量不能为空!!!");
         	return;
         }
         if(isNaN(document.myform.gamount.value*1))
         {
         	alert("商品数量只能是数字!!!");
         	return;
         }
         if(document.myform.gdate.value.trim()=="")
         {
         	alert("日期不能为空!!!");
         	return;
         }
         var reg = /^\d{4}-(0[1-9]|1[0-2])-([0-2][1-9]|3[0-1])$/;
         if(!reg.test(document.myform.gdate.value.trim()))
         {
         	alert("日期格式不对,只能为yyyy-mm-dd");
         	return;
         }
         if(document.myform.gintro.value.trim()=="")
         {
         	alert("商品说明不能为空!!!");
         	return;
         }
         if(document.myform.gbrief.value.trim()=="")
         {
         	alert("商品简介不能为空!!!");
         	return;
         }
         document.myform.submit();
       }
       function deleteGoods()
       {
       		document.myform.action.value="delete";
       		document.myform.submit();
       }
     </script>
   </head>
   <body>
     <table width="100%" cellpadding="0" cellspacing="0">
       <tr>
         <td><%@ include file="admintop.jsp" %></td>
       </tr>
       <tr>
         <td align="center">
           <form action="AdminServlet" method="post" name="myform">
             <table>
               <tr>
                 <td>商品名称:</td>
                 <td><input name="gname" size="30" value="<%=str[1] %>"/></td>
               </tr>
               <tr>
                 <td>商品价格:</td>
                 <td><input name="gprice" size="30" value="<%=str[2] %>"/></td>
               </tr>
               <tr>
                 <td>商品数量:</td>
                 <td><input name="gamount" size="30" value="<%=str[3] %>"/></td>
               </tr>
               <tr>
                 <td>商品类别:</td>
                 <td><input name="gclass" size="30" value="<%=str[4] %>"/></td>
               </tr>
               <tr>
                 <td>上架日期:</td>
                 <td><input name="gdate" size="30" value="<%=str[5] %>"/></td>
               </tr>
               <tr>
                 <td>图片URL:</td>
                 <td><input name="gurl" size="30" value="<%=str[6] %>"/></td>
               </tr>
               <tr>
                 <td>商品说明:</td>
               </tr>
               <tr>
                 <td colspan="2">
                   <textarea cols="60" rows="6" name="gintro"><%=str[7] %></textarea>
                 </td>
               </tr>
               <tr>
                 <td>商品简介:</td>
               </tr>
               <tr>
                 <td colspan="2">
                   <textarea cols="60" rows="6" name="gbrief"><%=str[8] %></textarea>
                 </td>
               </tr>
               <tr align="center">
                 <td colspan="2">
                   <input type="hidden" name="gid" value="<%= str[0] %>"/>
                   <input type="hidden" name="action" value=""/>
                   <input type="button" value="修改" onclick="modifyGoods()"/>
                   <input type="button" value="删除" onclick="deleteGoods()"/>
                   <input type="button" value="返回" onclick="javascript:history.back()"/>
                 </td>
               </tr>
             </table>
           </form>
         </td>
       </tr>
     </table>
   </body>
 </html>
 <% 
 	}
  %>