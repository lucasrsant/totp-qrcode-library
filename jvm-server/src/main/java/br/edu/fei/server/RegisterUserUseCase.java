package br.edu.fei.server;

import br.edu.fei.server.requests.RegisterUserRequest;

import java.util.concurrent.ThreadLocalRandom;

public class RegisterUserUseCase {

    private final MailSender mailSender;
    private final Repository<String, IdentifiedUserRegistrationRequest> userRepository;

    public RegisterUserUseCase(MailSender mailSender, Repository<String, IdentifiedUserRegistrationRequest> userRepository) {
        this.mailSender = mailSender;
        this.userRepository = userRepository;
    }

    public void execute(RegisterUserRequest userRegistrationRequest) {

        String verificationCode = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 999999));
        IdentifiedUserRegistrationRequest identifiedUserRegistrationRequest = new IdentifiedUserRegistrationRequest();
        identifiedUserRegistrationRequest.deviceId = userRegistrationRequest.deviceId;
        identifiedUserRegistrationRequest.email = userRegistrationRequest.email;
        identifiedUserRegistrationRequest.verificationCode = verificationCode;

        userRepository.insertOrUpdate(identifiedUserRegistrationRequest.email, identifiedUserRegistrationRequest);

        mailSender.sendVerificationCode(String.valueOf(verificationCode));
    }
}
