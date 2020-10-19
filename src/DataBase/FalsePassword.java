package DataBase;

import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Color;

/*
 * 此类用于密码错误时
 * 其上提醒应该输入正确的密码并对忘记密码的玩家提供发送邮件找回密码的服务
 */
public class FalsePassword extends JFrame {

	private JPanel contentPane;
	
	public FalsePassword(Player player) {
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
		
		JLabel lblNewLabel = new JLabel("\u5BC6\u7801\u8F93\u5165\u4E0D\u6B63\u786E\uFF0C\u8BF7\u91CD\u65B0\u8F93\u5165\uFF01");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setBounds(132, 83, 168, 32);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\u6CE8\u610F\u5BC6\u7801\u683C\u5F0F\u662F\u5426\u7B26\u5408\u8981\u6C42");
		lblNewLabel_1.setForeground(Color.GRAY);
		lblNewLabel_1.setBounds(142, 124, 182, 15);
		contentPane.add(lblNewLabel_1);
		
		//确定按钮
		JButton btnNewButton = new JButton("\u786E\u5B9A");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnNewButton.setBounds(163, 186, 97, 23);
		contentPane.add(btnNewButton);
		
		//密码找回按钮
		JButton btnNewButton_1 = new JButton("\u5FD8\u8BB0\u5BC6\u7801");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MailOperation mail=new MailOperation("smtp.163.com","smtp.163.com","xiejiaxin0902@163.com", "127598810212xjxA");
				try {
					mail.sendingMimeMail("xiejiaxin0902@163.com", player.getMail(), "", "", "找回密码！", player.getPassword());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_1.setBounds(163, 230, 97, 23);
		contentPane.add(btnNewButton_1);
		
		this.setVisible(true);
	}
}
