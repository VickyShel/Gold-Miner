package Server;

import java.applet.*; 
import java.net.*; 
import java.io.*; 
import javax.swing.JApplet; 

/*
 * 用于音频的播放
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
        sn = JApplet.newAudioClip(mus);     //虽然AudioClip 是一个接口，不能直接创建实例，但是java.applet.Applet 对象提供了一个静态的方法newAudioClip()，因此可以直接利用如下的语句，得到一个AudioClip 的实例引用：
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
