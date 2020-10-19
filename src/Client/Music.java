package Client;

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
        try{  
            mus = new File("4469_1.wav").toURI().toURL();   
        } catch (MalformedURLException e) {  
                e.printStackTrace();  
        }  
        jp.flag=true;
        sn = JApplet.newAudioClip(mus);
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
