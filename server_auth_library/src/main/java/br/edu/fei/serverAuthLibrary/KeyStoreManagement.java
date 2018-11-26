package br.edu.fei.serverAuthLibrary;

import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
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

    private PublicKey getPublicKey() {
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
