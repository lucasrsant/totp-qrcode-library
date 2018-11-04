package br.edu.fei.server.requests;

public class ConfirmValidationCodeRequest {
    public String email;
    public String verificationCode;
    public String deviceId;
}
