<%@ page contentType="text/html;charset=gb2312"%>
<%@ page import="cn.thxy.www.bean.AdminBean,cn.thxy.www.dbcart.DBcart,java.util.*"%>
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
     <title>商品添加</title>
     <script language="javascript" src="script/trim.js"></script>
     <script language="javascript">
       function addCheck()
       {
         if(document.addform.gname.value.trim()=="")
         {
         	alert("商品名称不能为空!!!");
         	return;
         }
         if(document.addform.gprice.value.trim()=="")
         {
         	alert("商品价格不能为空!!!");
         	return;
         }
         if(isNaN(document.addform.gprice.value*1))
         {
         	alert("商品价格只能是数字!!!");
         	return;
         }
         if(document.addform.gamount.value.trim()=="")
         {
         	alert("商品数量不能为空!!!");
         	return;
         }
         if(isNaN(document.addform.gamount.value*1))
         {
         	alert("商品数量只能是数字!!!");
         	return;
         }
         if(document.addform.gintro.value.trim()=="")
         {
         	alert("商品说明不能为空!!!");
         	return;
         }
         if(document.addform.gbrief.value.trim()=="")
         {
         	alert("商品简介不能为空!!!");
         	return;
         }
         document.addform.submit();         
       }
     </script>
   </head>
   <body>
     <table width="100%">
       <tr>
         <td><%@ include file="admintop.jsp" %></td>
       </tr>
       <tr>
         <td align="center">
           <form action="AdminServlet" method="post" name="addform">
             <table>
               <tr bgcolor="#E4EDFA">
                 <td>商品名称:</td>
                 <td><input name="gname"/></td>
               </tr>
               <tr>
                 <td>商品价格:</td>
                 <td><input name="gprice" /></td>
               </tr>
               <tr bgcolor="#E4EDFA">
                 <td>商品数量:</td>
                 <td><input name="gamount" /></td>
               </tr>
               <tr>
                 <td>商品类别:</td>
                 <td><input name="gclass" /></td>
               </tr>
               <tr bgcolor="#E4EDFA">
                 <td>图片URL:</td>
                 <td><input name="gurl" /></td>
               </tr>
               <tr>
                 <td>商品说明:</td>
               </tr>
               <tr>
                 <td colspan="2"><textarea cols="60" rows="6" name="gintro"></textarea></td>
               </tr>
               <tr>
                 <td>商品简介:</td>
               </tr>
               <tr>
                 <td colspan="2"><textarea cols="60" rows="6" name="gbrief"></textarea></td>
               </tr>
               <tr>
                 <td align="center" colspan="2">
                   <input type="button" value="商品添加" onclick="addCheck()"/>
                   <input type="hidden" name="action" value="addgoods"/>
                   <input type="reset" value="重置"/>
                   <input type="button" value="返回" onClick="history.back()">
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