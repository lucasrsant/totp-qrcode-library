package br.edu.fei.server;

import br.edu.fei.server.payloads.RegisterDevicePayload;

import java.util.concurrent.ThreadLocalRandom;

public class RegisterUserUseCase {

    private final MailSender mailSender;
    private final Repository<String, IdentifiedUserRegistrationRequest> userRepository;

    public RegisterUserUseCase(MailSender mailSender, Repository<String, IdentifiedUserRegistrationRequest> userRepository) {
        this.mailSender = mailSender;
        this.userRepository = userRepository;
    }

    public void execute(RegisterDevicePayload userRegistrationRequest) {

        String verificationCode = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 999999));
        IdentifiedUserRegistrationRequest identifiedUserRegistrationRequest = new IdentifiedUserRegistrationRequest();
        identifiedUserRegistrationRequest.deviceId = userRegistrationRequest.deviceId;
        identifiedUserRegistrationRequest.devicePublicKey = userRegistrationRequest.devicePublicKey;
        identifiedUserRegistrationRequest.email = userRegistrationRequest.emailAddress;
        identifiedUserRegistrationRequest.verificationCode = verificationCode;

        userRepository.insertOrUpdate(identifiedUserRegistrationRequest.email, identifiedUserRegistrationRequest);

        mailSender.sendVerificationCode(String.valueOf(verificationCode));
    }
}
