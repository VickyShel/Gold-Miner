package Server;

import java.applet.*; 
import java.net.*; 
import java.io.*; 
import javax.swing.JApplet; 

/*
 * ������Ƶ�Ĳ���
 */
public class Music extends JApplet
{ 
	AudioClip sn;
	Scene jp;
	public Music(Scene j) {
		jp=j;
	}
    public void playonce() {
    	URL mus=null;    
        try   
        {  
        mus = new File("4469_1.wav").toURI().toURL();   
        } catch (MalformedURLException e) {  
                e.printStackTrace();  
        }  
        jp.flag=true;
        sn = JApplet.newAudioClip(mus);     //��ȻAudioClip ��һ���ӿڣ�����ֱ�Ӵ���ʵ��������java.applet.Applet �����ṩ��һ����̬�ķ���newAudioClip()����˿���ֱ���������µ���䣬�õ�һ��AudioClip ��ʵ�����ã�
        sn.play(); 
    }
    public void chainstop() {
    	if(jp.flag==true) {
    		sn.stop();
    	    jp.flag=false;
    	}
    	else;
    }
} 
