package offer;

public class pra4 {
    public static String repalce(StringBuffer str){
        if(str == null){throw new NullPointerException();}
        //if(str.equals("")){return str.toString();}

        //统计空格的个数
        int spaceNum=0;
        int originalLen = str.length();
        for(int i=0;i<originalLen;i++){
            if(str.charAt(i) == ' '){
                spaceNum++;
            }
        }
        int addSpace = spaceNum*2;
        str.append(new char[addSpace]);
        int totalLength = originalLen+addSpace;
        int p1=originalLen-1, p2= totalLength-1;
        while(p1>=0 && p2>=0){
            if(str.charAt(p1)==' '){
                str.setCharAt(p2--,'0');
                str.setCharAt(p2--,'2');
                str.setCharAt(p2--,'%');
                //p2 -= 3;
            }
            else{
                str.setCharAt(p2--,str.charAt(p1));
            }
            p1--;
        }
        return str.toString();
    }
    public static void main(String[] args){

    }

}
