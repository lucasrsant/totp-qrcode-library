package br.edu.fei.server.payloads;

public class ConfirmValidationCodeRequest {
    public String emailAddress;
    public String verificationCode;
    public String deviceId;
}
