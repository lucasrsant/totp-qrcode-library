package br.edu.fei.server;

/*package*/ class IdentifiedUserRegistration {
    /*package*/ String verificationCode;
    /*package*/ String email;
    /*package*/ String deviceId;
    /*package*/ String devicePublicKey;
    private boolean isVerified = false;

    /*package*/ boolean isVerified() {
        return isVerified;
    }

    /*package*/ void setIsVerified() {
        this.isVerified = true;
    }
}
