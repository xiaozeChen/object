package com.cxz.utils.encrypt;

import com.cxz.utils.encrypt.encode.Base64Util;
import com.cxz.utils.encrypt.oneway.MD5Util;
import com.cxz.utils.encrypt.oneway.SHAUtil;
import com.cxz.utils.encrypt.symmetric.AESUtil;
import com.cxz.utils.encrypt.symmetric.DESUtil;
import com.cxz.utils.encrypt.unsymmetric.RSAUtil;

import org.junit.Test;

import java.util.Map;

import javax.crypto.Cipher;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private static String testStr = "这是一句测试文本！/This is a test string!/1234567890";

    @Test
    public void addition_isCorrect() throws Exception {
        System.out.println("base64");
        //base64加密解密测试
        String base64EncodeStr = Base64Util.base64EncodeStr(testStr);
        System.out.println("编码：" + base64EncodeStr);
        String base64DecodedStr = Base64Util.base64DecodedStr(base64EncodeStr);
        System.out.println("解码：" + base64DecodedStr);
        //MD5字符串加密测试
        System.out.println("MD5");
        System.out.println("空字符串：" + MD5Util.md5(""));
        System.out.println(MD5Util.md5(testStr));
        System.out.println(MD5Util.md5(testStr, "slat"));
        //MD5字符串多次加密测试
        System.out.println("0:" + MD5Util.md5(testStr, 0));
        System.out.println("1:" + MD5Util.md5(testStr, 1));
        System.out.println("2:" + MD5Util.md5(testStr, 2));
        System.out.println("3:" + MD5Util.md5(testStr, 3));
        //MD5文件测试
//        File file = new File("fileName");
//        assertEquals("md5文件测试", MD5Util.md5(file));
        //SHA测试
        System.out.println("SHA");
        System.out.println("SHA_null" + SHAUtil.sha(testStr, null));
        System.out.println("SHA224" + SHAUtil.sha(testStr, SHAUtil.SHA224));
        System.out.println("SHA256" + SHAUtil.sha(testStr, SHAUtil.SHA256));
        System.out.println("SHA384" + SHAUtil.sha(testStr, SHAUtil.SHA384));
        System.out.println("SHA512" + SHAUtil.sha(testStr, SHAUtil.SHA512));
        // ASE 字符串加密解密测试
        System.out.println("aes");
        String key1 = "1234567890123456";
        System.out.println("原数据 = " + testStr);
        String aesStr1 = AESUtil.aes(testStr, key1, Cipher.ENCRYPT_MODE);
        System.out.println("加密后 = " + aesStr1);
        String result1 = AESUtil.aes(aesStr1, key1, Cipher.DECRYPT_MODE);
        System.out.println("解密后 = " + result1);
        // DES 字符串加密解密测试
        System.out.println("des");
        String key2 = "1234567890123456";
        System.out.println("原数据 = " + testStr);
        String aesStr2 = DESUtil.des(testStr, key2, Cipher.ENCRYPT_MODE);
        System.out.println("加密后 = " + aesStr2);
        String result2 = DESUtil.des(aesStr2, key2, Cipher.DECRYPT_MODE);
        System.out.println("解密后 = " + result2);
        System.out.println("rsa");

        // RSA 字符串加密解密测试
        byte[] data = testStr.getBytes();
        // 密钥与数字签名获取
        Map<String, Object> keyMap = RSAUtil.getKeyPair();
        String publicKey = RSAUtil.getKey(keyMap, true);
        System.out.println("rsa获取公钥： " + publicKey);
        String privateKey = RSAUtil.getKey(keyMap, false);
        System.out.println("rsa获取私钥： " + privateKey);

        // 公钥加密私钥解密
        byte[] rsaPublic = RSAUtil.rsa(data, publicKey, RSAUtil.RSA_PUBLIC_ENCRYPT);
        System.out.println("rsa公钥加密： " + new String(rsaPublic));
        System.out.println("rsa私钥解密： " + new String(RSAUtil.rsa(rsaPublic, privateKey, RSAUtil.RSA_PRIVATE_DECRYPT)));

        // 私钥加密公钥解密
        byte[] rsaPrivate = RSAUtil.rsa(data, privateKey, RSAUtil.RSA_PRIVATE_ENCRYPT);
        System.out.println("rsa私钥加密： " + new String(rsaPrivate));
        System.out.println("rsa公钥解密： " + new String(RSAUtil.rsa(rsaPrivate, publicKey, RSAUtil.RSA_PUBLIC_DECRYPT)));

        // 私钥签名及公钥签名校验
        String signStr = RSAUtil.sign(rsaPrivate, privateKey);
        System.out.println("rsa数字签名生成： " + signStr);
        System.out.println("rsa数字签名校验： " + RSAUtil.verify(rsaPrivate, publicKey, signStr));
    }
}