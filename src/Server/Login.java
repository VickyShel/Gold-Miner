package Server;

/**
 * 
 * @author xiejiaxin
 * �������ڲ�����½ʱ�Ľ��沢�����ز���
 *
 */
import DataBase.DataBase;
import DataBase.FalsePassword;
import DataBase.Player;
import DataBase.Unexist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/*
 * �˷�������������½ʱ�Ľ���
 * ���С�ȷ�����͡�ע�ᡱ��ť����
 * ��ȷ�������ڼ��������Ƿ���ȷ������ȷ��ɽ�����Ϸ���棬��������������Ľ��棬�������µ�½Ҳ���Ե���һ�����
 * ��ע�ᡱ����ʹ����ҽ���ע��Ȼ���½
 */
public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JPasswordField passwordField;
	private Iterator<Player> playerlist;
	private Player player;
	private Player play;

	public Login() {
		DataBase database=DataBase.getDataBase();
		database.read();
		playerlist=database.getPlayers();
		player=new Player();
		play=new Player();
		
		setTitle("Server��½");
		getContentPane().setLayout(new BorderLayout(0, 0));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		ImageIcon icon=new ImageIcon("16e.PNG");
		JLabel label=new JLabel(icon);
		label.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());
		this.getLayeredPane().add(label,new Integer(-30001));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setLayout(null);
		contentPane.setOpaque(false); 
		
		JLabel lblNewLabel = new JLabel("\u59D3");
		lblNewLabel.setBounds(151, 53, 12, 15);
		contentPane.add(lblNewLabel);
		lblNewLabel.setFont(new Font("����",Font.BOLD,10));
		lblNewLabel.setForeground(Color.white);
		
		JLabel lblNewLabel_1 = new JLabel("\u540D");
		lblNewLabel_1.setBounds(151, 95, 12, 15);
		contentPane.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("����",Font.BOLD,10));
		lblNewLabel_1.setForeground(Color.white);
		
		textField = new JTextField();
		textField.setBounds(198, 50, 134, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(198, 92, 134, 21);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("\u8BF7\u8F93\u5165\u5BC6\u7801");
		lblNewLabel_2.setBounds(114, 138, 74, 15);
		contentPane.add(lblNewLabel_2);
		lblNewLabel_2.setFont(new Font("����",Font.BOLD,10));
		lblNewLabel_2.setForeground(Color.white);
		
		//ͨ������radiobutton���Խ�����ʾ��������������ת��
		JRadioButton rdbtnNewRadioButton = new JRadioButton("\u9690\u85CF\u5BC6\u7801");
		rdbtnNewRadioButton.setSelected(true);
		rdbtnNewRadioButton.setBounds(139, 175, 74, 23);
		contentPane.add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("\u663E\u793A\u5BC6\u7801");
		rdbtnNewRadioButton_1.setBounds(247, 175, 127, 23);
		contentPane.add(rdbtnNewRadioButton_1);
		rdbtnNewRadioButton_1.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {                                                  
				// TODO Auto-generated method stub
				if(e.getStateChange()==ItemEvent.SELECTED)
				{//��ѡ��
					passwordField.setEchoChar((char)0);
				}else
				{
					passwordField.setEchoChar('*');
				}
			}
		});
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(rdbtnNewRadioButton);
		bg.add(rdbtnNewRadioButton_1); 
		
		passwordField = new JPasswordField();
		passwordField.setBounds(198, 135, 134, 21);
		contentPane.add(passwordField);
		
		//��ȷ������ť
		JButton btnNewButton = new JButton("\u786E\u5B9A");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			
				 String string=textField_1.getText(); String str=textField.getText();
				  if(!playerlist.hasNext()) { new Unexist(); }
				  while(playerlist.hasNext()){
				  play=playerlist.next();
				  if((play.getFirstName().equals(str))&&(play.getLastName().equals(string))) {
					  if(play.getPassword().equals(String.valueOf(passwordField.getPassword()))) {
						  new Scene(play);
						  setVisible(false);
						  break;
					  }
					  else {
						  new FalsePassword(play);
					  }
				  }else {
					  continue;
				  }
				 
				//  new Scene(player);
				setVisible(false);
			}
		}});
		btnNewButton.setBounds(116, 218, 97, 23);
		contentPane.add(btnNewButton);
		
		//��ע�ᡱ��ť
		JButton btnNewButton_1 = new JButton("\u6CE8\u518C");
		btnNewButton_1.setBounds(248, 218, 97, 23);
		contentPane.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new Register();
				setVisible(false);
			}
			
		});
		
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		Login login=new Login();
	}
	
	class ImagePanel extends JPanel {
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			ImageIcon icon = new ImageIcon("����\\16e.png");
			g.drawImage(icon.getImage(), 0,0, icon.getIconWidth(),icon.getIconHeight(),null);
		}
	}
}
