package Client;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Date;

/*
 * ����������Server����TCP����
 */
public class Client extends Thread{
	Scene jp;
	Socket socketClient;
	BufferedReader brin;
	DataOutputStream brout;
	public long startTime;
	boolean flag=true;
	
	/*
	 * �˷������ڳ�ʼ��Client
	 * ����Socket�ӿڲ��õ���Ӧ�����������
	 */
	public Client(Scene j) throws Exception{
		socketClient=new Socket("localhost",6789);
		brin=new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
		brout=new DataOutputStream(socketClient.getOutputStream());
		jp=j;
	}
	
	/*
	 * ���ڽ�Client���������ɵ�����Thing����ͨ��socket�����Server��
	 * ����Thing���󣬻���Thing����Ŀ�Լ�Ŀ���Ǯ����ʼʱ��������ȥ
	 */
	public void sendlayout() throws Exception{
		String str=Integer.toString(jp.amount)+'\n';
		brout.writeBytes(str);
		for(int i=0;i<jp.amount;i++) {
			str=jp.bonus[i].name+" "+jp.bonus[i].x+" "+jp.bonus[i].y+'\n';
			if(jp.bonus[i].name.equalsIgnoreCase("pocket"))
				str=jp.bonus[i].name+" "+jp.bonus[i].x+" "+jp.bonus[i].y+" "+jp.bonus[i].value+'\n';
			brout.writeBytes(str);
		}
		str=Integer.toString(jp.iAim)+'\n';
		brout.writeBytes(str);
		str=Long.toString(startTime)+'\n';
		brout.writeBytes(str);
		brout.flush();
	}
	
	/*
	 * ʵʱ���乳�ӵ�״̬��Server�ˣ��Ӷ���֤���˵�ʵʱ��
	 * ����������п�תʱ���ӵ�x��yֵ���¹�ʱ���ӵ�x��yֵ���Ƕȼ��ٶȣ��Ϲ�ʱx��yֵ���Ƕȡ��ٶȼ�����������Ʒ�������������Ƕ�Ӧ�ı��룬����Ϊ��)
	 */
	public void sendhook() throws Exception{
		String str="";
		switch(jp.Hhook2.state_hook) {
		case 0:
			str="# "+jp.Hhook2.state_hook+" "+jp.Hhook2.x+" "+jp.Hhook2.y+" "+'\n';
			brout.writeBytes(str);
			brout.flush();
			break;
		case 1:
			str="# "+jp.Hhook2.state_hook+" "+jp.Hhook2.x+" "+jp.Hhook2.y+" "+jp.Hhook2.zeta+" "+jp.Hhook2.v+'\n';
			brout.writeBytes(str);
			brout.flush();
			break;
		case 2:
			str="# "+jp.Hhook2.state_hook+" "+jp.Hhook2.x+" "+jp.Hhook2.y+" "+jp.Hhook2.zeta+" "+jp.Hhook2.v+" "+jp.Hhook2.hookPos[4]+'\n';
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
	 * ʵ��Client���߳�
	 * ��ʼʱ�������һ����ʼʱ�䣬���ȴ�Server�˴�����׼���õ���ϢȻ�����ѭ������
	 * whileѭ��������Ϸ���ڽ���״̬��gameState=0),��᲻ͣ�Ľ���Server�˴������Ĺ���״̬����Ϣ��������Ӧ�ķ�Ӧ���Ӷ�����ͬ����
	 */
	public void run(){
		startTime=new Date().getTime();
		String str="";
		while(jp.gameState!=0) {
			try {str=brin.readLine();} catch(Exception e) {}
			if(str.equals("!")) {
				jp.gameState=0;
				break;
			}                                          
		}
		while(true) {
			while(jp.gameState==0) {
				if(jp.hook1flag==1 && jp.hook2flag==1) {
					try {str=brin.readLine();}catch(Exception e) {}
					if(str.charAt(0)=='#') {
						Scanner in=new Scanner(str);
						in.next();
						switch(in.nextInt()) {
						case 0:
							if(jp.Hhook1.state_hook!=0) {                                      //����Է�������Ҫֹͣ����Ϣ����Ὣ�������Ķ���������ز���
								if(jp.Hhook1.hookPos[4]!=-1) {                                
									jp.Hhook1.state_hook=0;
									jp.iMoney+=jp.bonus[jp.Hhook1.hookPos[4]].value;           //����������˶���������ö�Ӧ����ֵ���ڽ������޸�
									jp.jlMoney.setText("�����Ǯ: "+jp.iMoney);
									jp.iMoney1+=jp.bonus[jp.Hhook1.hookPos[4]].value;
									jp.jlMoney_1.setText("$"+jp.iMoney1);
									jp.remove(jp.bonus[jp.Hhook1.hookPos[4]].label);
									jp.is_exist[jp.Hhook1.hookPos[4]]=false;
									jp.Hhook1.hookPos[4]=-1;                                   
									jp.Hhook1.v=6;
									jp.Hhook1.a=true;
									jp.Hhook1.x=in.nextFloat();
									jp.Hhook1.y=in.nextFloat();
								}
								else {
									jp.Hhook1.state_hook=0;
									jp.Hhook1.v=6;
									jp.Hhook1.a=true;
									jp.Hhook1.x=in.nextFloat();
									jp.Hhook1.y=in.nextFloat();
								}
							}
							else {
								jp.Hhook1.x=in.nextFloat();
							    jp.Hhook1.y=in.nextFloat();
							}
							break;
						case 1:
							if(jp.Hhook1.state_hook==0) {
								jp.Hhook1.x=in.nextFloat();
							    jp.Hhook1.y=in.nextFloat();
								jp.Hhook1.zeta=in.nextFloat();
								jp.Hhook1.state_hook=1;
							    jp.Hhook1.v=in.nextDouble();
							    jp.Hhook1.a=false;
							}
							else {
								jp.Hhook1.a=true;
								jp.Hhook1.x=in.nextFloat();
							    jp.Hhook1.y=in.nextFloat();
							    jp.Hhook1.zeta=in.nextFloat();
							    jp.Hhook1.v=in.nextDouble();
							}
							break;
						case 2:
							if(jp.Hhook1.state_hook==2) {
								jp.Hhook1.x=in.nextFloat();
							    jp.Hhook1.y=in.nextFloat();
							    jp.Hhook1.zeta=in.nextFloat();
							    jp.Hhook1.v=in.nextDouble();
							    jp.Hhook1.hookPos[4]=in.nextInt();
							    jp.Hhook1.a=false;
							}
							else {
								jp.Hhook1.state_hook=2;
								jp.Hhook1.x=in.nextFloat();
							    jp.Hhook1.y=in.nextFloat();
							    jp.Hhook1.zeta=in.nextFloat();
							    jp.Hhook1.v=in.nextDouble();
							    jp.Hhook1.hookPos[4]=in.nextInt();
							    jp.Hhook1.a=true;
							}
							break;
						default:break;
						}
						in.close();
					}
					else if(str.charAt(0)=='(') {                                                  //���������Ҫ��ͣ����Ϣ���򲻻��ٽ��չ��ӵ�״̬��������Ȼ�Ͳ��ᶮ��
						jp.gameState=2;
						jp.remove(jp.jlstop);
						jp.add(jp.jstop);
						jp.repaint();
						while(true) {
							try {str=brin.readLine();}catch(Exception e) {str="$$$";}              //�ȴ���֪��������������Ϣ�����ָ���ԭ����״̬
							if(str.equalsIgnoreCase(")")) break;
						}
						jp.remove(jp.jstop);
						jp.add(jp.jlstop);
						jp.gameState=0;
					}
					else if(str.charAt(0)=='*') {                                                   //��������˳�����Ϣ�����ı�gameState��ֵ���Ӷ��˳���Ϸ
						jp.gameState=1;
						jp.flag2=false;
					}
					else;
				}
				try {sleep(40);}catch(Exception e1) {}
			}
		}	
	}
}

