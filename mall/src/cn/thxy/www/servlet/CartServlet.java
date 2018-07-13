package cn.thxy.www.servlet;

import javax.servlet.*;
import javax.servlet.http.*;

import cn.thxy.www.bean.CartBean;
import cn.thxy.www.dbcart.DBcart;

import java.io.*;
import java.util.*;

public class CartServlet extends HttpServlet
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
    	{//������Ϊ��¼����ʱ
    		//����û���������
    		String uid = request.getParameter("uid").trim();
    		String pwd = request.getParameter("pwd").trim();
    		String uname = new String(uid.getBytes(),"ISO-8859-1");
    		String sql = "select Uid from UserInfo where Uname='"+uname+"' and Upwd='"+pwd+"'";
    		boolean flag = DBcart.isLegal(sql);
    		if(flag)
    		{//�����û�����ʱ
    			//���û�������session��
    			session.setAttribute("user",uid);
    			//ҳ���ض�����ҳ
    			response.sendRedirect("index.jsp");
    		}
    		else
    		{
    			String msg = "�Բ���,��¼ʧ��,�����µ�¼!!!";
				pageForward(msg,request,response);
				
    		}
    	}
    	else if(action.equals("register"))
    	{//�û�ע��
    		String uname = request.getParameter("uname").trim();
    		String fpwd = request.getParameter("fpwd").trim();
    		String email = request.getParameter("email").trim();
    		String sql = "select Uid from UserInfo where Uname='"+uname+"'";
    		boolean flag = DBcart.isLegal(sql);
    		if(flag)
    		{//���û����Ѿ���ע��ʱ
    			String msg = "�Բ���,���û����Ѿ�����,������ע��!!!";
				pageForward(msg,request,response);
    		}
    		else
    		{
				int uid = DBcart.getID("UserInfo","Uid");
				//������gb2312ת��ΪISO-8859-1
				uname = new String(uname.getBytes(),"ISO-8859-1");
				fpwd = new String(fpwd.getBytes(),"ISO-8859-1");
				//�����û�ע����Ϣ
				String temp = "insert into UserInfo(Uid,Uname,Upwd,Uemail) "+
						"values("+uid+",'"+uname+"','"+fpwd+"','"+email+"')";
				int i = DBcart.updateTable(temp);
				if(i==0)
				{//û�в������ݿ�
	    			String msg = "�Բ���,ע��ʧ��,������ע��!!!";
					pageForward(msg,request,response);					
				}
				else
				{
					String msg = "��ϲ��,ע��ɹ�!!!";
					pageForward(msg,request,response);
				}
    		}
    		
    	}
    	else if(action.equals("uinfomodify"))
    	{//�û��޸ĸ�����Ϣ
    		//�õ��û��޸ĺ����Ϣ
    		String upwd = request.getParameter("upwd").trim();
    		String uemail = request.getParameter("uemail").trim();
    		String uname = (String)session.getAttribute("user");
    		//������ת��
    		uname = new String(uname.getBytes(),"ISO-8859-1");
    		//�������ݿ��û���Ϣ
    		String sql = "update UserInfo set upwd='"+upwd
    					+"',uemail='"+uemail+"' where uname='"+uname+"'";
    		int i = DBcart.updateTable(sql);
    		if(i==0)
    		{//������Ϣʧ��
    			String msg = "�Բ���,��Ϣ�޸�ʧ��!!!";
    			pageForward(msg,request,response);	
    		}
    		else
    		{//��Ϣ�޸ĳɹ�
    			String msg = "��ϲ��,��Ϣ�޸ĳɹ�!!!";
    			pageForward(msg,request,response);    			
    		}
    	}
    	else if(action.equals("pageChange"))
    	{//�û���ҳʱ
    		CartBean mycart = (CartBean)session.getAttribute("mycart");
	    	if(mycart==null)
	    	{
	    		//mycart = new CartBean();
	    	}
	    	//�õ������ҳ��
    		String curPage = request.getParameter("curPage");
    		String selPage = request.getParameter("selPage");
    		if(curPage!=null)
    		{//�û�ͨ�������,��һҳ��ťʱ
    			int page = Integer.parseInt(curPage.trim());
    			//��ס��ǰҳ
    			mycart.setCurPage(page);
    		}
    		else
    		{//���û���������б��ʱ
    			int page = Integer.parseInt(selPage.trim());
    			mycart.setCurPage(page);
    		}
    		String sql = mycart.getSql();    		
    		//�õ���ҳ��ҳ�������
    		Vector<String[]> vgoods = DBcart.getPageContent(mycart.getCurPage(),sql);
			request.setAttribute("vgoods",vgoods);
    		session.setAttribute("mycart",mycart);
			//���ص���ҳ
			String url = "/index.jsp";
    		ServletContext sc = getServletContext(); 
			RequestDispatcher rd = sc.getRequestDispatcher(url); 
			rd.forward(request,response);
    	}
    	else if(action.equals("search"))
    	{//�û�������Ʒʱ
    		//�õ�javaBean����
    		CartBean mycart = (CartBean)session.getAttribute("mycart");
    		if(mycart==null)
    		{
    			//mycart = new CartBean();
    		}
    		mycart.setCurPage(1);
    		//�õ�Ҫ��������Ϣ��ת��
    		String tsearch = request.getParameter("tsearch");
    		//�õ�Ҫ����������
    		String cname = request.getParameter("cname");
    		String sql = "";
    		String sqlpage = "";
    		if(cname==null)
    		{
	    		tsearch = new String(tsearch.trim().getBytes(),"ISO-8859-1");
	    		//�õ�������Ϣ��sql����Ϣ������sql
	    		sql = "select Gimgurl,Gname,Gintro,Gclass,Gprice,Glook,Gid "+
	    						"from GoodsInfo where Gname like '%"+tsearch+"%'";
	    		sqlpage = "select count(*) from GoodsInfo "+
	    									"where Gname like '%"+tsearch+"%'";    			
    		}
    		else
    		{
    			//cname = new String(cname.getBytes(),"ISO-8859-1");
    			//�õ�����ĳ����Ʒ��sql��������sql
	    		sql = "select Gimgurl,Gname,Gintro,Gclass,Gprice,Glook,Gid "+
	    						"from GoodsInfo where Gclass='"+cname.trim()+"'";
	    		//�õ��������ҳ��
	    		sqlpage = "select count(*) from GoodsInfo where Gclass='"+cname.trim()+"'";
    		}
    		mycart.setSql(sql);
    		//������ҳ��
    		int totalpage = DBcart.getTotalPage(sqlpage);
    		mycart.setTotalPage(totalpage);
    		session.setAttribute("mycart",mycart);
    		//�õ���һҳ������
    		Vector<String[]> vgoods = DBcart.getPageContent(1,sql);
    		if(vgoods.size()==0)
    		{//û���������û�Ҫ�ҵ���Ʒ
    			String msg = "�Բ���,û���ѵ���Ҫ����Ʒ!!!";
    			pageForward(msg,request,response);
    		}
    		else
    		{//��������Ϣ������
    			request.setAttribute("vgoods",vgoods);
    			String url = "/index.jsp";
				ServletContext sc = getServletContext(); 
				RequestDispatcher rd = sc.getRequestDispatcher(url); 
				rd.forward(request,response);  
    		}
    	}
    	else if(action.equals("buy"))
    	{//�û��������ʱ
    		CartBean mycart = (CartBean)session.getAttribute("mycart");
	    	if(mycart==null)
	    	{
	    		mycart = new CartBean();
	    	}
	    	//�õ�Ҫ��������ID
    		String gid = request.getParameter("gid").trim();
    		//�ж������Ƕ���Ĺ���,0-������ͼ����,1-����ϸ��Ϣ�����
    		String flag = request.getParameter("flag").trim();
    		mycart.buy(gid);
    		//�õ�������ǰ���ݵ�sql
    		String sql = mycart.getSql();
    		int page = mycart.getCurPage();
    		session.setAttribute("mycart",mycart);
			String url = "";
			if(flag.equals("0"))
			{
				url = "/index.jsp";
			}
			else
			{
				url = "/goodsdetail.jsp";
				sql = "select Gimgurl,Gname,Gintro,Gclass,Gprice,"+
    						"Glook,Gid,Gbrief from GoodsInfo where Gid="+gid;
    			page = 1;
			}
    		//���غ�,�õ�ҳ������
    		Vector<String[]> vgoods = DBcart.getPageContent(page,sql);
    		
    		
    		
    		if(vgoods.size()==0)
    		{//û���������û�Ҫ�ҵ���Ʒ
    			String msg = "�Բ���,û���ѵ���Ҫ����Ʒ11111111111111111!!!";
    			pageForward(msg,request,response);
    		}
    		
    		
    		
			request.setAttribute("vgoods",vgoods);
			//forward��Ҫȥ��ҳ��
    		ServletContext sc = getServletContext(); 
			RequestDispatcher rd = sc.getRequestDispatcher(url); 
			rd.forward(request,response);
    	}
    	else if(action.equals("changeNum"))
    	{//�û��޸Ĺ��ﳵ����Ʒ����ʱ
    		//�õ��޸���Ʒ��ID���޸ĺ������
    		String gnum = request.getParameter("gnum").trim();
    		String gid = request.getParameter("gid").trim();
    		int num = 0;
    		try
    		{
    			num = Integer.parseInt(gnum);
    		}
    		catch(Exception e)
    		{
    			//�޸ĵ��������Ϸ�ʱ
    			String msg = "�Բ���,�����޸Ĵ���!!!";
    			pageForward(msg,request,response); 
    		}
    		int id = Integer.parseInt(gid);
    		//�õ��������
    		String sql = "select Gamount from GoodsInfo where Gid="+id;
			int count = DBcart.getSelectId(sql);
    		if(count<num)
    		{//����������޸ĵ�ֵʱ
    			String msg = "�Բ��𣬿�治�����������ֻ�� "+count;
    			pageForward(msg,request,response);
    		}
    		else
    		{//����湻ʱ
	    		CartBean mycart = (CartBean)session.getAttribute("mycart");
	    		if(mycart==null)
	    		{
	    			mycart = new CartBean();
	    		}
	    		//�õ��û��Ĺ��ﳵ
	    		Map<String,Integer> cart = mycart.getCart();
	    		//�޸���Ʒ����
	    		cart.put(gid,num);
	    		session.setAttribute("mycart",mycart);
	    		response.sendRedirect("cart.jsp");    			
    		}
    	}
    	else if(action.equals("balance"))
    	{//���������ʱ���жϸ���Ʒ�����Ƿ�
    		CartBean mycart = (CartBean)session.getAttribute("mycart");
    		if(mycart==null)
    		{
    			mycart = new CartBean();
    		}
    		//�õ��û��Ĺ��ﳵ
    		Map<String,Integer> cart = mycart.getCart();
    		Set<String> gid = cart.keySet();
    		String msg = "";
    		for(String str:gid)
    		{
    			//�õ���ƷID������
    			int id = Integer.parseInt(str);
    			int count = cart.get(str);
    			//�õ��������Ʒ������
	    		String sql = "select Gamount from GoodsInfo where Gid="+id;
				int gamount = DBcart.getSelectId(sql);
    			if(gamount<count)
    			{
    				//�õ�����Ʒ������
    				String temp = "select Gname from GoodsInfo where Gid="+id;
    				Vector<String> vname = DBcart.getInfo(temp);
    				String name = vname.get(0);
    				msg += "�Բ���"+name+"�Ŀ��ֻ��"+gamount+"<br/>";
    			}
    		}
    		if(msg.equals(""))
    		{//��msgΪ��ʱ�������湻
    			response.sendRedirect("receiverinfo.jsp");
    		}
    		else
    		{//��ʾ�û���治��
    			pageForward(msg,request,response);
    		}
    	}
    	else if(action.equals("delete"))
    	{//�û�ɾ�����ﳵ�е���Ʒʱ
    		//�õ�ɾ����Ʒ��ID
    		String gid = request.getParameter("gid").trim();
    		//�õ�javaBean����
    		CartBean mycart = (CartBean)session.getAttribute("mycart");
    		if(mycart==null)
    		{
    			mycart  = new CartBean();
    		}
    		mycart.removeItem(gid);
    		session.setAttribute("mycart",mycart);
    		response.sendRedirect("cart.jsp");
    	}
    	else if(action.equals("saveRec"))
    	{//�����ջ�����Ϣ����session
    		//�յ�������
    		String recname = request.getParameter("recname");
    		String recadr = request.getParameter("recadr");
    		String rectel = request.getParameter("rectel");
    		String[] recMsg = new String[3];
    		recMsg[0] = recname.trim();
    		recMsg[1] = recadr.trim();
    		recMsg[2] = rectel.trim();
    		//����session���ض��򵽶���ҳ
    		session.setAttribute("recMsg",recMsg);
    		response.sendRedirect("order.jsp");
    	}
    	else if(action.equals("recModify"))
    	{//�û��޸��ջ�����Ϣʱ
    		String recname = request.getParameter("recname").trim();
    		String recadr = request.getParameter("recadr").trim();
    		String rectel = request.getParameter("rectel").trim();
    		String[] recMsg = (String[])session.getAttribute("recMsg");
    		//���ջ�����ϢΪ��ʱ
    		if(recMsg==null)
    		{
    			//�ض�����ҳ
    			response.sendRedirect("index.jsp");
    		}
    		else
    		{
    			//�޸�session�����ջ��˵���Ϣ
	    		recMsg[0] = recname;
	    		recMsg[1] = recadr;
	    		recMsg[2] = rectel;
	    		session.setAttribute("recMsg",recMsg);
	    		response.sendRedirect("order.jsp");    			
    		}

    	}
    	else if(action.equals("orderConfirm"))
    	{//���û�ȷ�϶�������ʱ
       		CartBean mycart = (CartBean)session.getAttribute("mycart");
       		//�ö���Ϊ��,�򷵻���ҳ
	    	if(mycart==null)
	    	{
    			response.sendRedirect("index.jsp");
	    	}
	    	else
	    	{
		    	//�õ��򶩵�������Ϣ���в������ݵ�sql
	    		String[] recMsg = (String[])session.getAttribute("recMsg");
	    		double oprice = mycart.getAccount();
	    		int oid = DBcart.getID("OrderInfo","Oid");
	    		String uname = (String)session.getAttribute("user");
	    		String sql = "select Uid from UserInfo where Uname='"+uname+"'";
	    		int uid = DBcart.getSelectId(sql);
	    		String upsql = "insert into OrderInfo(Oid,Odate,Ostate,Orecname,"+
	    					"Orecadr,Orectel,Uid,Ototalprice) values("+oid+
	    					",now(),'δ����','"+recMsg[0]+"','"+recMsg[1]+"','"+
	    					recMsg[2]+"',"+uid+","+oprice+")";			
	    		//�õ��򶩵�������в������ݵ�sql
	    		Vector<String[]> vgoods = mycart.getCartContent();
	    		int ogid = DBcart.getID("OrderGoods","OGid");    		
	    		String[] sqlarr = new String[vgoods.size()+1];
	    		for(int i=0;i<vgoods.size();i++)
	    		{
	    			String[] ginfo = vgoods.get(i);
	    			int gid = Integer.parseInt(ginfo[3]);
	    			int gamount = Integer.parseInt(ginfo[2]);
	    			double gprice = Double.parseDouble(ginfo[1]);
	    			double totalprice = gprice*gamount;
	    			String temp = "insert into OrderGoods(OGid,Oid,Uid,Gid,OGamount,"+
	    						"OGtotalprice) values("+ogid+","+oid+","+uid+","+gid+
	    						","+gamount+","+totalprice+")";
	    			sqlarr[i] = temp;
	    			ogid++;
	    		}
	    		sqlarr[vgoods.size()] = upsql;
	    		//ִ�и�����
	    		boolean flag = DBcart.batchSql(sqlarr);
	    		String msg = "";
	    		if(!flag)
	    		{
	    			msg = "�Բ���,�����ύʧ��";
	    		}
	    		else
	    		{
	    			msg = "��ϲ��,�����ύ�ɹ�";
	    		}
	    		//���ջ�����Ϣ��javaBean������Ϊ��
	    		session.setAttribute("recMsg",null);
	    		session.setAttribute("mycart",null);
	    		pageForward(msg,request,response);
	    	}
    	}
    	else if(action.equals("logout"))
    	{//���û�ע����¼ʱ
    		//��sessionʧЧ
    		request.getSession(true).invalidate();
    		response.sendRedirect("index.jsp");
    	}
    	else if(action.equals("getDetail"))
    	{//�û�����õ�ĳ��Ʒ����ϸ��Ϣʱ
	    	//�õ���ƷID
    		String gid = request.getParameter("gid").trim();
    		String sql = "select Gimgurl,Gname,Gintro,Gclass,Gprice,"+
    						"Glook,Gid,Gbrief from GoodsInfo where Gid="+gid;
    		//���±��е������
    		String updatesql = "update GoodsInfo set Glook=Glook+1 where Gid="+gid;
    		DBcart.updateTable(updatesql);
    		//�õ�����Ʒ����ϸ��Ϣ
    		Vector<String[]> vgoods = DBcart.getPageContent(1,sql);
			request.setAttribute("vgoods",vgoods);
			ServletContext sc = getServletContext(); 
			RequestDispatcher rd = sc.getRequestDispatcher("/goodsdetail.jsp"); 
			rd.forward(request,response);
    	}
    }
    
    public void pageForward(String msg,HttpServletRequest request,HttpServletResponse response)
    							throws ServletException,IOException
    {
		request.setAttribute("msg",msg);
		String url = "/error.jsp";
		ServletContext sc = getServletContext(); 
		RequestDispatcher rd = sc.getRequestDispatcher(url); 
		rd.forward(request,response);   	
    }
}