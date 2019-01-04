package br.edu.fei.serverAuthLibrary;

/***
 * Represents a basic verification code sender.
 */
public interface MailSender {

    /***
     * Sends a message containing the verification code to the user.
     * @param verificationCode The verification code generated during the device registration process
     */
    void sendVerificationCode(String verificationCode);
}
