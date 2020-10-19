package Client;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.*;

public class Record implements Comparable<Record>{
	public int score;
	public long time;
	public String other="";
	String timestr;
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");           //�趨ʱ�������õĸ�ʽ
	
	//Record����������str�л�ȡ���ݹ����µ�Record����
	public Record(String str) {
		Scanner in=new Scanner(str);          //��String str�л�ȡ�����Ϣ
		other+=in.next()+" ";
		score=in.nextInt();
		other+=score+" ";
		other+=in.next()+" ";
		other+=in.next()+" ";
		other+=in.next()+" ";
		time=in.nextLong();
		timestr=str;
	}
	
	//Record��������ͨ����ֵa�õ������Ӷ������µ�Record����
	public Record(int a) {
		score=a;
		time=new Date().getTime();                                                   //������ 1970 �� 1 �� 1 �� 00:00:00 GMT ������ Date �����ʾ�ĺ�����
		other="SCORE: "+score+" "+"TIME: "+df.format(new Date());                    // �����df�趨��ʽ�ģ�new Date()Ϊ��ȡ��ǰϵͳʱ��
		timestr="SCORE: "+score+" "+"TIME: "+df.format(new Date())+" "+time;
	}
	
	//�õ�Record String
	public String getstring() {
		return timestr;
	}
	
	//��дcompareTo����ʵ�ְ��շ���������������
	@Override
	public int compareTo(Record kk) {
		if(kk.score>score) return 1;
		else if(kk.score<score) return -1;
		else {
			if(time>kk.time) return 1;
			else return -1;
		}
	}
	
}