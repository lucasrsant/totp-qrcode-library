package br.edu.fei.auth.security;

import java.io.FileInputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.util.Base64;

public class KeyStoreManagement {

    private String jksFilePath;
    private String alias;
    private String password;

    private String publicKey;

    public KeyStoreManagement(String jksFilePath, String alias, String password) {
        this.alias = alias;
        this.password = password;
        this.jksFilePath = jksFilePath;
    }

    public String getPublicKey() throws Exception {
        if (publicKey == null || publicKey.isEmpty())
            publicKey = readPublicKey();

        return publicKey;
    }

    private String readPublicKey() throws Exception {

        FileInputStream inputStream = null;

        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            inputStream = new FileInputStream(jksFilePath);
            keyStore.load(inputStream, password.toCharArray());

            Key key = keyStore.getKey(alias, password.toCharArray());

            if(key instanceof PrivateKey) {
                Certificate certificate = keyStore.getCertificate(alias);
                PublicKey publicKey = certificate.getPublicKey();
                return Base64.getEncoder().encodeToString(publicKey.getEncoded());
            }
        }
        finally {
            if(inputStream != null)
                inputStream.close();
        }

        return "";
    }
}
