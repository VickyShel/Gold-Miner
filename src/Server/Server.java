package Server;

import java.io.*;
import java.net.*;
import java.util.*;

/*
 * ����������Client����TCP����
 */
public class Server extends Thread{
	ServerSocket welcome;
	Socket socketServer;
	BufferedReader brin;
	DataOutputStream brout;
	Scene jp;
	public long startTime;
	private long time;
	
	/*
	 * �˷������ڳ�ʼ��Server
	 * ����Socket�ӿڲ��õ���Ӧ�����������
	 */
	public Server(Scene j) throws Exception{
		welcome=new ServerSocket(6789);
		socketServer=welcome.accept();
		brin=new BufferedReader(new InputStreamReader(socketServer.getInputStream()));
		brout=new DataOutputStream(socketServer.getOutputStream());
		jp=j;
		String str="";
		produce(str);
	}
	
	/*
	 * ʵʱ���乳�ӵ�״̬��Client�ˣ��Ӷ���֤���˵�ʵʱ��
	 * ����������п�תʱ���ӵ�x��yֵ���¹�ʱ���ӵ�x��yֵ���Ƕȼ��ٶȣ��Ϲ�ʱx��yֵ���Ƕȡ��ٶȼ�����������Ʒ�������������Ƕ�Ӧ�ı��룬����Ϊ��)
	 */
	public void sendhook() throws Exception{
		String str="";
		switch(jp.Hhook1.state_hook) {
		case 0:
			str="# "+jp.Hhook1.state_hook+" "+jp.Hhook1.x+" "+jp.Hhook1.y+" "+'\n';
			brout.writeBytes(str);
			brout.flush();
			break;
		case 1:
			//System.out.println("Server"+jp.Hhook1.zeta);
			str="# "+jp.Hhook1.state_hook+" "+jp.Hhook1.x+" "+jp.Hhook1.y+" "+jp.Hhook1.zeta+" "+" "+jp.Hhook1.hookPos[4]+jp.Hhook1.v+'\n';
			brout.writeBytes(str);
			brout.flush();
			break;
		case 2:
			//System.out.println("Server 2");
			str="# "+jp.Hhook1.state_hook+" "+jp.Hhook1.x+" "+jp.Hhook1.y+" "+jp.Hhook1.zeta+" "+jp.Hhook1.v+" "+jp.Hhook1.hookPos[4]+'\n';
			brout.writeBytes(str);
			brout.flush();
			break;
		}
	}
	
	/*
	 * ������Ҫ�˳�������
	 */
	public void sendexit() throws Exception{
		brout.writeBytes("*"+'\n');
		brout.flush();
	}
	
	/*
	 * ������Ҫ��ʼ��Ϸ������
	 */
	public void sendstart() throws Exception{
		brout.writeBytes("!"+'\n');
		brout.flush();
	}
	
	/*
	 * ������Ҫ��ͣ������
	 */
	public void sendstop() throws Exception{
		brout.writeBytes("("+'\n');
		brout.flush();
	}
	
	/*
	 * ������Ҫ����������
	 */
	public void sendcontinue() throws Exception{
		brout.writeBytes(")"+'\n');
		brout.flush();
	}

	/*
	 * ����Client�˴�������Thing���󣬲���Ӧ�����õ��Լ��Ľ����ϣ��Ӷ�����������ͬͬ��
	 */
	public void produce(String str) {
		try{str=brin.readLine();}catch(Exception e) {}
		jp.amount=Integer.parseInt(str);
		jp.bonus=new Thing[jp.amount];
		jp.is_exist=new boolean[jp.amount];
		for(int i=0;i<jp.amount;i++) {
			jp.is_exist[i]=true;
		}
		jp.pigNum=new int[jp.amount];
		jp.pigCount=0;
		for(int i=0;i<jp.amount;i++) {
			try{str=brin.readLine();}catch(Exception e) {}
			Scanner in=new Scanner(str);
			String s=in.next();
			if(s.equalsIgnoreCase("pig")){
				jp.pigNum[jp.pigCount]=i;
				jp.pigCount++;
			}
			jp.bonus[i]=new Thing(jp,s,in.nextInt(), in.nextInt());
            if(s.equalsIgnoreCase("pocket")) {
				jp.bonus[i].value=in.nextInt();
			}
			in.close();
		}
		try{str=brin.readLine();}catch(Exception e) {}
		jp.iAim=Integer.parseInt(str);
		for(int i=0;i<jp.amount;i++) {
			jp.bonus[i].getLabel();
		}
		jp.jlAim.setText("Ŀ��Ǯ��: "+jp.iAim);
		try{str=brin.readLine();}catch(Exception e) {}
		startTime=Long.parseLong(str);
		try {sendstart();}catch(Exception e) {}
		jp.gameState=0;
	}
	
	/*
	 * ʵ��Server���߳�
	 * whileѭ��������Ϸ���ڽ���״̬��gameState=0),��᲻ͣ�Ľ���Client�˴������Ĺ���״̬����Ϣ��������Ӧ�ķ�Ӧ���Ӷ�����ͬ����
	 */
	public void run() {
		String str="";
		while(true) {
			while(jp.gameState==0) {
				if(jp.hook1flag==1 && jp.hook2flag==1) {
					try {str=brin.readLine();}catch(Exception e) {}
					if(str.charAt(0)=='#') {
						//System.out.println("#");
						Scanner in=new Scanner(str);
						in.next();
						switch(in.nextInt()) {
						case 0:
							if(jp.Hhook2.state_hook!=0) {
								if(jp.Hhook2.hookPos[4]!=-1) {
									//System.out.println("000");
									jp.Hhook2.state_hook=0;
									jp.iMoney+=jp.bonus[jp.Hhook2.hookPos[4]].value;
									jp.jlMoney.setText("�����Ǯ: "+jp.iMoney);
									jp.iMoney2+=jp.bonus[jp.Hhook2.hookPos[4]].value;
									jp.jlMoney_2.setText("$"+jp.iMoney2);
									jp.remove(jp.bonus[jp.Hhook2.hookPos[4]].label);
									jp.is_exist[jp.Hhook2.hookPos[4]]=false;
									jp.Hhook2.hookPos[4]=-1;
									jp.Hhook2.v=6;
									jp.Hhook2.a=true;
									jp.Hhook2.x=in.nextFloat();
									jp.Hhook2.y=in.nextFloat();
								}
								else {
									jp.Hhook2.state_hook=0;
									jp.Hhook2.v=6;
									jp.Hhook2.a=true;
									jp.Hhook2.x=in.nextFloat();
									jp.Hhook2.y=in.nextFloat();
								}
							}
							else {
								jp.Hhook2.x=in.nextFloat();
							    jp.Hhook2.y=in.nextFloat();
							}
							break;
						case 1:
							if(jp.Hhook2.state_hook==0) {
								jp.Hhook2.v=0.006;
								jp.Hhook2.x=in.nextFloat();
							    jp.Hhook2.y=in.nextFloat();
								jp.Hhook2.zeta=in.nextFloat();
								jp.Hhook2.state_hook=1;
							    jp.Hhook2.v=in.nextDouble();
							    jp.Hhook2.a=false;
							}
							else {
								jp.Hhook2.a=true;
								jp.Hhook2.x=in.nextFloat();
							    jp.Hhook2.y=in.nextFloat();
							    jp.Hhook2.zeta=in.nextFloat();
							    jp.Hhook2.v=in.nextDouble();
							}
							break;
						case 2:
							if(jp.Hhook2.state_hook==2) {
								jp.Hhook2.x=in.nextFloat();
							    jp.Hhook2.y=in.nextFloat();
							    jp.Hhook2.zeta=in.nextFloat();
							    jp.Hhook2.v=in.nextDouble();
							    jp.Hhook2.hookPos[4]=in.nextInt();
							    jp.Hhook2.a=false;
							}
							else {
								jp.Hhook2.state_hook=2;
								jp.Hhook2.x=in.nextFloat();
							    jp.Hhook2.y=in.nextFloat();
							    jp.Hhook2.zeta=in.nextFloat();
							    jp.Hhook2.v=in.nextDouble();
							    jp.Hhook2.hookPos[4]=in.nextInt();
							   // if(jp.Hhook2.hookPos[4]!=-1) jp.is_exist[jp.Hhook2.hookPos[4]]=false;
							    jp.Hhook2.a=true;
							}
							break;
						default:break;
						}
						in.close();
					}
					else if(str.charAt(0)=='(') {
						jp.gameState=2;
						jp.remove(jp.jlstop);
						jp.add(jp.jstop);
						jp.repaint();
						while(true) {
							try {str=brin.readLine();}catch(Exception e) {str="$$$";}
							if(str.equalsIgnoreCase(")")) break;
						}
						jp.remove(jp.jstop);
						jp.add(jp.jlstop);
						jp.gameState=0;
					}
					else if(str.charAt(0)=='*') {
						jp.gameState=1;
						try{sendexit();}catch(Exception e2) {}
					}
					else;
				}
				try {sleep(40);}catch(Exception e1) {}
			}
		}
	}
}
