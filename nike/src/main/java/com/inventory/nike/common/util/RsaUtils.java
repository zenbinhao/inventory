//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.inventory.nike.common.util;


import com.inventory.nike.manager.vo.RsaKeys;

import javax.crypto.Cipher;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class RsaUtils {
    private static final String KEY_ALGORITHM = "RSA";
    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";
    private static final int MAX_ENCRYPT_BLOCK = 117;
    private static final int MAX_DECRYPT_BLOCK = 128;
    private static final int INITIALIZE_LENGTH = 1024;

    public RsaUtils() {
    }

    private static Map<String, Object> genKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap(2);
        keyMap.put("RSAPublicKey", publicKey);
        keyMap.put("RSAPrivateKey", privateKey);
        return keyMap;
    }

    private static String sign(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(privateK);
        signature.update(data);
        return Base64.getEncoder().encodeToString(signature.sign());
    }

    private static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(Base64.getDecoder().decode(sign));
    }

    private static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(2, privateK);
        return subsectionProcessing(encryptedData, cipher);
    }

    private static byte[] subsectionProcessing(byte[] data, Cipher cipher) throws Exception {
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;

        for(int i = 0; inputLen - offSet > 0; offSet = i * 128) {
            byte[] cache;
            if (inputLen - offSet > 128) {
                cache = cipher.doFinal(data, offSet, 128);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }

            out.write(cache, 0, cache.length);
            ++i;
        }

        byte[] outData = out.toByteArray();
        out.close();
        return outData;
    }

    private static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(2, publicK);
        return subsectionProcessing(encryptedData, cipher);
    }

    private static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(1, publicK);
        return subsectionProcessing(data, cipher);
    }

    private static byte[] encryptByPrivateKey(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(1, privateK);
        return subsectionProcessing(data, cipher);
    }

    private static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key)keyMap.get("RSAPrivateKey");
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    private static String getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key)keyMap.get("RSAPublicKey");
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    private static String encryptedDataOnJava(String data, String publicKey) throws Exception {
        String str = Base64.getEncoder().encodeToString(encryptByPublicKey(data.getBytes(), publicKey));
        return str;
    }

    public static String decryptDataOnJava(String data, String privateKey) throws Exception {
        String temp = "";
        byte[] rs = Base64.getDecoder().decode(data);
        temp = new String(decryptByPrivateKey(rs, privateKey), "UTF-8");
        return temp;
    }

    public static String getPublicKey(HttpSession session) throws Exception {
        Map<String, Object> makKey = genKeyPair();
        String publicKey = getPublicKey(makKey);
        String privateKey = getPrivateKey(makKey);
        session.setAttribute("RSAPrivateKey", privateKey);
        return publicKey;
    }

    public static RsaKeys getRsaKeys() throws Exception {
        String suffix = "rsaKey_";
        RsaKeys keys = new RsaKeys();
        Map<String, Object> makKey = genKeyPair();
        keys.setPublicKey(getPublicKey(makKey));
        keys.setPrivateKey(getPrivateKey(makKey));
        keys.setRsaKey(suffix + RandomUtil.uuid());
        return keys;
    }

    public static String decryptPass(HttpSession session, String data) throws Exception {
        String privateKey = (String)session.getAttribute("RSAPrivateKey");
        String pass = decryptDataOnJava(data, privateKey);
        return pass;
    }

    public static void main(String[] args) throws Exception {
        RsaKeys rsaKeys = getRsaKeys();
        System.out.println("公钥：" + rsaKeys.getPublicKey());
        System.out.println("私钥：" + rsaKeys.getPrivateKey());
        String password = "yzsh123@";
        String enc = encryptedDataOnJava(password, rsaKeys.getPublicKey());
        String dec = decryptDataOnJava(enc, rsaKeys.getPrivateKey());
        System.out.println("密文：" + enc);
        System.out.println("明文：" + dec);
    }
}
