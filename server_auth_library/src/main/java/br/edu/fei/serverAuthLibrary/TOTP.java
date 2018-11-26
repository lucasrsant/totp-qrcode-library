package br.edu.fei.serverAuthLibrary;

import javax.crypto.SecretKey;

/*package*/ class TOTP {

    private final int returnDigits;
    private final long duration;
    private final SecretKey hashKey;
    private final HashingAlgorithm hashingAlgorithm;

    /*package*/ TOTP(int returnDigits, long duration, SecretKey hashKey, HashingAlgorithm hashingAlgorithm) {
        this.returnDigits = returnDigits;
        this.duration = duration;
        this.hashKey = hashKey;
        this.hashingAlgorithm = hashingAlgorithm;
    }

    /*package*/ int getReturnDigits() {
        return returnDigits;
    }

    /*package*/ long getDuration() {
        return duration;
    }

    /*package*/ SecretKey getHashKey() {
        return hashKey;
    }

    /*package*/ HashingAlgorithm getHashingAlgorithm() {
        return hashingAlgorithm;
    }
}
