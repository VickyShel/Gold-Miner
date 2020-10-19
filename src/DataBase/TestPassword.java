package DataBase;

/*
 * 此类用于检查密码是否符合规范
 */
public class TestPassword {
	//此方法用于检验密码有无超过三个字母连续重复
    public static boolean chongfuZC(String x) {
        for(int i=0;i<x.length()-3;i++) {
            String a=x.substring(i+1);
            String b=x.substring(i,i+3);
            if(a.contains(b)) {
                return true;
            }
        }
        return false;
    }
    
    //判断密码是否超过8位，并且有大小写字母及数字
    public boolean testPassword(String string) {
        String a=string;
        if(a.length()<=8) {
            return false;
        }
        int[] z= {0,0,0};
        for(int i=0;i<a.length();i++) {
            if(a.charAt(i)>='0'&&a.charAt(i)<='9') {
                z[0]=1;
            }
            else if(a.charAt(i)>='a'&&a.charAt(i)<='z') {
                z[1]=1;
            }
            else if(a.charAt(i)>='A'&&a.charAt(i)<='Z') {
                z[2]=1;
            }
        }
        if(chongfuZC(a)) {
            return false;
        }
        if((z[0]+z[1]+z[2])==3) {
            return true;
        }
        else {
        	return false;
        }
    }
}
