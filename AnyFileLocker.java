package AnyFileLocker;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
class MyException extends Exception
{

	private static final long serialVersionUID = 1L;

	public MyException(String errorInfo)
	{
		super(errorInfo);
	}
}
public class AnyFileLocker {

	public boolean FileLock(boolean lock,String ifname,String ofname,String password)
	{
		DataInputStream istream=null;
		DataOutputStream ostream=null;
		String Flag= "JI2FL";
		int FlagLen=Flag.length();
		String Upassword=password+"_JI2FFL_V";
		int PassLen=Upassword.length();
		boolean rettype=true;
		try {
			istream=new DataInputStream(new FileInputStream(ifname));
			if(lock==true)
			{
				ostream=new DataOutputStream(new FileOutputStream(ofname));
				ostream.writeBytes(Flag);
				ostream.writeInt(PassLen);
				ostream.writeBytes(Upassword);
				char temp=0;
				int rtp=0;
				int i=0;
				while((rtp=istream.read())!=-1)
				{
					temp=(char)rtp;
					temp^=Upassword.charAt(i);
					i=(i+1)%PassLen;
					ostream.write(temp);
				}
			}
			else
			{
				byte[] rbFlag=new byte[FlagLen];
				istream.read(rbFlag, 0, FlagLen);
				String rFlag=new String(rbFlag);
				if(rFlag.equals(Flag))
				{
					int rPassLen=istream.readInt();
					byte[] rbpass=new byte[rPassLen];
					istream.read(rbpass, 0, rPassLen);
					String rpass=new String(rbpass);
					if(rpass.equals(Upassword))
					{
						ostream=new DataOutputStream(new FileOutputStream(ofname));
						char temp=0;
						int rtp=0;
						int i=0;
						while((rtp=istream.read())!=-1)
						{
							temp=(char)rtp;
							temp^=Upassword.charAt(i);
							i=(i+1)%PassLen;
							ostream.write(temp);
						}
					}
					else
					{
						throw new MyException("Password Is Not True");
						
					} 
				}
				else
				{
					throw new MyException("File Is Not Be Locked");
				}
				
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rettype=false;
		}catch(IOException e)
		{
			e.printStackTrace();
			rettype=false;
		}catch(Exception e)
		{
			e.printStackTrace();
			rettype=false;
		}finally
		{
			if(istream!=null)
			{
				try {
					istream.close();
				}catch(IOException e)
				{
					e.printStackTrace();
					rettype=false;
				}
			}
			if(ostream!=null)
			{
				try {
					ostream.close();
				}catch(IOException e)
				{
					e.printStackTrace();
					rettype=false;
				}
			}
		}
		return rettype;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(args.length==1&&((args[0].charAt(0))=='?' || (args[0].toUpperCase()).equals("HELP")))
		{
			System.out.println("包说明：");
			System.out.println("加解密任意格式文件");
			System.out.println("参数说明：模式 源文件路径 新文件路径 密码");
			System.out.println("模式:Lock UnLock 不区分大小写");
			System.out.println("例如：java -jar AnyFileLocker.jar Lock D:\\test.mp3 D:\\testlock.mp3 mypassword");
			System.out.println("例如：java -jar AnyFileLocker.jar Unock test.exe D:\\testunlock.exe mypassword");
		}
		else if(args.length!=4)
		{
			Scanner jin = new Scanner(System.in);
			System.out.println("请输入加密的文件名称\n>/ ");
			String oldname = jin.next();
			System.out.println("请输入加密后的文件名称\n>/ ");
			String newname = jin.next();
			System.out.println("请输入加密密码\n>/ ");
			String password = jin.next();
			System.out.println("请输入操作类型：0.lock 1.unlock\n>/ ");
			int type = jin.nextInt();
			boolean rtype=false;
			if(type==0)
				rtype=true;
			AnyFileLocker lockfile=new AnyFileLocker();
			if(lockfile.FileLock(rtype,oldname, newname, password)==false)
				System.out.println("发生了一些不该有的错误！！");
			System.out.println("操作结束！！");
			jin.close();
		
		  } else { 
			  AnyFileLocker lockfile=new AnyFileLocker();
			  boolean rtype=false;
				if(args[0].toUpperCase().equals("LOCK"))
					rtype=true;
				else if(args[0].toUpperCase().equals("UNLOCK"))
					rtype=false;
				else
				{
					System.out.println("参数模式不正确！！"+args[0]);
					System.exit(0);
				}
			  if(lockfile.FileLock(rtype,args[1], args[2], args[3])==false)
					System.out.println("发生了一些不该有的错误！！");
			  System.out.println("操作完毕！！" +args[1]+" - "+args[0]+" -> "+args[2]); 
			  }
		 
		
	}

}
