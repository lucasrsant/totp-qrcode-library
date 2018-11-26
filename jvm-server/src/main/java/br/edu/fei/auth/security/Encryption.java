package br.edu.fei.auth.security;

import javax.crypto.Cipher;

public class Encryption {

    private KeyStoreManagement keyStoreManagement;

    public Encryption(KeyStoreManagement keyStoreManagement) {
        this.keyStoreManagement = keyStoreManagement;
    }

    public byte[] encrypt(byte[] payload) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, keyStoreManagement.getPublicKey());
        return cipher.doFinal(payload);
    }

    public byte[] decrypt(byte[] cipherText) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, keyStoreManagement.getPrivateKey());
        return cipher.doFinal(cipherText);
    }
}
