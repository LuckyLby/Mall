<%@ page contentType="text/html;charset=gb2312" %>
<%@ page import="java.util.*,cn.thxy.www.bean.AdminBean,cn.thxy.www.dbcart.DBcart" %>
<link href="css/generalstyle.css" type="text/css" rel="stylesheet">
<script language="javascript" src="script/trim.js"></script>
<script language="javascript">
  function txtclear()
  {
    document.mfsearch.tsearch.value="";
  }
  function check()
  {
    var key = document.mfsearch.tsearch.value;
    if(key.trim()=="")
    {
      alert("关键字不能为空");
      return;
    }
    document.mfsearch.submit();
  }
 </script>
<form action="AdminServlet" method="post" name="mfsearch">
<table>
  <tr>
    <td>
      <input name="tsearch" value="请输入要搜索的关键字" onfocus="txtclear()"/>
    </td>
  </tr>
  <tr align="right">
    <td>
      <input type="hidden" name="action" value="search"/>
      <input type="button" id="bsearch" name="bsearch" value="搜索" onclick="check()"/>		        
    </td>
  </tr>
</table>
</form>