package br.edu.fei.auth.security;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;

public class Security {

    private KeyStore keyStore;

    public Security(String jksFilePath) throws IOException {
        FileInputStream inputStream = null;

        try {
            String password = "1234567890";
            keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            inputStream = new FileInputStream(jksFilePath);
            keyStore.load(inputStream, password.toCharArray());
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if(inputStream != null)
                inputStream.close();
        }
    }

    public String getPublicKey() {
        return "blah";
    }
}
