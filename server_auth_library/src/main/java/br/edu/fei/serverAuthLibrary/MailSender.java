package br.edu.fei.serverAuthLibrary;

public interface MailSender {
    void sendVerificationCode(String verificationCode);
}
