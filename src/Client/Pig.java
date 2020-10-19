package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*
 * С����˶���λ�á�����ı�
 */
public class Pig extends Thread{
	Scene jp;
	public int[] direction;
	
	/*
	 * С��ĳ�ʼ���������趨���ڵĽ���
	 */
	public Pig(Scene j) {
		jp=j;
	}
	
	/*
	 * С����߳�
	 * ������״̬�У���᲻ͣ���ж��Ƿ�������Thing������ײ���Լ��Ƿ񵽴�߽磬����ͻ���ķ���
	 */
	public void run() {
		direction=new int[jp.pigCount];
		for(int i=0;i<jp.pigCount;i++) {
			direction[i]=1;                                                                 //ÿһ��С����һ���Լ��ķ���ͨ������������С���ǰ������
		}
		while(jp.gameState!=1) {
			if(jp.gameState==0) {
				for(int i=0;i<jp.pigCount;i++) {
					jp.bonus[jp.pigNum[i]].x+=direction[i];
					if(jp.bonus[jp.pigNum[i]].x<=2 || jp.bonus[jp.pigNum[i]].x>=700) {      //�ж��Ƿ񵽴�߽�
						direction[i]=-direction[i];
						jp.bonus[jp.pigNum[i]].x+=2*direction[i];
					}
					for(int j=0;j<jp.amount;j++) {                                          //�ж��Ƿ�������Thing�������ײ
						if( j!=jp.pigNum[i] && jp.is_exist[j]==true) {
							if(jp.gameState!=0) break;
							if(jp.bonus[jp.pigNum[i]].is_attack(jp.bonus[j].rec)) {
								direction[i]=-direction[i];
								jp.bonus[jp.pigNum[i]].x+=2*direction[i];
								break;
							}
						}	
					}
					if(jp.gameState==0) {                                                   //��ʱ�ı�С���λ�ü�����
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
