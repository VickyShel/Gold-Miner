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
 * 此类是当玩家并未注册就登陆时所产生的界面
 */
public class Unexist extends JFrame {

	private JPanel contentPane;
	
	public Unexist() {
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
		
		JLabel lblNewLabel = new JLabel("您未注册！");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setBounds(188, 84, 60, 32);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("请回到上页面进行注册......");
		lblNewLabel_1.setForeground(Color.GRAY);
		lblNewLabel_1.setBounds(147, 123, 164, 15);
		contentPane.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("\u786E\u5B9A");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnNewButton.setBounds(172, 171, 97, 23);
		contentPane.add(btnNewButton);
		
		this.setVisible(true);
	}
}
