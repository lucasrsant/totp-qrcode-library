package br.edu.fei.auth.totp;

public class TOTP {

    private final int returnDigits;
    private final long duration;
    private final String hashKey;
    private final HashingAlgorithm hashingAlgorithm;

    public TOTP(int returnDigits, long duration, String hashKey, HashingAlgorithm hashingAlgorithm) {
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

    public String getHashKey() {
        return hashKey;
    }

    public HashingAlgorithm getHashingAlgorithm() {
        return hashingAlgorithm;
    }
}
