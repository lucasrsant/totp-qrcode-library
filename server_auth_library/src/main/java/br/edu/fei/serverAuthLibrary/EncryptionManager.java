package br.edu.fei.serverAuthLibrary;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

public class EncryptionManager {

    private static byte[] encrypt(byte[] data, Key key, String algorithm) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, key);

            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static byte[] decrypt(EncryptedPayload encryptedPayload, Key key) {
        byte[] encryptedSessionKey = Base64.getDecoder().decode(encryptedPayload.sessionKey);
        byte[] encryptedContent = Base64.getDecoder().decode(encryptedPayload.content);

        byte[] sessionKey = EncryptionManager.decrypt(encryptedSessionKey, key, "RSA/ECB/PKCS1Padding");
        return EncryptionManager.decrypt(encryptedContent, sessionKey, "AES");
    }

    private static byte[] decrypt(byte[] cipherText, Key key, String algorithm) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, key);

            return cipher.doFinal(cipherText);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static byte[] decrypt(byte[] cipherText, byte[] key, String algorithm) {
        try {
            Key secretKey = new SecretKeySpec(key, 0, key.length, algorithm);
            return decrypt(cipherText, secretKey, algorithm);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
