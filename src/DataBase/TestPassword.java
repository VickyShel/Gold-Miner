package DataBase;

/*
 * �������ڼ�������Ƿ���Ϲ淶
 */
public class TestPassword {
	//�˷������ڼ����������޳���������ĸ�����ظ�
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
    
    //�ж������Ƿ񳬹�8λ�������д�Сд��ĸ������
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
