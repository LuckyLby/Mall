package cn.thxy.www.dbcart;

import java.sql.*;
import java.util.*;
import javax.sql.*;
import javax.naming.*;

public class DBcart
{
	//����ÿҳ��ʾ��Ʒ������
	private static int span=2;
	
	public static int getSpan()
	{
		return span;
	}
	
	public static void setSpan(int i)
	{
		span = i;
	}
	
	public static Vector<String> getInfo(String sql)
	{
		Vector<String> vclass = new Vector<String>();
		try
		{
			//��ʼ��������
			Context initial = new InitialContext();    
            //����mysqlΪ����Դjndi����      	
            DataSource ds = (DataSource)initial.lookup("java:comp/env/jdbc/cartds");
            //�õ�����
            Connection con=ds.getConnection();
            //�������
            Statement st = con.createStatement();
            //ִ�����õ������
            ResultSet rs = st.executeQuery(sql);
            while(rs.next())
            {
            	String str = rs.getString(1);
            	str = new String(str.getBytes("ISO-8859-1"),"gb2312");
            	vclass.add(str);
            }
            //�رս����,���
            rs.close();
            st.close();
            //�黹����
            con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return vclass;
	}
	
	public static boolean isLegal(String sql)
	{
		boolean flag = false;
		try
		{
			//��ʼ��������
			Context initial = new InitialContext();    
            //����mysqlΪ����Դjndi����      	
            DataSource ds = (DataSource)initial.lookup("java:comp/env/jdbc/cartds");
            //�õ�����
            Connection con=ds.getConnection();
            //�������
            Statement st = con.createStatement();
            //ִ�����õ������
            ResultSet rs = st.executeQuery(sql);
            if(rs.next())
            {
            	flag = true;
            }			
            rs.close();
            st.close();
            con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}
	
	public static int getID(String tname,String colname)
	{
		int id = 0;
		try
		{
			//��ʼ��������
			Context initial = new InitialContext();    
            //����mysqlΪ����Դjndi����      	
            DataSource ds = (DataSource)initial.lookup("java:comp/env/jdbc/cartds");
            //�õ�����
            Connection con=ds.getConnection();
            //�������
            Statement st = con.createStatement();
            String sql = "select Max("+colname+") from "+tname;
            //ִ�����õ������
            ResultSet rs = st.executeQuery(sql);
            if(rs.next())
            {
            	id = rs.getInt(1);
            }
            id++;
            rs.close();
            st.close();
            con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return id;
	}
	
	public static int updateTable(String sql)
	{
		int i = 0;
		try
		{
			//��ʼ��������
			Context initial = new InitialContext();    
            //����mysqlΪ����Դjndi����      	
            DataSource ds = (DataSource)initial.lookup("java:comp/env/jdbc/cartds");
            //�õ�����
            Connection con=ds.getConnection();
            //�������
            Statement st = con.createStatement();
            //���±�
            i = st.executeUpdate(sql);
            //�ر����
            st.close();
            //�黹����
            con.close();		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return i;
	}
	
	
	public static Vector<String[]> getInfoArr(String sql)
	{
		Vector<String[]> vtemp = new Vector<String[]>();
		try
		{
			//��ʼ��������
			Context initial = new InitialContext();    
            //����mysqlΪ����Դjndi����      	
            DataSource ds = (DataSource)initial.lookup("java:comp/env/jdbc/cartds");
            //�õ�����
            Connection con=ds.getConnection();
            //�������
            Statement st = con.createStatement();
            //ִ�����õ������
            ResultSet rs = st.executeQuery(sql);
            //��ȡ�������Ԫ����
            ResultSetMetaData rsmt = rs.getMetaData();
            //�õ�������е�������
            int count = rsmt.getColumnCount();
            while(rs.next())
            {
            	String[] str = new String[count];
            	for(int i=0;i<count;i++)
            	{
            		str[i] = rs.getString(i+1);
            		str[i] = new String(str[i].getBytes("ISO-8859-1"),"gb2312");
            	}
            	vtemp.add(str);
            }
            rs.close();
            st.close();
            con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return vtemp;
	}

	public static int getTotalPage(String sql)
	{
		int totalpage = 1;
		try
		{
			//��ʼ��������
			Context initial = new InitialContext();    
            //����mysqlΪ����Դjndi����      	
            DataSource ds = (DataSource)initial.lookup("java:comp/env/jdbc/cartds");
            //�õ�����
            Connection con=ds.getConnection();
            //�������
            Statement st = con.createStatement();
            //ִ�����õ������
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            //�õ��ܼ�¼����
            int rows = rs.getInt(1);
            totalpage = rows/span;
            if(rows%span!=0)
            {
            	totalpage++;
            }
            //�رս����,���
            rs.close();
            st.close();
            //�黹����
            con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return totalpage;
	}
	
	public static Vector<String[]> getPageContent(int page,String sql)
	{
		Vector<String[]> vcon = new Vector<String[]>();
		try
		{
			//��ʼ��������
			Context initial = new InitialContext();    
            //����mysqlΪ����Դjndi����      	
            DataSource ds = (DataSource)initial.lookup("java:comp/env/jdbc/cartds");
            //�õ�����
            Connection con=ds.getConnection();
            //�������
            Statement st = con.createStatement();
			//ִ�����õ������
            ResultSet rs = st.executeQuery(sql);
            //��ȡ�������Ԫ����
            ResultSetMetaData rsmt = rs.getMetaData();
            //�õ�������е�������
            int count = rsmt.getColumnCount();
            int start = (page-1)*span;
            if(start!=0)
            {
            	rs.absolute(start);
            }
			int temp=0;
            while(rs.next()&&temp<span)
            {
            	temp++;
            	String[] str = new String[count];
            	for(int i=0;i<str.length;i++)
            	{
            		str[i] = rs.getString(i+1);
            		//ת��
            		str[i] = new String(str[i].getBytes("ISO-8859-1"),"gb2312");
            	}
            	vcon.add(str);
            }
            //�رս����,���
            rs.close();
            st.close();
            con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return vcon;
	}
	
	public static int getSelectId(String sql)
	{
		int id = 0;
		try
		{
			//��ʼ��������
			Context initial = new InitialContext();    
            //����mysqlΪ����Դjndi����      	
            DataSource ds = (DataSource)initial.lookup("java:comp/env/jdbc/cartds");
            //�õ�����
            Connection con=ds.getConnection();
            //�������
            Statement st = con.createStatement();
			sql = new String(sql.getBytes(),"ISO-8859-1");
			//ִ�����õ������
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            id = rs.getInt(1);
            rs.close();
            st.close();
            con.close();			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return id;
	}
	
	public static boolean batchSql(String[] sql)
	{
		boolean flag = true;
		Connection con = null;
		try
		{
			//��ʼ��������
			Context initial = new InitialContext();    
            //����mysqlΪ����Դjndi����      	
            DataSource ds = (DataSource)initial.lookup("java:comp/env/jdbc/cartds");
            //�õ�����
            con=ds.getConnection();
            //�����Զ��ύģʽ,����ʼһ������
            con.setAutoCommit(false);
            //�������
            Statement st = con.createStatement();
            for(String str:sql)
            {
            	str = new String(str.getBytes(),"ISO-8859-1");
            	//����������е�sql
            	st.addBatch(str);
            }
            //ִ��������
            st.executeBatch();
            //�������ύ
            con.commit();
            //�ָ��Զ��ύģʽ
            con.setAutoCommit(true);
            st.close();
		}
		catch(Exception e)
		{
			flag = false;
			try
			{
				//����ع�
				con.rollback();
			}
			catch(Exception ee)
			{
				ee.printStackTrace();
			}
		}
		finally
		{
			try
			{
				con.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return flag;
	}
}