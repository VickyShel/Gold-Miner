package Server;

import java.io.*;
import java.net.*;
import java.util.*;

/*
 * 此类用于与Client进行TCP传输
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
	 * 此方法用于初始化Server
	 * 生成Socket接口并得到相应的输入输出流
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
	 * 实时传输钩子的状态给Client端，从而保证两端的实时性
	 * 传输的内容有空转时钩子的x及y值，下钩时钩子的x及y值、角度及速度，上钩时x及y值、角度、速度及拉上来的物品（若拉上来则是对应的编码，否则为零)
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
	 * 传输想要退出的请求
	 */
	public void sendexit() throws Exception{
		brout.writeBytes("*"+'\n');
		brout.flush();
	}
	
	/*
	 * 传输想要开始游戏的请求
	 */
	public void sendstart() throws Exception{
		brout.writeBytes("!"+'\n');
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
	 * 接受Client端传过来的Thing对象，并相应的设置到自己的界面上，从而保障两边相同同步
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
		jp.jlAim.setText("目标钱数: "+jp.iAim);
		try{str=brin.readLine();}catch(Exception e) {}
		startTime=Long.parseLong(str);
		try {sendstart();}catch(Exception e) {}
		jp.gameState=0;
	}
	
	/*
	 * 实现Server的线程
	 * while循环：若游戏仍在进行状态（gameState=0),则会不停的接收Client端传过来的钩子状态的信息并作出相应的反应，从而保持同步性
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
									jp.jlMoney.setText("共获金钱: "+jp.iMoney);
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
