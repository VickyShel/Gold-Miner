package Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;

public class Hook extends Thread{
	double radius;                         //���ӵ��˶��뾶
	int name;                              //���ӵ����֣��ǡ�1����2��
	Scene jp;                              //��Ӧ����
	Rectangle rHook;                       //���ӵľ��ο�
	public int[] hookPos=new int[5];       //0��1�ǿ�ʼ�������xy��2��3�ǽ�����xy��Ϊ�߳�������4�Ǳ����ϵ�����ı��
	int state_hook=0;                      //0:��ת 1:�¹� 2:�Ϲ�
	JLabel jlHook=new JLabel(new ImageIcon("����.PNG"));     //���ӵ�ͼƬ
	long start;                            //��Ϸ�Ŀ�ʼʱ��
	double v=0.00016;                      //��ת�ٶ�
	double zeta=0;                         //��Ӧ�Ƕ�
	float x=0;                             //���ӵ�x����
	float y=0;                             //���ӵ�y����
	boolean a=true;                        //�Ƿ��������ù���λ��
	
	/*
	 * ���ڳ�ʼ�����ӣ�����λ�ü����ο��
	 */
	public Hook(int a,Scene k,long start){
		this.start=start;
		jp=k;
		name=a;
		radius=40;
		if(name==1) {
			hookPos[0]=300;
			hookPos[1]=100;
			hookPos[2]=310;
			hookPos[3]=100;
			jp.hook1flag=1;
		}
		if(name==2) {
			hookPos[0]=500;
			hookPos[1]=100;
			hookPos[2]=510;
			hookPos[3]=100;
			jp.hook2flag=1;
		}
	    hookPos[4]=-1;
	    rHook=new Rectangle(hookPos[2],hookPos[3],20,20);
	    jlHook.setBounds(hookPos[2], hookPos[3], 20,20);
	    jp.add(jlHook);
	    jp.Thcount++;
	}
	
	/*
	 * �߳�����ȷ�����ӵ�λ�ò���ʱ�ı䣬�Ӷ��ﵽ��̬Ч��
	 */
	public void run() {
		while(jp.gameState!=1) {
			if(jp.gameState==0) {
				switch(state_hook) {
				case 0:
					long time=new Date().getTime();
					long differValue=time-start;
					zeta=differValue*0.0025;
					hookPos[2]=(int)(Math.cos(-zeta)*radius)+hookPos[0];
					hookPos[3]=(int)(Math.abs(Math.sin(zeta)*radius))+hookPos[1];
					break;
				case 1:
					v=0.00016;
					if(a) {
						x=hookPos[2];
						y=hookPos[3];
						a=false;
					}
					for(int i=0;i<jp.amount;i++) {
						rHook.x=hookPos[2];
						rHook.y=hookPos[3];
						if(jp.is_exist[i]==true && rHook.intersects(jp.bonus[i].rec)) {
							hookPos[4]=i;
							state_hook=2;
							v=jp.bonus[i].velocity;
						}
						if(hookPos[2]<=10 || hookPos[2]>=710 || hookPos[3]<=50 || hookPos[3]>=520) {
							state_hook=2;
	                        v=0.00016;
						}
					}
					x+=Math.cos(-zeta)*v;
					y+=Math.abs(Math.sin(zeta))*v;
					hookPos[2]=(int)x;
					hookPos[3]=(int)y;
					break;
				case 2:
					if(hookPos[4]!=-1) {
						jp.bonus[hookPos[4]].x=hookPos[2];
						jp.bonus[hookPos[4]].y=hookPos[3];
						jp.bonus[hookPos[4]].moveJLabel();
						if(hookPos[3]<=145) {
							state_hook=0;
							jp.iMoney+=jp.bonus[hookPos[4]].value;
							jp.jlMoney.setText("�����Ǯ: "+jp.iMoney);
							if(name==1) {
								jp.iMoney1+=jp.bonus[hookPos[4]].value;
								jp.jlMoney_1.setText("$"+jp.iMoney1);
							}
							else {
								jp.iMoney2+=jp.bonus[hookPos[4]].value;
								jp.jlMoney_2.setText("$"+jp.iMoney2);
							}
							jp.remove(jp.bonus[hookPos[4]].label);
							jp.is_exist[hookPos[4]]=false;
							hookPos[4]=-1;
							v=0.00016;
							a=true;
							if(name==2) jp.music.chainstop();
						}
					}
					else {
						if(hookPos[3]<=145) {
							state_hook=0;
							v=0.00016;
							a=true;
							if(name==2) jp.music.chainstop();
						}
					}
					x-=Math.cos(-zeta)*v;
					y-=Math.abs(Math.sin(zeta))*v;
					hookPos[2]=(int)x;
					hookPos[3]=(int)y;
					break;
				default:break;
				}
				if(jp.gameState==0) {
					this.moveJLabel();
				}
			}
	    }
	}
	
	/*
	 * �����ƶ����ӵ�λ��
	 */
	public void moveJLabel() {
		jlHook.setBounds(hookPos[2], hookPos[3], 20,20);
	}
}

