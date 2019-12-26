package com.toy.search.utils;

import com.toy.search.constant.Constants;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

public class CipherUtil {
    private static final int AES_KEY_SIZE = 16;

    private static final String KEY_ALGORITHM = "AES";
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    public static final SecretKeySpec TOKEN_KEY;
    public static final PublicKey RSA_PUBLIC_KEY;
    public static final PrivateKey RSA_PRIVATE_KEY;

    static {
        try {
            TOKEN_KEY = CipherUtil.buildTokenKey(Constants.COOKIE_KEY);
            byte[] buffer = Base64.decodeBase64(Constants.RSA_PUBLIC_KEY);
            RSA_PUBLIC_KEY = CipherUtil.buildRSAPublicKey(buffer);

            buffer = Base64.decodeBase64(Constants.RSA_PRIVATE_KEY);
            RSA_PRIVATE_KEY = CipherUtil.buildRSAPrivateKey(buffer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] encByAES(byte[] data, SecretKeySpec key) throws Exception {
        return dealAES(data, key, Cipher.ENCRYPT_MODE);
    }


    public static byte[] decByAES(byte[] data, SecretKeySpec key) throws Exception {
        return dealAES(data, key, Cipher.DECRYPT_MODE);
    }

    private static byte[] dealAES(byte[] data, SecretKeySpec key, int mode)
            throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(mode, key);
        return cipher.doFinal(data);
    }

    public static SecretKeySpec buildTokenKey(byte[] key) throws Exception {
        key = Arrays.copyOfRange(key, 0, AES_KEY_SIZE);
        return new SecretKeySpec(key, KEY_ALGORITHM);
    }



    public static PublicKey buildRSAPublicKey(byte[] key) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(key);
        return keyFactory.generatePublic(keySpec);
    }

    public static PrivateKey buildRSAPrivateKey(byte[] key) throws Exception {
        PKCS8EncodedKeySpec keySpec= new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory= KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    public static byte[] encByAES(byte[] data, byte[] key) throws Exception {
        return dealAES(data, buildTokenKey(key), Cipher.ENCRYPT_MODE);
    }
}
