package br.edu.fei.serverAuthLibrary;

import java.util.concurrent.ThreadLocalRandom;

/***
 * This class implements the use case of a device registration.
 */
public class RegisterDeviceUseCase {

    private final MailSender mailSender;
    private final Repository<String, IdentifiedDeviceRegistrationPayload> deviceRepository;

    /***
     * The class constructor. An instance of {@link MailSender} and {@link Repository} must be provided.
     * @param mailSender Instance of MailSender to be used to send code verification to the user email.
     * @param deviceRepository Instance of Repository to storage the registered, but unverified, device.
     */
    public RegisterDeviceUseCase(MailSender mailSender, Repository<String, IdentifiedDeviceRegistrationPayload> deviceRepository) {
        this.mailSender = mailSender;
        this.deviceRepository = deviceRepository;
    }

    /***
     * Executes the device registration use case with the provided input
     * @param request The payload that contains the device information and the email address.
     * @return A string indicating the result of registration. It could be "device_already_registered",
     * "unable_to_register_device", or "device_registered".<br>
     * <br>
     * <code>device_already_registered</code> - Indicates that the device is already registered for the provided email address.<br>
     * <code>unable_to_register_device</code> - Indicates that the device is already registered for another email address.<br>
     * <code>device_registered</code> - Indicates that the device was registered successfully.
     */
    public String execute(RegisterDevicePayload request) {

        if(deviceRepository.contains(request.deviceId))
            return deviceRepository.get(request.deviceId).email.equals(request.emailAddress)
                    ? "device_already_registered" : "unable_to_register_device";

        String verificationCode = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 999999));
        IdentifiedDeviceRegistrationPayload identifiedDeviceRegistrationPayload = new IdentifiedDeviceRegistrationPayload();
        identifiedDeviceRegistrationPayload.deviceId = request.deviceId;
        identifiedDeviceRegistrationPayload.devicePublicKey = request.devicePublicKey;
        identifiedDeviceRegistrationPayload.email = request.emailAddress;
        identifiedDeviceRegistrationPayload.verificationCode = verificationCode;

        deviceRepository.insertOrUpdate(identifiedDeviceRegistrationPayload.deviceId, identifiedDeviceRegistrationPayload);

        mailSender.sendVerificationCode(String.valueOf(verificationCode));

        return "device_registered";
    }
}
