package Client;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Date;

/*
 * 此类用于与Server进行TCP传输
 */
public class Client extends Thread{
	Scene jp;
	Socket socketClient;
	BufferedReader brin;
	DataOutputStream brout;
	public long startTime;
	boolean flag=true;
	
	/*
	 * 此方法用于初始化Client
	 * 生成Socket接口并得到相应的输入输出流
	 */
	public Client(Scene j) throws Exception{
		socketClient=new Socket("localhost",6789);
		brin=new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
		brout=new DataOutputStream(socketClient.getOutputStream());
		jp=j;
	}
	
	/*
	 * 用于将Client界面所生成的所用Thing对象通过socket传输给Server端
	 * 除了Thing对象，还将Thing的数目以及目标金钱、开始时间数传过去
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
	 * 实时传输钩子的状态给Server端，从而保证两端的实时性
	 * 传输的内容有空转时钩子的x及y值，下钩时钩子的x及y值、角度及速度，上钩时x及y值、角度、速度及拉上来的物品（若拉上来则是对应的编码，否则为零)
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
	 * 传输想要退出的请求
	 */
	public void sendexit() throws Exception{
		brout.writeBytes("*"+'\n');
		brout.flush();
	}
	
	/*
	 * 传输想要暂停的请求
	 */
	public void sendstop() throws Exception{
		brout.writeBytes("("+'\n');
		brout.flush();
	}
	
	/*
	 * 传输想要继续的请求
	 */
	public void sendcontinue() throws Exception{
		brout.writeBytes(")"+'\n');
		brout.flush();
	}
	
	/*
	 * 实现Client的线程
	 * 初始时将会产生一个初始时间，并等待Server端传来已准备好的消息然后进入循环当中
	 * while循环：若游戏仍在进行状态（gameState=0),则会不停的接收Server端传过来的钩子状态的信息并作出相应的反应，从而保持同步性
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
							if(jp.Hhook1.state_hook!=0) {                                      //如果对方传来将要停止的消息，则会将拉上来的东西进行相关操作
								if(jp.Hhook1.hookPos[4]!=-1) {                                
									jp.Hhook1.state_hook=0;
									jp.iMoney+=jp.bonus[jp.Hhook1.hookPos[4]].value;           //如果拉上来了东西，则会获得对应的数值并在界面上修改
									jp.jlMoney.setText("共获金钱: "+jp.iMoney);
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
					else if(str.charAt(0)=='(') {                                                  //如果传来想要暂停的消息，则不会再接收钩子的状态，钩子自然就不会懂了
						jp.gameState=2;
						jp.remove(jp.jlstop);
						jp.add(jp.jstop);
						jp.repaint();
						while(true) {
							try {str=brin.readLine();}catch(Exception e) {str="$$$";}              //等待，知道传来继续的消息，则会恢复到原来的状态
							if(str.equalsIgnoreCase(")")) break;
						}
						jp.remove(jp.jstop);
						jp.add(jp.jlstop);
						jp.gameState=0;
					}
					else if(str.charAt(0)=='*') {                                                   //如果传来退出的消息，则会改变gameState数值，从而退出游戏
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

