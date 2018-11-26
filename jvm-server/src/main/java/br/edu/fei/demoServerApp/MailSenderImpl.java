package br.edu.fei.demoServerApp;

import br.edu.fei.serverAuthLibrary.MailSender;

public class MailSenderImpl implements MailSender {
    @Override
    public void sendVerificationCode(String verificationCode) {
        System.out.println(String.format("The verification code is %s", verificationCode));
    }
}
