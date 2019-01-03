package br.edu.fei.serverAuthLibrary;

/***
 * Represents a basic verification code sender.
 */
public interface MailSender {
    void sendVerificationCode(String verificationCode);
}
