package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.io.*;
import java.util.*;
import DataBase.Player;

public class Scene extends JPanel implements Runnable{
	public int iAim=0;             //Ŀ��Ǯ��(14��Thing�ļ�ֵ�ܺ�)           
	public int iMoney=0;           //�����Ǯ��
	public int iTime=60;           //ʣ��ʱ��
	public int iMoney1=0;          //���һ�����Ǯ
	public int iMoney2=0;          //��Ҷ������Ǯ
	public int gameState=2;        //0:����Ϸ״̬ 1:������Ϸ״̬ 2����ͣ
	public Integer Thcount=0;      //���ӵĸ���
	public File filesingle=new File("filesingle2.txt");                   //�������˷���file����
	public File fileteam=new File("fileteam.txt");                       //�����������file����
	public TreeSet<Record> treesingle=new TreeSet<Record>();             //�Զ��Ը���Record�����շ�����������
	public TreeSet<Record> treeteam=new TreeSet<Record>();               //�Զ����Ŷ�Record�����շ�����������
	public boolean flag=false;                                          
	public boolean flag2=true;
	private boolean flag1=false;
	private static Scene jpScene;
	boolean start_flag=false;                                            
	private Graphics graph;
	Thing[] bonus;                                                       //Thing�����飺����14:����5 ���ƽ�4 ��ʯͷ��3 ����� 2 ����?���ڴ�
	boolean[] is_exist;                                                  //��¼�Ƿ�����ײ������Thing��������Ϊtrue��������Ϊfalse������������������Ǿͱ�Ϊfalse�ˣ�
	int amount;                                                          //�ж���Thing
	JButton start=new JButton("��ʼ");                                   
	JLabel jlAim=new JLabel("Ŀ��Ǯ��: "+iAim);
	JLabel jlMoney=new JLabel("�����Ǯ: "+iMoney);
	JLabel jlTime=new JLabel("ʣ��ʱ��: "+iTime);
	JLabel jlMoney_1=new JLabel("$"+iMoney1);
	JLabel jlMoney_2=new JLabel("$"+iMoney2);
	JLabel player=new JLabel("��Ҷ���ʷǰ��÷֣�");
	JLabel team=new JLabel("�Ŷ���ʷ�÷֣�");
	JLabel[] jlscore=new JLabel[5];                                      //������ʷǰ��÷�
	JLabel[] jlteam=new JLabel[5];                                       //�Ŷ���ʷǰ��÷�
	JButton jlExit=new JButton("�˳�����");
	JButton jlstop=new JButton("��ͣ");
	JLabel jstop=new JLabel("�Է���ͣ");
	JLabel gg=new JLabel("���ź�,��û��ͨ������");
	
	Player play;
	
	Timer timer;
	Client client;
	Music music;
	public int hook2flag=0;                                                    //�������Ƿ�new��
	public int hook1flag=0;                                                    //�������Ƿ�new��
	Hook Hhook1;
	Hook Hhook2;
	
	int[] pigNum;
	int pigCount;
	Pig Tp;
	
	/*
	 * ���ڳ�ʼ��Scene���棬ͬʱ����launchFrame�������������Եĳ�ʼ���Ȳ���
	 */
	  public Scene(Player player) { 
		  this.play=player;
		  launchFrame();
		  jpScene=this;
	  }
	  
	/*
	 * ���ڲ���GUI������ȹ����Եĳ�ʼ��
	 */
	public void launchFrame() {
		JFrame myframe=new JFrame();
		myframe.setTitle("�ƽ��-client");
		myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myframe.setContentPane(this);
		this.setLayout(null);
		this.setOpaque(false);             //JPanel��͸����
		
		graph=this.getGraphics();
		ImageIcon bg=new ImageIcon("������.PNG");
		JLabel label=new JLabel(bg);
		label.setBounds(-3,-3,bg.getIconWidth(),bg.getIconHeight());
		myframe.getLayeredPane().add(label,new Integer(-30001));//��������ͼƬ�ŵ����������.ContentPanel��Z_order����ֵΪ-3000����Ϊ��������ContentPanel�ĵײ㣬����Ӧ�ñ�-30000��С
		myframe.setBounds(0, 0, 784,604);//û�м���ֵ
		
		jlAim.setBounds(5, 10, 100, 30);
		jlMoney.setBounds(5, 50, 100, 30);
		jlTime.setBounds(120,10,100,30);
		jlExit.setBounds(600, 50,110,20);
		jlExit.addActionListener(new ExitListener());
		jlstop.setBounds(610, 20,80,20);
		jlstop.addActionListener(new StopListener());
		jstop.setBounds(610, 20,160,20);
		jlMoney_1.setBounds(305,90,100,20);
		jlMoney_1.setFont(new Font("����",Font.BOLD,20));
		jlMoney_2.setBounds(490,90,100,20);
		jlMoney_2.setFont(new Font("����",Font.BOLD,20));
		gg.setBounds(350,300,400,50);
		player.setBounds(50, 230, 300, 300);
		team.setBounds(400, 230, 300, 300);
		start.setBounds(300,300, 80, 40);
		this.add(start);
		start.addActionListener(new StartListener());                                 //��start��Ӽ�����     
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
		
		timer=new Timer();                                                                          //���ڼ�ʱ��ÿ100�������һ�κ���
		timer.schedule(new TimerTask() {
			int a=-1;
			public void run() {
				if(gameState==0) {
					try{client.sendhook();}catch(Exception e) {}
					a++;
					if(a==10) {                                                                      //ȷ������1���ӣ���10*100����
						a=0;
						iTime--;
					    if(iTime==1) {iTime--;System.out.println("shijianmeile");jlTime.setText("ʣ��ʱ��: "+iTime); gameState=1;}
					    if(gameState==0) { jlTime.setText("ʣ��ʱ��: "+iTime);}
					}
				}
			}
		},0,100);
	}
	
	//����������֮�����
	public void paintComponent(Graphics g) {
		if(gameState==0 && hook1flag==1 && hook2flag==1) {
			g.drawLine(Hhook1.hookPos[0], Hhook1.hookPos[1], Hhook1.hookPos[2]+10, Hhook1.hookPos[3]+2);           // +10��+2
			g.drawLine(Hhook2.hookPos[0], Hhook2.hookPos[1], Hhook2.hookPos[2]+10, Hhook2.hookPos[3]+2);
		}
	}
	
	/*
	 * Scene�̣߳�����Ҫ���������Ϸ���ڵĿ���
	 * �������Ϸ�����е�״̬�����ʱ���ж��Ƿ�ÿ����Ʒ�����������ˣ����������Ϸֹͣ���������
	 * �����Ϸֹͣ�������²��ý��棬�������Ƿ�ͨ�ؼ���ʷ�����Ϸ��¼����˳���Ϸ
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
					gg.setText("��ͨ���˱���");
					gg.setVisible(true);
				}
				else { 
					gg.setText("���ź�,��û��ͨ������");
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
	
	//���ڲ������е�Thing���������Ƿ�����ײ���жϣ�����У������²�����֪��û����ײΪֹ
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
						if(is_exist[i]==true && bonus[i].is_attack(bonus[j].rec)) {                      //�ж��Ѳ�����Thing֮���Ƿ���ײ
							flag=true;                                                                   //����ײ����flag��Ϊtrue�������ٽ�������Thing
							break;
						}
					}
				}
			}
		}
		for(int i=0;i<amount;i++) {
			bonus[i].getLabel();
		}
		jlAim.setText("Ŀ��Ǯ��: "+iAim);
	}
	
	//��͹��ӿ�ʼ�����߳̿�ʼ
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
	
	//��ս���
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
	
	//���¿�ʼ��Ϸ���������趨
	public void restart(){
		iTime=60;
		jlMoney.setText("�����Ǯ: "+iMoney);
		jlTime.setText("ʣ��ʱ��: "+iTime);
		jlMoney_1.setText("$"+iMoney1);
		jlMoney_2.setText("$"+iMoney2);
		this.produce();
		repaint();
	}
	
	//��ȡ�ļ����ݣ��õ���ʷ��ҵĳɼ���
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
			System.out.println(e.getMessage()+"�ļ���ȡ���� read()������");
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
	
	//�����¼�¼��ͬ��ǰ�ļ�¼һ��д����Ӧ���ļ���ȥ
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
	
	//�Ӽ�¼Tree�л�ȡ������ǰ�����¼�������Ϣ��չʾ
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
	
	//������������ʼ��ť�����¼����������ȥ��ť������client�������˶����̡߳�����һЩlabel���ػ����
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
	 * �����������Ƿ������°�ť����������תΪ�¹�״̬������ʱ����״̬����Server
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
			if(e.getKeyCode()==KeyEvent.VK_DOWN && Hhook2.state_hook==0) {            //VK_DOWN  �¼�ͷ   
				music.playonce();
				Hhook2.state_hook=1;
				try{client.sendhook();}catch(Exception e1) {}
			}
		}
	}
	
	/*
	 * �����������˳���ť�������˳���Ϸ
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
	 * ������������ͣ������¼����������󴫸��Է�
	 */
	public class StopListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			try {
				if(e.getActionCommand().equals("��ͣ")) {
					System.out.println("zanting");
					gameState=2;
					try{client.sendstop();}catch(Exception e2) {}
					jlstop.setText("����");
				}
				else if(e.getActionCommand().equals("����")) {
					System.out.println("����");
					try{client.sendcontinue();}catch(Exception e2) {}
					jlstop.setText("��ͣ");
					gameState=0;
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}