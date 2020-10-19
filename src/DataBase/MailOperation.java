package DataBase;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/*
 * ���ڷ����ʼ�
 */
public class MailOperation {
		private Properties props; //ϵͳ����
		private Session mailSession; //�ʼ��Ự����
		private MimeMessage mimeMsg; //MIME�ʼ�����
		
		public MailOperation(String SMTPHost, String Port, String MailUsername, String MailPassword) {
			Auth au = new Auth(MailUsername, MailPassword);		//����ϵͳ����
			props=java.lang.System.getProperties(); //���ϵͳ���Զ���
			props.put("mail.smtp.host", SMTPHost); //����SMTP����
			props.put("mail.smtp.port", Port); //���÷���˿ں�
			props.put("mail.smtp.auth", "true"); //ͬʱͨ����֤//����ʼ��Ự����
			mailSession = Session.getInstance(props, au);
		}
		
		public boolean sendingMimeMail(String MailFrom, String MailTo,String MailCopyTo, String MailBCopyTo, String MailSubject,String MailBody) {
			try {
				//����MIME�ʼ�����
				mimeMsg=new MimeMessage(mailSession);
				//���÷�����
				mimeMsg.setFrom(new InternetAddress(MailFrom));
				//����������
				if(MailTo!=null){
					mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(MailTo));
				}
				//���ó�����
				if(MailCopyTo!=null){
					mimeMsg.setRecipients(javax.mail.Message.RecipientType.CC,InternetAddress.parse(MailCopyTo));
				}
				//���ð�����
				if(MailBCopyTo!=null){
					mimeMsg.setRecipients(javax.mail.Message.RecipientType.BCC,InternetAddress.parse(MailBCopyTo));
				}
				//�����ʼ�����
				mimeMsg.setSubject(MailSubject,"utf-8");
				//�����ʼ����ݣ����ʼ�body����ת��ΪHTML��ʽ
				mimeMsg.setContent(MailBody,"text/html;charset=utf-8");
				//�����ʼ�
				Transport.send(mimeMsg);
				return true;
			} catch (Exception e) {
					e.printStackTrace();
					return false;
			}
		}
		
		public class Auth extends Authenticator {
	    	private String username = "";
	    	private String password = "";
	    	public Auth(String username, String password) {
	    		this.username = username;
	    		this.password = password;
	    	}
	    	public PasswordAuthentication getPasswordAuthentication() {
	    		return new PasswordAuthentication(username, password);
	    	}
	    }
}
	
    
    


