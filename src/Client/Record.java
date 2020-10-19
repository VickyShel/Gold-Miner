package Client;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.*;

public class Record implements Comparable<Record>{
	public int score;
	public long time;
	public String other="";
	String timestr;
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");           //设定时间所采用的格式
	
	//Record构造器，从str中获取数据构造新的Record对象
	public Record(String str) {
		Scanner in=new Scanner(str);          //从String str中获取相关信息
		other+=in.next()+" ";
		score=in.nextInt();
		other+=score+" ";
		other+=in.next()+" ";
		other+=in.next()+" ";
		other+=in.next()+" ";
		time=in.nextLong();
		timestr=str;
	}
	
	//Record构造器，通过数值a得到分数从而构造新的Record对象
	public Record(int a) {
		score=a;
		time=new Date().getTime();                                                   //返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数
		other="SCORE: "+score+" "+"TIME: "+df.format(new Date());                    // （获得df设定形式的）new Date()为获取当前系统时间
		timestr="SCORE: "+score+" "+"TIME: "+df.format(new Date())+" "+time;
	}
	
	//得到Record String
	public String getstring() {
		return timestr;
	}
	
	//重写compareTo方法实现按照分数进行逆序排序
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