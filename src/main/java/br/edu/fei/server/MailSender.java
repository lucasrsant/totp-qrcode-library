package br.edu.fei.server;

public interface MailSender {
    void sendVerificationCode(String verificationCode);
}
