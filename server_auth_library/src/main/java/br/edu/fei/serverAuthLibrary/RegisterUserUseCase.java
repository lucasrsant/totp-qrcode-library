package br.edu.fei.serverAuthLibrary;

import java.util.concurrent.ThreadLocalRandom;

public class RegisterUserUseCase {

    private final MailSender mailSender;
    private final Repository<String, IdentifiedUserRegistration> userRepository;

    public RegisterUserUseCase(MailSender mailSender, Repository<String, IdentifiedUserRegistration> userRepository) {
        this.mailSender = mailSender;
        this.userRepository = userRepository;
    }

    public String execute(RegisterDevicePayload request) {

        if(userRepository.contains(request.deviceId))
            return userRepository.get(request.deviceId).email.equals(request.emailAddress)
                    ? "device_already_registered" : "unable_to_register_device";

        String verificationCode = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 999999));
        IdentifiedUserRegistration identifiedUserRegistration = new IdentifiedUserRegistration();
        identifiedUserRegistration.deviceId = request.deviceId;
        identifiedUserRegistration.devicePublicKey = request.devicePublicKey;
        identifiedUserRegistration.email = request.emailAddress;
        identifiedUserRegistration.verificationCode = verificationCode;

        userRepository.insertOrUpdate(identifiedUserRegistration.deviceId, identifiedUserRegistration);

        mailSender.sendVerificationCode(String.valueOf(verificationCode));

        return "device_registered";
    }
}
