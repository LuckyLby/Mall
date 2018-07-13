package cn.thxy.www.servlet;

import javax.servlet.*;
import javax.servlet.http.*;

import cn.thxy.www.bean.AdminBean;
import cn.thxy.www.dbcart.DBcart;

import java.io.*;
import java.util.*;

public class AdminServlet extends HttpServlet
{
	public  void doGet(HttpServletRequest request,HttpServletResponse response)
              							throws ServletException,IOException
    {
    	this.doPost(request,response);
    }
	public  void doPost(HttpServletRequest request,HttpServletResponse response)
              							throws ServletException,IOException
    {
    	//���ñ����ʽ
    	request.setCharacterEncoding("gb2312");
    	response.setContentType("text/html;charset=gb2312");
    	response.setCharacterEncoding("gb2312");
    	//�õ�session����
    	HttpSession session = request.getSession(true);
    	//�õ����������
    	PrintWriter out = response.getWriter();
    	//�õ�����Ķ���
    	String action = request.getParameter("action").trim();
    	if(action.equals("login"))
    	{//����Ա��½ʱ
    		//�õ���½���û���������
    		String apwd = request.getParameter("apwd").trim();
    		String aname = request.getParameter("aname").trim();
    		String sql = "select Aid from AdminInfo where Aname='"+
    								aname+"' and Apwd='"+apwd+"'";
    		sql = new String(sql.getBytes(),"ISO-8859-1");
    		//�жϸ��û��Ƿ���ȷ
    		boolean flag = DBcart.isLegal(sql);
    		if(flag)
    		{//����Ա��½�ɹ�
    			session.setAttribute("admin",aname);
    			response.sendRedirect("adminindex.jsp");
    		}
    		else
    		{//��½ʧ��
    			String msg = "�Բ���,��½ʧ��,�����µ�½!!!";
    			String url = "/error.jsp";
				pageForward(msg,url,request,response);
    		}
    	}
    	else if(action.equals("logout"))
    	{//����Աע��ʱ
    		//ʹsessionʧЧ
    		request.getSession(true).invalidate();
    		response.sendRedirect("index.jsp");
    	}
    	else if(action.equals("pageChange"))
    	{//��Ʒ��ҳʱ
    		AdminBean adBean = (AdminBean)session.getAttribute("adBean");
    		if(adBean==null)
    		{
    			adBean = new AdminBean();
    		}
	    	//�õ������ҳ��
    		String curPage = request.getParameter("curPage");
    		if(curPage!=null)
    		{//�û�ͨ�������,��һҳ��ťʱ
    			int page = Integer.parseInt(curPage.trim());
    			//��ס��ǰҳ
    			adBean.setCurPage(page);
    		}
    		else
    		{//���û���������б��ʱ
    			String selPage = request.getParameter("selPage").trim();
    			int page = Integer.parseInt(selPage);
    			adBean.setCurPage(page);
    		}
    		String sql = adBean.getSql();
    		//�õ���ҳ��ҳ�������
    		Vector<String[]> vgoods = DBcart.getPageContent(adBean.getCurPage(),sql);
			request.setAttribute("vgoods",vgoods);
    		session.setAttribute("adBean",adBean);
    		//forward���޸ĵ���ҳ��
			String url = "/adminindex.jsp";
    		ServletContext sc = getServletContext(); 
			RequestDispatcher rd = sc.getRequestDispatcher(url); 
			rd.forward(request,response);	
    	}
    	else if(action.equals("search"))
    	{//������Ʒʱ
    		//�õ�javaBean����
    		AdminBean adBean = (AdminBean)session.getAttribute("adBean");
    		if(adBean==null)
    		{
    			adBean = new AdminBean();
    		}
    		adBean.setCurPage(1);
    		//�õ�Ҫ��������Ϣ��ת��
    		String tsearch = request.getParameter("tsearch");
    		String cname = request.getParameter("cname");
    		String sql = "";
    		String sqlpage = "";
    		if(cname==null)
    		{//���������������ʱ
	    		tsearch = new String(tsearch.trim().getBytes(),"ISO-8859-1");
	    		//�õ�������Ϣ��sql����Ϣ������sql
	    		sql = "select Gimgurl,Gname,Gintro,Gclass,Gprice,Glook,Gid,"+
	    						"Gdate from GoodsInfo where Gname like '%"+tsearch+"%'";
	    		sqlpage = "select count(*) from GoodsInfo "+
	    									"where Gname like '%"+tsearch+"%'";    			
    		}
    		else
    		{//���������ʱ
//    			cname = new String(cname.trim().getBytes(),"ISO-8859-1");
	    		//�õ����������Ϣ��sql����Ϣ������sql
	    		sql = "select Gimgurl,Gname,Gintro,Gclass,Gprice,Glook,Gid,"+
	    						"Gdate from GoodsInfo where Gclass='"+cname.trim()+"'";
	    		sqlpage = "select count(*) from GoodsInfo "+
	    									"where Gclass='"+cname.trim()+"'";    			
    		}
    		adBean.setSql(sql);
    		//������ҳ��
    		int totalpage = DBcart.getTotalPage(sqlpage);
    		adBean.setTotalPage(totalpage);
    		session.setAttribute("adBean",adBean);
    		//�õ���һҳ������
    		Vector<String[]> vgoods = DBcart.getPageContent(1,sql);
    		if(vgoods.size()==0)
    		{//û���������û�Ҫ�ҵ���Ʒ
    			String msg = "�Բ���,û���ѵ���Ҫ����Ʒ!!!";
    			String url = "/error.jsp";
    			pageForward(msg,url,request,response);
    		}
    		else
    		{//��������Ϣ������
    			request.setAttribute("vgoods",vgoods);
    			String url = "/adminindex.jsp";
	    		ServletContext sc = getServletContext(); 
				RequestDispatcher rd = sc.getRequestDispatcher(url); 
				rd.forward(request,response);
    		}
    	}
    	else if(action.equals("goodsManage"))
    	{//������Ʒʱ
    		//�õ�Ҫ�޸Ļ�ɾ����Ʒ��ID
    		String gid = request.getParameter("gid").trim();
    		String sql = "select Gid,Gname,Gprice,Gamount,Gclass,Gdate,Gimgurl,"+
    						"Gintro,Gbrief from GoodsInfo where Gid="+Integer.parseInt(gid);
    		//�õ�����Ʒ����Ϣ
    		Vector<String[]> vgoods = DBcart.getInfoArr(sql);	
			request.setAttribute("vgoods",vgoods);
			ServletContext sc = getServletContext(); 
			RequestDispatcher rd = sc.getRequestDispatcher("/modifygoods.jsp"); 
			rd.forward(request,response);
    	}
    	else if(action.equals("addgoods"))
    	{//������Ʒ
			//�����������Ʒ�ĸ�������
    		String gname = request.getParameter("gname").trim();
    		String gprice = request.getParameter("gprice").trim();
    		String gamount = request.getParameter("gamount").trim();
    		String gclass = request.getParameter("gclass").trim();
    		String gurl = request.getParameter("gurl").trim();
    		String gintro = request.getParameter("gintro").trim();
    		String gbrief = request.getParameter("gbrief").trim();
    		int gid = DBcart.getID("GoodsInfo","Gid");
    		String sql = "";
    		if(!gclass.equals("")&&!gurl.equals(""))
    		{//��������Ʒ����ͼƬURL
	    		//��ϳ�sql���
	    		sql = "insert into GoodsInfo(Gid,Gname,Gprice,"+
	    						"Gamount,Gdate,Gclass,Gimgurl,Gintro,Gbrief)"+
	    						" values("+gid+",'"+gname+"',"+Double.parseDouble(gprice)+
	    						","+Integer.parseInt(gamount)+",now(),'"+gclass+
	    						"','"+gurl+"','"+gintro+"','"+gbrief+"')";    			
    		}
    		else if(!gclass.equals("")&&gurl.equals(""))
    		{//������Ʒ���,��ͼƬURLû������
	    		sql = "insert into GoodsInfo(Gid,Gname,Gprice,"+
	    						"Gamount,Gdate,Gclass,Gintro,Gbrief)"+
	    						" values("+gid+",'"+gname+"',"+Double.parseDouble(gprice)+
	    						","+Integer.parseInt(gamount)+",now(),'"+gclass+
	    						"','"+gintro+"','"+gbrief+"')";   			
    		}
    		else if(gclass.equals("")&&!gurl.equals(""))
    		{//����ͼƬURL,��û����Ʒ���
	    		sql = "insert into GoodsInfo(Gid,Gname,Gprice,"+
	    						"Gamount,Gdate,Gimgurl,Gintro,Gbrief)"+
	    						" values("+gid+",'"+gname+"',"+Double.parseDouble(gprice)+
	    						","+Integer.parseInt(gamount)+",now(),'"+
	    						gurl+"','"+gintro+"','"+gbrief+"')"; 
    		}
    		else
    		{//��Ʒ����ͼƬURL��û������
	    		sql = "insert into GoodsInfo(Gid,Gname,Gprice,"+
	    						"Gamount,Gdate,Gintro,Gbrief)"+
	    						" values("+gid+",'"+gname+"',"+Double.parseDouble(gprice)+
	    						","+Integer.parseInt(gamount)+",now(),'"+gintro+"','"+gbrief+"')"; 
    		}
    		//��gb2312ת��Ϊiso-8859-1���������ݿ�
    		sql = new String(sql.getBytes(),"ISO-8859-1");
    		int i = DBcart.updateTable(sql);
    		String msg = "";
    		if(i==1)
    		{
    			msg = "��ϲ��,��Ʒ��ӳɹ�!!!";
    		}
    		else
    		{
    			msg = "�Բ���,��Ʒ���ʧ��!!!";
    		}
    		pageForward(msg,"/error.jsp",request,response);
    	}
    	else if(action.equals("modify"))
    	{//�޸���Ʒ��Ϣ
    		//�����޸ĺ���Ʒ�ĸ�������ֵ
    		String gid = request.getParameter("gid").trim();
    		String gname = request.getParameter("gname").trim();
    		String gprice = request.getParameter("gprice").trim();
    		String gamount = request.getParameter("gamount").trim();
    		String gclass = request.getParameter("gclass").trim();
    		String gdate = request.getParameter("gdate").trim();
    		String gurl = request.getParameter("gurl").trim();
    		String gintro = request.getParameter("gintro").trim();
    		String gbrief = request.getParameter("gbrief").trim();
    		//���ַ���ת��Ϊ��ֵ��
    		int id = Integer.parseInt(gid);
    		double price = Double.parseDouble(gprice);
    		int amount = Integer.parseInt(gamount);
    		//�õ�Ҫ���µ�Sql���
    		String sql = "update GoodsInfo set gname=\""+gname+"\","+
    						"gprice="+price+",gamount="+amount+",gclass='"+
    						gclass+"',gdate='"+gdate+"',gimgurl='"+gurl+"',"+
    						"gintro='"+gintro+"',gbrief='"+gbrief+"' where gid="+id;
    		//��sqlת��,��ִ�и���
    		sql = new String(sql.getBytes(),"ISO-8859-1");
    		int i = DBcart.updateTable(sql);
    		String msg = "";
    		if(i==1)
    		{
    			msg = "��ϲ��,��Ʒ�޸ĳɹ�!!!";
    		}
    		else
    		{
    			msg = "�Բ���,��Ʒ�޸�ʧ��!!!";
    		}
    		pageForward(msg,"/error.jsp",request,response);
    	}
    	else if(action.equals("delete"))
    	{//ɾ����Ʒ
    		//�õ�Ҫɾ����Ʒ��ID
    		String gid = request.getParameter("gid");
    		//��IDת��Ϊ��ֵ��
    		int id = Integer.parseInt(gid);
    		//��ɾ����Ʒʱ,ֻ������Ʒ������Ϊ0
    		String sql = "update GoodsInfo set gamount=0 where Gid="+id;
    		int i = DBcart.updateTable(sql);
    		String msg = "";
    		if(i==1)
    		{
    			msg = "��ϲ��,��Ʒ�޸ĳɹ�!!!";
    		}
    		else
    		{
    			msg = "�Բ���,��Ʒ�޸�ʧ��!!!";
    		}
    		pageForward(msg,"/error.jsp",request,response);
    	}
    	else if(action.equals("orderPageChange"))
    	{//������ҳʱ
    		//�õ�javaBean����
    		AdminBean adBean = (AdminBean)session.getAttribute("adBean");
    		if(adBean==null)
    		{
    			adBean = new AdminBean();
    		}
    		//���ܲ���
    		String curPage = request.getParameter("curPage");
    		String selPage = request.getParameter("selPage");
    		//���õ�ǰҳ��¼����Ϊ10
    		DBcart.setSpan(10);
    		if(curPage!=null)
    		{//�û�ͨ�������,��һҳ��ťʱ
    			int page = Integer.parseInt(curPage.trim());
    			//��ס��ǰҳ
    			adBean.setCurPage(page);
    		}
    		else
    		{//���û���������б��ʱ
    			int page = Integer.parseInt(selPage.trim());
    			adBean.setCurPage(page);
    		}
    		//�õ���ǰ��ҳ��ִ�е�sql
    		String sql = adBean.getSql();
    		//�õ���ҳ��ҳ�������
    		Vector<String[]> vorder = DBcart.getPageContent(adBean.getCurPage(),sql);
			request.setAttribute("vorder",vorder);
    		session.setAttribute("adBean",adBean);
    		//�ָ���¼���Ϊ2
    		DBcart.setSpan(2);
    		//forward���޸ĵ���ҳ��
			String url = "/ordermanage.jsp";
    		ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(url);
			rd.forward(request,response);
    	}
    	else if(action.equals("orderSearch"))
    	{//��������
    		//�õ�javaBean����
    		AdminBean adBean = (AdminBean)session.getAttribute("adBean");
    		if(adBean==null)
    		{
    			adBean = new AdminBean();
    		}
    		String txtsearch = request.getParameter("txtsearch");
    		String type = request.getParameter("type");
    		String sql = "";
    		//��ÿҳ��¼����Ϊ10
    		DBcart.setSpan(10);
    		adBean.setCurPage(1);
    		if(txtsearch!=null)
    		{//�û����ı�����������������
	    		int oid = Integer.parseInt(txtsearch.trim());
	  			sql = "select Oid,Uname,Odate,Ostate from OrderInfo,UserInfo"+
	  						" where Oid="+oid+" and OrderInfo.Uid=UserInfo.Uid";
	    		adBean.setSql(sql);
	    		//������ҳ��
	    		adBean.setTotalPage(1);    			
    		}
    		else
    		{
    			String sqlpage = "";
    			if(type.trim().equals("all"))
    			{//��ѯ���ж���
  					sql = "select Oid,Uname,Odate,Ostate from OrderInfo,UserInfo"+
  						" where OrderInfo.Uid=UserInfo.Uid";
  					sqlpage = "select count(*) from OrderInfo";
    			}
    			else if(type.trim().equals("yes"))
    			{//��ѯ�����ѷ�������
			  		sql = "select Oid,Uname,Odate,Ostate from OrderInfo,UserInfo"+
			  				" where Ostate='�ѷ���' and OrderInfo.Uid=UserInfo.Uid order by Oid";
			  		sqlpage = "select count(*) from OrderInfo where Ostate='�ѷ���'";
    			}
    			else if(type.trim().equals("no"))
    			{//��ѯ����δ��������
			  		sql = "select Oid,Uname,Odate,Ostate from OrderInfo,UserInfo"+
			  				" where Ostate='δ����' and OrderInfo.Uid=UserInfo.Uid order by Oid";
			  		sqlpage = "select count(*) from OrderInfo where Ostate='δ����'";
    			}
    			//ת��
		  		sqlpage = new String(sqlpage.getBytes(),"ISO-8859-1");
		  		sql = new String(sql.getBytes(),"ISO-8859-1");
		  		int totalpage = DBcart.getTotalPage(sqlpage);
		  		adBean.setSql(sql);
		  		//��ס��ǰ��ҳ��
		  		adBean.setTotalPage(totalpage);
    		}
    		session.setAttribute("adBean",adBean);
    		//�õ���һҳ������
    		Vector<String[]> vorder = DBcart.getPageContent(1,sql);
		  	DBcart.setSpan(2);
    		if(vorder.size()==0)
    		{//û���������û�Ҫ�ҵ���Ʒ
    			String msg = "�Բ���,û���ѵ���Ҫ��ѯ�Ķ���!!!";
    			String url = "/error.jsp";
    			pageForward(msg,url,request,response);
    		}
    		else
    		{//��������Ϣ������
    			request.setAttribute("vorder",vorder);
    			String url = "/ordermanage.jsp";
	    		ServletContext sc = getServletContext(); 
				RequestDispatcher rd = sc.getRequestDispatcher(url); 
				rd.forward(request,response);
    		}
    	}
    	else if(action.equals("ordermanage"))
    	{//����鿴/����ʱ
    		String oid = request.getParameter("oid").trim();
    		int id = Integer.parseInt(oid);
    		//�õ������Ļ�����Ϣ
    		String osql = "select Orecname,Orecadr,Orectel,Odate,Ostate,Oid from OrderInfo"+
    							" where Oid="+id;
    		Vector<String[]> vorderinfo = DBcart.getInfoArr(osql);
    		//�õ������л�����Ϣ
    		String ogsql = "select Gname,OGamount,OGtotalprice from GoodsInfo,"+
    							"OrderGoods where GoodsInfo.Gid=OrderGoods.Gid"+
    							" and Oid="+id;
    		Vector<String[]> vordergoods = DBcart.getInfoArr(ogsql);
			request.setAttribute("vorderinfo",vorderinfo);
			request.setAttribute("vordergoods",vordergoods);
			String url = "/ordermodify.jsp";
			ServletContext sc = getServletContext(); 
			RequestDispatcher rd = sc.getRequestDispatcher(url); 
			rd.forward(request,response);
    	}
    	else if(action.equals("orderEnsure"))
    	{//ĳ����ȷ��ʱ
    		String oid = request.getParameter("oid");
    		int id = Integer.parseInt(oid);
    		String aname = (String)session.getAttribute("admin");
    		int aid = DBcart.getSelectId("select Aid from AdminInfo where Aname='"+aname+"'");		
    		String sql = "update OrderInfo set Aid="+aid+",Ostate='�ѷ���' where Oid="+id;
    		String temp = "select Gid,OGamount from OrderGoods where Oid="+id;
    		Vector<String[]> vtemp = DBcart.getInfoArr(temp);
    		String[] str = new String[vtemp.size()+1];
    		for(int i=0;i<vtemp.size();i++)
    		{
    			String[] arr = vtemp.get(i);
    			str[i] = "update GoodsInfo set Gamount=Gamount-"+
    							Integer.parseInt(arr[1])+" where Gid="+arr[0];
    		}
    		str[vtemp.size()] = sql;
    		boolean flag = DBcart.batchSql(str);
    		String msg = "";
    		if(flag)
    		{
    			msg = "��ϲ��,����ȷ���ɹ�!!!";
    		}
    		else
    		{
    			msg = "�Բ���,����ȷ��ʧ��!!!";
    		}
    		pageForward(msg,"/error.jsp",request,response);
    	}
    	else if(action.equals("orderDelete"))
    	{//ɾ��ĳ����ʱ
    		String oid = request.getParameter("oid");
    		int id = Integer.parseInt(oid);
    		String[] str = new String[2];
    		str[0] = "delete from OrderGoods where Oid="+id;
    		str[1] = "delete from OrderInfo where Oid="+id;
    		boolean flag = DBcart.batchSql(str);
    		String msg = "";
    		if(flag)
    		{
    			msg = "��ϲ��,����ɾ���ɹ�!!!";
    		}
    		else
    		{
    			msg = "�Բ���,����ɾ��ʧ��!!!";
    		}
    		pageForward(msg,"/error.jsp",request,response);
    	}
    	else if(action.equals("changePwd"))
    	{//�޸�����ʱ
    		String aname = (String)session.getAttribute("admin");
    		String oldpwd = request.getParameter("oldpwd").trim();
    		String newpwd = request.getParameter("firpwd").trim();
    		String sql = "select Aid from AdminInfo where Aname='"+aname+
    							"' and Apwd='"+oldpwd+"'";
    		sql = new String(sql.getBytes(),"ISO-8859-1");
    		boolean flag = DBcart.isLegal(sql);
    		String msg = "";
    		if(flag)
    		{
    			String temp = "update AdminInfo set Apwd='"+
    								newpwd+"' where Aname='"+aname+"'";
    			temp = new String(temp.getBytes(),"ISO-8859-1");
    			int i = DBcart.updateTable(temp);
    			if(i==1)
    			{
    				msg = "��ϲ��,�����޸ĳɹ�!!!";
    			}
    			else
    			{
    				msg = "�Բ���,�����޸�ʧ��!!!";
    			}
    			pageForward(msg,"/error.jsp",request,response);
    		}
    		else
    		{
    			msg = "�Բ���,��������������!!!";
    			pageForward(msg,"/error.jsp",request,response);
    		}
    	}
    	else if(action.equals("adminManage"))
    	{//����Ա����ʱ
    		String aname = (String)session.getAttribute("admin");
    		String sql = "select Aid from AdminInfo where Aname='"+aname
    							+"' and Alevel='����'";
    		sql = new String(sql.getBytes(),"ISO-8859-1");
    		boolean flag = DBcart.isLegal(sql);
      		if(flag)
    		{
    			session.setAttribute("level","����");
    			response.sendRedirect("adminmanage.jsp");
    		}
    		else
    		{
    			String msg = "�Բ���,��û��Ȩ�������й���!!!";
    			pageForward(msg,"/error.jsp",request,response);
    		}
    	}
    	else if(action.equals("adminAdd"))
    	{//��ӹ���Ա
    		String aname = request.getParameter("aname").trim();
    		String apwd = request.getParameter("apwd").trim();
    		int aid = DBcart.getID("AdminInfo","Aid");
    		String temp = "select Aid from AdminInfo where aname='"+aname+"'";
    		temp = new String(temp.getBytes(),"ISO-8859-1");
    		boolean flag = DBcart.isLegal(temp);
    		String msg = "";
    		if(flag)
    		{
    			msg = "�Բ�����û��Ѿ�����!!!";
    		}
    		else
    		{
	    		String sql = "insert into AdminInfo(Aid,Aname,Apwd,Alevel)"+
	    							" values("+aid+",'"+aname+"','"+apwd+"','��ͨ')";
	    		sql = new String(sql.getBytes(),"ISO-8859-1");
	    		int i = DBcart.updateTable(sql);
	    		if(i==1)
	    		{
	    			msg = "��ϲ��,����Ա��ӳɹ�!!!";
	    		}
	    		else
	    		{
	    			msg = "�Բ���,����Ա���ʧ��!!!";
	    		}    			
    		}
    		pageForward(msg,"/error.jsp",request,response);
    	}
    	else if(action.equals("adminDelete"))
    	{//ɾ������Ա
    		String aid = request.getParameter("aid").trim();
    		int id = Integer.parseInt(aid);
    		String temp = "select Aid from AdminInfo where Aid="+
    										id+" and Alevel='����'";
    		temp = new String(temp.getBytes(),"ISO-8859-1");
    		boolean flag = DBcart.isLegal(temp);
    		String msg = "";
    		if(!flag)
    		{
	    		String sql = "delete from AdminInfo where Aid="+id;
	    		int i = DBcart.updateTable(sql);
	    		if(i==1)
	    		{
	    			msg = "��ϲ��,����Աɾ���ɹ�!!!";
	    		}
	    		else
	    		{
	    			msg = "�Բ���,����Աɾ��ʧ��!!!";
	    		}    			
    		}
    		else
    		{
    			msg = "�Բ���,��������Ա������ɾ��!!!";
    		}

    		pageForward(msg,"/error.jsp",request,response);
    	}
    	else if(action.equals("resetpwd"))
    	{//��������ʱ
    		String aname = request.getParameter("aname").trim();
    		String apwd = request.getParameter("apwd").trim();
    		String temp = "select Aid from AdminInfo where aname='"+aname+"'";
    		temp = new String(temp.getBytes(),"ISO-8859-1");
    		boolean flag = DBcart.isLegal(temp);
    		String msg = "";
    		if(!flag)
    		{
    			msg = "�Բ���,�û����������!!!";
    		}
    		else
    		{
    			String sql = "update AdminInfo set Apwd='"+apwd+"' where aname='"+aname+"'";
    			sql = new String(sql.getBytes(),"ISO-8859-1");
    			int i = DBcart.updateTable(sql);
    			if(i==1)
    			{
    				msg = "��ϲ��,�������óɹ�!!!";
    			}
    			else
    			{
    				msg = "�Բ���,��������ʧ��!!!";
    			}
    		}
    		pageForward(msg,"/error.jsp",request,response);
    	}
    }
    public void pageForward(String msg,String url,HttpServletRequest request,
    					HttpServletResponse response)throws ServletException,IOException
    {
		request.setAttribute("msg",msg);
		ServletContext sc = getServletContext(); 
		RequestDispatcher rd = sc.getRequestDispatcher(url); 
		rd.forward(request,response);   	
    }
}