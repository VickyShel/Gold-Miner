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

//对某对象初始化
public Thing(Scene jp,String name,int x,int y) {
	this.x=x;
	this.y=y;
	this.jp=jp;
	this.name=name;
	
	//这里的生成只是生成了每个Thing的矩形，并生成了相应的ImageIcon，但并没有生成相应的Label用于加到面板上
	switch(name) {
	case "goldBig":
		value=500;
		velocity=0.00005;
		image=new ImageIcon("大金子.PNG");                                                                  //这里的路径设置可能有问题
		rec=new Rectangle(x,y,image.getIconWidth(),image.getIconHeight());
		break;
	case "goldmedium":
		value=200;
		velocity=0.00006;
		image=new ImageIcon("中金子.PNG");
		rec=new Rectangle(x,y,image.getIconWidth(),image.getIconHeight());
		break;
	case "goldsmall":
		value=100;
		velocity=0.00008;
		image=new ImageIcon("小金子.PNG");
		rec=new Rectangle(x,y,image.getIconWidth(),image.getIconHeight());
		break;
	case "stone":
		value=20;
		velocity=0.00006;
		image=new ImageIcon("石头.PNG");
		rec=new Rectangle(x,y,image.getIconWidth(),image.getIconHeight());
		break;
	case "pocket":
		value=(int)(Math.random()*1000);
		velocity=0.00006;
		image=new ImageIcon("口袋.PNG");
		rec=new Rectangle(x,y,image.getIconWidth(),image.getIconHeight());
		break;
	case "pig":
		value=10;
		velocity=0.00008;
		image=new ImageIcon("小猪.PNG");
		rec=new Rectangle(x,y,image.getIconWidth(),image.getIconHeight());
		break;
	default:break;
	}
}

//若相撞则返回true，否则返回false
public boolean is_attack(Rectangle myrec) {                 
	if((rec).intersects(myrec)) {
		return true;
	}else {
		return false;
	}
}

//将对应的Thing对象添加到对应的Panel中显示
public void getLabel() {                                                                        
	label=new JLabel(image);
	label.setBounds(x, y, image.getIconWidth(), image.getIconHeight());              //生成Label
	jp.add(label);
}

//将某对象添加到对应面板上从而显示
public void addToPanel() {
	label=new JLabel(image);
	label.setBounds(x, y, image.getIconWidth(), image.getIconHeight());
	jp.add(label);
}

//移动label
public void moveJLabel() {
	label.setBounds(x, y, image.getIconWidth(), image.getIconHeight());
}

//移动rec
public void moverec() {
	rec.setBounds(x, y, image.getIconWidth(), image.getIconHeight());
}
}
