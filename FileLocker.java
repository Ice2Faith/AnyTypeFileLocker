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
			ifile.createNewFile();	//�����ļ�
			ofile.createNewFile();
			InputStreamReader istream=new InputStreamReader(new FileInputStream(ifile));	//�����ļ�������������
			BufferedWriter ostream=new BufferedWriter(new FileWriter(ofile)); 	//�����ļ����󴴽������
			
			BufferedReader filein=new BufferedReader(istream);	//��������ת��Ϊ��ʶ���ַ���
			
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
			ostream.flush();	//���浽�ļ�
			ostream.close();	//�ر���
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
			System.out.println("��˵����");
			System.out.println("�ӽ��ܣ�ͬ��������ִ��һ����ɼ��ܣ���ִ��һ�ν���");
			System.out.println("���磺D:\\01.txt->����1->D:\\01.loc->����1->D:\\01.txt");
			System.out.println("��˼����ԭԴ�ļ����ݣ�ע����ļ����޹�");
			System.out.println("����˵����Դ�ļ�·�� ���ļ�·�� ����");
			System.out.println("���磺D:\\test.mp3 D:\\testlock.mp3 mypassword");
		}
		else
		if(args.length>=3)
		{
			lockfile.LockFile(args[0], args[1], args[2]);
			System.out.println("�ļ�������ϣ���" +args[0]+" --> "+args[1]);
		}
		else
		{
			Scanner jin = new Scanner(System.in);
			System.out.println("��������ܵ��ļ�����\n>/ ");
			String oldname = jin.next();
			System.out.println("��������ܺ���ļ�����\n>/ ");
			String newname = jin.next();
			System.out.println("�������������\n>/ ");
			String password = jin.next();
			lockfile.LockFile(oldname, newname, password);
			System.out.println("�ļ����ܽ�������");
			jin.close();
		}
		
	}
}
