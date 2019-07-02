package com.itheima.test;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
public class Md5Test {

    /**
     * Md5Hash
     *   它是apache提供的，就是在md5加密的思想上进行散列算法。
     *   散列算法，就是把当前的数据使用密钥进行打散，然后重新排列
     * 把密钥称之为盐
     *
     * 不仅能控制加的盐是什么，还能控制加几次盐
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(new Md5Hash("1234","C",2));
    }

    /**
     * MD5加密
     *   它在加密完成后，组成的加密字符串是由任意字符组成的。这些任意字符中有可能有特殊字符
     *   我们需要把任意字符组成的字符串，转成有可见字符组成。
     * 可见字符：
     *   可以通过键盘直接输入的字符。
     * 转换的方式是用Base64编码
     *      为什么叫编码，是因为，有编码就有解码。
     *      而加密是单向不可逆的。
     *
     *      编码的类是BASE64Encoder
     *      解码的类是BASE64Decoder
     *
     * @param args

    public static void main(String[] args)throws Exception {
        String password = "1234";
        //1.创建加密对象
        MessageDigest md5 = MessageDigest.getInstance("md5");
        //2.对密码加密
        byte[] bytes = md5.digest(password.getBytes());
        //3.输出加密后的密码
//        String md5Password= new String(bytes,0,bytes.length);
        //4.Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        //5.编码输出

        System.out.println(encoder.encode(bytes));
    }*/

    /**
     * Base64是通过64个基础字符进行的转换。
     *    a~z   A~Z 0~9  + /
     *    它是由0~63指定的
     *    如果没有对上的用=
     * base64的转换过程就是3变4  就是把3字节变成4字节
     * 一个字节占8位
     *      s12三个字符来转换base64
     *      转换的结果是czEy
     *
     *   它先把s12字符串转成3个字符
     *   s
     *   1
     *   2
     *   在把3个字符对应的ascii码取出来
     *      s  115
     *      1  49
     *      2  50
     *   把这3个数转成二进制的
     *      115  1110011
     *      49   110001
     *      50   110010
     *  把他们补全8位
     *      01110011 00110001  00110010
     *  三字节变四字节
     *      00011100 00110011 00000100 00110010
     *  把字节的转成十进制
     *      28  51  4  50
     * @param args

    public static void main(String[] args) {
        BASE64Encoder encoder = new BASE64Encoder();
        String str = encoder.encode("s12".getBytes());
        System.out.println(str);
    }*/
}
