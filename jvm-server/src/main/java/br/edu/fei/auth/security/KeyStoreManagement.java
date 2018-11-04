package br.edu.fei.auth.security;

import java.io.FileInputStream;
import java.security.*;
import java.util.Base64;

import org.jetbrains.annotations.Nullable;

public class KeyStoreManagement {

    private String jksFilePath;
    private String alias;
    private String password;

    private PublicKey publicKey;

    public KeyStoreManagement(String jksFilePath, String alias, String password) {
        this.alias = alias;
        this.password = password;
        this.jksFilePath = jksFilePath;
    }

    public String getPublicKeyAsString() throws Exception {
        return Base64.getEncoder().encodeToString(getPublicKey().getEncoded());
    }

    public PublicKey getPublicKey() throws Exception {
        if (publicKey == null)
            publicKey = readPublicKey();

        return publicKey;
    }

    public PrivateKey getPrivateKey() throws Exception {
        return readPrivateKey();
    }

    private @Nullable PublicKey readPublicKey() throws Exception {

        FileInputStream inputStream = null;

        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            inputStream = new FileInputStream(jksFilePath);
            keyStore.load(inputStream, password.toCharArray());

            Key key = keyStore.getKey(alias, password.toCharArray());

            if(key instanceof PrivateKey)
                return keyStore.getCertificate(alias).getPublicKey();
        }
        finally {
            if(inputStream != null)
                inputStream.close();
        }

        return null;
    }

    private @Nullable PrivateKey readPrivateKey() throws Exception {

        FileInputStream inputStream = null;

        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            inputStream = new FileInputStream(jksFilePath);
            keyStore.load(inputStream, password.toCharArray());

            Key key = keyStore.getKey(alias, password.toCharArray());

            if(key instanceof PrivateKey)
                return (PrivateKey)key;
        }
        finally {
            if(inputStream != null)
                inputStream.close();
        }

        return null;
    }
}
