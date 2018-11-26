package br.edu.fei.auth.totp;

import javax.crypto.SecretKey;

public class TOTP {

    private final int returnDigits;
    private final long duration;
    private final SecretKey hashKey;
    private final HashingAlgorithm hashingAlgorithm;

    public TOTP(int returnDigits, long duration, SecretKey hashKey, HashingAlgorithm hashingAlgorithm) {
        this.returnDigits = returnDigits;
        this.duration = duration;
        this.hashKey = hashKey;
        this.hashingAlgorithm = hashingAlgorithm;
    }

    public int getReturnDigits() {
        return returnDigits;
    }

    public long getDuration() {
        return duration;
    }

    public SecretKey getHashKey() {
        return hashKey;
    }

    public HashingAlgorithm getHashingAlgorithm() {
        return hashingAlgorithm;
    }
}
