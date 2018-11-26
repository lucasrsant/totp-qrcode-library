package br.edu.fei.auth.security;

import java.io.FileInputStream;
import java.security.*;
import java.util.Base64;

public class KeyStoreManagement {

    private PublicKey publicKey;
    private PrivateKey privateKey;

    public KeyStoreManagement(String jksFilePath, String alias, String password) throws Exception {
        readKeys(jksFilePath, alias, password);
    }

    public String getPublicKeyAsString() {
        return Base64.getEncoder().encodeToString(getPublicKey().getEncoded());
    }

    public String getPrivateKeyAsString() {
        return Base64.getEncoder().encodeToString(getPrivateKey().getEncoded());
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    private void readKeys(String jksFilePath, String alias, String password) throws Exception {

        FileInputStream inputStream = null;

        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            inputStream = new FileInputStream(jksFilePath);
            keyStore.load(inputStream, password.toCharArray());

            Key key = keyStore.getKey(alias, password.toCharArray());

            if(key instanceof PrivateKey) {
                this.publicKey = keyStore.getCertificate(alias).getPublicKey();
                this.privateKey = (PrivateKey)key;
            }
        }
        finally {
            if(inputStream != null)
                inputStream.close();
        }
    }
}
