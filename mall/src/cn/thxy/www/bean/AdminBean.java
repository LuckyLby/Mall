package cn.thxy.www.bean;

public class AdminBean
{
	private int curPage = 1;
	private String sql;
	private int totalPage = 1;
	
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
}