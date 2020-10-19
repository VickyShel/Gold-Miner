package Client;

import DataBase.DataBase;
import DataBase.Player;
import DataBase.TestPassword;
import DataBase.FalsePassword;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPasswordField;

/*
 * 此类用于产生注册时的界面
 */
public class Register extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private Player player;
	private DataBase database;
	private JTextField textField_2;

	/*
	 * 此方法用于产生GUI
	 */
	public Register() {
		database=DataBase.getDataBase();
		player=new Player();
		
		setTitle("Server注册");
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
		lblNewLabel.setBounds(169, 31, 12, 15);
		contentPane.add(lblNewLabel);
		lblNewLabel.setForeground(Color.white);
		
		textField = new JTextField();
		textField.setBounds(216, 28, 126, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("\u540D");
		lblNewLabel_1.setBounds(169, 62, 12, 15);
		contentPane.add(lblNewLabel_1);
		lblNewLabel_1.setForeground(Color.white);
		
		textField_1 = new JTextField();
		textField_1.setBounds(216, 59, 126, 21);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("\u8F93\u5165\u5BC6\u7801");
		lblNewLabel_2.setBounds(137, 93, 58, 15);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("\u786E\u8BA4\u5BC6\u7801");
		lblNewLabel_3.setBounds(137, 123, 58, 15);
		contentPane.add(lblNewLabel_3);
		
		//通过两个radiobutton可以进行显示密码和隐藏密码的转换
		JRadioButton rdbtnNewRadioButton = new JRadioButton("\u9690\u85CF\u5BC6\u7801");
		rdbtnNewRadioButton.setSelected(true);
		rdbtnNewRadioButton.setBounds(140, 151, 89, 23);
		contentPane.add(rdbtnNewRadioButton);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(216, 90, 126, 21);
		contentPane.add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(216, 120, 126, 21);
		contentPane.add(passwordField_1);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("\u663E\u793A\u5BC6\u7801");
		rdbtnNewRadioButton_1.setBounds(226, 151, 127, 23);
		rdbtnNewRadioButton_1.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {                                                  
				// TODO Auto-generated method stub
				if(e.getStateChange()==ItemEvent.SELECTED)
				{//被选中
					passwordField.setEchoChar((char)0);
					passwordField_1.setEchoChar((char)0);
				}else
				{
					passwordField.setEchoChar('*');
					passwordField_1.setEchoChar('*');
				}
			}
		});
		contentPane.add(rdbtnNewRadioButton_1);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(rdbtnNewRadioButton);
		bg.add(rdbtnNewRadioButton_1);
		
		JLabel lblNewLabel_4 = new JLabel("\u957F\u5EA6\u8D85\u8FC78\u4F4D\uFF0C\u5305\u62EC\u5927\u5C0F\u5199\u5B57\u6BCD.\u6570\u5B57.\u5176\u5B83\u7B26\u53F7\uFF08\u4EE5\u4E0A\u56DB\u79CD\u81F3\u5C11\u4E09\u79CD\uFF09");
		lblNewLabel_4.setBounds(59, 205, 367, 28);
		contentPane.add(lblNewLabel_4);
		lblNewLabel_4.setForeground(Color.white);
		
		//确定按钮
		JButton btnNewButton = new JButton("\u786E\u8BA4");
		btnNewButton.setBounds(185, 240, 97, 23);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(new ButtonListener(passwordField,passwordField_1,player));
		
		JLabel lblNewLabel_5 = new JLabel("\u90AE\u7BB1");
		lblNewLabel_5.setBounds(157, 180, 24, 15);
		contentPane.add(lblNewLabel_5);
		
		textField_2 = new JTextField();
		textField_2.setBounds(216, 180, 126, 21);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		this.setVisible(true);
	}
	
	/*
	 * 监听器用于获得玩家填写信息，并对密码进行是否符合标准的判断
	 * 若一切正确，则将会将此信玩家加入到文件数据库当中，并重新显示登陆页面用于玩家登录进入游戏
	 */
	public class ButtonListener implements ActionListener{
		private String string2;
		private String string1;
		private String string3;
		private String password;
		private Player player;
		
		private ButtonListener(JPasswordField passwordField,JPasswordField passwordField_1,Player player) {
			this.player=player;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			password=String.valueOf(passwordField_1.getPassword());
			string1=textField_1.getText();
			string2=textField.getText();
			string3=textField_2.getText();
			boolean flag=true;
			if(!String.valueOf(passwordField.getPassword()).equals(String.valueOf(passwordField_1.getPassword())))
			{
				flag=false;
			}
			boolean flag1=true;
			TestPassword test=new TestPassword();
			flag1=test.testPassword(password);
			if(flag==false) {
				new FalsePassword(player);
			}
			else if(flag1==false) {
				new FalsePassword(player);
			}
			else {
				player.setFirstName(string2);
				player.setLastName(string1);
				player.setPassword(password);
				player.setMail(string3);
				database.addCustomer(player);
				database.setrecord();
				setVisible(false);
				new Login();
			}
		}
	}
}
