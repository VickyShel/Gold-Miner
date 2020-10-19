package Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;

public class Hook extends Thread{
	double radius;                         //钩子的运动半径
	int name;                              //钩子的名字，是“1”或“2”
	Scene jp;                              //对应界面
	Rectangle rHook;                       //钩子的矩形框
	public int[] hookPos=new int[5];       //0、1是开始点的坐标xy，2、3是结束点xy，为线程所共享，4是被勾上的物体的编号
	int state_hook=0;                      //0:空转 1:下钩 2:上钩
	JLabel jlHook=new JLabel(new ImageIcon("钩子.PNG"));     //钩子的图片
	long start;                            //游戏的开始时间
	double v=0.00016;                      //空转速度
	double zeta=0;                         //对应角度
	float x=0;                             //钩子的x坐标
	float y=0;                             //钩子的y坐标
	boolean a=true;                        //是否立即设置钩子位置
	
	/*
	 * 用于初始化钩子，包括位置及矩形框等
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
	 * 线程用来确定钩子的位置并及时改变，从而达到动态效果
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
							jp.jlMoney.setText("共获金钱: "+jp.iMoney);
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
	 * 用来移动钩子的位置
	 */
	public void moveJLabel() {
		jlHook.setBounds(hookPos[2], hookPos[3], 20,20);
	}
}

