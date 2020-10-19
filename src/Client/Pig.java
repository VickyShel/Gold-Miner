package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*
 * 小猪的运动及位置、方向改变
 */
public class Pig extends Thread{
	Scene jp;
	public int[] direction;
	
	/*
	 * 小猪的初始化，用于设定所在的界面
	 */
	public Pig(Scene j) {
		jp=j;
	}
	
	/*
	 * 小猪的线程
	 * 在运行状态中，则会不停的判断是否与其他Thing对象碰撞，以及是否到达边界，否则就会更改方向
	 */
	public void run() {
		direction=new int[jp.pigCount];
		for(int i=0;i<jp.pigCount;i++) {
			direction[i]=1;                                                                 //每一个小猪都有一个自己的方向，通过正负来更改小猪的前进方向
		}
		while(jp.gameState!=1) {
			if(jp.gameState==0) {
				for(int i=0;i<jp.pigCount;i++) {
					jp.bonus[jp.pigNum[i]].x+=direction[i];
					if(jp.bonus[jp.pigNum[i]].x<=2 || jp.bonus[jp.pigNum[i]].x>=700) {      //判断是否到达边界
						direction[i]=-direction[i];
						jp.bonus[jp.pigNum[i]].x+=2*direction[i];
					}
					for(int j=0;j<jp.amount;j++) {                                          //判断是否与其他Thing类对象碰撞
						if( j!=jp.pigNum[i] && jp.is_exist[j]==true) {
							if(jp.gameState!=0) break;
							if(jp.bonus[jp.pigNum[i]].is_attack(jp.bonus[j].rec)) {
								direction[i]=-direction[i];
								jp.bonus[jp.pigNum[i]].x+=2*direction[i];
								break;
							}
						}	
					}
					if(jp.gameState==0) {                                                   //及时改变小猪的位置及方向
						jp.bonus[jp.pigNum[i]].moveJLabel();
						jp.bonus[jp.pigNum[i]].moverec();
					}
				}
			}
			if(jp.gameState!=1) {
				try {sleep(60);}catch(Exception e) {}
			}
		}
	}
}
