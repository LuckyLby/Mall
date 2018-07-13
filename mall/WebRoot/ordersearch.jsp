<%@ page contentType="text/html;charset=gb2312" %>
<%@ page import="java.util.*,cn.thxy.www.bean.AdminBean,cn.thxy.www.dbcart.DBcart" %>
<script language="javascript" src="script/trim.js"></script>
<script language="javascript">
  function txtclear()
  {
    document.mfsearch.txtsearch.value="";
  }
  function check()
  {
    var key = document.mfsearch.txtsearch.value;
    if(key.trim()=="")
    {
      alert("关键字不能为空!!!");
      return;
    }
    var reg = /^\d+$/;
    if(!reg.test(key))
    {
      alert("定单号只能为整数!!!");
      return;
    }
    document.mfsearch.submit();
  }
</script>
<table>
<form name="mfsearch" action="AdminServlet" method="post">
  <tr>
    <td>
      <input name="txtsearch" value="请输入要查询的定单号" onfocus="txtclear()"/>
    </td>
  </tr>
  <tr>
    <td align="right">
      <input type="button" value="搜索" onclick="check()"/>
      <input type="hidden" name="action" value="orderSearch"/>
    </td>
  </tr>
</form>
</table>