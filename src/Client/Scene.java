package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.io.*;
import java.util.*;
import DataBase.Player;

public class Scene extends JPanel implements Runnable{
	public int iAim=0;             //目标钱数(14个Thing的价值总和)           
	public int iMoney=0;           //共获金钱数
	public int iTime=60;           //剩余时间
	public int iMoney1=0;          //玩家一所获金钱
	public int iMoney2=0;          //玩家二所获金钱
	public int gameState=2;        //0:玩游戏状态 1:不玩游戏状态 2：暂停
	public Integer Thcount=0;      //钩子的个数
	public File filesingle=new File("filesingle2.txt");                   //创建个人分数file对象
	public File fileteam=new File("fileteam.txt");                       //创建团体分数file对象
	public TreeSet<Record> treesingle=new TreeSet<Record>();             //自动对个人Record对象按照分数进行排序
	public TreeSet<Record> treeteam=new TreeSet<Record>();               //自动对团队Record对象按照分数进行排序
	public boolean flag=false;                                          
	public boolean flag2=true;
	private boolean flag1=false;
	private static Scene jpScene;
	boolean start_flag=false;                                            
	private Graphics graph;
	Thing[] bonus;                                                       //Thing的数组：至少14:个，5 个黄金，4 个石头，3 个猪和 2 个”?”口袋
	boolean[] is_exist;                                                  //记录是否有碰撞。若此Thing还存在则为true，否则则为false（比如把它挖上来了那就变为false了）
	int amount;                                                          //有多少Thing
	JButton start=new JButton("开始");                                   
	JLabel jlAim=new JLabel("目标钱数: "+iAim);
	JLabel jlMoney=new JLabel("共获金钱: "+iMoney);
	JLabel jlTime=new JLabel("剩余时间: "+iTime);
	JLabel jlMoney_1=new JLabel("$"+iMoney1);
	JLabel jlMoney_2=new JLabel("$"+iMoney2);
	JLabel player=new JLabel("玩家二历史前五得分：");
	JLabel team=new JLabel("团队历史得分：");
	JLabel[] jlscore=new JLabel[5];                                      //个人历史前五得分
	JLabel[] jlteam=new JLabel[5];                                       //团队历史前五得分
	JButton jlExit=new JButton("退出本关");
	JButton jlstop=new JButton("暂停");
	JLabel jstop=new JLabel("对方暂停");
	JLabel gg=new JLabel("很遗憾,您没有通过本关");
	
	Player play;
	
	Timer timer;
	Client client;
	Music music;
	public int hook2flag=0;                                                    //看钩子是否被new了
	public int hook1flag=0;                                                    //看钩子是否被new了
	Hook Hhook1;
	Hook Hhook2;
	
	int[] pigNum;
	int pigCount;
	Pig Tp;
	
	/*
	 * 用于初始化Scene界面，同时调用launchFrame函数完成相关属性的初始化等操作
	 */
	  public Scene(Player player) { 
		  this.play=player;
		  launchFrame();
		  jpScene=this;
	  }
	  
	/*
	 * 用于产生GUI并完成先关属性的初始化
	 */
	public void launchFrame() {
		JFrame myframe=new JFrame();
		myframe.setTitle("黄金矿工-client");
		myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myframe.setContentPane(this);
		this.setLayout(null);
		this.setOpaque(false);             //JPanel是透明的
		
		graph=this.getGraphics();
		ImageIcon bg=new ImageIcon("主界面.PNG");
		JLabel label=new JLabel(bg);
		label.setBounds(-3,-3,bg.getIconWidth(),bg.getIconHeight());
		myframe.getLayeredPane().add(label,new Integer(-30001));//将主界面图片放到内容面板里.ContentPanel的Z_order的数值为-3000，因为主界面在ContentPanel的底层，所以应该比-30000还小
		myframe.setBounds(0, 0, 784,604);//没有加数值
		
		jlAim.setBounds(5, 10, 100, 30);
		jlMoney.setBounds(5, 50, 100, 30);
		jlTime.setBounds(120,10,100,30);
		jlExit.setBounds(600, 50,110,20);
		jlExit.addActionListener(new ExitListener());
		jlstop.setBounds(610, 20,80,20);
		jlstop.addActionListener(new StopListener());
		jstop.setBounds(610, 20,160,20);
		jlMoney_1.setBounds(305,90,100,20);
		jlMoney_1.setFont(new Font("隶书",Font.BOLD,20));
		jlMoney_2.setBounds(490,90,100,20);
		jlMoney_2.setFont(new Font("隶书",Font.BOLD,20));
		gg.setBounds(350,300,400,50);
		player.setBounds(50, 230, 300, 300);
		team.setBounds(400, 230, 300, 300);
		start.setBounds(300,300, 80, 40);
		this.add(start);
		start.addActionListener(new StartListener());                                 //给start添加监听器     
		for(int i=0;i<5;i++) {
			jlscore[i]=new JLabel();
			jlteam[i]=new JLabel();
			jlscore[i].setBounds(50, 250+i*15, 300, 300);
			jlteam[i].setBounds(400, 250+i*15, 300, 300);
		}
		this.addKeyListener(new KeyDownListener());
		this.add(jlAim);
		this.add(jlMoney);
		this.add(jlTime);
		this.add(jlExit);
		this.add(jlstop);
		this.add(jlMoney_1);
		this.add(jlMoney_2);
		
		read();
		myframe.setVisible(true);  
		Thread thread=new Thread(this);
        thread.start();
		
		music=new Music(this);                            
		
		timer=new Timer();                                                                          //用于计时，每100毫秒调用一次函数
		timer.schedule(new TimerTask() {
			int a=-1;
			public void run() {
				if(gameState==0) {
					try{client.sendhook();}catch(Exception e) {}
					a++;
					if(a==10) {                                                                      //确保经过1秒钟，即10*100毫秒
						a=0;
						iTime--;
					    if(iTime==1) {iTime--;System.out.println("shijianmeile");jlTime.setText("剩余时间: "+iTime); gameState=1;}
					    if(gameState==0) { jlTime.setText("剩余时间: "+iTime);}
					}
				}
			}
		},0,100);
	}
	
	//画钩子与人之间的线
	public void paintComponent(Graphics g) {
		if(gameState==0 && hook1flag==1 && hook2flag==1) {
			g.drawLine(Hhook1.hookPos[0], Hhook1.hookPos[1], Hhook1.hookPos[2]+10, Hhook1.hookPos[3]+2);           // +10、+2
			g.drawLine(Hhook2.hookPos[0], Hhook2.hookPos[1], Hhook2.hookPos[2]+10, Hhook2.hookPos[3]+2);
		}
	}
	
	/*
	 * Scene线程，最重要的是完成游戏环节的控制
	 * 如果是游戏进行中的状态，则会时刻判断是否每个物品都被拉上来了，如果是则游戏停止，产生结果
	 * 如果游戏停止，则重新布置界面，并产生是否通关及历史玩家游戏记录最后退出游戏
	 */
	public void run() {
		while(true) {
			if(gameState==0) {
				this.requestFocus();
				flag1=true;
				for(int i=0;i<amount;i++) {
					if(is_exist[i]==true) {
						flag1=false;
					}
				}
				if(flag1) {
					try {
						Thread.sleep(30);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					gameState=1;
					continue;
				}
				repaint();
			}
			else if(gameState==1){
				System.out.println("Server Scene Run game over");
				music.stop();
				clear();
				if(iAim<=iMoney) { 
					gg.setText("您通过了本关");
					gg.setVisible(true);
				}
				else { 
					gg.setText("很遗憾,您没有通过本关");
					gg.setVisible(true);
					iMoney=iMoney1=iMoney2=0;
					iAim=0;
				}
				this.remove(jlExit);
				this.remove(jlstop);
				this.add(gg);
				music.chainstop();
				setrecord();
				display();
				music.start();
				repaint();
				break;
			}
			else {}
			try {Thread.sleep(5);}catch(Exception e) {};
		}
	}
	
	//用于产生所有的Thing，并进行是否有碰撞的判断，如果有，则重新产生，知道没有碰撞为止
	public void produce() {
		boolean flag=true;
		int k=iAim;
		while(flag) {
			iAim=k;
			amount=14+(int)(Math.random()*4);                                                          
			bonus=new Thing[amount];
			is_exist=new boolean[amount];
			for(int i=0;i<amount;i++) {
				is_exist[i]=true;
			}
			pigNum=new int[amount];
			pigCount=0;
			int a=(int)(Math.random()*2)+1;
			int b=a+(int)(Math.random()*2)+1;
			for(int i=0;i<a;i++) {
				bonus[i]=new Thing(this,"goldBig",(int)(Math.random()*700), (int)(Math.random()*315+215));
				iAim+=bonus[i].value;
			}
			for(int i=a;i<b;i++) {
				bonus[i]=new Thing(this,"goldmedium",(int)(Math.random()*700), (int)(Math.random()*255+215));
				iAim+=bonus[i].value;
			}
			for(int i=b;i<5;i++) {
				bonus[i]=new Thing(this,"goldsmall",(int)(Math.random()*680), (int)(Math.random()*265+215));
				iAim+=bonus[i].value;
			}
			for(int i=5;i<9;i++) {
				bonus[i]=new Thing(this,"stone",(int)(Math.random()*700), (int)(Math.random()*265+215));
			}
			for(int i=9;i<12;i++) {
				bonus[i]=new Thing(this,"pig",(int)(Math.random()*700), (int)(Math.random()*315+215));
				pigNum[pigCount]=i;
				pigCount++;
			}
			for(int i=12;i<14;i++) {
				bonus[i]=new Thing(this,"pocket",(int)(Math.random()*710), (int)(Math.random()*265+215));
				iAim+=bonus[i].value;
			}
			for(int i=14;i<amount;i++) {
				switch((int)(Math.random()*6)){
				case 0:bonus[i]=new Thing(this,"goldsmall",(int)(Math.random()*710), (int)(Math.random()*325+200));break;
				case 1:bonus[i]=new Thing(this,"goldmedium",(int)(Math.random()*710), (int)(Math.random()*275+200));break;
				case 2:bonus[i]=new Thing(this,"goldBig",(int)(Math.random()*660), (int)(Math.random()*175+200));break;
				case 3:bonus[i]=new Thing(this,"stone",(int)(Math.random()*700), (int)(Math.random()*275+200));break;
				case 4:bonus[i]=new Thing(this,"pocket",(int)(Math.random()*700), (int)(Math.random()*275+200));break;
				case 5:bonus[i]=new Thing(this,"pig",(int)(Math.random()*710), (int)(Math.random()*325+200));
					   pigNum[pigCount]=i;pigCount++;break;
				default:break;
				}
			}
			flag=false;
			for(int i=0;i<amount;i++) {
				if(i<=amount-2) {
					for(int j=i+1;j<amount;j++) {
						if(is_exist[i]==true && bonus[i].is_attack(bonus[j].rec)) {                      //判断已产生的Thing之间是否碰撞
							flag=true;                                                                   //若碰撞，则flag变为true，将会再进行生产Thing
							break;
						}
					}
				}
			}
		}
		for(int i=0;i<amount;i++) {
			bonus[i].getLabel();
		}
		jlAim.setText("目标钱数: "+iAim);
	}
	
	//猪和钩子开始动，线程开始
	public void start() { 
		gameState=0;
		Tp=new Pig(this);
		Tp.start();
		long start=client.startTime;
		Hhook1=new Hook(1,this,start);
		Hhook2=new Hook(2,this,start);
		Hhook1.start();
		Hhook2.start();
	}
	
	//清空界面
	public void clear(){
		for(int i=0;i<amount;i++) {
			if(is_exist[i]) remove(bonus[i].label);
		}
		remove(Hhook1.jlHook);
		remove(Hhook2.jlHook);
		if(Hhook1.hookPos[4]!=-1) remove(bonus[Hhook1.hookPos[4]].label);
		if(Hhook2.hookPos[4]!=-1) remove(bonus[Hhook2.hookPos[4]].label);
		this.repaint();
	}
	
	//重新开始游戏，完成相关设定
	public void restart(){
		iTime=60;
		jlMoney.setText("共获金钱: "+iMoney);
		jlTime.setText("剩余时间: "+iTime);
		jlMoney_1.setText("$"+iMoney1);
		jlMoney_2.setText("$"+iMoney2);
		this.produce();
		repaint();
	}
	
	//读取文件数据，得到历史玩家的成绩等
	public void read() {
		String str="";
		try {
			Scanner in=new Scanner(filesingle);
			while(in.hasNext()) {
				str=in.nextLine();
				treesingle.add(new Record(str));
			}
			in.close();
		}
		catch(Exception e) {
			System.out.println(e.getMessage()+"文件读取函数 read()有问题");
		}
		try {
			Scanner in=new Scanner(fileteam);
			while(in.hasNext()) {
				str=in.nextLine();
				treeteam.add(new Record(str));
			}
			in.close();
		}
		catch(Exception e) {}
	}
	
	//把最新记录连同以前的记录一起写到相应的文件中去
	public void setrecord() {
		Record single=new Record(iMoney2);
		Record team=new Record(iMoney);
		treesingle.add(single);
		treeteam.add(team);
		String str="";
		try {
			PrintStream out=new PrintStream(filesingle);
			for(Record word:treesingle)
				str+=word.getstring()+'\n';
			out.print(str);
			out.close();
		}
		catch(Exception e) {}
		try {
			str="";
			PrintStream out=new PrintStream(fileteam);
			for(Record word:treeteam)
				str+=word.getstring()+'\n';
			out.print(str);
			out.close();
		}
		catch(Exception e) {}
	}
	
	//从记录Tree中获取到近期前五个记录的相关信息并展示
	public void display() {
		this.add(player);
		this.add(team);
		String str="";
		int i=0;
		for(Record word:treesingle) {
			jlscore[i].setText(word.other);
			this.add(jlscore[i]);
			i++;
			if(i==5) break;
		}
		str="";
		i=0;
		for(Record word:treeteam) {
			jlteam[i].setText(word.other);
			this.add(jlteam[i]);
			i++;
			if(i==5) break;
		}
		
	}
	
	//监听器监听开始按钮，若事件产生则会移去按钮、开启client、开启此对象线程、加上一些label并重绘界面
	public class StartListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			try {
				client=new Client(jpScene);
				jpScene.produce(); 
				client.start(); 
				client.sendlayout();
				jpScene.start();
				start_flag=true;
				jpScene.remove(start);
				repaint();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	/*
	 * 监听器监听是否按了向下按钮，若按了则转为下钩状态，并及时将此状态传给Server
	 */
	public class KeyDownListener implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			if(e.getKeyCode()==KeyEvent.VK_DOWN && Hhook2.state_hook==0) {            //VK_DOWN  下箭头   
				music.playonce();
				Hhook2.state_hook=1;
				try{client.sendhook();}catch(Exception e1) {}
			}
		}
	}
	
	/*
	 * 监听器监听退出按钮，用于退出游戏
	 */
	public class ExitListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			clear();
			setrecord();
			display();
			jpScene.repaint();
			Timer time=new Timer();
			time.schedule(new TimerTask() {
				int a=0;
				public void run() {
					a++;
					if(a==4) {
						System.exit(0);
					}
				}
			},0,1000);	
		}
	}
	
	/*
	 * 监听器监听暂停或继续事件，并将请求传给对方
	 */
	public class StopListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			try {
				if(e.getActionCommand().equals("暂停")) {
					System.out.println("zanting");
					gameState=2;
					try{client.sendstop();}catch(Exception e2) {}
					jlstop.setText("继续");
				}
				else if(e.getActionCommand().equals("继续")) {
					System.out.println("继续");
					try{client.sendcontinue();}catch(Exception e2) {}
					jlstop.setText("暂停");
					gameState=0;
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}