package cn.thxy.www.bean;

import java.util.*;

import cn.thxy.www.dbcart.DBcart;

public class CartBean
{
	private Map<String,Integer> cart = new HashMap<String,Integer>();
	private int curPage = 1;
	private String sql;
	private int totalPage = 1;
	
	public Map<String,Integer> getCart()
	{
		return this.cart;
	}
	
	public int getCurPage()
	{//�õ��û���ǰҳ
		return this.curPage;
	}
	
	public void setCurPage(int curPage)
	{//��¼�û���ǰҳ
		this.curPage = curPage;
	}
	
	public void setSql(String sql)
	{
		this.sql = sql;
	}
	
	public String getSql()
	{
		return this.sql;
	}

	public int getTotalPage()
	{
		return this.totalPage;
	}
	
	public void setTotalPage(int totalPage)
	{
		this.totalPage = totalPage;
	}
	
	public void buy(String sid)
	{
		if(cart.containsKey(sid))
		{//�û����ǵ�һ�ι�����Ʒ
			//������Ʒ������1
			cart.put(sid,cart.get(sid)+1);
		}
		else
		{//�û���һ�ι���
			cart.put(sid,1);
		}
	}
	
	public String manageStr(String str)
	{//�õ���Ʒ����Ϣ˵��
		String info = "";
		String[] msg = str.split("\\|");
		for(String temp:msg)
		{
			info = info+temp+"   ";
		}
		return info;
	}
	
	public Vector<String[]> getCartContent()
	{
		Vector<String[]> vgoods = new Vector<String[]>();
		//�õ�Map�еļ�ֵ
		Set<String> gid = cart.keySet();
		//�õ�����Ʒ����Ϣ
		for(String str:gid)
		{
			String[] arr = new String[4];
			arr[3] = str;
			//�õ���Ʒ����
			arr[2] = cart.get(str).toString();
			//�õ���Ʒ���ƺͼ۸�
			String sql = "select Gname,Gprice from GoodsInfo where Gid="+Integer.parseInt(str);
			Vector<String[]> vtemp = DBcart.getInfoArr(sql);
			String[] ginfo = vtemp.get(0);
			arr[0] = ginfo[0];
			arr[1] = ginfo[1];
			vgoods.add(arr);
		}
		return vgoods;
	}
	
	public double getAccount()
	{
		double account = 0.0;
		//�õ�������Ʒ����Ϣ
		Vector<String[]> ginfo = this.getCartContent();
		for(String[] str:ginfo)
		{//�õ���Ʒ�ܼ�
			account += Integer.parseInt(str[2])*Double.parseDouble(str[1]);
		}
		//ʹ��Ʒ������λС��
		account=Math.round(account*100)/100.0;
		return account;
	}
	
	public void removeItem(String gid)
	{
		cart.remove(gid);
	}
}