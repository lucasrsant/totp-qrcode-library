package br.edu.fei.server.payloads;

public class ConfirmValidationCodeRequest {
    public String email;
    public String verificationCode;
    public String deviceId;
}
