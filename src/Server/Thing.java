package Server;

import java.awt.Label;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
                                           
public class Thing {
public int value;
public int x;
public int y;
public JLabel label;
public Rectangle rec;
public ImageIcon image;
public double velocity;
private JPanel jp;
public String name;

//��ĳ�����ʼ��
public Thing(Scene jp,String name,int x,int y) {
	this.x=x;
	this.y=y;
	this.jp=jp;
	this.name=name;
	
	//���������ֻ��������ÿ��Thing�ľ��Σ�����������Ӧ��ImageIcon������û��������Ӧ��Label���ڼӵ������
	switch(name) {
	case "goldBig":
		value=500;
		velocity=0.00005;
		image=new ImageIcon("�����.PNG");                                                                  //�����·�����ÿ���������
		rec=new Rectangle(x,y,image.getIconWidth(),image.getIconHeight());
		break;
	case "goldmedium":
		value=200;
		velocity=0.00006;
		image=new ImageIcon("�н���.PNG");
		rec=new Rectangle(x,y,image.getIconWidth(),image.getIconHeight());
		break;
	case "goldsmall":
		value=100;
		velocity=0.00008;
		image=new ImageIcon("С����.PNG");
		rec=new Rectangle(x,y,image.getIconWidth(),image.getIconHeight());
		break;
	case "stone":
		value=20;
		velocity=0.00006;
		image=new ImageIcon("ʯͷ.PNG");
		rec=new Rectangle(x,y,image.getIconWidth(),image.getIconHeight());
		break;
	case "pocket":
		value=(int)(Math.random()*1000);
		velocity=0.00006;
		image=new ImageIcon("�ڴ�.PNG");
		rec=new Rectangle(x,y,image.getIconWidth(),image.getIconHeight());
		break;
	case "pig":
		value=10;
		velocity=0.00008;
		image=new ImageIcon("С��.PNG");
		rec=new Rectangle(x,y,image.getIconWidth(),image.getIconHeight());
		break;
	default:break;
	}
}

//����ײ�򷵻�true�����򷵻�false
public boolean is_attack(Rectangle myrec) {                 
	if((rec).intersects(myrec)) {
		return true;
	}else {
		return false;
	}
}

//����Ӧ��Thing������ӵ���Ӧ��Panel����ʾ
public void getLabel() {                                                                        
	label=new JLabel(image);
	label.setBounds(x, y, image.getIconWidth(), image.getIconHeight());              //����Label
	jp.add(label);
}

//��ĳ������ӵ���Ӧ����ϴӶ���ʾ
public void addToPanel() {
	label=new JLabel(image);
	label.setBounds(x, y, image.getIconWidth(), image.getIconHeight());
	jp.add(label);
}

//�ƶ�label
public void moveJLabel() {
	label.setBounds(x, y, image.getIconWidth(), image.getIconHeight());
}

//�ƶ�rec
public void moverec() {
	rec.setBounds(x, y, image.getIconWidth(), image.getIconHeight());
}
}
