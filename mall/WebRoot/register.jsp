<%@ page contentType="text/html;charset=gb2312" %>
<%@ page import="java.util.*,cn.thxy.www.bean.CartBean" %>
<html>
  <head>
    <title>注册</title>
    <script language="javascript" src="script/trim.js"></script>
    <script language="javascript">
      function checkUid()
      {
      	var flag = false;
      	var uname = document.regform.uname.value;
      	if(uname.trim()=="")
      	{
      		document.all.uinfo.innerHTML =
      				"<font color='red' size='2px'>此项为必填项，请设置你的用户名。</font>";
      	}
      	else
      	{
      		flag = true;
			document.all.uinfo.innerHTML =
					"<font size='2px'>用户名可以由小写英文字母、中文、数字组成。</font>";
      	}
      	return flag;
      }
      
      function checkFirPwd()
      {
      	var flag = false;
      	var fpwd = document.regform.fpwd.value;
      	if(fpwd.trim=="")
      	{
      		document.all.pfinfo.innerHTML = 
      				"<font color='red' size='2px'>此项为必填项，请设置你的密码。</font>";
      	}
      	else if(fpwd.length<6||fpwd.length>20||!fpwd.match("[a-zA-Z0-9]+"))
      	{
      		document.all.pfinfo.innerHTML = 
      				"<font color='red' size='2px'>您设置的密码有误，请重新设置你的密码。</font>";
      	}
      	else
      	{
      		flag = true;
     		document.all.pfinfo.innerHTML = 
      				"<font size='2px'>您的密码可以由大小写英文字母、数字组成，长度6－20位。</font>";      		
      	}
      	return flag;
      }
      
      function checkSecPwd()
      {
      	var flag = false;
      	var fpwd = document.regform.fpwd.value;
      	var spwd = document.regform.spwd.value;
      	if(fpwd.trim()=="")
      	{
       		document.all.psinfo.innerHTML = 
      				"<font color='red' size='2px'>此项为必填项，请再次输入您的密码。</font>";     		
      	}
      	else if(fpwd.trim()!=spwd.trim())
      	{
      		document.all.psinfo.innerHTML = 
     				"<font color='red' size='2px'>两次密码输入的不一致，请重新输入。</font>";      		
      	}
      	else
      	{
      		flag = true;
      		document.all.psinfo.innerHTML = "";
      	}
      	return flag;
      }
      
      function checkEmail()
      {
      	var flag = false;
      	var email = document.regform.email.value;
      	if(email.trim()=="")
      	{//当该项为空时
      		document.all.einfo.innerHTML = 
      				"<font color='red' size='2px'>此项为必填项，请输入您的Email地址。</font>";
      	}
      	else
      	{
      		flag = true;
      		document.all.einfo.innerHTML = 
      				"<font size='2'>请填写有效的E-mail地址。</font>";      		
      	}
      	return flag;
      	//****************************************/
      	/*没有写正则表达式来判断E-mail格式是否正确
      	//****************************************/
      }
      
      function mfSub()
      {
      	var flag1 = checkUid();
      	var flag2 = checkFirPwd();
      	var flag3 = checkSecPwd();
      	var flag4 = checkEmail();
      	if(flag1&&flag2&&flag3&&flag4)
      	{
      		document.regform.submit();
      	}
      }
    </script>
  </head>
  <body>
    <jsp:useBean id="mycart" class="cn.thxy.www.bean.CartBean" scope="session"/>
    <center>
      <table width="100%">
		  <tr height="70">
		    <td align="center"><img src="img/other/logo.gif" width="50" height="50"/>购物新天地</td>
		  </tr>
		  <tr height="15">
		    <td><hr color="#FF7F00" width="100%"/></hr></td>
		  </tr>
		  <tr align="center">
		    <td>
		      <table border="0">
		      <form action="CartServlet" method="post" name="regform">
		        <tr height="50">
		          <td>请填写你的用户名:</td>
		          <td><input type="text" name="uname" size="20" onblur="checkUid()"/></td>
		          <td id="uinfo">
		            <font size="2px">用户名可以由小写英文字母、中文、数字组成。</font>
		          </td>
		        </tr>
		        <tr height="50">
		          <td>设置你的密码:</td>
		          <td><input type="password" name="fpwd" size="20" onblur="checkFirPwd()"/></td>
		          <td id="pfinfo">
		            <font size="2px">您的密码可以由大小写英文字母、数字组成，长度6－20位。</font>
		          </td>
		        </tr>
		        <tr height="50">
		          <td>请再次输入你的密码:</td>
		          <td><input type="password" name="spwd" size="20" onblur="checkSecPwd()"/></td>
		          <td id="psinfo"></td>
		        </tr>
		        <tr height="50">
		          <td>请填写你的E-mail地址:</td>
		          <td><input type="text" name="email" size="20" onblur="checkEmail()"/></td>
		          <td id="einfo">
		            <font size="2">请填写有效的E-mail地址。</font>
		          </td>
		        </tr>
		        <tr align="center">
		          <td colspan="3">注:以上各项为必填项,请认真填写。</td>
		        </tr>
		        <tr align="center">
		          <td colspan="2" align="right">
		            <input type="button" name="sub" value="注册" onclick="mfSub()"/>
		            <input type="hidden" name="action" value="register"/>
		          </td>
		          <td><a href="javascript:history.back()">单击这里返回</a></td>
		        </tr>
		        </form>
		      </table>
		    </td>
		  </tr>
      </table>
    </center>
  </body>
</html>