<%@ page contentType="text/html;charset=gb2312" %>
<script language="javascript" src="script/trim.js"></script>
<script language="javascript">
  function fcheck()
  {
    var uid = document.all.uid.value.trim();
    var pwd = document.all.pwd.value.trim();
    if(uid=="")
    {
      alert("用户名不能为空!!!");
      return;
    }
    if(pwd=="")
    {
      alert("密码不能为空!!!");
      return;
    }
    if(pwd.length<6)
    {
      alert("密码长度不得小于6!!!");
      return;
    }    
    document.loginform.submit();
  }
</script>
<form name="loginform" action="CartServlet" method="post">
  <center>
  <table border="0" bgcolor="#FFF0E1" width="80%" height="150">
    <tr align="center" height="40">
      <td>用户名:</td>
      <td><input type="text" id="uid" name="uid" size="12"/></td>
    </tr>
    <tr>
      <td>密&nbsp;&nbsp;码:</td>
      <td><input type="password" id="pwd" name="pwd" size="12"/></td>
    </tr>
    <tr height="40">
      <td>
        <input type="button" id="denglu" name="denglu" value="登录" onclick="fcheck()"/>
        <input type="hidden" name="action" value="login"/>
      </td>
      <td><input type="reset" id="zhuce" name="zhuce" value="重置"/></td>
    </tr>
    <tr align="right">
      <td colspan="2"><a href="register.jsp">[新用户注册]</a></td>
    </tr>
  </table>
  </center>
</form>