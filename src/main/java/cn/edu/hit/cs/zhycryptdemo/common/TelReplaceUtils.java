package cn.edu.hit.cs.zhycryptdemo.common;

public class TelReplaceUtils {


    public static String replaceEnc(String originalTel) {
        String newTel = String.format("%s%s%s%s%s%s%s%s%s%s%s",
                addOperation(originalTel, 0),
                addOperation(originalTel, 10),
                addOperation(originalTel, 9),
                addOperation(originalTel, 8),
                addOperation(originalTel, 7),
                addOperation(originalTel, 6),
                addOperation(originalTel, 5),
                addOperation(originalTel, 4),
                addOperation(originalTel, 3),
                addOperation(originalTel, 2),
                addOperation(originalTel, 1)
        );
        return newTel;
    }

    public static String replaceDec(String encTel) {
        String tempTel = String.format("%s%s%s%s%s%s%s%s%s%s%s",
                getInt(encTel, 0),
                getInt(encTel, 10),
                getInt(encTel, 9),
                getInt(encTel, 8),
                getInt(encTel, 7),
                getInt(encTel, 6),
                getInt(encTel, 5),
                getInt(encTel, 4),
                getInt(encTel, 3),
                getInt(encTel, 2),
                getInt(encTel, 1)
        );

        String originalTel = String.format("%s%s%s%s%s%s%s%s%s%s%s",
                substractOperation(tempTel, 0),
                substractOperation(tempTel, 1),
                substractOperation(tempTel, 2),
                substractOperation(tempTel, 3),
                substractOperation(tempTel, 4),
                substractOperation(tempTel, 5),
                substractOperation(tempTel, 6),
                substractOperation(tempTel, 7),
                substractOperation(tempTel, 8),
                substractOperation(tempTel, 9),
                substractOperation(tempTel, 10)
        );
        return originalTel;
    }


    private static int addOperation(String originalTel, int index) {
        int temp = getInt(originalTel, index) + index;
        int tempMod10 = temp % 10;
        return tempMod10;
    }

    private static int substractOperation(String originalTel, int index) {
        int temp = getInt(originalTel, index) - index + 10;
        int tempMod10 = temp % 10;
        return tempMod10;
    }


    private static int getInt(String tel, int index) {
        String t = String.valueOf(tel.charAt(index));
        int temp = Integer.valueOf(t);
        return temp;
    }


    public static void main(String[] args) {
        String tel = "18846448756";
        String telEnc = replaceEnc(tel);
        String telDec = replaceDec(telEnc);
        System.out.println(telEnc + " " + telDec);
    }


}
