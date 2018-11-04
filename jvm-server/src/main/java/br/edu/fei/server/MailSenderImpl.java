package br.edu.fei.server;

public class MailSenderImpl implements MailSender {
    @Override
    public void sendVerificationCode(String verificationCode) {
        System.out.println(String.format("The verification code is %s", verificationCode));
    }
}
