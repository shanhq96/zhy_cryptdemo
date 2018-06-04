package cn.edu.hit.cs.zhycryptdemo.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

@Slf4j
public class AESUtils {
    private static final String defaultCharset = "UTF-8";
    private static final String KEY_AES = "AES";
    private static final String KEY = "123456";

    public static String encrypt(String data) {
        return encrypt(data, KEY);
    }

    public static String decrypt(String data) {
        return decrypt(data, KEY);
    }


    public static void main(String[] args) throws Exception {

        /**
         *
         FieldTypeComment
         idint(11) NOT NULL
         employeeid varchar(255) NOT NULL员工id
         name_encvarchar(255) NOT NULL员工姓名_AES
         age_encvarchar(255) NOT NULL员工年龄_AES
         age_hombigint(20) NOT NULL员工年龄_paillier
         tel_encvarchar(255) NOT NULL员工联系方式_AES
         idnumber_encvarchar(255) NOT NULL员工身份证号_AES
         income_encvarchar(255) NOT NULL员工月收入_AES
         income_hombigint(20) NOT NULL员工月收入_paillier
         month_encvarchar(255) NOT NULL工作月数_AES
         month_hombigint(20) NOT NULL工作月数_paillier
         outcome_encvarchar(255) NOT NULL员工月支出_AES
         outcome_hombigint(20) NOT NULL员工月支出_paillier
         userid
         */

        String employeeid = "1143710110";
        String name = "zhy";
        int age = 18;
        String tel = "18812341234";
        String idnumber = "230122123412341234";
        int income = 50;
        int month = 5;
        int outcome = 60;
        String userid = "1";
        String nameEnc = encrypt(name);
        String nameDec = decrypt(nameEnc);
        System.out.println("nameEnc" + nameEnc + " nameDec" + nameDec);
        System.out.println("name：" + encrypt(name));

        System.out.println("age：" + encrypt(String.valueOf(age)));
        System.out.println("tel：" + encrypt(tel));
        System.out.println("idnumber：" + encrypt(idnumber));
        System.out.println("income：" + encrypt(String.valueOf(income)));
        System.out.println("month：" + encrypt(String.valueOf(month)));
        System.out.println("outcome：" + encrypt(String.valueOf(outcome)));

    }

    /**
     * 加密
     *
     * @param data 需要加密的内容
     * @param key  加密密码
     * @return
     */
    public static String encrypt(String data, String key) {
        return doAES(data, key, Cipher.ENCRYPT_MODE);
    }

    /**
     * 解密
     *
     * @param data 待解密内容
     * @param key  解密密钥
     * @return
     */
    public static String decrypt(String data, String key) {
        return doAES(data, key, Cipher.DECRYPT_MODE);
    }

    /**
     * 加解密
     *
     * @param data     待处理数据
     * @param password 密钥
     * @param mode     加解密mode
     * @return
     */
    private static String doAES(String data, String key, int mode) {
        try {
            if (StringUtils.isEmpty(data) || StringUtils.isEmpty(key)) {
                return null;
            }
            //判断是加密还是解密
            boolean encrypt = mode == Cipher.ENCRYPT_MODE;
            byte[] content;
            //true 加密内容 false 解密内容
            if (encrypt) {
                content = data.getBytes(defaultCharset);
            } else {
                content = parseHexStr2Byte(data);
            }
            //1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator kgen = KeyGenerator.getInstance(KEY_AES);
            //2.根据ecnodeRules规则初始化密钥生成器
            //生成一个128位的随机源,根据传入的字节数组
            kgen.init(128, new SecureRandom(key.getBytes()));
            //3.产生原始对称密钥
            SecretKey secretKey = kgen.generateKey();
            //4.获得原始对称密钥的字节数组
            byte[] enCodeFormat = secretKey.getEncoded();
            //5.根据字节数组生成AES密钥
            SecretKeySpec keySpec = new SecretKeySpec(enCodeFormat, KEY_AES);
            //6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance(KEY_AES);// 创建密码器
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(mode, keySpec);// 初始化
            byte[] result = cipher.doFinal(content);
            if (encrypt) {
                //将二进制转换成16进制
                return parseByte2HexStr(result);
            } else {
                return new String(result, defaultCharset);
            }
        } catch (Exception e) {
            log.error("AES 密文处理异常", e);
        }
        return null;
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

}
