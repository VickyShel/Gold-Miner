package DataBase;

import java.util.Scanner;

/*
 * �����Ǵ洢��һ�����Ϣ����
 */
public class Player{
private String firstName;
private String lastName;
private String mail;
private String password;
public Player() {
	
}
public Player(String string) {
	Scanner in=new Scanner(string);
	while(in.hasNext()) {
		firstName=in.next();
		lastName=in.next();
		mail=in.next();
		password=in.next();
	}
	in.close();
}
//�õ���ҵ��й���Ϣ������д���ļ�����ȡ�ļ�
public String getstring() {
	return firstName+" "+lastName+" "+mail+" "+password;
}
public void setFirstName(String fir) {
	this.firstName=fir;
}
public void setLastName(String last) {
	this.lastName=last;
}
public void setMail(String string) {
	this.mail=string;
}
public void setPassword(String password) {
	this.password=password;
}
public String getMail() {
	return mail;
}
public String getFirstName() {
	return firstName;
}
public String getLastName() {
	return lastName;
}
public String getPassword() {
	return password;
}
}
