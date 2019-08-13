package FileLocker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import com.ice2faith.service.impl.myFileLock;

public class FileLocker {

	public void LockFile(String ifilename,String ofilename,String Password) {
		// TODO Auto-generated method stub
		File ifile=new File(ifilename);	
		File ofile=new File(ofilename);
		try {
			ifile.createNewFile();	//创建文件
			ofile.createNewFile();
			InputStreamReader istream=new InputStreamReader(new FileInputStream(ifile));	//利用文件对象建立输入流
			BufferedWriter ostream=new BufferedWriter(new FileWriter(ofile)); 	//利用文件对象创建输出流
			
			BufferedReader filein=new BufferedReader(istream);	//将输入流转换为可识别字符串
			
			int PassLen=Password.length();
			char temp=0;
			int rtp=0;
			int i=0;
			while((rtp=filein.read())!=-1)
			{
				temp=(char)rtp;
				temp^=Password.charAt(i);
				i=(i+1)%PassLen;
				ostream.write(temp);
			}

			filein.close();
			ostream.flush();	//保存到文件
			ostream.close();	//关闭流
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		myFileLock lockfile=new myFileLock();
		if(args.length==1)
		{
			System.out.println("包说明：");
			System.out.println("加解密：同样的密码执行一次完成加密，再执行一次解密");
			System.out.println("例如：D:\\01.txt->密码1->D:\\01.loc->密码1->D:\\01.txt");
			System.out.println("意思：还原源文件内容，注意和文件名无关");
			System.out.println("参数说明：源文件路径 新文件路径 密码");
			System.out.println("例如：D:\\test.mp3 D:\\testlock.mp3 mypassword");
		}
		else
		if(args.length>=3)
		{
			lockfile.LockFile(args[0], args[1], args[2]);
			System.out.println("文件操作完毕！！" +args[0]+" --> "+args[1]);
		}
		else
		{
			Scanner jin = new Scanner(System.in);
			System.out.println("请输入加密的文件名称\n>/ ");
			String oldname = jin.next();
			System.out.println("请输入加密后的文件名称\n>/ ");
			String newname = jin.next();
			System.out.println("请输入加密密码\n>/ ");
			String password = jin.next();
			lockfile.LockFile(oldname, newname, password);
			System.out.println("文件加密结束！！");
			jin.close();
		}
		
	}
}
