package DataBase;

import java.io.File;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

/*
 * 此类用于完成像文件中读取玩家姓名、密码、邮箱等信息
 */
public class DataBase {
	CopyOnWriteArrayList<Player> playerlist;
	private static DataBase database=new DataBase();
	public File player=new File("player.txt");
	private  DataBase() {
		playerlist=new CopyOnWriteArrayList<Player>();
	}
	
	public void addCustomer(Player player) {
		playerlist.add(player);
	}
	
	public static DataBase getDataBase() {
		return database;
	}
	
	public Player getCustomer(int index) {
		return playerlist.get(index);
	}
	
	public CopyOnWriteArrayList<Player> getPlayer(){
		return playerlist;
	}
	
	public void read() {
		String str="";
		try {
			Scanner in=new Scanner(player);
			while(in.hasNext()) {
				str=in.nextLine();
				playerlist.add(new Player(str));;
			}
			in.close();
		}
		catch(Exception e) {}
	}
	
	public void setrecord() {
		String str="";
		try {
			PrintStream out=new PrintStream(player);
			for(Player pl:playerlist)
				str+=pl.getstring()+'\n';
			out.print(str);
			out.close();
		}
		catch(Exception e) {}
	}
	
	
	public Iterator<Player> getPlayers() {
		Iterator<Player> playerIterator=playerlist.iterator();
		return playerIterator;
	}
	
	public int getNumOfCustomers() {
		return playerlist.size();
	}

}
