package br.edu.fei.serverAuthLibrary;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class AuthenticationSession {
    private String id;
    private SecretKey hashKey;
    private String oneTimePassword;

    /*package*/ AuthenticationSession(int duration) {
        id = UUID.randomUUID().toString();
        hashKey = generateKey();
        oneTimePassword = TOTPGenerator.generate(new TOTP(8, duration, hashKey, HashingAlgorithm.SHA_256));
    }

    /*package*/ String getId() {
        return id;
    }

    /*package*/ SecretKey getHashKey() {
        return hashKey;
    }

    /*package*/ String getOneTimePassword() {
        return oneTimePassword;
    }

    private SecretKey generateKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            keyGenerator.init(256);
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }   

        return null;
    }
}
