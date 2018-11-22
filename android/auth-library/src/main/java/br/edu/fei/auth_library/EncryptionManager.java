package br.edu.fei.auth_library;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

/*package*/ class EncryptionManager {
    /*package*/ static EncryptedPayload encrypt(byte[] plainText) {
        EncryptedPayload encryptedPayload = new EncryptedPayload();

        try {
            PublicKey serverPublicKey = KeyManager.getServerPublicKey();

            Key sessionKey = generateSessionKey();

            if(sessionKey != null) {
                encryptedPayload.sessionKey = encrypt(sessionKey.getEncoded(), serverPublicKey, "RSA/ECB/PKCS1Padding");
                encryptedPayload.content = encrypt(plainText, sessionKey, "AES");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return encryptedPayload;
    }

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

    private static Key generateSessionKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}
